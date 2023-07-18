package Gold.Graph.Kruskal;

import java.io.*;
import java.util.*;

/**
 * Gold 3(나만 안되는 연애)
 *
 * https://www.acmicpc.net/problem/14621
 *
 * Solution: MST(Kruskal) + Union-Find
 * 1. 간선들을 가중치가 작은 순서대로 정렬
 * 2. 간선들을 하나씩 확인
 *  -> 인접한 노드가 각각 남초 대학교이고 여초 대학교인지 확인
 *  -> Union-Find으로 인접한 두 노드가 이미 연결이 되었있는지 아닌지 확인
 *      -> 연결되어 있지 않으면 간선 추가
 */
public class Prob14621 {

    static int n, m;
    static String[] gender;
    static List<Edge> edges = new ArrayList<>();
    static int[] parent;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        gender = new String[n + 1];
        parent = new int[n + 1];
        st = new StringTokenizer(br.readLine());
        for(int i = 1; i < n + 1; i++){
            gender[i] = st.nextToken();
            parent[i] = i;
        }

        for(int i = 0; i < m; i++){
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());


            edges.add(new Edge(u, v, d));
        }

        Collections.sort(edges, new Comparator<Edge>(){
            @Override
            public int compare(Edge e1, Edge e2) {
                return e1.cost - e2.cost;
            }
        });

        int answer = 0;
        for(Edge edge : edges){
            int s = edge.s;
            int d = edge.d;
            int cost = edge.cost;

            if(gender[s].equals(gender[d])){
                continue;
            }

            int rootS = findRoot(s);
            int rootD = findRoot(d);
            if(rootS == rootD){
                continue;
            }

            parent[d] = rootS;
            parent[rootD] = rootS;
            answer += cost;

        }

        System.out.println(allConnected() ? answer : -1);
    }

    public static boolean allConnected() {
        for(int i = 2; i < n + 1; i++){
            int rootA = findRoot(i);
            int rootB = findRoot(i - 1);
            if (rootA != rootB) {
                return false;
            }
        }

        return true;
    }

    public static int findRoot(int node) {
        if (parent[node] == node) {
            return node;
        }

        int next = findRoot(parent[node]);
        parent[node] = next;
        return parent[node];
    }

    public static class Edge{
        int s;
        int d;
        int cost;

        public Edge(int s, int d, int cost) {
            this.s = s;
            this.d = d;
            this.cost = cost;
        }
    }

}
