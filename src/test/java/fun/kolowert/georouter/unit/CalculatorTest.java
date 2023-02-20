package fun.kolowert.georouter.unit;

import fun.kolowert.georouter.bean.GeoPoint;
import fun.kolowert.georouter.common.Calculator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CalculatorTest {

    @Test
    void calculatedDistanceShouldDeviateLessThen5Percent() {
        GeoPoint p1 = new GeoPoint("Amsterdam", 52.3, 4.9);
        GeoPoint p2 = new GeoPoint("Lviv", 49.77, 24.02);
        Calculator calc = new Calculator(Calculator.DistanceMeasureUnit.KILOMETER);
        double calculatedDistance = calc.calculateDistanceOnGlobe(p1, p2);
        double distanceObtainedByGoogleMap = 1507.0;
        double deviation = Math.abs(calculatedDistance - distanceObtainedByGoogleMap);
        double deviationInPercent = 100.0 * deviation / distanceObtainedByGoogleMap;
        System.out.println("deviation:" + deviation + " deviationInPercent:" + deviationInPercent);
        assertTrue(deviationInPercent < 5.0);
    }
}
