package Gold.Graph;

import java.io.*;
import java.util.*;

/**
 * Gold 5(감시 파하기)
 *
 * https://www.acmicpc.net/problem/18428
 *
 * Solution: Brute Force + Backtrack + Combination
 * 1. 선생님들의 시야에 있는 좌표들 모두 찾기 (findPossibleLocations())
 * 2. 해당 위치들의 조합을 찾기 (findAnswer())
 * 3. 해당 조합의 위치에 장애물들을 놓을때, 학생들이 시야에서 가져지는지 확인 (checkAnswer())
 */
public class Prob18428 {

    static int n;
    static int[][] grid;
    static List<Pos> teachers = new ArrayList<>();
    static List<Pos> obstacles = new ArrayList<>();

    static int[] dx = {1, -1, 0, 0};
    static int[] dy = {0, 0, 1, -1};

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        grid = new int[n + 1][n + 1];
        for(int y = 1; y < n + 1; y++){
            st = new StringTokenizer(br.readLine());
            for(int x = 1; x < n + 1; x++){
                String cur = st.nextToken();
                if(cur.equals("S")){
                    grid[y][x] = 1;
                }else if(cur.equals("T")){
                    grid[y][x] = 2;
                    teachers.add(new Pos(x, y));
                }else{
                    grid[y][x] = 0;
                }
            }
        }

        boolean ans = false;
        findPossibleLocations();
        for(int i = 0; i < obstacles.size() - 3; i++){
            if(findAnswer(0, i)){
                ans = true;
                break;
            }
        }

        System.out.println(ans ? "YES" : "NO");
    }

    public static void findPossibleLocations() {
        for(Pos teacher : teachers){
            int x = teacher.x;
            int y = teacher.y;

            for (int i = 0; i < 4; i++) {
                int nx = x;
                int ny = y;

                while(true){
                    nx += dx[i];
                    ny += dy[i];

                    if (nx <= 0 || ny <= 0 || nx > n || ny > n) {
                        break;
                    }
                    if (grid[ny][nx] == 0) {
                        obstacles.add(new Pos(nx, ny));
                    }else{
                        break;
                    }
                }
            }

        }
    }

    public static boolean findAnswer(int depth, int idx){
        if (depth == 3) {
            return checkGrid();
        }

        for (int i = idx; i < obstacles.size(); i++) {
            Pos pos = obstacles.get(i);
            int x = pos.x;
            int y = pos.y;
            if (grid[y][x] == 0) {
                grid[y][x] = -1;
                if(findAnswer(depth + 1, i + 1)){
                    return true;
                }
                grid[y][x] = 0;
            }
        }

        return false;
    }
    public static boolean checkGrid(){
        for(Pos teacher : teachers){
            int x = teacher.x;
            int y = teacher.y;

            for (int i = 0; i < 4; i++) {
                int nx = x;
                int ny = y;

                while(true){
                    nx += dx[i];
                    ny += dy[i];

                    if (nx <= 0 || ny <= 0 || nx > n || ny > n) {
                        break;
                    }
                    if (grid[ny][nx] == 1) {
                        return false;
                    }else if(grid[ny][nx] == 2 || grid[ny][nx] == -1){
                        break;
                    }
                }
            }
        }

        return true;
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
            return "{x=" + x + ", y=" + y + "}";
        }
    }
}
/*
3
S X X
X X X
T X S
-> YES

3
S S S
X S S
T X S
-> NO

5
X X T X X
X X X X X
T X S X T
X X X X X
X X T X X
-> NO

5
X S X X T
T X X S X
X X X X X
X T X X X
X X X T X
-> YES
 */