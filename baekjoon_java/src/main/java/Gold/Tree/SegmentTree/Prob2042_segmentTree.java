package Gold.Tree.SegmentTree;

import java.io.*;
import java.util.*;

/**
 * Gold 2(구간 합 구하기)
 *
 * https://www.acmicpc.net/problem/2042
 *
 * Solution: Segment Tree
 */
public class Prob2042_segmentTree {

    static int n;
    static int m;
    static int k;

    static long[] num;
    static long[] dp;

    /**
     * Segment Tree:
     * https://m.blog.naver.com/ndb796/221282210534
     *
     * 1. Root(idx = 1) 에 num[1] ~ num[n] 까지의 누적 합 저장
     * 2. Root 의 left child(idx = 1*2) 에는 num[1] ~ num[mid] 까지의 누적 합 저장 (mid = (1+n)/2)
     * 3. Root 의 right child(idx = 1*2 + 1) 에는 num[mid + 1] ~ num[n] 까지의 누적 합 저장 (mid = (1+n)/2)
     * 4. 모든 노드에 대해서 left child 랑 right child 에 저장할 값 구하기 (2~3 반복)
     *
     */
    static long[] tree;
    static int treeSize;

    static StringBuilder ans = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        num = new long[n + 1];
        dp = new long[n + 1];

        treeSize = 4 * n + 1;
        tree = new long[treeSize];
        for (int i = 1; i < n + 1; i++) {
            num[i] = Long.parseLong(br.readLine());
            dp[i] = dp[i - 1] + num[i];
        }
        initTree(1, 1, n);

        for(int i = 0; i < m + k; i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());

            if(a == 1){
                int b = Integer.parseInt(st.nextToken());
                long c = Long.parseLong(st.nextToken());

                long diff = c - num[b];
                num[b] = c;
                updateTree(1, b, diff, 1, n);
            }else{
                int b = Integer.parseInt(st.nextToken());
                int c = Integer.parseInt(st.nextToken());

                long sum = findSum(1, b, c, 1, n);
                ans.append(sum).append("\n");
            }

        }

        System.out.println(ans);
    }

    /**
     * tree[idx] 에는 num[s] ~ num[e] 까지의 합 저장
     * @param idx
     * @param s
     * @param e
     */
    public static void initTree(int idx, int s, int e) {
        if (s > e) {
            return;
        } else if (s == e) {
            /**
             * s == e -> num[s] ~ num[e] == num[s] ~ num[s] == num[s]
             */
            tree[idx] = num[s];
            return;
        }
        tree[idx] = dp[e] - dp[s - 1];

        /**
         * n == 5:
         *                      1~5(1)  => num[1]에서 num[5]까지의 합 (tree 에서 현재 노드의 idx 값)(left child idx = 2*idx, right child idx = 2*idx+1)
         *          1~3(2)                  4~5(3)
         *      1~2(4)   3~3(5)     4~4(6)          5~5(7)
         * 1~1(12) 2~2(13)
         */
        int mid = (s + e) / 2;
        initTree(2 * idx, s, mid);
        initTree(2 * idx + 1, mid + 1, e);
    }

    /**
     * tree 의 값 업데이트
     */
    public static void updateTree(int idx, int target, long diff, int s, int e) {
        if (target < s || target > e || idx > treeSize) {
            return;
        }
        /**
         * target 이 범위 안에 있으면 값 업데이트
         * -> 범위 안에 있다 == target 이 s이상, e 이하의 값이다
         *  -> target 이 범위 안에 있으면, 변화된 num[target] 값에 의해 num[target] 이 합에 들어 있는 값들이 모두 영향을 받고 값들을 업데이트 시켜줘야함
         */
        tree[idx] += diff;

        int mid = (s + e) / 2;
        updateTree(2 * idx, target, diff, s, mid);
        updateTree(2 * idx + 1, target, diff, mid + 1, e);
    }

    public static long findSum(int idx, int targetS, int targetE, int s, int e) {
        /**
         * ex:
         * targetS = 3, targetE = 5 일때(num[3]~num[5] 의 합을 구해야 됨):
         *  -> s = 4, e = 5 이면 num[4] ~ num[5] 의 합을 구한 것 => 더 이상 쪼갤 필요 없음
         *  -> s = 1, e = 3 이면 num[1] ~ num[3] 의 합을 구한 것 => 우리가 필요한 것은 num[3]~ num[3] 이므로, 더 쪼개야됨
         *      -> s = 1, e = 2 && s = 3 , e = 3 으로 쪼갤 수 있음
         *          -> s = 3 , e = 3 이면 num[3]~ num[3] && (targetS <= s && targetE >= e) 조건 만족 => tree[idx] 반환
         *          -> s = 1, e = 2 이면 num[1]~ num[2] -> 최종 값(num[3]~num[5] 의 합)을 구하는데 필요 X 
         *                                                  -> (targetS > s && targetS > e || targetE < s && targetE < e) => 0 반환
         */
        if (targetS <= s && targetE >= e) {
            return tree[idx];
        }else if(targetS > s && targetS > e || targetE < s && targetE < e){
            return 0L;
        }

        int mid = (s + e) / 2;
        /**
         * 1~5까지의 합 = 1~3까지의 합 + 4~5까지의 합
         *            = findSum(2 * idx, targetS, targetE, s, mid) + findSum(2 * idx + 1, targetS, targetE, mid + 1, e)
         */
        return findSum(2 * idx, targetS, targetE, s, mid) + findSum(2 * idx + 1, targetS, targetE, mid + 1, e);
    }

    public static void printTree() {
        for (int k = 0; k < tree.length; k++) {
            System.out.println("tree[" + k + "] = " + tree[k]);
        }
        System.out.println("================================");
    }
}
