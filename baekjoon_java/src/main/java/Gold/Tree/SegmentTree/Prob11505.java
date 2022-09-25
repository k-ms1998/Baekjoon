package Gold.Tree.SegmentTree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Gold 1(구간 곱 구하기)
 *
 * https://www.acmicpc.net/problem/11505
 *
 * Solution: Segment Tree
 */
public class Prob11505 {

    static int n;
    static int m;
    static int k;

    static int[] num;

    static int treeSize;
    static long[] tree;
    static final int MOD = 1000000007;

    static StringBuilder ans = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        treeSize = 4 * n + 1;
        tree = new long[treeSize];

        num = new int[n + 1];
        num[0] = 1;
        for (int i = 1; i < n + 1; i++) {
            int curNum = Integer.parseInt(br.readLine());
            num[i] = curNum;
        }
        initTree(1, 1, n);

        for (int i = 0; i < m + k; i++) {
            st = new StringTokenizer(br.readLine());

            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            if (a == 1) {
                /**
                 * num[b] 의 값을 변경 할 값인 c로 바꾸고 updateTree() 호출
                 * 이때, updateTree()는 initTree 와 똑같은 방식으로 트리를 쪼개면서 값을 업데이트
                 * But, return 하는 조건이 다름
                 * target 값인 b가 현재 탐색 중인 노드의 구간안에 없으면 return 하도록 함
                 */
                num[b] = c;
                updateTree(1, b, 1, n);
            } else {
                long curAns = findMul(1, b, c, 1, n) % MOD;
                ans.append(curAns).append("\n");
            }
        }

        System.out.println(ans);
    }

    /*
    1 0 3 4 5
    -> 2 ~ 5 의 곱 == 0
    -> 3 ~ 5 의 곱 == 60
    if dp를 이용해서 ,  4 ~ 5 의 곱을 dp[5] / dp[3] 으로 하면 0 이 나옴. 하지만, 옳은 값은 20
    (dp = {1, 0, 0, 0, 0} 이 되기 때문에 dp[5]/dp[3] 을 하면 0이 나옴 (DivideByZero Error 을 따로 해결한다고 기정했을때))
        => 그러므로, 2042문제(구간 합 구하기) 처럼 dp로 구간의 곱 구하기 X
     */

    /**
     * 1~5까지의 구간의 곱 = 1~3 까지의 구간의 곱 * 4~5까지의 구간의 곱
     *  -> 1~3까지의 구간의 곱 = 1~2까지의 구간의 곱 * 3~3까지의 구간의 곱
     *      -> 1~2까지의 구간의 곱 = 1~1까지의 구간의 곱 * 2~2까지의 구간의 곱
     *  -> 4~5까지의 구간의 곱 = 4~4까지의 구간의 곱 * 5~5까지의 구간의 곱
     */
    public static long initTree(int idx, int s, int e) {
        if (idx > treeSize || s > e) {
            return 1L;
        } else if (s == e) {
            tree[idx] = num[s];
            return tree[idx];
        }

        int mid = (s + e) / 2;
        /**
         * 1~5까지의 구간의 곱 = 1~3 까지의 구간의 곱 * 4~5까지의 구간의 곱
         */
        long idxNum = (initTree(2 * idx, s, mid) * initTree(2 * idx + 1, mid + 1, e)) % MOD;
        tree[idx] = idxNum;

        return idxNum;
    }

    public static long updateTree(int idx, int target, int s, int e) {
        /**
         * target 값이 현재 곱의 구간 안에 없으면 return 함
         * Because, target 값이 현재 곱의 구간 안에 없으면 해당 구간의 곱의 값이 변하지 않기 때문에
         */
        if (target < s || target > e || idx > treeSize) {
            return tree[idx];
        } else if (s == e) {
            tree[idx] = num[s];
            return tree[idx];
        }

        int mid = (s + e) / 2;
        long updatedNum = (updateTree(2 * idx, target, s, mid) * updateTree(2 * idx + 1, target, mid + 1, e)) % MOD;
        tree[idx] = updatedNum;

        return updatedNum;
    }

    public static long findMul(int idx, int targetS, int targetE, int s, int e) {
        if ((s < targetS && e < targetS) || (s > targetE && e > targetE)) {
            return 1L;
        } else if (targetS <= s && targetE >= e) {
            return tree[idx];
        }

        int mid = (s + e) / 2;
        return (findMul(2 * idx, targetS, targetE, s, mid) * findMul(2 * idx + 1, targetS, targetE, mid + 1, e)) % MOD;
    }
}
