package Gold.Basic;

import java.io.*;
import java.util.*;

/**
 * Gold 3(경사로)
 *
 * https://www.acmicpc.net/problem/14890
 * 
 * Solution: 구현
 */
public class Prob14890 {

    static int n, l;
    static int[][] grid;
    static boolean[][][] passed;
    static int answer = 0;
    static int[] dx = {0, 0, 1, -1};
    static int[] dy = {1, -1, 0, 0};

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        l = Integer.parseInt(st.nextToken());

        grid = new int[n][n];
        /*
        passed[y][x][0] -> (x, y)에서 세로로 놓인 경사로가 있는지
        passed[y][x][1] -> (x, y)에서 가로로 놓인 경사로가 있는지
         */
        passed = new boolean[n][n][2];
        for(int y = 0; y < n; y++){
            st = new StringTokenizer(br.readLine());
            for(int x = 0;  x < n; x++) {
                grid[y][x] = Integer.parseInt(st.nextToken());
            }
        }

        // 각 방향으로 경사로를 놓을수 있는지 확인
        for(int x = 0; x < n; x++){
            placeRamp(x, 0, 0);
        }
        for(int x = 0; x < n; x++){
            placeRamp(x, n - 1, 1);
        }
        for(int y = 0; y < n; y++){
            placeRamp(0, y, 2);
        }
        for(int y = 0; y < n; y++){
            placeRamp(n - 1, y, 3);
        }

//        printGrid();

        for(int x = 0; x < n; x++){
            boolean flag = true;
            for(int y = 1; y < n; y++){
                // 세로로 탐색할때, 현재 위치와 직전 위치에서 높이가 다를때
                // 둘 다의 위치의 passed 값이 true 이면 경사로가 놓였으므로 이동 가능
                // 둘 중 하나라도 false 이면 경사로가 놓을 수 없음
                if(grid[y][x] != grid[y-1][x]){
                    if(!passed[y][x][0] || !passed[y-1][x][0]){
                        flag = false;
                        break;
                    }
                }
            }
            if(flag){
                answer++;
            }
        }
        for(int y = 0; y < n; y++){
            boolean flag = true;
            for(int x = 1; x < n; x++){
                // 가로로 탐색할때, 현재 위치와 직전 위치에서 높이가 다를때
                // 둘 다의 위치의 passed 값이 true 이면 경사로가 놓였으므로 이동 가능
                // 둘 중 하나라도 false 이면 경사로가 놓을 수 없음
                if(grid[y][x] != grid[y][x - 1]){
                    if (!passed[y][x][1] || !passed[y][x - 1][1]) {
                        flag = false;
                        break;
                    }
                }
            }
            if(flag){
                answer++;
            }
        }

        System.out.println(answer);
    }

    // dir = 0 -> 아래; dir = 1 -> 위; dir = 2 -> 오른; dir = 3 -> 왼;
    public static void placeRamp(int x, int y, int dir){
        int nx = x;
        int ny = y;
        int curH = grid[y][x];
        while(true){
            nx += dx[dir];
            ny += dy[dir];
            if(nx < 0 || ny < 0 || nx >= n || ny >= n){
                break;
            }

            int nextH = grid[ny][nx];
            if(curH != nextH){
                /*
                현재 높이가 다음 위치의 높이보다 1 낮을때 탐색 시작
                길이 l짜리 경사로를 설치할 수 있는지 없는지 확인
                */
                if(curH - nextH == 1){
                    boolean flag = true;
                    int cnt = 0;
                    int tx = nx;
                    int ty = ny;
                    while(cnt < l){
                        if(tx < 0 || ty < 0 || tx >= n || ty >= n){
                            flag = false;
                            break;
                        }

                        /*
                         만약, 이미 해당 위치에 경사로가 설치되어 있으면 건너뛰기
                         */
                        if(passed[ty][tx][dir/2]){
                            /*
                            만약, 경사로의 길이가 1이면 false 로 업데이트
                            ex: 2 2 1 2 2, l = 1 이거, 왼->오 방향으로 탐색을 마쳐서 (2, 0)에 경사로가 있을때:
                            오->왼 방향으로 탐색할때, (2,0)에는 이미 경사로가 있으므로 건너뜀
                            But, passed[0][2][1]를 true 로 유지하면, (2, 0) -> (3, 0) 으로 갈수 있는것 처럼 보임
                                -> 그러므로 passed[0][2][1]을 false 로 바꿔줌
                             */
                            if(l == 1){
                                passed[ty][tx][dir / 2] = false;
                            }
                            flag = false;
                            break;
                        }
                        if(grid[ty][tx] != nextH){
                            flag = false;
                            break;
                        }

                        tx += dx[dir];
                        ty += dy[dir];
                        ++cnt;
                    }
                    /*
                    경사로를 설치할 수 있으면 passed 업데이트
                        -> passed 는 경사로가 필요한 위치에 경사로가 놓을 수 있는 확인
                    */
                    if(flag){
                        tx = nx;
                        ty = ny;
                        cnt = 0;
                        passed[ny - dy[dir]][nx - dx[dir]][dir / 2] = true;
                        while(cnt < l){
                            passed[ty][tx][dir / 2] = true;
                            tx += dx[dir];
                            ty += dy[dir];
                            ++cnt;
                        }
                        nx += (l - 1) * dx[dir];
                        ny += (l - 1) * dy[dir];
                        nextH = grid[ny][nx];
                    }
                }
            }

            curH = nextH;
        }
    }

    public static void printGrid(){
        for(int y = 0; y < n; y++){
            for(int x = 0; x < n; x++){
                System.out.print("{" + passed[y][x][0] + "," + passed[y][x][1] + "} ");
            }
            System.out.println();
        }
    }
}
