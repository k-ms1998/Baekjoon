package Gold.Tree.SegmentTree;

import java.io.*;
import java.util.*;

/**
 * Gold 1(최솟값 최댓값)
 *
 * https://www.acmicpc.net/problem/2357
 *
 * Solution: Segment Tree
 * -> Segment Tree 로 a~b 사이의 최댓값과 최솟값을 노드로 저장하기
 */
public class Prob2357 {

    static int n;
    static int m;
    static long[] num;

    static Node[] tree;
    static int treeSize;

    static long tmpMin;
    static long tmpMax;
    static StringBuilder ans = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        num = new long[n + 1];
        for (int i = 1; i < n + 1; i++) {
            num[i] = Long.parseLong(br.readLine());
        }

        treeSize = 4 * n + 1;
        tree = new Node[treeSize];
        initTree(1, 1, n);

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            tmpMin = Long.MAX_VALUE;
            tmpMax = 0L;
            findMaxMin(1, 1, n, a, b);
//            System.out.println("a = " + a + ", b = " + b);
            ans.append(tmpMin).append(" ").append(tmpMax).append("\n");
        }

        System.out.println(ans);
    }

    public static void initTree(int idx, int s, int e) {
        if (idx > treeSize || s > e) {
            return;
        }
        tree[idx] = createNode(s, e);

        int mid = (s + e) / 2;
        initTree(2 * idx, s, mid);
        initTree(2 * idx + 1, mid + 1, e);
    }

    /**
     * s~e 구간에서 가장 큰 값과 가장 작은 값 찾기
     */
    public static Node createNode(int s, int e) {
        long curMax = 0L;
        long curMin = Long.MAX_VALUE;

        for (int i = s; i <= e; i++) {
            curMax = Math.max(curMax, num[i]);
            curMin = Math.min(curMin, num[i]);
        }

        return new Node(curMin, curMax);
    }

    /**
     * Segment Tree 를 탐색해서 각 구간에서 최대값과 최솟값 구하기
     * @param idx: Tree 에서 노드의 인덱스 값
     * @param s: 현재 구간의 시작 지점  
     * @param e: 현재 구간의 끝 지점
     * @param targetS: 찾는 구간의 시작 지점
     * @param targetE: 찾는 구간의 끝 지점
     */
    public static void findMaxMin(int idx, int s, int e, int targetS, int targetE) {
        if ((targetS > s && targetS > e) || (targetE < s && targetE < e)) {
            return;
        } else if (targetS <= s && targetE >= e) {
            Node curNode = tree[idx];
            tmpMin = Math.min(tmpMin, curNode.minNum);
            tmpMax = Math.max(tmpMax, curNode.maxNum);
            
            return;
        }

        int mid = (s + e) / 2;
        findMaxMin(2 * idx, s, mid, targetS, targetE);
        findMaxMin(2 * idx + 1, mid + 1, e, targetS, targetE);
    }

    static class Node{
        long minNum;
        long maxNum;

        public Node(long minNum, long maxNum) {
            this.minNum = minNum;
            this.maxNum = maxNum;
        }

        @Override
        public String toString() {
            return "Node = {min = " + minNum + ", max = " + maxNum + "}";
        }
    }
}
