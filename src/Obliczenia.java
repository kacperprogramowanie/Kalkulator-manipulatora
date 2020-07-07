import java.math.BigDecimal;
import java.math.RoundingMode;

public class Obliczenia {
    double L3 = 0.0;
    public Obliczenia() {}

        public double[] prosteZadanie ( double[] przegub1, double[] przegub2, double[] przegub3, double[] pozycja)
        {
            double[][] T01 = transformacja(przegub1);
            double[][] T12 = transformacja(przegub2);
            double[][] T23 = transformacja(przegub3);

            double[][] T02 = mnozenieMacierzy(T01, T12);
            double[][] T03 = mnozenieMacierzy(T02, T23);

            double[][] Ptcp = new double[4][1];
            Ptcp[0][0] = pozycja[0];
            Ptcp[1][0] = pozycja[1];
            Ptcp[2][0] = pozycja[2];
            Ptcp[3][0] = 1;

            double[][] Ptcp0 = mnozenieMacierzy(T03, Ptcp);
            double[] wynik = new double[3];
            wynik[0] = Ptcp0[0][0];
            wynik[1] = Ptcp0[1][0];
            wynik[2] = Ptcp0[2][0];

            return wynik;

        }

        public  double[] odwrotneZadanie ( double[] przegub1, double[] przegub2, double[] przegub3, double[] pozycja){
            double a1 = przegub2[1];
            double a2 = przegub3[1];
            double a = Math.toDegrees(Math.atan(pozycja[1] / pozycja[0]));
            double c = Math.sqrt(pozycja[0] * pozycja[0] + pozycja[1] * pozycja[1]);
            double Beta, Ksi, Gamma;

            do {
                do {
                    L3 = L3 + 0.01;
                    if (L3 > 500) {
                        double[] error = {-999, 0, 0};
                        return error;
                    }
                    Beta = Math.toDegrees(Math.acos(((L3 * L3) + (a2 * a2) - (c * c) - (a1 * a1)) / (-2 * c * a1)));
                } while (Beta < 0 || Beta > 275 || Beta + a < 0 || Beta + a > 360 || Double.isNaN(Beta));


                Ksi = Math.toDegrees(Math.asin((c * Math.sin(Math.toRadians(Beta))) / (Math.sqrt((L3 * L3) + (a2 * a2)))));
                Gamma = Math.toDegrees(Math.asin((L3 * Math.sin(Math.toRadians(90))) / (Math.sqrt((L3 * L3) + (a2 * a2)))));
            } while (Ksi + Gamma < 0 || Ksi + Gamma > 180 || Double.isNaN(Ksi) || Double.isNaN(Gamma));


            double[] wynik = new double[3];
            wynik[0] =Beta + a;
            wynik[1] = 180 - Ksi - Gamma;
            wynik[2] = L3;
            return wynik;
        }

        public  double[] predkoscTCP ( double[] przegub1, double[] przegub2, double[] przegub3, double[] pozycja){
            double[][] jakobian = jakobian(przegub1, przegub2, przegub3);

            double[][] Vp = new double[3][1];
            Vp[0][0] = pozycja[0];
            Vp[1][0] = pozycja[1];
            Vp[2][0] = pozycja[2];

            double[][] Vtcp = mnozenieMacierzy(jakobian, Vp);
            double[] wynik = new double[3];
            wynik[0] = Vtcp[0][0];
            wynik[1] = Vtcp[1][0];
            wynik[2] = Vtcp[2][0];

            return wynik;
        }

        public  double[] predkoscPrzegubow ( double[] przegub1, double[] przegub2, double[] przegub3,
        double[] pozycja){
            double[][] jakobian = jakobian(przegub1, przegub2, przegub3);
            double wyz = wyznacznik(jakobian);
            double[][] jakobianOdw = macierzOdwrotna(jakobian, wyz);

            double[][] Vtcp = new double[3][1];
            Vtcp[0][0] = pozycja[0];
            Vtcp[1][0] = pozycja[1];
            Vtcp[2][0] = pozycja[2];

            double[][] Vp = mnozenieMacierzy(jakobianOdw, Vtcp);
            double[] wynik = new double[3];
            wynik[0] = Vp[0][0];
            wynik[1] = Vp[1][0];
            wynik[2] = Vp[2][0];

            return wynik;
        }

        public double[] silaTCP ( double[] przegub1, double[] przegub2, double[] przegub3, double[] pozycja){
            double[][] jakobian = jakobian(przegub1, przegub2, przegub3);
            double[][] jakobianT = transponacja(jakobian);
            double wyz = wyznacznik(jakobianT);
            double[][] jakobianOdw = macierzOdwrotna(jakobianT, wyz);

            double[][] Fp = new double[3][1];
            Fp[0][0] = pozycja[0];
            Fp[1][0] = pozycja[1];
            Fp[2][0] = pozycja[2];

            double[][] Ftcp = mnozenieMacierzy(jakobianOdw, Fp);
            double[] wynik = new double[3];
            wynik[0] = Ftcp[0][0];
            wynik[1] = Ftcp[1][0];
            wynik[2] = Ftcp[2][0];

            return wynik;
        }

        public  double[] silaPrzegubow ( double[] przegub1, double[] przegub2, double[] przegub3, double[] pozycja)
        {
            double[][] jakobian = jakobian(przegub1, przegub2, przegub3);
            double[][] jakobianT = transponacja(jakobian);

            double[][] Ftcp = new double[3][1];
            Ftcp[0][0] = pozycja[0];
            Ftcp[1][0] = pozycja[1];
            Ftcp[2][0] = pozycja[2];

            double[][] Fp = mnozenieMacierzy(jakobianT, Ftcp);
            double[] wynik = new double[3];
            wynik[0] = Fp[0][0];
            wynik[1] = Fp[1][0];
            wynik[2] = Fp[2][0];

            return wynik;
        }


        public  double[][] mnozenieMacierzy ( double[][] m1, double[][] m2){
            int m1R = m1.length;
            int m1W = m1[1].length;
            int m2W = m2[1].length;
            double[][] m3 = new double[m1R][m2W];

            for (int j = 0; j < m1R; j++) {
                for (int i = 0; i < m2W; i++) {
                    m3[j][i] = 0;
                    for (int k = 0; k < m1W; k++) {
                        m3[j][i] = m3[j][i] + (m1[j][k] * m2[k][i]);
                    }
                }
            }
            //Zmniejszenie precyzji danych rzeczywistych
            for (int j = 0; j < m1R; j++) {
                for (int i = 0; i < m2W; i++) {
                    m3[j][i] = BigDecimal.valueOf(m3[j][i]).setScale(6, RoundingMode.HALF_UP).doubleValue();
                }
            }
            return m3;
        }


        public static double[][] transformacja ( double[] przegub){
            double[][] macierz = new double[4][4];

            macierz[0][0] = Math.cos(Math.toRadians(przegub[3]));
            macierz[0][1] = -1 * (Math.sin(Math.toRadians(przegub[3])));
            macierz[0][2] = 0;
            macierz[0][3] = przegub[1];

            macierz[1][0] = Math.cos(Math.toRadians(przegub[0])) * Math.sin(Math.toRadians(przegub[3]));
            macierz[1][1] = Math.cos(Math.toRadians(przegub[0])) * Math.cos(Math.toRadians(przegub[3]));
            macierz[1][2] = -(Math.sin(Math.toRadians(przegub[0])));
            macierz[1][3] = -1 * przegub[2] * (Math.sin(Math.toRadians(przegub[0])));

            macierz[2][0] = Math.sin(Math.toRadians(przegub[0])) * Math.sin(Math.toRadians(przegub[3]));
            macierz[2][1] = Math.sin(Math.toRadians(przegub[0])) * Math.cos(Math.toRadians(przegub[3]));
            macierz[2][2] = Math.cos(Math.toRadians(przegub[0]));
            macierz[2][3] = przegub[2] * (Math.cos(Math.toRadians(przegub[0])));

            macierz[3][0] = 0;
            macierz[3][1] = 0;
            macierz[3][2] = 0;
            macierz[3][3] = 1;

            for (int j = 0; j < 4; j++) {
                for (int i = 0; i < 4; i++) {
                    macierz[j][i] = BigDecimal.valueOf(macierz[j][i]).setScale(6, RoundingMode.HALF_UP).doubleValue();
                }
            }

            return macierz;
        }

        public static double[][] jakobian ( double[] p1, double[] p2, double[] p3){
            double a1s = Math.sin(Math.toRadians(p1[0]));
            double a1c = Math.cos(Math.toRadians(p1[0]));
            double a2s = Math.sin(Math.toRadians(p2[0]));
            double a2c = Math.sin(Math.toRadians(p2[0]));
            double a3s = Math.sin(Math.toRadians(p3[0]));
            double a3c = Math.sin(Math.toRadians(p3[0]));

            double l1 = p1[1];
            double l2 = p2[1];
            double l3 = p3[1];

            double d1 = p1[2];
            double d2 = p2[2];
            double d3 = p3[2];

            double o1s = Math.sin(Math.toRadians(p1[3]));
            double o1c = Math.cos(Math.toRadians(p1[3]));
            double o2s = Math.sin(Math.toRadians(p2[3]));
            double o2c = Math.sin(Math.toRadians(p2[3]));
            double o3s = Math.sin(Math.toRadians(p3[3]));
            double o3c = Math.sin(Math.toRadians(p3[3]));

            double[][] jak = new double[3][3];

            jak[0][0] = (-1 * o1s * (l3 * o2c + l3 * a3s * o2s)) + (o1c * (-1 * l3 * a2c * o2s + d3 * a3s * a2c + d2 * a2s));
            jak[0][1] = (-1 * o2s * (l3 * o1c + d3 * a3s * o1s * a2c + l2)) + (o2c * (-1 * l3 * o1s * a2c + d3 * a3s * o1c));
            jak[0][2] = (a3s * o1c * o2s + a3s * o1s * a2c * o2c + o1s * a2s * a3c);

            jak[1][0] = ((-1 * o1s * (l3 * a1c * a2c * o2s - d3 * a3s * a1c * a2c * o2c - d3 * a3c * a1c * a2s - d2 * a1c * a2s)) + (o1c * (l3 * a1c * o2c + d3 * a3s * a1c * o2s + l2 * a1c)));
            jak[1][1] = ((-1 * o2s * (l3 * a1c * o1s - d3 * a3s * a1c * o1c * a2s + d3 * a3s * a1s * a2s)) + (o2c * (l3 * a1c * o1c * a2c - 1 * l3 * a1s * a2s + d3 * a3s * a1c * o1s)));
            jak[1][2] = (a3s * a1c * o1s * o2s - a3s * a1c * o1c * a2c * o2c + a3s * a1s * a2s * o2c - a3c * a1c * o1c * a2s + a3c * a1s * a2c);

            jak[2][0] = ((-1 * o1s * (l3 * a1s * a2c * o2s - d3 * a3s * a1s * a2c * o2s - d3 * a3c * a1s * a2s - d2 * a1s * a2s)) + (o1c * (l3 * a1s * o2c + d3 * a3s * a1s * o2s + l2 * a1s)));
            jak[2][1] = ((-1 * o2s * (l3 * a1s * o1s - d3 * a3s * a1s * o1c * a2c - d3 * a3s * a1c * a2s)) + (o2c * (l3 * a1s * o1c * a2c + l3 * a1c * a2s + d3 * a3s * a1s * o1s)));
            jak[2][2] = (a3s * a1s * o1s * o2s - a3s * a1s * o1c * a2c * o2c - a3s * a1c * a2s * o2c - a3c * a1s * o1c * a2s + a3c * a1c * a2c);

            for (int j = 0; j < 3; j++) {
                for (int i = 0; i < 3; i++) {
                    jak[j][i] = BigDecimal.valueOf(jak[j][i]).setScale(6, RoundingMode.HALF_UP).doubleValue();
                }
            }

            return jak;
        }

        public static double wyznacznik ( double[][] tab){
            double wyznacznik = tab[0][0] * tab[1][1] * tab[2][2] + tab[0][1] * tab[1][2] * tab[2][0] + tab[0][2] * tab[1][0] * tab[2][1]
                    - tab[0][2] * tab[1][1] * tab[2][0] - tab[1][2] * tab[2][1] * tab[0][0] - tab[2][2] * tab[0][1] * tab[1][0];
            return wyznacznik;
        }

        public static double[][] transponacja ( double[][] tab){
            double[][] nTab = new double[tab.length][tab.length];
            for (int i = 0; i < tab.length; i++) {
                for (int j = 0; j < tab.length; j++) {
                    nTab[i][j] = tab[j][i];
                }
            }
            return nTab;
        }

        public static double[][] macierzOdwrotna ( double[][] tab, double wyz){
            double[][] macierzOdw = new double[3][3];
            macierzOdw[0][0] = tab[1][1] * tab[2][2] - tab[1][2] * tab[2][1];
            macierzOdw[0][1] = -1 * (tab[1][0] * tab[2][2] - tab[1][2] * tab[2][0]);
            macierzOdw[0][2] = tab[1][0] * tab[2][1] - tab[1][1] * tab[2][0];

            macierzOdw[1][0] = -1 * (tab[0][1] * tab[2][2] - tab[0][2] * tab[2][1]);
            macierzOdw[1][1] = tab[0][0] * tab[2][2] - tab[0][2] * tab[2][0];
            macierzOdw[1][2] = -1 * (tab[0][0] * tab[2][1] - tab[0][1] * tab[2][0]);

            macierzOdw[2][0] = tab[0][1] * tab[1][2] - tab[0][2] * tab[1][1];
            macierzOdw[2][1] = -1 * (tab[0][0] * tab[1][2] - tab[0][2] * tab[1][0]);
            macierzOdw[2][2] = tab[0][0] * tab[1][1] - tab[0][1] * tab[1][0];

            macierzOdw = transponacja(macierzOdw);

            for (int i = 0; i < tab.length; i++) {
                for (int j = 0; j < tab.length; j++) {
                    macierzOdw[i][j] = macierzOdw[i][j] / wyz;
                }
            }
            return macierzOdw;
        }

        public void edytujL3(int x){
        L3=L3+x;
        if(L3<0) L3=0;
        if(L3>500) L3=500;
    }

    }
