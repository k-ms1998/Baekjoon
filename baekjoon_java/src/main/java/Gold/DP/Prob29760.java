package Gold.DP;

import java.io.*;
import java.util.*;

/**
 * Gold 2(건물 방분하기)
 *
 * https://www.acmicpc.net/problem/29760
 *
 * (완전탐색/DFS 로 풀이시 시간초과 -> 최대 2^100 임)
 * Solution: DP
 * 1. 이전 층에서 방문해야되는 가장 작은 방의 호수(prevX0), 가장 큰 방의 호수(prevX1) 구하기
 * 2. 현재 층에서 가장 작은 방의 호수(minX), 가장 큰 방의 호수(maxX) 구하기
 * 3. 이번 충에서 maxX -> minX로 방문 할때: dp[y][1] = Math.min(prevX0->maxX->minX, prevX1->maxX->minX)
 *    이번 충에서 minX -> maxX로 방문 할때: dp[y][0] = Math.min(prevX0->minX->maxX, prevX1->minX->maxX)
 */
public class Prob29760 {

    static int n, h, w;
    static List<Integer>[] list;
    static int maxH = 0;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        h = Integer.parseInt(st.nextToken());
        w = Integer.parseInt(st.nextToken());

        list = new List[h + 1];
        for(int i = 0; i < h + 1; i++){
            list[i] = new ArrayList<>();
        }
        for(int i = 0; i < n; i++){
            st = new StringTokenizer(br.readLine());
            int y = Integer.parseInt(st.nextToken());
            int x = Integer.parseInt(st.nextToken());
            list[y].add(x);
            maxH = Math.max(maxH, y);
        }

        for(int i = 0; i < h + 1; i++){
            Collections.sort(list[i]);

        }

        int[][] dp = new int[h + 1][2];
        int[][] dx = new int[h + 1][2];
        dx[0][0] = 1;
        dp[0][1] = 1;
        for (int y = 1; y < h + 1; y++) {
            int size = list[y].size();
            if(size == 0){
                dp[y] = dp[y-1];
                dx[y] = dx[y-1];
                continue;
            }
            int maxX = list[y].get(size - 1);
            int minX = list[y].get(0);
            int dif = maxX - minX;

            int prevX0 = dx[y - 1][0];
            int prevX1 = dx[y - 1][1];

            int distA = Math.min(Math.abs(prevX0 - minX) + dp[y - 1][0], Math.abs(prevX1 - minX) + dp[y - 1][1]) + dif; // prevX -> minX -> maxX
            int distB = Math.min(Math.abs(prevX0 - maxX) + dp[y - 1][0], Math.abs(prevX1 - maxX) + dp[y - 1][1]) + dif; // prevX -> maxX -> minX

            dx[y][0] = minX;
            dx[y][1] = maxX;
            dp[y][0] = distB;
            dp[y][1] = distA;
        }

        System.out.println(Math.min(dp[h][0], dp[h][1]) + 100 * (maxH - 1));
    }

}
