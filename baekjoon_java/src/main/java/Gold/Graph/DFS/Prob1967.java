package Gold.Graph.DFS;

import java.io.*;
import java.util.*;

/**
 * Gold 4(트리의 지름)
 *
 * https://www.acmicpc.net/problem/1967
 *
 * Solution: DFS
 * 1. 리프 노드까지의 거리를 구하면, 중간에 나오는 모든 노드들까지의 거리도 계산 가능하기 때문에, 우선 루트에서 각 리프 노드들까지의 거리 찾기
 * 2. 리프 노드에서 시작해서 위로 올라가야하기 때문에, 각 노드의 parent 도 저장해줌
 * 3. 루트로 부터 거리가 가장 먼 리프 노드 찾기
 * 4. 해당 리프 노드로부터 가장 거리가 먼 리프 노드 찾기
 * 5. 트리의 지름 = (루트에서 3번에서 찾은 노드까지의 거리 + 루트에서 4번에서 찾은 노드까지의 거리) OR (루트부터 가장 멀리있는 노드까지의 거리)
 *  -> 트리에 따라서, 리프 노드 간의 거리 보다, 단순히 루트에서 하나의 리프 노드까지의 거리가 더 클 수도 있음
 */
public class Prob1967 {

    static int n;
    static List<Node>[] edges;
    static int[] distFromRoot;
    static int[] parent;
    static boolean[] visited;
    static boolean[] isLeaf;
    static boolean[] parentVisited;
    static int ans = 0;

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());

        edges = new List[n + 1];
        distFromRoot = new int[n + 1];
        parent = new int[n + 1];
        visited = new boolean[n + 1];
        isLeaf = new boolean[n + 1];
        parentVisited = new boolean[n + 1];
        for(int i = 1; i < n + 1; i++){
            edges[i] = new ArrayList<>();
            parent[i] = i;
        }
        for(int i = 0; i < n - 1; i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            edges[a].add(new Node(b, c));
            edges[b].add(new Node(a, c));
        }
        visited[1] = true;
        distFromRoot[1] = 0;
        findLeaf(1, 0, 1);

        /**
         * 루트로부터 가장 멀리 있는 노드 찾기(해당 노드는 리프 노드임)
         */
        int sLeaf = 0;
        int sLeafDist = 0;
        for(int i = 1; i < n + 1; i++){
            if(isLeaf[i]){
                if(sLeafDist < distFromRoot[i]){
                    sLeaf = i;
                    sLeafDist = distFromRoot[i];
                }
            }
        }
        /**
         * 루트에서 해당 노드까지 가는데 거치는 모든 노드들을 찾아서 저장(parentVisited)
         */
        parentVisited[1] = true;
        checkParents(sLeaf);
        
        for(int i = 1; i < n + 1; i++){
            /**
             * 모든 리프 노들에 대해서 위에서 찾은 노드까지의 거리 계산
             * 1. findCommonParent를 통해 두 노드의 공통 조상 찾기
             * 2. 거리 계산
             */
            if(isLeaf[i]){
                int nextLeaf = i;
                int commonParent = findCommonParent(nextLeaf);
                int curDist = distFromRoot[sLeaf] + distFromRoot[nextLeaf] - 2 * distFromRoot[commonParent];
                ans = Math.max(ans, curDist);
            }
            /**
             * 리프 노드들끼리의 거리보다 단순히 루트로 부터 시작한 노드의 거리가 더 멀수 있기 때문에 확인
             * (일자로 이어진 트리가 이런 경우: 
             *  ex: 1->2->3->4 이면, 트리의 지름은 1->4까지의 거리
             *  )
             */
            ans = Math.max(ans, distFromRoot[i]);
        }

        System.out.println(ans);
    }

    public static void checkParents(int node){
        if(parent[node] == node){
            parentVisited[node] = true;
            return;
        }

        parentVisited[node] = true;
        checkParents(parent[node]);
    }

    /**
     * parentVisited 루트에서 가장 멀리 있는 리프 노드의 모든 조상들을 저장하고 있음
     * 
     * 하나의 리프 노드에서 시작해서 루트까지 갈때, parentVisited 에 존재하는 노드가 있으면, 해당 노드가 공통 조상
     */
    public static int findCommonParent(int node){
        if(parentVisited[node]){
            return node;
        }

        return findCommonParent(parent[node]);
    }

    /**
     * 리프 노드들을 찾고, 루트로 부터 각 노드들까지의 거리를 계산하고(distFromNode), 각 노드의 부모 업데이트(parent)
     */
    public static void findLeaf(int node, int curCost, int prevNode){
        parent[node] = prevNode;

        boolean checkIfLeaf = true;
        for(Node adj: edges[node]){
            int adjNode = adj.b;
            int adjCost = adj.c;

            if(!visited[adjNode]){
                checkIfLeaf = true;
                visited[adjNode] = true;
                distFromRoot[adjNode] = curCost + adjCost;
                findLeaf(adjNode, curCost + adjCost, node);
                visited[adjNode] = false;
            }
        }

        if(checkIfLeaf && !isLeaf[node]){
            isLeaf[node] = true;
        }
    }

    public static class Node{
        int b;
        int c;

        public Node(int b, int c){
            this.b = b;
            this.c = c;
        }
    }
}
/*
4
1 2 1
2 3 2
3 4 3

5
1 2 1
2 3 2
3 4 3
3 5 1
*/