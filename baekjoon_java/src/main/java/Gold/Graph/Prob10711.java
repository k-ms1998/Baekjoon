package Gold.Graph;

import java.io.*;
import java.util.*;

/**
 * 1. 세로(h), 가로(w) 입력 (1 <= h, w, <= 1_000)
 * 2. 현재 모래성 상태 입력
 *  2-1. 모래성이 쌓인 부분에서 8방향을 탐색해서 모래성이 몇 개 쌓여있는지 확인
 *      2-1-1. 현재 좌표의 튼튼함보다 8방향으로 모래성이 쌓여있지 않는 개수가 같거나 더 크면, 현재 성은 무너짐
 *  2-2. 진행하면서, 현재 좌표의 성이 몇 번째때 무너지는지 저장(time)
 *
 */
public class Prob10711 {

    static int h, w;
    static int[][] sandCastle;
    static int[][] counter;

    static int[] dx = {0, 1, 1, 1, 0, -1, -1, -1};
    static int[] dy = {-1, -1, 0, 1, 1, 1, 0, -1};

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        h = Integer.parseInt(st.nextToken());
        w = Integer.parseInt(st.nextToken());

        Deque<Point> queue = new ArrayDeque<>();
        sandCastle = new int[h][w];
        counter = new int[h][w];
        for(int y = 0; y < h; y++){
            String row = br.readLine().trim();
            for(int x = 0; x < w; x++){
                char c = row.charAt(x);
                if(c == '.'){
                    sandCastle[y][x] = 0;
                    queue.offer(new Point(x, y));
                }else{
                    sandCastle[y][x] = c - '0';
                }
            }
        }

        int curTime = 0;
        while(!queue.isEmpty()){
            curTime++;

            int size = queue.size();
            for(int i = 0; i < size; i++){
                Point p = queue.poll();
                int x = p.x;
                int y = p.y;

                for(int dir = 0; dir < 8; dir++){
                    int nx = x + dx[dir];
                    int ny = y + dy[dir];

                    if(nx < 0 || ny < 0 || nx >= w || ny >= h){
                        continue;
                    }

                    counter[ny][nx]++;
                    if(counter[ny][nx] >= sandCastle[ny][nx] && sandCastle[ny][nx] > 0){
                        sandCastle[ny][nx] = 0;
                        queue.offer(new Point(nx, ny));
                    }
                }
            }
        }

        System.out.println(curTime - 1);
    }

    public static class Point{
        int x;
        int y;

        public Point(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    public static void printSandCastle(){
        StringBuilder output = new StringBuilder();
        for(int y = 0; y < h; y++){
            for(int x = 0; x < w; x++){
                output.append(sandCastle[y][x]).append(" ");
            }
            output.append("\n");
        }
        output.append("----------\n");

        System.out.println(output);
    }

}