/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2013-2019 ISSE, University of Augsburg 
 */

package org.roboticsapi.device.kuka.kr.javarcc;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.roboticsapi.device.kuka.kr.javarcc.devices.KrKin;
import org.roboticsapi.facet.runtime.rpi.core.types.RPIdouble;
import org.roboticsapi.facet.runtime.rpi.world.types.RPIFrame;
import org.roboticsapi.facet.runtime.rpi.world.types.RPIRotation;
import org.roboticsapi.facet.runtime.rpi.world.types.RPIVector;

@RunWith(Parameterized.class)
public class KRKinTest {
	
	// KR16 values
	private double[] d = new double[] { -0.675, 0, 0, -0.67, 0, -0.115 };
	private double[] a = new double[] { 0.26, 0.68, 0.035, 0, 0, 0 };
	private double[] alpha = new double[] { +Math.PI / 2d, 0, -Math.PI / 2d, +Math.PI / 2d, -Math.PI / 2d, +Math.PI };
	private double[] theta = new double[] { 0, 0, +Math.PI / 2d, 0, 0, 0 };
	private double[] qmin = new double[] { Math.toRadians(-185), Math.toRadians(-155), Math.toRadians(-130),
			Math.toRadians(-350), Math.toRadians(-130), Math.toRadians(-350) };
	private double[] qmax = new double[] { Math.toRadians(185), Math.toRadians(35), Math.toRadians(154),
			Math.toRadians(350), Math.toRadians(130), Math.toRadians(350) };
	private double[] jointValues;
	private double[] cartesianValues;

	@Parameterized.Parameters
	public static Collection<?> testValues() {
		return Arrays.asList(new Object[][] {
				// {joint values}, {cartesian values}
				{ new double[] { 0.0, -1.5707963267948966, 1.5707963267948966, 0.0, 1.5707963267948966, 0.0 },
						new double[] { 0.93, 0d, 1.205, -Math.PI, 0, -Math.PI } },
				{ new double[] { 0.583375, -2.583779, -1.396541, 0.725790, -2.162611, 0.459666 },
						new double[] { -0.545000, 0.435600, 0.560000, 0d, Math.PI / 2f, 0d } },
				{ new double[] { 0.0, -1.5707963267948966, 1.5707963267948966, 0.0, 0.0, 0.0 },
						new double[] { 1.0455, 0, 1.32, 0, Math.PI / 2f, 0 } },
				{ new double[] { 2.558220, -1.371372, 2.258478, 2.436314, -2.126088, -1.492669 },
						new double[] { -0.545000, -0.435600, 0.800000, 1.570790, 0.500000, 1.570790 } },

		});
	}

	public KRKinTest(double[] jointValues, double[] cartesianValues) {
		this.jointValues = jointValues;
		this.cartesianValues = cartesianValues;
	}

	@Test
	public void testInvKinFindsGivenSolution() {
		KrKin kin = new KrKin(d, theta, a, alpha, qmin, qmax);
	
		RPIFrame input = new RPIFrame(
				new RPIVector(new RPIdouble(cartesianValues[0]), new RPIdouble(cartesianValues[1]),
						new RPIdouble(cartesianValues[2])),
				new RPIRotation(new RPIdouble(cartesianValues[3]), new RPIdouble(cartesianValues[4]),
						new RPIdouble(cartesianValues[5])));
		List<double[]> invKin = kin.invKin8(input);
		boolean solution = false;

		for (double[] sol : invKin) {
			boolean near = true;
			int i = 0;
			for (double v : sol) {
				double dist = v - jointValues[i];
				while (dist > Math.PI)
					dist -= 2 * Math.PI;
				while (dist < -Math.PI)
					dist += 2 * Math.PI;

				if (!near(dist, 0)) {
					near = false;
					break;
				}
				++i;
			}
			solution = near;
			if (solution)
				break;
		}

		Assert.assertTrue(
				"solution not found: j=" + Arrays.toString(jointValues) + "|c=" + Arrays.toString(cartesianValues),
				solution);

		double[] res = kin.invKin(input, jointValues);
		for (int i = 0; i < res.length; i++)
			Assert.assertEquals("Incorrect solution for joint " + i, jointValues[i], res[i], 0.001);
	}

	private boolean near(double v, double e) {
		return Math.abs(v - e) < 0.001;
	}
	
	@Test
	public void testInvKinReturnsNanArrayWhenWorkspaceExceeded() {
		KrKin kin = new KrKin(d, theta, a, alpha, qmin, qmax);
		
		RPIFrame input = new RPIFrame(
				new RPIVector(new RPIdouble(cartesianValues[0] + 1000), new RPIdouble(cartesianValues[1]  + 1000),
						new RPIdouble(cartesianValues[2]  + 1000)),
				new RPIRotation(new RPIdouble(cartesianValues[3]  + 1000), new RPIdouble(cartesianValues[4]  + 1000),
						new RPIdouble(cartesianValues[5]  + 1000)));
		
		double[] invKin = kin.invKin(input, new double[] {0,0,0,0,0,0});
		
		boolean nanFound = false;
		for(double d : invKin) {
			if(Double.isNaN(d)) {
				nanFound = true;
				break;
			}
		}
		
		Assert.assertTrue(nanFound);
	}
}
