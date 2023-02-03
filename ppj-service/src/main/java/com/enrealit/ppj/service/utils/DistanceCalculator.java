package com.enrealit.ppj.service.utils;

import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import com.enrealit.ppj.shared.enums.DistanceUnit;

public class DistanceCalculator {

	private DistanceCalculator() {
	}

	public static double distance(GeoJsonPoint point1, GeoJsonPoint point2, DistanceUnit unit) {
		double lat1 = point1.getX();
		double lon1 = point1.getY();
		double lat2 = point2.getX();
		double lon2 = point2.getY();
		if ((lat1 == lat2) && (lon1 == lon2)) {
			return 0;
		} else {
			double theta = lon1 - lon2;
			double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2))
					+ Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
			dist = Math.acos(dist);
			dist = Math.toDegrees(dist);
			dist = dist * 60 * 1.1515;
			if (unit == DistanceUnit.KILOMETER) {
				dist = dist * 1.609344;
			} else if (unit == DistanceUnit.NAUTIC_MILE) {
				dist = dist * 0.8684;
			}
			return (dist);
		}
	}

}
