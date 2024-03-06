package Platinum.LCA;

import java.io.*;
import java.util.*;

/**
 * 1. N개의 정점으로 이루어진 트리가 있다
 * 2. 이때 두 노드 사이의 거리를 출력하기
 * 	2-1. LCA 사용
 * 	2-2. 두 노드의 공통 조상 찾기
 * 	2-3. 해당 공통 조상에서 각각의 노드까지의 거리의 합이 두 노드 사이의 거리
 * ---
 * 1. 1번 노드를 루트 노드로 두고 트리를 생성하기
 */
public class Prob1761 {
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static StringBuilder sb = new StringBuilder();
	
	static int size;
	static int[][] dp;
	static int[] dist; // 1번(루트)노드에서 다른 정점들까지의 거리
	static int [] height;
	
	static List<Edge>[] edges;
	static int maxH = 0;
	
	public static void main(String[] args) throws IOException{
		size = Integer.parseInt(br.readLine());
		edges = new List[size + 1];
		dp = new int[size + 1][17]; // 일자로 이루어진 트리의 경우 루트와 리프 노드의 최대 높이 차이 = 40_000 => 2^16 = 65536 -> dp는 [size + 1][16]으로 선언
		dist = new int[size + 1];
		height = new int[size + 1];
		for(int idx = 0; idx < size + 1; idx++) {
			edges[idx] = new ArrayList<>();
		}
		height[0] = -1;
		
		for(int idx = 0; idx < size - 1; idx++) {
			st = new StringTokenizer(br.readLine());
			int nodeA = Integer.parseInt(st.nextToken());
			int nodeB = Integer.parseInt(st.nextToken());
			int dist = Integer.parseInt(st.nextToken());
			
			edges[nodeA].add(new Edge(nodeB, dist));
			edges[nodeB].add(new Edge(nodeA, dist));
		}
		
		createTree();
		createDp();

		int commandCount = Integer.parseInt(br.readLine());
		for(int idx = 0; idx < commandCount; idx++) {
			st = new StringTokenizer(br.readLine());
			int nodeA = Integer.parseInt(st.nextToken());
			int nodeB = Integer.parseInt(st.nextToken());
			
			int common = findCommon(nodeA, nodeB);

			// nodeA에서 공통 조상까지의 거리 = (1->nodeA거리) - (1->common까지의 거리)
			int totalDist = (dist[nodeA] - dist[common]) + (dist[nodeB] - dist[common]);
			sb.append(totalDist).append("\n");
		}
		
		System.out.println(sb);
	}
	
	public static int findCommon(int nodeA, int nodeB) {
		int aH = height[nodeA];
		int bH = height[nodeB];
		if(aH < bH) { // nodeB가 nodeA보다 밑에 있다 -> nodeB를 올려준다
			for(int h = maxH; h >= 0; h--) {
				int nextNode = dp[nodeB][h]; // 가장 뒤에서 부터 확인하기
				int nextH = height[nextNode];
				
				if(aH < nextH) {
					nodeB = nextNode;
				}else if(aH == nextH) {
					nodeB = nextNode;
					break;
				}
			}
		}else if(aH > bH){ // nodeA가 nodeB보다 밑에 있다 -> nodeA를 올려준다
			for(int h = maxH; h >= 0; h--) {
				int nextNode = dp[nodeA][h]; // 가장 뒤에서 부터 확인하기
				int nextH = height[nextNode];
				
				if(bH < nextH) {
					nodeA = nextNode;
				}else if(bH == nextH) {
					nodeA = nextNode;
					break;
				}
			}
		}
		
		if(nodeA == nodeB) {
			return nodeA;
		}
		
		/*
		 *  두 노드의 높이가 같아졌지만 서로 다른 노드이다 -> 공통 조상을 찾을때까지 올라간다
		 *  1. 두 노드의 h번째 부모가 같다 -> 공통 조상 중 하나
		 *  2. 공통 조상이 아닌 시점을 찾는다 -> 해당 노드로 둘 다 이동 -> 다시 1번과 2번 반복
		 *  3. 이를 통해 결국 공통 조상의 바로 밑에 있는 노드까지 이동함
		 *  4. 해당 노드의 부모가 공통 조상이기 때문에 해당 노드의 부모를 반환
		 */
		for(int h = maxH; h >= 0; h--) {
			int nextNodeA = dp[nodeA][h];
			int nextNodeB = dp[nodeB][h];
			
			if(nextNodeA != nextNodeB) {
				nodeA = nextNodeA;
				nodeB = nextNodeB;
			}
		}
		
		return dp[nodeA][0];
	}
	
	// 루트가 1이라고 가정하고 트리를 생성한다
	public static void createTree() {
		Deque<Info> queue = new ArrayDeque<>();
		queue.offer(new Info(1, 0, 0));
		boolean[] visited = new boolean[size + 1];

		visited[1] = true;
		while(!queue.isEmpty()) {
			Info info = queue.poll();
			int node = info.node;
			int curDist = info.dist;
			int h = info.height;
			maxH = Math.max(maxH, h);
			
			for(Edge nextEdge : edges[node]) {
				int next = nextEdge.node;
				int nextDist = nextEdge.dist;
				if(!visited[next]) {
					visited[next] = true;
					height[next] = h + 1;
					dp[next][0] = node; // 현재 노드의 부모는 바로 알 수 있기 때문에 설정해준다
					dist[next] = curDist + nextDist;
					queue.offer(new Info(next, curDist + nextDist, h + 1));
				}
			}
		}
		
		maxH = (int) Math.ceil(Math.log(maxH)/ Math.log(2));
	}
	
	// dp 채우기
	public static void createDp() {
		for(int h = 1; h <= maxH; h++) {
			for(int nodeA = 1; nodeA <= size; nodeA++) {
				dp[nodeA][h] = dp[dp[nodeA][h-1]][h-1];
			}
		}	
	}
	
	public static class Edge{
		int node;
		int dist;
		
		public Edge(int node, int dist) {
			this.node = node;
			this.dist = dist;
		}
	}
	
	public static class Info{
		int node;
		int dist;
		int height;
		
		public Info(int node, int dist, int height) {
			this.node = node;
			this.dist = dist;
			this.height = height;
		}
	}
}
/*
7
1 6 13
6 3 9
3 5 7
4 1 3
2 4 20
4 7 2
5
1 6
1 4
2 6
2 3
1 5

4
2 1 2
2 3 3
4 2 1
1
2 4
*/
