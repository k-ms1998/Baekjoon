package Gold.Graph.BFS;

import java.io.*;
import java.util.*;

/**
 * 1. grid에서 현재 쓰레기가 있는 칸이면, 인접한 빈칸('.')을 'n'으로 변경
 * 	1-1. 만약 빈칸이 아닌 칸들도 해주면, 인접한 칸이 'g'에서 '.'로 변경 될수도 있음 -> 오답 유발
 * 2. 시작 지점에서 도착 지점까지 다익스트라로 경로 찾기
 * 	2-1. 이때 만약 인접한 칸으로 갔을때 지나간 쓰레기 칸의 숫자가 더 작은 값으로 업데이트 가능하면 값 업데이트 && 큐에 넣기
 * 	2-2. 인접한 칸으로 갔을때 지나간 쓰레기 칸의 숫자가 현재 쓰레기를 지나간 칸의 숫와 같을때, 쓰레기랑 인접한 칸을 방문한 횟수를 비교
 * 3. 도착지점의 trash랑 trashNear 값 출력
 */
public class Prob1445 {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    static char[][] grid;
    static Info[][] minTrash;
    static int height, width;
    static int srcCol, srcRow;
    static int dstCol, dstRow;

    static final int[] dCol = {1, 0, -1, 0};
    static final int[] dRow = {0, 1, 0, -1};
    static final int INF = 100_000_000;

    public static void main(String[] args) throws IOException{
        st = new StringTokenizer(br.readLine());
        height = Integer.parseInt(st.nextToken());
        width = Integer.parseInt(st.nextToken());

        grid = new char[height][width];
        minTrash = new Info[height][width];
        for(int row = 0; row < height; row++) {
            String curRow = br.readLine();
            for(int col = 0; col < width; col++) {
                grid[row][col] = curRow.charAt(col);
                if(grid[row][col] == 'F') {
                    dstCol = col;
                    dstRow = row;
                }
                if(grid[row][col] == 'S') {
                    srcCol = col;
                    srcRow = row;
                }
                minTrash[row][col] = new Info(INF, INF);
            }
        }

        for(int row = 0; row < height; row++) {
            for(int col = 0; col < width; col++) {
                if(grid[row][col] == 'g') {
                    for(int dir = 0; dir < 4; dir++) {
                        int nCol = col + dCol[dir];
                        int nRow = row + dRow[dir];

                        if(nCol < 0 || nRow < 0 || nCol >= width || nRow >= height) {
                            continue;
                        }
                        if(grid[nRow][nCol] == '.') { // 인접한 칸이 빈칸인 경우에만
                            grid[nRow][nCol] = 'n';
                        }
                    }
                }
            }
        }

        PriorityQueue<Node> queue = new PriorityQueue<>(new Comparator<Node>() {
            @Override
            public int compare(Node n1, Node n2) {
                if(n1.trash == n2.trash) {
                    return n1.trashNear - n2.trashNear;
                }

                return n1.trash - n2.trash;
            }
        });

        minTrash[srcRow][srcCol] = new Info(0, 0);
        queue.offer(new Node(srcCol, srcRow, 0, 0));
        while(!queue.isEmpty()) {
            Node node = queue.poll();
            int col = node.col;
            int row = node.row;
            int trash = node.trash;
            int trashNear = node.trashNear;

            if(col == dstCol && row == dstRow) {
                break;
            }

            for(int dir = 0; dir < 4; dir++) {
                int nCol = col + dCol[dir];
                int nRow = row + dRow[dir];

                if(nCol < 0 || nRow < 0 || nCol >= width || nRow >= height) {
                    continue;
                }

                int nextTrash = trash;
                int nextTrashNear = trashNear;
                if(grid[nRow][nCol] == 'g') { // 다음 이동할 칸이 쓰레기 칸이면
                    nextTrash++;
                }

                if(grid[nRow][nCol] == 'n') { // 다음 이동할 칸이 쓰레기랑 인접한 칸이면
                    nextTrashNear++;
                }

                if(minTrash[nRow][nCol].trash > nextTrash) {
                    minTrash[nRow][nCol].trash = nextTrash;
                    minTrash[nRow][nCol].trashNear = nextTrashNear;
                    queue.offer(new Node(nCol, nRow, nextTrash, nextTrashNear));

                }else if(minTrash[nRow][nCol].trash == nextTrash) {
                    if(minTrash[nRow][nCol].trashNear > nextTrashNear) {
                        minTrash[nRow][nCol].trash = nextTrash;
                        minTrash[nRow][nCol].trashNear = nextTrashNear;
                        queue.offer(new Node(nCol, nRow, nextTrashNear, nextTrashNear));
                    }
                }
            }
        }

        System.out.printf("%d %d", minTrash[dstRow][dstCol].trash, minTrash[dstRow][dstCol].trashNear);
    }

    public static class Node{
        int col;
        int row;
        int trash;
        int trashNear;

        public Node(int col, int row, int trash, int trashNear) {
            this.col = col;
            this.row = row;
            this.trash = trash;
            this.trashNear = trashNear;
        }
    }

    public static class Info{
        int trash;
        int trashNear;

        public Info(int trash, int trashNear) {
            this.trash = trash;
            this.trashNear = trashNear;
        }
    }

}
