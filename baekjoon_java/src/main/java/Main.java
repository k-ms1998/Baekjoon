import Bronze.Prob2798;
import Bronze.Prob2920;
import Gold.Prob4195;
import Silver.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
        prob10828_solve();

        /**
         * Gold
         */
//        prob4195_solve();
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

        problem.solve(n, m , data);
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
}
