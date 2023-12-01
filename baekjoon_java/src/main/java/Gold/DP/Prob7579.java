package Gold.DP;
import java.io.*;
import java.util.*;

public class Prob7579{

    static int n, m;
    static int[] a;
    static int[] c;
    static int[][]dp;
    static int maxCost = 0;
    static final int INF = 1_000_000_001;

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        a = new int[n + 1];
        st = new StringTokenizer(br.readLine());
        for(int i = 1; i < n + 1; i++){
            a[i] = Integer.parseInt(st.nextToken());
        }

        c = new int[n + 1];
        st = new StringTokenizer(br.readLine());
        for(int i = 1; i < n + 1; i++){
            c[i] = Integer.parseInt(st.nextToken());
            maxCost += c[i];
        }

        dp = new int[n + 1][maxCost + 1];

        for(int i = 1; i < n + 1; i++){
            int curA = a[i];
            int curC = c[i];
            for(int j = 0; j < maxCost + 1; j++){
                if(curC > j){
                    dp[i][j] = dp[i-1][j];
                }else{
                    dp[i][j] = Math.max(dp[i-1][j], dp[i-1][j-curC] + curA);
                }
            }
        }

        int answer = INF;
        for(int i = 1; i < n + 1; i++){
            for(int j = 0; j < maxCost + 1; j++){
                if(dp[i][j] >= m){
                    answer = Math.min(answer, j);
                    break;
                }
            }
        }

        System.out.println(answer);

    }
}