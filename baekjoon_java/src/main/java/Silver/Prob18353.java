package Silver;

import java.io.*;
import java.util.*;

/**
 * Silver 2(병사 배치하기)
 *
 * https://www.acmicpc.net/problem/18353
 *
 * Solution: DP(Longest Decreasing Sequence)
 * Longest Decreasing Sequence 랑 동일한 문제
 * 전투력이 무조건 내림차순이어야 하는데, 가장 긴 내림차순을 유지하기 위해 병사를 열외
 * 열외 시킬 병사의 수 = 총 병사의 수 - 가장 긴 감소하는 수열의 길이
 * 
 * 시간 단축을 위해서 Binary Search 도 이용
 */
public class Prob18353 {

    static int n;
    static int[] soldier;
    static int[] dp;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());

        soldier = new int[n + 1];
        dp = new int[n + 1];
        st = new StringTokenizer(br.readLine());
        for(int i = 1; i < n + 1; i++){
            soldier[i] = Integer.parseInt(st.nextToken());
        }
        soldier[0] = Integer.MAX_VALUE;

        int[] sequence = new int[n + 1];
        int idx = 1;
        sequence[1] = soldier[1];
        for(int i = 2; i < n + 1; i++){
            if(sequence[idx] > soldier[i]){
                idx++;
                sequence[idx] = soldier[i];
            }else{
                /**
                 * Binary Search로 현재 숫자(soldier[i])가 들어갈 위치 찾아주기
                 */
                binarySearch(sequence, idx, soldier[i]);
            }
        }

        System.out.println(n - idx);

//        /**
//         * 일반적인 DP로 풀이
//         * 해당 문제는 시간 초과 발생하지 않고 통과하나, 이분 탐색을 했을때보다 시간이 오래 소요되기는 함
//         */
//        int cnt = 0;
//        for(int i = 1; i < n + 1; i++){
//            for(int j = 0; j < i; j++){
//                if(soldier[j] > soldier[i]){
//                    dp[i] = Math.max(dp[i], dp[j] + 1);
//                }
//            }
//            cnt = Math.max(cnt, dp[i]);
//        }
//
//        System.out.println(n - cnt);
    }

    private static void binarySearch(int[] sequence, int idx, int curNum) {
        int left = 1; // sequence 는 인덱스 1부터 시작하기 때문에 left 도 1부터 시작
        int right = idx;
        while(left <= right){
            int mid = (left + right) / 2;
            if(sequence[mid] > curNum){
                left = mid + 1;
            }else{
                right = mid - 1;
            }
        }
        sequence[left] = curNum;
    }


}
/*
7
15 11 4 8 5 12 10

7
25 11 4 8 5 22 10
 */