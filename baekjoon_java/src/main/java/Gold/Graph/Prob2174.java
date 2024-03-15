package Gold.Graph;

import java.io.*;
import java.util.*;

/**
 * 1. 각 로봇은 처음에 NWES 중 하나의 방향을 향해 서 있다
 *  1-1. N=North, W=West, E=East, S=South
 * 2. 총 3개의 명령이 있다
 *  2-1. L: 로봇이 왼쪽으로 90도를 회전
 *  2-2. R: 로봇이 오른쪽으로 90도 회전
 *  2-3. F: 로봇이 바라보고 있는 방향으로 한 칸 움직인다
 *
 *
 */
public class Prob2174 {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    static int width, height;
    static int robotCount, commandCount;

    static final int[] dCol = {1, 0, -1, 0};
    static final int[] dRow = {0, 1, 0, -1};

    static Robot[] robots;
    static boolean[] hit;
    static int[][] grid;

    public static void main(String args[]) throws IOException{
        st = new StringTokenizer(br.readLine());
        width = Integer.parseInt(st.nextToken());
        height = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        robotCount = Integer.parseInt(st.nextToken());
        commandCount = Integer.parseInt(st.nextToken());

        robots = new Robot[robotCount + 1];
        hit = new boolean[robotCount + 1];
        grid = new int[height + 1][width + 1];
        for(int idx = 1; idx <= robotCount; idx++){
            st = new StringTokenizer(br.readLine());
            int col = Integer.parseInt(st.nextToken());
            int row = height - Integer.parseInt(st.nextToken()) + 1;
            String dirStr = st.nextToken();

            int dir = 0;
            if(dirStr.equals("E")){
                dir = 0;
            }else if(dirStr.equals("S")){
                dir = 1;
            }else if(dirStr.equals("W")){
                dir = 2;
            }else{
                dir = 3;
            }

            robots[idx] = new Robot(col, row, dir);
            grid[row][col] = idx;
        }

        boolean crashed = false;
        for(int idx = 0; idx < commandCount; idx++){
            st = new StringTokenizer(br.readLine());
            int robot = Integer.parseInt(st.nextToken());
            String command = st.nextToken();
            int repeat = Integer.parseInt(st.nextToken());

            if(crashed){
                continue;
            }

            int dir = robots[robot].dir;
            for(int r = 0; r < repeat; r++){
                if(command.equals("L")){
                    robots[robot].dir = (robots[robot].dir + 3) % 4;
                }else if(command.equals("R")){
                    robots[robot].dir = (robots[robot].dir + 1) % 4;
                }else{
                    int nCol = robots[robot].col + dCol[dir];
                    int nRow = robots[robot].row + dRow[dir];

                    if(nCol < 1 || nRow < 1 || nCol > width || nRow > height){
                        sb.append(String.format("Robot %d crashes into the wall\n", robot));
                        crashed = true;
                        break;
                    }
                    if(grid[nRow][nCol] != 0){
                        sb.append(String.format("Robot %d crashes into robot %d\n", robot, grid[nRow][nCol]));
                        crashed = true;
                        break;
                    }

                    grid[robots[robot].row][robots[robot].col] = 0;
                    robots[robot].col = nCol;
                    robots[robot].row = nRow;
                    grid[nRow][nCol] = robot;
                }
            }
        }

        if(crashed){
            System.out.println(sb);
        }else{
            System.out.println("OK");
        }

    }

    public static class Robot{
        int col;
        int row;
        int dir;

        public Robot(int col, int row, int dir){
            this.col = col;
            this.row = row;
            this.dir = dir;
        }
    }
}