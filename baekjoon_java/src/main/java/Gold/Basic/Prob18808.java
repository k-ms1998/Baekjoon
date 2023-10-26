package Gold.Basic;

import java.io.*;
import java.util.*;

/**
 * Gold 3(스티커 붙이기)
 *
 * https://www.acmicpc.net/problem/18808
 *
 * Solution: 구현
 */
public class Prob18808 {

    static int n, m, k;
    static Sticker[] s;
    static int[][] board;
    static int answer = 0;

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        board = new int[n][m];

        s = new Sticker[k];
        for(int i = 0; i < k; i++){
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            int[][] tmp = new int[r][c];

            for(int y = 0 ; y < r; y++){
                st = new StringTokenizer(br.readLine());
                for(int x = 0; x < c; x++){
                    tmp[y][x] = Integer.parseInt(st.nextToken());
                }
            }

            s[i] = new Sticker(r, c, tmp);
        }

        for(int i = 0; i < k; i++){
            int[][] tmp = s[i].grid;
            for(int d = 0; d < 4; d++){
                boolean found = false;
                int r = tmp.length;
                int c = tmp[0].length;
                // printSticker(tmp, r, c);
                for(int y = 0; y <= n - r; y++){
                    for(int x = 0; x <= m - c; x++){
                        // System.out.println("y=" + y + ", x=" + x);
                        boolean flag = false;
                        for(int sy = 0; sy < r; sy++){
                            for(int sx = 0; sx < c; sx++){
                                if(board[sy + y][sx + x] + tmp[sy][sx] >= 2){
                                    flag = true;
                                    break;
                                }
                            }
                            if(flag){
                                flag = true;
                                break;
                            }
                        }

                        if(!flag){
                            found = true;
                            // System.out.println("!!! FOUND !!!");
                            // printSticker(tmp, r, c);
                            for(int sy = 0; sy < r; sy++){
                                for(int sx = 0; sx < c; sx++){
                                    board[sy + y][sx + x] += tmp[sy][sx];
                                    if(tmp[sy][sx] == 1){
                                        answer++;
                                    }
                                }
                            }
                            break;
                        }
                    }
                    if(found){
                        break;
                    }
                }

                if(!found){
                    tmp = rotate(tmp);
                }else{
                    break;
                }
            }
        }

        // printSticker(board, n, m);
        System.out.println(answer);
    }

    public static int[][] rotate(int[][] src){
        int r = src.length;
        int c = src[0].length;

        // r*c 에서 c*r 로 바꿈 -> 90도 회전
        int[][] dst = new int[c][r];
        for(int y = 0; y < r; y++){
            for(int x = 0; x < c; x++){
                dst[x][r - 1 - y] = src[y][x];
            }
        }

        // printSticker(src, r, c);
        // printSticker(dst, c, r);
        // System.out.println("====================");

        return dst;
    }

    public static void printSticker(int[][] org, int h, int w){
        for(int y = 0; y < h; y++){
            for(int x = 0; x < w; x++){
                System.out.print(org[y][x] + " ");
            }
            System.out.println();
        }
        System.out.println("----------");

    }

    public static class Sticker{
        int r;
        int c;
        int[][] grid;

        public Sticker(int r, int c, int[][] grid){
            this.r = r;
            this.c = c;
            this.grid = grid;
        }
    }
}