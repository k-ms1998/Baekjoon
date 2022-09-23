package Gold.BellmanFord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Gold 4 (타임머신)
 *
 * https://www.acmicpc.net/problem/11657
 *
 * Solution: Bellman-Ford
 */
public class Prob11657 {

    static int n;
    static int m;

    static List<Bus>[] buses;
    static long[] dist;

    static int MAX_DIST;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        buses = new List[n + 1];
        for (int i = 1; i < n + 1; i++) {
            buses[i] = new ArrayList<>();
        }
        MAX_DIST = n * 10001;

        m = Integer.parseInt(st.nextToken());
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            buses[a].add(new Bus(b, c));
        }

        if (bellmanFord()) {
            System.out.println("-1");
        } else {
            StringBuffer ans = new StringBuffer();
            for (int i = 2; i < n + 1; i++) {
                if (dist[i] == MAX_DIST) {
                    ans.append("-1");
                }else{
                    ans.append(dist[i]);
                }
                ans.append("\n");
            }
            System.out.println(ans);
        }
    }

    public static boolean bellmanFord() {
        dist = new long[n + 1];
        for (int i = 1; i < n + 1; i++) {
            dist[i] = MAX_DIST;
        }
        dist[1] = 0;

        for (int i = 1; i < n; i++) {
            for (int j = 1; j < n + 1; j++) {
                List<Bus> adjBuses = buses[j];
                for (Bus bus : adjBuses) {
                    int d = bus.d;
                    int cost = bus.cost;

                    if(dist[j] != MAX_DIST){
                        if (dist[d] > dist[j] + cost) {
                            dist[d] = dist[j] + cost;
                        }
                    }

                }
            }
        }

        for (int j = 1; j < n + 1; j++) {
            List<Bus> adjBuses = buses[j];
            for (Bus bus : adjBuses) {
                int d = bus.d;
                int cost = bus.cost;

                if(dist[j] != MAX_DIST){
                    if (dist[d] > dist[j] + cost) {
                        return true;
                    }
                }

            }
        }

        return false;
    }

    static class Bus{
        int d;
        int cost;

        public Bus(int d, int cost){
            this.d = d;
            this.cost = cost;
        }

        @Override
        public String toString() {
            return "{ " + d + ", " + cost + " }";
        }
    }
}
