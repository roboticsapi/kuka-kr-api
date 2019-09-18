/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2013-2019 ISSE, University of Augsburg 
 */

package org.roboticsapi.device.kuka.kr.systemtest;

import org.junit.runner.RunWith;
import org.roboticsapi.device.kuka.kr.Kr16_2;
import org.roboticsapi.device.kuka.kr.Kr210R3100Ultra;
import org.roboticsapi.device.kuka.kr.runtime.rpi.KrMockDriver;
import org.roboticsapi.framework.multijoint.activity.JointPtpInterfaceTests;
import org.roboticsapi.systemtest.RoboticsTestSuite;
import org.roboticsapi.systemtest.RoboticsTestSuite.DeviceInterfaceTests;
import org.roboticsapi.systemtest.WithDevice;
import org.roboticsapi.systemtest.WithRcc;
import org.roboticsapi.systemtest.WithRcc.Rcc;

@RunWith(RoboticsTestSuite.class)
@DeviceInterfaceTests({ JointPtpInterfaceTests.class })
@WithDevice(device = Kr16_2.class, deviceDrivers = { KrMockDriver.class })
@WithDevice(device = Kr210R3100Ultra.class, deviceDrivers = { KrMockDriver.class })
@WithRcc(Rcc.DedicatedJavaRcc)
public class KrSystemTest {

}
