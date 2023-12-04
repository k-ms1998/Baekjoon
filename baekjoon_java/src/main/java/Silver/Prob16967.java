package Silver;
import java.io.*;
import java.util.*;

public class Prob16967{

    static int h, w, x, y;

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        h = Integer.parseInt(st.nextToken());
        w = Integer.parseInt(st.nextToken());
        x = Integer.parseInt(st.nextToken());
        y = Integer.parseInt(st.nextToken());

        int[][] grid = new int[h + x][w + y];
        for(int yy = 0; yy < h + x; yy++){
            st = new StringTokenizer(br.readLine());
            for(int xx = 0; xx < w + y; xx++){
                grid[yy][xx] = Integer.parseInt(st.nextToken());
            }
        }

        int[][] a = new int[h][w];
        for(int yy = 0; yy < h; yy++){
            for(int xx = 0; xx < w; xx++){
                a[yy][xx] = grid[yy][xx];
            }
        }
        int sy = x;
        int sx = y;
        for(int yy = sy; yy < h; yy++){
            for(int xx = sx; xx < w; xx++){
                a[yy][xx] = grid[yy][xx] - a[yy - x][xx - y];
            }
        }

        printGrid(a);
    }

    public static void printGrid(int[][] grid){
        int height = grid.length;
        int width = grid[0].length;

        StringBuilder tmp = new StringBuilder();
        for(int yy = 0; yy < height; yy++){
            for(int xx = 0; xx < width; xx++){
                tmp.append(grid[yy][xx]).append(" ");
            }
            tmp.append("\n");
        }

        System.out.println(tmp);
    }
}
