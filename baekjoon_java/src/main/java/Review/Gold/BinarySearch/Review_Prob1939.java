package Review.Gold.BinarySearch;

import java.io.*;
import java.util.*;
import java.util.Map.*;

/*
3 3
1 2 2
3 1 3
2 3 2
1 3
*/

/**
 * Gold 4
 */
public class Review_Prob1939 {

    static int n;
    static int m;
    static public Map<Integer, List<Entry<Integer, Integer>>> edges = new HashMap<>();

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String conditions = br.readLine();
        String[] conditionsSplit = conditions.split(" ");
        n = Integer.valueOf(conditionsSplit[0]);
        m = Integer.valueOf(conditionsSplit[1]);

        int maxW = 0;
        int minW = 1000000000;

        for (int i = 0; i < m; i++) {
            String edge = br.readLine();
            String[] edgeSplit = edge.split(" ");

            int s = Integer.valueOf(edgeSplit[0]);
            int d = Integer.valueOf(edgeSplit[1]);
            int w = Integer.valueOf(edgeSplit[2]);

            if (maxW < w) {
                maxW = w;
            }
            if (minW > w) {
                minW = w;
            }

            int kA = s;
            Entry<Integer, Integer> vA = Map.entry(d, w);
            List<Entry<Integer, Integer>> valuesA = new ArrayList<>();
            if (edges.containsKey(kA)) {
                valuesA = edges.get(kA);
            }
            valuesA.add(vA);
            edges.put(kA, valuesA);

            int kB = d;
            Entry<Integer, Integer> vB = Map.entry(s, w);
            List<Entry<Integer, Integer>> valuesB = new ArrayList<>();
            if (edges.containsKey(kB)) {
                valuesB = edges.get(kB);
            }
            valuesB.add(vB);
            edges.put(kB, valuesB);
        }

        String srcDst = br.readLine();
        String[] srcDstSplit = srcDst.split(" ");
        int src = Integer.valueOf(srcDstSplit[0]);
        int dst = Integer.valueOf(srcDstSplit[1]);

        while (minW <= maxW) {
            int midW = (minW + maxW) / 2;

            if (bfs(src, dst, midW)) {
                minW = midW + 1;
            } else {
                maxW = midW - 1;
            }
        }

        System.out.println(maxW);
    }

    private static boolean bfs(int src, int dst, int w) {
        Boolean[] visited = new Boolean[n+1];
        for (int i = 0; i <= n; i++) {
            visited[i] = false;
        }
        Queue<Integer> nextNode = new ArrayDeque<>();

        nextNode.add(src);
        while (!nextNode.isEmpty()) {
            int curNode = nextNode.poll();
//            System.out.println("nextNode = " + nextNode);
//            for (Boolean aBoolean : visited) {
//                System.out.print(" " + aBoolean);
//            }
//            System.out.println("\n===========================================");

            if (visited[curNode]) {
                continue;
            }

            visited[curNode] = true;
            List<Entry<Integer, Integer>> entries = edges.get(curNode);
            for (Entry<Integer, Integer> entry : entries) {
                int tmpDst = entry.getKey();
                int tmpW = entry.getValue();

                if (w <= tmpW && !visited[tmpDst]) {
                    nextNode.add(tmpDst);
                }
            }
        }

        return visited[dst];
    }

}
