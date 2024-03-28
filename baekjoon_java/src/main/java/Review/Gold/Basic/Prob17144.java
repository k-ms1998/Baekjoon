package Review.Gold.Basic;

import java.io.*;
import java.util.*;

/**
 * 1. 공기청정기는 항상 1번 열에 설치되어 있고, 크기는 두 행을 차지한단
 * 2. 공기청정기가 설치되어 있지 않은 칸에는 미세먼지가 있고, 각 칸마다 A만큼 미세먼지를 가지고 있다
 * 3. 1초 동안 다음과 같은 일이 순서대로 일어난다
 * 	3-1. 미세먼지가 확산된다
 * 		3-1-1. (r, c) 칸에 있는 미세먼지는 인접한 네 방향으로 확산된다
 * 		3-1-2. 인접한 방향에 공치청정기가 있거나, 칸이 없으면 그 방햐향으로는 확산이 일어나지 않는다
 * 		3-1-3. 확산되는 양은 A/5 이고, 소수점은 버린다
 * 		3-1-4. (r, c)에 남은 미세먼지의 양은 처음 A - (인접한 방향들로 확산된 양)
 * 	3-2. 공기청정기가 작동한다
 * 		3-2-1. 공치청정기에서 바람이 나온다
 * 		3-2-2. 위쪽 공기청정기는의 바람은 빈시계방향으로 순환하고, 아래쪽은 시계방향으로 순환
 * 		3-2-3. 바람이 불면 미세먼지가 바람의 방향대로 모두 한 칸씩 이동한다
 * 		3-2-4. 공기청정기에서 부는 바람은 미세먼지가 없는 바람이고, 공기 청정기로 들어간 미세먼지는 모두 정화된다
 */
public class Prob17144 {
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static StringBuilder sb = new StringBuilder();

	static int height, width, rotations;
	static int[][] grid;
	
	static int airRowA = -1;
	static int airRowB = -1;
	static boolean[][] visited;
	
	static final int[] dCol = {1, 0, -1, 0};
	static final int[] dRow = {0, 1, 0, -1};
	
	public static void main(String[] args) throws IOException{
		st = new StringTokenizer(br.readLine());
		
		height = Integer.parseInt(st.nextToken());
		width = Integer.parseInt(st.nextToken());
		rotations = Integer.parseInt(st.nextToken());
		
		grid = new int[height][width];
		for(int row = 0; row < height; row++) {
			st = new StringTokenizer(br.readLine());
			for(int col = 0; col < width; col++) {
				grid[row][col] = Integer.parseInt(st.nextToken());
				if(grid[row][col] == -1) {
					if(airRowA == -1) {
						airRowA = row;
					}else {
						airRowB = row;
					}
				}
			}
		}
		
		while(rotations-- > 0) {
			spreadDust(); // 먼지 확산
			blowDustUpper(airRowA, 1, 3); // 위쪽으로 바람
			blowDustLower(airRowB, 3, 1); // 아래쪽으로 바람
		}
		
		int answer = findAnswer();
		System.out.println(answer);
	}	
	
	public static void spreadDust() {
		int[][] copy = new int[height][width];
		
		for(int row = 0; row < height; row++) {
			for(int col = 0; col < width; col++) {
				if(grid[row][col] <= 0) {
					continue;
				}
				
				int curSpread = 0;
				int curDust = grid[row][col] / 5;
				for(int dir = 0; dir < 4; dir++) {
					int nCol = col + dCol[dir];
					int nRow = row + dRow[dir];
					
					if(nCol < 0 || nRow < 0 || nCol >= width || nRow >= height) {
						continue;
					}
					if(grid[nRow][nCol] != -1) {
						curSpread += curDust;
						copy[nRow][nCol] += curDust;
					}
				}
				copy[row][col] += (grid[row][col] - curSpread);
			}
		}
		
		copyToGrid(copy);
	}
	
	public static void blowDustUpper(int startRow, int delta, int dir) {
		int col = 0;
		int row = startRow - 1;
		
		while(true) {
			int nCol = col + dCol[dir];
			int nRow = row + dRow[dir];
			if(nCol < 0 || nRow < 0 || nCol >= width || nRow > startRow) {
				dir = (dir + delta) % 4;
				continue;
			}
			if(grid[nRow][nCol] == -1) {
				grid[row][col] = 0;
				break;
			}
			
			grid[row][col] = grid[nRow][nCol];
			row = nRow;
			col = nCol;
		}
	}
	
	public static void blowDustLower(int startRow, int delta, int dir) {
		int col = 0;
		int row = startRow + 1;
		
		while(true) {
			int nCol = col + dCol[dir];
			int nRow = row + dRow[dir];
			if(nCol < 0 || nRow < startRow || nCol >= width || nRow >= height) {
				dir = (dir + delta) % 4;
				continue;
			}
			if(grid[nRow][nCol] == -1) {
				grid[row][col] = 0;
				break;
			}
			
			grid[row][col] = grid[nRow][nCol];
			row = nRow;
			col = nCol;
		}
	}
	
	public static void copyToGrid(int[][] copy) {
		copy[airRowA][0] = -1;
		copy[airRowB][0] = -1;
		for(int row = 0; row < height; row++) {
			for(int col = 0; col < width; col++) {
				grid[row][col] = copy[row][col];
			}
		}
	}
	
	public static int findAnswer() {
		int count = 0;
		for(int row = 0; row < height; row++) {
			for(int col = 0; col < width; col++) {
				if(grid[row][col] != -1) {
					count += grid[row][col];
				}
			}
		}
		
		return count;
	}
	
	public static void printGrid() {
		for(int row = 0; row < height; row++) {
			for(int col = 0; col < width; col++) {
				System.out.print(grid[row][col] + " ");
			}
			System.out.println();
		}
		System.out.println("----------");
	}
	
}