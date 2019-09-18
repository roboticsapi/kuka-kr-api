/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2013-2019 ISSE, University of Augsburg 
 */

package org.roboticsapi.device.kuka.kr;

import org.roboticsapi.core.world.Transformation;
import org.roboticsapi.framework.linearunit.AbstractLinearunit;
import org.roboticsapi.framework.linearunit.LinearunitSlider;
import org.roboticsapi.framework.multijoint.PrismaticJoint;

public abstract class Kl extends AbstractLinearunit {

	private final Transformation kukaCellOriginToLinearunitBase;

	public Kl(int sliderCount, Transformation kukaCellOriginToLinearunitBase) {
		this.kukaCellOriginToLinearunitBase = kukaCellOriginToLinearunitBase;
		setSliderCount(sliderCount);
	}

	protected abstract KlSlider createSlider(int index);

	protected abstract PrismaticJoint<?> createJoint(int index);

	@Override
	protected final PrismaticJoint<?> createLinearunitJoint(int index) {
		return createJoint(index);
	}

	@Override
	protected final LinearunitSlider createLinearunitSlider(int index) {
		KlSlider slider = createSlider(index);
		slider.setCellOriginToLinearunitBase(kukaCellOriginToLinearunitBase);
		return slider;
	}

	@Override
	protected final Transformation createLinearunitBaseToSliderBaseTransformation(int index) {
		return ((KlSlider) getSlider(index)).getLinearunitBaseToSliderBase();
	}

}
