package Gold.Basic;

import java.io.*;
import java.util.*;

/**
 * Gold 3(하늘에서 별똥별이 빗발친다)
 *
 * https://www.acmicpc.net/problem/14658
 *
 * Solution: 구현
 */
public class Prob14658 {

    static int n, m, l, k;
    static Point[] stars;
    static int answer = 1;

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        l = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        stars = new Point[k];
        for(int i = 0; i < k; i++){
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());

            stars[i] = new Point(x, y);
        }

        for(int i = 0; i < k; i++){
            Point cur = stars[i];
            int ax = cur.x;
            int ay = cur.y;
            for(int j = i + 1; j < k; j++){
                Point next = stars[j];
                int bx = next.x;
                int by = next.y;

                int sx = Math.min(ax, bx);
                int sy = Math.min(ay, by);
                int dx = Math.min(n, sx + l);
                int dy = Math.min(m, sy + l);

                int cnt = 0;
                for(int r = 0; r < k; r++){
                    Point check = stars[r];
                    if(inbounds(check.x, check.y, sx, sy, dx, dy)){
                        cnt++;
                    }
                }

                answer = Math.max(answer, cnt);
            }
        }


        System.out.println(k - answer);
    }

    public static boolean inbounds(int x, int y, int ax, int ay, int bx, int by){
        int maxX = Math.max(ax, bx);
        int minX = Math.min(ax, bx);
        int maxY = Math.max(ay, by);
        int minY = Math.min(ay, by);

        return x >= minX && x <= maxX && y >= minY && y <= maxY;
    }

    public static class Point{
        int x;
        int y;

        public Point(int x, int y){
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString(){
            return "{x=" + x + ", y=" + y + "}";
        }
    }
}