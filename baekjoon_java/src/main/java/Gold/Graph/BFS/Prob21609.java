package Gold.Graph.BFS;

import java.io.*;
import java.util.*;

/**
 * Gold 2(상어 중학교)
 *
 * https://www.acmicpc.net/problem/21609
 *
 * Solution: 구현 + BFS
 */
public class Prob21609 {

    static int n, m;
    static int[][] grid;
    static int[] dx = {1, 0, -1, 0};
    static int[] dy = {0, 1, 0, -1};
    static int answer = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        grid = new int[n][n];
        for(int y = 0; y < n; y++){
            st = new StringTokenizer(br.readLine());
            for(int x = 0; x < n; x++){
                grid[y][x] = Integer.parseInt(st.nextToken());
            }
        }


        while(findGroup()){ // 그룹 찾기
            updateGrid(); // 중력 가하기
            rotateGrid(); // 반시계 방향
            updateGrid(); // 중력 가하기
        }

        System.out.println(answer);
    }

    /*
    반시계 방향으로 90도 회전:
    정사각형일 경우, (x, y) -> (y, n - 1 - x) 가 됨
     */
    public static void rotateGrid() {
        int[][] tmp = new int[n][n];
        for(int y = 0; y < n; y++){
            for(int x = 0; x < n; x++){
                tmp[n -1 - x][y] = grid[y][x];
            }
        }

        for(int y = 0; y < n; y++){
            for(int x = 0; x < n; x++){
                grid[y][x] = tmp[y][x];
            }
        }
    }

    /*
    중력 가하기:
    밑에서 부터 일반 블록 탐색
    각 일반 블록을 빈칸이 아닌 칸을 만날때까지 밑으로 옮기기
     */
    public static void updateGrid() {
        for(int y = n - 1; y >= 0; y--){
            for(int x = 0; x < n; x++){
                if (grid[y][x] >= 0) {
                    int color = grid[y][x];
                    int ny = y + 1;
                    while (true) {
                        if(ny >= n){
                            ny--;
                            break;
                        }
                        if(grid[ny][x] != -2){
                            ny--;
                            break;
                        }
                        grid[ny][x] = -2;
                        ny++;
                    }
                    grid[y][x] = -2;
                    grid[ny][x] = color;
                }
            }
        }
    }

    public static boolean findGroup(){
        Group group = new Group(-1, new ArrayList<>(), new Point(-1, -1));
        
        boolean[][] visited = new boolean[n][n];
        for(int y = 0; y < n; y++){
            for(int x = 0; x < n; x++){
                if(grid[y][x] >= 1 && !visited[y][x]){ // 일반 블록이 무조건 하나는 포함되어 있어야 하기 때문에, 아직 방문하지 않은 일반 블록에서 부터 그룹 찾기 시작
                    Deque<Point> queue = new ArrayDeque<>();
                    int startColor = grid[y][x];

                    queue.offer(new Point(x, y));
                    visited[y][x] = true;

                    int rainbowCnt = 0; // 무지개 블록의 개수
                    List<Point> list = new ArrayList<>(); // 현재 그룹에 포함되어 있는 모든 점들
                    list.add(new Point(x, y));

                    /*
                    !! 방문한 무지개 블록은 따로 처리 !!
                    현재 그룹을 탐색할때, 각 무지개 블록을 이미 방문했는지 안했는지 확인할 필요는 있음(확인 안하면 무한 루프 생길수 있음)
                    But, 각 그룹에 대해서 같은 무지개 블록이 각자 그룹에 포함되어 있을 수 있기 때문에, 크게 봤을때는 방문처리 하면 안됨
                    ex:
                    -> 1 1 0 0 2 가 있을때, 총 2개의 그룹 생김: 
                        -> 1. {1, 1, 0, 0}
                        -> 2. {0, 0, 2}
                        => 이때, 1번과 2번 그룹은 둘 다 공통의 무지개의 블록을 그룹에 포함하고 있음
                     */
                    boolean[][] rainbowVisited = new boolean[n][n];

                    while (!queue.isEmpty()) {
                        Point p = queue.poll();
                        int px = p.x;
                        int py = p.y;

                        for(int i = 0; i < 4; i++){
                            int nx = px + dx[i];
                            int ny = py + dy[i];

                            if(nx < 0 || ny < 0 || nx >= n || ny >= n){
                                continue;
                            }

                            if(grid[ny][nx] == 0){
                                if(!rainbowVisited[ny][nx]){
                                    rainbowVisited[ny][nx] = true;
                                    queue.offer(new Point(nx, ny));
                                    list.add(new Point(nx, ny));
                                    rainbowCnt++;
                                }
                            }else if(grid[ny][nx] == startColor){
                                if(!visited[ny][nx]){
                                    visited[ny][nx] = true;
                                    queue.offer(new Point(nx, ny));
                                    list.add(new Point(nx, ny));
                                }
                            }
                        }
                    }

                    if(list.size() > 1){
                        if(compareGroup(group, rainbowCnt, list, x, y)){ // 조건에 따라 그룹 비교하기
                            group = new Group(rainbowCnt, list, new Point(x, y));
                        }
                    }
                }
            }
        }

        int listSize = group.list.size();
        if(listSize <= 1) {
            return false;
        }

        answer += (listSize * listSize);
        for(Point p : group.list){
            int x = p.x;
            int y = p.y;

            grid[y][x] = -2;
        }

        return true;
    }

    public static boolean compareGroup(Group group, int rainbowCnt, List<Point> list, int x, int y) {
        List<Point> groupList = group.list;
        int groupRainbowCnt = group.rainbowCnt;
        Point start = group.start;
        if(groupList.size() == list.size()){
            if (rainbowCnt == groupRainbowCnt) {
                if (start.y == y) {
                    return start.x < x;
                }else if(start.y < y){
                    return true;
                }else{
                    return false;
                }
            } else if (rainbowCnt > groupRainbowCnt) {
                return true;
            } else{
                return false;
            }
        }else if(groupList.size() < list.size()){
            return true;
        }else{
            return false;
        }
    }

    public static void printGrid(){
        for(int y = 0; y < n; y++){
            for(int x = 0; x < n; x++){
                System.out.print(grid[y][x] + " ");
            }
            System.out.println();
        }
        System.out.println("-----");
    }

    public static class Point{
        int x;
        int y;

        public Point(int x, int y){
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString(){
            return "{x=" + x + ", y=" + y + "}";
        }
    }

    public static class Group{
        int rainbowCnt;
        List<Point> list;
        Point start;

        public Group(int rainbowCnt, List<Point> list, Point start) {
            this.rainbowCnt = rainbowCnt;
            this.list = list;
            this.start = start;
        }

        @Override
        public String toString() {
            return "Group{" +
                    "rainbowCnt=" + rainbowCnt +
                    ", list=" + list +
                    ", start=" + start +
                    '}';
        }
    }
}
