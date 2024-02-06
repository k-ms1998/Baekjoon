package Platinum.LCA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 공통 조상 찾기(DP)
 */
public class Prob11438 {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    static int size;
    static int findTotal;
    static int maxH;
    static int[][] dp;
    static List<Integer>[] edges;
    static int[] depth;
    static boolean[] visited;

    public static void main(String args[]) throws IOException {
        size = Integer.parseInt(br.readLine());

        edges = new List[size + 1];
        depth = new int[size + 1];
        visited = new boolean[size + 1];
        for (int idx = 0; idx < size + 1; idx++) {
            edges[idx] = new ArrayList<>();
        }
        /*
        인덱스 0은 노드가 들어있지 않음 -> -1로 설정 -> 0으로 설정시, 루트 노드랑 높이가 같은 것이 되기 때문에 두 개의 노드의 높이를 맞춰줄떄 꼬일 수 있음
        ex: nodeA가 더 밑에 있어서 nodeB로 맞춰 줄려고 하고, nodeB가 루트 노드일때:
            dp[nodeA][5]가 0이면 0번째 노드 확인 -> 높이를 0으로 생각 -> nodeB랑 같은 높이에 있다고 착각 -> 오답
         */
        depth[0] = -1;

        maxH = (int) Math.ceil(Math.log(size + 1) / Math.log(2)) + 1;
        dp = new int[size + 1][maxH];
        for (int idx = 0; idx < size - 1; idx++) {
            st = new StringTokenizer(br.readLine().trim());
            int nodeA = Integer.parseInt(st.nextToken());
            int nodeB = Integer.parseInt(st.nextToken());

            edges[nodeA].add(nodeB);
            edges[nodeB].add(nodeA); // 두 개의 정점 연결 시켜주기
        }

        // 그래프의 루트는 1 -> 1에서 시작해서 트리 생성
        Queue<Node> queue = new ArrayDeque<>();
        queue.offer(new Node(1, 0));
        visited[1] = true;
        depth[1] = 0;
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            int num = node.num;
            int h = node.h;

            for (int next : edges[num]) {
                if (!visited[next]) {
                    visited[next] = true;
                    dp[next][0] = num; // 다음 노드(next)의 2^0번째 조상, 즉 부모 = 현재 노드(node)
                    depth[next] = h + 1;
                    queue.offer(new Node(next, h + 1));
                }
            }
        }

        // dp값 채워주기
        for (int h = 1; h < maxH; h++) {
            for (int node = 1; node < size + 1; node++) {
                int prevParent = dp[node][h - 1];
                dp[node][h] = dp[prevParent][h - 1];
            }
        }

        findTotal = Integer.parseInt(br.readLine());
        for (int findIdx = 0; findIdx < findTotal; findIdx++) {
            st = new StringTokenizer(br.readLine());
            int nodeA = Integer.parseInt(st.nextToken());
            int nodeB = Integer.parseInt(st.nextToken());

            sb.append(lca(nodeA, nodeB)).append("\n");
        }

        System.out.println(sb);
    }

    public static int lca(int nodeA, int nodeB) {
        // 높이 맞추기
        int aH = depth[nodeA];
        int bH = depth[nodeB];
        if(aH > bH){
            for(int h = maxH - 1; h >= 0; h--) {
                int parentA = dp[nodeA][h];
                int parentAH = depth[parentA];
                if (parentAH > bH) {
                    nodeA = parentA;
                }else if(parentAH == bH){
                    nodeA = parentA;
                    break;
                }
            }
        }else if(aH < bH){
            for(int h = maxH - 1; h >= 0; h--) {
                int parentB = dp[nodeB][h];
                int parentBH = depth[parentB];
                if (parentBH > aH) {
                    nodeB = parentB;
                }else if(parentBH == aH){
                    nodeB = parentB;
                    break;
                }
            }
        }
        
        if (nodeA == nodeB) {
            return nodeA;
        }

        /*
        현재 높이를 맞춘 상태
        만약 현재 노드들에서 2^h 만큼 위에 있는 조상을 확인했을때 다를 경우 -> 아직 공통 조상을 찾지 못했음 -> 노드 이동
        (만약 높이 h에 있는 조상을 확인 했을때 둘 다 같은 노드를 가르키고 있다 -> 그러면 높이 0~h는 두 노드 모두 공통 조상을 가지고 있음)
        그러므로, 둘다 2^h만큼 갔을때 공통 조상을 가르키고 있지 않은 시점까지 올라간다
         */
        for (int h = maxH - 1; h >= 0; h--) {
            /*
            둘다 2^h만큼 갔을때 공통 조상을 가르키고 있지 않은 시점까지 올라간다 -> 결국 가장 밑에 있는 공통 조상 바로 밑에 있는 높이까지 올라감
            -> 해당 노드들에서 한번씩만 바로 올라가면 공통 조상을 찾음
            ->그러므로, 마지막에 dp[nodeA][0] 반환
             */
            if (dp[nodeA][h] != dp[nodeB][h]) { 
                nodeA = dp[nodeA][h];
                nodeB = dp[nodeB][h];
            }
        }

        return dp[nodeA][0];
    }

    public static class Node {
        int num;
        int h;

        public Node(int num, int h) {
            this.num = num;
            this.h = h;
        }
    }
}