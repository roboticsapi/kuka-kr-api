/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2013-2019 ISSE, University of Augsburg 
 */

package org.roboticsapi.device.kuka.kr;

import java.util.Arrays;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.roboticsapi.core.RoboticsContextImpl;
import org.roboticsapi.core.exception.RoboticsException;
import org.roboticsapi.device.kuka.kr.Kr210R3100Ultra;
import org.roboticsapi.device.kuka.kr.Kr270R2700Ultra;
import org.roboticsapi.device.kuka.kr.StatusTurn;

public class KRStatusTurnTest {

	private Kr210R3100Ultra kr210;
	private Kr270R2700Ultra kr270;
	private RoboticsContextImpl ctx;

	@Before
	public void setup() throws RoboticsException {

		kr210 = new Kr210R3100Ultra();
		kr270 = new Kr270R2700Ultra();
		ctx = new RoboticsContextImpl("test");
		ctx.initialize(kr210);
		ctx.initialize(kr270);

	}

	@After
	public void teardown() {
		ctx.destroy();
		ctx = null;
		kr210 = null;
		kr270 = null;
	}

	@Test
	public void testKR210R3100UltraHomeAndAllNegativeTurn() throws RoboticsException {

		double[] home = new double[] { 0, Math.toRadians(-90), Math.toRadians(90), 0, 0, 0 };
		StatusTurn expectedHome = new StatusTurn(false, false, false,
				new boolean[] { false, true, false, false, false, false });

		Assert.assertEquals(expectedHome, kr210.getStatusTurnFor(home));

		double[] allNegative = new double[] { Math.toRadians(-4.87), Math.toRadians(-130.14), Math.toRadians(-1.1),
				Math.toRadians(-5.60), Math.toRadians(-8.11), Math.toRadians(-14.14) };
		boolean[] expectedNegativeTurn = new boolean[6];
		Arrays.fill(expectedNegativeTurn, true);

		StatusTurn expectedAllNegative = new StatusTurn(true, false, true, expectedNegativeTurn);
		StatusTurn calculatedResult = kr210.getStatusTurnFor(allNegative);
		Assert.assertEquals(expectedAllNegative, calculatedResult);

		// Test KUKA bit representation
		Assert.assertEquals(63, calculatedResult.getKukaTurn());
		Assert.assertEquals(7, calculatedResult.getKukaStatus());
	}

	@Test
	public void testKR210R3100UltraStatusOverheadFlip() throws RoboticsException {

		double[] overheadTrue = new double[] { 0, Math.toRadians(-99.89), Math.toRadians(3.37), 0,
				Math.toRadians(-1.96), 0 };
		StatusTurn expectedOverheadTrue = new StatusTurn(true, false, true,
				new boolean[] { false, true, false, false, true, false });

		Assert.assertEquals(expectedOverheadTrue, kr210.getStatusTurnFor(overheadTrue));

		double[] overheadFalse = new double[] { 0, Math.toRadians(-99.88), Math.toRadians(3.37), 0,
				Math.toRadians(-1.96), 0 };
		StatusTurn expectedOverheadFalse = new StatusTurn(false, false, true,
				new boolean[] { false, true, false, false, true, false });

		Assert.assertEquals(expectedOverheadFalse, kr210.getStatusTurnFor(overheadFalse));
	}

	@Test
	public void testKR210R3100UltraStatusA5Flip() throws RoboticsException {

		double[] a5True = new double[] { 0, Math.toRadians(-99.89), Math.toRadians(3.37), 0, Math.toRadians(-1.96), 0 };
		StatusTurn expectedA5True = new StatusTurn(true, false, true,
				new boolean[] { false, true, false, false, true, false });

		Assert.assertEquals(expectedA5True, kr210.getStatusTurnFor(a5True));

		double[] a5False = new double[] { 0, Math.toRadians(-99.89), Math.toRadians(3.37), 0, Math.toRadians(0.1), 0 };
		StatusTurn expectedA5StatusAndA5TurnFalse = new StatusTurn(true, false, false,
				new boolean[] { false, true, false, false, false, false });

		Assert.assertEquals(expectedA5StatusAndA5TurnFalse, kr210.getStatusTurnFor(a5False));
	}

	@Test
	public void testKR210R3100UltraStatusA3Flip() throws RoboticsException {

		double[] a3True = new double[] { Math.toRadians(1.02), Math.toRadians(-137.8), Math.toRadians(-1.69),
				Math.toRadians(1.09), Math.toRadians(13.71), Math.toRadians(-35.9) };
		StatusTurn expectedA3true = new StatusTurn(true, true, false,
				new boolean[] { false, true, true, false, false, true });

		StatusTurn calculatedRes = kr210.getStatusTurnFor(a3True);
		Assert.assertEquals(expectedA3true, calculatedRes);

		Assert.assertEquals(1, calculatedRes.getKukaStatus());
		Assert.assertEquals(38, calculatedRes.getKukaTurn());

		double[] a3False = new double[] { Math.toRadians(1.02), Math.toRadians(-137.8), Math.toRadians(-1.67),
				Math.toRadians(1.09), Math.toRadians(13.71), Math.toRadians(-35.9) };
		StatusTurn expectedA3False = new StatusTurn(true, false, false,
				new boolean[] { false, true, true, false, false, true });

		StatusTurn calculatedResult = kr210.getStatusTurnFor(a3False);
		Assert.assertEquals(expectedA3False, calculatedResult);

		Assert.assertEquals(3, calculatedResult.getKukaStatus());
		Assert.assertEquals(38, calculatedResult.getKukaTurn());
	}

	@Test
	public void testKR270R2700UltraHomeAndAllNegativeTurn() throws RoboticsException {

		double[] home = new double[] { 0, Math.toRadians(-90), Math.toRadians(90), 0, 0, 0 };
		StatusTurn expectedHome = new StatusTurn(false, false, false,
				new boolean[] { false, true, false, false, false, false });

		Assert.assertEquals(expectedHome, kr270.getStatusTurnFor(home));

		double[] allNegative = new double[] { Math.toRadians(-4.87), Math.toRadians(-130.14), Math.toRadians(-1.1),
				Math.toRadians(-5.60), Math.toRadians(-8.11), Math.toRadians(-14.14) };
		boolean[] expectedNegativeTurn = new boolean[6];
		Arrays.fill(expectedNegativeTurn, true);

		StatusTurn expectedAllNegative = new StatusTurn(true, false, true, expectedNegativeTurn);
		StatusTurn calculatedResult = kr270.getStatusTurnFor(allNegative);
		Assert.assertEquals(expectedAllNegative, calculatedResult);

		// Test KUKA bit representation
		Assert.assertEquals(63, calculatedResult.getKukaTurn());
		Assert.assertEquals(7, calculatedResult.getKukaStatus());
	}

	@Test
	public void testKR270R2700UltraStatusOverheadFlip() throws RoboticsException {

		double[] overheadTrue = new double[] { 0, Math.toRadians(-101.30), Math.toRadians(3.37), 0,
				Math.toRadians(-1.96), 0 };
		StatusTurn expectedOverheadTrue = new StatusTurn(true, false, true,
				new boolean[] { false, true, false, false, true, false });

		Assert.assertEquals(expectedOverheadTrue, kr270.getStatusTurnFor(overheadTrue));

		double[] overheadFalse = new double[] { 0, Math.toRadians(-101.28), Math.toRadians(3.37), 0,
				Math.toRadians(-1.96), 0 };
		StatusTurn expectedOverheadFalse = new StatusTurn(false, false, true,
				new boolean[] { false, true, false, false, true, false });

		Assert.assertEquals(expectedOverheadFalse, kr270.getStatusTurnFor(overheadFalse));
	}

	@Test
	public void testKR270R2700UltraStatusA5Flip() throws RoboticsException {

		double[] a5True = new double[] { 0, Math.toRadians(-101.3), Math.toRadians(3.37), 0, Math.toRadians(-1.96), 0 };
		StatusTurn expectedA5True = new StatusTurn(true, false, true,
				new boolean[] { false, true, false, false, true, false });

		Assert.assertEquals(expectedA5True, kr270.getStatusTurnFor(a5True));

		double[] a5False = new double[] { 0, Math.toRadians(-101.3), Math.toRadians(3.37), 0, Math.toRadians(0.1), 0 };
		StatusTurn expectedA5StatusAndA5TurnFalse = new StatusTurn(true, false, false,
				new boolean[] { false, true, false, false, false, false });

		Assert.assertEquals(expectedA5StatusAndA5TurnFalse, kr270.getStatusTurnFor(a5False));
	}

	@Test
	public void testKR270R2700UltraStatusA3Flip() throws RoboticsException {

		double[] a3True = new double[] { Math.toRadians(1.02), Math.toRadians(-137.8), Math.toRadians(-1.96),
				Math.toRadians(1.09), Math.toRadians(13.71), Math.toRadians(-35.9) };
		StatusTurn expectedA3true = new StatusTurn(true, true, false,
				new boolean[] { false, true, true, false, false, true });

		StatusTurn calculatedRes = kr270.getStatusTurnFor(a3True);
		Assert.assertEquals(expectedA3true, calculatedRes);

		Assert.assertEquals(1, calculatedRes.getKukaStatus());
		Assert.assertEquals(38, calculatedRes.getKukaTurn());

		double[] a3False = new double[] { Math.toRadians(1.02), Math.toRadians(-137.8), Math.toRadians(-1.95),
				Math.toRadians(1.09), Math.toRadians(13.71), Math.toRadians(-35.9) };
		StatusTurn expectedA3False = new StatusTurn(true, false, false,
				new boolean[] { false, true, true, false, false, true });

		StatusTurn calculatedResult = kr270.getStatusTurnFor(a3False);
		Assert.assertEquals(expectedA3False, calculatedResult);

		Assert.assertEquals(3, calculatedResult.getKukaStatus());
		Assert.assertEquals(38, calculatedResult.getKukaTurn());
	}

}
