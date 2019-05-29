/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.kuka.kr.runtime;

import org.roboticsapi.cartesianmotion.device.CartesianActuatorDriver;
import org.roboticsapi.robot.RobotArmDriver;

/**
 * Interface for drivers that require a KR-specific driver, e.g. KRC-based
 * linear units.
 */
public interface KRDriver extends RobotArmDriver, CartesianActuatorDriver {
	/**
	 * Returns the driver's device name (required for identifying the counterpart at
	 * the RoboticsRuntime).
	 *
	 * @return the device name at the RoboticsRuntime.
	 */
	public String getDeviceName();

	/**
	 * Returns the driver's device type at the RoboticsRuntime. Can be
	 * <code>null</code>, if it is not necessary.
	 *
	 * @return the driver's device type at the RoboticsRuntime
	 */
	public String getDeviceType();
}
