package Gold.DP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Gold 4
 *
 * https://www.acmicpc.net/problem/11054
 *
 * Solution: DP
 * 가장 긴 바이토닉 부분 수열 = 가장 긴 증가하는 부분 수열의 길이 + 가장 긴 감소하는 부분 수열의 길이
 *
10
1 5 2 1 4 3 4 5 2 1
 *
 */
public class Prob11054 {

    static int n;
    static int[] num;
    static int[] dpAsc;
    static int[] dpDesc;

    static int ans;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());

        num = new int[n];
        dpAsc = new int[n];
        dpDesc = new int[n];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            num[i] = Integer.parseInt(st.nextToken());
        }

        /**
         * 가장 긴 증가하는 부분 수열 구하기
         * 1. num[x1] 기준, num[0]~num[x2] 중 자기 자신보다 작은 값을 찾기
         * 2. 해당 값에 자기 자신을 더하면 증가하는 수열이 됨 -> dp 값 업데이트
         * ex: num: 1 2 3 1 2 5
         *     dp:  0 1 2 0 1 3 => dp[5]는 dp[2]에 1을 추가한 값(2+1) -> 이때, dp[2]에는 인덱스 0~2까지 포함해서 증가하는 수열의 길이 (수열은 1 2 3)
         *                                                              -> 수열 1 2 3에 5를 추가하면 여전히 증가하는 수열이고, 수열의 길이도 1 증가됨 (수열은 1 2 3 5)
         *  즉, dpAsc[x] 애는 0~x-1 까지의 증가하는 수열에 num[x] 값을 추가해서 증가하는 수열의 길이를 저장하는 것
         */
        for (int x1 = 0; x1 < n; x1++) {
            for(int x2 = 0; x2 < x1; x2++){
                if (num[x1] > num[x2]) {
                    dpAsc[x1] = Math.max(dpAsc[x1], dpAsc[x2] + 1);
                }
            }
        }
//        System.out.println("List.of(dpAsc) = " + List.of(dpAsc));

        /**
         * 가장 긴 감소하는 부분 수열 구하기
         * 1. num[x1] 기준, num[x2]~num[n] 중 자기 자신보다 작은 값을 찾기
         * 2. 해당 값에 자기 자신을 더하면 감소하는 수열이 됨 -> dp 값 업데이트
         * 즉, dpDesc[x] 애는 x~n 까지의 감소하는 수열에 num[x] 값을 추가해서 감소하는 수열의 길이를 저장하는 것
         */
        for (int x1 = n - 1; x1 >= 0; x1--) {
            for(int x2 = x1+1; x2 < n; x2++){
                if (num[x1] > num[x2]) {
                    dpDesc[x1] = Math.max(dpDesc[x1], dpDesc[x2] + 1);
                }
            }
        }
//        System.out.println("List.of(dpAsc) = " + List.of(dpDesc));

        /**
         * 가장 긴 바이토닉 부분 수열 = 가장 긴 증가하는 부분 수열의 길이 + 가장 긴 감소하는 부분 수열의 길이
         * (이때, 둘의 값을 더하고 1을 더한다. Because, 증가하는 부분 수열 & 감소하는 부분 수열 모두 자기 자신을 포함하지 않기 때문에)
         */
        for (int i = 0; i < n; i++) {
            ans = Math.max(ans, dpAsc[i] + dpDesc[i]);
        }
        System.out.println(ans + 1);
    }
}
