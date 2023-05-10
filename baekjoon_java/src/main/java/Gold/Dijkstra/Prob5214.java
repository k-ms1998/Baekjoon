package Gold.Dijkstra;

import java.io.*;
import java.util.*;

/**
 * Gold 2(환승)
 *
 * https://www.acmicpc.net/problem/5214
 *
 * Solution: Dijkstra
 * 1. 역은 하이퍼루프와 연결되도록 간선 생성 & 하이퍼루프트는 연결된 역들과 간선 생성
 * ->
 * 9 3 2
 * 1 2 3
 * 1 4 5
 * 1번 역과 연결된 하이퍼루프: edges[1] = {1, 2}
 * 1번 하이퍼루프와 연결된 역: loop[1][0] = 1, loop[1][1] = 2
 * ...
 * 
 * 2. 1번 역에서 출발해서, 연결되어 있는 하이퍼 루프들로 이동
 * 3. 하이퍼루프에서 연결된 역들로 이동
 * 4. 약 역 또는 하이퍼루프에 도달하는 최단 역의 수가 갱신될때마다 덱에 추가(다익스트라)
 * 5, 마지막 역에 도착하면 종료
 *
 * 잘못된 풀이:
 * 하이퍼루프를 무시하고 역들끼리 바로 간선으로 연결하기
 * => 간선의 수 = kC2 * m = k(k-1)/2 * m => O(k^2 * m) => 최대 1000*1000*1000 (1<= k, m <= 1000) => 메모리 & 시간 초과
 * 
 * 정답 풀이:
 * 억들과 하이퍼루프 간의 간선만 생각
 * =? 간선의 수 = k * m = O(k * m) => 최대 1000 * 1000 (1<= k, m <= 1000)
 *
 */
public class Prob5214 {

    static int n, k, m;
    static int[] dist; // 각 역까지 도달하는데 거쳐야 되는 역의 수
    static int[] loopDist; // 각 하이퍼루프까지 도달하는데 거쳐야 되는 역의 수
    static List<Integer>[] edges; // 역 -> 하이퍼루프의 간선들
    static int[][] loop; // 하이퍼루프 -> 역까지의 간선들

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        dist = new int[n + 1];
        edges = new List[n + 1];
        for(int i = 0; i < n + 1; i++){
            dist[i] = n + m + 1;;
            edges[i] = new ArrayList<>();
        }

        loopDist = new int[m];
        loop = new int[m][k];
        for(int i = 0; i < m; i++){
            loopDist[i] = n + m + 1;
            String[] arr = br.readLine().split(" ");
            for(int j = 0; j < k; j++) {
                int a = Integer.parseInt(arr[j]);
                loop[i][j] = a; // a == 하이퍼루프와 연결된 역
                edges[a].add(i); // i == 현재 입력받는 중인 하이퍼루프
            }
        }

        Deque<Info> queue = new ArrayDeque<>();
        queue.offer(new Info(1, 1, 0)); // flag == 0 => 현재 역에 위치함; flag == 1 => 현재 하이퍼루프에 위치함
        dist[1] = 1;

        while (!queue.isEmpty()) {
            Info info = queue.poll();
            int node = info.node;
            int cnt = info.cnt;
            int flag = info.flag;

            if (node == n && flag == 0) {
                break;
            }

            if(flag == 0){
                for (int next : edges[node]) {
                    if (loopDist[next] > cnt) {
                        loopDist[next ] = cnt;
                        queue.offer(new Info(next, cnt, 1)); // 역에서 하이퍼루프로 갈때는 새로운 역을 거치지 않기 떄문에 cnt 값 증가 X
                    }
                }
            }else{
                for (int i = 0; i < k; i++) {
                    int next = loop[node][i];
                    if(dist[next] > cnt + 1){
                        dist[next] = cnt + 1;
                        queue.offer(new Info(next, cnt + 1, 0));
                    }
                }
            }
        }

        System.out.println(dist[n] == n + m + 1 ? -1 : dist[n]);
    }

    public static class Info{
        int node;
        int cnt;
        int flag;

        public Info(int node, int cnt, int flag){
            this.node = node;
            this.cnt = cnt;
            this.flag = flag;
        }
    }

}
