package Silver.BinarySearch;

import java.io.*;
import java.util.*;

/**
 *
 * Silver 2(나무 자르기)
 *
 * https://www.acmicpc.net/problem/2805
 *
 * Solution: Binary Search(Upper Bound)
 */
public class Prob2805 {

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        long m = Long.parseLong(st.nextToken());

        st = new StringTokenizer(br.readLine());
        int[] trees = new int[n];
        int maxTree = 0;
        for (int i = 0; i < n; i++) {
            trees[i] = Integer.parseInt(st.nextToken());
            maxTree = Math.max(maxTree, trees[i]);
        }

        /**
         * 최대 높이, 즉 최댓값을 찾는것이기 때문에 upper bound
         * 이분 탐색으로 자르기 시작할 높이 찾기
         * mid 값으로 부터 모든 나무들을 잘랐을때, 총 얻게 되는 나무의 길이 계산
         * 해당 길이와 필요한 길이(m) 과 비교해서, left 또는 right 값 업데이트 해주기
         */
        long left = 0;
        long right = maxTree;
        while(left <= right){
            long mid = (left + right) / 2;

            long cutTrees = 0L;
            for(int i = 0; i < n; i++){
                if(trees[i] > mid){
                    cutTrees += (trees[i] - mid);
                }
            }

            if(cutTrees >= m){
                left = mid + 1;
            }else{
                right = mid - 1;
            }
        }

        /**
         * upper bound 이므로, right 값이 정답
         */
        System.out.println(right);
    }
}
/*
4 66
20 15 10 25
 */