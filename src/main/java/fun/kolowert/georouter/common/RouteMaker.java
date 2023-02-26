package fun.kolowert.georouter.common;

import fun.kolowert.georouter.bean.GeoPoint;
import fun.kolowert.georouter.bean.SuperPoint;
import fun.kolowert.georouter.serv.Serv;
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
    private double randomSectorCoefficient = 0.0;

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
        List<SuperPoint> base = points.stream().map(SuperPoint::new).collect(Collectors.toList());
        List<SuperPoint> result = new ArrayList<>(points.size());

        SuperPoint firstPoint = base.remove(0);
        result.add(firstPoint);

        // prepare parameter to find random index by plane way
        var choiceDeepness = randomChoiceDeepness;
        if (choiceDeepness < 1) { choiceDeepness = 1; }

        while (!base.isEmpty()) {
            val lastSuperPoint = result.get(result.size() - 1);
            base.forEach(
                    e -> e.setDistanceToPrevious(
                            calculator.calculateDistanceOnGlobe(lastSuperPoint.getGeoPoint(), e.getGeoPoint()))
            );

            base = base.stream().sorted().collect(Collectors.toList());

//            System.out.println("#300 lastSuperPoint > " + lastSuperPoint); //||||||||||||||||||||||||||
//            System.out.println("#310 sorted base > " + base );  //||||||||||||||||||||||||||

            // make random index to chose from sorted points
            if (choiceDeepness > base.size()) { choiceDeepness = base.size(); }
            int randomIndex;
            if (randomSectorCoefficient != 0.0) { // do it by randomSector way
                randomIndex = Serv.randomSector(choiceDeepness - 1, 0.6);
            } else { // or do it by plane way
                randomIndex = ThreadLocalRandom.current().nextInt(0, choiceDeepness);
            }

//            System.out.println("#500 choiceDeepness > " + choiceDeepness); //||||||||||||||||||||||||||
//            System.out.println("#510 randomIndex > " + randomIndex); //||||||||||||||||||||||||||
//            System.out.println();

            SuperPoint nextPoint = base.remove(randomIndex);
            result.add(nextPoint);
        }

        // add first point to the end to make loop
        if (isLoop) {
            result.add(firstPoint);
        }

//        System.out.println("#900 " + " ChoiceDeepness: " + randomChoiceDeepness + " SectorCoef: "
//                + randomSectorCoefficient + " isLoop:" + isLoop); //||||||||||||||||||||||||||
//        System.out.println("#910 " + result); //||||||||||||||||||||||||||

        return result.stream().map(SuperPoint::getGeoPoint).collect(Collectors.toList());
    }
}
