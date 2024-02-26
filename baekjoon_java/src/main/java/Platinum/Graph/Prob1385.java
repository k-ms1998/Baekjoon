package Platinum.Graph;

import java.io.*;
import java.util.*;

/**
 * 1.벌집 모양의 격자가 주어진다
 * 2.a->b로 갈 수 있는 최단거리 구하기
 * 3. a, b <= 1_000_000
 *  3-1. 벌집 모양이기 때문에 높이를 2배 들려야 한다
 *  3-2. 벌집 모양으로 1_000_000 만큼의 번호를 부여하기 위해서는 약 4_000 * 2_000 크기의 격자 필요
 *  3-3. 시작지점(1)이 중앙에 오기 위해서 4001 * 2001 크기의 배열 생성
 * 4. 벌집 만들기
 *  4-1. 처음 1번과 6방향으로 인접한 번호들을 하드코딩으로 부여
 *  4-2. 그 다음 층 부터는 (오른위, 오른아래, 아래, 왼아래, 왼위, 위) 순서로 (1 + 층, 2 + 층, 2 + 층, 2 + 층, 2 + 층, 2 + 층)만큼 움직임
 *      4-2-1. 4-2의 규칙에 따라 각 칸에 번호 부여 & index배열에 각 번호의 위치를 저장
 * 5. start지점에서 end 지점까지 도달하는데 걸리는 최단거리 구하기(다익스트라)
 *  5-1. 이때, 하나의 칸에서 인접한 칸들은 (오른아래, 아래, 왼아래, 왼위, 위, 오른위) 이다
 * 6. end에서 start로 이동하면서 경로를 차기
 *  6-1. 현재 지점까지의 최단거리 - 1로 이동할 수 있는 인접한 칸으로 움직이기
 */
public class Prob1385 {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    static int start;
    static int end;

    static int[][] grid;
    static int[][] dist;
    static Point[] index;
    static List<Point> route = new ArrayList<>();
    static int num = 1;

    // 오른아래, 아래, 왼아래, 왼위, 위, 오른위
    static final int[] dCol = {1, 0, -1, -1, 0, 1};
    static final int[] dRow = {1, 2, 1, -1, -2, -1};

    //오른위, 오른아래, 아래, 왼아래, 왼위, 위
    static final int[] iCol = {1, 1, 0, -1, -1, 0};
    static final int[] iRow = {-1, 1, 2, 1, -1, -2};
    static final int[] iCount = {1, 2, 2, 2, 2, 2};

    static final int HEIGHT = 4001;
    static final int WIDTH = 2001;
    static final int MAX_NUM = 1_000_000;
    static final int INF = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException{
        st = new StringTokenizer(br.readLine().trim());

        start = Integer.parseInt(st.nextToken());
        end = Integer.parseInt(st.nextToken());

        grid = new int[HEIGHT][WIDTH];
        index = new Point[1_000_001];
        for(int idx = 0; idx < index.length; idx++){
            index[idx] = new Point(0, 0, -1);
        }
        int midRow = HEIGHT / 2;
        int midCol = WIDTH / 2;

        initGrid(midCol, midRow); // 1~7번 하드 코딩
        createGrid(midCol - 1, midRow - 3, 0); // 규칙에 따라서 8번부터 번호 부여
        initDist(); // dist배열 초기화
        findMinDist(); // start->end 최단 거리 차기

        Point endPoint = index[end];
        findRoute(endPoint.col, endPoint.row, dist[endPoint.row][endPoint.col]); // 경로 구하기

        for(int idx = route.size() - 1; idx >=0; idx--){
            Point curPoint = route.get(idx);
            int curCol = curPoint.col;
            int curRow = curPoint.row;

            int curNum = grid[curRow][curCol];
            sb.append(curNum).append(" ");
        }

        System.out.println(sb.toString().trim());
    }

    public static void initGrid(int midCol, int midRow){
        grid[midRow][midCol] = num;
        index[num] = new Point(midCol, midRow, 0);
        num++;
        grid[midRow - 2][midCol] = num;
        index[num] = new Point(midCol, midRow - 2, 0);
        num++;
        grid[midRow - 1][midCol + 1] = num;
        index[num] = new Point(midCol + 1, midRow - 1, 0);
        num++;
        grid[midRow + 1][midCol + 1] = num;
        index[num] = new Point(midCol + 1, midRow + 1, 0);
        num++;
        grid[midRow + 2][midCol] = num;
        index[num] = new Point(midCol, midRow + 2, 0);
        num++;
        grid[midRow + 1][midCol - 1] = num;
        index[num] = new Point(midCol - 1, midRow + 1, 0);
        num++;
        grid[midRow - 1][midCol - 1] = num;
        index[num] = new Point(midCol, midRow - 2, 0);
        num++;
    }

    public static void createGrid(int startCol, int startRow, int depth){
        if(startCol < 0 || startRow < 0 || startCol >= WIDTH || startRow >= HEIGHT){ // 혹시 모를 범위 밖 예외 처리
//            System.out.println("[START : OUT OF BOUNDS]");
            return;
        }
        int col = startCol;
        int row = startRow;

        grid[row][col] = num;
        index[num] = new Point(col, row, 0);
        num++;
        for(int dir = 0; dir < 6; dir++){
            int moveLength = iCount[dir] + depth;
            for(int count = 0; count < moveLength; count++){
                if(num > MAX_NUM){
                    return;
                }
                col += iCol[dir];
                row += iRow[dir];

//                System.out.printf("col=%d, row=%d, depth=%d\n", col, row, depth);
                if(col < 0 || row < 0 || col >= WIDTH || row >= HEIGHT){ // 혹시 모를 범위 밖 예외 처리
                    continue;
                }
                grid[row][col] = num;
                index[num] = new Point(col, row, 0);
                num++;
            }
        }

        createGrid(col, row - 2, depth + 1);
    }

    public static void initDist(){
        dist = new int[HEIGHT][WIDTH];
        for(int row = 0; row < HEIGHT; row++){
            Arrays.fill(dist[row], INF);
        }
    }

    public static void findMinDist(){
        Point startPoint = index[start];
        Deque<Point> queue = new ArrayDeque<>();
        queue.offer(new Point(startPoint.col, startPoint.row, 0));
        dist[startPoint.row][startPoint.col] = 0;
        while(!queue.isEmpty()){
            Point p = queue.poll();
            int col = p.col;
            int row = p.row;
            int d = p.d;

            for(int dir = 0; dir < 6; dir++){
                int nCol = col + dCol[dir];
                int nRow = row + dRow[dir];

                if(nCol < 0 || nRow < 0 || nCol >= WIDTH || nRow >= HEIGHT){
                    continue;
                }

                if(dist[nRow][nCol] > d + 1 && grid[nRow][nCol] != 0){
                    dist[nRow][nCol] = d + 1;
                    queue.offer(new Point(nCol, nRow, d + 1));
                }
            }
        }
    }

    public static void findRoute(int col, int row, int curDist){
        route.add(new Point(col, row, 0));
        if(curDist == 0){
            return;
        }

        for(int dir = 0; dir < 6; dir++){
            int nCol = col + dCol[dir];
            int nRow = row + dRow[dir];

            if(nCol < 0 || nRow < 0 || nCol >= WIDTH || nRow >= HEIGHT){
                continue;
            }

            if(dist[nRow][nCol] == curDist - 1){
                findRoute(nCol, nRow, curDist - 1);
                return;
            }
        }
    }

    public static void printGrid(){
        for(int row = 0; row < HEIGHT; row++){
            for(int col = 0; col <WIDTH; col++){
                System.out.printf("%2d ", grid[row][col]);
            }
            System.out.println();
        }
        System.out.println("-----");
    }

    public static void printAround(int col, int row){
        for(int dir = 0; dir < 6; dir++){
            int nCol = col + dCol[dir];
            int nRow = row + dRow[dir];

            if(nCol < 0 || nRow < 0 || nCol >= WIDTH || nRow >= HEIGHT){
                System.out.printf("dir=%d, num=%d, dist=%d\n", dir, -1, dist[nRow][nCol]);
                continue;
            }else{
                System.out.printf("dir=%d, num=%d, dist=%d\n", dir, grid[nRow][nCol], dist[nRow][nCol]);
            }

        }
    }

    public static class Point{
        int col;
        int row;
        int d;

        public Point(int col, int row, int d){
            this.col = col;
            this.row = row;
            this.d = d;
        }

        @Override
        public String toString(){
            return String.format("[col=%d, row=%d, d=%d]", col, row, d);
        }
    }
}
