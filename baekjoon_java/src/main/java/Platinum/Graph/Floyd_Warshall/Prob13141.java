package Platinum.Graph.Floyd_Warshall;

import java.io.*;
import java.util.*;

/**
 * Platinum 5(Ignition)
 *
 * https://www.acmicpc.net/problem/13141
 *
 * Solution: Floyd + Math
 */
public class Prob13141 {

    static int n, m;
    static int[][] dist;
    static Edge[] edges;

    static final int INF = 1000000000;

    static double ans = Double.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        dist = new int[n + 1][n + 1];
        edges = new Edge[m];
        for (int i = 1; i < n + 1; i++) {
            for (int j = 1; j < n + 1; j++) {
                if (i == j) {
                    dist[i][j] = 0;
                } else {
                    dist[i][j] = INF;
                }
            }
        }
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int src = Integer.parseInt(st.nextToken());
            int dst = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());

            dist[src][dst] = Math.min(dist[src][dst], cost);
            dist[dst][src] = Math.min(dist[dst][src], cost);
            edges[i] = new Edge(src, dst, cost);
        }

        floydWarshall();
//        printGrid(dist);
        for (int i = 1; i < n + 1; i++) {
            burnGraph(i);
        }

        System.out.println(ans);
    }

    static public void burnGraph(int s) {
        double time = 0.0;

        for (int i = 0; i < m; i++) {
            Edge curEdge = edges[i];
            int curSrc = curEdge.src;
            int curDst = curEdge.dst;
            int curCost = curEdge.cost;

            double curTime = (dist[s][curSrc] + dist[s][curDst] + curCost) / 2.0;

            time = Math.max(time, curTime);
        }
        ans = Math.min(ans, time);
    }

    static public void floydWarshall() {
        for (int k = 1; k < n + 1; k++) {
            for (int i = 1; i < n + 1; i++) {
                for (int j = 1; j < n + 1; j++) {
                    if (dist[i][j] > dist[i][k] + dist[k][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                    }
                }
            }
        }
    }

    static public void printGrid(int[][] grid) {
        for (int i = 1; i < n + 1; i++) {
            for (int j = 1; j < n + 1; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("--------------------");
    }

    public static class Edge {
        int src;
        int dst;
        int cost;

        public Edge(int src, int dst, int cost) {
            this.src = src;
            this.dst = dst;
            this.cost = cost;
        }

        @Override
        public String toString() {
            return "Edge{" +
                    "src=" + src +
                    ", dst=" + dst +
                    ", cost=" + cost +
                    '}';
        }
    }
}
