import java.io.*;
import java.util.*;

public class Main {
    
    static int n, m;
    static char[][] grid;
    static int[][] time; // 얼음들이 녹는데 걸리는 시간 저장
    static int[][] dist;

    static int startX = -1;
    static int startY = -1;
    static int endX = -1;
    static int endY = -1;
    
    static final int[] dx = {0, 1, 0, -1};
    static final int[] dy = {1, 0, -1, 0};
    static final int INF = 100_000_000;
    
    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        
        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        
        grid = new char[n][m];
        time = new int[n][m];
        dist = new int[n][m];
        for(int y = 0; y < n; y++){
            String row = br.readLine();
            for(int x = 0; x < m; x++){
                grid[y][x] = row.charAt(x);
                dist[y][x] = INF;
                if(grid[y][x] == 'L'){
                    if(startX == -1){
                        startX = x;
                        startY = y;
                    }else{
                        endX = x;
                        endY = y;
                    }
                }
            }
        }
        
        /*
        * 모든 X 좌표를 확인하면서 인접한 좌표가 물이거나 백조이면 큐에 추가
        * 해당 좌표들은 1초 지나는 시점에 바로 녹기 때문에 time을 1로 저장
        */
        Deque<Point> toMelt = new ArrayDeque<>();
        for(int y = 0; y < n; y++){
            for(int x = 0; x < m; x++){
                if(grid[y][x] == 'X'){
                    for(int dir = 0; dir < 4; dir++){
                        int nx = x + dx[dir];
                        int ny = y + dy[dir];
                        
                        if(nx < 0 || ny < 0 || nx >= m || ny >= n){
                            continue;
                        }
                        
                        if(grid[ny][nx] == '.' || grid[ny][nx] == 'L'){
                            time[y][x] = 1;
                            toMelt.offer(new Point(x, y, 1));
                            break;
                        }
                    }
                }
            }
        }
        
        /*
        * 큐가 빈 큐가 될때까지 탐색
        * 현재 큐에서 pop되는 좌표들은 모두 얼음이였다가 녹은 좌표들
        * 즉, 이제는 물이 된 좌표들이기 때문에 인접한 좌표에 얼음이 있으면 해당 얼음을 녹일 수 있음
        * 이때, 인접한 좌표가 처음 방문(time == 0)이거나 물이 되는데 걸리는 시간이 최소가 되도록 업데이트가 가능하면 큐에 추가
        */
        while(!toMelt.isEmpty()){
            Point p = toMelt.poll();
            int x = p.x;
            int y = p.y;
            int curTime = p.cnt;
                
            for(int dir = 0; dir < 4; dir++){
                int nx = x + dx[dir];
                int ny = y + dy[dir];
                        
                if(nx < 0 || ny < 0 || nx >= m || ny >= n){
                    continue;
                }
                        
                if(grid[ny][nx] == 'X' && (time[ny][nx] == 0 || time[ny][nx] > curTime + 1)){
                    time[ny][nx] = curTime + 1;
                    toMelt.offer(new Point(nx, ny, curTime + 1));
                }
            }
        }
        
        int answer = INF;

        /*
        * 다익스트라 -> PQ로 거리가 cnt가 가장 가까운 좌표가 가장 앞에 있음
        * 도착지점에 도달하면 break -> 더 이상 cnt가 더 작은 값으로 도착지점에 올 수 없기 때문에
            -> PQ 안쓰면 도착지점에 도달했을때 continue하면 -> 시간 초과 발생
            -> PQ 안쓰고 도착지점에 도달했을때 break히면 -> 오답 
        */
        PriorityQueue<Point> queue = new PriorityQueue<>(new Comparator<Point>(){
            @Override
            public int compare(Point p1, Point p2){
                return p1.cnt - p2.cnt;
            }
        });
        queue.offer(new Point(startX, startY, 0));
        dist[startY][startX] = 0;
        while(!queue.isEmpty()){
            Point p = queue.poll();
            int x = p.x;
            int y = p.y;
            int cnt = p.cnt;
            
            if(x == endX && y == endY){
                answer = Math.min(answer, cnt);
                break;
            }
            
            for(int dir = 0; dir < 4; dir++){
                int nx = x + dx[dir];
                int ny = y + dy[dir];
                        
                if(nx < 0 || ny < 0 || nx >= m || ny >= n){
                    continue;
                }
                        
                int nextCnt = Math.max(cnt, time[ny][nx]);
                if(dist[ny][nx] > nextCnt){
                    dist[ny][nx] = nextCnt;
                    queue.offer(new Point(nx, ny, nextCnt));
                }
            }
        }

        System.out.println(answer);
    }
    
    public static class Point{
        int x;
        int y;
        int cnt;
        
        public Point(int x, int y){
            this.x = x;
            this.y = y;
        }
        
        public Point(int x, int y, int cnt){
            this.x = x;
            this.y = y;
            this.cnt = cnt;
        }
        
        @Override
        public String toString(){
            return "{x=" + x + ", y=" + y + "}";
        }
    }
    
}