/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.kuka.kr.runtime;

import java.util.Map;

import org.roboticsapi.kuka.kr.AbstractKR;
import org.roboticsapi.runtime.robot.driver.SoftRobotDHRobotArmDriver;

public final class SimulatedKRDriver extends SoftRobotDHRobotArmDriver<AbstractKR> implements KRDriver {

	private final static String DEVICE_TYPE = "kuka_kr_arm_sim";

	public SimulatedKRDriver() {
		super();
	}

	@Override
	public String getDeviceType() {
		return DEVICE_TYPE;
	}

	@Override
	protected void collectDriverSpecificParameters(Map<String, String> parameters) {
		// empty
	}

}
