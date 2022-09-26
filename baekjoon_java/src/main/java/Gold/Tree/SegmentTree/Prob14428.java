package Gold.Tree.SegmentTree;

import java.io.*;
import java.util.*;

/**
 * Gold 1(수열과 쿼리 16)
 *
 * https://www.acmicpc.net/problem/14428
 *
 * Solution: Segment Tree
 */
public class Prob14428 {

    static int n;
    static long[] num;
    static int m;
    /**
     * Tree 에는 해당 범위의 가장 작은 값의 인덱스를 가짐
     * -> ex: 10 9 8 7 6 이고, 범위가 1~5일때, 가장 작은 값은 6이며 해당 인덱스는 5 => 5를 tree 에 저장
     */
    static int[] tree;

    static int treeSize;
    static StringBuilder ans = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        treeSize = 4 * n;
        tree = new int[treeSize + 1];

        num = new long[n + 2];
        num[n + 1] = Long.MAX_VALUE;
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i < n + 1; i++) {
            num[i] = Long.parseLong(st.nextToken());
        }
        initTree(1, 1, n);

        m = Integer.parseInt(br.readLine());
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());

            int q1 = Integer.parseInt(st.nextToken());
            int q2 = Integer.parseInt(st.nextToken());
            if (q1 == 1) {
                long q3 = Long.parseLong(st.nextToken());
                num[q2] = q3;
                updateTree(1, q2, 1, n);
            } else {
                int q3 = Integer.parseInt(st.nextToken());
                int ansIdx = findMin(1, q2, q3, 1, n);
                ans.append(ansIdx).append("\n");
            }
        }

        System.out.println(ans);
    }

    public static int initTree(int idx, int s, int e) {
        if (s > e || idx > treeSize) {
            return n + 1;
        }else if(s == e){
            tree[idx] = s;
            return s;
        }

        int mid = (s + e) / 2;
        int idxA = initTree(2 * idx, s, mid);
        int idxB = initTree(2 * idx + 1, mid + 1, e);
        long numA = num[idxA];
        long numB = num[idxB];

        if (numA == numB) {
            tree[idx] = idxA < idxB ? idxA : idxB;
        } else {
            tree[idx] = numA < numB ? idxA : idxB;
        }

        return tree[idx];
    }

    /**
     * ex: 1~5의 최솟값을 구하고, target == 4
     * 1~5
     * -> 1~3
     *  -> 1~2
     *      -> 1~1
     *      -> 2~2
     *  -> 3~3
     * -> 4~5
     *  -> 4~4
     *  -> 5~5
     * => 4를 포함한 노드들의 최솟값들은 변경 대상일 수 있음
     * But, 4를 포함하지 않는 노드에서 1~5의 최솟값이 있을 수 있기 때문에 4를 포함하지 않으면 tree[idx] 반환
     */
    public static int updateTree(int idx, int target, int s, int e) {
        if (s == e) {
            tree[idx] = s;
        } else if (target >= s && target <= e) {
            int mid = (s + e) / 2;
            int idxA = updateTree(2 * idx, target, s, mid);
            int idxB = updateTree(2 * idx + 1, target, mid + 1, e);
            long numA = num[idxA];
            long numB = num[idxB];

            if (numA == numB) {
                tree[idx] = idxA < idxB ? idxA : idxB;
            } else {
                tree[idx] = numA < numB ? idxA : idxB;
            }
        }

        return tree[idx];

//        if (target < s || target > e) {
//            return tree[idx];
//        } else if (s == e) {
//            tree[idx] = s;
//            return s;
//        }
//
//        int mid = (s + e) / 2;
//        int idxA = updateTree(2 * idx, target, s, mid);
//        int idxB = updateTree(2 * idx + 1, target, mid + 1, e);
//        long numA = num[idxA];
//        long numB = num[idxB];
//
//        if (numA == numB) {
//            tree[idx] = idxA < idxB ? idxA : idxB;
//        } else {
//            tree[idx] = numA < numB ? idxA : idxB;
//        }
//        return tree[idx];
    }

    public static int findMin(int idx, int targetS, int targetE, int s, int e) {
        if ((s < targetS && e < targetS) || (s > targetE && e > targetE)) {
            return n + 1;
        } else if (s >= targetS && e <= targetE) {
            return tree[idx];
        }

        int mid = (s + e) / 2;
        int idxA = findMin(2 * idx, targetS, targetE, s, mid);
        int idxB = findMin(2 * idx + 1, targetS, targetE, mid + 1, e);
        long numA = num[idxA];
        long numB = num[idxB];

        if (numA == numB) {
            return idxA < idxB ? idxA : idxB;
        } else {
            return numA < numB ? idxA : idxB;
        }
    }
}
