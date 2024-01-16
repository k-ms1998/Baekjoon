package Gold.Graph.BFS;
import java.io.*;
import java.util.*;

/**
 * 1. N, M 입력 (2 <= N, M <= 1_000)
 * 2. N x M 개의 칸에 대한 정보 입력
 * 3. 각 칸은 숲으로 막혀 있거나, 지나갈 수 있도록 비어 있다
 * 4. 준겸이는 인접한 상하좌우로 움직일 수 있다(오른쪽=(0,1), 아래쪽=(1,0))
 *  4-1. 각 줄이랑 행의 마지막 칸에서 다음칸으로 움직이면 반대쪽으로 움직인다(ex: (0, M-1)에서 오른쪽을 한칸 움직이면 (0,0))
 * 5. 준겸이가 움직일 수 있는 총 빈 구역의 갯수를 구하기(A->B로 갈 수 있으면 같은 구역) => Flood 문제?
 */
public class Prob27211 {

    static int N, M;
    static int[][] grid;
    static boolean[][] visited;

    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {-1, 0, 1, 0};

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine().trim());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        grid = new int[N][M];
        visited = new boolean[N][M];
        for(int y = 0; y < N; y++){
            st = new StringTokenizer(br.readLine().trim());
            for(int x = 0; x < M; x++){
                grid[y][x] = Integer.parseInt(st.nextToken());
            }
        }

        int count = 0;
        for(int y = 0; y < N; y++){
            for(int x = 0; x < M; x++){
                if(grid[y][x] == 0 && !visited[y][x]){
                    ++count;
                    Deque<Point> queue = new ArrayDeque<>();
                    queue.offer(new Point(x, y));
                    visited[y][x] = true;

                    while(!queue.isEmpty()){
                        Point p = queue.poll();
                        int px = p.x;
                        int py = p.y;

                        for(int dir = 0; dir < 4; dir++){
                            int nx = px + dx[dir];
                            int ny = py + dy[dir];

                            if(nx < 0){
                                nx = M - 1;
                            }
                            if(ny < 0){
                                ny = N - 1;
                            }
                            if(nx >= M){
                                nx = 0;
                            }
                            if(ny >= N){
                                ny = 0;
                            }

                            if(grid[ny][nx] == 0 && !visited[ny][nx]){
                                visited[ny][nx] = true;
                                queue.offer(new Point(nx, ny));
                            }
                        }
                    }

                }
            }
        }

        System.out.println(count);
    }

    public static class Point{
        int x;
        int y;

        public Point(int x, int y){
            this.x = x;
            this.y = y;
        }
    }
}
