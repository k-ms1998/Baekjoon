package Gold.Backtracking;

import java.io.*;
import java.util.*;

public class Prob2239 {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    static int[][] grid = new int[9][9];

    static int[] gridBitH = new int[9];
    static int[] gridBitV = new int[9];
    static int[][] smallGrid = new int[3][3];

    static List<Point> blank = new ArrayList<>();

    static boolean found = false;

    public static void main(String args[]) throws IOException{
        for(int row = 0; row < 9; row++){
            String curRow = br.readLine();
            for(int col = 0; col < 9; col++){
                int num = curRow.charAt(col) - '0';
                grid[row][col] = num;



                if(num == 0){
                    blank.add(new Point(col, row));
                }else{
                    gridBitH[col] |= (1 << num);
                    gridBitV[row] |= (1 << num);
                    smallGrid[row/3][col/3] |= (1 << num);
                }

            }
        }

        dfs(0);
        System.out.println(sb);
    }

    public static void dfs(int idx){
        if(found){
            return;
        }
        if(idx == blank.size()){
            found = true;
            StringBuilder tmp = new StringBuilder();
            for(int row = 0; row < 9; row++){
                for(int col = 0; col < 9; col++){
                    sb.append(grid[row][col]);
                }
                sb.append("\n");
            }


            return;
        }

        Point p = blank.get(idx);
        int col = p.col;
        int row = p.row;
        int bit = gridBitH[col] | gridBitV[row] | smallGrid[row/3][col/3];

        for(int next = 1; next <= 9; next++){
            int nextBit = (1 << next);
            if((bit & nextBit) == 0){
                gridBitH[col] |= nextBit;
                gridBitV[row] |= nextBit;
                smallGrid[row/3][col/3] |= nextBit;
                grid[row][col] = next;
                dfs(idx + 1);
                gridBitH[col] ^= nextBit;
                gridBitV[row] ^= nextBit;
                smallGrid[row/3][col/3] ^= nextBit;
                grid[row][col] = 0;
            }
        }
    }

    public static class Point{
        int col;
        int row;

        public Point(int col, int row){
            this.col = col;
            this.row = row;
        }
    }
}