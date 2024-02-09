package Review.Gold;

import java.io.*;
import java.util.*;

/**
 * 1. N줄에 0~9 이하의 숫자가 3개씩 적혀 있다
 * 2. 첫 줄에서 마지막 줄에 도착
 *  2-1. 현재 줄에서 바로 아래 또는 한 칸 옆으로 이동 가능
 *     2-2. 처음은 중간에서 시작
 * 3. 마지막 출에 도착했을때 얻을 수 있는 최고 점수와 최저 점수 구하기
 *  3-1. DP
 *      3-1-1. 0번째 칸 = 현재 0번째 칸의 점숨 + max(직전 줄의 0번째 칸, 직전 줄의 1번째 칸)
 *      3-1-1. 1번째 칸 = 현재 1번째 칸의 점숨 + max(직전 줄의 0번째 칸, 직전 줄의 1번째 칸, 직전 줄의 2번째 칸)
 *      3-1-1. 2번째 칸 = 현재 2번째 칸의 점숨 + max(직전 줄의 1번째 칸, 직전 줄의 2번째 칸)
 */
public class Prob2096 {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    static int size;
    static int[][] grid;
    static int[][] dpMax;
    static int[][] dpMin;
    
    public static void main(String[] args) throws IOException{
        size = Integer.parseInt(br.readLine());
        
        grid = new int[size][3];
        dpMax = new int[size][3];
        dpMin = new int[size][3];
        for(int row = 0; row < size; row++){
            st = new StringTokenizer(br.readLine());

            grid[row][0] = Integer.parseInt(st.nextToken());
            grid[row][1] = Integer.parseInt(st.nextToken());
            grid[row][2] = Integer.parseInt(st.nextToken());

            if(row == 0){
                dpMax[0][0] = grid[0][0];
                dpMax[0][1] = grid[0][1];
                dpMax[0][2] = grid[0][2];
                dpMin[0][0] = grid[0][0];
                dpMin[0][1] = grid[0][1];
                dpMin[0][2] = grid[0][2];
            }else{
                dpMax[row][0] = grid[row][0] + Math.max(dpMax[row - 1][0], dpMax[row - 1][1]);
                dpMax[row][1] = grid[row][1] + Math.max(dpMax[row - 1][0], Math.max(dpMax[row - 1][1], dpMax[row - 1][2]));
                dpMax[row][2] = grid[row][2] + Math.max(dpMax[row - 1][1], dpMax[row - 1][2]);

                dpMin[row][0] = grid[row][0] + Math.min(dpMin[row - 1][0], dpMin[row - 1][1]);
                dpMin[row][1] = grid[row][1] + Math.min(dpMin[row - 1][0], Math.min(dpMin[row - 1][1], dpMin[row - 1][2]));
                dpMin[row][2] = grid[row][2] + Math.min(dpMin[row - 1][1], dpMin[row - 1][2]);
            }
        }

        int maxScore = Math.max(dpMax[size - 1][0], Math.max(dpMax[size - 1][1], dpMax[size - 1][2]));
        int minScore = Math.min(dpMin[size - 1][0], Math.min(dpMin[size - 1][1], dpMin[size - 1][2]));
        System.out.printf("%d %d", maxScore, minScore);
    }
}
