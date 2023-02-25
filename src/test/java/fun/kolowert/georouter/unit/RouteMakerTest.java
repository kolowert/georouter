package fun.kolowert.georouter.unit;

import fun.kolowert.georouter.bean.GeoPoint;
import fun.kolowert.georouter.common.Calculator;
import fun.kolowert.georouter.common.RouteMaker;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RouteMakerTest {

    @ParameterizedTest
    @MethodSource("provideArguments")
    void shouldWork(List<GeoPoint> input) {
        Calculator calculator = new Calculator(Calculator.DistanceMeasureUnit.KILOMETER);
        RouteMaker routeMaker = new RouteMaker(calculator);
        var result = routeMaker.makeSingleRoute(input);
        assertNotNull(result);
        assertEquals(input.get(0).getName(), result.get(0).getName());
        assertEquals(input.size() + 1, result.size());
    }

    private static Stream<Arguments> provideArguments() {
        List<GeoPoint> geoPoints = new ArrayList<>();

        geoPoints.add(new GeoPoint("NYC", 40.7, -74.0));
        geoPoints.add(new GeoPoint("LA", 34.1, -118.3));
        geoPoints.add(new GeoPoint("Denver",39.74,-104.99));
        geoPoints.add(new GeoPoint("Chicago",41.88,-87.63));
        geoPoints.add(new GeoPoint("Dallas",32.78,-96.8));

        return Stream.of(
                Arguments.of(geoPoints)
        );
    }
}
