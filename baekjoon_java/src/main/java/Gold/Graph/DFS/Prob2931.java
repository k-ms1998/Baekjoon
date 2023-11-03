package Gold.Graph.DFS;

import java.io.*;
import java.util.*;

/**
 * Gold 2(가스관)
 *
 * https://www.acmicpc.net/problem/2931
 *
 * Solution: 구현
 */
public class Prob2931 {

    static int n, m;
    static char[][] grid;
    static int[] dx = {1, 0, -1, 0}; // 오른쪽, 위, 왼쪽, 아래쪽
    static int[] dy = {0, -1, 0, 1};
    static int sx, sy, ex, ey;
    static boolean[][] v;
    static StringBuilder ans = new StringBuilder();

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());


        grid = new char[n][m];
        v = new boolean[n][m];
        for(int y = 0; y < n; y++){
            String row = br.readLine();
            for(int x = 0; x < m; x++){
                grid[y][x] = row.charAt(x);
                if(grid[y][x] == 'M'){
                    sx = x;
                    sy = y;
                }
                if(grid[y][x] == 'Z'){
                    ex = x;
                    ey = y;
                }
            }
        }

        v[ey][ex] = true;
        for(int i = 0; i < 4; i++){
            int nx = ex + dx[i];
            int ny = ey + dy[i];

            if(nx < 0 || ny < 0 || nx >= m || ny >= n){
                continue;
            }

            if(grid[ny][nx] != '.' && grid[ny][nx] != 'M' && grid[ny][nx] != 'Z'){
                v[ny][nx] = true;
                findPipe(nx,ny);
                break;
            }
        }

        System.out.println(ans);
    }

    public static void findPipe(int x, int y){
        char c = grid[y][x];
        List<Integer> list = new ArrayList<>();
        if(c == '|'){
            list.add(1);
            list.add(3);
        }else if(c == '-'){
            list.add(0);
            list.add(2);
        }else if(c == '+'){
            list.add(0);
            list.add(1);
            list.add(2);
            list.add(3);
        }else if(c == '1'){
            list.add(0);
            list.add(3);
        }else if(c == '2'){
            list.add(0);
            list.add(1);
        }else if(c == '3'){
            list.add(1);
            list.add(2);
        }else{
            list.add(2);
            list.add(3);
        }

        for(int d : list){
            int nx = x + dx[d];
            int ny = y + dy[d];

            if(nx < 0 || ny < 0 || nx >= m || ny >= n){
                continue;
            }
            if(grid[ny][nx] == '.'){
                // System.out.println("nx=" + (nx + 1) + ", ny=" + (ny + 1));

                boolean[] conn = new boolean[4];
                for(int i = 0; i < 4; i++){
                    int nnx = nx + dx[i];
                    int nny = ny + dy[i];

                    if(nnx < 0 || nny < 0 || nnx >= m || nny >= n){
                        continue;
                    }

                    if(grid[nny][nnx] != '.'){
                        // System.out.println("conn=" + i + ", nnx=" + nnx + ", nny=" + nny);
                        if(connected(i, nnx, nny)){
                            conn[i] = true;
                            // System.out.println("conn=" + i);
                        }

                    }
                }

                ans.append(ny + 1).append(" ").append(nx + 1).append(" ");
                // System.out.println(conn[0] + ", " + conn[1] + ", " + conn[2] + ", " + conn[3]);
                // 0 == 오른쪽, 1 == 위쪽, 2 = 왼쪽, 3 == 아래쪽
                if(!conn[0] && conn[1] && !conn[2] && conn[3]){ // '|'
                    ans.append("|");
                }
                if(conn[0] && !conn[1] && conn[2] && !conn[3]){ // '-'
                    ans.append("-");
                }
                if(conn[0] && conn[1] && conn[2] && conn[3]){ // '+'
                    ans.append("+");
                }
                if(conn[0] && !conn[1] && !conn[2] && conn[3]){ // '1'
                    ans.append("1");
                }
                if(conn[0] && conn[1] && !conn[2] && !conn[3]){ // '2'
                    ans.append("2");
                }
                if(!conn[0] && conn[1] && conn[2] && !conn[3]){ // '3'
                    ans.append("3");
                }
                if(!conn[0] && !conn[1] && conn[2] && conn[3]){ // '4'
                    ans.append("4");
                }

                return;
            }
        }

        for(int d : list){
            int nx = x + dx[d];
            int ny = y + dy[d];

            if(nx < 0 || ny < 0 || nx >= m || ny >= n){
                continue;
            }
            if(!v[ny][nx] && connected(d, nx, ny)){
                v[ny][nx] = true;
                findPipe(nx, ny);
            }
        }
    }

    public static boolean connected(int d, int x, int y){
        if(d == 0){ // 오른쪽
            if(grid[y][x] == '-' || grid[y][x] == '+' || grid[y][x] == '3' || grid[y][x] == '4'){
                return true;
            }
            return false;
        }else if(d == 1){ // 위
            if(grid[y][x] == '|' || grid[y][x] == '+' || grid[y][x] == '1' || grid[y][x] == '4'){
                return true;
            }
            return false;
        }else if(d == 2){ // 왼쪽
            if(grid[y][x] == '-' || grid[y][x] == '+' || grid[y][x] == '1' || grid[y][x] == '2'){
                return true;
            }
            return false;

        }else{ // 아래쪽
            if(grid[y][x] == '|' || grid[y][x] == '+' || grid[y][x] == '2' || grid[y][x] == '3'){
                return true;
            }
            return false;
        }
    }
}