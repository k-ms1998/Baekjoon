package Gold.Tree;
import java.io.*;
import java.util.*;

/**
 * Gold 1(그래프 트리 분할)
 *
 * https://www.acmicpc.net/problem/22954
 *
 * Solution: 트리
 */
public class Prob22954 {

    static int n, m;
    static int[] parent;
    static Set<Integer> roots = new HashSet<>();
    static List<Edge> es = new ArrayList<>();
    static StringBuilder ans = new StringBuilder();

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        parent = new int[n + 1];
        for(int i = 1; i < n + 1; i++){
            parent[i] = i;
        }

        for(int i = 0; i < m; i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            int rootA = findRoot(a);
            int rootB = findRoot(b);


            if(rootA != rootB){
                parent[b] = rootA;
                parent[rootB] = rootA;
            }

            es.add(new Edge(i + 1, a, b));
        }

        findRoots();
        // System.out.println("roots=" + roots);
        if(roots.size() >= 3 || n <= 2){
            ans.append(-1);
        }else if(roots.size() == 2){
            int rootA = -1;
            int rootB = -1;

            for(int r : roots){
                if(rootA == -1){
                    rootA = r;
                }else{
                    rootB = r;
                }
            }

            boolean[] visited = new boolean[n + 1];
            List<Integer> nodesA = new ArrayList<>();
            List<Integer> nodesB = new ArrayList<>();
            List<Integer> edgesA = new ArrayList<>();
            List<Integer> edgesB = new ArrayList<>();

            List<Info>[] edges = new List[n + 1];
            for(int i = 1; i < n + 1; i++){
                edges[i] = new ArrayList<>();
            }
            for(int i = 0; i < es.size(); i++){
                Edge e = es.get(i);
                int a = e.a;
                int b = e.b;

                edges[a].add(new Info(i + 1, b));
                edges[b].add(new Info(i + 1, a));
            }

            visited[rootA] = true;
            visited[rootB] = true;
            nodesA.add(rootA);
            nodesB.add(rootB);

            Deque<Info> queue = new ArrayDeque<>();
            queue.offer(new Info(-1, rootA));

            while(!queue.isEmpty()){
                Info info = queue.poll();
                int node = info.adj;

                for(Info ii : edges[node]){
                    int num = ii.num;
                    int next = ii.adj;
                    if(!visited[next] && next != rootB){
                        queue.offer(ii);
                        visited[next] = true;
                        nodesA.add(next);
                        edgesA.add(num);
                    }
                }
            }

            queue = new ArrayDeque<>();
            queue.offer(new Info(-1, rootB));

            while(!queue.isEmpty()){
                Info info = queue.poll();
                int node = info.adj;

                for(Info ii : edges[node]){
                    int num = ii.num;
                    int next = ii.adj;
                    if(!visited[next] && next != rootA){
                        queue.offer(ii);
                        visited[next] = true;

                        nodesB.add(next);
                        edgesB.add(num);
                    }
                }
            }

            if(nodesA.size() == nodesB.size()){
                ans.append(-1);
            }else{
                ans.append(nodesA.size() + " " + nodesB.size()).append("\n");
                for(int node : nodesA){
                    ans.append(node).append(" ");
                }
                ans.append("\n");
                for(int edge : edgesA){
                    ans.append(edge).append(" ");
                }
                ans.append("\n");
                for(int node : nodesB){
                    ans.append(node).append(" ");
                }
                ans.append("\n");
                for(int edge : edgesB){
                    ans.append(edge).append(" ");
                }
            }
        }else{
            parent = new int[n + 1];
            for(int i  = 0; i < n + 1; i++){
                parent[i] = i;
            }

            List<Integer> used = new ArrayList<>();
            for(int i = 0; i < m; i++){
                Edge e = es.get(i);

                int a = e.a;
                int b = e.b;

                int rootA = findRoot(a);
                int rootB = findRoot(b);

                if(rootA != rootB){
                    parent[b] = rootA;
                    parent[rootB] = rootA;
                    used.add(i + 1);
                }
            }

            // System.out.println("used=" + used);
            List<Info>[] edges = new List[n + 1];
            for(int i = 1; i < n + 1; i++){
                edges[i] = new ArrayList<>();
            }
            for(int i : used){
                Edge e = es.get(i - 1);
                int a = e.a;
                int b = e.b;

                edges[a].add(new Info(i, b));
                edges[b].add(new Info(i, a));
            }

            boolean flag = true;
            for(int i : used){
                Edge e = es.get(i - 1);
                int a = e.a;
                int b = e.b;

                List<Integer> nodesA = new ArrayList<>();
                List<Integer> edgesA = new ArrayList<>();
                List<Integer> nodesB = new ArrayList<>();
                List<Integer> edgesB = new ArrayList<>();
                boolean[] v = new boolean[n + 1];

                Deque<Info> queue = new ArrayDeque<>();
                queue.offer(new Info(-1, a));
                v[a] = true;
                nodesA.add(a);
                while(!queue.isEmpty()){
                    Info info = queue.poll();
                    int node = info.adj;

                    for(Info ii : edges[node]){
                        int num = ii.num;
                        int next = ii.adj;
                        if(!v[next] && next != b){
                            queue.offer(ii);
                            v[next] = true;
                            nodesA.add(next);
                            edgesA.add(num);
                        }
                    }
                }

                queue = new ArrayDeque<>();
                queue.offer(new Info(-1, b));
                v[b] = true;
                nodesB.add(b);
                while(!queue.isEmpty()){
                    Info info = queue.poll();
                    int node = info.adj;

                    for(Info ii : edges[node]){
                        int num = ii.num;
                        int next = ii.adj;
                        if(!v[next] && next != a){
                            queue.offer(ii);
                            v[next] = true;

                            nodesB.add(next);
                            edgesB.add(num);
                        }
                    }
                }

                if(nodesA.size() != nodesB.size()){
                    ans.append(nodesA.size() + " " + nodesB.size()).append("\n");
                    for(int node : nodesA){
                        ans.append(node + " ");
                    }
                    ans.append("\n");
                    for(int edge : edgesA){
                        ans.append(edge + " ");
                    }
                    ans.append("\n");
                    for(int node : nodesB){
                        ans.append(node + " ");
                    }
                    ans.append("\n");
                    for(int edge : edgesB){
                        ans.append(edge + " ");
                    }
                    ans.append("\n");

                    flag = false;
                    break;
                }

                if(flag){
                    ans.append(-1);
                }
            }
        }

        System.out.println(ans);
    }

    public static void findRoots(){
        for(int i = 1; i < n + 1; i++){
            int root = findRoot(i);
            roots.add(root);
        }
    }

    public static int findRoot(int node){
        if(parent[node] == node){
            return node;
        }

        int next = findRoot(parent[node]);
        parent[node] = next;
        return parent[node];
    }

    public static class Edge{
        int num;
        int a;
        int b;

        public Edge(int num, int a, int b){
            this.num = num;
            this.a = a;
            this.b = b;
        }
    }

    public static class Info{
        int num;
        int adj;

        public Info(int num, int adj){
            this.num = num;
            this.adj = adj;
        }
    }
}
