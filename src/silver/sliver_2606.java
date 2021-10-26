package silver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class sliver_2606 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		ArrayList<Integer> []edges = new ArrayList[n];
		for(int i = 0; i < n; i++) {
			edges[i] = new ArrayList<Integer>();
		}
		int e = sc.nextInt();
		for(int i = 0; i < e; i++) {
			String inStr = sc.nextLine();
			if(inStr.equals("")) {
				i--;
				continue;
			}
			String []v = inStr.split(" ");
			int left = Integer.parseInt(v[0]);
			int right = Integer.parseInt(v[1]);
			edges[left-1].add(right-1);
			edges[right-1].add(left-1);
//			List<Integer>v = Arrays.stream(inStr.split(" "))
//					.map(Integer::parseInt)
//					.collect(Collectors.toList());
//			System.out.println(v);
//			edges[Integer.valueOf(v[0])].add(Integer.valueOf(v[0]));
		}
//		for(int i = 0; i < edges.length; i++) {
//			Iterator iter = edges[i].iterator();
//			while(iter.hasNext()) {
//				System.out.println(i+":"+iter.next());
//			}
//		}
		for(int i = 0; i  < edges[0].size(); i++) {
			int tmpNode = edges[0].get(i);
			for(int j = 0; j < edges[tmpNode].size(); j++) {
				int cNode = edges[tmpNode].get(j);
				if(!edges[0].contains(cNode) && cNode != 0){
					edges[0].add(edges[tmpNode].get(j));
				}
			}
		}
		//System.out.println(edges[0]);
		System.out.println(edges[0].size());
	}

}
