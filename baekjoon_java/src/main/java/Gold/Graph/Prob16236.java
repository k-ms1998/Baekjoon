package Gold.Graph;

import java.io.*;
import java.util.*;

/**
 * Gold 3(아기 상어)
 *
 * https://www.acmicpc.net/problem/16236
 *
 * Solution: BFS + 구현
 */
public class Prob16236 {

    static int n;
    static int[][] grid;
    static int[][] visited;
    static Node shark;
    static Node[] fishes;
    static int fishCnt = 0;
    static final int INF = 1000000000;
    static int ans = 0;

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        /*
        입력
         */
        n = Integer.parseInt(br.readLine());
        grid = new int[n + 1][n + 1];
        fishes = new Node[n*n];
        for(int y = 1; y < n + 1; y++){
            st = new StringTokenizer(br.readLine());
            for(int x = 1; x < n + 1; x++){
                int num = Integer.parseInt(st.nextToken());
                if(num == 9){
                    shark = new Node(x, y, 2);
                }else if(num != 0){
                    grid[y][x] = num;
                    fishes[fishCnt] = new Node(x, y, grid[y][x]);
                    ++fishCnt;
                }else{
                    grid[y][x] = num;
                }
            }
        }
        moveShark(shark.x, shark.y, shark.size, 0);

        System.out.println(ans);
    }

    /*
    상어 움직이기
    상어가 움직일 수 있는 위치는 항상 한 군데 밖에 없음
     */
    public static void moveShark(int x, int y, int size, int cnt){
        Info nextPos = new Info(n, n, INF);
        /*
        상어의 위치를 옮길때마다, 현재 상어의 위치부터 모든 칸까지의 거리를 계산
         */
        reachable(x, y, size);
        /*
        상어의 위치를 옮길때마다, 각 물고기까지의 거리 계산
        - 물고기의 크기가 상어보다 크거나 같거나, 이미 먹은 물고기라서 grid[][] 값이 0이면 확인 X
        - 물고기까지의 거리가 0이면 도달하지 못하는 물고기로 간주함 -> 확인 X
        도달 가능한 물고기들 중에서 거리를 확인하고, 거리가 가장 작은 물고기 구하기 (nextPost)
        거리가 같으면, y 좌표를 확인해서 가장 위에 있는 물고기를 찾고, y도 같으면 x 좌표를 확인해서 더 왼쪽에 있는 물고기 찾기
         */
        for(int i = 0; i < fishCnt; i++){
            Node fish = fishes[i];
            if(fish.size >= size){
                continue;
            }
            if(grid[fish.y][fish.x] == 0){
                continue;
            }
            int distToFish = visited[fish.y][fish.x];
            if(distToFish > 0){
                if(distToFish < nextPos.dist){
                    nextPos = new Info(fish.x, fish.y, distToFish);
                    continue;
                }else if(nextPos.dist == distToFish){
                    if(fish.y < nextPos.y){
                        nextPos = new Info(fish.x, fish.y, distToFish);
                        continue;
                    }else if(fish.y == nextPos.y){
                        if(fish.x < nextPos.x){
                            nextPos = new Info(fish.x, fish.y, distToFish);
                        }
                    }
                }
            }
        }

        /*
        만약 nextPos의 거리가 INF 이면 잡아 먹을 수 있는 물고기가 없다는 뜻 -> 종료
         */
        if(nextPos.dist == INF){
            return;
        }

        /*
        물고기를 잡아먹기
        - grid[][] 값 업데이트
        - 물고기를 잡아 먹을때, 잡아 먹은 물고기의 횟수가 상어의 크기와 같으면 상어의 크기도 1 증가 && 잡아 먹은 물고기의 수 초기화
         */
        grid[nextPos.y][nextPos.x] = 0;
        ans += nextPos.dist;
        if(cnt + 1 == size){
            moveShark(nextPos.x, nextPos.y, size + 1, 0);
        }else{
            moveShark(nextPos.x, nextPos.y, size, cnt + 1);
        }
    }

    /*
    BFS 로 현재 상어의 위치부터 모든 점까지의 거리 계산
     */
    public static void reachable(int x, int y, int size){
        int[] dx = {1, -1, 0, 0};
        int[] dy = {0, 0, 1, -1};

        visited = new int[n + 1][n + 1];
        Deque<Info> queue = new ArrayDeque<>();
        queue.offer(new Info(x, y, 0));

        while(!queue.isEmpty()){
            Info node = queue.poll();
            int nodeX = node.x;
            int nodeY = node.y;
            int nodeDist = node.dist;

            for(int i = 0; i < 4; i++){
                int nx = nodeX + dx[i];
                int ny = nodeY + dy[i];

                if(nx <= 0 || ny <= 0 || nx > n || ny > n){
                    continue;
                }
                if(grid[ny][nx] <= size){
                    if(visited[ny][nx] == 0 || visited[ny][nx] > nodeDist + 1){
                        visited[ny][nx] = nodeDist + 1;
                        queue.offer(new Info(nx, ny, nodeDist + 1));
                    }
                }
            }
        }

    }

    public static class Node {
        int x;
        int y;
        int size;

        public Node(int x, int y, int size){
            this.x = x;
            this.y = y;
            this.size = size;
        }

        @Override
        public String toString() {
            return "Node: {x=" + x + ",y=" + y + ",size=" + size + "}";
        }
    }

    public static class Info{
        int x;
        int y;
        int dist;

        public Info(int x, int y, int dist){
            this.x = x;
            this.y = y;
            this.dist = dist;
        }

        @Override
        public String toString() {
            return "Info: {x=" + x + ",y=" + y + ",dist=" + dist + "}";
        }
    }
}