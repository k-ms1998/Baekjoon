package Gold.Graph.Union_Find;

import java.io.*;
import java.util.*;

public class Prob3108 {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	
	static int size;
	static Point[] points;
	static Line[][] lines;
	static int[] parent;
	
	public static void main(String[] args) throws IOException{
		size = Integer.parseInt(br.readLine());
		points = new Point[size];
		lines = new Line[size][4];
		parent = new int[size];
		
		for(int idx = 0; idx < size; idx++) {
			st = new StringTokenizer(br.readLine());
			int srcCol = Integer.parseInt(st.nextToken());
			int srcRow = Integer.parseInt(st.nextToken());
			int dstCol = Integer.parseInt(st.nextToken());
			int dstRow = Integer.parseInt(st.nextToken());
			
			updateLines(idx, srcCol, srcRow, dstCol, dstRow);
			points[idx] = new Point(srcCol, srcRow, dstCol, dstRow);
			parent[idx] = idx;
		}		

		for(int idxA = 0; idxA < size; idxA++) {
			for(int idxB = idxA + 1; idxB < size; idxB++) {
				boolean isConnected = false;
				for(int lineA = 0; lineA < 4; lineA++) {
					for(int lineB = 0; lineB < 4; lineB++) {
						if(checkLine(lines[idxA][lineA], lines[idxB][lineB])) {
							isConnected = true;
							break;
						}
					}
					if(isConnected) {
						break;
					}
				}
				if(isConnected) {
					int rootA = findRoot(idxA);
					int rootB = findRoot(idxB);
					
					if(rootA != rootB) {
						if(rootA > rootB) {
							parent[rootB] = rootA;
							parent[idxB] = rootA;
						}else {
							parent[rootA] = rootB;
							parent[idxA] = rootB;
						}
						
					}
				}				
			}
		}
		
		Set<Integer> nums = new HashSet<>();
		for(int idx = 0; idx < size; idx++) {
			int root = findRoot(idx);
			nums.add(root);
		}
		
		int answer = nums.size();
		boolean startConnected = false;
		Line startA = new Line(0, 0, 0, 0, true);
		Line startB = new Line(0, 0, 0, 0, true);
		
		for(int idx = 0; idx < size; idx++) {
			for(int line = 0; line < 4; line++) {
				if(checkLine(startA, lines[idx][line]) || checkLine(startB, lines[idx][line])) {
					startConnected = true;
					break;
				}
			}
			if(startConnected) {
				answer--;
				break;
			}
		}
		
		System.out.println(answer);
	}
	
	public static int findRoot(int node) {
		if(node == parent[node]) {
			return node;
		}
		
		return parent[node] = findRoot(parent[node]);
	}
	
	public static boolean checkLine(Line lineA, Line lineB) {
		if(lineA.isVertical && !lineB.isVertical) {
			int srcRow = lineA.srcRow;
			int dstRow = lineA.dstRow;
			int targetRow = lineB.srcRow;
			
			int srcCol = lineB.srcCol;
			int dstCol = lineB.dstCol;
			int targetCol = lineA.srcCol;
			
			if(srcRow <= targetRow && targetRow <= dstRow
					&& srcCol <= targetCol && targetCol <= dstCol) {
				return true;
			}
		}
		if(!lineA.isVertical && lineB.isVertical) {
			int srcRow = lineB.srcRow;
			int dstRow = lineB.dstRow;
			int targetRow = lineA.srcRow;
			
			int srcCol = lineA.srcCol;
			int dstCol = lineA.dstCol;
			int targetCol = lineB.srcCol;
			
			if(srcRow <= targetRow && targetRow <= dstRow
					&& srcCol <= targetCol && targetCol <= dstCol) {
				return true;
			}
		}
		if(!lineA.isVertical && !lineB.isVertical) {
			int srcRowA = lineA.srcRow;
			int srcColA = lineA.srcCol;
			int dstColA = lineA.dstCol;
			
			int srcRowB = lineB.srcRow;
			int srcColB = lineB.srcCol;
			int dstColB = lineB.dstCol;
			
			if(srcRowA == srcRowB && 
					((srcColB <= srcColA && srcColA <= dstColB)
							|| (srcColB <= dstColA && dstColA <= dstColB)
							|| (srcColA <= srcColB && srcColB <= dstColA)
							|| (srcColA <= dstColB && dstColB <= dstColA))) {
				return true;
			}
		}
		if(lineA.isVertical && lineB.isVertical) {
			int srcColA = lineA.srcCol;
			int srcRowA = lineA.srcRow;
			int dstRowA = lineA.dstRow;
			
			int srcColB = lineB.srcCol;
			int srcRowB = lineB.srcRow;
			int dstRowB = lineB.dstRow;
			
			if(srcColA == srcColB && 
					((srcRowB <= srcRowA && srcRowA <= dstRowB)
							|| (srcRowB <= dstRowA && dstRowA <= dstRowB)
							|| (srcRowA <= srcRowB && srcRowB <= dstRowA)
							|| (srcRowA <= dstRowB && dstRowB <= dstRowA))) {
				return true;
			}
		}
		
		return false;
	}
	
	public static void updateLines(int idx, int srcCol, int srcRow, int dstCol, int dstRow) {
		// top
		Line lineT = new Line(srcCol, srcRow, dstCol, srcRow, false);
		//right
		Line lineR = new Line(dstCol, srcRow, dstCol, dstRow, true);
		//bottom
		Line lineB = new Line(srcCol, dstRow, dstCol, dstRow, false);
		//left
		Line lineL = new Line(srcCol, srcRow, srcCol, dstRow, true);
								
		lines[idx][0] = lineT;
		lines[idx][1] = lineR;
		lines[idx][2] = lineB;
		lines[idx][3] = lineL;
	}
	
	public static class Line{
		int srcCol;
		int srcRow;
		int dstCol;
		int dstRow;
		boolean isVertical;
		
		public Line(int srcCol, int srcRow, int dstCol, int dstRow, boolean isVertical) {
			this.srcCol = srcCol;
			this.srcRow = srcRow;
			this.dstCol = dstCol;
			this.dstRow = dstRow;
			this.isVertical = isVertical;
		}
	}
	
	public static class Point{
		int srcCol;
		int srcRow;
		int dstCol;
		int dstRow;
		
		public Point(int srcCol, int srcRow, int dstCol, int dstRow) {
			this.srcCol = srcCol;
			this.srcRow = srcRow;
			this.dstCol = dstCol;
			this.dstRow = dstRow;
		}
	}

}
