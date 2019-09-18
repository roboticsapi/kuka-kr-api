/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2013-2019 ISSE, University of Augsburg 
 */

package org.roboticsapi.device.kuka.kr.extension;

import org.roboticsapi.device.kuka.kr.Kr16_2;
import org.roboticsapi.device.kuka.kr.Kr210R3100Ultra;
import org.roboticsapi.device.kuka.kr.Kr270R2700Ultra;
import org.roboticsapi.device.kuka.kr.Kr5ScaraR350Z320;
import org.roboticsapi.device.kuka.kr.Kr6R900Agilus;
import org.roboticsapi.extension.AbstractRoboticsObjectBuilder;

public class KrExtension extends AbstractRoboticsObjectBuilder {

	public KrExtension() {
		super(Kr210R3100Ultra.class, Kr270R2700Ultra.class,
				Kr5ScaraR350Z320.class, Kr6R900Agilus.class, Kr16_2.class);
	}

}
