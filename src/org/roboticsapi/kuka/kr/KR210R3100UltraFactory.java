/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.kuka.kr;

import org.roboticsapi.multijoint.Joint;
import org.roboticsapi.multijoint.link.Link;
import org.roboticsapi.robot.RobotArmDriver;
import org.roboticsapi.world.Frame;

public class KR210R3100UltraFactory extends KR210R3100Ultra {

	private KR210R3100UltraFactory() {
	}

	public static Joint createKR210R3100UltraJoint(int nr, String name, RobotArmDriver driver) {
		KR210R3100UltraFactory factory = new KR210R3100UltraFactory();
		return factory.createJoint(nr, name, driver.createJointDriver(nr));
	}

	public static Link createKR210R3100UltraJointLink(int nr, Joint from, Joint to) {
		KR210R3100UltraFactory factory = new KR210R3100UltraFactory();
		if (nr < 1 || nr > 5) {
			return null;
		}
		factory.setJoint(nr - 1, from);
		factory.setJoint(nr, to);
		return factory.createLink(nr);
	}

	public static Link createKR210R3100UltraFlangeLink(Joint from, Frame flange) {
		KR210R3100UltraFactory factory = new KR210R3100UltraFactory();

		factory.setJoint(5, from);
		factory.setFlange(flange);
		return factory.createLink(6);
	}
}
