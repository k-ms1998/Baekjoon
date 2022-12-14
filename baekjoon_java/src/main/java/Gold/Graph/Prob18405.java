package Gold.Graph;

import java.io.*;
import java.util.*;

/**
 * Gold 5(경쟁적 전염)
 *
 * https://www.acmicpc.net/problem/18405
 *
 * Solution: BFS
 * fs 만큼 바이러스 번호가 작은 순서대로 바이러스를 확산 시키면 됨(BFS)
 */
public class Prob18405 {

    static int n, k;
    static int[][] grid;
    static Pos[][] viruses;
    static int[] startIdx;
    static int[] endIdx;
    static int fs, fx, fy;

    static int[] dx = {1, -1, 0, 0};
    static int[] dy = {0, 0, 1, -1};

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        grid = new int[n + 1][n + 1];
        viruses = new Pos[k+1][200];
        startIdx = new int[k + 1];
        endIdx = new int[k + 1];
        for(int y = 1; y < n + 1; y++){
            st = new StringTokenizer(br.readLine());
            for(int x = 1; x < n + 1; x++){
                int v = Integer.parseInt(st.nextToken());
                grid[y][x] = v;
                if(v > 0){
                    viruses[v][endIdx[v]] = new Pos(x, y);
                    endIdx[v]++;
                }
            }
        }

        st = new StringTokenizer(br.readLine());
        fs = Integer.parseInt(st.nextToken());
        fx = Integer.parseInt(st.nextToken());
        fy = Integer.parseInt(st.nextToken());

        for(int i = 0; i < fs; i++){
            spreadVirus();
        }

        System.out.println(grid[fx][fy]);
    }

    public static void spreadVirus(){
        for (int i = 1; i < k + 1; i++) {
            int start = startIdx[i];
            int end = endIdx[i];
            for (int j = start; j < end; j++) {
                Pos virus = viruses[i][j];
                int x = virus.x;
                int y = virus.y;

                for (int r = 0; r < 4; r++) {
                    int nx = x + dx[r];
                    int ny = y + dy[r];

                    if (nx <= 0 || ny <= 0 || nx > n || ny > n) {
                        continue;
                    }
                    if (grid[ny][nx] == 0) {
                        grid[ny][nx] = i;
                        viruses[i][endIdx[i]] = new Pos(nx, ny);
                        endIdx[i]++;
                    }
                }
            }
            startIdx[i] = end;
        }
    }

    public static class Pos{
        int x;
        int y;

        public Pos(int x, int y){
            this.x = x;
            this.y = y;
        }
    }
}
