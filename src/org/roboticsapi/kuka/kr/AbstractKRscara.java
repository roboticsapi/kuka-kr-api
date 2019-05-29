/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.kuka.kr;

import org.roboticsapi.core.DeviceParameters;
import org.roboticsapi.core.InvalidParametersException;
import org.roboticsapi.multijoint.AbstractJoint;
import org.roboticsapi.multijoint.Joint;
import org.roboticsapi.multijoint.JointDriver;
import org.roboticsapi.robot.AbstractRobotArm;
import org.roboticsapi.robot.RobotArmDriver;

public abstract class AbstractKRscara extends AbstractRobotArm<Joint, RobotArmDriver> {

	public AbstractKRscara() {
		super(4);
	}

	@Override
	protected void defineMaximumParameters() throws InvalidParametersException {
		addMaximumParameters(getJointDeviceParameters(0, 0));
	}

	@Override
	protected final Joint createJoint(int number, String name) {
		JointDriver jointDriver = getDriver().createJointDriver(number);
		return createJoint(number, name, jointDriver);
	}

	protected abstract AbstractJoint<JointDriver> createJoint(int number, String name, JointDriver driver);

	@Override
	public void validateParameters(DeviceParameters parameters) throws InvalidParametersException {
	}

}
