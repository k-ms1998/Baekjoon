import Bronze.Prob2798;
import Bronze.Prob2920;
import Silver.Prob1874;
import Silver.Prob1966;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        /**
         * Bronze
         */
//        prob2798_solve();
//        prob2920_solve();

        /**
         * Silver
         */
//        prob1874_solve();
        prob1966_solve();

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
}
