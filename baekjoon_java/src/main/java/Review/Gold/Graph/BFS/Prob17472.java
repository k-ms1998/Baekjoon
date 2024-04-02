package Review.Gold.Graph.BFS;

import java.io.*;
import java.util.*;

/**
 * 1. 바다와 땅으로 이루어진 격자에서 모든 섬을  다리로 연결할 수 있을때, 다리 길이의 최솟값을 구하기
 * 	1-1. 다리는 바다에만 건설 가능
 * 	1-2. 다리의 방향이 중간에 바뀌면 안된다
 * 	1-3. 다리의 길이는 2이상이어야한다
 * --------
 * 1. 인접해 있는 땅들의 하나로 묶는다 -> Flood-Fill
 * 	1-1. 각 땅이 있는 좌표를 순서대로 탐색
 * 	1-2. 인접해 있는 땅들로 이동하면서 번호 부여
 * 2. 각 섬마다 다른 섬까지 연결하는데 필요한 최단 거리 구하기
 */
public class Prob17472 {
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static StringBuilder sb = new StringBuilder();

	static int height, width;
	static int[][] grid;
	
	static int[][] island;
	static List<Point>[] islandList;
	static int[][] dist;
	static List<Point> land = new ArrayList<>();
	static int islandNum = 1;
	static int[] parent;
	
	static final int[] dCol = {1, 0, -1, 0};
	static final int[] dRow = {0, 1, 0, -1};
	static final int INF = 1_000_000_000;
	
	public static void main(String[] args) throws IOException{
		st = new StringTokenizer(br.readLine());
		height = Integer.parseInt(st.nextToken());
		width = Integer.parseInt(st.nextToken());
		
		grid = new int[height][width];
		island = new int[height][width];
		islandList = new List[7];
		dist = new int[7][7];
		parent = new int[7];
		for(int idx = 0; idx < 7; idx++) {
			islandList[idx] = new ArrayList<>();
			Arrays.fill(dist[idx], INF);
			parent[idx] = idx;
		}
		
		for(int row = 0; row < height; row++) {
			st = new StringTokenizer(br.readLine());
			for(int col = 0; col < width; col++) {
				grid[row][col] = Integer.parseInt(st.nextToken());
				if(grid[row][col] == 1) {
					land.add(new Point(col, row));
				}
			}
		}
		
		flood();
//		printArr(island);
		
		for(int start = 1; start < islandNum; start++) {
			dist[start][start] = 0;
			connect(start);
		}
		
		int answer = unionIsland();
		System.out.println(answer < INF ? answer : -1);
	}
	
	public static int unionIsland() {
		int totalDist = 0;
		
		PriorityQueue<Edge> pq = new PriorityQueue<>(new Comparator<Edge>() {
			@Override
			public int compare(Edge e1, Edge e2) {
				return e1.d - e2.d;
			}
		});
		for(int start = 1; start < islandNum; start++) {
			for(int end = start + 1; end < islandNum; end++) {
				pq.add(new Edge(start, end, dist[start][end]));
			}
		}
		
//		System.out.println(pq);
		while(!pq.isEmpty()) {
			Edge edge = pq.poll();
			int start = edge.nodeA;
			int end = edge.nodeB;
			int d = edge.d;
			if(d < 2) {
				continue;
			}

			int rootStart = findRoot(start);
			int rootEnd = findRoot(end);
			
			if(rootStart != rootEnd) {
//				System.out.println(edge);
				parent[rootEnd] = rootStart;
				parent[end] = rootStart;
				totalDist += d;
				totalDist = Math.min(INF, totalDist);
			}
		}
		
		return totalDist;
	}
	
	public static void connect(int start) {
		for(Point p : islandList[start]) {
			if(!isOuter(p)) { // 바다와 인접한 땅이 아닐경우
				continue;
			}
			int startCol = p.col;
			int startRow = p.row;
			
			for(int dir = 0; dir < 4; dir++) {
				int len = 0;
				int nCol = startCol;
				int nRow = startRow;
				while(true) {
					len++;
					nCol += dCol[dir];
					nRow += dRow[dir];
					
					if(nCol < 0 || nRow < 0 || nCol >= width || nRow >= height) {
						break;
					}
					if(grid[nRow][nCol] == 1) {
						if(len - 1 < 2) {
							break;
						}
						
						int adjIslandNum = island[nRow][nCol];
						dist[start][adjIslandNum] = Math.min(dist[start][adjIslandNum], len - 1);
						dist[adjIslandNum][start] = dist[start][adjIslandNum];
						break;
					}
				}	
			}
		}
	}
	
	public static void flood() {
		for(Point l : land) {
			int startCol = l.col;
			int startRow = l.row;
			
			if(island[startRow][startCol] == 0) {
				Deque<Point> queue = new ArrayDeque<>();
				queue.offer(new Point(startCol, startRow));
				island[startRow][startCol] = islandNum;
				islandList[islandNum].add(new Point(startCol, startRow));
				
				while(!queue.isEmpty()) {
					Point p = queue.poll();
					int col = p.col;
					int row = p.row;
					
					for(int dir = 0; dir < 4; dir++) {
						int nCol = col + dCol[dir];
						int nRow = row + dRow[dir];
						
						if(nCol < 0 || nRow < 0 || nCol >= width || nRow >= height) {
							continue;
						}
						
						if(island[nRow][nCol] == 0 && grid[nRow][nCol] == 1) {
							island[nRow][nCol] = islandNum;
							queue.offer(new Point(nCol, nRow));
							islandList[islandNum].add(new Point(nCol, nRow));
						}
						
					}
					
				}
				
				islandNum++;
			}
		}
	}
	
	public static int findRoot(int node) {
		if(parent[node] == node) {
			return node;
		}
		
		return parent[node] = findRoot(parent[node]);
	}
	
	public static boolean isOuter(Point p) {
		int col = p.col;
		int row = p.row;
		for(int dir = 0; dir < 4; dir++) {
			int nCol = col + dCol[dir];
			int nRow = row + dRow[dir];
			
			if(nCol < 0 || nRow < 0 || nCol >= width || nRow >= height) {
				continue;
			}
			
			if(grid[nRow][nCol] == 0) {
				return true;
			}
		}
		
		return false;
	}
	
	public static class Point{
		int col;
		int row;
		
		public Point(int col, int row) {
			this.col = col;
			this.row = row;
		}
	}
	
	public static class Edge{
		int nodeA;
		int nodeB;
		int d;
		
		public Edge(int nodeA, int nodeB, int d) {
			this.nodeA = nodeA;
			this.nodeB = nodeB;
			this.d = d;
		}
		
		@Override
		public String toString() {
			return String.format("edge={s=%d, e=%d, d=%d}", nodeA, nodeB, d);
		}
	}
	
	public static void printArr(int[][] arr) {
		for(int row = 0; row < height; row++) {
			for(int col = 0; col < width; col++) {
				System.out.print(arr[row][col] + " ");
			}
			System.out.println();
		}
		System.out.println("----------");
	}
	
}