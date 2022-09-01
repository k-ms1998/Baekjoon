package Silver.DivideAndConquer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Silver 1
 *
 * https://www.acmicpc.net/problem/1629
 *
 * Solution: Divide and Conquer
 */
public class Prob1629 {

    static long a;
    static long b;
    static long c;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        a = Long.parseLong(st.nextToken());
        b = Long.parseLong(st.nextToken());
        c = Long.parseLong(st.nextToken());

        long ansNum = calculate(b);
        System.out.println(ansNum);
    }

    /**
     * ex:
     * a^10 = a^5 * a^5
     *      = (a^2 * a^2 * a) * (a^2 * a^2 * a)
     *      = ((a * a) * (a * a) * a) * ((a * a) * (a * a) * a)
     */
    public static long calculate(long p) {
        if (p == 1) {
            return a % c;
        }

        long num = calculate(p/2);
        if (p % 2 == 0) {
            return num * num % c;
        } else {
            return (num * num % c) * a % c;
        }
    }
}
