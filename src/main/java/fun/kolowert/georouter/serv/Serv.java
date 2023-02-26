package fun.kolowert.georouter.serv;

import java.util.concurrent.ThreadLocalRandom;

public class Serv {

    /**
     * It returns random sector number in the sectorAmount range.
     * @param sectorAmount range for random sector number, like (0 .. sectorAmount - 1)
     * @param sectorCoefficient part of first sector (should be > 0 and <= 1)
     *                          if this param is not in range (0-1] it become 0.5
     * @return random sector number
     * Returned number has probability of sectorCoefficient to hit sector-0 otherwise any other sector.
     * If returned number not hit sector-0 it has probability of sectorCoefficient / 2 to hit sector-1 otherwise any other sector.
     * If returned number not hit sector-1 it has probability of sectorCoefficient / 4 to hit sector-2 otherwise any other sector.
     * ... and so on in sectorAmount range
     */
    public static int randomSector(int sectorAmount, double sectorCoefficient) {
        if (sectorAmount < 0) { sectorAmount = 0; }
        if (sectorAmount > 16) { sectorAmount = 16; }
        if (sectorCoefficient <= 0 || sectorCoefficient > 1) { sectorCoefficient = 0.5; }

        int sector = 0;
        while (sector < sectorAmount) {
            double random = ThreadLocalRandom.current().nextDouble();
            if (random <= sectorCoefficient) {
                return sector;
            }
            ++sector;
        }
        return sectorAmount;
    }

    public static String normDouble(double input, int tail) {
        if (tail < 0) { tail = 0; }
        if (tail > 12) { tail = 12; }

        double c = Math.pow(10, tail);
        double d = Math.round(0.0 + input * c) / c;

        int left = (int)d;
        double right = d - left;
        String niceLeft = String.valueOf(left);
        String niceRight = right + "00000000000000";
        niceRight = niceRight.subSequence(2, 2 + tail).toString();

        return tail > 0 ? niceLeft + "." + niceRight : niceLeft;

    }

    public static void main(String[] args) {
        System.out.println(normDouble(15.555556, 0));
        System.out.println(normDouble(15.555556, 1));
        System.out.println(normDouble(15.555556, 2));
        System.out.println(normDouble(15.555556, 3));
        System.out.println(normDouble(15.555556, 4));
        System.out.println(normDouble(15.0, 4));
    }

}
