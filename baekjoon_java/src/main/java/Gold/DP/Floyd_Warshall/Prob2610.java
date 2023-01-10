package Gold.DP.Floyd_Warshall;

import java.io.*;
import java.util.*;

/**
 * Gold 2(회의 준비)
 *
 * https://www.acmicpc.net/problem/2610
 *
 * Solution: Floyd-Warshall
 * 1. 플로이드를 이용해서, 각 노드에 대해서 모든 노드까지의 거리 계산
 * 2. 계산한 거리를 통해서, 하나의 노드에서 도달 가능한 모든 노드들 찾을 수 있음(거리 != INF 이면 도달 가능)
 * 3. 도달 가능한 노드들끼리는 하나의 위원회
 * 4. 각 위원회에 대해서, 위원회에 속한 모든 노드들 확인
 *  4-1. 각 노드에 대해서, 같은 위원회에 있는 특정 노드까지의 최대 거리 값 확인
 *  4-2. 해당 최댓값에 대해서, 가장 작은 값을 가진 노드가 위원회 대표
 * 5. 각 위원회 대표들을 값이 작은 순서로 정렬 후 출력
 */
public class Prob2610 {

    static int n, m;
    static int[][] dist;
    static boolean[] visited;
    static int[] group;
    static final int INF = 1000000000;

    static StringBuilder ans = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        m = Integer.parseInt(br.readLine());


        dist = new int[n + 1][n + 1];
        group = new int[n + 1];
        visited = new boolean[n + 1];
        for (int i = 1; i < n + 1; i++) {
            for (int j = 1; j < n + 1; j++) {
                if (i != j) {
                    dist[i][j] = INF;
                }
            }
        }
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            dist[a][b] = 1;
            dist[b][a] = 1;
        }
        floyd();

        /**
         * 연결된 노드들끼리 위원회 결성
         */
        int groupNum = 1;
        for (int y = 1; y < n + 1; y++) {
            if (visited[y]) {
                continue;
            }
            visited[y] = true;
            group[y] = groupNum;
            for (int x = 1; x < n + 1; x++) {
                if (dist[y][x] != INF && !visited[x]) {
                    visited[x] = true;
                    group[x] = groupNum;
                }
            }

            ++groupNum;
        }
        ans.append(groupNum - 1).append("\n");

        /**
         * 각 위원회의 대표 고르기
         */
        int[] rep = new int[groupNum];
        for (int curGroup = 1; curGroup < groupNum; curGroup++) {
            int idx = 0;
            int curDist = INF;
            for (int i = 1; i < n + 1; i++) {
                if (group[i] == curGroup) {
                    int tmpDist = 0;
                    for (int j = 1; j < n + 1; j++) {
                        if (dist[i][j] != INF) {
                            tmpDist = Math.max(tmpDist, dist[i][j]);
                        }
                    }

                    if (tmpDist < curDist) {
                        curDist = tmpDist;
                        idx = i;
                    }
                }
            }
            rep[curGroup] = idx;
        }
        Arrays.sort(rep);
        for (int i = 1; i < groupNum; i++) {
            ans.append(rep[i]).append("\n");
        }
        System.out.println(ans);
    }

    public static void floyd() {
        for (int k = 1; k < n + 1; k++) {
            for (int i = 1; i < n + 1; i++) {
                for (int j = 1; j < n + 1; j++) {
                    if (dist[i][k] == INF || dist[k][j] == INF) {
                        continue;
                    }

                    dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                }
            }
        }
    }
}