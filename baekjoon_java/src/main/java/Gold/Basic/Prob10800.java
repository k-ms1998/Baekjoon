package Gold.Basic;

import java.io.*;
import java.util.*;

/**
 * Gold 3(컬러볼)
 *
 * https://www.acmicpc.net/problem/10800
 *
 * Solution: 정렬 + 누적합
 * cf. 1 <= n <= 200,000 이므로, O(N^2) 으로 풀이시 무조건 시간 초과 발생 + 2차원 배열 생성시 메모리 초과 발생
 *  -> 그러므로, O(N) 선에서 해결하고, 2차원 배열이 생성하지 않고 문제를 풀어야 한다
 * cf. 크기가 큰 공이 크기가 작은 공을 잡는 것이므로, 정렬과 누적합을 이용한다
 *
 * 1. 공들을 크기가 작은 순으로 정렬함
 * 2. 공들을 순서대로 탐색하면서, 지금까지의 공들의 크기의 합을 계속 업데이트 시켜줌(누적합)
 * 3. 이때, 색깔이 같은 공들 끼리는 잡을 수 없기 때문에, 공들의 색깔별로 크기의 누적합을 따로 저장 시켜줌(sums)
 * 4. 공들의 크기가 같아도 서로 잡을 수 없기 때문에, 공들의 크기가 같을때의 상황 고려
 *  4-1. 직전의 공과 크기가 같고 색깔도 같을때
 *      -> 4-1-1. 직전의 공과 크기랑 색깔 모두 같으면, 잡을 수 있는 공들의 크기의 합은 직전의 공과 같음
 *  4-2. 직전의 공과 크기는 같은데 색깔은 다를때
 */
public class Prob10800 {

    static int n;
    static long[] sums;
    static long[] answers;
    static PriorityQueue<Ball> balls;

    static StringBuilder ans = new StringBuilder();
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        sums = new long[n + 1];
        answers = new long[n];
        /**
         * 공들을 크기가 작은 순으로 정렬
         * 크기가 같으면, 색깔이 작은 순으로 정렬
         */
        balls = new PriorityQueue<>(new Comparator<Ball>(){
            @Override
            public int compare(Ball b1, Ball b2){
                if (b1.size == b2.size) {
                    return b1.color - b2.color;
                }
                return b1.size - b2.size;
            }
        });
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int color = Integer.parseInt(st.nextToken());
            int size = Integer.parseInt(st.nextToken());

            balls.offer(new Ball(i, color, size));
        }

        int prevIdx = -1;
        int prevColor = -1;
        int prevSize = -1;
        long prevSum = 0L;
        long sum = 0L;
        while (!balls.isEmpty()) {
            Ball ball = balls.poll();
            int idx = ball.idx;
            int color = ball.color;
            int size = ball.size;

            if (prevSize == size) {
                if (prevColor == color) {
                    /**
                     * 직전의 공과 크기랑 색깔이 같으면, 잡을 수 있는 공들의 크기의 합은 직전의 공과 같음
                     */
                    answers[idx] = answers[prevIdx];
                }else{
                    /**
                     * 직전의 공과 크기만 같을때, 잡을 수 있는 공들의 크기의 합은 공의 크기가 달랐던 마지막 시점에서 누적합 현재 색깔의 누적합
                     * 공의 크기가 달랐던 마지막 시점에서의 누적합(prevSum)에서 빼는 이유는, 크기가 같으면 서로 잡히지 않기 때문에 서로 무시해야됨
                     * 
                     * ex: 크기가 1 2 3 3 3 4 5 이거, 서로 색깔이 다 다를때:
                     *  -> 크기가 3인 공들 끼리는 서로 못 잡아먹어서 합이 누적이 되면 안되어야 함    => prevSum
                     *  -> 4부터는 크기가 3인 공들도 모두 잡아 먹을 수 있기 떄문에, 크기가 3인 공들의 합도 누적이 되어야 함 => Sum
                     *  
                     * 그러므로, 직전의 공과 크기가 같은 경우에는 prevSum 의 값을 업데이트 시켜주지 않음
                     */
                    answers[idx] = prevSum - sums[color];
                }
            }else{
                /**
                 * 직전의 공과 크기가 다르면 바로 잡아 먹을 수 있는 공들의 합을 구하면 됨
                 * -> 잡아 먹을 수 있는 공들의 합 = 지금까지의 누적합 - 같은 색깔 공들의 누적합
                 *  =>(Because, 공의 색깔이 같으면 서로 못 잡아먹기 때문에)
                 *
                 *  prevSum 도 sum 으로 업데이트 시켜줌
                 */
                answers[idx] = sum - sums[color];
                prevSum = sum;
            }

            prevIdx = idx;
            prevColor = color;
            prevSize = size;
            sum += size;
            sums[color] += size;
        }

        for (int i = 0; i < n; i++) {
            ans.append(answers[i]).append("\n");
        }
        System.out.println(ans);
    }

    public static class Ball{
        int idx;
        int color;
        int size;

        public Ball(int idx, int color, int size){
            this.idx = idx;
            this.color = color;
            this.size = size;
        }
    }
}
/*
10
1 10
3 15
1 3
4 8
3 8
5 8
5 8
5 8
6 8
7 8

-> 56
   61
   0
   3
   3
   3
   3
   3
 */
