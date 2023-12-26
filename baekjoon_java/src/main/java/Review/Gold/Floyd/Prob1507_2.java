package Review.Gold.Floyd;

import java.io.*;
import java.util.*;

public class Prob1507_2 {

    static int n;

    static final int INF = 1000000000;
    static int[][] original;
    static int[][] dist;

    static int ans = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        dist = new int[n + 1][n + 1];
        original = new int[n + 1][n + 1];
        for (int y = 1; y < n + 1; y++) {
            st = new StringTokenizer(br.readLine());
            for (int x = 1; x < n + 1; x++) {
                int cost = Integer.parseInt(st.nextToken());
                dist[y][x] = cost;
                original[y][x] = cost;
            }
        }

        floydWarshall();
        if(ans != -1){
            findAnswer();
        }
        System.out.println(ans);
    }

    public static void floydWarshall() {
        for (int r = 1; r < n + 1; r++) {
            for (int s = 1; s < n + 1; s++) {
                for (int d = 1; d < n + 1; d++) {
                    if (r == s || s == d || d == r) {
                        continue;
                    }

                    if (original[s][d] > original[s][r] + original[r][d]) {
                        ans = -1;
                        return;
                    } else if (original[s][d] == original[s][r] + original[r][d]) {
                        dist[s][d] = 0;
                    }
                }
            }
        }
    }

    public static void findAnswer() {
        for (int y = 1; y < n + 1; y++) {
            for (int x = y + 1; x < n + 1; x++) {
                ans += dist[y][x];
            }
        }
    }
}
