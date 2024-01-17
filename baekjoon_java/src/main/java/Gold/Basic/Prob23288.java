package Gold.Basic;

import java.io.*;
import java.util.*;

/**
 * 1. N x M 지도 존재
 *  1-1. 지도의 오른쪽 = 동쪽, 위쪽 = 북쪽
 *  1-2. (r, c) -> r = 북쪽으로부터 떨어진 칸의 수(row), c = 서쪽으로부터 떨어진 칸의 수(column)
 *  1-3. 가장 왼쪽 위에 있는 칸 = (1,1), 가장 오른쪽 아래에 있는 칸 = (N, M)
 *
 * 2. 주사위는 지도 위에 윗 면 = , 동쪽을 바라보는 방향 = 3인 상태로 (1 , 1)에 놓여져 있음
 *  2-1. 전개도에 따라서 1-6, 3-4, 2-5 가 서로의 반대쪽에 있음
 *
 * 3. 가장 처음에 주사위의 이동 방향은 동쪽이다
 *  3-1. 이동 방향에 칸이 없다면(Out of Bounds) 이동 방향을 반대로 한 다음 한칸 굴러간다
 *  3-2. 주사위가 도착한 칸에 대한 점수를 획득한다
 *  3-3. 주사위의 아랫면에 있는 정수 A과 주사위가 있는 칸에 있는 정수 B를 비교해서 이동 방향이 결정됨
 *      3-3-1. A > B -> 이동 방향을 90도 시계 방향으로 회전
 *      3-3-2. A < B -> 이동 방향을 90도 반시개 방향으로 회전
 *      3-3-3. A = B -> 이동 방향의 변화 X
 *
 * 4. 칸 (x, y)에 대한 점수는 다음과 같이 구할 수 있다
 *  4-1. (x, y) 에 있는 정수를 B라고 했을때, (x, y)에서 동서남북 방향으로 연속해서 이동할 수 있는 칸의 수 = C
 *      4-1-1. 점수 = B x C
 */
public class Prob23288 {

    static int n, m, k;
    static int[][] map;

    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {1, 0, -1, 0};

    static int[][] points;

    static int[] ver = {2, 1, 5, 6};
    static int[] hor = {4, 1, 3, 6};

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        map = new int[n + 1][m + 1];
        points = new int[n + 1][m + 1];
        for(int x = 1; x < n + 1; x++){
            st = new StringTokenizer(br.readLine());
            for(int y = 1; y < m + 1; y++){
                map[x][y] = Integer.parseInt(st.nextToken());
            }
        }

        // 각 좌표에서 얻을 수 있는 점수를 구하기
        boolean[][] v = new boolean[n + 1][m + 1];
        for(int x = 1; x < n + 1; x++){
            for(int y = 1; y < m + 1; y++){
                if(!v[x][y]){
                    Deque<Point> flood = new ArrayDeque<>();
                    Deque<Point> queue = new ArrayDeque<>();
                    queue.offer(new Point(x, y));
                    flood.offer(new Point(x, y));
                    int curCnt = 1;
                    v[x][y] = true;

                    // BFS로 연속으로 움직일 수 있는 칸 찾기(현재 칸과 인접한 칸의 숫자가 같으면 인접한 칸으로 움직일 수 있음 -> 이렇게 움직여서 도달할 수 있는 칸의 총 개수 = 연속으로 움직일 수 있는 칸)
                    while(!queue.isEmpty()){
                        Point p = queue.poll();
                        int px = p.x;
                        int py = p.y;

                        for(int dir = 0; dir < 4; dir++){
                            int nx = px + dx[dir];
                            int ny = py + dy[dir];

                            if(nx <= 0 || ny <= 0 || nx > n || ny > m){
                                continue;
                            }

                            if(!v[nx][ny] && map[x][y] == map[nx][ny]){
                                v[nx][ny] = true;
                                curCnt++;
                                queue.offer(new Point(nx, ny));
                                flood.offer(new Point(nx, ny));
                            }

                        }
                    }

                    // 현재 (x, y)에서 도달할 수 있는 모든 칸들에 대해서 points 업데이트
                    while(!flood.isEmpty()){
                        Point p = flood.poll();
                        int px = p.x;
                        int py = p.y;
                        points[px][py] = map[px][py] * curCnt;
                    }
                }

            }
        }

        // k번 만큼 움직이기
        int x = 1;
        int y = 1;
        int dir = 0; // 주사위가 굴러가는 방향
        int answer = 0;
        while(k-- > 0){
            int nx = x + dx[dir];
            int ny = y + dy[dir];
            if(nx <= 0 || ny <= 0 || nx > n || ny > m){
                dir = (dir + 2) % 4;
                k++;
                continue;
            }

            answer += points[nx][ny];

            if(dir == 0){ // 동쪽으로 움직이기
                moveEast();
            }else if(dir == 1){ // 남쪽으로 움직이기
                moveSouth();
            }else if(dir == 2){ // 서쪽으로 움직이기
                moveWest();
            }else{ // 북쪽으로 움직이기
                moveNorth();
            }

            int diceBottom = hor[3];
            if(diceBottom > map[nx][ny]){
                dir = (dir + 1) % 4;
            }else if(diceBottom < map[nx][ny]){
                dir = (dir + 3) % 4;
            }

            x = nx;
            y = ny;
        }

        System.out.println(answer);
    }

    public static void moveSouth(){
        int last = ver[3];
        for(int i = 3; i >= 1; i--){
            ver[i] = ver[i-1];
        }
        ver[0] = last;

        hor[1] = ver[1];
        hor[3] = ver[3];
    }

    public static void moveNorth(){
        int first = ver[0];
        for(int i = 0; i < 3; i++){
            ver[i] = ver[i+1];
        }
        ver[3] = first;

        hor[1] = ver[1];
        hor[3] = ver[3];
    }

    public static void moveEast(){
        int last = hor[3];
        for(int i = 3; i >= 1; i--){
            hor[i] = hor[i-1];
        }
        hor[0] = last;

        ver[1] = hor[1];
        ver[3] = hor[3];
    }

    public static void moveWest(){
        int first = hor[0];
        for(int i = 0; i < 3; i++){
            hor[i] = hor[i+1];
        }
        hor[3] = first;

        ver[1] = hor[1];
        ver[3] = hor[3];
    }

    public static class Point{
        int x;
        int y;

        public Point(int x, int y){
            this.x = x;
            this.y = y;
        }
    }
}