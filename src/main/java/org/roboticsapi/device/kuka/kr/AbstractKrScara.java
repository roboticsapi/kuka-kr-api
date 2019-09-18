/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2013-2019 ISSE, University of Augsburg 
 */

package org.roboticsapi.device.kuka.kr;

import org.roboticsapi.core.DeviceParameters;
import org.roboticsapi.core.InvalidParametersException;
import org.roboticsapi.framework.multijoint.Joint;
import org.roboticsapi.framework.robot.AbstractRobotArm;
import org.roboticsapi.framework.robot.RobotArmDriver;

public abstract class AbstractKrScara extends AbstractRobotArm<Joint, RobotArmDriver> {

	public AbstractKrScara() {
		super(4);
	}

	@Override
	protected void defineMaximumParameters() throws InvalidParametersException {
		addMaximumParameters(getJointDeviceParameters(0, 0));
	}

	@Override
	public void validateParameters(DeviceParameters parameters) throws InvalidParametersException {
	}

}
