package Review.Gold.Tree;

import java.io.*;
import java.util.*;

/**
 * Gold 2(트리의 높이와 너비)
 *
 * https://www.acmicpc.net/problem/2250
 *
 * Solution: 전위순회 + 중위순회
 * 1. 전위순회로 각 레벨(높이)에 놓인 노드들 찾기
 * 2. 중위순회로 각 노드의 x좌표 구하기
 * 3. 각 레벨별로 노드들을 탐색해서 레벨의 너비 구하기
 */
public class Prob2250 {

    static int n;
    static Node[] nodes;
    static int[] parents;
    static int root;
    static List<Integer>[] byH;
    static List<Integer> byX = new ArrayList<>();

    static final int INF = 100000000;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        nodes = new Node[n + 1];
        parents = new int[n + 1];
        for(int i = 0; i < n; i++){
            st = new StringTokenizer(br.readLine());
            int node = Integer.parseInt(st.nextToken());
            int left = Integer.parseInt(st.nextToken());
            int right = Integer.parseInt(st.nextToken());

            nodes[node] = new Node(node, left, right, 0);
            if(left != -1){
                parents[left] = node;
            }
            if(right != -1){
                parents[right] = node;
            }
        }

        for(int i = 1; i < n + 1; i++){
            if(parents[i] == 0){
                root = i;
                break;
            }
        }

        //전위순회로 각 노드의 높이 구하기
        byH = new List[n + 1];
        for(int i = 0; i < n + 1; i++){
            byH[i] = new ArrayList<>();
        }
        preOrder(root, 1);

        // 중위순회로 각 노드의 x 구하기
        inOrder(root);
        int idx = 1;
        for(int num : byX){
            nodes[num].x = idx;
            idx++;
        }

        // 각 높이를 확인하면서 너비 구하기
        int ansH = 0;
        int ansW = 0;
        for(int h = 1; h < n + 1; h++){
            if(byH[h].size() == 0){
                break;
            }
            int minX = INF;
            int maxX = 0;
            for(int num : byH[h]){
                Node node = nodes[num];
                minX = Math.min(minX, node.x);
                maxX = Math.max(maxX, node.x);
            }

            int curW = maxX - minX + 1;
            if(ansW < curW){
                ansH = h;
                ansW = curW;
            }
        }

        System.out.println(ansH + " " + ansW);
    }

    public static void inOrder(int node) {
        if(node == -1){
            return;
        }

        int left = nodes[node].left;
        int right = nodes[node].right;

        inOrder(left);
        byX.add(node);
        inOrder(right);

    }

    public static void preOrder(int node, int h) {
        if(node == -1){
            return;
        }

        int left = nodes[node].left;
        int right = nodes[node].right;

        byH[h].add(node);
        preOrder(left, h + 1);
        preOrder(right, h + 1);
    }

    public static class Node{
        int node;
        int left;
        int right;
        int x;

        public Node(int node, int left, int right, int x) {
            this.node = node;
            this.left = left;
            this.right = right;
            this.x = x;
        }
    }

}
