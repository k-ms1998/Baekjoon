package Gold.Basic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

/**
 * Gold 5
 * 
 * Solution: 수학
 *
 */
public class Prob1011 {

    static int s;
    static int d;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int t = Integer.valueOf(br.readLine());
        for (int i = 0; i < t; i++) {
            st = new StringTokenizer(br.readLine());
            s = Integer.parseInt(st.nextToken());
            d = Integer.parseInt(st.nextToken());

            int dist = d - s;
            int maxTravel = (int) Math.sqrt(dist);

            if (maxTravel == Math.sqrt(dist)) {
                System.out.println(maxTravel * 2 - 1);
            } else if (dist <= maxTravel * (maxTravel + 1)) {
                System.out.println(2 * maxTravel);
            } else {
                System.out.println(2 * maxTravel + 1);
            }

        }
    }
}
