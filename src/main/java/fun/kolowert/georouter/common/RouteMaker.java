package fun.kolowert.georouter.common;

import fun.kolowert.georouter.bean.GeoPoint;
import fun.kolowert.georouter.bean.SuperPoint;
import lombok.Getter;
import lombok.Setter;
import lombok.val;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class RouteMaker {

    Calculator calculator;

    @Setter
    @Getter
    private int randomChoiceDeepness = 2;

    @Setter
    @Getter
    private boolean isLoop = true;


    public RouteMaker(Calculator calculator) {
        this.calculator = calculator;
    }

    /**
     * It receives a list of points and arranges them in a sequence according to the defined algorithm.
     * Algorithm:
     * 1) Take the first point from the main list;
     * 2) Move the point to the list of results;
     * 3) Find in the main list n geopoints the closest to previous one;
     * 4) Randomly choose one of the n geopoints and move it to the list of results;
     * 5) Go to line 2) while there are geopoints left in the main list;
     * 6) Return list of results;
     * @return arranged list of points
     */
    public List<GeoPoint> makeSingleRoute(List<GeoPoint> points) {
        List<SuperPoint> base = points.stream().map(e -> new SuperPoint(e)).collect(Collectors.toList());
        List<SuperPoint> result = new ArrayList<>(points.size());

        SuperPoint firstPoint = base.remove(0);
        result.add(firstPoint);

        while (!base.isEmpty()) {
            val lastSuperPoint = result.get(result.size() - 1);
            base.forEach(
                    e -> e.setDistanceToPrevious(
                            calculator.calculateDistanceOnGlobe(lastSuperPoint.getGeoPoint(), e.getGeoPoint()))
            );

            base = base.stream().sorted().collect(Collectors.toList());

            // chose randomly one of nearest
            var rightEdge = randomChoiceDeepness - 1;
            if (rightEdge < 1) {
                rightEdge = 1;
            }
            if (rightEdge > base.size() - 1) {
                rightEdge = base.size() - 1;
            }

            // TODO it could be more interesting here
            int randomIndex = ThreadLocalRandom.current().nextInt(0, rightEdge + 1);

            SuperPoint nextPoint = base.remove(randomIndex);
            result.add(nextPoint);
        }

        // add first point to the end to make loop
        if (isLoop) {
            result.add(firstPoint);
        }

        return result.stream().map(e -> e.getGeoPoint()).collect(Collectors.toList());
    }
}
