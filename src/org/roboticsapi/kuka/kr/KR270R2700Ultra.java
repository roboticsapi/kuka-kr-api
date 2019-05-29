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

public class KR270R2700Ultra extends AbstractKR {

	public KR270R2700Ultra() {
		super();
	}

	@Override
	protected void defineMaximumParameters() throws InvalidParametersException {
		super.defineMaximumParameters();
		addMaximumParameters(new CartesianParameters(2, 10, Math.PI / 2f, Math.PI));
		addMaximumParameters(new RobotToolParameter(new RobotTool(270, getFlange(), 25, 25, 25)));
	}

	@Override
	protected RevoluteJoint<JointDriver> createJoint(int number, String name, JointDriver driver) {
		switch (number) {
		case 0:
			return createRevoluteJoint(driver, name, Math.toRadians(-185), Math.toRadians(185), Math.toRadians(105),
					Math.toRadians(210));
		case 1:
			return createRevoluteJoint(driver, name, Math.toRadians(-140), Math.toRadians(-5), Math.toRadians(101),
					Math.toRadians(202), null, Math.toRadians(-90));
		case 2:
			return createRevoluteJoint(driver, name, Math.toRadians(-120), Math.toRadians(155), Math.toRadians(107),
					Math.toRadians(214), null, Math.toRadians(90));
		case 3:
			return createRevoluteJoint(driver, name, Math.toRadians(-350), Math.toRadians(350), Math.toRadians(122),
					Math.toRadians(244));
		case 4:
			return createRevoluteJoint(driver, name, Math.toRadians(-122.5), Math.toRadians(122.5), Math.toRadians(113),
					Math.toRadians(226));
		case 5:
			return createRevoluteJoint(driver, name, Math.toRadians(-350), Math.toRadians(350), Math.toRadians(175),
					Math.toRadians(350));
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
		return -1.2;
	}

	@Override
	protected double getD5() {
		return -0.24; // Ausgemessen aus dem CAD-Modell
	}

	@Override
	protected double getA0() {
		return 0.35;
	}

	@Override
	protected double getA1() {
		return 1.150;
	}

	@Override
	protected double getA2() {
		return 0.041;
	}

}
