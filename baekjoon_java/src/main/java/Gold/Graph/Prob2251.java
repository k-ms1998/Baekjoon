package Gold.Graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Gold 5:
 *
 * https://www.acmicpc.net/problem/2251
 *
 * Solution : Brute Force + BFS
 */
public class Prob2251 {

    /**
     * 물통 A, B, C 의 용량
     */
    static int[] capacity = {0, 0, 0};

    /**
     * 물통은 3개로 고정. 그러므로, 물통 3개간 물을 옮길 수 있는 경우 의 수 모두 생각
     * ts -> td
     * => 2 -> 0, 2 -> 1, 1 -> 0, 1 -> 2, 0 -> 1, 0 -> 2
     */
    static int[] ts = {2, 2, 1, 1, 0, 0};
    static int[] td = {0, 1, 0, 2, 1, 2};

    /**
     * C 뭍롱에 남아 있을 수 있는 물의 양 추척
     */
    static boolean[] visited;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        capacity[0] = Integer.parseInt(st.nextToken());
        capacity[1] = Integer.parseInt(st.nextToken());
        capacity[2] = Integer.parseInt(st.nextToken());

        visited = new boolean[capacity[2] + 1];
        bfs();

        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < capacity[2] + 1; i++) {
            if (visited[i] == true) {
                ans.append(i + " ");
            }
        }
        System.out.println(ans);
    }

    public static void bfs() {
        /**
         *  queue 에 현재 3개의 물통에 들어 있는 물의 양 저장
         *  처음에는 A, B 는 비어 있고, C만 가득 차 있기 때문에 (0, 0, capacity[2]) 추가
         */
        Deque<Integer[]> queue = new ArrayDeque<>();
        queue.offer(new Integer[]{0, 0, capacity[2]});

        /**
         * 물이 들어 있는 조합을 확인하기 위한 변수
         * 이미 확인 한 조합은 다시 확인하지 않도록 하기 위해서 -> 이미 확인한 조합인지 아닌지 확인하지 않으면 무한 루프
         * 
         * 물통이 3개가 있으므로, 3차원 배열이 필요할 것 같지만, 사실은 2차원 배열로 가능
         * -> Because, A + B + C 의 양은 무조건 capacity[2] 입니다.
         *  -> A랑 B에 들어있는 양만 알면 자동으로 C양도 정해짐
         *      -> 그러므로, A랑 B에 들어있는 양의 조합으로만 확인하면됨
         */
        boolean[][] check = new boolean[capacity[0] + 1][capacity[1] + 1];

        while (!queue.isEmpty()) {
            Integer[] cur = queue.poll();

            /**
             * 해당 조합을 이미 확인 했으면 무시
             */
            if (check[cur[0]][cur[1]] == true) {
                continue;
            }

            /**
             * 해당 조합을 확인했다고 업데이트
             */
            check[cur[0]][cur[1]] = true;
            for (int i = 0; i < 6; i++) {
                int s = ts[i];
                int d = td[i];

                int dstCapacity = capacity[d];

                int curS = cur[s];
                int curD = cur[d];

                /**
                 * d의 물통 -> c 의 물통으로 물 옮기기
                 */
                curD += curS;
                curS = 0;
                if(curD > dstCapacity){
                    /**
                     * 이때, d의 물통이 해당 물통의 최대 용량을 넘었을 경우,
                     * 초과된 양만큼 d에서 뺴기 && s에 더하기
                     */
                    int diff = curD - dstCapacity;
                    curD -= diff;
                    curS += diff;
                }

                Integer[] next = new Integer[3];
                for (int t = 0; t < 3; t++) {
                    /**
                     * !! Shallow Copy !!
                     */
                    next[t] = cur[t];
                }
                next[s] = curS;
                next[d] = curD;
                queue.offer(next);

                if(next[0] == 0){
                    visited[next[2]] = true;
                }
            }
        }

    }
}
