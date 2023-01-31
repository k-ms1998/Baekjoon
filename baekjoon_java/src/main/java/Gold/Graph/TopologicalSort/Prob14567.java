package Gold.Graph.TopologicalSort;

import java.io.*;
import java.util.*;

/**
 * Gold 5(선수과목)
 *
 * https://www.acmicpc.net/problem/14567
 *
 * Solution: Topological Sort
 */
public class Prob14567 {

    static int n, m;
    static Stack<Integer>[] edges;
    static int[] inCount;
    static int[] semester;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        edges = new Stack[n + 1];
        inCount = new int[n + 1];
        semester = new int[n + 1];
        for (int i = 1; i < n + 1; i++) {
            edges[i] = new Stack<>();
        }
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            edges[a].push(b);
            inCount[b]++;
        }

        Deque<Node> nodes = new ArrayDeque<>();
        for (int i = 1; i < n + 1; i++) {
            if (inCount[i] == 0) {
                nodes.offer(new Node(i, 1));
                semester[i] = 1;
            }
        }

        while (!nodes.isEmpty()) {
            Node node = nodes.poll();
            int num = node.num;
            int sem = node.sem;

            while (!edges[num].isEmpty()) {
                int adj = edges[num].pop();
                inCount[adj]--;
                if(inCount[adj] == 0){
                    nodes.offer(new Node(adj, sem + 1));
                    semester[adj] = sem + 1;
                }
            }
        }

        StringBuilder ans = new StringBuilder();
        for (int i = 1; i < n + 1; i++) {
            ans.append(semester[i]).append(" ");
        }

        System.out.println(ans);
    }

    public static class Node{
        int num;
        int sem;

        public Node(int num, int sem){
            this.num = num;
            this.sem = sem;
        }
    }
}
