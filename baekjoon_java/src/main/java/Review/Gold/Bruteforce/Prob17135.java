package Review.Gold.Bruteforce;

import java.io.*;
import java.util.*;

/**
 * 1. 궁수 3명 배치
 * 	1-1. 궁수들은 동시에 공격 진행
 * 	1-2. 각 궁수는 자신의 가장 가까운 적을 공격
 * 		1-2-1. 가장 가까운 적이 여러명이면 가장 왼쪽에 있는 적 공격
 * 2. 궁수의 공격이 끝나면 적들은 아래로 한칸 움직인다
 */
public class Prob17135 {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    static int height, width, range;
    static int[][] grid;
    static int[][] copyGrid;
    static int[] castle = new int[3];

    static int answer = 0;

    public static void main(String[] args) throws IOException{
        st = new StringTokenizer(br.readLine());
        height = Integer.parseInt(st.nextToken());
        width = Integer.parseInt(st.nextToken());
        range = Integer.parseInt(st.nextToken());

        grid = new int[height + 1][width];
        for(int row = 0; row < height; row++) {
            st = new StringTokenizer(br.readLine());
            for(int col = 0; col < width; col++) {
                grid[row][col] = Integer.parseInt(st.nextToken());
            }
        }

        findCombination(0, 0);

        System.out.println(answer);
    }

    public static void findCombination(int selectIdx, int col) {
        if(selectIdx == 3) {
            copyGrid = new int[height][width];
            for(int tRow = 0; tRow < height; tRow++) {
                for(int tCol = 0; tCol < width; tCol++) {
                    copyGrid[tRow][tCol] = grid[tRow][tCol];
                }
            }

//			System.out.printf("%d, %d, %d\n", castle[0], castle[1], castle[2]);
            int removedCnt = 0;
            for(int moveCnt = 0; moveCnt <= height; moveCnt++) {
                List<Point> enemies = new ArrayList<>();
                for(int idx = 0; idx < 3; idx++) {
                    int pCol = castle[idx];

                    Point enemy = findEnemy(pCol);
                    if(enemy.col != -1) {
                        enemies.add(enemy);
                    }
                }

                for(Point enemy : enemies) {
                    int pCol = enemy.col;
                    int pRow = enemy.row;
                    if(copyGrid[pRow][pCol] == 1) {
                        copyGrid[pRow][pCol] = 0;
                        removedCnt++;
                    }
                }

//				printGrid();
                moveGrid();
            }
            answer = Math.max(answer, removedCnt);

            return;
        }
        if(col == width) {
            return;
        }

        findCombination(selectIdx, col + 1);

        castle[selectIdx] = col;
        findCombination(selectIdx + 1, col + 1);

    }

    public static void moveGrid() {
        for(int row = height - 1; row >= 1; row--) {
            for(int col = 0; col < width; col++) {
                copyGrid[row][col] = copyGrid[row - 1][col];
            }
        }
        for(int col = 0; col < width; col++) {
            copyGrid[0][col] = 0;
        }
    }

    public static Point findEnemy(int sCol) {
        for(int r = 1; r <= range; r++) {
            for(int col = Math.max(0,sCol - r); col <= Math.min(sCol + r, width - 1); col++) {
                for(int row = height - 1; row >= Math.max(0, height - r); row--) {
                    if(copyGrid[row][col] == 1) {
                        int diffW = Math.abs(sCol - col);
                        int diffH = Math.abs(height - row);
                        if(diffW + diffH <= r) {
                            return new Point(col, row);
                        }
                    }
                }
            }
        }

        return new Point(-1, -1);
    }

    public static void printGrid() {
        for(int row = 0; row < height; row++) {
            for(int col = 0; col < width; col++) {
                System.out.print(copyGrid[row][col] + " ");
            }
            System.out.println();
        }
        System.out.println("----------");
    }

    public static class Point{
        int col;
        int row;

        public Point(int col, int row) {
            this.col = col;
            this.row = row;
        }
    }

}