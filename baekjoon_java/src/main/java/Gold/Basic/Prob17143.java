package Gold.Basic;

import java.io.*;
import java.util.*;

/**
 * Gold 2(낚시왕)
 *
 * https://www.acmicpc.net/problem/17143
 * 
 * Solution: 구현
 */
public class Prob17143 {

    static int h;
    static int w;
    static int m;

    static int[][] grid;
    static Shark[] sharks;

    static int[] dx = {0, 0, 0, 1, -1};
    static int[] dy = {0, -1, 1, 0, 0};

    static long ans = 0L;

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        h = Integer.parseInt(st.nextToken());
        w = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        grid = new int[h + 1][w + 1];
        sharks = new Shark[m + 1];
        sharks[0] = new Shark(0, 0, 0, 0, 0, 0);
        for(int i = 1; i < m + 1; i++){
            st = new StringTokenizer(br.readLine());
            int y = Integer.parseInt(st.nextToken());
            int x = Integer.parseInt(st.nextToken());
            int speed = Integer.parseInt(st.nextToken());
            int dir = Integer.parseInt(st.nextToken());
            int size = Integer.parseInt(st.nextToken());
            sharks[i] = new Shark(i, x, y, speed, dir, size);
            grid[y][x] = i;
        }

        for(int x = 1; x < w + 1; x++){
            for(int y = 1; y < h + 1; y++){
                if(grid[y][x] != 0){
                    Shark shark = sharks[grid[y][x]];
                    ans += shark.size;
                    sharks[shark.num] = new Shark(shark.num, 0, 0, 0, 0, 0);
                    grid[y][x] = 0;
                    break;
                }
            }
            moveSharks();
            // System.out.println(List.of(sharks));
        }

        System.out.println(ans);
    }

    /**
     * 1. 일단 모든 좌표에 상어가 없는 상태로 초기화(grid[y][x] = 0)
     * 2. 각 상어에 대해서 위치 움직이기
     *  2-1. 위치를 옮길때, 벽에 닿으면 반대 방향으로 움직임
     * 3. 바뀐 위치에 이미 상어가 있으면, 해당 상어와 현재 상어의 크기를 비교해서 업데이트
     */
    public static void moveSharks(){
        /**
         * 상어가 없는 상태로 초기화
         */
        for(int i = 1; i < m + 1; i++){
            Shark shark = sharks[i];
            if(shark.size != 0){
                grid[shark.y][shark.x] = 0;
            }
        }

        for(int i = 1; i < m + 1; i++){
            Shark prev = sharks[i];
            if(prev.size == 0){
                /*
                size == 0 -> 이미 제거된 상어 -> 무시
                 */
                continue;
            }

            int curSpeed = prev.speed;
            int curDir = prev.dir;
            int curSize = prev.size;

            /**
             * 벽에 닿으면 반대 방향으로 움직이기:
             * 1. 일단 상어를 속도만큼 바라보고 있는 방향으로 움직이기
             * 2. 움직인 상어가 벽을 넘어간 위치이면, 넘어간 만큼 다시 벽과 멀어지는 방향으로 업데이트
             * 3. 만약 다시 벽을 넘어간 상태이면, 다시 넘어간 만큼 벽과 멀어지는 방향으로 업데이트
             * 4. 벽 내부에 위치할때까지 반복
             * ex: 벽이 1~4 일고, 상어가 3에 있으면, 속도가 10일때
             * 3 -> 13 -> -5 -> 7 -> 1 => 최종 위치는 1
             */
            int curX = prev.x + curSpeed * dx[curDir];
            int curY = prev.y + curSpeed * dy[curDir];
            int dirChange = 1;
            if(curDir == 1 || curDir == 2){
                while(curY < 1 || curY > h){
                    // System.out.println("tmpY = " + tmpY);
                    if(curY < 1){
                        dirChange *= -1;
                        int diff = 1 - curY;
                        curY = 1 + diff;
                    }else if(curY > h){
                        dirChange *= -1;
                        int diff = curY - h;
                        curY = h - diff;
                    }
                }
            }else if(curDir == 3 || curDir == 4){
                while(curX < 1 || curX > w){
                    // System.out.println("tmpX = " + tmpX);
                    if(curX < 1){
                        dirChange *= -1;
                        int diff = 1 - curX;
                        curX = 1 + diff;
                    }else if(curX > w){
                        dirChange *= -1;
                        int diff = curX - w;
                        curX = w - diff;
                    }
                }
            }

            if(dirChange == -1){
                if(curDir == 1){
                    curDir = 2;
                }else if(curDir == 2){
                    curDir = 1;
                }else if(curDir == 3){
                    curDir = 4;
                }else if(curDir == 4){
                    curDir = 3;
                }
            }

            Shark nextShark = new Shark(i, curX, curY, curSpeed, curDir, curSize);
            sharks[i] = nextShark;
            if(grid[curY][curX] == 0){
                grid[curY][curX] = i;
            }else{
                /*
                바뀐 상어의 위치에 다른 상어가 있는지 없는지 확인
                 */
                Shark inPlaceShark = sharks[grid[curY][curX]];
                if(inPlaceShark.size < nextShark.size){
                    sharks[inPlaceShark.num] =  new Shark(inPlaceShark.num, 0, 0, 0, 0, 0);
                    grid[curY][curX] = i;
                }else{
                    sharks[i] = new Shark(i, 0, 0, 0, 0, 0);
                }
            }
        }
    }

    public static class Shark{
        int num;
        int x;
        int y;
        int speed;
        int dir;
        int size;

        public Shark(int num, int x, int y, int speed, int dir, int size){
            this.num = num;
            this.x = x;
            this.y = y;
            this.speed = speed;
            this.dir = dir;
            this.size = size;
        }

        @Override
        public String toString(){
            return "Shark: [num="+num+", x="+x+", y="+y+", size="+size+"]";
        }
    }
}