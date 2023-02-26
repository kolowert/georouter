package fun.kolowert.georouter.common;

import fun.kolowert.georouter.bean.GeoPoint;
import lombok.Getter;
import lombok.Setter;
import lombok.val;

import java.util.List;

public class Calculator {

    private final double EARTH_RADIUS_METER = 6_371_300.0;
    private final double ADJUSTMENT_COEFICIENT = 1.145;

    @Setter
    @Getter
    private DistanceMeasureUnit measureUnit;

    public enum DistanceMeasureUnit {
        KILOMETER (1000.0),
        STATUTE_MILE (1609.344),
        NAUTICAL_MILE (1852.0);

        @Getter
        private final double unitInMeter;
        DistanceMeasureUnit(double inMeter) {
            unitInMeter = inMeter;
        }
    }

    public Calculator() {
        this.measureUnit = DistanceMeasureUnit.KILOMETER;
    }

    public Calculator(DistanceMeasureUnit measureUnit) {
        this.measureUnit = measureUnit;
    }

    public double calculateDistanceOnGlobe(GeoPoint p1, GeoPoint p2) {

        val earthRadius = EARTH_RADIUS_METER / measureUnit.unitInMeter;

        val meridianOffset_grad = Math.abs(p1.getLatitude() - p2.getLatitude());
        val elevationOffset_grad = p1.getElevation() + p2.getElevation();
        val mainRadius = earthRadius + 0.001 * 0.5 * elevationOffset_grad;
        val meridianLength = 2.0 * Math.PI * mainRadius;
        val meridianOffset = meridianOffset_grad * meridianLength / 360.0;

        val parallelOffset_grad = Math.abs(p1.getLongitude() - p2.getLongitude());
        val parallel1Radius = Math.cos((p1.getLatitude() * Math.PI / 180.0)) * mainRadius;
        val parallel1Length = 2.0 * Math.PI * parallel1Radius;
        val parallel1Segment = parallelOffset_grad * parallel1Length / 360.0;
        val parallel2Radius = Math.cos((p2.getLatitude() * Math.PI / 180.0)) * mainRadius;
        val parallel2Length = 2.0 * Math.PI * parallel2Radius;
        val parallel2Segment = parallelOffset_grad * parallel2Length / 360.0;
        val parallelSegmentAverageLength = 0.5 * (parallel1Segment + parallel2Segment);

        val hypotenuse = Math.sqrt(Math.pow(meridianOffset, 2.0) + Math.pow(parallelSegmentAverageLength, 2.0));

        return ADJUSTMENT_COEFICIENT * hypotenuse;
    }

    public double calculateDistanceOnGlobe(List<GeoPoint> points) {
        double fullDistance = 0.0;
        for (int i = 1; i < points.size(); i++) {
            fullDistance += calculateDistanceOnGlobe(points.get(i-1), points.get(i));
        }
        return fullDistance;
    }
}
