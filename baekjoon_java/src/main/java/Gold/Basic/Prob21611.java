package Gold.Basic;

import java.io.*;
import java.util.*;

/**
 * 1. NxN 격자(N은 항상 홀수)
 * 2. (행, 열)로 좌표가 주어진
 * 3. (1,1)~(N,N)까지 있고, 마법사 상어는 ((N+1)/2, (N+1)/2)에 있다
 * 4. 격자에서 실선이 있고 점선이 있다
 *  4-1. 실선 = 벽
 *  4-2. 점선 != 벽
 * 5. 상어가 위치해 있는 칸 외에 모든 칸에 1, 2, 3 구슬이 있다
 * 6. 마법을 시전하려면 방향과 거리를 정해야 한다
 *  6-1. 방향은 총 4방향 -> 위(1), 아래(2), 왼(3), 오(4)
 *  6-2. 상어는 d방향으로 거리가 s인 모든 칸에 얼음 파편을 던져 그 칸에 있는 구슬을 몯 파괴한다
 *  6-3. 구슬이 파괴되면 빈 칸이 된다
 *  6-4. 벽은 파괴되지 않는다
 *  6-5. 빈 칸이 있으면 옆 칸에 있는 구슬이 해당 칸으로 움직인다
 * 7. 구슬은 폭발한다
 *  7-1. 4개 이상 연속하는 구슬이 있을때 발생한다
 *  7-2. 구슬이 폭발해서 해당 칸들이 빈칸이 되면, 다시 다른 구슬들이 자리를 차지한다
 *  7-2. 더 이상 폭발할 수 있는 구슬이 없을때까지 반복
 * 8. 남은 구슬들로 그룹을 만든다
 *  8-1. 각 그룹은 같은 숫자의 구슬들만 있다
 *  8-2. 각 그룹은 [A, B]의 구슬들로 변한다
 *      8-2-1. A = 해당 그룹에 들어 있는 구슬의 개수
 *      8-2-2. B = 해당 그룹을 이루고 있는 구슬의 번호
 *  8-3. 만약 구슬이 칸의 수 보다 많아 칸에 들어가지 못하는 경우, 그런 구슬들은 사라짐
 *
 *  ----
 *  1. (0,0)부터 차례대로 각 칸에 번호를 부여하기
 *  2. 2차원 격자를 1차원 배열로 관리하기 
 *      2-1. (0,0)의 번호 = 0 -> arr[0]이랑 대응됨
 *  3. 이를 통해 구슬을 움직일때 단순히 1차원 배열에서 해결 가능
 */
public class Prob21611 {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    static int size;
    static int commandSize;

    static int[][] grid;
    static int sCol, sRow;
    static int[] gridArr;
    static int[][] gridNum;

    // 빈칸, 위, 아래, 왼, 오
    static final int[] magicCol = {0, 0, 0, -1, 1};
    static final int[] magicRow = {0, -1, 1, 0, 0};

    // 오, 아래, 왼, 위
    static final int[] dCol = {1, 0, -1, 0};
    static final int[] dRow = {0, 1, 0, -1};

    static int answer = 0;

    public static void main(String[] args) throws IOException{
        st = new StringTokenizer(br.readLine());

        size = Integer.parseInt(st.nextToken());
        commandSize = Integer.parseInt(st.nextToken());

        grid = new int[size][size];
        gridArr = new int[size * size];
        gridNum = new int[size][size];

        sCol = ((size + 1) / 2) - 1;
        sRow = ((size + 1) / 2) - 1;
        for(int row = 0; row < size; row++){
            st = new StringTokenizer(br.readLine());
            for(int col = 0; col < size; col++){
                grid[row][col] = Integer.parseInt(st.nextToken());
            }
        }
        intiDirGird();

        for(int idx = 0; idx < commandSize; idx++){
            st = new StringTokenizer(br.readLine());
            int dir = Integer.parseInt(st.nextToken());
            int dist = Integer.parseInt(st.nextToken());

            // 주어진 방향에 따라 구슬 파괴
            destroy(dir, dist);
            while(true){
                int exploded = findAndRemoveContinuous();
                if(exploded == 0){
                    break;
                }
            }
            moveMarbles();
            changeGrid();
        }

        System.out.println(answer);
    }

    public static void destroy(int dir, int dist){
        for(int delta = 1; delta <= dist; delta++){
            int nCol = sCol + delta * magicCol[dir];
            int nRow = sRow + delta * magicRow[dir];

            if(nCol < 0 || nRow < 0 || nCol >= size || nRow >= size){
                break;
            }

            int idx = gridNum[nRow][nCol];
            gridArr[idx] = 0;
        }
    }

    public static void moveMarbles(){
        for(int idx = gridArr.length - 2; idx >= 0; idx--){
            if(gridArr[idx] == 0){
                for(int nIdx = idx - 1; nIdx >= 0; nIdx--){
                    if(gridArr[nIdx] != 0){
                        gridArr[idx] = gridArr[nIdx];
                        gridArr[nIdx] = 0;
                        break;
                    }
                }
            }
        }
    }

    public static int findAndRemoveContinuous(){
        int explode = 0;

        for(int idx = 0; idx < gridArr.length; idx++){
            int marble = gridArr[idx];
            if(marble > 0){
                Deque<Integer> queue = new ArrayDeque<>();
                queue.offer(idx);
                for(int nIdx = idx + 1; nIdx < gridArr.length; nIdx++){
                    int nextMarble = gridArr[nIdx];
                    if(nextMarble == 0){
                        continue;
                    }

                    if(marble == nextMarble){
                        queue.offer(nIdx);
                    }else{
                        idx = nIdx - 1;
                        break;
                    }
                }
                if(queue.size() >= 4){
                    answer += queue.size() * marble;
                    explode++;
                    while(!queue.isEmpty()){
                        int nIdx = queue.poll();
                        gridArr[nIdx] = 0;
                    }
                }
            }
        }

        return explode;
    }

    public static void changeGrid(){
        Deque<Deque<Integer>> totalQueue = new ArrayDeque<>();
        for(int idx = 0; idx < gridArr.length - 1; idx++){
            int marble = gridArr[idx];
            if(marble == 0){
                continue;
            }
            Deque<Integer> queue = new ArrayDeque<>();
            queue.offer(marble);
            for(int nIdx = idx + 1; nIdx < gridArr.length; nIdx++){
                int nextMarble = gridArr[nIdx];
                if(marble == nextMarble){
                    queue.offer(marble);
                }else{
                    idx = nIdx - 1;
                    break;
                }
            }
            totalQueue.offer(queue);
        }

        int nIdx = gridArr.length - 2;
        while(!totalQueue.isEmpty()){
            if(nIdx < 0){
                break;
            }

            Deque<Integer> queue = totalQueue.pollLast();
            int size = queue.size();
            int marble = queue.peek();

            gridArr[nIdx] = size;
            gridArr[nIdx - 1] = marble;
            nIdx -= 2;
        }

    }

    public static void intiDirGird(){
        boolean[][] visited = new boolean[size][size];
        int dir = 0;

        int idx = 0;
        int col = 0;
        int row = 0;
        visited[row][col] = true;
        while(true){
            gridArr[idx] = grid[row][col];
            gridNum[row][col] = idx;
            if(col == sCol && row == sRow){
                break;
            }
            int nCol = col + dCol[dir];
            int nRow = row + dRow[dir];

            if(nCol < 0 || nRow < 0 || nCol >= size || nRow >= size){
                dir = (dir + 1) % 4;
                continue;
            }
            if(visited[nRow][nCol]){
                dir = (dir +  1) % 4;
                continue;
            }

            visited[row][col] = true;
            col = nCol;
            row = nRow;
            idx++;
        }

    }

    public static void printGrid(int[][] arr){
        for(int row = 0; row < size; row++){
            for(int col = 0; col < size; col++){
                System.out.print(arr[row][col] + " ");
            }
            System.out.println();
        }
        System.out.println("-----");
    }
    public static void printGridArr(){
        for(int idx = 0; idx < gridArr.length; idx++){
            System.out.print(gridArr[idx] + " ");
        }
        System.out.println();
    }

}
