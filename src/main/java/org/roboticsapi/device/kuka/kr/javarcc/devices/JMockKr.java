/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2013-2019 ISSE, University of Augsburg 
 */

package org.roboticsapi.device.kuka.kr.javarcc.devices;

import org.roboticsapi.facet.javarcc.devices.JDevice;
import org.roboticsapi.facet.runtime.rpi.world.types.RPIFrame;
import org.roboticsapi.framework.multijoint.javarcc.devices.JMockMultijointDevice;
import org.roboticsapi.framework.multijoint.javarcc.interfaces.JMultijointInterface;
import org.roboticsapi.framework.robot.javarcc.interfaces.JArmKinematicsInterface;

public class JMockKr extends JMockMultijointDevice implements JDevice, JArmKinematicsInterface, JMultijointInterface {
	private KrKin kin;

	public JMockKr(double[] d, double[] theta, double[] a, double[] alpha, double[] min, double[] max, double[] maxVel,
			double[] maxAcc) {
		super(6, min, max, new double[] { 0, -Math.PI / 2, Math.PI / 2, 0, 0, 0 }, maxVel, maxAcc);
		kin = new KrKin(d, theta, a, alpha, min, max);
	}

	@Override
	public RPIFrame kin(double[] joints, RPIFrame ret) {
		return kin.kin(joints, ret);
	}

	@Override
	public double[] invKin(double[] hintJoints, RPIFrame frame) {
		return kin.invKin(frame, hintJoints);
	}

}
