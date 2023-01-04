package Gold;

import java.io.*;
import java.util.*;

/**
 * Gold 2(로봇 청소기)
 * 
 * https://www.acmicpc.net/problem/4991
 * 
 * Solution: BFS + DFS + Brute Force
 * 모든 더러운 칸을 청소해야 하는데, 이미 방문했던 좌표를 다시 방문할 수 있음
 * 그러므로, 단순히 BFS 만으로 최단 거리를 찾아서 풀이하는 것이 불가능
 * 
 * 1. BFS로 시작 지점과 모든 더러운 칸, 그리고 각 더러운 칸들에 대해서 다른 더러운 칸들까지의 최단 거리 구하기
 *  -> 1-1. 시작 지점 및 각 더러운 칸들에서 시작해서, 모든 칸들까지의 최단 거리 계산
 * 2. 시작 지점에서 시작해서, 모든 경우의 수에 대해서 모든 더러운 칸을 청소하는데 최단 거리를 구하기
 *  2-1. Combination 으로, 청소하는 순서의 모든 조합데 대해서 최단 거리를 구한 후, 그 중 가장 작은 값이 최종 답 (DFS)
 */
public class Prob4991 {

    static int finalCost = Integer.MAX_VALUE;
    static final int INF = 1000000000;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder ans = new StringBuilder();

        int[] dx = {1, -1, 0, 0};
        int[] dy = {0, 0, 1, -1};

        while(true) {
            st = new StringTokenizer(br.readLine());
            int w = Integer.parseInt(st.nextToken());
            int h = Integer.parseInt(st.nextToken());
            if (w == 0 && h == 0) {
                break;
            }

            char[][] grid = new char[h][w];
            int dirtyCnt = 1;
            Info[] dirtyArr = new Info[11]; // dirtyArr[0] = 청소기의 시작 지점; 1~dirtyCnt 는 각 더러운 칸의 위치
            for (int y = 0; y < h; y++) {
                String curRow = br.readLine();
                for (int x = 0; x < w; x++) {
                    char c = curRow.charAt(x);
                    if (c == '*') {
                        dirtyArr[dirtyCnt] = new Info(x, y, 0);
                        ++dirtyCnt;
                    } else if (c == 'o') {
                        dirtyArr[0] = new Info(x, y, 0); // 로봇의 시작 지점
                    }
                    grid[y][x] = c;
                }
            }

            /**
             * 각 지점에 대해서 다른 모든 지점까지의 최단 거리 저장
             * dist[i][j] -> i 지점에서 j 지점까지의 최단 거리 저장
             */
            boolean unreachable = false;
            int[][] dist = new int[dirtyCnt][dirtyCnt];
            for (int i = 0; i < dirtyCnt; i++) {
                Info base = dirtyArr[i];

                int[][] tmp = new int[h][w];
                for(int ty = 0; ty < h; ty++){
                    for(int tx = 0; tx < w; tx++){
                        tmp[ty][tx] = INF;
                    }
                }
                tmp[base.y][base.x] = 0;

                Deque<Info> queue = new ArrayDeque<>();
                queue.offer(new Info(base.x, base.y, 0));
                while(!queue.isEmpty()){
                    Info cur = queue.poll();
                    int x = cur.x;
                    int y = cur.y;
                    int d = cur.d;

                    for (int j = 0; j < 4; j++) {
                        int nx = x + dx[j];
                        int ny = y + dy[j];

                        if (nx < 0 || ny < 0 || nx >= w || ny >= h) {
                            continue;
                        }
                        if(grid[ny][nx] == 'x'){
                            continue;
                        }

                        if (tmp[ny][nx] > d + 1) {
                            tmp[ny][nx] = d + 1;
                            queue.offer(new Info(nx, ny, d + 1));
                        }
                    }
                }

                for (int j = i + 1; j < dirtyCnt; j++) {
                    Info next = dirtyArr[j];
                    int nx = next.x;
                    int ny = next.y;
                    dist[i][j] = tmp[ny][nx];
                    dist[j][i] = tmp[ny][nx];
                    if (dist[i][j] == INF) {
                        unreachable = true;
                        break;
                    }
                }
            }

            finalCost = INF;
            if(!unreachable){
                boolean visited[] = new boolean[dirtyCnt];
                for (int i = 1; i < dirtyCnt; i++) {
                    visited[0] = true;
                    calculateDist(2, i, dirtyCnt, dist[0][i], visited, dist);
                }
            }

            ans.append(finalCost == INF ? -1 :finalCost).append("\n");
        }

        System.out.println(ans);
    }

    /**
     * 청소 가능한 모든 순서의 조합에 대해서 최단거리 계산
     */
    public static void calculateDist(int depth, int idx, int dirtyCnt, int curCost, boolean[] visited, int[][] dist) {
        if (depth == dirtyCnt) {
            finalCost = Math.min(finalCost, curCost);
            return;
        }
        visited[idx] = true;
        for (int i = 0; i < dirtyCnt; i++) {
            if (!visited[i]) {
                calculateDist(depth + 1, i, dirtyCnt, curCost + dist[idx][i], visited, dist);
            }
        }
        visited[idx] = false;
    }

    public static class Info{
        int x;
        int y;
        int d;

        public Info(int x, int y, int d) {
            this.x = x;
            this.y = y;
            this.d = d;
        }
    }
}
