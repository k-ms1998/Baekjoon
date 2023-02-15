package Gold.DP.Floyd_Warshall;

import java.io.*;
import java.util.*;

/**
 * Gold 5(노드사이의 거리)
 *
 * https://www.acmicpc.net/problem/1240
 *
 * Solution: Floyd
 * But, 단순 BFS 로 푸는 것이 더 효율적
 */
public class Prob1240 {

    static int n, m;
    static int[][] dist;
    static StringBuilder ans = new StringBuilder();
    static final int INF = 1000000000;

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        dist = new int[n + 1][n + 1];
        for(int i = 1; i < n + 1; i++){
            for(int j = 1; j < n + 1; j++){
                dist[i][j] = i == j ? 0 : INF;
            }
        }
        for(int i = 0; i < n - 1; i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            dist[a][b] = c;
            dist[b][a] = c;
        }
        floyd();
        for(int i = 0; i < m; i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            ans.append(dist[a][b]).append("\n");
        }

        System.out.println(ans);
    }

    public static void floyd(){
        for(int k = 1; k < n + 1; k++){
            for(int i = 1; i < n + 1; i++){
                for(int j = i + 1; j < n + 1; j++){
                    /**
                     * 시간 초과 발생을 예방하기 위해서, j는 i+1번째 노드 부터 탐색 시작
                     *  -> 양방향이기 때문에 i->j를 찾으면 j->i도 거리가 같기 때문에 또 탐색할 필요 X
                     * 시간 초과 발생을 예방하기 위해, 도달하지 못하는 경우에는 건너뜀
                     */
                    if(dist[i][k] == INF || dist[k][j] == INF || k == i || k == j){
                        continue;
                    }
                    dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                    dist[j][i] = dist[i][j];
                }
            }
        }
    }

}