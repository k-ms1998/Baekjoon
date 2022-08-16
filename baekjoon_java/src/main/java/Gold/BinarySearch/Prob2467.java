package Gold.BinarySearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Gold 5
 *
 * https://www.acmicpc.net/problem/2467
 *
 * Solution: Binary Search
 */
public class Prob2467 {

    static int n;
    static int[] fluid;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());

        fluid = new int[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            fluid[i] = Integer.parseInt(st.nextToken());
        }
        /**
         * 오름차순으로 정렬
         */
        Arrays.sort(fluid);

        int l = 0;
        int r = n - 1;
        int prevNum = Math.abs(fluid[r] + fluid[l]);
        int ansLNum = fluid[l];
        int ansRNum = fluid[r];
        while (r > l) {
            int lNum = fluid[l];
            int rNum = fluid[r];
            int num = lNum + rNum;
            int curNum = Math.abs(num);

            /**
             * 현재 용액의 특성값과, 이전에 탐색한 특성값 중 0에 가장 가까운 값과 비교 (prevNum 에는 특성값이 0에 가장 가까운 값을 저장)
             */
            if (curNum <= prevNum) {
                ansLNum = fluid[l];
                ansRNum = fluid[r];
                prevNum = curNum;
            }

            /**
             * fluid는 현재 오름차순으로 정렬되어 있음
             * 
             * 현재 두 용액의 합이 0 미만: 0에 더 가깝게 만들기 위해서는 l을 한 칸 오른쪽으로 옮겨야 함 -> 만약에, r 칸을 줄이면 num의 값은 0으로 부터 더 멀어짐
             * 현재 두 용액의 합이 0 초과: 0에 더 가깝게 만들기 위해서는 r을 한 칸 왼쪽으로 옮겨야 함 -> 만약에, l 칸을 줄이면 num의 값은 0으로 부터 더 멀어짐
             */
            if (num < 0) {
                l++;
            } else if (num > 0) {
                r--;
            } else {
                break;
            }
        }

        System.out.println(ansLNum + " " + ansRNum);
    }
}
