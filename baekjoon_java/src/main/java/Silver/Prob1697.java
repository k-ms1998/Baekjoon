package Silver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Silver 1: BFS
 *
 * 문제
 * 수빈이는 동생과 숨바꼭질을 하고 있다. 수빈이는 현재 점 N(0 ≤ N ≤ 100,000)에 있고, 동생은 점 K(0 ≤ K ≤ 100,000)에 있다. 수빈이는 걷거나 순간이동을 할 수 있다.
 * 만약, 수빈이의 위치가 X일 때 걷는다면 1초 후에 X-1 또는 X+1로 이동하게 된다. 순간이동을 하는 경우에는 1초 후에 2*X의 위치로 이동하게 된다.
 * 수빈이와 동생의 위치가 주어졌을 때, 수빈이가 동생을 찾을 수 있는 가장 빠른 시간이 몇 초 후인지 구하는 프로그램을 작성하시오.
 *
 * 입력
 * 첫 번째 줄에 수빈이가 있는 위치 N과 동생이 있는 위치 K가 주어진다. N과 K는 정수이다.
 *
 * 출력
 * 수빈이가 동생을 찾는 가장 빠른 시간을 출력한다.
 *
 * Solution:
 * 1. BFS로 탐색
 * 2. 해당 X 위치를 이미 방문한 적이 있으면 무시 -> 무시하지 않을 경우 무한 루프에 빠짐
 * 3. 위치 X에 최초로 도착 했을때가 도달하는데 걸리는 최단 시간이므로, 한번 도달 했으면 더 이상 걸린 시간 업데이트 X
 */
public class Prob1697 {
    public static int src;
    public static int dst;
    public static int ans = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] conditions = br.readLine().split(" ");
        src = Integer.valueOf(conditions[0]);
        dst = Integer.valueOf(conditions[1]);

        bfs();
        System.out.println(ans);
    }

    /**
     * BFS
     */
    public static void bfs() {
        /**
         * nextNum : 다음에 방문할 좌표들 모음
         * visited : 해당 위치에 도달하는데 걸린 최단 시간
         */
        Deque<Integer> nextNum = new ArrayDeque<>();
        nextNum.offer(src);
        Integer[] visited = new Integer[100001];
        for (int i = 0; i < 100001; i++) {
            visited[i] = 0;
        }
        while (true) {
            int currentNum = nextNum.poll();
            if (currentNum == dst) {
                ans = visited[currentNum];
                break;
            }
            if (currentNum < 0 || currentNum > 100000) {
                continue;
            }

            int numSub = currentNum - 1;
            int numAdd = currentNum + 1;
            int numMul = 2 * currentNum;

            int currentCnt = visited[currentNum];
            /**
             * visited[x] == 0 -> 위치 x 에 방문한적 없음
             */
            if (numSub >= 0 && numSub <= 100000) {
                if(visited[numSub] == 0){
                    nextNum.offer(numSub);
                    visited[numSub] = currentCnt + 1;
                }
            }
            if (numAdd >= 0 && numAdd <= 100000) {
                if(visited[numAdd] == 0){
                    nextNum.offer(numAdd);
                    visited[numAdd] = currentCnt + 1;
                }
            }
            if (numMul >= 0 && numMul <= 100000) {
                if(visited[numMul] == 0){
                    nextNum.offer(numMul);
                    visited[numMul] = currentCnt + 1;
                }
            }
        }

    }
}
