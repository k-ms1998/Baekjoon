package Gold.Graph;

import java.util.*;
import java.io.*;

/**
 * Gold 3(치즈)
 *
 * https://www.acmicpc.net/problem/2638
 *
 * Solution: BFS
 */
public class Prob2638 {

    static int n;
    static int m;

    static int[][] grid;

    static int[] dx = {1, -1, 0, 0};
    static int[] dy = {0, 0, 1, -1};

    static int finalCnt = 0;
    static int time = 0;

    static Deque<Pos> outsideAir = new ArrayDeque<>();

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;


        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        grid =  new int[n][m];

        for(int y = 0; y < n; y++){
            st = new StringTokenizer(br.readLine());
            for(int x = 0; x < m; x++){
                grid[y][x] = Integer.parseInt(st.nextToken());
                if(grid[y][x] == 1){
                    ++finalCnt;
                }
            }
        }

        while(finalCnt > 0){
            removeCheese();
//            printGrid();
            ++time;
        }
        System.out.println(time);
    }

    public static void removeCheese(){
        Deque<Pos> outside = outsideCheese();

        while (!outside.isEmpty()) {
            Pos curPos = outside.poll();
            int curX = curPos.x;
            int curY = curPos.y;

            if (grid[curY][curX] == 1) {
                if (checkRemove(curX, curY)) {
                    /**
                     * 녹은 치즈들은 이제 공기임으로 값을 변경해줌
                     * 이때, -1로 변경하면 outside 에 저장된 치즈들에 대해서 2개 이상의 바깥 공기와 닿고 있는지 판별하는데 영향을 미치기 때문에 -1이 아닌 0으로 변경
                     * ex: 
                     * -1 -1 -1
                     * -1  1  1 이 있을때, (2,1)에 위치한 1의 값은 녹는 치즈 임으로 값을 변경해야 하는데, -1로 변경할 경우 (2,2)도 동시에 녹는 것으로 판단되는 오류 발생
                     */
                    grid[curY][curX] = 0;
                    outsideAir.offer(new Pos(curX, curY));
                    --finalCnt;
                }
            }
        }

    }

    private static boolean checkRemove(int curX, int curY) {
        int cnt = 0;
        for (int i = 0; i < 4; i++) {
            int nx = curX + dx[i];
            int ny = curY + dy[i];
            if (nx < 0 || nx >= m || ny < 0 || ny >= n) {
                continue;
            }

            if (grid[ny][nx] == -1) {
                ++cnt;
            }
        }

        if (cnt >= 2) {
            return true;
        }

        return false;
    }

    /**
     * 현재 치즈가 바깥 쪽에 있는 치즈인지 확인
     * 1. grid에서 값이 0인 부분을 찾는다
     * 2. 해당 위치로부터 도달가능한 모든 0의 값들에 대해서 -1로 변경 해준다 (-1이면 바깥쪽의 공기)
     * 3. 값이 1인 부분은 따로 outSide에 저장해둔다
     *  -> 이후에, outSide에 저장된 위치의 치즈들이 녹아 없어지는 대상인지 아닌지 확인
     *      -> 만약, 바깥 공기 2개 이상의 면과 닿고 있으면 녹음 (이때, 바깥 공기는 값이 0이 아니고 -1임)
     * => 1의 값에 도달하면 멈춤으로써 치즈를 넘어서 안쪽 공기의 값은 변경이 안되도록 하고(치즈가 경계선), 치즈를 넘지 않는 0에 대해서는 모두 -1로 변경 해줌
     * => 이때, 현재 위치한 0의 값에서 인접한 칸들도 모두 바깥 공기임으로, 더 이상 인접한 0의 값으로 이동할 수 없을때까지 0의 값을 찾으면 현재의 바깥 공기 위치들을 모두 찾을 수 있음
     */
    private static Deque<Pos> outsideCheese() {
        Deque<Pos> outside = new ArrayDeque<>();

        if(outsideAir.isEmpty()){
            for (int y = 0; y < n; y++) {
                for (int x = 0; x < m; x++) {
                    if(grid[y][x] == 0){
                        outsideAir.offer(new Pos(x, y));
                        break;
                    }
                }
            }
        }

        while (!outsideAir.isEmpty()) {
            Pos curPos = outsideAir.poll();
            int curX = curPos.x;
            int curY = curPos.y;
            grid[curY][curX] = -1;

            for (int i = 0; i < 4; i++) {
                int nx = curX + dx[i];
                int ny = curY + dy[i];

                if (nx < 0 || nx >= m || ny < 0 || ny >= n) {
                    continue;
                }

                if (grid[ny][nx] == 0) {
                    grid[ny][nx] = -1;
                    outsideAir.offer(new Pos(nx, ny));
                } else if (grid[ny][nx] == 1) {
                    outside.offer(new Pos(nx, ny));
                }
            }
        }

        return outside;
    }

    public static void printGrid(){
        for(int y = 0; y < n; y++){
            for(int x = 0; x < m; x++){
                if(grid[y][x] != -1){
                    System.out.print(grid[y][x] + " ");
                }else{
                    System.out.print(0 + " ");
                }
            }
            System.out.println();
        }
        System.out.println("---------------------");
    }

    public static class Pos{
        int x;
        int y;

        public Pos(int x, int y){
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString(){
            return "Pos: [x=" + x + ", y=" + y + "]";
        }
    }
}
/*
8 9
0 0 0 0 0 0 0 0 0
0 0 0 1 1 0 0 0 0
0 0 0 1 1 0 1 1 0
0 0 1 1 1 1 1 1 0
0 0 1 1 1 1 1 0 0
0 0 1 1 0 1 1 0 0
0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0
Ans: 4

8 9
0 0 0 0 0 0 0 0 0
0 1 1 1 0 1 1 1 0
0 1 0 1 0 1 0 1 0
0 1 0 1 1 1 0 1 0
0 1 0 0 1 0 0 1 0
0 1 0 1 1 1 0 1 0
0 1 1 0 0 0 1 1 0
0 0 0 0 0 0 0 0 0
Ans: 3

8 9
0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0
0 0 0 0 1 0 0 0 0
0 0 0 1 1 1 0 0 0
0 0 0 0 1 0 0 0 0
0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0
Ans: 2

7 7
0 0 0 0 0 0 0
0 1 1 0 1 1 0
0 1 1 1 1 1 0
0 0 1 0 1 0 0
0 1 1 1 1 1 0
0 1 1 0 1 1 0
0 0 0 0 0 0 0
Ans: 3
 */