/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2013-2019 ISSE, University of Augsburg 
 */

package org.roboticsapi.device.kuka.kr.runtime.rpi.extension;

import org.roboticsapi.device.kuka.kr.runtime.rpi.KrGenericDriver;
import org.roboticsapi.device.kuka.kr.runtime.rpi.KrMockDriver;
import org.roboticsapi.device.kuka.kr.runtime.rpi.KrRsiDriver;
import org.roboticsapi.facet.runtime.rpi.extension.RpiExtension;
import org.roboticsapi.facet.runtime.rpi.mapping.MapperRegistry;

public class KrRuntimeExtension extends RpiExtension {

	public KrRuntimeExtension() {
		super(KrMockDriver.class, KrRsiDriver.class, KrGenericDriver.class);
	}

	@Override
	protected void registerMappers(MapperRegistry mr) {
		// do nothing...
	}

	@Override
	protected void unregisterMappers(MapperRegistry mr) {
		// do nothing...
	}

}
