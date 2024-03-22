package Platinum.LIS;

import java.io.*;
import java.util.*;

public class Prob14003 {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static StringBuilder sb = new StringBuilder();
	
	static int size;
	static int[] arr;
	static int[] lis;
	static int[] lisIdx;
	static int[] parent;
	static int lastIdx = 0;
	
	static Stack<Integer> seq = new Stack<>();
	
	public static void main(String[] args) throws IOException{
		size = Integer.parseInt(br.readLine());
		arr = new int[size];
		lis = new int[size];
		lisIdx = new int[size];
		parent = new int[size];
		
		st = new StringTokenizer(br.readLine());
		for(int idx = 0; idx < size; idx++) {
			int num = Integer.parseInt(st.nextToken());
			arr[idx] = num;
			if(lastIdx == 0) {
				lis[lastIdx] = num;
				lisIdx[lastIdx] = idx;
				parent[0] = -1;
				
				lastIdx++;
				continue;
			}
			
			int lastNum = lis[lastIdx - 1];
			if(lastNum < num) {
				lis[lastIdx] = num;	
				lisIdx[lastIdx] = idx;
				parent[idx] = lisIdx[lastIdx - 1];
				
				lastIdx++;
			}else {
				int left = 0;
				int right = lastIdx - 1;
				while(left < right) {
					int mid = (left + right) / 2;
					int midNum = lis[mid];
					
					if(midNum < num) {
						left = mid + 1;
					}else {
						right = mid;
					}
				}
				
				lis[left] = num;
				lisIdx[left] = idx;
				if(left == 0) {
					parent[idx] = -1;
				}else {
					parent[idx] = lisIdx[left - 1];	
				}
			}
		}
		findRoute(lisIdx[lastIdx - 1]);
		
		sb.append(lastIdx).append("\n");
		while(!seq.isEmpty()) {
			sb.append(seq.pop()).append(" ");
		}
		System.out.println(sb);
	}
	
	public static void findRoute(int idx) {
		if(idx == -1) {
			return;
		}
		
		seq.push(arr[idx]);
		
		int parentIdx = parent[idx];
		findRoute(parentIdx);
	}

}
/*
7
3 2 5 7 6 1 4

7
3 6 5 2 7 1 4

7
3 7 5 2 6 1 4

6
20 10 20 30 20 50
*/