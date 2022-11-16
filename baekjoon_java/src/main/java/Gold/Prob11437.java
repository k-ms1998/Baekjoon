package Gold;

import java.io.*;
import java.util.*;

/**
 * Gold 3(LCA)
 *
 * https://www.acmicpc.net/problem/11437
 *
 * Solution: Tree + 가장
 */
public class Prob11437 {

    static int n, m;
    static List<Integer>[] tree;
    static int[] parent;
    static int[] depth;

    static StringBuilder ans = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        tree = new List[n + 1];
        parent = new int[n + 1];
        depth = new int[n + 1];
        for (int i = 1; i < n + 1; i++) {
            tree[i] = new ArrayList<>();
            parent[i] = i;
            depth[i] = n + 1;
        }
        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int p = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            tree[p].add(c);
            tree[c].add(p);
        }
        createTree();

        m = Integer.parseInt(br.readLine());
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            ans.append(findNearestParent(a, b)).append("\n");
        }

        System.out.println(ans);
    }

    /**
     * 1. 각 노드의 높이를 맞춰준다
     * 2. 높이가 같아지면, 서로 같아질때까지 트리의 위로 올라간다
     * 3. 제일 먼저 두 노드가 같아지는 지점이 가장 가까운 부모
     */
    public static int findNearestParent(int a, int b) {
        int aDepth = depth[a];
        int bDepth = depth[b];

        while(aDepth > bDepth) {
            a = parent[a];
            aDepth--;
        }

        while(bDepth > aDepth) {
            b = parent[b];
            bDepth--;
        }

        while(a!=b) {
            a = parent[a];
            b = parent[b];
        }
        return a;
    }

    /**
     * 루트 노드는 정해져 있으므로, 루트 노드부터 시작해서 트리를 생성하기
     */
    public static void createTree() {
        /**
         * 루트 노드는 항상 1로 정해져 있으며, 루트 노드부터 설정해주기
         */
        Deque<Node> queue = new ArrayDeque<>();
        queue.offer(new Node(1, 0));
        depth[1] = 0;
        while (!queue.isEmpty()) {
            Node curNode = queue.poll();
            int curParent = curNode.num;
            int curDepth = curNode.depth;
            List<Integer> rootChildren = tree[curParent];
            for (Integer child : rootChildren) {
                if (child == 1) {
                    continue;
                }
                if(depth[curParent] < depth[child]){
                    parent[child] = curParent;
                    depth[child] = curDepth + 1;
                    queue.offer(new Node(child, curDepth + 1));
                }
            }
        }
    }

    public static class Node{
        int num;
        int depth;

        public Node(int num, int depth) {
            this.num = num;
            this.depth = depth;
        }
    }
}
