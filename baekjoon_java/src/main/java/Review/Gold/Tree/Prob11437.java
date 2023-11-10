package Review.Gold.Tree;

import java.io.*;
import java.util.*;

/**
 * Gold 3(LCA)
 *
 * https://www.acmicpc.net/problem/11437
 *
 * Solution: LCA
 */
public class Prob11437{

    static int n, m;
    static StringBuilder ans = new StringBuilder();
    static int[] parent;
    static List<Integer>[] edges;
    static int[] depth;
    static boolean[] v;

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        parent = new int[n + 1];
        edges = new List[n + 1];
        depth = new int[n + 1];
        v = new boolean[n + 1];
        for(int i = 1; i < n + 1; i++){
            edges[i] = new ArrayList<>();
        }
        for(int i = 1; i < n; i++){
            st = new StringTokenizer(br.readLine());
            int p = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            edges[p].add(c);
            edges[c].add(p);
        }

        v[1] = true;
        parent[1] = 1;
        createTree(1, 0);

        m = Integer.parseInt(br.readLine());
        for(int i = 0; i < m; i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            int na = a;
            int nb = b;
            int ha = depth[a];
            int hb = depth[b];
            while(ha != hb){
                if(ha > hb){
                    ha--;
                    na = parent[na];
                }else{
                    hb--;
                    nb = parent[nb];
                }
            }

            while(na != nb){
                if(na == 1 || nb == 1){
                    break;
                }
                na = parent[na];
                nb = parent[nb];
            }

            ans.append(na).append("\n");
        }

        System.out.println(ans);
    }

    public static void createTree(int node, int d){
        depth[node] = d;
        if(edges[node].size() == 0){
            return;
        }

        for(int next : edges[node]){
            if(!v[next]){
                v[next] = true;
                parent[next] = node;
                createTree(next, d + 1);
            }
        }

    }


}