package Gold.BitMasking;

import java.io.*;
import java.util.*;

/**
 * Gold 1(달이 차오른다, 가자)
 *
 * https://www.acmicpc.net/problem/1194
 *
 * Solution: BFS + BitMasking
 * 1. 시작 지점부터 시작해서, 인접한 노드들로 이동
 * 2. 각 노드로 이동할때, (x, y) 좌표와 현재 획득한 열쇠들에 따라서 따로 방문 했는지 확인
 *  -> (x, y)를 'a' 열쇠를 들고 있는거랑, 'b' 열쇠를 들고 있는거랑 다름
 *  -> 현재 획득한 열쇠 여부를 비트마스킹으로 통해 표현
 *      -> 000001 => 'a' 열쇠 획득, 000011 => 'a', 'b' 열쇠 획득
 * 3. 출구에 도착하면 끝
 */
public class Prob1194 {

    static int n, m;
    static char[][] grid;
    static int sx, sy;
    static int dx, dy;
    static boolean[][][] dist;

    static final int INF = 100000000;
    static int[] tx = {0, 1, 0, -1};
    static int[] ty = {1, 0, -1, 0};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        grid = new char[n][m];
        dist = new boolean[n][m][1 << 6];
        for (int y = 0; y < n; y++) {
            String cur = br.readLine();
            for (int x = 0; x < m; x++) {
                grid[y][x] = cur.charAt(x);
                if(grid[y][x] == '0'){
                    sx = x;
                    sy = y;
                }else if(grid[y][x] == '1'){
                    dx = x;
                    dy = y;
                }
            }
        }
//
//        System.out.println('a' - 'a'); // == 0
//        System.out.println('A' - 'A'); // == 0

        int answer = -1;
        Deque<Info> queue = new ArrayDeque<>(); // 큐에는 어차피 이동 거리가 짧은 순으로 값이 들어감
        queue.offer(new Info(sx, sy, 0, 0));
        dist[sy][sx][0] = true;
        while (!queue.isEmpty()) {
            Info info = queue.poll();
            int x = info.x;
            int y = info.y;
            int visited = info.visited;
            int d = info.d;

            if (grid[y][x] == '1') { // 출구 지점에 도착하면 바로 탐색 종료
                answer = d;
                break;
            }

            if(grid[y][x] == 'a' || grid[y][x] == 'b' || grid[y][x] == 'c'
                || grid[y][x] == 'd' || grid[y][x] == 'e' || grid[y][x] == 'f'){
                visited = visited | (1 << (grid[y][x] - 'a'));
            }
            for (int i = 0; i < 4; i++) {
                int nx = x + tx[i];
                int ny = y + ty[i];

                if (nx < 0 || ny < 0 || nx >= m || ny >= n) {
                    continue;
                }

                if(grid[y][x] == '#'){ // 벽이면 건너뛰기
                    continue;
                } else if(grid[y][x] == 'A' || grid[y][x] == 'B' || grid[y][x] == 'C'
                        || grid[y][x] == 'F' || grid[y][x] == 'E' || grid[y][x] == 'D'){ // 문에 도달하면 열쇠를 가지고 있는지 확인
                    if ((visited & (1 << (grid[y][x] - 'A'))) == (1 << (grid[y][x] - 'A'))) { // 'A' - 'A' == 0, 'a' - 'a' == 0
                        if(!dist[ny][nx][visited]){
                            dist[ny][nx][visited] = true;
                            queue.offer(new Info(nx, ny, visited, d + 1));
                        }
                    }
                }else{
                    if (!dist[ny][nx][visited]) {
                        dist[ny][nx][visited] = true;
                        queue.offer(new Info(nx, ny, visited, d + 1));
                    }
                }
            }
        }

        System.out.println(answer);
    }

    public static class Info{
        int x;
        int y;
        int visited;
        int d;

        public Info(int x, int y, int visited, int d) {
            this.x = x;
            this.y = y;
            this.visited = visited;
            this.d = d;
        }
    }
}
