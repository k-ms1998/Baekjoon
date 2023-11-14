package Silver;

import java.io.*;
import java.util.*;

/**
 * Silver 2(1,2,3 더하기 3)
 *
 * https://www.acmicpc.net/problem/15988
 *
 * Solution: DP
 */
public class Prob15988 {

    static final int MOD = 1_000_000_009;

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringBuilder ans = new StringBuilder();
        int t = Integer.parseInt(br.readLine());
        int maxN = 0;
        List<Integer> ns = new ArrayList<>();
        while(t-- > 0){
            int n = Integer.parseInt(br.readLine());
            maxN = Math.max(maxN, n);
            ns.add(n);
        }
        int[] dp = new int[Math.max(maxN + 1, 5)];
        dp[0] = 0;
        dp[1] = 1;
        dp[2] = 2;
        dp[3] = 4;

        for(int i = 4; i < maxN + 1; i++){
            dp[i] = ((dp[i-1] + dp[i-2]) % MOD + dp[i-3]) % MOD;
        }

        for(int n : ns){
            ans.append(dp[n] % MOD).append("\n");
        }

        System.out.println(ans);
    }
}