package Gold.Graph.BFS;

import java.io.*;
import java.util.*;

/**
 * Gold 2(벽 부수고 이동하기 4)
 *
 *  https://www.acmicpc.net/problem/16946
 *
 * Solution: 
 * 1. 시간 효울서을 위해서 0이 있는 칸들 먼저 찾기
 * 2. 각 0 좌표로부터, 도달 가능한 모든 0들을 찾기
 * 3. 해당 칸들에 번호 부여
 *  -> 섬 문제랑 비슷
 * 4. 모든 1의 칸 찾기
 * 5. 각 1의 칸에서 인접한 0들을 찾기
 * 6. 인접 0들의 번호를 가져와서, 각 번호가 부여된 0의 개수들을 더해줌
 *  -> 총 합 + 1(현재 자신의 칸) 해당 1의 위치로부터 도달 가능한 칸의 총 수
 * => ex:
 * 11001
 * 00111
 * 01010
 * 10101
 * -> 1은 -로 바꾸고, 0의 칸들은 같은 그룹끼리 번호 부여
 *      -1 -1  1  1 -1
 *       2  2 -1 -1 -1
 *       2 -1  3 -1  4
 *      -1  5 -1  6 -1
 *     => (1, 0)의 -1 에서 도달 가능한 칸 = 1(자기 자신의 칸) + 번호가 1인 그룹들 + 번호가 2인 그룹들
 *                                    = 1 + 2 + 3 = 6
 */
public class Prob16946 {

    static int n, m;
    static int[][] grid;
    static int[] dx = {1, 0, -1, 0};
    static int[] dy = {0, 1, 0, -1};
    static Map<Integer, Integer> islands = new HashMap<>();
    static Deque<Point> zeros = new ArrayDeque<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        grid = new int[n][m];
        for (int y = 0; y < n; y++) {
            String cur = br.readLine();
            for(int x = 0; x < m; x++){
                grid[y][x] = Integer.parseInt(String.valueOf(cur.charAt(x)));
                if(grid[y][x] == 0){
                    zeros.add(new Point(x, y));
                } else{
                    grid[y][x] = -1;
                }
            }
        }

        int islandNum = 0;
        while (!zeros.isEmpty()) {
            Point p = zeros.poll();
            int x = p.x;
            int y = p.y;

            if (grid[y][x] == 0) {
                islandNum++;
                int size = 1;
                Deque<Point> queue = new ArrayDeque<>();
                queue.offer(new Point(x, y));
                grid[y][x] = islandNum;
                while (!queue.isEmpty()) {
                    Point point = queue.poll();
                    int cx = point.x;
                    int cy = point.y;

                    for (int i = 0; i < 4; i++) {
                        int nx = cx + dx[i];
                        int ny = cy + dy[i];

                        if (nx < 0 || ny < 0 || nx >= m || ny >= n) {
                            continue;
                        }
                        if (grid[ny][nx] == 0) {
                            size++;
                            grid[ny][nx] = islandNum;
                            queue.offer(new Point(nx, ny));
                        }
                    }
                }

                islands.put(islandNum, size);
            }
        }

        StringBuilder ans = new StringBuilder();
        for (int y = 0; y < n; y++) {
            for(int x = 0; x < m; x++){
                if(grid[y][x] == -1){
                    int cnt = 1;
                    Set<Integer> visited = new HashSet<>(); // HashSet 을 이용해서, 해당 번호의 그룹을 이미 확인 했느지 알 수 있음
                    for(int i = 0; i < 4; i++){
                        int nx = x + dx[i];
                        int ny = y + dy[i];

                        if(nx < 0 || ny < 0 || nx >= m || ny >= n){
                            continue;
                        }

                        if(grid[ny][nx] != -1){
                            int num = grid[ny][nx];
                            if (!visited.contains(num)) {
                                cnt += islands.get(num);
                                visited.add(num);
                            }
                        }
                    }
                    ans.append(cnt%10);
                }else{
                    ans.append("0");
                }
            }
            ans.append("\n");
        }

        System.out.println(ans);
    }

    public static class Point{
        int x;
        int y;

        public Point(int x, int y){
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "{x=" + x + ", y=" + y + "}";
        }
    }
}
/*
4 4
0000
0100
0010
0000
 */