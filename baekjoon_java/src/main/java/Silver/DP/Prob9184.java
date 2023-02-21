package Silver.DP;

import java.io.*;
import java.util.*;

/**
 * Silver 2(신나는 함수 실행)
 *
 * https://www.acmicpc.net/problem/9184
 * 
 * Solution: DP(메모이제이션)
 * 1. 단순히 문제에서 나온 가상 코드대로 함수를 작성해서 풀이하면 됨
 * 2. 단, 문제에서 말했듯이 그대로 구현하면 너무 오랜 시간이 걸리기 때문에 dp로 해결
 * 3. 메모이제이션을 통해 w(a, b, c) 값을 dp[a][b][c]에 저장해서, 필요할떄 dp에서 값을 바로 추출
 */
public class Prob9184 {

    static long[][][] dp;
    static boolean[][][] visited;
    static StringBuilder ans = new StringBuilder();

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;


        dp = new long[51][51][51];
        visited = new boolean[51][51][51];
        while(true){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            if(a == -1 && b == -1 && c == -1){
                break;
            }

            long cur = w(a, b, c);
            ans.append("w(").append(a).append(", ").append(b).append(", ").append(c).append(") = ").append(cur).append("\n");
        }

        System.out.println(ans);
    }

    public static long w (int a, int b, int c){
        if(a <= 0 || b <= 0 || c <= 0){
            return 1L;
        }
        if(a > 20 || b > 20 || c > 20){
            if(visited[20][20][20]){
                dp[a][b][c] = dp[20][20][20];
                return dp[20][20][20];
            }

            visited[a][b][c] = true;
            dp[a][b][c] = w(20, 20, 20);
            return dp[a][b][c];
        }

        if(visited[a][b][c]){
            return dp[a][b][c];
        }

        visited[a][b][c] = true;
        if(a < b && b < c){

            dp[a][b][c] = w(a, b, c-1) + w(a, b-1, c-1) - w(a, b-1, c);
            return dp[a][b][c];
        }

        dp[a][b][c] = w(a-1, b, c) + w(a-1, b-1, c) + w(a-1, b, c-1) -  w(a-1, b-1, c-1);
        return dp[a][b][c];
    }
}