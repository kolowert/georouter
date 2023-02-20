package fun.kolowert.georouter.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GeoPoint {
    String name;
    double latitude;
    double longitude;
    double elevation = 0.0;

    public GeoPoint(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
