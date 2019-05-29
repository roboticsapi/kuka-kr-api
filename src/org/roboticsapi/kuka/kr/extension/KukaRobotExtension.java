/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.kuka.kr.extension;

import org.roboticsapi.extension.AbstractRoboticsObjectBuilder;
import org.roboticsapi.kuka.kr.KR16_2;
import org.roboticsapi.kuka.kr.KR210R3100Ultra;
import org.roboticsapi.kuka.kr.KR270R2700Ultra;
import org.roboticsapi.kuka.kr.KR5scaraR350Z320;
import org.roboticsapi.kuka.kr.KR6R900Agilus;

public class KukaRobotExtension extends AbstractRoboticsObjectBuilder {

	public KukaRobotExtension() {
		super(KR210R3100Ultra.class, KR270R2700Ultra.class, KR5scaraR350Z320.class, KR6R900Agilus.class, KR16_2.class);
	}

}
