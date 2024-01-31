package Gold.BruteForce;

import java.io.*;
import java.util.*;

/**
 * 비숍은 대각선에 있는 말들을 잡을 수 있다
 * 비숍을 놓을 수 없는 위치 존재 && 비숍이 서로 잡지 못하도록 배치
 * 이때 놓을 수 있는 비숏 최대 개수 구하기
 *
 * 체스판의 크기 <= 10
 *
 * 풀이:
 * 0. 체스판이 다음과 같이 있을때(0=검은칸, 1 = 흰색칸):
 *  0 1 0 1 0
 *  1 0 1 0 1
 *  0 1 0 1 0
 *  1 0 1 0 1
 *  0 1 0 1 0
 * => 검은칸에 비숏을 놓아도 흰색칸에는 도달 못함 & vice versa
 *  => 그러므로 조합론에서 총 확인해야하는 조합의 수를 줄이기 위해 비숍을 놓을 수 있는 칸이 검은칸인지 흰색칸인지 구별
 *  => 구별 후, 각각 검은칸과 흰색칸에 최대 몇개씩 놓을 수 있는지 확인하고 두 개의 값을 더한다
 *  => 이렇게하면 최대 100C50이 아니라 2* 50C25가 되기 때문에 확인해야하는 조합의 수가 급격히 줄어듬(+ 100C50 시도시 메모리 초과)
 * 1. 이분탐색을 이용해 체스판에 놓을 비숍의 수를 정하기
 * 2. 조합론을 통해 놓을 수 있는 위치 중에서 정한 수 만큼 놓기 시도
 *  2-1. 각 비숏을 놓을때마다 다른 비숍들에게 안잡히는지 확인 후 놓기 => 나중에 한번에 확인 시도시 메모리 초과 발생
 */
public class Prob1799 {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    static int boardSize;
    static int[][] board;
    static List<Point> placeableA = new ArrayList<>();
    static List<Point> placeableB = new ArrayList<>();
    static Point[] bishops;
    static int selectCount;
    static int maxBishopCount = 0;

    public static void main(String args[]) throws IOException{
        boardSize = Integer.parseInt(br.readLine().trim());
        board = new int[boardSize][boardSize];

        for(int row = 0; row < boardSize; row++){
            st = new StringTokenizer(br.readLine().trim());
            for(int col = 0; col < boardSize; col++){
                board[row][col] = Integer.parseInt(st.nextToken());
                if(board[row][col] == 1){
                    maxBishopCount++;
                    if(row % 2 == col % 2){
                        placeableA.add(new Point(row, col));
                    }else{
                        placeableB.add(new Point(row, col));
                    }
                }
            }
        }

        int aMax = 0;
        int left = 0;
        int right = placeableA.size();
        while(left <= right){
            int mid = (left + right) / 2;
            bishops = new Point[mid];
            selectCount = mid;
            if(placeBishops(mid, placeableA.size(), placeableA)){
                aMax = Math.max(aMax, mid);
                left = mid + 1;
            }else{
                right = mid - 1;
            }
        }

        int bMax = 0;
        left = 0;
        right = placeableB.size();
        while(left <= right){
            int mid = (left + right) / 2;
            bishops = new Point[mid];
            selectCount = mid;
            if(placeBishops(mid, placeableB.size(), placeableB)){
                bMax = Math.max(bMax, mid);
                left = mid + 1;
            }else{
                right = mid - 1;
            }
        }

        System.out.println(aMax + bMax);
    }

    public static boolean placeBishops(int numberOfBishops, int targetElementIdx, List<Point> bishopList){
        return findCombination(0, 0, targetElementIdx, bishopList);
    }

    public static boolean findCombination(int selectIdx, int elementIdx, int targetElementIdx, List<Point> bishopList){
        //기저 조건
        if(selectIdx == selectCount){
            return true;
        }
        if(elementIdx == targetElementIdx){
            return false;
        }

        // 전처리 + 재귀 호출 1
        boolean flag = true;
        Point curPoint = bishopList.get(elementIdx);
        for(int bishopA = 0; bishopA < selectIdx; bishopA++){
            if(!checkPlaceable(bishops[bishopA], curPoint)){
                flag = false;
                break;
            }
        }
        if(flag){
            bishops[selectIdx] = curPoint;
            if(findCombination(selectIdx + 1, elementIdx + 1, targetElementIdx, bishopList)){
                return true;
            }
        }

        // 전처리 + 재귀 호출 2
        bishops[selectIdx] = new Point(-1, -1);
        if(findCombination(selectIdx, elementIdx + 1, targetElementIdx, bishopList)){
            return true;
        }

        return false;
    }

    public static boolean checkPlaceable(Point pA, Point pB){
        int aCol = pA.col;
        int aRow = pA.row;
        int bCol = pB.col;
        int bRow = pB.row;

        int colDiff = Math.abs(aCol - bCol);
        int rowDiff = Math.abs(aRow - bRow);

        return colDiff != rowDiff;
    }

    public static void printBishops(){
        int[][] grid = new int[boardSize][boardSize];
        for(int bIdx = 0; bIdx < selectCount; bIdx++){
            Point p = bishops[bIdx];
            grid[p.row][p.col] = 1;
        }

        StringBuilder tmpSb = new StringBuilder();
        for(int row = 0; row < boardSize; row++){
            for(int col = 0; col < boardSize; col++){
                tmpSb.append(grid[row][col]).append(" ");
            }
            tmpSb.append("\n");
        }
        tmpSb.append("----------");

        System.out.println(tmpSb);
    }

    public static class Point{
        int row;
        int col;

        public Point(int row, int col){
            this.row = row;
            this.col = col;
        }
    }
}