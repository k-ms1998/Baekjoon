package Review.Gold.Bruteforce;

import java.io.*;
import java.util.*;

/**
 * 백트래킹
 */
public class Prob17136 {
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static StringBuilder sb = new StringBuilder();

	static int[][] grid = new int[10][10];
	static List<Point> ones = new ArrayList<>();
	static int answer = -1;
	static int[] used = {0, 0, 0, 0, 0, 0};
	
	public static void main(String[] args) throws IOException{
		for(int row = 0; row < 10; row++) {
			st = new StringTokenizer(br.readLine());
			for(int col = 0; col < 10; col++) {
				grid[row][col] = Integer.parseInt(st.nextToken());
				if(grid[row][col] == 1) {
					ones.add(new Point(col, row));
				}
			}
		}
		
		findAnswer(0, 0);
		
		System.out.println(answer);
	}
	
	public static void findAnswer(int idx, int count) {
		if(idx == ones.size()) {
			if(answer == -1) {
				answer = count;
			}else {
				answer = Math.min(answer, count);
			}
			
			return;
		}
		if(answer != -1 && answer <= count) {
			return;
		}
		
		
		Point p = ones.get(idx);
		int col = p.col;
		int row = p.row;
		
		if(grid[row][col] == 0) {
			findAnswer(idx + 1, count);
		}else {
			for(int size = 1; size <= 5; size++) {
//				System.out.printf("col=%d, row=%d, size=%d, used=%d\n", col, row, size, used[size]);

				if(used[size] >= 5) {
					continue;
				}
				if(isPlaceable(col, row, size)) {
					update(col, row, size, 0);
					used[size]++;
					
					findAnswer(idx + 1, count + 1);
					
					update(col, row, size, 1);
					used[size]--;
				}
			}
		}
	}
	
	public static boolean isPlaceable(int startCol, int startRow, int size) {
		int endCol = startCol + size - 1;
		int endRow = startRow + size - 1;
		
		if(endCol >= 10 || endRow >= 10) {
			return false;
		}
		
		for(int col = startCol; col <= endCol; col++) {
			for(int row = startRow; row <= endRow; row++) {
				if(grid[row][col] == 0) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	public static void update(int startCol, int startRow, int size, int target) {
		int endCol = startCol + size - 1;
		int endRow = startRow + size - 1;
		for(int col = startCol; col <= endCol; col++) {
			for(int row = startRow; row <= endRow; row++) {
				grid[row][col] = target;
			}
		}

	}
	
	public static class Point{
		int col;
		int row;
		
		public Point(int col, int row) {
			this.col = col;
			this.row = row;
		}
	}
	
}