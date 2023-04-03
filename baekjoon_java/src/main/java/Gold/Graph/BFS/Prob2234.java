package Gold.Graph.BFS;

import java.io.*;
import java.util.*;

/**
 * Gold 3(성곽)
 *
 * https://www.acmicpc.net/problem/2234
 *
 * Solution: FloodFill(BFS) + 비트마스킹
 * FloodFill: 하나의 지점으로부터, 인접한 노드들을 확인해서 연결되어 있으면 그룹핑 하기 (섬 문제)
 * 비트마스킹: 하나의 지점에 대해서 동서남북 방향의 벽의 여부를 숫자의 합으로 알 수 있음
 * -> 각 합을 이진수로 변환해서 벽의 위치를 알 수 있음
 *  -> 0: 벽 X -> 0000
 *  -> 1: 서쪽에 벽 -> 0001
 *  -> 2: 북쪽에 벽 -> 0010
 *  -> 4: 동쪽에 벽 -> 0100
 *  -> 8: 남쪽에 벽 -> 1000
 *      => ex: 11 -> 1011 -> 서, 북, 남 쪽에 벽이 있음
 */
public class Prob2234 {

    static int n, m;
    static int[][] grid;
    static int[][] islands;
    static Map<Integer, Integer> islandSize = new HashMap<>();
    static int maxSize = 0;

    // 남쪽, 동쪽, 북쪽, 서쪽
    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {1, 0, -1, 0};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        grid = new int[m][n];
        islands = new int[m][n];
        for (int y = 0; y < m; y++) {
            st = new StringTokenizer(br.readLine());
            for (int x = 0; x < n; x++) {
                grid[y][x] = Integer.parseInt(st.nextToken());
            }
        }

        int num = 0;
        for (int y = 0; y < m; y++) {
            for (int x = 0; x < n; x++) {
                // 아직 방문하지 않은 점에서 출발해서, 도달 가능한 모든 점들 확인 -> FloodFill
                if (islands[y][x] == 0) {
                    num++;
                    int cnt = 1;
                    Deque<Point> queue = new ArrayDeque<>();
                    queue.offer(new Point(x, y));
                    islands[y][x] = num;
                    while (!queue.isEmpty()) {
                        Point cur = queue.poll();
                        int cx = cur.x;
                        int cy = cur.y;

                        // 15 = 1111, 1 = 0001, 2 = 0010, 4 = 0100, 8 = 1000; 11 = 1011 -> 동쪽을 제외한 모든 방향에 벽 존재
                        int[] binary = toBinary(grid[cy][cx]);
                        for (int i = 0; i < 4; i++) {
                            int nx = cx + dx[i];
                            int ny = cy + dy[i];

                            if (nx < 0 || ny < 0 || nx >= n || ny >= m || binary[i] == 1) {
                                continue;
                            }
                            if(islands[ny][nx] == 0){
                                islands[ny][nx] = num;
                                cnt++;
                                queue.offer(new Point(nx, ny));
                            }
                        }
                    }

                    maxSize = Math.max(maxSize, cnt);
                    islandSize.put(num, cnt);
                }
            }
        }

        /*
        벽을 부쉈을때 가장 넓은 방의 크기 구하기
         -> 각 지점으로 부터 인접한 점 확인
            -> 부여된 번호가 다르면, 현재 지점의 방의 크기 + 벽 넘어의 방의 크기
         */
        int maxSize2 = maxSize;
        for (int y = 0; y < m; y++) {
            for(int x = 0; x < n; x++){
                int island = islands[y][x];
                int curSize = islandSize.get(island);
                // (0,0)부터 차례대로 각 점을 확인하기 때문에, 인접한 남쪽과 동쪽 점만 확인하면됨
                for (int i = 0; i < 2; i++) {
                    int nx = x + dx[i];
                    int ny = y + dy[i];

                    if (nx < 0 || ny < 0 || nx >= n || ny >= m) {
                        continue;
                    }
                    if(islands[ny][nx] != island){
                        maxSize2 = Math.max(maxSize2, curSize + islandSize.get(islands[ny][nx]));
                    }

                }
            }
        }

        StringBuilder ans = new StringBuilder();
        ans.append(islandSize.size()).append("\n");
        ans.append(maxSize).append("\n");
        ans.append(maxSize2).append("\n");

        System.out.println(ans);
    }

    public static int[] toBinary(int num) {
        int[] binary = new int[4];

        int idx = 3;
        while (num > 0) {
            int rem = num % 2;
            binary[idx] = rem;
            idx--;
            num /= 2;
        }

        return binary;
    }

    public static class Point{
        int x;
        int y;


        public Point(int x,int y){
            this.x = x;
            this.y = y;
        }
    }
}
