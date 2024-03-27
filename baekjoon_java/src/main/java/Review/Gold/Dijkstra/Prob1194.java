package Review.Gold.Dijkstra;

import java.io.*;
import java.util.*;

/**
 * 1. 직사각형 격자의 미로 탈출하기
 * 	1-1. 격자는 빈칸, 벽, 열쇠, 문, 출구로 이루어져 있다
 * 		1-1-1. 열쇠는 대응하는 문만 열 수 있다
 * 		1-1-2. 열쇠는 a, b, c, d, e, f만 존재 -> 최대 6개
 * 	1-3. 격자의 가로세로는 최대 50
 * 2. 상하좌우로 인접한 칸으로 움직일 수 있다
 * 3. 가장 적은 이동 횟수로 미로를 탈출한다
 * --------------------
 * 1. 다익스트라 -> 우선순위 큐
 * 	1-1. 이동 횟수가 가장 적은 정보가 앞으로 오도록 minHeap 생성
 * 2. 현재 좌표로부터 상하좌우로 인접한 칸 확인
 * 	2-1. 인접한 좌표로 이동 했을때 최소 이동 횟수를 갱신할 수 있으면 해당 좌표로 움직이고 정보를 큐에 넣는다
 * 		2-1-1. 이때, 현재 어떤 열쇠들을 가지고 있는지에 따라 상태가 다르기 때문에 현재 가지고 있는 열쇠에 따라 최소 이동횟수가 갱신 가능한지 확인
 * 		2-1-2. 3차원 배열 생성 -> dist[row][col][keys] -> (col, row) 좌표에서 keys 만큼 열쇠를 가지고 있을때의 최소 이동 횟수
 * 	2-2. 인접한 칸이 벽('#'), 빈칸('.') 또는 탈출구('1'), 열쇠('a'~'f'), 문('A'~'F') 인지에 따라 다르게 확인
 * 		2-2-1. 벽이면 무시
 * 		2-2-2. 빈칸 또는 탈출구이면 최소 이동 횟수가 갱신 가능한지 확인
 * 		2-2-3. 문이면 해당 문을 열 수 있는 열쇠를 가지고 있는지 확인
 * 		2-2-4. 열쇠이면 최소 이동 횟수가 갱신 가능한지 확인
 * 3. 우선순위 큐에서 뺀 좌표가 탈출구 이면 -> 인접한 좌표 탐색 무시
 * 	3-1. 우선순위 큐이기 때문에 큐에 다른 좌표들의 이동 횟수는 무조건 현재 이동 횟수보다 크거나 같다
 * 	3-2. 즉, 더 적은 횟수로 탈출구에 도착할 수 있는 방법이 없음
 */
public class Prob1194 {
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static StringBuilder sb = new StringBuilder();

	static int height, width;
	static char[][] grid;
	
	static int[][][] dist;
	static final int INF = 1_000_000_000;
	static int startCol, startRow;
	
	static int[] dCol = {1, 0, -1, 0};
	static int[] dRow = {0, 1, 0, -1};
	
	public static void main(String[] args) throws IOException{
		st = new StringTokenizer(br.readLine());
		height = Integer.parseInt(st.nextToken());
		width = Integer.parseInt(st.nextToken());
		
		grid = new char[height][width];
		dist = new int[height][width][1 << 6];
		for(int row = 0; row < height; row++) {
			String curRow = br.readLine();
			for(int col = 0; col < width; col++) {
				grid[row][col] = curRow.charAt(col);
				Arrays.fill(dist[row][col], INF);
				
				if(grid[row][col] == '0') {
					grid[row][col] = '.';
					startCol = col;
					startRow = row;
				}
			}
		}

		
		// 이동 횟수가 가장 적은 값이 앞으로 오도록 minHeap 생성
		Deque<Node> queue = new ArrayDeque<>();
		queue.offer(new Node(startCol, startRow, 0, 0));
		dist[startRow][startCol][0] = 0;
		
		int answer = INF;
		while(!queue.isEmpty()) {
			Node node = queue.poll();
			int col = node.col;
			int row = node.row;
			int d = node.d;
			int keys = node.keys;

			if(grid[row][col] == '1') { // 탈출구에 도착 -> 더 이상 적은 이동 횟수로 탈출구에 도착하는 방법이 없음 -> 인접한 좌표 탐색 무시
				answer = Math.min(answer, d);
				continue;
			}
			
			for(int dir = 0; dir < 4; dir++) {
				int nCol = col + dCol[dir];
				int nRow = row + dRow[dir];
				
				if(nCol < 0 || nRow < 0 || nCol >= width || nRow >= height) {
					continue;
				}
				
				if(grid[nRow][nCol] == '#') { // 벽 -> 무시
					continue;
					
				}else if(grid[nRow][nCol] == '.' || grid[nRow][nCol] == '1') { // 빈칸 또는 탈출구 -> 최소 이동 횟수가 갱신 가능한지 확인
					if(dist[nRow][nCol][keys] > d + 1) {
						dist[nRow][nCol][keys] = d + 1;
						queue.offer(new Node(nCol, nRow, d + 1, keys));
					}
					
				}else if('A' <= grid[nRow][nCol] && grid[nRow][nCol] <='F') { // 문 -> 대응하는 키를 가지고 있는지 확인 -> 최소 이동 횟수가 갱신 가능한지 확인
					int nextBit = (1 << (grid[nRow][nCol] - 'A'));
					if((keys & nextBit) == nextBit) {
						if(dist[nRow][nCol][keys] > d + 1) {
							dist[nRow][nCol][keys] = d + 1;
							queue.offer(new Node(nCol, nRow, d + 1, keys));
						}
					}
					
				}else if('a' <= grid[nRow][nCol] && grid[nRow][nCol] <='f') { // 열쇠 -> 최소 이동 횟수가 갱신 가능한지 확인
					int key = (1 << (grid[nRow][nCol] - 'a'));
					int nextKey = keys | key;
					if(dist[nRow][nCol][nextKey] > d + 1) {
						dist[nRow][nCol][nextKey] = d + 1;
						queue.offer(new Node(nCol, nRow, d + 1, nextKey));
					}
					
				}
			}
		}
		
		System.out.println(answer == INF ? -1 : answer);
	}
	
	public static class Node{
		int col;
		int row;
		int d;
		int keys;
		
		public Node(int col, int row, int d, int keys) {
			this.col = col;
			this.row = row;
			this.d = d;
			this.keys = keys;
		}
		
		@Override
		public String toString() {
			return String.format("col=%d, row=%d, d=%d, keys=%s", col, row, d, Integer.toBinaryString(keys));
		}
	}
}