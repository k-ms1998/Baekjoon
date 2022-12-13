package Silver;

import java.io.*;
import java.util.*;

/**
 * Silver 5(큰 수 구성하기)
 *
 * https://www.acmicpc.net/problem/18511
 *
 * Solution: Brute Force(Combination)
 * 1. 재귀로 집합 k의 원소들로 만들수 있는 모든 숫자 구하기
 * 2. 구한 숫자들 중에서, n보다 작거나 같은 숫자 중 가장 큰 숫자가 정답
 */
public class Prob18511 {

    static long n;
    static int k;
    static int[] nums;
    static long ans = 0L;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        nums = new int[k];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < k; i++) {
            nums[i] = Integer.parseInt(st.nextToken());
        }

        findCombination(0, 0, 1);
        System.out.println(ans);
    }

    /**
     * 재귀로 집합 k의 원소들로 만들 수 있는 모든 수 구하고,
     * 해당 숫자들 중에서 n보다 작거나 같은 숫자들 중에서 가장 큰 수 구하기
     */
    public static void findCombination(int depth, int num, int digit) {
        if (num <= n) {
            ans = Math.max(ans, num);
        }
        if (digit >= n) {
            return;
        }

        for (int i = 0; i < k; i++) {
            findCombination(depth + 1, num + nums[i] * digit, digit * 10);
        }
    }
}
/*
15 1
9

15 2
9 9
 */