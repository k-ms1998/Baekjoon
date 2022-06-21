import Bronze.*;
import Gold.*;
import Silver.*;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        /**
         * Bronze
         */
//        prob2798_solve();
//        prob2920_solve();

        /**
         * Silver
         */
//        prob1874_solve();
//        prob1966_solve();
//        prob5397_solve();
//        prob1920_solve();
//        prob10828_solve();
//        prob11866_solve();
//        prob13335_solve();
//        prob2346_solve();
//        prob1543_solve();
//        prob1302_solve();
//        prob1325_solve();
//        prob1427_solve();
//        prob10814_solve();
        prob11650_solve();

        /**
         * Gold
         */
//        prob4195_solve();
//        prob17298_solve();
//        prob2110_solve();
//        prob1939_solve();
//        prob12865_solve();
//        prob1461_solve();
    }

    private static void prob2798_solve() {
        Prob2798 problem = new Prob2798();

        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        List<Integer> data = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            data.add(sc.nextInt());
        }

        problem.solve(n, m, data);
    }

    private static void prob2920_solve() {
        Prob2920 problem = new Prob2920();

        Scanner sc = new Scanner(System.in);

        List<Integer> data = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            data.add(sc.nextInt());
        }

        problem.solve(data);
    }

    private static void prob1874_solve() {
        Prob1874 problem = new Prob1874();

        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        List<Integer> data = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            data.add(sc.nextInt());
        }

        problem.solve(n, data);
    }

    private static void prob1966_solve() {
        Prob1966 problem = new Prob1966();

        problem.solve();
    }

    private static void prob5397_solve() {
        Prob5397 problem = new Prob5397();

        problem.solve();
    }

    private static void prob1920_solve() {
        Prob1920 problem = new Prob1920();

        problem.solve();
    }

    private static void prob4195_solve() {
        Prob4195 problem = new Prob4195();

        problem.solve();
    }

    private static void prob10828_solve() throws IOException {
        Prob10828 problem = new Prob10828();

        problem.solve();
    }

    private static void prob17298_solve() throws IOException {
        Prob17298 problem = new Prob17298();

        problem.solve();
    }

    private static void prob11866_solve() throws IOException {
        Prob11866 problem = new Prob11866();

        problem.solve();
    }

    private static void prob13335_solve() throws IOException {
        Prob13335 problem = new Prob13335();

        problem.solve();
    }

    private static void prob2346_solve() throws IOException {
        Prob2346 problem = new Prob2346();

        problem.solve();
    }

    private static void prob1543_solve() throws IOException {
        Prob1543 problem = new Prob1543();

        problem.solve();
    }

    private static void prob1302_solve() throws IOException {
        Prob1302 problem = new Prob1302();

        problem.solve();
    }

    private static void prob2110_solve() throws IOException {
        Prob2110 problem = new Prob2110();

        problem.solve();
    }

    private static void prob1939_solve() throws IOException {
        Prob1939 problem = new Prob1939();

        problem.solve();
    }

    private static void prob12865_solve() throws IOException {
        Prob12865 problem = new Prob12865();

        problem.solve();
    }

    private static void prob1325_solve() throws IOException {
        Prob1325 problem = new Prob1325();

        problem.solve();
    }

    private static void prob1461_solve() throws IOException {
        Prob1461 problem = new Prob1461();

        problem.solve();
    }

    private static void prob1427_solve() throws IOException{
        Prob1427 problem = new Prob1427();

        problem.solve();
    }

    private static void prob10814_solve() throws IOException{
        Prob10814 problem = new Prob10814();

        problem.solve();
    }

    private static void prob11650_solve() throws IOException {
        Prob11650 problem = new Prob11650();

        problem.solve();
    }
}
