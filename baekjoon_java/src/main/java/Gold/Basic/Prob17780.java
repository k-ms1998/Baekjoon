package Gold.Basic;

import java.io.*;
import java.util.*;

/**
 * Gold 2(새로운 게임)
 * 
 * https://www.acmicpc.net/problem/17780
 * 
 * Solution: 구현
 */
public class Prob17780 {

    static int n, k;
    static int[][] grid;
    static Horse[] horses;
    static List<Integer>[][] list;

    static int[] dx = {1, -1, 0, 0};
    static int[] dy = {0, 0, -1, 1};

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        grid = new int[n][n];
        list = new List[n][n];
        for(int y = 0; y < n; y++){
            st = new StringTokenizer(br.readLine());
            for(int x = 0; x < n; x++){
                grid[y][x] = Integer.parseInt(st.nextToken());
                list[y][x] = new ArrayList<>();
            }
        }


        horses = new Horse[k];
        for(int i = 0; i < k; i++){
            st = new StringTokenizer(br.readLine());
            int y = Integer.parseInt(st.nextToken()) - 1;
            int x = Integer.parseInt(st.nextToken()) - 1;
            int d = Integer.parseInt(st.nextToken()) - 1;

            horses[i] = new Horse(d, x, y);
            list[y][x].add(i);
        }

        int answer = 0;

        while(answer <= 1000){
            // printList();
            if(check()){
                break;
            }

            for(int i = 0; i < k; i++){
                Horse cur = horses[i];
                int d = cur.d;
                int x = cur.x;
                int y = cur.y;

                if(list[y][x].size() == 0){
                    continue;
                }
                if(list[y][x].get(0) != i){
                    continue;
                }

                int nx = x + dx[d];
                int ny = y + dy[d];

                boolean flag = false;
                if(nx < 0 || ny < 0 || nx >= n || ny >= n){
                    d = d == 0 ? 1 : d == 1 ? 0 : d == 2 ? 3 : 2;
                    horses[i].d = d;
                    flag = true;
                }else{
                    if(grid[ny][nx] == 2){
                        d = d == 0 ? 1 : d == 1 ? 0 : d == 2 ? 3 : 2;
                        horses[i].d = d;
                        flag = true;
                    }else if(grid[ny][nx] == 1){
                        Collections.reverse(list[y][x]);
                        list[ny][nx].addAll(list[y][x]);
                        list[y][x] = new ArrayList<>();

                    }else{
                        list[ny][nx].addAll(list[y][x]);
                        list[y][x] = new ArrayList<>();
                    }
                }

                if(flag){
                    nx = x + dx[d];
                    ny = y + dy[d];

                    if(nx >= 0 && ny >= 0 && nx < n && ny < n){
                        if(grid[ny][nx] != 2){
                            if(grid[ny][nx] == 1){
                                Collections.reverse(list[y][x]);
                                list[ny][nx].addAll(list[y][x]);
                                list[y][x] = new ArrayList<>();

                            }else{
                                list[ny][nx].addAll(list[y][x]);
                                list[y][x] = new ArrayList<>();
                            }
                        }else{
                            nx = x;
                            ny = y;
                        }
                    }else{
                        nx = x;
                        ny = y;
                    }
                }

                for(int h : list[ny][nx]){
                    horses[h].x = nx;
                    horses[h].y = ny;
                }

            }

            answer++;
        }


        System.out.println(answer > 1000 ? -1 : answer);
    }

    public static boolean check(){
        for(int y = 0; y < n; y++){
            for(int x = 0; x < n; x++){
                if(list[y][x].size() >= 4){
                    return true;
                }
            }
        }

        return false;
    }

    public static void printList(){
        for(int y = 0; y < n; y++){
            for(int x = 0; x < n; x++){
                System.out.print(list[y][x] + " ");
            }
            System.out.println();
        }

        System.out.println();
    }

    public static class Horse{
        int d;
        int x;
        int y;

        public Horse(int d, int x, int y){
            this.d = d;
            this.x = x;
            this.y = y;
        }
    }
}