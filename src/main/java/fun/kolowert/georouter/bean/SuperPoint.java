package fun.kolowert.georouter.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.val;

public class SuperPoint implements Comparable<SuperPoint> {

    @Getter
    private GeoPoint geoPoint;

    @Setter
    @Getter
    private double distanceToPrevious = 0.0;

    public SuperPoint(GeoPoint geoPoint) {
        this.geoPoint = geoPoint;
    }

    @Override
    public int compareTo(SuperPoint other) {
        val difference = this.distanceToPrevious - other.distanceToPrevious;
        if (difference > 0.0) {
            return 1;
        }
        if (difference < 0.0) {
            return -1;
        }
        return 0;
    }
}
