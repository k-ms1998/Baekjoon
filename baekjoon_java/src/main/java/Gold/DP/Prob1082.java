package Gold.DP;

import java.io.*;
import java.util.*;

/**
 * Gold 3(방 번호)
 *
 * https://www.acmicpc.net/problem/1082
 *
 * Solution: DP
 */
public class Prob1082 {

    static int n;
    static int[] price;
    static String[] dp;
    static int m;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());

        price = new int[n];
        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < n; i++){
            price[i] = Integer.parseInt(st.nextToken());
        }

        m = Integer.parseInt(br.readLine());
        dp = new String[m + 1];

        dp[0] = "";
        for(int i = 1; i < m + 1; i++){
            dp[i] = "";
            for(int j = 0; j < n; j++){
                int p = price[j];
                int prevP = i - p;
                if(prevP >= 0){
                    String prevNum = dp[prevP];
                    String updated = updateDp(prevNum, j);
                    if(updated.startsWith("0")){
                        updated = "0";
                    }
                    if(dp[i].length() < updated.length()){
                        dp[i] = updated;
                    }else if(dp[i].length() == updated.length()){
                        if(dp[i].compareTo(updated) < 0){
                            dp[i] = updated;
                        }
                    }
                }
            }
        }

        System.out.println(dp[m]);
    }

    public static String updateDp(String num, int target) {
        String updated = "";

        boolean flag = false;
        for(int i = 0; i < num.length(); i++){
            int cur = Integer.parseInt(String.valueOf(num.charAt(i)));
            if(!flag && cur <= target){
                updated += String.valueOf(target);
                flag = true;
            }
            updated += String.valueOf(cur);
        }
        if(!flag){
            updated += String.valueOf(target);
        }

        return updated;
    }
}

