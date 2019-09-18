/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2013-2019 ISSE, University of Augsburg 
 */

package org.roboticsapi.device.kuka.kr;

import java.util.Arrays;
import java.util.Map;

import org.roboticsapi.core.DeviceParameters;
import org.roboticsapi.core.InvalidParametersException;
import org.roboticsapi.core.realtimevalue.RealtimeValueReadException;
import org.roboticsapi.core.world.Relation;
import org.roboticsapi.core.world.Transformation;
import org.roboticsapi.core.world.TransformationException;
import org.roboticsapi.core.world.World;
import org.roboticsapi.core.world.realtimevalue.realtimetransformation.RealtimeTransformation;
import org.roboticsapi.framework.multijoint.Joint;
import org.roboticsapi.framework.multijoint.link.BaseLink;
import org.roboticsapi.framework.multijoint.link.FlangeLink;
import org.roboticsapi.framework.multijoint.link.JointLink;
import org.roboticsapi.framework.multijoint.link.Link;
import org.roboticsapi.framework.robot.AbstractRobotArm;
import org.roboticsapi.framework.robot.DHRobotArm;
import org.roboticsapi.framework.robot.RobotArmDriver;

public abstract class AbstractKr extends AbstractRobotArm<Joint, RobotArmDriver> implements DHRobotArm {

	private static final int NUMBER_OF_JOINTS = 6;

	public AbstractKr() {
		super(NUMBER_OF_JOINTS);
	}

	@Override
	protected void defineMaximumParameters() throws InvalidParametersException {
		super.defineDefaultParameters();
		addMaximumParameters(getJointDeviceParameters(0, 0));
	}

	@Override
	protected final Link createLink(int number) {
		switch (number) {
		case 0:
			return new BaseLink(getBase(), getJoint(0), new Transformation(0d, 0d, 0d, 0d, 0d, Math.PI));
		case 1:
			return new JointLink(getJoint(0), getJoint(1),
					new Transformation(getA0(), 0.0, getD0(), 0.0, 0.0, getAlpha()[0]));
		case 2:
			return new JointLink(getJoint(1), getJoint(2),
					new Transformation(getA1(), 0.0, 0.0, 0.0, 0.0, getAlpha()[1]));
		case 3:
			return new JointLink(getJoint(2), getJoint(3),
					new Transformation(0.0, getA2(), 0.0, getTheta()[2], 0.0, getAlpha()[2]));
		case 4:
			return new JointLink(getJoint(3), getJoint(4),
					new Transformation(0.0, 0.0, getD3(), 0.0, 0.0, getAlpha()[3]));
		case 5:
			return new JointLink(getJoint(4), getJoint(5), new Transformation(0.0, 0.0, 0.0, 0.0, 0.0, getAlpha()[4]));
		case 6:
			return new FlangeLink(getJoint(5), getFlange(),
					new Transformation(0.0, 0.0, getD5(), 0.0, 0.0, getAlpha()[5]));
		}
		return null;
	}

	@Override
	public final double[] getA() {
		double[] a = new double[NUMBER_OF_JOINTS];
		a[0] = getA0();
		a[1] = getA1();
		a[2] = getA2();
		return a;
	}

	@Override
	public final double[] getAlpha() {
		double[] alpha = new double[NUMBER_OF_JOINTS];
		alpha[0] = +Math.PI / 2d;
		alpha[2] = -Math.PI / 2d;
		alpha[3] = +Math.PI / 2d;
		alpha[4] = -Math.PI / 2d;
		alpha[5] = +Math.PI;
		return alpha;
	}

	@Override
	public final double[] getD() {
		double[] d = new double[NUMBER_OF_JOINTS];
		d[0] = getD0();
		d[3] = getD3();
		d[5] = getD5();
		return d;
	}

	@Override
	public final double[] getTheta() {
		double[] theta = new double[NUMBER_OF_JOINTS];
		theta[2] = +Math.PI / 2d;
		return theta;
	}

	protected abstract double getD0();

	protected abstract double getD3();

	protected abstract double getD5();

	protected abstract double getA0();

	protected abstract double getA1();

	protected abstract double getA2();

	@Override
	public void validateParameters(DeviceParameters parameters) throws InvalidParametersException {
	}

	public StatusTurn getStatusTurnFor(double[] jointValues) {

		// Check argument
		if (jointValues.length != NUMBER_OF_JOINTS) {
			throw new IllegalArgumentException("Unexpected number of joint values. " + "Expected " + NUMBER_OF_JOINTS
					+ ", but passed: " + jointValues.length);
		}

		// Compute TURN
		boolean turn[] = new boolean[NUMBER_OF_JOINTS];
		Arrays.fill(turn, false);

		for (int i = 0; i < jointValues.length; i++) {
			if (jointValues[i] < 0) {
				turn[i] = true;
			}
		}

		// Compute STATUS
		boolean overhead = false;
		boolean axisA3negative = false;
		boolean axisA5negative = false;

		double theta = 0.0;

		// For robots with offset between axis A3 and A4 (getA2() != 0) we
		// need to find the angle of the axis A3 (at which it intersect

		if (getA2() != 0.0) {
			double ratio = getA2() / getD3();
			theta = Math.atan(ratio);
		}

		if (jointValues[2] < theta) {
			axisA3negative = true;
		}

		if (jointValues[4] < 0) {
			axisA5negative = true;
		}

		// Overhead ?
		try {
			overhead = isOverheadSolution(jointValues);

		} catch (RealtimeValueReadException e) {
			e.printStackTrace();
		} catch (TransformationException e) {
			e.printStackTrace();
		}

		return new StatusTurn(overhead, axisA3negative, axisA5negative, turn);
	}

	/*
	 * When the wrist root point, located at the intersection of axes A4, A5 and
	 * A6, has negative x coordinates in the coordinate system of the axis 1
	 * (inclusive given joint rotation), this is a overhead solution. Otherwise
	 * it's a basic solution. This function assumes, the joint 5 is the wrist
	 * point and works probably only with KUKA KR robots.
	 */
	public boolean isOverheadSolution(double[] jointValues) throws RealtimeValueReadException, TransformationException {
		Map<Relation, RealtimeTransformation> jointMap = this.getForwardKinematicsRelationMap(jointValues);

		Transformation A1toA5 = getJoint(0).getMovingFrame().getTransformationTo(getJoint(5).getFixedFrame(),
				World.getMeasuredTopology().withSubstitution(jointMap, null));
		return A1toA5.getTranslation().getX() < 0.0;
	}
}
