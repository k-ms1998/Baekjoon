package Gold.Tree.SegmentTree;

import java.io.*;
import java.util.*;

/**
 * Gold 1(커피숍2)
 *
 * https://www.acmicpc.net/problem/1275
 *
 * Solution: Segment Tree
 * 기본적인 세그먼트 트리 문제
 */
public class Prob1275 {

    static int n, q;
    static long[] num;
    static long[] tree;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        q = Integer.parseInt(st.nextToken());

        num = new long[n + 1];
        tree = new long[4 * n + 1];
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i < n + 1; i++) {
            num[i] = Long.parseLong(st.nextToken());
        }
        createTree(1, n, 1);

        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int a = Integer.parseInt(st.nextToken());
            long b = Long.parseLong(st.nextToken());

            int l = x < y ? x : y;
            int r = x > y ? x : y;
            ans.append(findAnswer(1, n, 1, l, r)).append("\n");

            long diff = b - num[a];
            num[a] = b;
            updateTree(1, n, 1, a, diff);
        }

        System.out.println(ans);
    }

    public static long createTree(int left, int right, int idx) {
        if(left == right){
            tree[idx] = num[left];
            return tree[idx];
        }

        int mid = (left + right) / 2;
        tree[idx] = createTree(left, mid, 2 * idx) + createTree(mid + 1, right, 2 * idx + 1);

        return tree[idx];
    }

    public static void updateTree(int left, int right, int idx, int target, long diff) {
        /**
         * 업데이트 할때, left랑 right 사이에 target 값이 있을때만 업데이트
         *
         */
        if ((left <= target && target <= right)) {
            tree[idx] += diff;
            /**
             * left랑 right랑 같으면 더 이상 깊이 탐색할 필요가 없기 때문에 return
             */
            if (left == right) {
                return;
            }
            int mid = (left + right) / 2;
            updateTree(left, mid, 2 * idx, target, diff);
            updateTree(mid + 1, right, 2 * idx + 1, target, diff);
        }
    }

    public static long findAnswer(int left, int right, int idx, int l, int r) {
        if (right < l) {
            return 0;
        }
        if (left > r) {
            return 0;
        }

        if(left >= l && r >= right){
            return tree[idx];
        }

        int mid = (left + right) / 2;
        return findAnswer(left, mid, 2 * idx, l, r) + findAnswer(mid + 1, right, 2 * idx + 1, l, r);
    }
}