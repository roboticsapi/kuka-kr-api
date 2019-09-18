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

public class Kr210R3100Ultra extends AbstractKr {

	public Kr210R3100Ultra() {
		super();
	}

	@Override
	protected void defineMaximumParameters() throws InvalidParametersException {
		super.defineMaximumParameters();

		addMaximumParameters(new CartesianParameters(2, 3, 80, Math.toRadians(200), Math.toRadians(200) * 2,
				Math.toRadians(200) * 20, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
		addMaximumParameters(new RobotToolParameter(new RobotTool(210, new Pose(getFlange()), 25, 25, 25)));
	}

	@Override
	protected RevoluteJoint<JointDriver> createJoint(int number, String name) {
		switch (number) {
		case 0:
			return createRevoluteJoint(name, Math.toRadians(-185), Math.toRadians(185), Math.toRadians(105),
					Math.toRadians(210));
		case 1:
			return createRevoluteJoint(name, Math.toRadians(-140), Math.toRadians(0), Math.toRadians(101),
					Math.toRadians(202), null, Math.toRadians(-90));
		case 2:
			return createRevoluteJoint(name, Math.toRadians(-120), Math.toRadians(155), Math.toRadians(107),
					Math.toRadians(214), null, Math.toRadians(90));
		case 3:
			return createRevoluteJoint(name, Math.toRadians(-350), Math.toRadians(350), Math.toRadians(136),
					Math.toRadians(272));
		case 4:
			return createRevoluteJoint(name, Math.toRadians(-122.5), Math.toRadians(122.5), Math.toRadians(129),
					Math.toRadians(158));
		case 5:
			return createRevoluteJoint(name, Math.toRadians(-350), Math.toRadians(350), Math.toRadians(206),
					Math.toRadians(412));
		default:
			return null;
		}
	}

	@Override
	public void validateParameters(DeviceParameters parameters) throws InvalidParametersException {
	}

	@Override
	protected double getD0() {
		return -0.675;
	}

	@Override
	protected double getD3() {
		return -1.4;
	}

	@Override
	protected double getD5() {
		return -0.24;
	}

	@Override
	protected double getA0() {
		return 0.35;
	}

	@Override
	protected double getA1() {
		return 1.35;
	}

	@Override
	protected double getA2() {
		return 0.041;
	}

}
