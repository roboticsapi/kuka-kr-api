/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2013-2019 ISSE, University of Augsburg 
 */

package org.roboticsapi.device.kuka.kr;

import org.roboticsapi.core.InvalidParametersException;
import org.roboticsapi.core.world.Pose;
import org.roboticsapi.core.world.Transformation;
import org.roboticsapi.framework.cartesianmotion.parameter.CartesianParameters;
import org.roboticsapi.framework.multijoint.AbstractJoint;
import org.roboticsapi.framework.multijoint.JointDriver;
import org.roboticsapi.framework.multijoint.link.BaseLink;
import org.roboticsapi.framework.multijoint.link.FlangeLink;
import org.roboticsapi.framework.multijoint.link.JointLink;
import org.roboticsapi.framework.multijoint.link.Link;
import org.roboticsapi.framework.robot.parameter.RobotTool;
import org.roboticsapi.framework.robot.parameter.RobotToolParameter;

public class Kr5ScaraR350Z320 extends AbstractKrScara {

	public Kr5ScaraR350Z320() {
		super();
	}

	@Override
	protected void defineMaximumParameters() throws InvalidParametersException {
		super.defineMaximumParameters();
		addMaximumParameters(new CartesianParameters(2, 10, Double.POSITIVE_INFINITY, Math.PI / 2f, Math.PI,
				Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
		addMaximumParameters(new RobotToolParameter(new RobotTool(210, new Pose(getFlange()), 25, 25, 25)));
	}

	@Override
	protected AbstractJoint<JointDriver> createJoint(int number, String name) {
		switch (number) {
		case 0:
			return createRevoluteJoint(name, Math.toRadians(-155), Math.toRadians(155), Math.toRadians(525),
					Math.toRadians(1050));
		case 1:
			return createRevoluteJoint(name, Math.toRadians(-145), Math.toRadians(145), Math.toRadians(525),
					Math.toRadians(1050));
		case 2:
			return createPrismaticJoint(name, 0, 0.32, 2, 4);
		case 3:
			return createRevoluteJoint(name, Math.toRadians(-358), Math.toRadians(358), Math.toRadians(2400),
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
