package Gold.DP.Floyd_Warshall;

import java.io.*;
import java.util.*;

/**
 * Gold 2(궁금한 민호)
 *
 * https://www.acmicpc.net/problem/1507
 *
 * Solution: Floyd-Warshall
 */
public class Prob1507 {

    static int n;

    static final int INF = 1000000000;
    static int[][] original;
    static int[][] dist;

    static int ans = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        dist = new int[n + 1][n + 1];
        original = new int[n + 1][n + 1];
        for (int i = 1; i < n + 1; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j < n + 1; j++) {
                int cost = Integer.parseInt(st.nextToken());
                dist[i][j] = cost;
                original[i][j] = cost;
            }
        }

        floydWarshall();
        if(ans != -1){
            findAnswer();
        }
        printGrid(dist);
        System.out.println(ans);
    }

    /**
     * 플로이드-와셜을 이용해서, i->j로 갈때 다른 노드를 거쳐서 가는지 아닌지 확인
     * 이때,다른 노드를 거쳐서 가면 i->j를 연결하는 도로는 없는것이고, i->k && k->j 도로 덕분에 i->j가 연결이 되는 것
     * 그러므로, i->j == i->k + k->j이면 i->j 도로는 없는 것 => i->j 거리를 0으로 치환
     *
     * ex:
     * 0 6 15 2 6   -> i == 1; 0 6 0 2 0
     * 6 0 9 8 12   -> i == 2; 6 0 9 0 0
     * 15 9 0 16 18 -> i == 3; 0 9 0 16 18
     * 2 8 16 0 4   -> i == 4; 2 0 16 0 4
     * 6 12 18 4 0  -> i == 5; 0 0 18 4 0
     * => ANS = 6 + 2 + 9 + 16 + 18 + 4 = 55
     *
     * 플로이드-와셜로 다른 노드를 거쳐서 최단 거리로 도달 가능한 거리들을 제외하게 되면,
     * 남는 거리들은 i->j를 바로 연결하는 도로들만 남게됨
     * 해당 도로들의 거리의 합 == ANSWER
     *
     * 만약에, i->j의 값이 i->k + j->k의 값보다 크면 모순이므로 불가능한 경우 => -1 출력
     * (이미 입력으로 주어진 값들이 i->j 까지의 최단 거리인데, i->k + j->k의 값이 더 작은 것은 있으면 안되는 경우)
     */
    public static void floydWarshall() {
        for (int k = 1; k < n + 1; k++) {
            for (int i = 1; i < n + 1; i++) {
                for (int j = i + 1; j < n + 1; j++) {
                    /**
                     * k == i 또는 i == j 또는 j == k 인 경우들은 건너뜀으로써,
                     * 다른 노드를 거치지 않고 i -> j로 바로 가는 경우들은 건너뜀
                     * (i->k->j 로 갈때, i == k || j == k 이면 i->j랑 같음)
                     */
                    if (k == i || i == j || j == k) {
                        continue;
                    }

                    if (original[i][j] > original[i][k] + original[k][j]) {
                        ans = -1;
                        return;
                    } else if (original[i][j] == original[i][k] + original[k][j]) {
                        dist[i][j] = 0;
                        dist[j][i] = 0;
                    }
                }
            }
        }
    }

    public static void findAnswer() {
        for (int i = 1; i < n + 1; i++) {
            for (int j = i + 1; j < n + 1; j++) {
                ans += dist[i][j];
            }
        }
    }

    public static void printGrid(int[][] grid) {
        for (int i = 1; i < n + 1; i++) {
            for (int j = 1; j < n + 1; j++) {
                int num = grid[i][j] != INF ? grid[i][j] : 0;
                System.out.print(num + " ");
            }
            System.out.println();
        }
        System.out.println("----------[Grid]----------");
    }
}
