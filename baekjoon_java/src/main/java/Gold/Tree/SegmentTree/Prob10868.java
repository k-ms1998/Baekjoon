package Gold.Tree.SegmentTree;

import java.io.*;
import java.util.*;

/**
 * Gold 1(최솟값)
 *
 * https://www.acmicpc.net/problem/10868
 * 
 * Solution: Segment Tree
 */
public class Prob10868 {

    static int n, m;
    static int[] num;

    static int[] tree;

    static StringBuilder ans = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        num = new int[n + 1];
        tree = new int[4 * n + 1];
        for (int i = 1; i < n + 1; i++) {
            num[i] = Integer.parseInt(br.readLine());
        }

        createTree(1, n, 1);
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());
            ans.append(findTree(1, n, 1, s, d)).append("\n");
        }

        System.out.println(ans);
    }

    public static int findTree(int s, int d, int idx, int ts, int td) {
//        System.out.println("s = " + s + ", d = " + d + ", ts = " + ts + ", td = " + td);
        if (s > d) {
            /**
             * s > d 이면 예외 사항이므로 MXA_VALUE 반환
             */
            return Integer.MAX_VALUE;
        }else if (s >= ts && d <= td) {
            /**
             * 현재 s랑 d 값이 target 범위 안에 있을때는 tree[idx] 값 반환
             */
            return tree[idx];
        }else if (d < ts || s > td) {
            /**
             * d가 ts 보다 작거나, s가 td 보다 크면, 아무리 s이상 d이하의 값으로 범위를 쪼개도 s 또는 d 중 하나라도 target 범위 (ts <= x <= td) 에 들어오는 경우가 없음
             * 그러므로, 예외 사항이므로 MAX_VALUE 반환
             */
            return Integer.MAX_VALUE;
        }

        /**
         * s 랑 d 모두 target 범위 안에 없더라도, s가 ts 보다 작거나 같거나, d가 td 보다 크거나 같으면,
         * s이상 d이하의 값으로 범위를 쪼개면 target 범위 내에 들어오는 경우들이 생기기 때문에 범위를 쪼개서 다시 탐색
         */
        int mid = (s + d) / 2;
        return Math.min(findTree(s, mid, 2 * idx, ts, td), findTree(mid + 1, d, 2 * idx + 1, ts, td));
    }

    /**
     * s == d 가 될때까지 가서 tree[idx]에 값을 저장해주고, 다시 트리의 하위에서 상위로 올라가면서 값들을 비교해서 최솟값을 저장
     */
    public static int createTree(int s, int d, int idx) {
        if(s == d){
            tree[idx] = num[s];
            return num[s];
        }

        int mid = (s + d) / 2;
        tree[idx] = Math.min(createTree(s, mid, 2 * idx), createTree(mid + 1, d, 2 * idx + 1));

        return tree[idx];
    }

    public static void printTree() {
        for (int i = 1; i < tree.length; i++) {
            System.out.print(tree[i] + " ");
        }
        System.out.println();
    }
}
/*
10 4
75
30
100
38
50
51
52
20
81
5
1 10
3 5
6 9
8 10
 */