/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.kuka.kr;

import org.roboticsapi.cartesianmotion.parameter.CartesianParameters;
import org.roboticsapi.core.InvalidParametersException;
import org.roboticsapi.multijoint.AbstractJoint;
import org.roboticsapi.multijoint.JointDriver;
import org.roboticsapi.multijoint.link.BaseLink;
import org.roboticsapi.multijoint.link.FlangeLink;
import org.roboticsapi.multijoint.link.JointLink;
import org.roboticsapi.multijoint.link.Link;
import org.roboticsapi.robot.parameter.RobotTool;
import org.roboticsapi.robot.parameter.RobotToolParameter;
import org.roboticsapi.world.Transformation;

public class KR5scaraR350Z320 extends AbstractKRscara {

	public KR5scaraR350Z320() {
		super();
	}

	@Override
	protected void defineMaximumParameters() throws InvalidParametersException {
		super.defineMaximumParameters();
		addMaximumParameters(new CartesianParameters(2, 10, Math.PI / 2f, Math.PI));
		addMaximumParameters(new RobotToolParameter(new RobotTool(210, getFlange(), 25, 25, 25)));
	}

	@Override
	protected AbstractJoint<JointDriver> createJoint(int number, String name, JointDriver driver) {
		switch (number) {
		case 0:
			return createRevoluteJoint(driver, name, Math.toRadians(-155), Math.toRadians(155), Math.toRadians(525),
					Math.toRadians(1050));
		case 1:
			return createRevoluteJoint(driver, name, Math.toRadians(-145), Math.toRadians(145), Math.toRadians(525),
					Math.toRadians(1050));
		case 2:
			return createPrismaticJoint(driver, name, 0, 0.32, 2, 4);
		case 3:
			return createRevoluteJoint(driver, name, Math.toRadians(-358), Math.toRadians(358), Math.toRadians(2400),
					Math.toRadians(480));
		default:
			return null;
		}
	}

	@Override
	protected Link createLink(int number) {
		switch (number) {
		case 0:
			return new BaseLink(getBase(), getJoint(0), new Transformation(0d, 0d, 0.301d, 0d, 0d, 0d));
		case 1:
			return new JointLink(getJoint(0), getJoint(1), new Transformation(0.125, 0.0, 0.076, 0.0, 0.0, 0.0));
		case 2:
			return new JointLink(getJoint(1), getJoint(2), new Transformation(0.225, 0.0, -0.131, 0.0, 0.0, 0.0));
		case 3:
			return new JointLink(getJoint(2), getJoint(3), new Transformation(0.0, 0.0, 0.0, 0.0, 0.0, 0.0));
		case 4:
			return new FlangeLink(getJoint(3), getFlange(), new Transformation(0.0, 0.0, 0.0, 0.0, 0.0, 3.1416));
		}
		return null;
	}

}
