package Gold.Graph;

import java.io.*;
import java.util.*;

/**
 * Gold 3(소문난 칠공주)
 *
 * https://www.acmicpc.net/problem/1941
 *
 * Solution: Combination + BFS
 * 1. 모든 칸에 대해서 0~24까지의 번호를 매기고, 해당 번호들 7가지로 만들 수 있는 모든 조합들 찾기(makeCombinations)
 * 2. 각 조합에 대해서, 연결 되어 있는지 확인(BFS)
 *  2-1. 첫번째 노드에서 시작
 *  2-2. 첫번째 노드에서, 인접한 노드가 조합중에 있는지 확인
 *  2-3. 있으면, queue 에 추가해서 해당 노드랑 인접한 노드가 또 있는지 확인
 *  2-4. 모든 노드를 탐색할때까지 확인
 * 3. 해당 조합의 노드들이 모두 끊기지 않고 연결되어 있으면, 그 중에서 'S' 값을 가진 노드의 갯수를 계산
 */
public class Prob1941 {

    static Node[] grid;
    static List<Comb> combinations = new ArrayList<>();

    static int[] dx = {1, 0, -1, 0};
    static int[] dy = {0, 1, 0, -1};

    static int ans = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        grid = new Node[25];
        for (int y = 0; y < 5; y++) {
            String curRow = br.readLine();
            for (int x = 0; x < 5; x++) {
                char c = curRow.charAt(x);
                grid[5 * y + x] = new Node(x, y, c);
            }
        }

        makeCombination();
        findAns();
        System.out.println(ans);
    }

    public static void findAns() {
        for (Comb combination : combinations) {
            if(isAdj(combination)){
                ++ans;
            }
        }
    }

    public static boolean isAdj(Comb comb) {
        Node[] tmp = {grid[comb.n1], grid[comb.n2], grid[comb.n3], grid[comb.n4], grid[comb.n5], grid[comb.n6], grid[comb.n7]};
        boolean[] adj = new boolean[7];

        Deque<Node> queue = new ArrayDeque<>();
        queue.offer(tmp[0]);
        adj[0] = true;
        while(!queue.isEmpty()){
            Node cur = queue.poll();
            int x = cur.x;
            int y = cur.y;


            for (int i = 0; i < 4; i++) {
                int nx = x + dx[i];
                int ny = y + dy[i];
                if (ny < 0 || ny >= 5 || nx < 0 || nx >= 5) {
                    continue;
                }
                for (int j = 0; j < 7; j++) {
                    Node next = tmp[j];
                    if (next.x == nx && next.y == ny && !adj[j]) {
                        queue.offer(next);
                        adj[j] = true;
                    }
                }
            }
        }

        int cnt = 0;
        for (int i = 0; i < 7; i++) {
            if(!adj[i]){
                return false;
            }
            if(tmp[i].c == 'S'){
                ++cnt;
            }
        }

        return cnt >= 4 ? true : false;
    }

    private static void makeCombination() {
        for (int i1 = 0; i1 < 19; i1++) {
            for (int i2 = i1 + 1; i2 < 20; i2++) {
                for (int i3 = i2 + 1; i3 < 21; i3++) {
                    for (int i4 = i3 + 1; i4 < 22; i4++) {
                        for (int i5 = i4 + 1; i5 < 23; i5++) {
                            for (int i6 = i5 + 1; i6 < 24; i6++) {
                                for (int i7 = i6 + 1; i7 < 25; i7++) {
                                    combinations.add(new Comb(i1, i2, i3, i4, i5, i6, i7));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static class Comb{
        int n1;
        int n2;
        int n3;
        int n4;
        int n5;
        int n6;
        int n7;

        public Comb(int n1, int n2, int n3, int n4, int n5, int n6, int n7) {
            this.n1 = n1;
            this.n2 = n2;
            this.n3 = n3;
            this.n4 = n4;
            this.n5 = n5;
            this.n6 = n6;
            this.n7 = n7;
        }

        @Override
        public String toString() {
            return "Comb{" +
                    "n1=" + n1 +
                    ", n2=" + n2 +
                    ", n3=" + n3 +
                    ", n4=" + n4 +
                    ", n5=" + n5 +
                    ", n6=" + n6 +
                    ", n7=" + n7 +
                    '}';
        }
    }

    public static class Node{
        int x;
        int y;
        char c;

        public Node(int x, int y, char c) {
            this.x = x;
            this.y = y;
            this.c = c;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "x=" + x +
                    ", y=" + y +
                    ", c=" + c +
                    '}';
        }
    }
}
