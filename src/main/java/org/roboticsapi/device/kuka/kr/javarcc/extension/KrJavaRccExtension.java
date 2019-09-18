/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2013-2019 ISSE, University of Augsburg 
 */

package org.roboticsapi.device.kuka.kr.javarcc.extension;

import org.roboticsapi.device.kuka.kr.javarcc.devices.JMockKr;
import org.roboticsapi.facet.javarcc.extension.JavaRccExtension;
import org.roboticsapi.facet.javarcc.extension.JavaRccExtensionPoint;
import org.roboticsapi.facet.runtime.rpi.RpiParameters;
import org.roboticsapi.facet.runtime.rpi.core.types.RPIdoubleArray;

public class KrJavaRccExtension extends JavaRccExtension {

	@Override
	public void extend(JavaRccExtensionPoint ep) {
		ep.registerDevice("kuka_kr_arm_sim",
				(p, d) -> new JMockKr(param(p, "dh_d"), param(p, "dh_t"), param(p, "dh_a"), param(p, "dh_al"),
						param(p, "min_joint"), param(p, "max_joint"), param(p, "max_vel"), param(p, "max_acc")));
	}

	private double[] param(RpiParameters p, String name) {
		RPIdoubleArray val = p.get(RPIdoubleArray.class, name);
		double[] ret = new double[val.getSize()];
		for (int i = 0; i < ret.length; i++)
			ret[i] = val.get(i).get();
		return ret;
	}
}
