/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2013-2019 ISSE, University of Augsburg 
 */

package org.roboticsapi.device.kuka.kr;

import java.util.Arrays;

public class StatusTurn {

	private final boolean isOverhead;
	private final boolean statusA3;
	private final boolean statusA5;
	private final boolean turn[];

	public StatusTurn(boolean isOverhead, boolean negativeA3, boolean negativeA5, boolean[] turn) {
		super();
		this.isOverhead = isOverhead;
		this.statusA3 = negativeA3;
		this.statusA5 = negativeA5;
		this.turn = turn;
	}

	/*
	 * Returns an array containing values for each axis. False for negative
	 * range (< 0°), true for positive range and zero (>= 0°).
	 */
	public boolean[] getTurn() {
		return turn;
	}

	/*
	 * Returns true if the robot is in overhead area, false otherwise.
	 */
	public boolean isInOverheadPostion() {
		return isOverhead;
	}

	/*
	 * Returns the status of given Axis. True if negative (<0), false if
	 * positive or zero (>=0).
	 */
	public boolean isA3axisNegative() {
		return statusA3;
	}

	/*
	 * Returns the status of given Axis. True if negative (<0), false if
	 * positive or zero (>=0).
	 */
	public boolean isA5axisNegative() {
		return statusA5;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (isOverhead ? 1231 : 1237);
		result = prime * result + (statusA3 ? 1231 : 1237);
		result = prime * result + (statusA5 ? 1231 : 1237);
		result = prime * result + Arrays.hashCode(turn);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		StatusTurn other = (StatusTurn) obj;
		if (isOverhead != other.isOverhead) {
			return false;
		}
		if (statusA3 != other.statusA3) {
			return false;
		}
		if (statusA5 != other.statusA5) {
			return false;
		}
		if (!Arrays.equals(turn, other.turn)) {
			return false;
		}
		return true;
	}

	public int getKukaStatus() {
		return ((isInOverheadPostion() ? 1 : 0) + (!isA3axisNegative() ? 2 : 0) + (isA5axisNegative() ? 4 : 0));
	}

	public int getKukaTurn() {

		int turn = 0;

		for (int i = 0; i < getTurn().length; i++) {
			if (getTurn()[i]) {
				turn += 1 << i;
			}
		}

		return turn;
	}
}
