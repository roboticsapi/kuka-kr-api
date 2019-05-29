/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.kuka.kr.javarcc.devices;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.roboticsapi.runtime.robot.javarcc.DHKinematics;
import org.roboticsapi.runtime.world.javarcc.primitives.RPICalc;
import org.roboticsapi.runtime.world.types.RPIFrame;
import org.roboticsapi.world.mutable.MutableRotation;
import org.roboticsapi.world.mutable.MutableTransformation;

public class KRKin {

	DHKinematics kin;

	double[] q_min, q_max;

	private double[] d, theta, a, alpha;

	public KRKin(double[] d, double[] theta, double[] a, double[] alpha, double[] min, double[] max) {
		this.d = d;
		this.theta = theta;
		this.a = a;
		this.alpha = alpha;
		kin = new DHKinematics(d, theta, a, alpha, true);
		q_min = min;
		q_max = max;
	}

	public RPIFrame kin(double[] joints, RPIFrame ret) {
		return kin.kin(joints, ret);
	}

	public double[] invKin(RPIFrame frame, double[] hintJoints) {
		List<double[]> res8 = invKin8(frame);

		// Normalize solutions to [-PI + hint joint, PI + hint joint]
		for (int i = 0; i < res8.size(); i++) {
			for (int j = 0; j < 6; j++) {
				// Solution found differs more than 180 degrees from hint joints
				double diff;
				while (Math.abs(diff = res8.get(i)[j] - hintJoints[j]) > Math.PI) {
					res8.get(i)[j] -= Math.signum(diff) * 2 * Math.PI;
				}
			}
		}

		// Select valid configurations
		List<double[]> valid_config = new ArrayList<double[]>();

		for (double[] res : res8) {
			boolean valid = true;
			for (int j = 0; j < 6; j++) {
				// solution exceeds maximum joint value
				while (res[j] > q_max[j]) {
					res[j] -= 2 * Math.PI;
				}
				// solution below minimum joint value
				while (res[j] < q_min[j]) {
					res[j] += 2 * Math.PI;
				}

				// check whether (adjusted) joint value is still inside joint
				// limits
				valid &= (res[j] <= q_max[j]);
			}
			if (valid) {
				valid_config.add(res);
				// std::cout << "Solution " << (i + 1) << " valid" << std::endl;
			}
		}

		if (valid_config.size() == 0) {
			return new double[] { Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN };
		}

		// Select best configuration

		int selected = 0;
		double min_sq = Double.POSITIVE_INFINITY;

		for (int i = 0; i < valid_config.size(); i++) {
			double sq = 0;
			for (int j = 0; j < 6; j++) {
				double diff = valid_config.get(i)[j] - hintJoints[j];
				sq += diff * diff;
			}

			if (sq < min_sq) {
				selected = i;
				min_sq = sq;
			}
		}

		return valid_config.get(selected);
	}

	private void dh(double a, double al, double d, double th, MutableTransformation ret) {
		MutableTransformation tmp = RPICalc.frameCreate();
		ret.setVectorEuler(0, 0, d, th, 0, 0);
		tmp.setVectorEuler(a, 0, 0, 0, 0, al);
		ret.multiply(tmp);
	}

	public List<double[]> invKin8(RPIFrame position) {
		MutableTransformation pos = RPICalc.frameCreate();
		MutableTransformation ourpos = RPICalc.frameCreate();
		MutableTransformation t5e = RPICalc.frameCreate();
		MutableTransformation tr5 = RPICalc.frameCreate();
		MutableTransformation tr1 = RPICalc.frameCreate();
		MutableTransformation t15 = RPICalc.frameCreate();
		MutableTransformation t12 = RPICalc.frameCreate();
		MutableTransformation t23 = RPICalc.frameCreate();
		MutableTransformation tr3 = RPICalc.frameCreate();
		MutableTransformation t3e = RPICalc.frameCreate();

		List<double[]> result = new ArrayList<double[]>();
		ourpos.setVectorEuler(0, 0, 0, 0, 0, Math.PI);
		RPICalc.rpiToFrame(position, pos);
		ourpos.multiply(pos);

		dh(a[5], alpha[5], d[5], theta[5], t5e);
		t5e.invertTo(tr5);
		ourpos.multiplyTo(tr5, tr5);

		// Two solutions for axis 1
		for (int iq1 = 0; iq1 < 2; ++iq1) {
			// Angle 1
			double q1 = Math.atan2(tr5.getTranslation().getY(), tr5.getTranslation().getX());

			// Second solution?
			if (iq1 == 1) {
				q1 += (q1 > 0) ? -Math.PI : Math.PI;
			}

			dh(a[0], alpha[0], d[0], theta[0] + q1, tr1);
			tr1.invertTo(t15);
			t15.multiply(tr5);

			// Calculate Angles 2 and 3 (two solutions)
			List<double[]> q23 = calculate23(t15.getTranslation().getX(), t15.getTranslation().getY(), a[1],
					Math.abs(d[3]), a[2]);

			// Solution found
			if (q23.size() == 2) {

				// Two solutions for q23
				for (int iq2 = 0; iq2 < 2; ++iq2) {
					dh(a[1], alpha[1], d[1], theta[1] + q23.get(iq2)[0], t12);
					dh(a[2], alpha[2], d[2], theta[2] + q23.get(iq2)[1], t23);
					tr1.multiplyTo(t12, tr3);
					tr3.multiply(t23);
					tr3.invertTo(t3e);
					t3e.multiply(ourpos);

					List<double[]> q456 = calculate456(t3e.getRotation());

					// Two solutions for q456
					for (int iq4 = 0; iq4 < 2; iq4++) {
						double[] sol = new double[6];
						sol[0] = q1;
						sol[1] = q23.get(iq2)[0];
						sol[2] = q23.get(iq2)[1];
						sol[3] = q456.get(iq4)[0];
						sol[4] = q456.get(iq4)[1];
						sol[5] = q456.get(iq4)[2];

						result.add(sol);
					}
				}
			} else {
				// If no solution for first axis 1 solution found, no solutions
				// will ever be found
				if (iq1 == 0)
					return result;
			}
		}
		return result;
	}

	private List<double[]> calculate23(double x, double y, double a1, double a2, double offset) {
		double d = Math.sqrt(x * x + y * y);
		double a = Math.sqrt(a2 * a2 + offset * offset);
		double gamma = Math.atan2(offset, a2);

		double v2 = Math.acos(((a1 * a1) + (a * a) - (d * d)) / (2 * a1 * a));

		if (v2 != v2)
			return new ArrayList<double[]>();

		double[] qa = new double[2], qb = new double[2];

		qa[1] = -Math.PI + v2 - gamma;
		qb[1] = Math.PI - v2 - gamma;

		double delta = Math.atan2(-y, x);

		double beta = Math.acos(((a1 * a1) + (d * d) - (a * a)) / (2 * a1 * d));

		qa[0] = -delta + beta;
		qb[0] = -delta - beta;

		return Arrays.asList(qa, qb);
	}

	private List<double[]> calculate456(MutableRotation rot) {
		// adjust the end effector orientation to the orientation of frame 5
		MutableRotation r3e = RPICalc.rotationCreate();
		r3e.setEuler(0, 0, Math.PI);
		rot.multiplyTo(r3e, r3e);

		double[] data = new double[9];
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				data[i * 3 + j] = r3e.get(i, j);

		double sqrtv = Math.sqrt(data[2] * data[2] + data[5] * data[5]);

		double[] qa = new double[3], qb = new double[3];

		qa[0] = Math.atan2(+data[5], +data[2]);
		qb[0] = Math.atan2(-data[5], -data[2]);

		qb[1] = Math.atan2(+sqrtv, data[8]);
		qa[1] = Math.atan2(-sqrtv, data[8]);

		qa[2] = Math.atan2(+data[7], -data[6]);
		qb[2] = Math.atan2(-data[7], +data[6]);

		return Arrays.asList(qa, qb);
	}

}
