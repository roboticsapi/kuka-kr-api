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
import org.roboticsapi.kuka.kr.KR210R3100Ultra;
import org.roboticsapi.kuka.kr.KR270R2700Ultra;
import org.roboticsapi.kuka.kr.KR6R900Agilus;
import org.roboticsapi.multijoint.link.Link;
import org.roboticsapi.visualization.property.Visualisation;
import org.roboticsapi.visualization.property.VisualizationProperty;
import org.roboticsapi.world.Transformation;

public class KR210R3100ColladaExtension implements RoboticsObjectListener, PluginableExtension {

	private static final Visualisation[] KR210R3100_MODELS = new Visualisation[] {
			new Visualisation("COLLADA", new Transformation(0, 0, 0, 0, 0, 0), getResource("KR210R3100_Link0.dae")),
			new Visualisation("COLLADA", new Transformation(0, 0, 0, 0, 0, 0), getResource("KR210R3100_Link1.dae")),
			new Visualisation("COLLADA", new Transformation(0, 0, 0, 0, 0, 0), getResource("KR210R3100_Link2.dae")),
			new Visualisation("COLLADA", new Transformation(0, 0, 0, 0, 0, 0), getResource("KR210R3100_Link3.dae")),
			new Visualisation("COLLADA", new Transformation(0, 0, 0, 0, 0, 0), getResource("KR210R3100_Link4.dae")),
			new Visualisation("COLLADA", new Transformation(0, 0, 0, 0, 0, 0), getResource("KR210R3100_Link5.dae")),
			new Visualisation("COLLADA", new Transformation(0, 0, 0, 0, 0, 0), getResource("KR210R3100_Link6.dae")) };

	private static final Visualisation[] KR270R2700_MODELS = new Visualisation[] {
			new Visualisation("COLLADA", new Transformation(0, 0, 0, 0, 0, 0), getResource("KR270R2700_Link0.dae")),
			new Visualisation("COLLADA", new Transformation(0, 0, 0, 0, 0, 0), getResource("KR270R2700_Link1.dae")),
			new Visualisation("COLLADA", new Transformation(0, 0, 0, 0, 0, 0), getResource("KR270R2700_Link2.dae")),
			new Visualisation("COLLADA", new Transformation(0, 0, 0, 0, 0, 0), getResource("KR270R2700_Link3.dae")),
			new Visualisation("COLLADA", new Transformation(0, 0, 0, 0, 0, 0), getResource("KR270R2700_Link4.dae")),
			new Visualisation("COLLADA", new Transformation(0, 0, 0, 0, 0, 0), getResource("KR270R2700_Link5.dae")),
			new Visualisation("COLLADA", new Transformation(0, 0, 0, 0, 0, 0), getResource("KR270R2700_Link6.dae")) };

	private static final Visualisation[] KR6R900_MODELS = new Visualisation[] {
			new Visualisation("COLLADA", new Transformation(0, 0, 0, 0, 0, 0), getResource("KR6R900_Link0.dae")),
			new Visualisation("COLLADA", new Transformation(0, 0, 0, 0, 0, 0), getResource("KR6R900_Link1.dae")),
			new Visualisation("COLLADA", new Transformation(0, 0, 0, 0, 0, 0), getResource("KR6R900_Link2.dae")),
			new Visualisation("COLLADA", new Transformation(0, 0, 0, 0, 0, 0), getResource("KR6R900_Link3.dae")),
			new Visualisation("COLLADA", new Transformation(0, 0, 0, 0, 0, 0), getResource("KR6R900_Link4.dae")),
			new Visualisation("COLLADA", new Transformation(0, 0, 0, 0, 0, 0), getResource("KR6R900_Link5.dae")),
			new Visualisation("COLLADA", new Transformation(0, 0, 0, 0, 0, 0), getResource("KR6R900_Link6.dae")) };

	@Override
	public void onAvailable(RoboticsObject object) {
		if (object instanceof AbstractKR) {
			AbstractKR robot = (AbstractKR) object;
			Visualisation[] models = getModels(robot);

			if (models != null) {
				for (int i = 0; i < 7; i++) {
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
		if (robot instanceof KR6R900Agilus) {
			return KR6R900_MODELS;
		}
		if (robot instanceof KR210R3100Ultra) {
			return KR210R3100_MODELS;
		}
		if (robot instanceof KR270R2700Ultra) {
			return KR270R2700_MODELS;
		}
		return null;
	}

	private static final URL getResource(String resource) {
		return KR210R3100ColladaExtension.class.getClassLoader().getResource("models/" + resource);
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
		return "KUKA KR210 Collada visualization model";
	}

	@Override
	public String getDescription() {
		return "Collada model of KUKA KR210 to display it in Robotics API visualization";
	}

}
