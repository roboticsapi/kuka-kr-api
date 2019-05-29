/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.kuka.kr.runtime.extension;

import java.util.HashMap;
import java.util.Map;

import org.roboticsapi.core.RoboticsObject;
import org.roboticsapi.kuka.kr.AbstractKR;
import org.roboticsapi.kuka.kr.runtime.RSIKRDriver;
import org.roboticsapi.robot.RobotArmDriver;
import org.roboticsapi.runtime.SoftRobotRuntime;
import org.roboticsapi.runtime.driver.DeviceBasedInstantiator;
import org.roboticsapi.runtime.extension.AbstractSoftRobotRoboticsBuilder;

public class SoftRobotKukaRobotRSIExtension extends AbstractSoftRobotRoboticsBuilder {

	private static final String EXTENSION_RSI = "kuka_kr_rsi";

	private final Map<RSIKRDriver, DeviceBasedInstantiator<AbstractKR>> map = new HashMap<RSIKRDriver, DeviceBasedInstantiator<AbstractKR>>();

	public SoftRobotKukaRobotRSIExtension() {
		super(RSIKRDriver.class);
	}

	@Override
	protected String[] getRuntimeExtensions() {
		return new String[] { EXTENSION_RSI };
	}

	@Override
	protected void onRoboticsObjectAvailable(RoboticsObject object) {
		if (object instanceof AbstractKR) {
			final AbstractKR device = (AbstractKR) object;
			RobotArmDriver d = device.getDriver();

			if (d instanceof RSIKRDriver) {
				final RSIKRDriver driver = (RSIKRDriver) d;
				final DeviceBasedInstantiator<AbstractKR> loader = new DeviceBasedInstantiator<AbstractKR>(device,
						driver);

				map.put(driver, loader);
				driver.addOperationStateListener(loader);
			}
		}
	}

	@Override
	protected void onRoboticsObjectUnavailable(RoboticsObject object) {
		if (object instanceof RSIKRDriver) {
			final RSIKRDriver driver = (RSIKRDriver) object;
			final DeviceBasedInstantiator<AbstractKR> loader = map.remove(driver);

			driver.removeOperationStateListener(loader);
		}
	}

	@Override
	protected void onRuntimeAvailable(SoftRobotRuntime runtime) {
		// do nothing...
	}

	@Override
	protected void onRuntimeUnavailable(SoftRobotRuntime runtime) {
		// do nothing...
	}

}
