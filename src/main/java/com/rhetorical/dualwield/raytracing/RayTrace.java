package com.rhetorical.dualwield.raytracing;

/* Credit goes to CJP10 on the Spigot forums for the original code I built off of. */

import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class RayTrace {

	private Vector origin, direction;

	public RayTrace(Vector origin, Vector direction) {
		this.origin = origin;
		this.direction = direction;
	}

	//Gets a point on the ray with the given distance.
	private Vector getPostion(double distance) {
		return origin.clone().add(direction.clone().multiply(distance));
	}

	//Traverse the positions along the ray's path
	private ArrayList<Vector> traverse(double distance, double accuracy) {
		ArrayList<Vector> positions = new ArrayList<>();
		for (double d = 0; d <= distance; d += accuracy) {
			positions.add(getPostion(d));
		}
		return positions;
	}

	//Checks for an intersection within the bounding box with the given distance and accuracy
	public boolean intersects(BoundingBox boundingBox, double distance, double accuracy) {
		ArrayList<Vector> positions = traverse(distance, accuracy);
		for (Vector position : positions) {
			if (intersects(position, boundingBox.getMin(), boundingBox.getMax())) {
				return true;
			}
		}
		return false;
	}


	//Checks for intersection within bounds
	private static boolean intersects(Vector position, Vector min, Vector max) {
		if (position.getX() < min.getX() || position.getX() > max.getX()) {
			return false;
		} else if (position.getY() < min.getY() || position.getY() > max.getY()) {
			return false;
		} else if (position.getZ() < min.getZ() || position.getZ() > max.getZ()) {
			return false;
		}
		return true;
	}

}