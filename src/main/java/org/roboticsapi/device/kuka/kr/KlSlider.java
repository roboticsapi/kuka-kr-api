/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2013-2019 ISSE, University of Augsburg 
 */

package org.roboticsapi.device.kuka.kr;

import org.roboticsapi.core.Dependency;
import org.roboticsapi.core.world.Rotation;
import org.roboticsapi.core.world.Transformation;
import org.roboticsapi.core.world.Vector;
import org.roboticsapi.framework.linearunit.LinearunitSlider;

public class KlSlider extends LinearunitSlider {

	// to be configured
	private final Dependency<Transformation> kukaCellOriginToBasePoint;
	private final Dependency<Transformation> kukaBasePointToAxis1;
	private final Dependency<Transformation> kukaAxis1ToFlange;

	// automatically determined
	private final Dependency<Transformation> cellOriginToLinearunitBase;
	private final Dependency<Transformation> linearunitBaseToSliderBase;
	private final Dependency<Vector> movementDirection;

	public KlSlider() {
		// to be configured
		kukaCellOriginToBasePoint = createDependency("kukaCellOriginToBasePoint");
		kukaBasePointToAxis1 = createDependency("kukaBasePointToAxis1");
		kukaAxis1ToFlange = createDependency("kukaAxis1ToFlange");

		// automatically determined
		cellOriginToLinearunitBase = createDependency("cellOriginToLinearunitBase");
		linearunitBaseToSliderBase = createDependency("linearunitBaseToSliderBase");
		movementDirection = createDependency("movementDirection");
	}

	/**
	 * Creates a new LinearunitSlider corresponding to kuka teaching values.
	 *
	 * @param kukaCellOriginToBasePoint
	 *            teaching cell parameter "Fusspunkt"
	 * @param kukaBasePointToAxis1
	 *            machine transformation parameter "Frame zwischen der Achse 1
	 *            und dem Fusspunkt"
	 * @param kukaAxis1ToFlange
	 *            machine transformation parameter "Frame zwischen Flansch und
	 *            Achse 1"
	 */
	public KlSlider(Transformation kukaCellOriginToBasePoint, Transformation kukaBasePointToAxis1,
			Transformation kukaAxis1ToFlange) {
		this();
		setKukaCellOriginToBasePoint(kukaCellOriginToBasePoint);
		setKukaBasePointToAxis1(kukaBasePointToAxis1);
		setKukaAxis1ToFlange(kukaAxis1ToFlange);
		updateDependendValues();
	}

	public final void setCellOriginToLinearunitBase(Transformation cellOriginToLinearunitBase) {
		this.cellOriginToLinearunitBase.set(cellOriginToLinearunitBase);
		updateDependendValues();
	}

	public final Transformation getCellOriginToLinearunitBase() {
		return cellOriginToLinearunitBase.get();
	}

	public final void setKukaCellOriginToBasePoint(Transformation cellOriginToBasePoint) {
		this.kukaCellOriginToBasePoint.set(cellOriginToBasePoint);
		updateDependendValues();
	}

	public final void setKukaBasePointToAxis1(Transformation basePointToAxis1) {
		this.kukaBasePointToAxis1.set(basePointToAxis1);
		updateDependendValues();
	}

	public final void setKukaAxis1ToFlange(Transformation axis1ToFlange) {
		this.kukaAxis1ToFlange.set(axis1ToFlange);
		updateDependendValues();
	}

	public final Transformation getKukaCellOriginToBasePoint() {
		return kukaCellOriginToBasePoint.get();
	}

	public final Transformation getKukaBasePointToAxis1() {
		return kukaBasePointToAxis1.get();
	}

	public final Transformation getKukaAxis1ToFlange() {
		return kukaAxis1ToFlange.get();
	}

	private final void updateDependendValues() {
		Transformation linearunitBaseToSliderBase = null;
		Vector movementDirection = null;
		Transformation baseMountFrameOffset = null;

		if (cellOriginToLinearunitBase.get() != null && kukaCellOriginToBasePoint.get() != null
				&& kukaBasePointToAxis1.get() != null) {
			Transformation baseToAxis1 = cellOriginToLinearunitBase.get().invert()
					.multiply(kukaCellOriginToBasePoint.get()).multiply(kukaBasePointToAxis1.get());
			movementDirection = baseToAxis1.getRotation().getZ();

			if (kukaAxis1ToFlange.get() != null) {
				linearunitBaseToSliderBase = baseToAxis1
						.multiply(new Transformation(kukaAxis1ToFlange.get().getRotation(), new Vector()));
				baseMountFrameOffset = new Transformation(Rotation.IDENTITY, kukaAxis1ToFlange.get().getTranslation());
			}
		}

		this.linearunitBaseToSliderBase.set(linearunitBaseToSliderBase);
		this.movementDirection.set(movementDirection);
		setBaseMountFrameOffset(baseMountFrameOffset);
	}

	public final Transformation getLinearunitBaseToSliderBase() {
		return linearunitBaseToSliderBase.get();
	}

	public final Vector getMovementDirection() {
		return movementDirection.get();
	}

}
