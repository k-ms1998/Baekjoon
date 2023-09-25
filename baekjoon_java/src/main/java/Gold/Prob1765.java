package Gold;

import java.io.*;
import java.util.*;

/**
 * Gold 2(닭싸움 팀 정하기)
 *
 * https://www.acmicpc.net/problem/1765
 *
 * Solution: BFS
 * 1. 각 노드마다 탐색 시작
 * 2. 친구이면 추가
 * 3. 원수이면 인접한 노드의 인접한 노드들 확인
 *  -> 인접한 노드의 인접한 노드가 원수이면 친구이므로 추가
 */
public class Prob1765 {

    static int n, m;
    static List<Edge>[] edges;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        m = Integer.parseInt(br.readLine());

        edges = new List[n + 1];
        for (int i = 0; i < n + 1; i++) {
            edges[i] = new ArrayList<>();
        }
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            String f = st.nextToken();
            int p = Integer.parseInt(st.nextToken());
            int q = Integer.parseInt(st.nextToken());

            if (f.equals("F")) {
                edges[p].add(new Edge(q, false));
                edges[q].add(new Edge(p, false));
            } else {
                edges[p].add(new Edge(q, true));
                edges[q].add(new Edge(p, true));
            }
        }

        int answer = 0;
        boolean[] visited = new boolean[n + 1];
        for (int i = 1; i < n + 1; i++) {
            if (!visited[i]) {
                visited[i] = true;
                answer++;

                Deque<Integer> queue = new ArrayDeque<>();
                queue.offer(i);
                while(!queue.isEmpty()){
                    int node = queue.poll();

                    for (Edge e : edges[node]) {
                        int next = e.node;
                        boolean isEnemy = e.enemy;
                        if(visited[next]){
                            continue;
                        }

                        if(!isEnemy){
                            visited[next] = true;
                            visited[next] = true;
                            queue.offer(next);
                        }else{ // isEnemy
                            for(Edge ee : edges[next]){
                                int eeNext = ee.node;
                                boolean eeIsEnemy = ee.enemy;
                                if(visited[eeNext]){
                                    continue;
                                }

                                if(eeIsEnemy){
                                    visited[eeNext] = true;
                                    visited[eeNext] = true;
                                    queue.offer(eeNext);
                                }
                            }
                        }
                    }
                }
            }
        }

        System.out.println(answer);
    }

    public static class Edge{
        int node;
        boolean enemy;

        public Edge(int node, boolean enemy) {
            this.node = node;
            this.enemy = enemy;
        }
    }
}
