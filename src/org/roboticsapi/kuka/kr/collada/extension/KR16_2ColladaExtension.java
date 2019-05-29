/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.kuka.kr.collada.extension;

import java.net.URL;

import org.roboticsapi.core.RoboticsObject;
import org.roboticsapi.extension.PluginableExtension;
import org.roboticsapi.extension.RoboticsObjectListener;
import org.roboticsapi.kuka.kr.AbstractKR;
import org.roboticsapi.kuka.kr.KR16_2;
import org.roboticsapi.multijoint.link.Link;
import org.roboticsapi.visualization.property.Visualisation;
import org.roboticsapi.visualization.property.VisualizationProperty;
import org.roboticsapi.world.Transformation;

public class KR16_2ColladaExtension implements RoboticsObjectListener, PluginableExtension {

	private final static boolean use_hires_models = false;

	private static final Visualisation[] KR16_2_MODELS = new Visualisation[] {
			new Visualisation("COLLADA", new Transformation(0, 0, 0, 0, 0, 0), getResource("KR16_2-Link0.dae")),
			new Visualisation("COLLADA", new Transformation(0, 0, 0, 0, 0, 0), getResource("KR16_2-Link1.dae")),
			new Visualisation("COLLADA", new Transformation(0, 0, 0, 0, 0, 0), getResource("KR16_2-Link2.dae")),
			new Visualisation("COLLADA", new Transformation(0, 0, 0, 0, 0, 0), getResource("KR16_2-Link3.dae")),
			new Visualisation("COLLADA", new Transformation(0, 0, 0, 0, 0, 0), getResource("KR16_2-Link4.dae")),
			new Visualisation("COLLADA", new Transformation(0, 0, 0, 0, 0, 0), getResource("KR16_2-Link5.dae")),
			new Visualisation("COLLADA", new Transformation(0, 0, 0, 0, 0, 0), getResource("KR16_2-Link6.dae")) };

	@Override
	public void onAvailable(RoboticsObject object) {
		if (object instanceof AbstractKR) {

			final int joint_Count = 7;

			AbstractKR robot = (AbstractKR) object;
			Visualisation[] models = getModels(robot);

			if (models != null) {
				for (int i = 0; i < joint_Count; i++) {
					Link link = robot.getLink(i);

					if (link == null) {
						continue;
					}
					link.addProperty(new VisualizationProperty(models[i]));
				}
			}
		}
	}

	@Override
	public void onUnavailable(RoboticsObject object) {
		// do nothing...
	}

	private static final Visualisation[] getModels(AbstractKR robot) {
		if (robot instanceof KR16_2) {
			return KR16_2_MODELS;
		}
		return null;
	}

	private static final URL getResource(String resource) {
		String path = "models/";
		if (use_hires_models)
			path += "HiRes/";
		return KR16_2ColladaExtension.class.getClassLoader().getResource(path + resource);
	}

	@Override
	public void activate() {
		// TODO Auto-generated method stub

	}

	@Override
	public void deactivate() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getName() {
		return "KUKA KR16-2 Collada visualization model";
	}

	@Override
	public String getDescription() {
		return "Collada model of KUKA KR16-2 to display it in Robotics API visualization";
	}

}
