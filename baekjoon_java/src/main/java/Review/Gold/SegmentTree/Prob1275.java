package Review.Gold.SegmentTree;

import java.io.*;
import java.util.*;

/**
 * Gold 1(커피숍 2)
 * https://www.acmicpc.net/problem/1275
 * Solution: 세그먼트 트리
 * 기본적인 세그먼트 트리 문제
 */
public class Prob1275 {

    static int n, q;
    static long[] num;
    static long[] tree;
    static StringBuilder ans = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        q = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        num = new long[n + 1];
        for (int i = 1; i < n + 1; i++) {
            num[i] = Long.parseLong(st.nextToken());
        }

        tree = new long[4 * n + 1];
        createTree(1, 1, n);

        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            if(x > y){
                int tmp = x;
                x = y;
                y = tmp;
            }
            int a = Integer.parseInt(st.nextToken());
            long b = Long.parseLong(st.nextToken());

            ans.append(findSum(1, 1, n, x, y)).append("\n");
            long curNum = num[a];
            long dif = b - curNum;
            num[a] = b;
            updateTree(1, 1, n, a, dif);
        }

        System.out.println(ans);
    }

    /**
     * 세그먼트 트리 생성
     */
    public static long createTree(int idx, int left, int right) {
        if (left == right) {
            tree[idx] = num[left];
            return tree[idx];
        }

        int mid = (left + right) / 2;
        tree[idx] = createTree(2 * idx, left, mid) + createTree(2 * idx + 1, mid + 1, right);

        return tree[idx];
    }

    /**
     * 세그먼트 트리 부분한 구하기
     * 1. 현재 left랑 right가 합을 구할려는 범위(targetL <= x <= targetR) 이면 해당 값 그대로 반환
     * 2. 만약 범위에 없는데 left == right이면, 트리의 끝에 도착했고 범위에 없기 때문에 0 반환
     * 3. left가 targetR 보다 크거나, right가 targetL보다 작으면 아무리 범위를 줄여도 합을 구할려는 범위 안에 들어갈 수 없기 때문에 0 반환
     *  ex: 현재 left = 4, right = 5라서 4~5의 합을 보고 있고, 합을 구할려는게 1~3의 합일때, 해당 위치로부터 아무리 깊게 트리를 들어가도 워하는 범위인 1~3안에 들어올 수 없음
     * 4. 위의 3경우를 모두 만족하지 않으면 적어도 left 또는 right 둘 중 하나는 원하는 범위 안에 있으므로 더 깊게 들어가면 원하는 범위 안에 들수 있음
     *  ex: 현재 left = 1, right = 3라서 1~3의 합을 보고 있고, 합을 구할려는게 2~3의 합일때 right가 2~3안에 들어가기 때문에 더 깊이 탐색하면 2~3안에 들어가는 범위를 찾을 수도 있음
     */
    public static long findSum(int idx, int left, int right, int targetL, int targetR) {
        if (left >= targetL && right <= targetR) {
            return tree[idx];
        } else if (left == right) {
            return 0L;
        } else if (left > targetR || right < targetL) {
            return 0L;
        }

        int mid = (left + right) / 2;
        return findSum(2 * idx, left, mid, targetL, targetR)
                + findSum(2 * idx + 1, mid + 1, right, targetL, targetR);
    }

    /**
     * 세그먼트 트리 업데이트
     * 1. 현재 left랑 right 사이에 target이 있음
     *  -> left~right의 범위의 합에 업데이트 시킬려는 값이 포함되어 있음
     *      -> 범위의 합 업데이트 (dif 만큼 추가해줌)
     */
    public static void updateTree(int idx, int left, int right, int target, long dif) {
        if(target < left || target > right){
            return;
        }
        if (left == right) {
            tree[idx] += dif;
            return;
        }
        tree[idx] += dif;
        int mid = (left + right) / 2;
        updateTree(2 * idx, left, mid, target, dif);
        updateTree(2 * idx + 1, mid + 1, right, target, dif);
    }

}
/*
5 3
1 2 3 4 5
1 4 1 1
2 3 3 1
3 5 4 1
 */