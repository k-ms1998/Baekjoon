package Silver;

import java.io.*;
import java.util.*;

/**
 * Silver 2(부분 수열의 합)
 *
 * https://www.acmicpc.net/problem/1182
 */
public class Prob1182 {

    public static int n, s;
    public static int[] nums;
    public static int ans = 0;

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        s = Integer.parseInt(st.nextToken());
        ans = s == 0 ? -1 : 0;

        nums = new int[n + 1];
        st = new StringTokenizer(br.readLine());
        for(int i = 1; i < n + 1; i++){
            nums[i] = Integer.parseInt(st.nextToken());
        }
        findAnswer(1, 0);

        System.out.println(ans);
    }

    public static void findAnswer(int depth, long curSum){
        if(depth > n){
            if(curSum == s){
                ++ans;
            }
            return;
        }

        findAnswer(depth + 1, curSum);
        findAnswer(depth + 1, curSum + nums[depth]);
    }
}