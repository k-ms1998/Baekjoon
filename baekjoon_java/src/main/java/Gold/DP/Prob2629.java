package Gold.DP;

import java.io.*;
import java.util.*;

public class Prob2629 {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    static int weightCount;
    static int[] weights;
    static int marbleCount;
    static int[] marbles;

    static boolean[][] dp;
    static int totalW = 0;
    static int lastBit;


    public static void main(String[] args) throws IOException{
        weightCount = Integer.parseInt(br.readLine().trim());

        weights = new int[weightCount + 1];
        st = new StringTokenizer(br.readLine().trim());
        for(int idx = 1; idx <= weightCount; idx++) {
            weights[idx] = Integer.parseInt(st.nextToken());
            totalW += weights[idx];
        }
        lastBit = (1 << (weightCount + 1));
        initDp();

        marbleCount = Integer.parseInt(br.readLine().trim());

        marbles = new int[marbleCount];
        st = new StringTokenizer(br.readLine().trim());
        for(int idx = 0; idx < marbleCount; idx++) {
            marbles[idx] = Integer.parseInt(st.nextToken());
        }

        for(int mIdx = 0; mIdx < marbleCount; mIdx++) {
            int marble = marbles[mIdx];
            boolean found = false;
            for(int w = 0; w <= totalW; w++) {
                int curW = w + marble;
                if(curW > totalW) {
                    break;
                }
                if(dp[weightCount][curW] && dp[weightCount][w]) {
                    found = true;
                    break;
                }
            }


            if(found) {
                sb.append("Y ");
            }else {
                sb.append("N ");
            }
        }


        System.out.println(sb);
    }

    public static void initDp() {
        dp = new boolean[weightCount + 1][totalW + 1];
        dp[0][0] = true;


        for(int wIdx = 1; wIdx <= weightCount; wIdx++) {
            int curW = weights[wIdx];
            int curBit = (1 << wIdx);
            for(int w = 0; w <= totalW; w++) {
                if(w < curW) {
                    dp[wIdx][w] = dp[wIdx - 1][w];
                }else {
                    int prevW = w - curW;
                    dp[wIdx][w] = dp[wIdx - 1][w] | dp[wIdx - 1][prevW];

                }
            }
        }

    }
}
/*
3
20 35 50
1
5
*/