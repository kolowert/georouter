package fun.kolowert.georouter.unit;

import fun.kolowert.georouter.bean.GeoPoint;
import fun.kolowert.georouter.common.Calculator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CalculatorTest {

    @ParameterizedTest
    @MethodSource("providePoints")
    void calculatedDistanceShouldDeviateLessThen5Percent(GeoPoint p1, GeoPoint p2, double distance) {
        Calculator calc = new Calculator(Calculator.DistanceMeasureUnit.KILOMETER);
        double calculatedDistance = calc.calculateDistanceOnGlobe(p1, p2);
        double deviation = Math.abs(calculatedDistance - distance);
        double deviationInPercent = 100.0 * deviation / distance;
        System.out.println("deviation:" + deviation + " deviationInPercent:" + deviationInPercent);
        assertTrue(deviationInPercent < 5.0);
    }

    private static Stream<Arguments> providePoints() {
        GeoPoint p1 = new GeoPoint("Amsterdam", 52.3, 4.9);
        GeoPoint p2 = new GeoPoint("Lviv", 49.77, 24.02);
        double distance12ObtainedByGoogleMap = 1507.0;

        GeoPoint p3 = new GeoPoint("NYC", 40.7, -74.0);
        GeoPoint p4 = new GeoPoint("LA", 34.1, -118.3);
        double distance34ObtainedByGoogleMap = 4469.0;

        return Stream.of(
                Arguments.of(p1, p2, distance12ObtainedByGoogleMap),
                Arguments.of(p3, p4, distance34ObtainedByGoogleMap)
        );
    }

}
