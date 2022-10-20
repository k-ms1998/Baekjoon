package Gold.Basic;

import java.io.*;
import java.util.*;

/**
 * Gold 1(이사)
 *
 * https://www.acmicpc.net/problem/17371
 */
public class Prob17371 {

    static int n;
    static Pos[] grid;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        /*
         * 0 <= n <= 1000
         * 0 <= n^2 <= 1,000,000
         */
        n = Integer.parseInt(br.readLine());
        grid = new Pos[n];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            Double x = Double.parseDouble(st.nextToken());
            Double y = Double.parseDouble(st.nextToken());
            grid[i] = new Pos(x, y);
        }

        /**
         * 집에서 가장 가까운 편의점은 해당 편의점과 같은 위치에 있는 것
         * -> 그러므로, 집이 하나의 편의점과 같은 좌표에 있다고 가정
         * 
         * 집의 위치를 각 편의점으로 잡은 뒤, 해당 좌표에 대해서 가장 멀리는 편의점을 찾기
         * 가장 멀리 있는 편의점들 중에서 , 거리가 짧으면 평균 거리가 제일 작은 것
         * (Because, 집에 가장 가까운 편의점의 거리는 항상 0이기 때문에, 가장 멀리 있는 편의점들 중에서 거리가 가장 작은 것만 고려)
         *
         * 최종 값으로 출력하는 값이 집의 위치는 맞지만, 결국 해당 좌표로 부터 가장 가까운 거리의 편의점과 가장 멀리 있는 거리의 편의점까지의 거리의 평균만 같으면 답으로 인정되기 떄문에,
         * 집의 위치를 편의점의 위치들 중 하나로 잡아도 문제 없음
         */
        Double minAvg = Double.MAX_VALUE;
        Pos ans = grid[0];
        for (int i = 0; i < n; i++) {
            Pos src = grid[i];
            Double maxDist = 0.0;
            /**
             * 각 좌표로 부터, 다른 좌표까지의 거리 중 가장 멀리 있는 편의점을 구한다
             */
            for (int j = 0; j < n; j++) {
                Pos dst = grid[j];
                Double curDist = calculateDist(src, dst);
                if (maxDist < curDist) {
                    maxDist = curDist;
                }
            }

            /**
             * 가장 멀리 있는 좌표들 중에서, 거리가 가장 짧은 편의점 찾기
             * 이때, 집의 위치는 편의점들 중 하나와 같은 위치에 있기 때문에 ans 값을 src 값으로 업데이트
             */
            if(maxDist < minAvg){
                ans = src;
                minAvg = maxDist;
            }

        }

        System.out.println(ans.x + " " + ans.y);
    }

    public static Double calculateDist(Pos src, Pos dst) {
        Double srcX = src.x;
        Double srcY = src.y;
        Double dstX = dst.x;
        Double dstY = dst.y;

        return Math.sqrt((srcX-dstX)*(srcX-dstX) + (srcY-dstY)*(srcY-dstY));
    }

    public static class Pos{
        Double x;
        Double y;

        public Pos(Double x, Double y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "Pos:[x=" + x + ", y=" + y + "]";
        }
    }
}
