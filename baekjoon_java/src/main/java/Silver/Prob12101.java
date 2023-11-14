package Silver;
import java.io.*;
import java.util.*;

public class Prob12101{

    static Set<String> routes = new HashSet<>();
    static int n, k;

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        dfs(1, "1");
        dfs(2, "2");
        dfs(2, "1+1");
        dfs(3, "3");
        dfs(3, "2+1");
        dfs(3, "1+2");
        dfs(3, "1+1+1");

        List<String> sorted = new ArrayList<>();
        for(String r : routes){
            sorted.add(r);
        }
        Collections.sort(sorted);
        // System.out.println("sorted=" + sorted);
        System.out.println(sorted.size() < k ? -1 : sorted.get(k-1));
    }

    public static void dfs(int node, String route){
        if(node > n){
            return;
        }
        if(node == n){
            routes.add(route);
            return;
        }

        dfs(node + 1, route + "+1");
        dfs(node + 2, route + "+2");
        dfs(node + 3, route + "+3");
    }
}