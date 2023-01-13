package Gold.Tree.MST;

import java.io.*;
import java.util.*;

/**
 * Gold 1(다리 만들기 2)
 *
 * https://www.acmicpc.net/problem/17472
 *
 * Solution: BFS + MST + Union-Find
 * 1. 섬들끼리 묶어서 번호를 부여한다
 * (num[x][y]: (x, y)이 몇번째 섬인지 저장, islandSize: 각 섬의 크기, islandPos[i][j]: i번 섬에 속한 j번째 섬의 일부분의 좌표)
 *
 * 2. 각 섬에 대해서, 최단 거리 구하기
 * (dist[i][j]: i번째 섬에서 j번째 섬까지의 최단 거리)
 *  -> 이때, 조건에 의해서 다리는 일자로 밖에 놓지 못한다
 *
 * 3. dist[i][j] 를 참고해서, 모든 섬을 연결 시키는 간선들을 만들고, 거리가 짧은 순으로 정렬
 *
 * 4. 각 간선을 순서대로 탐색하면서 섬들끼리 연결 (MST, Union-Find)
 */
public class Prob17472 {

    static int n, m;
    static int[][] grid;
    static int[][] num;
    static Stack<Pos> islands = new Stack<>();
    static int[] islandSize = new int[7];
    static Pos[][] islandPos = new Pos[7][100];
    static int[][] dist;
    static int[] dx = {1, -1, 0, 0};
    static int[] dy = {0, 0, 1, -1};
    static int[] parent;
    static final int INF = 1000000000;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        grid = new int[n + 1][m + 1];
        num = new int[n + 1][m + 1];
        for (int y = 1; y < n + 1; y++) {
            st = new StringTokenizer(br.readLine());
            for(int x = 1; x < m + 1; x++){
                grid[y][x] = Integer.parseInt(st.nextToken());
                if (grid[y][x] == 1) {
                    islands.push(new Pos(x, y));
                }
            }
        }

        int islandNum = 1;
        while(!islands.isEmpty()){
            Pos cur = islands.pop();
            int x = cur.x;
            int y = cur.y;

            if (num[y][x] > 0) {
                continue;
            }
            int tmpSize = 1;
            num[y][x] = islandNum;
            islandPos[islandNum][0] = new Pos(x, y);
            Deque<Pos> queue = new ArrayDeque<>();
            queue.offer(new Pos(x, y));
            while (!queue.isEmpty()) {
                Pos island = queue.poll();
                int xx = island.x;
                int yy = island.y;

                for (int i = 0; i < 4; i++) {
                    int nx = xx + dx[i];
                    int ny = yy + dy[i];

                    if (nx <= 0 || ny <= 0 || nx > m || ny > n) {
                        continue;
                    }

                    if (grid[ny][nx] == 1 && num[ny][nx] == 0) {
                        num[ny][nx] = islandNum;
                        islandPos[islandNum][tmpSize] = new Pos(nx, ny);
                        ++tmpSize;
                        queue.offer(new Pos(nx, ny));
                    }
                }
            }
            islandSize[islandNum] = tmpSize;
            ++islandNum;
        }
        dist = new int[islandNum][islandNum];
        for (int i = 1; i < islandNum; i++) {
            for (int j = 1; j < islandNum; j++) {
                if (i != j) {
                    dist[i][j] = INF;
                }
            }
        }

        for (int i = 1; i < islandNum; i++) {
            for (int j = 0; j < islandSize[i]; j++) {
                Pos curIsland = islandPos[i][j];
                for (int d = 0; d < 4; d++) {
                    int nx = curIsland.x;
                    int ny = curIsland.y;
                    int bridgeLen = 0;
                    while (true) {
                        nx += dx[d];
                        ny += dy[d];
                        if (nx <= 0 || ny <= 0 || nx > m || ny > n) {
                            break;
                        }

                        ++bridgeLen;
                        if(grid[ny][nx] == 1){
                            /*
                            육지에는 다리 놓지 않기 때문에 다리의 길이 1 감소
                             */
                            --bridgeLen;
                            /*
                           각 다리의 길이는 1보다 커야함으로, 길이가 1이하이면 추가 X
                             */
                            if (bridgeLen <= 1) {
                                break;
                            }
                            dist[i][num[ny][nx]] = Math.min(dist[i][num[ny][nx]] , bridgeLen);
                            break;
                        }
                    }
                }
            }
        }

        PriorityQueue<Edge> edges = new PriorityQueue<>(new Comparator<Edge>(){
            @Override
            public int compare(Edge e1, Edge e2) {
                return e1.cost - e2.cost;
            }
        });

        parent = new int[islandNum];
        for (int i = 1; i < islandNum; i++) {
            parent[i] = i;
            for (int j = 1; j < islandNum; j++) {
                if (dist[i][j] == 0 || dist[i][j] == INF) {
                    continue;
                }
                edges.offer(new Edge(i, j, dist[i][j]));
            }
        }

        /**
         * MST:
         * 가중치가 작은 순서대로 간선들 탐ㅅ개
         * 인접한 노드들끼리 도달 가능한지 Union-Find 로 확인
         */
        int ans = 0;
        while (!edges.isEmpty()) {
            Edge edge = edges.poll();
            int s = edge.s;
            int d = edge.d;
            int cost = edge.cost;

            int rootS = findRoot(s);
            int rootD = findRoot(d);
            if (rootS != rootD) {
                parent[rootD] = rootS;
                ans += cost;
            }
        }

        for (int i = 2; i < islandNum; i++) {
            int rootA = findRoot(i - 1);
            int rootB = findRoot(i);
            if (rootA != rootB) {
                ans = 0;
                break;
            }
        }
        System.out.println(ans == 0 ? -1 : ans);
    }

    public static int findRoot(int node) {
        if (parent[node] == node) {
            return node;
        }

        int nextParent = findRoot(parent[node]);
        parent[node] = nextParent;
        return nextParent;
    }

    public static class Pos{
        int x;
        int y;

        public Pos(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    public static class Edge{
        int s;
        int d;
        int cost;

        public Edge(int s, int d, int cost) {
            this.s = s;
            this.d = d;
            this.cost = cost;
        }
    }
}
