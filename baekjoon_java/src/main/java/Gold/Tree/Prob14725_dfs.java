package Gold.Tree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Gold 3(개미굴)
 *
 * https://www.acmicpc.net/problem/14725
 *
 * Solution: Tree(DFS)
 */
public class Prob14725_dfs {

    static int n;
    static Node tree = new Node();

    static int maxDepth = 0;
    static StringBuilder ans = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int k = Integer.parseInt(st.nextToken());
            maxDepth = Math.max(maxDepth, k);

            String root = st.nextToken();
            if (!tree.children.containsKey(root)) {
                tree.children.put(root, new Node());
            }
            Node tmp = tree.children.get(root);
            for (int j = 0; j < k - 1; j++) {
                String nextString = st.nextToken();
                if (!tmp.children.containsKey(nextString)) {
                    tmp.children.put(nextString, new Node());
                }
                tmp = tmp.children.get(nextString);
            }
        }
//        System.out.println("tree = " + tree);

        List<String> roots = tree.children.keySet().stream().collect(Collectors.toList());
        Collections.sort(roots);
        for (String r : roots) {
            dfs(0, r, tree, "");
        }

        System.out.println(ans);
    }

    public static void dfs(int depth, String key, Node subTree, String spacer) {
        if (depth == maxDepth) {
            return;
        }
        ans.append(spacer).append(key).append("\n");

        Node nextSubTree = subTree.children.get(key);
        List<String> nextChildren = nextSubTree.children.keySet().stream().collect(Collectors.toList());
        Collections.sort(nextChildren);
        for (String child : nextChildren) {
            dfs(depth + 1, child, nextSubTree, spacer + "--");
        }

    }

    static class Node{
        HashMap<String, Node> children = new HashMap<>();

        @Override
        public String toString() {
            return "Node{" +
                    "children=" + children +
                    '}';
        }
    }
}