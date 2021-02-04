package tools;

import main.Main;

import java.awt.*;

/**
 * A class containing useful formulas
 */
public class Formulas {

	/**
	 * Gets the distance between two points
	 * @param x1 the first x coordinate
	 * @param x2 the second x coordinate
	 * @param y1 the first y coordinate
	 * @param y2 the second y coordinate
	 * @return the distance between the two points
	 */
	public static double distance(double x1, double x2, double y1, double y2) {
		return Math.pow(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2), .5);
	}

	/**
	 * Fixes an angle to make it always between 0 and 2 pi
	 * @param angle the angle to be fixed
	 * @return the fixed angle
	 */
	public static double fixAngle(double angle) {
		while (angle >= 2 * Math.PI) {
			angle -= 2 * Math.PI;
		}
		while (angle < 0) {
			angle += 2 * Math.PI;
		}
		return angle;
	}

	/**
	 * Determines the angle between two given angles
	 * @param angle the first angle
	 * @param other the second angle
	 * @return the angle between the given angles
	 */
	public static double angleBetween(double angle, double other) {
		if (angle < 0 || other < 0) {
			System.out.println("wtf");
		}
		if (angle > other) {
			return Math.min(angle - other, 2 * Math.PI - (angle - other));
		} else {
			return Math.min(other - angle, 2 * Math.PI - (other - angle));
		}
	}

	/**
	 * Rotates a given Polygon by a given angle
	 * @param originalPolygon the Polygon to be rotated
	 * @param rotationX the x coordinate of the rotation point
	 * @param rotationY the y coordinate of the rotation point
	 * @param rotateAngle the angle to be rotated by
	 * @return the rotated Polygon
	 */
	public static Polygon rotatePolygon(Polygon originalPolygon, double rotationX, double rotationY, double rotateAngle) {

		//The polygon to be returned
		Polygon polygon = new Polygon();

		int numberOfPoints = originalPolygon.npoints;

		//for each point in the original polygon
		for (int i = 0; i < numberOfPoints; i++) {

			//set px and py to the polygon point translated to the origin
			double px = originalPolygon.xpoints[i] - rotationX * Main.scale;
			double py = originalPolygon.ypoints[i] - rotationY * Main.scale;

			//get the distance from the origin to the translated point
			double distance = Formulas.distance(0, px, 0, py);

			//get the angle to the point
			double pointAngle = Math.atan2(py, px);

			//add the angle to be rotated by to the already existing angle
			pointAngle += Formulas.fixAngle(rotateAngle);

			//translate the new point back to the position of the track, and add it to the polygon
			polygon.addPoint((int) (Math.cos(pointAngle) * distance + rotationX * Main.scale), (int) (Math.sin(pointAngle) * distance + rotationY * Main.scale));
		}

		return polygon;
	}
	
}
