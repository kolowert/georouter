package fun.kolowert.georouter;

import fun.kolowert.georouter.bean.GeoPoint;
import fun.kolowert.georouter.common.Calculator;
import fun.kolowert.georouter.common.RouteMaker;
import fun.kolowert.georouter.serv.CsvHandler;
import fun.kolowert.georouter.serv.Serv;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * It plays to check algorithms
 */
public class BossTest {

    @ParameterizedTest
    @ValueSource(strings = {"test-points-04.csv", "test-points-09.csv", "test-points-76.csv"})
    void testRouteMakerFromCsv(String input) {
        Calculator calculator = new Calculator(Calculator.DistanceMeasureUnit.KILOMETER);
        RouteMaker routeMaker = new RouteMaker(calculator);
        routeMaker.setRandomChoiceDeepness(3);
        routeMaker.setRandomSectorCoefficient(0.6);

        String filePath = "src/test/resources/points/" + input;
        List<GeoPoint> points = CsvHandler.makeListOfGeoPointsFromCsvFile(filePath);

        List<GeoPoint> arrangedPoints = routeMaker.makeSingleRoute(points);
        double distance = calculator.calculateDistanceOnGlobe(arrangedPoints);
        System.out.print(input + " " + Serv.normDouble(distance, 2) + " km >> "); //|||||||||||||||
        arrangedPoints.forEach(e -> System.out.print(e.getName() + " > ")); //|||||||||||||||
        System.out.println(); //|||||||||||||||

        assertTrue(true);
    }

    @Test
    void testRouteMaker() {
        Calculator calculator = new Calculator(Calculator.DistanceMeasureUnit.KILOMETER);
        RouteMaker routeMaker = new RouteMaker(calculator);
        routeMaker.setRandomChoiceDeepness(3);
        routeMaker.setRandomSectorCoefficient(0.6);

        for (int i = 1; i <= 5; i++) {
            List<GeoPoint> arrangedPoints = routeMaker.makeSingleRoute(getTestPoints());
            double distance = calculator.calculateDistanceOnGlobe(arrangedPoints);
            System.out.print(i + " (" + Serv.normDouble(distance, 2) + ") >> "); //|||||||||||||||
            arrangedPoints.forEach(e -> System.out.print(e.getName() + " > ")); //|||||||||||||||
            System.out.println(); //|||||||||||||||
        }
        assertTrue(true);
    }

    private List<GeoPoint> getTestPoints() {
        List<GeoPoint> points = new ArrayList<>();
        points.add(new GeoPoint("Kansas City",39.1,-94.58));
        points.add(new GeoPoint("Dallas",32.78,-96.8));
        points.add(new GeoPoint("Houston",29.76,-95.36));
        points.add(new GeoPoint("Jackson",32.3,-90.18));
        points.add(new GeoPoint("Memphis",35.15,-90.05));
        points.add(new GeoPoint("New Orleans",29.97,-90.08));
        points.add(new GeoPoint("Oklahoma City",35.47,-97.51));
        points.add(new GeoPoint("St. Louis",38.63,-90.2));
        points.add(new GeoPoint("Wichita",37.69,-97.34));
        return points;
    }

}
