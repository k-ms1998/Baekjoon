package Gold.BruteForce;

import java.io.*;
import java.util.*;

/**
 * Gold 3(사다리 조작)
 *
 * https://www.acmicpc.net/problem/15684
 *
 * Solution: Backtracking + BruteForce
 * 1. 가로선을 놓을 수 있는 모든 경우의 수 계산해서, 최단으로 가능한 갯수 찾기
 *  -> 추가 가로선의 수가 3이 초과하면 -1을 출력하므로, 가로선을 추가하는 경우는 0개부터 3개 추가할때까지만 확인
 * 2. grid에 가로선이 놓인 위치 저장
 *  -> ex: grid[1][2] == true => 첫번째 가로선에서, 세로선 2->3을 잇는 선이 존재
 * 3. checkAnswer() 에서 각 i에 대하여, i에서 출발해서 i에 도착 가능한지 확인
 *  -> 각 i에 대하여, grid 를 확인해서 가로선 1부터 h까지 내려가면서 확인
 */
public class Prob15684 {

    static int n, m, h;
    static boolean[][] grid;
    static final int INF = 1000000000;
    static int ans;
    static boolean found = false;

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        ans = INF;
        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        h = Integer.parseInt(st.nextToken());

        grid = new boolean[h + 1][n];
        for(int i = 0; i < m; i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            grid[a][b] = true;
        }

        /*
        가로선을 총 0개~3개까지 추가할 수 있기 때문에, 각 갯수의 가로선을 추가했을때 답을 구할 수 있는지 확인
         */
        for(int i = 0; i <= 3; i++){
            findAnswer(i, 0, 1, 1);
            if(found){
                break;
            }
        }

        System.out.println(ans == INF || ans > 3 ? -1 : ans);
    }

    public static void findAnswer(int target, int depth, int idxX, int idxY){
        if(target == depth){
            if(checkAnswer()){
                ans = target;
                found = true;
            }

            return;
        }

        /*
        각 (x, y) 좌표에 가로선을 추가
        백트래킹이므로, 가로선을 추가하고, 확인한 뒤, 다시 가로선을 제거
         */
        for(int y = idxY; y < h + 1; y++){
            for(int x = idxX; x < n; x++){
                if(!grid[y][x]){
                    grid[y][x] = true;
                    findAnswer(target, depth + 1, x, y);
                    grid[y][x] = false;
                }
                if(found){
                    return;
                }
            }
            idxX = 0;
        }
    }

    /*
    답을 구할 수 있는지 확인
    grid[y][curX] == true -> 세로선 curX에서 curX + 1로 갈 수 있는 선 존재
    grid[y][curX - 1] == true -> 세로선 curX에서 curX - 1로 갈 수 있는 선 존재
     */
    public static boolean checkAnswer(){
        for(int i = 1; i < n + 1; i++){
            int start = i;
            int curX = i;
            for(int y = 1; y < h + 1; y++){
                if(curX == n){
                    if(grid[y][curX - 1]){
                        curX--;
                    }
                }else if(curX == 1){
                    if(grid[y][curX]){
                        curX++;
                    }
                }else{
                    if(grid[y][curX]){
                        curX++;
                    }else if(grid[y][curX - 1]){
                        curX--;
                    }
                }
            }
            if(start != curX){
                return false;
            }
        }

        return true;
    }
}