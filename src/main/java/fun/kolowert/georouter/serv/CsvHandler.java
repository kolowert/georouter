package fun.kolowert.georouter.serv;

import fun.kolowert.georouter.bean.GeoPoint;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvHandler {


    private static final String DELIMITER = ",";

    public static List<GeoPoint> makeListOfGeoPointsFromCsvFile(String filePath) {
        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(DELIMITER);
                records.add(Arrays.asList(values));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<GeoPoint> geoPoints = new ArrayList<>();
        try {
            records.forEach(
                e -> geoPoints.add(new GeoPoint(e.get(0), Double.parseDouble(e.get(1)), Double.parseDouble(e.get(2))))
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return geoPoints;
    }
}
