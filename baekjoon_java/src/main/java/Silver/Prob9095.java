package Silver;

import java.io.*;
import java.util.*;

/**
 * Silver 3(1, 2, 3 더하기)
 *
 * https://www.acmicpc.net/problem/9095
 *
 * Solution: DFS
 * 1. 단순 조합론 문제
 * 2. 깊이 우선 탐색으로 1, 2, 3을 한번씩 더해보면서 합을 구하기
 * 3. 이때, 조합의 합이 n 값을 넘으면 죵료 (n 값이 넘어가면 n을 만드는 것이 불가능함)
 */
public class Prob9095 {

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder ans = new StringBuilder();

        int t = Integer.parseInt(br.readLine());
        while(t-- > 0){
            int n = Integer.parseInt(br.readLine());

            int cnt = 0;
            for(int i = 1; i <= 3; i++){
                cnt += findAnswer(i, n, 0);
            }
            ans.append(cnt).append("\n");
        }

        System.out.println(ans);
    }

    public static int findAnswer(int sum, int target, int tmpCnt){
        if(sum == target){
            return tmpCnt + 1;
        }else if(sum > target){
            return tmpCnt;
        }

        for(int i = 1; i <= 3; i++){
            tmpCnt = findAnswer(sum + i, target, tmpCnt);
        }

        return tmpCnt;
    }
}