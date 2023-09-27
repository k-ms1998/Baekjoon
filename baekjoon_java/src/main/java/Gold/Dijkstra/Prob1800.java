package Gold.Dijkstra;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Gold 1(인터넷 설치)
 *
 * https://www.acmicpc.net/problem/1800
 *
 * Solution: 이분탐색 + 다익스트라
 * 1. 이분탐색을 이용해서 mid (mid = (left + right)/2) 보다 가중치가 큰 간선들을 몇개 써야하는지 확인
 * 2. 다익스트라를 이용해서 1번 노드에서 N 번노드까지 가는데 mid보다 큰 가중치를 가진 간선을 몇개 쓰는지 확인
 * 3. 만약 N번까지 도달하는데 mid보다 큰 가중치를 가진 간선을 k개 보다 적게 쓴다면 mid를 줄여도 됨 -> right = mid - 1
 *  -> mid보다 큰 가중치를 가진 간선을 k개 보다 적게 쓴다면 N번 컴퓨터를 연결할때 원장선생님이 내게 되는 값이 무조건 mid보다 작은 값이라는게 보장됨
 *      -> ex: mid = 10, k = 3 인데, N번 노드를 연결하는데 사용된 간선들의 가중치가 1, 5, 7, 11, 12 => 가장 큰 k개는 무료이기 때문에 원장선생님은 1, 5 중에서 큰 값인 5에 대한 값만 지불하면됨
 *          -> mid의 값을 줄여도 됨
 */
public class Prob1800 {

    static int n, p, k;
    static List<Edge>[] edges;
    static long INF = 0;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        p = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        edges = new List[n + 1];
        for(int i = 1; i < n + 1; i++){
            edges[i] = new ArrayList<>();
        }
        for(int i = 0; i < p; i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());

            edges[a].add(new Edge(b, cost));
            edges[b].add(new Edge(a, cost));
            INF += cost;
        }

        // 이분 탐색
        long answer = -1;
        long left = 0;
        long right = INF;
        while(left <= right){
            long mid = (left + right) / 2;
            if(dijkstra(mid)){ // true이면 mid 값을 줄여도됨 -> 즉, 무조건 mid 값 이하로 원장선생님이 값을 지불하면 됨 -> answer 업데이트
                answer = mid;
                right = mid - 1;
            }else{
                left = mid + 1;
            }
        }

        System.out.println(answer);
    }

    public static boolean dijkstra(long target){
        int[] cnt = new int[n + 1]; // 1번 노드에서 N 번노드까지 가는데 mid보다 큰 가중치를 가진 간선을 몇개 쓰는지 저장
        Arrays.fill(cnt, n + 1);

        cnt[1] = 0;
        Deque<Integer> queue = new ArrayDeque<>();
        queue.offer(1);
        while (!queue.isEmpty()) {
            int node = queue.poll();

            if (node == n) {
                continue;
            }

            for (Edge e : edges[node]) {
                int next = e.node;
                long nc = e.cost;

                int flag = nc <= target ? 0 : 1;
                if (cnt[next] > cnt[node] + flag) {
                    cnt[next] = cnt[node] + flag;
                    queue.offer(next);
                }
            }
        }

        return cnt[n] <= k;
    }

    public static class Edge{
        int node;
        long cost;

        public Edge(int node, long cost) {
            this.node = node;
            this.cost = cost;
        }
    }
}
