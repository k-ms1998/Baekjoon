package Gold.BitMasking;

import java.io.*;
import java.util.*;

/**
 * Gold 5(캠프 준비)
 *
 * https://www.acmicpc.net/problem/16938
 * 
 * Solution: BruteForce + BitMasking
 * 1. 비트마스킹을 통해 모든 경우의 수 찾기
 * -> n = 5이면, 00000~11111  탐색
 *  -> 00110 -> 2번 문제랑 3번 문제를 고르는 경우
 */
public class Prob16938 {

    static int n,  l, r, x;
    static int[] num;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        l = Integer.parseInt(st.nextToken());
        r = Integer.parseInt(st.nextToken());
        x = Integer.parseInt(st.nextToken());

        num = new int[n];
        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < n; i++){
            num[i] = Integer.parseInt(st.nextToken());
        }

        /**
         * n = 3 -> (1 << n) = 1000
         * -> n = 3이면 3개의 문제만 확인하면되기 때문에, 000~111 확인하면 됨
         * (1000(2) - 1 = 111(2))
         */
        int answer = 0;
        int maxBit = (1 << n) - 1; 
        for (int bit = 0; bit <= maxBit; bit++) {
            int sum = 0;
            int maxN = 0;
            int minN = Integer.MAX_VALUE;
            int cnt = 0;
            for (int i = 0; i < n; i++) {
                if((bit & (1 << i)) == (1 << i)){
                    ++cnt;
                    sum += num[i];
                    maxN = Math.max(maxN, num[i]);
                    minN = Math.min(minN, num[i]);
                }
            }
            if(sum >= l && sum <= r && (maxN - minN) >= x && cnt >= 2){
                ++answer;
            }
        }

        System.out.println(answer);
    }
}
