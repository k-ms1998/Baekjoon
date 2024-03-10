package Gold;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Prob15486 {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    static int totalDays;

    static int[] dp;

    public static void main(String[] args) throws IOException {
        totalDays = Integer.parseInt(br.readLine());
        dp = new int[totalDays + 1];

        for(int day = 1; day <= totalDays; day++){
           st = new StringTokenizer(br.readLine());
           int duration = Integer.parseInt(st.nextToken());
           int value = Integer.parseInt(st.nextToken());

           int endDay = day + duration - 1;
           dp[day] = Math.max(dp[day], dp[day - 1]);
           if(endDay > totalDays){
                continue;
           }

           dp[endDay] = Math.max(dp[endDay], value);
           dp[endDay] = Math.max(dp[endDay], Math.max(dp[endDay - 1], dp[day - 1] + value));
        }

        System.out.println(dp[totalDays]);
    }
}
