package Gold.Basic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Gold 5 (다각형의 면적)
 *
 * https://www.acmicpc.net/problem/2166
 *
 * Solution: 신발끈 공식
 * -> https://namu.wiki/w/%EC%8B%A0%EB%B0%9C%EB%81%88%20%EA%B3%B5%EC%8B%9D
 */
public class Prob2166 {

    static int n;
    static Pos[] points;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        points = new Pos[n + 1];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            points[i] = new Pos(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }
        points[n] = points[0];

        /**
         * 신발끈 공식으로 다각형의 넓이 구하기
         * 이때, 모든 좌표를 시게 또는 반시계 방향으로 순서대로 계산해야됨
         * (이미 문제 조건에서 다각형을 이루는 순서대로 N개의 점을 입력하기 때문에 입력 받은 좌표 순서대로 계산하면 됨)
         */
        long sumA = 0L;
        long sumB = 0L;
        for (int i = 1; i < n + 1; i++) {
            long tmpSumA = points[i - 1].y * points[i].x;
            long tmpSumB = points[i - 1].x * points[i].y;

            sumA += tmpSumA;
            sumB += tmpSumB;
        }
        double ans = Math.abs((sumA - sumB))/2.0;
        // 신발끈 공식 끝
        
        System.out.println(String.format("%.1f", ans));
    }

    static class Pos{
        long x;
        long y;

        public Pos(long x, long y) {
            this.x = x;
            this.y = y;
        }
    }
}
