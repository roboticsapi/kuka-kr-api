/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.kuka.kr.runtime;

import java.util.Map;

import org.roboticsapi.configuration.ConfigurationProperty;
import org.roboticsapi.core.exception.ConfigurationException;
import org.roboticsapi.kuka.kr.AbstractKR;
import org.roboticsapi.runtime.fieldbus.ethernet.AbstractEthernetDriver;
import org.roboticsapi.runtime.robot.driver.SoftRobotDHRobotArmDriver;

public final class RSIKRDriver extends SoftRobotDHRobotArmDriver<AbstractKR> implements KRDriver {

	private final static String DEVICE_TYPE = "kuka_kr_arm_rsi";

	private AbstractEthernetDriver ethernetDriver = null;

	public RSIKRDriver() {
		super();
	}

	public final AbstractEthernetDriver getEthernetDriver() {
		return ethernetDriver;
	}

	@ConfigurationProperty(Optional = false)
	public final void setEthernetDriver(AbstractEthernetDriver ethernetDriver) {
		immutableWhenInitialized();
		this.ethernetDriver = ethernetDriver;
	}

	@Override
	public String getDeviceType() {
		return DEVICE_TYPE;
	}

	@Override
	protected void validateConfigurationProperties() throws ConfigurationException {
		super.validateConfigurationProperties();

		checkNotNullAndInitialized("ethernetDriver", ethernetDriver);
	}

	@Override
	protected void collectDriverSpecificParameters(Map<String, String> parameters) {
		// Can probably be moved to KRDriver or even multijoint?

		StringBuilder max_vel = new StringBuilder("{");
		StringBuilder max_acc = new StringBuilder("{");

		for (int i = 0; i < getJointCount(); i++) {
			max_vel.append(getJoint(i).getMaximumVelocity());
			max_acc.append(getJoint(i).getMaximumAcceleration());

			if (i != getJointCount() - 1) {
				max_vel.append(",");
				max_acc.append(",");
			} else {
				max_vel.append("}");
				max_acc.append("}");
			}
		}
		parameters.put("max_vel", max_vel.toString());
		parameters.put("max_acc", max_acc.toString());

		parameters.put("ethernet", ethernetDriver.getDeviceName());
	}

}
