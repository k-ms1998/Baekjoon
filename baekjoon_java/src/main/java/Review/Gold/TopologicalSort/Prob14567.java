package Review.Gold.TopologicalSort;

import java.io.*;
import java.util.*;

/**
 * Gold 5(선수과목)
 *
 * https://www.acmicpc.net/problem/14567
 *
 * Solution: 위상 정렬
 */
public class Prob14567{

    static int n, m;
    static int[] inCount;
    static List<Integer>[] edges;
    static int[] semester;

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        inCount = new int[n + 1];
        edges = new List[n + 1];
        semester = new int[n + 1];
        for(int i = 1; i < n + 1; i++){
            edges[i] = new ArrayList<>();
        }

        for(int i = 0;  i < m; i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            inCount[b]++;
            edges[a].add(b); // a->b (a를 먼저 들어야 b를 들을수 있음)
        }


        Deque<Integer> queue = new ArrayDeque<>();
        for(int i = 1; i < n + 1; i++){
            if(inCount[i] == 0){
                queue.offer(i);
                semester[i] = 1;
            }
        }

        while(!queue.isEmpty()){
            int node = queue.poll();

            for(int next : edges[node]){
                inCount[next]--;
                if(inCount[next] == 0){
                    queue.offer(next);
                    semester[next] = semester[node] + 1;
                }
            }
        }

        StringBuilder ans = new StringBuilder();
        for(int i = 1; i < n + 1; i++){
            ans.append(semester[i]).append(" ");
        }

        System.out.println(ans);
    }

}