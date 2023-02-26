package fun.kolowert.georouter.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GeoPoint {
    String name;
    private double latitude;
    private double longitude;
    private double elevation = 0.0;

    public GeoPoint(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return ":" + name + ":";
    }
}
