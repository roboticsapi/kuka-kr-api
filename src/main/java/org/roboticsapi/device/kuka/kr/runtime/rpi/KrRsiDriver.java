/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2013-2019 ISSE, University of Augsburg 
 */

package org.roboticsapi.device.kuka.kr.runtime.rpi;

import org.roboticsapi.communication.ethernet.runtime.rpi.driver.EthernetGenericDriver;
import org.roboticsapi.configuration.ConfigurationProperty;
import org.roboticsapi.core.Dependency;
import org.roboticsapi.core.Dependency.Builder;
import org.roboticsapi.facet.runtime.rpi.RpiParameters;
import org.roboticsapi.facet.runtime.rpi.mapping.RpiRuntime;

public final class KrRsiDriver extends KrGenericDriver {

	private final static String DEVICE_TYPE = "kuka_kr_arm_rsi";

	private final Dependency<EthernetGenericDriver> ethernetDriver;

	public KrRsiDriver() {
		ethernetDriver = createDependency("ethernetDriver");
	}
	
	@Override
	protected Builder<RpiRuntime> getRuntimeBuilder() {
		return () -> ethernetDriver.get().getRuntime();
	}

	public final EthernetGenericDriver getEthernetDriver() {
		return ethernetDriver.get();
	}

	@ConfigurationProperty(Optional = false)
	public final void setEthernetDriver(EthernetGenericDriver ethernetDriver) {
		this.ethernetDriver.set(ethernetDriver);
	}

	@Override
	public String getRpiDeviceType() {
		return DEVICE_TYPE;
	}

	@Override
	protected RpiParameters getRpiDeviceParameters() {
		return super.getRpiDeviceParameters().with("ethernet", ethernetDriver.get().getRpiDeviceName());
	}

}
