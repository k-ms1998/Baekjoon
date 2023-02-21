package Silver.DP;

import java.io.*;
import java.util.*;

/**
 * Silver 1(카드 구매하기)
 *
 * https://www.acmicpc.net/problem/11052
 *
 * Solution: DP
 * 1. 단순 배낭 문제
 * 2. 카드팩\카드의 개수: 1 2 3 ... n
 *                    1
 *                    2
 *                    3
 *                   ...
 *                    n
 *  3. 점화식(j >= i 일때): dp[i][j] = Math.max(dp[i-1][j], cards[i] + dp[i][j-i])
 *      => j의 카드를 만들때 필요한 최대 비용
 *          = Max(i-1 번째 카드팩까지 써서 j 만들기, i번째 카드팩을 하나 사용 + i 번째 카드를 쓸때 (j-1)개의 카드를 만드는데 드는 비용)
 */
public class Prob11052 {

    static int n;
    static int[] cards;
    static int[][] dp;

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        cards = new int[n + 1];

        st = new StringTokenizer(br.readLine());
        for(int i = 1; i < n + 1; i++){
            cards[i] = Integer.parseInt(st.nextToken());
        }

        dp = new int[n + 1][n + 1];
        for(int i = 1; i < n + 1; i++){
            int curCard = cards[i];
            for(int j = 1; j < n + 1; j++){
                if(j < i){
                    dp[i][j] = dp[i-1][j];
                }else{
                    dp[i][j] = Math.max(dp[i-1][j], curCard + dp[i][j - i]);
                }

            }
        }

        System.out.println(dp[n][n]);
    }
}