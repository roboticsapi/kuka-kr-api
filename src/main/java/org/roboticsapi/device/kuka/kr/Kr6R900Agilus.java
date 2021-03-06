/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2013-2019 ISSE, University of Augsburg 
 */

package org.roboticsapi.device.kuka.kr;

import org.roboticsapi.core.DeviceParameters;
import org.roboticsapi.core.InvalidParametersException;
import org.roboticsapi.core.world.Pose;
import org.roboticsapi.framework.cartesianmotion.parameter.CartesianParameters;
import org.roboticsapi.framework.multijoint.JointDriver;
import org.roboticsapi.framework.multijoint.RevoluteJoint;
import org.roboticsapi.framework.robot.parameter.RobotTool;
import org.roboticsapi.framework.robot.parameter.RobotToolParameter;

public class Kr6R900Agilus extends AbstractKr {

	public Kr6R900Agilus() {
		super();
	}

	@Override
	protected void defineMaximumParameters() throws InvalidParametersException {
		super.defineMaximumParameters();
		addMaximumParameters(new CartesianParameters(2, 10, 100, Math.PI / 2f, Math.PI, 10 * Math.PI,
				Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
		addMaximumParameters(new RobotToolParameter(new RobotTool(6, new Pose(getFlange()), 25, 25, 25)));
	}

	@Override
	protected RevoluteJoint<JointDriver> createJoint(int number, String name) {
		switch (number) {
		case 0:
			return createRevoluteJoint(name, Math.toRadians(-170), Math.toRadians(170), Math.toRadians(360),
					Math.toRadians(720));
		case 1:
			return createRevoluteJoint(name, Math.toRadians(-190), Math.toRadians(45), Math.toRadians(300),
					Math.toRadians(600), null, Math.toRadians(-90));
		case 2:
			return createRevoluteJoint(name, Math.toRadians(-120), Math.toRadians(156), Math.toRadians(360),
					Math.toRadians(720), null, Math.toRadians(90));
		case 3:
			return createRevoluteJoint(name, Math.toRadians(-185), Math.toRadians(185), Math.toRadians(381),
					Math.toRadians(762));
		case 4:
			return createRevoluteJoint(name, Math.toRadians(-120), Math.toRadians(120), Math.toRadians(388),
					Math.toRadians(776));
		case 5:
			return createRevoluteJoint(name, Math.toRadians(-350), Math.toRadians(350), Math.toRadians(615),
					Math.toRadians(1230));
		default:
			return null;
		}
	}

	@Override
	public void validateParameters(DeviceParameters parameters) throws InvalidParametersException {
	}

	@Override
	protected double getD0() {
		return -0.4;
	}

	@Override
	protected double getD3() {
		return -0.42;
	}

	@Override
	protected double getD5() {
		return -0.08;
	}

	@Override
	protected double getA0() {
		return 0.025;
	}

	@Override
	protected double getA1() {
		return 0.455;
	}

	@Override
	protected double getA2() {
		return -0.035;
	}

}
