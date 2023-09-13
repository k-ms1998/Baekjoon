package Gold.Basic;

import java.io.*;
import java.util.*;

/**
 * Gold 3(선분교차 1)
 *
 * https://www.acmicpc.net/problem/17386
 *
 * Solution: Math(CCW 알고리즘: Counter-Clockwise Algorithm)
 *
 * A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4) & 선분 AB, CD가 있을때:
 *
 * 그러므로, A->B->C랑 A->B->D, C->D->A랑 C->D->B 확인
 *
 *  선분교차: https://snowfleur.tistory.com/98
 *  신발끈 공식: https://m.blog.naver.com/10baba/220723256611
 */
public class Prob17386 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        int x1 = Integer.parseInt(st.nextToken());
        int y1 = Integer.parseInt(st.nextToken());
        int x2 = Integer.parseInt(st.nextToken());
        int y2 = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        int x3 = Integer.parseInt(st.nextToken());
        int y3 = Integer.parseInt(st.nextToken());
        int x4 = Integer.parseInt(st.nextToken());
        int y4 = Integer.parseInt(st.nextToken());

        Point a = new Point(x1, y1);
        Point b = new Point(x2, y2);
        Point c = new Point(x3, y3);
        Point d = new Point(x4, y4);

        int abc = ccw(a, b, c);
        int abd = ccw(a, b, d);
        int cda = ccw(c, d, a);
        int cdb = ccw(c, d, b);

        int answer = 0;
        if(abc * abd < 0 && cda * cdb < 0){
            answer = 1;
        }

        System.out.println(answer);
    }

    public static int ccw(Point p1, Point p2, Point p3) {
        long x1 = p1.x;
        long y1 = p1.y;
        long x2 = p2.x;
        long y2 = p2.y;
        long x3 = p3.x;
        long y3 = p3.y;

        /*
        신발끈 공식
        | x1 x2 x3 x1 |
        | y1 y2 y3 y1 |
        -> ((x1*y2) + (x2*y3) + (x3*y1)) - ((x2*y1) + (x3*y2) + (x1*y3))
         */
        long result = ((x1 * y2) + (x2 * y3) + (x3 * y1)) - ((x2 * y1) + (x3 * y2) + (x1 * y3));
        
        //0 == 일직선, 0보다 크면 반시계 방향, 0보다 작으면 시계 방향
        return result < 0 ? 1 : -1;
    }

    public static class Point{
        int x;
        int y;

        public Point(int x, int y){
            this.x = x;
            this.y = y;
        }
    }
}
