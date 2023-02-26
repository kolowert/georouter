package fun.kolowert.georouter.bean;

import fun.kolowert.georouter.serv.Serv;
import lombok.Getter;
import lombok.Setter;
import lombok.val;

public class SuperPoint implements Comparable<SuperPoint> {

    @Getter
    private final GeoPoint geoPoint;

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

    @Override
    public String toString() {
        return "::" + geoPoint.name + ":" + Serv.normDouble(distanceToPrevious, 0) + ":";
    }
}
