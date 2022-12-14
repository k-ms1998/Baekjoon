package Silver;

import java.io.*;
import java.util.*;

/**
 * Silver 5(왕복)
 * 
 * https://www.acmicpc.net/problem/18311
 * 
 * Solution: 누적합
 */
public class Prob18311 {

    static int n;
    static long k;
    static long[] num;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        k = Long.parseLong(st.nextToken());

        /**
         * 1~n 번째 숫자의 누적합을 num[i]에 저장
         */
        long curD = 0;
        num = new long[n + 1];
        st = new StringTokenizer(br.readLine());
        for(int i = 1; i < n + 1; i++){
            long curNum = Long.parseLong(st.nextToken());
            curD += curNum;
            num[i] = curD;
        }
        /**
         * k의 값이 누적합의 최대값보다 크면, 끝까지 갔다가 다시 출발 지점 방향으로 돌아가는 것
         * 그러므로, k의 값을 알맞게 조정
         */
        if(num[n] < k) {
            k = num[n] - (k - num[n]);
        }

        /**
         * k의 값이 들어가는 누적합의 구간을 찾기
         */
        int ans = 0;
        for(int i = n; i >= 1; i--) {
            if(k >= num[i-1] && k < num[i]) {
                ans = i;
                break;
            }
        }
        System.out.println(ans);
    }
}
/*
5 43
7 4 2 4 5
-> 1

5 0
7 4 2 4 5
-> 1

5 22
7 4 2 4 5
-> 5
 */