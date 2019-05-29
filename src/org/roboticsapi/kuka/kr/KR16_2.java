/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.kuka.kr;

import org.roboticsapi.cartesianmotion.parameter.CartesianParameters;
import org.roboticsapi.core.DeviceParameters;
import org.roboticsapi.core.InvalidParametersException;
import org.roboticsapi.multijoint.JointDriver;
import org.roboticsapi.multijoint.RevoluteJoint;
import org.roboticsapi.robot.parameter.RobotTool;
import org.roboticsapi.robot.parameter.RobotToolParameter;

public class KR16_2 extends AbstractKR {

	public KR16_2() {
		super();
	}

	@Override
	protected void defineMaximumParameters() throws InvalidParametersException {
		super.defineMaximumParameters();
		addMaximumParameters(new CartesianParameters(2, 10, Math.PI / 2f, Math.PI));
		addMaximumParameters(new RobotToolParameter(new RobotTool(16, getFlange(), 25, 25, 25)));
	}

	@Override
	protected RevoluteJoint<JointDriver> createJoint(int number, String name, JointDriver driver) {
		switch (number) {
		case 0:
			return createRevoluteJoint(driver, name, Math.toRadians(-185), Math.toRadians(185), Math.toRadians(156),
					Math.toRadians(312));
		case 1:
			return createRevoluteJoint(driver, name, Math.toRadians(-155), Math.toRadians(35), Math.toRadians(156),
					Math.toRadians(312), null, Math.toRadians(-90));
		case 2:
			return createRevoluteJoint(driver, name, Math.toRadians(-130), Math.toRadians(154), Math.toRadians(156),
					Math.toRadians(312), null, Math.toRadians(90));
		case 3:
			return createRevoluteJoint(driver, name, Math.toRadians(-350), Math.toRadians(350), Math.toRadians(330),
					Math.toRadians(660));
		case 4:
			return createRevoluteJoint(driver, name, Math.toRadians(-130), Math.toRadians(130), Math.toRadians(330),
					Math.toRadians(660));
		case 5:
			return createRevoluteJoint(driver, name, Math.toRadians(-350), Math.toRadians(350), Math.toRadians(615),
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
		return -0.675;
	}

	@Override
	protected double getD3() {
		return -0.67;
	}

	@Override
	protected double getD5() {
		return -0.115;
	}

	@Override
	protected double getA0() {
		return 0.26;
	}

	@Override
	protected double getA1() {
		return 0.68;
	}

	@Override
	protected double getA2() {
		return 0.035;
	}

}
