package Gold;

import java.io.*;
import java.util.*;

/**
 * Gold 4(알고스팟)
 *
 * https://www.acmicpc.net/problem/1261
 *
 * Solution: BFS
 */
public class Prob1261 {

    static int n, m;
    static char[][] grid;
    static int[][] count; // 벽을 부순 갯수 저장

    static int[] dx = {1, -1, 0, 0};
    static int[] dy = {0, 0, 1, -1};
    static final int INF = 1000000000;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        m = Integer.parseInt(st.nextToken());
        n = Integer.parseInt(st.nextToken());

        grid = new char[n + 1][m + 1];
        count = new int[n + 1][m + 1];
        for (int y = 1; y < n + 1; y++) {
            String curRow = br.readLine();
            for (int x = 1; x < m + 1; x++) {
                grid[y][x] = curRow.charAt(x - 1);
                count[y][x] = INF;
            }
        }

        PriorityQueue<Node> queue = new PriorityQueue<>(new Comparator<Node>(){
            @Override
            public int compare(Node n1, Node n2) {
                return n1.wall - n2.wall;
            }
        });
        queue.offer(new Node(1, 1, 0));
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            int x = node.x;
            int y = node.y;
            int wall = node.wall;

            if (x == m && n == y) {
                System.out.println(wall);
                break;
            }

            for (int i = 0; i < 4; i++) {
                int nx = x + dx[i];
                int ny = y + dy[i];

                if (nx <= 0 || ny <= 0 || nx > m || ny > n) {
                    continue;
                }
                if (grid[ny][nx] == '0') {
                    if (count[ny][nx] > wall) {
                        count[ny][nx] = wall;
                        queue.offer(new Node(nx, ny, wall));
                    }
                }else{
                    if (count[ny][nx] > wall + 1) {
                        count[ny][nx] = wall + 1;
                        queue.offer(new Node(nx, ny, wall + 1));
                    }
                }
            }
        }

    }

    public static class Node{
        int x;
        int y;
        int wall;

        public Node(int x, int y, int wall) {
            this.x = x;
            this.y = y;
            this.wall = wall;
        }
    }

}
