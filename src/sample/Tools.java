package sample;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.DoubleStream;

public class Tools {

    public static double[][] transpose(double[][] array) {
        if (array == null || array.length == 0) return array;
        int width = array.length;
        int height = array[0].length;
        double[][] array_new = new double[height][width];
        for (int x = 0; x < width; x++) for (int y = 0; y < height; y++) array_new[y][x] = array[x][y];
        return array_new;
    }

    public static double[][] arrToTable(double[] arr, int l) {
        List<double[]> bands = new ArrayList<>();
        double[][] table = new double[l][l];
        int c = 0;
        for (int j = l - 1; j > 0; j--) {
            double[] band = new double[j];
            System.arraycopy(arr, c, band, 0, j);
            c += j;
            bands.add(band);
        }
        for (int i = 0; i < l; i++)
            for (int j = 0; j < l; j++) {
                if (j < i) table[i][j] = 1 / table[j][i];
                if (i == j) table[i][j] = 1;
                if (j > i) table[i][j] = bands.get(i)[j - i - 1];
            }
        return table;
    }

    private static double[][] processThird(double[][] data) {
        double[][] geoms = new double[data.length][data[0].length];
        for (int i = 0; i < data.length; i++) {
            double[] datum = data[i];
            double[][] table = arrToTable(datum, Main.options.length);
            double[] res = calculate(table);
            System.arraycopy(res, 0, geoms[i], 0, res.length);
        }
        return geoms;
    }

    static double[] calculate(double[][] arr) {
        double[] geoms = new double[arr.length];
        double[] geoms2 = new double[arr.length];

        for (int i = 0; i < arr.length; i++) {
            double product = 1;
            for (int j = 0; j < arr[0].length; j++) product *= arr[i][j];
            geoms[i] = Math.pow(product, 1.0 / arr.length);
        }

        double sum = DoubleStream.of(geoms).sum();
        for (int i = 0; i < geoms.length; i++) geoms2[i] = geoms[i] / sum;
        return geoms2;
    }

    public static double[] finalResult() {
        double[][] matrix = transpose(processThird(Main.thirdLvl));
        double[] res = new double[matrix.length];
        double[] sndLvl = calculate(arrToTable(Main.sndLvl, Main.params.length));

        for (int i = 0; i < matrix.length; i++) {
            double sum = 0;
            for (int j = 0; j < matrix[0].length; j++) sum += sndLvl[j] * matrix[i][j];
            res[i] = sum;
        }
        return res;
    }

    public static List<String[]> makePairs(String[] strings) {
        List<String[]> res = new ArrayList<>();
        for (int i = 0; i < strings.length; i++)
            for (int k = i; k < strings.length; k++)
                if (i != k) {
                    String[] pair = new String[2];
                    pair[0] = strings[i];
                    pair[1] = strings[k];
                    res.add(pair);
                }
        return res;
    }

}