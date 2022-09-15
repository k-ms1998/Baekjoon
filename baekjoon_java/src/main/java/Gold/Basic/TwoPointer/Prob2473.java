package Gold.Basic.TwoPointer;

import java.io.*;
import java.util.*;

/**
 * Gold 3(세 용액)
 *
 * https://www.acmicpc.net/problem/2473
 *
 * Solution: Two Pointer
 */
public class Prob2473 {

    static int n;
    /**
     * 용액의 값이 -1,000,000,000 이상, 1,000,000,000 이하이므로 long 으로 선언
     */
    static long[] num;

    static StringBuilder ans = new StringBuilder();

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        num = new long[n];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            num[i] = Long.parseLong(st.nextToken());
        }
        /**
         * 값들을 오름차순으러 정렬
         * Two Pointer 로 풀기 위해서는 정렬이 필수!
         */
        Arrays.sort(num);
        
        long[] fluids = new long[3]; // 특성값이랑 가장 가까운 값을 만드는 용액들의 값 저장
        long closestNum = Long.MAX_VALUE;
        boolean found = false;
        /**
         * Two Pointer + 1
         * 총 3개의 용액을 섞음 => Three Pointer
         * 이때, 포인터 하나는 고정 시킴(i)
         * 그리고 나머지 두개의 포인터(left, right) 을 그냥 Two Pointer 문제랑 동일하게 움직이면서 용액들을 섞은 값 찾기
         * => O(N logN)
         */
        for (int i = 0; i < n - 2; i++) {
            if (found) {
                break;
            }
            long tmp = num[i];

            /**
             * left 와 right 로 일반적인 Two Pointer 문제랑 동일하게 풀이
             */
            int left = i + 1;
            int right = n - 1;
            while (left < right) {
                long curSum = tmp + num[left] + num[right];
                if(Math.abs(curSum) < Math.abs(closestNum)){
                    closestNum = curSum;
                    fluids[0] = num[i];
                    fluids[1] = num[left];
                    fluids[2] = num[right];
                }

                if(curSum > 0){
                    /**
                     * curSum 이 0보다 크면, curSum 이 0과 더 가까워 질려면 일단 curSum 을 감소 시켜야됨
                     * -> 그러므로, right 를 왼쪽으로 한칸 이동
                     */
                    right--;
                } else if (curSum < 0) {
                    /**
                     * curSum 이 0보다 크면, curSum 이 0과 더 가까워 질려면 일단 curSum 을 증가 시켜야됨
                     * -> 그러므로, left 를 오른쪽으로 한칸 이동
                     */
                    left++;
                } else {
                    /**
                     * curSum == 0 이면 특성값(0)에 가장 가까운 값이기 때문에 더 이상 탐색할 필요 X
                     */
                    found = true;

                    break;
                }
            }
        }

        ans.append(fluids[0]).append(" ").append(fluids[1]).append(" ").append(fluids[2]);

        System.out.println(ans);
    }
}
