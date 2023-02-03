package Review.Silver.Graph.BFS;

import java.io.*;
import java.util.*;

/**
 * Silver 1(숨바꼭질)
 *
 * https://www.acmicpc.net/problem/1697
 *
 * Solution: BFS
 * 1. n, k 중 큰 값을 size라고 할때, 크기가 size + 1인 배열을 선언함(costs)
 * 2. costs에는 각 인덱스에 대해여, 인덱스에 도달하는데 걸리는 최소한의 시간을 저장하고 있음
 * 3. 수빈이의 시작 지점을 덱에 넣으면서 시작
 * 4. 덱에서 하나씩 poll 하고, poll한 위치로부터 걷거나 순간 이동
 * 5. 걷거나 순간 이동한 위치가 범위 안에 있고(1~size), 해당 위치에 도달하는 최단 시간이 업데이트가 될떄 덱에 추가
 * 6. 덱이 빌때까지 반복
 * 7. costs[k]가 최종 값 -> k에 도달하는데 걸리는 최단 시간 저장
 */
public class Prob1697 {

    static final int INF = 1000000000;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        int size = n > k ? n + 1 : k + 1;
        int[] costs = new int[size + 1];
        Arrays.fill(costs, INF);

        Deque<Info> queue = new ArrayDeque<>();
        queue.offer(new Info(n, 0));
        costs[n] = 0;

        while (!queue.isEmpty()) {
            Info cur = queue.poll();
            int num = cur.num;
            int cost = cur.cost;

            if (num == k) {
                continue;
            }

            int nx1 = num - 1;
            int nx2 = num + 1;
            int nx3 = 2 * num;
            if (nx1 >= 0 && nx1 <= size) {
                if(costs[nx1] > cost + 1){
                    costs[nx1] = cost + 1;
                    queue.offer(new Info(nx1, cost + 1));
                }
            }
            if (nx2 >= 0 && nx2 <= size) {
                if(costs[nx2] > cost + 1){
                    costs[nx2] = cost + 1;
                    queue.offer(new Info(nx2, cost + 1));
                }
            }
            if (nx3 >= 0 && nx3 <= size) {
                if(costs[nx3] > cost + 1){
                    costs[nx3] = cost + 1;
                    queue.offer(new Info(nx3, cost + 1));
                }
            }
        }

        System.out.println(costs[k]);
    }

    public static class Info{
        int num;
        int cost;

        public Info(int num, int cost) {
            this.num = num;
            this.cost = cost;
        }
    }
}
