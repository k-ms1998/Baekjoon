package Gold.Greedy;

import java.io.*;
import java.util.*;

/**
 * Gold 3(줄 세우기)
 *
 * https://www.acmicpc.net/problem/7570
 *
 * Solution: LIS(Longest Increasing Sequence)
 * 1. 1씩 가장 긴 증가하는 수열 찾기
 * 2. 해당 수열에 속해 있는 숫자들은 위치를 바꿀 필요 없음
 *  -> 그러므로, 해당 수열에 속해 있지 않는 숫자들만 위치를 바꾸면 됨
 *      -> answer = n - (해당 수열에 속해 있지 않는 숫자들의 수)
 *
 *  EX:
 *  5 2 4 1 3
 *  (2, 3)이 1씩 증가하는 가장 긴 수열 => answer = 5 - 2 = 3
 *
 *  count[5] = count[2] + 1 = 1;
 *  count[2] = count[1] + 1 = 1;
 *  count[4] = count[3] + 1 = 1;
 *  count[1] = count[0] + 1 = 1;
 *  count[3] = count[2] + 1 = 2;
 */
public class Prob7570 {

    static int n;
    static int[] count;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        count = new int[n + 1];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            int num = Integer.parseInt(st.nextToken());
            count[num] = count[num - 1] + 1;
        }
        int max = 0;
        for(int i = 1; i < n + 1; i++){
            max = Math.max(max, count[i]);
        }

        System.out.println(n - max);
    }

}
