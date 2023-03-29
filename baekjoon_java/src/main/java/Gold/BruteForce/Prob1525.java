package Gold.BruteForce;

import java.io.*;
import java.util.*;

/**
 * Gold 2(퍼즐)
 *
 * https://www.acmicpc.net/problem/1525
 *
 * Solution: 완전탐색 (BFS)
 * 1. 0 위치를 기준으로, 인접한 칸과 swap
 * 2. swap 할때마다 해당 조합을 확인했는지 안했는지 확인
 *  -> 시간 초과 및 메모리 초과를 방지하기 위해서, 3X3 칸을 문자열로 변환
 *      -> ex; 123
 *             456
 *             780 => "123456780"
 *      -> 각 조합의 문자열을 HashSet 에 넣기
 *      -> 만약, HashSet 에 해당 문자열이 이미 있으면, 이미 해당 조합은 확인을 했으므로 무시
 *  -> 문자열에서 swap:
 *      -> 서로 swap 할 인덱스 구하기 -> 3X3 배열이므로, (x, y) -> 3*y + x
 *      -> replace() 를 통해 스왑 구현 가능
 *          -> ex: 123457680 이 있고, 7과0을 바꾸고 싶을때:
 *                  123456780 -> 12349680 (7을 임의로 9로 변경)
 *                  => 12349680 -> 12349687 (0을 7로 변경)
 *                      => 12349687 -> 12340687 (임이의 9를 0으로 변경)
 */
public class Prob1525 {

    static int zx, zy;
    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {1, 0, -1, 0};
    static Set<String> visited = new HashSet<>();

    static int answer;
    static final int INF = 1000000000;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        answer = INF;

        String initBoard = "";
        for (int y = 0; y < 3; y++) {
            st = new StringTokenizer(br.readLine());
            for(int x = 0; x < 3; x++){
                int num = Integer.parseInt(st.nextToken());
                initBoard += String.valueOf(num);
                if(num == 0){
                    zx = x;
                    zy = y;
                }
            }
        }

        Deque<Info> queue = new ArrayDeque<>();
        queue.offer(new Info(initBoard, 0, zx, zy));
        visited.add(initBoard);
        while (!queue.isEmpty()) {
            Info info = queue.poll();
            String board = info.board;
            int cnt = info.cnt;
            int x = info.x;
            int y = info.y;

            if (board.equals("123456780")) {
                answer = Math.min(answer, cnt);
                break;
            }

            int idxA = 3 * y + x;
            char c = board.charAt(idxA);
            for (int i = 0; i < 4; i++) {;
                int nx = x + dx[i];
                int ny = y + dy[i];
                if(nx < 0 || ny < 0 || nx >= 3 || ny >= 3){
                    continue;
                }

                int idxB = 3 * ny + nx;
                char nextC = board.charAt(idxB);
                String nextBoard = board.replace(nextC, '9');
                nextBoard = nextBoard.replace(c, nextC);
                nextBoard = nextBoard.replace('9', c);
                if(!visited.contains(nextBoard)) {
                    visited.add(nextBoard);
                    queue.offer(new Info(nextBoard, cnt + 1, nx, ny));
                }
            }
        }

        System.out.println(answer == INF ? -1 : answer);
    }

    public static class Info{
        String board;
        int cnt;
        int x;
        int y;

        public Info(String board, int cnt, int x, int y){
            this.board = board;
            this.cnt = cnt;
            this.x = x;
            this.y = y;
        }
    }
}
