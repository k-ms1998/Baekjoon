package Gold.Basic;

import java.io.*;
import java.util.*;

/**
 * 1. 상어에는 1이상 M이하의 숫자가 붙어 있다(모두 서로 다른 번호를 가지고 있음)
 *  1-1. 1번을 가진 어른 상어는 나머지 다른 상어를 모두 내쫓을 수 있음
 *
 * 2. N x N 격자에서 M개의 칸에 상어가 한 마리씩 들어 있다
 * (1 <= N <= 20, 1 <= M <= N^2, 1 <= k <= 1000)
 *
 * 3. 맨 처음에는 모든 상어가 자신의 위치에서 냄새를 뿌린다
 *  3-1. 그 후 1초마다 모든 상어가 동시에 인접한(상하좌우)로 이동
 *  3-2. 이동 후 자신의 냄새를 새로운 칸에 뿌린다
 *  3-3. k번 이동하고 나면 냄새는 사라짐
 *
 * 4. 상어는 인접한 칸으로 움직일때 아무 냄새가 없는 칸으로 이동
 *  4-1. 아무 냄새가 없는 칸이 없으면 특정 우선순위에 의해 정한다. 각 상어마다 우선순위가 다르며, 보는 방향에 따라도 다를 수 있음
 *
 * 5. 맨 처음에 상어가 보고 있는 방향이 주어지고, 그 후에는 방금 이동한 방향이 보고 있는 방향이 된다
 *
 * 6. 모든 상어가 이동한 후, 한 칸에 여러 마리의 상어가 남아 있으면, 가장 작은 번호를 가진 상어를 제욓고 모두 격자 밖으로 쫓겨난다
 *
 */
public class Prob19237 {

    static int n, m, k;

    //위, 아래, 왼쪽, 오른쪽
    static final int[] dx = {0, 0, -1, 1};
    static final int[] dy = {-1, 1, 0, 0};

    static Smell[][] grid;
    static Shark[] sharks;
    static int[][][] sharkPref;

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine().trim());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        grid = new Smell[n][n];
        sharks = new Shark[m];
        sharkPref = new int[m][4][4];

        for(int y = 0; y < n; y++){
            st = new StringTokenizer(br.readLine().trim());
            for(int x = 0; x < n; x++){
                int num = Integer.parseInt(st.nextToken());
                grid[y][x] = new Smell(-1, 0);
                if(num > 0){
                    sharks[num - 1] = new Shark(x, y, -1);
                }
            }
        }

        st = new StringTokenizer(br.readLine().trim());
        for(int idx = 0; idx < m; idx++){
            sharks[idx].d = Integer.parseInt(st.nextToken()) - 1;
        }

        for(int sIdx = 0; sIdx < m; sIdx++){
            for(int dIdx = 0; dIdx < 4; dIdx++){
                st = new StringTokenizer(br.readLine().trim());
                for(int pIdx = 0; pIdx < 4; pIdx++){
                    sharkPref[sIdx][dIdx][pIdx] = Integer.parseInt(st.nextToken()) - 1;
                }
            }
        }

        int answer = 0;
        while(answer <= 1_000){
            if(isOver()){
                break;
            }
            // printGrid();
            List<Shark> tmp = new ArrayList<>();
            for(int i = 0; i < m; i++){
                tmp.add(new Shark(sharks[i].x, sharks[i].y, sharks[i].d));
            }
            for(int sIdx = 0; sIdx < m; sIdx++){
                Shark s = sharks[sIdx];
                int x = s.x;
                int y = s.y;
                int d = s.d;
                if(x == -1 || y == -1){
                    continue;
                }

                boolean flag = false;
                for(int pIdx = 0; pIdx < 4; pIdx++){
                    int dir = sharkPref[sIdx][d][pIdx];
                    int nx = x + dx[dir];
                    int ny = y + dy[dir];
                    if(isOutOfBounds(nx, ny)){
                        continue;
                    }

                    if(grid[ny][nx].left == 0 && isEmptyPoint(nx, ny, tmp)){
                        flag = true;
                        sharks[sIdx] = new Shark(nx, ny, dir);
                        grid[y][x] = new Smell(sIdx, k);
                        break;
                    }
                }
                if(!flag){
                    for(int pIdx = 0; pIdx < 4; pIdx++){
                        int dir = sharkPref[sIdx][d][pIdx];
                        int nx = x + dx[dir];
                        int ny = y + dy[dir];

                        if(isOutOfBounds(nx, ny)){
                            continue;
                        }

                        if(grid[ny][nx].num == sIdx){
                            sharks[sIdx] = new Shark(nx, ny, dir);
                            grid[y][x] = new Smell(sIdx, k);
                            break;
                        }
                    }
                }
            }
            answer++;
            updateSmell();
            removeShark();
        }

        System.out.println(answer > 1000 ? -1 : answer);
    }

    public static boolean isEmptyPoint(int x, int y, List<Shark> tmp){
        for(Shark s : tmp){
            if(s.x == x && s.y == y){
                return false;
            }
        }

        return true;
    }

    public static void updateSmell(){
        for(int y = 0; y < n; y++){
            for(int x = 0; x < n; x++){
                if(grid[y][x].left > 0){
                    grid[y][x].left--;
                    if(grid[y][x].left == 0){
                        grid[y][x].num = -1;
                    }
                }
            }
        }
    }

    public static void removeShark(){
        for(int idxA = 0; idxA < m; idxA++){
            int cx = sharks[idxA].x;
            int cy = sharks[idxA].y;
            int cNum = idxA;
            if(cx == -1 || cy == -1){
                continue;
            }
            for(int idxB = idxA; idxB < m; idxB++){
                if(idxA == idxB){
                    continue;
                }

                int tx = sharks[idxB].x;
                int ty = sharks[idxB].y;
                int tNum = idxB;
                if(tx == -1 || ty == -1){
                    continue;
                }

                if(cx == tx && cy == ty){
                    if(cNum < tNum){
                        sharks[idxB] = new Shark(-1, -1, -1);
                    }else{
                        sharks[idxA] = new Shark(-1, -1, -1);

                        cx = tx;
                        cy = ty;
                        cNum = tNum;
                    }
                }

            }
        }
    }

    public static boolean isOver(){
        int cnt = 0;
        for(Shark s : sharks){
            if(s.x != -1 && s.y != -1){
                cnt++;
            }
        }
        return cnt == 1;
    }

    public static boolean isOutOfBounds(int x, int y){
        return x < 0 || y < 0 || x >= n || y >= n;
    }

    public static void printGrid(){
        for(int y = 0; y < n; y++){
            for(int x = 0; x < n; x++){
                System.out.print((grid[y][x].num + 1) + " ");
            }
            System.out.println();
        }
        printSharks();
        System.out.println("----------");
    }

    public static void printSharks(){
        for(int i = 0; i < m; i++){
            System.out.println(sharks[i]);
        }
    }

    public static class Shark{
        int x;
        int y;
        int d;

        public Shark(int x, int y, int d){
            this.x = x;
            this.y = y;
            this.d = d;
        }

        @Override
        public String toString(){
            return "[x=" + x + ", y=" + y + ", d=" + (d + 1) + "]";
        }
    }

    public static class Smell{
        int num;
        int left;

        public Smell(int num, int left){
            this.num = num;
            this.left = left;
        }
    }
}