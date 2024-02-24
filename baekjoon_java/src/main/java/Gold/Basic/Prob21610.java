package Gold.Basic;

import java.io.*;
import java.util.*;

/**
 * 1. 1번 행과 N번 행은 연결되어 있음 & 1번 열과 N번 열은 연결되어 있음
 *  1-1. 즉, 범위 밖을 벗어나면 반대쪽으로 간다
 * 2. (N, 1), (N, 2), (N-1, 1), (N-1, 2) 에 비구름이 생긴다
 *  2-1. d방향으로 s거리 만큼 이동을 한다
 *  2-2. 총 8 방향으로 이동가능 -> 왼쪽, 왼쪽위, 위, 오른쪽위, 오른쪽, 오른쪽아래, 아래, 왼쪽아래
 * 3. 이동을 명령하면 다음 순서대로 진행
 *  3-1. 모든 구름은 d방향으로 s칸 이동한다
 *  3-2. 각 구름에서 비가 내려 구름이 있는 칸의 바구니에 저장된 물의 양이 1증가
 *  3-3. 구름이 사라진다
 *  3-4. 3-2에서 물이 증가한 칸(r,c)에 물 복사 버그 마법을 시전한다
 *      3-4-1. 대각선 방향으로 1인 칸에 물이 있는 바구니의 수 만큼 (r, c)에 있는 바구니의 물 양이 증가한다
 *          3-4-1-1. 이때는 격자를 벗어나서 대각선에 있는 칸은 취급하지 않는다
 *  3-5. 바구니에 저장된 물의 양이 2 이상인 모든 칸에 구름이 생기고 물의 양이 2 줄어든다
 *      3-5-1. 이때 구름이 생기는 칸은 3-3에서 구름이 사라진 칸이 아니어야 한다
 *
 */
public class Prob21610 {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    static int size, commandSize;
    static int[][] grid;

    static final int[] dCol = {0, -1, -1, 0, 1, 1, 1, 0, -1};
    static final int[] dRow = {0, 0, -1, -1, -1, 0, 1, 1, 1};

    static final int[] diagCol = {-1, 1, 1, -1};
    static final int[] diagRow = {-1, -1, 1, 1};

    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine());
        size = Integer.parseInt(st.nextToken());
        commandSize = Integer.parseInt(st.nextToken());

        grid = new int[size][size];
        for(int row = 0; row < size; row++){
            st = new StringTokenizer(br.readLine());
            for(int col = 0; col < size; col++){
                grid[row][col] = Integer.parseInt(st.nextToken());
            }
        }

        Deque<Point> clouds = new ArrayDeque<>();
        clouds.offer(new Point(0, size - 2));
        clouds.offer(new Point(0, size - 1));
        clouds.offer(new Point(1, size - 2));
        clouds.offer(new Point(1, size - 1));
        for(int idx = 0; idx < commandSize; idx++){
            st = new StringTokenizer(br.readLine());
            int dir = Integer.parseInt(st.nextToken());
            int dist = Integer.parseInt(st.nextToken());

            int cloudSize = clouds.size();
            boolean[][] visited = new boolean[size][size];
            // 모든 구름을 이동시키기
            for(int count = 0; count < cloudSize; count++){
                Point p = clouds.poll();
                int nCol = (p.col + dist*(dCol[dir] == -1 ? size - 1 : dCol[dir])) % size;
                int nRow = (p.row + dist*(dRow[dir] == -1 ? size - 1 : dRow[dir])) % size;

                // 움직인 구름의 위치에 비 내리기 & 구름 없애기(방문 체크)
                grid[nRow][nCol]++;
                visited[nRow][nCol] = true;
                clouds.offer(new Point(nCol, nRow));
            }
            for(int count = 0; count < cloudSize; count++){
                Point p = clouds.poll();
                int nCol = p.col;
                int nRow = p.row;

                // 해당 칸들 대각선의 위치들 확인하기
                for(int diag = 0; diag < 4; diag++){
                    int ddCol = nCol + diagCol[diag];
                    int ddRow = nRow + diagRow[diag];

                    if(ddCol < 0 || ddRow < 0 || ddCol >= size || ddRow >= size){
                        continue;
                    }

                    if(grid[ddRow][ddCol] >= 1){
                        grid[nRow][nCol]++;
                    }
                }
            }

            for(int row = 0; row < size; row++){
                for(int col = 0; col < size; col++){
                    if(!visited[row][col] && grid[row][col] >= 2){
                        grid[row][col] -= 2;
                        clouds.offer(new Point(col, row));
                    }
                }
            }

//            printGrid();
        }

        int answer = 0;
        for(int row = 0; row < size; row++){
            for(int col = 0; col < size; col++){
                answer += grid[row][col];
            }
        }

        System.out.println(answer);
    }

    public static void printGrid(){
        for(int row = 0; row < size; row++){
            for(int col = 0; col < size; col++){
                System.out.print(grid[row][col] + " ");
            }
            System.out.println();
        }
        System.out.println("----------");
    }

    public static class Point{
        int col;
        int row;

        public Point(int col, int row){
            this.col = col;
            this.row = row;
        }

        @Override
        public String toString(){
            return String.format("[col=%d, row=%d]", col, row);
        }
    }
}
