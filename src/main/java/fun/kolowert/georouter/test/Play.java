package fun.kolowert.georouter.test;

import fun.kolowert.georouter.bean.GeoPoint;
import fun.kolowert.georouter.serv.Serv;

import java.util.List;

public class Play {

    public static void main(String[] args) {

        csvHandlerPlay(true);

        randomSectorPlay(false);


    }

    private static void csvHandlerPlay(boolean doIt) {
        if (!doIt) return;
        String filePath = "src/main/resources/points/test-points-9.csv";
        List<GeoPoint> points = fun.kolowert.georouter.serv.CsvHandler.makeListOfGeoPointsFromCsvFile(filePath);

        System.out.println("points from file: " + filePath);
        for (int i = 0; i < points.size(); i++) {
            System.out.print(i + ") " + points.get(i) + "  ");
        }
    }

    private static void randomSectorPlay(boolean doIt) {
        if (!doIt) return;
        int[] sectorCounter = new int[12];
        int quantity = 100_000;

        for (int i = 0; i < quantity; i++) {
            int randomSector = Serv.randomSector(6, 0.6);
            ++sectorCounter[randomSector];
            //System.out.println(i + ") >>> Random sector " + randomSector);
        }

        System.out.println("Sector counted: ");
        for (int i = 0; i < sectorCounter.length; i++) {
            System.out.print("(" + i + ") " + Serv.normDouble(100.0 * sectorCounter[i] / quantity, 4) + ", ");
        }
    }

}
