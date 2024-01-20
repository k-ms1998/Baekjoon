package Gold;

import java.io.*;
import java.util.*;

/**
 * 1. 크기가 3*3인 배열(인덱스는 1부터 시작) -> 1초가 지날때마다 배열에 연산 적용
 *  1-1. r 연산 -> 모든 행에 대해서 정렬을 수행. 행의 개수 >= 열의 개수 인 경우
 *  1-2. c 연산 -> 모든 열에 대해서 정렬을 수행. 행의 개수 <= 열의 개수 인 경우
 * 2. 한 행 또는 열에 있는 수를 정렬하려면 각가의 수가 몇 번 나왔는지 알아야 한다.
 *  2-1. 그 다음, 수의 등장 횟수가 커지는 순으로 , 그러한 것이 여러가지면 수가 커지는 순으로 정렬한다
 *  2-2. 그 다음, 배열에 정렬된 결과를 다시 넣어야 한다. 정렬된 결과를 배열에 넣을 때는, 수와 등장 횟수를 모두 넣으면, 순서는 수가 먼저이다
 *
 * ex:
 * [3, 1, 1]에는 3이 1번, 1이 2번 등장한다 -> 정렬한 결과 = [3, 1, 1, 2]
 * 다시 [3, 1, 1, 2]를 정렬해야 하는데, 3이 1번, 1이 2번, 2가 1번이므로 -> 정렬한 결과 = [2, 1, 3, 1, 1, 2]
 */
public class Prob17140 {

    static int r, c ,k;
    static int[][] arr;
    static int rowSize = 3;
    static int colSize = 3;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        arr = new int[3][3];
        for(int y = 0; y < 3; y++){
            st = new StringTokenizer(br.readLine());
            for(int x = 0; x < 3; x++){
                arr[y][x] = Integer.parseInt(st.nextToken());
            }
        }

        int answer = 0;
        while(answer <= 100){
//            printArr();
            if((rowSize >= r && colSize >= c) && arr[r-1][c-1] == k){ // 인덱스는 1부터 시작하기 때문에
                break;
            }
            answer++;

            if(rowSize >= colSize){
                executeR();
            }else{
                executeC();
            }
        }

        System.out.println(answer > 100 ? -1 : answer);
    }

    public static void executeR(){
        int[][] tmp = new int[101][101];
        int maxC = 0;
        for(int y = 0; y < rowSize; y++){
            int[] cnt = new int[101]; // 배열에 arr에 들어있는 수는 모두 100이하인 자연수
            for(int x = 0; x < colSize; x++){
                int curNum = arr[y][x];
                if(curNum == 0){
                    continue;
                }
                cnt[curNum]++;
            }
            PriorityQueue<Info> pq = new PriorityQueue<>(new Comparator<Info>(){
                @Override
                public int compare(Info i1, Info i2){
                    if(i1.count == i2.count){
                        return i1.num - i2.num;
                    }

                    return i1.count - i2.count;
                }
            });
            for(int i = 0; i <= 100; i++){
                if(cnt[i] > 0){
                    pq.add(new Info(i, cnt[i]));
                }
            }
            int size = pq.size();
            maxC = Math.max(maxC, 2*size);

            int idx = 0;
            while(!pq.isEmpty()){
                Info info = pq.poll();
                tmp[y][idx] = info.num;
                tmp[y][idx + 1] = info.count;

                idx += 2;
            }
        }

        colSize = Math.min(maxC, 100);
        arr = new int[rowSize][colSize];
        for(int y = 0; y < rowSize; y++){
            for(int x = 0; x < colSize; x++){
                arr[y][x] = tmp[y][x];
            }
        }
    }

    public static void executeC(){
        int[][] tmp = new int[101][101];
        int maxR = 0;
        for(int x = 0; x < colSize; x++){
            int[] cnt = new int[101]; // 배열에 arr에 들어있는 수는 모두 100이하인 자연수
            for(int y = 0; y < rowSize; y++){
                int curNum = arr[y][x];
                if(curNum == 0){
                    continue;
                }
                cnt[curNum]++;
            }
            PriorityQueue<Info> pq = new PriorityQueue<>(new Comparator<Info>(){
                @Override
                public int compare(Info i1, Info i2){
                    if(i1.count == i2.count){
                        return i1.num - i2.num;
                    }

                    return i1.count - i2.count;
                }
            });
            for(int i = 0; i <= 100; i++){
                if(cnt[i] > 0){
                    pq.add(new Info(i, cnt[i]));
                }
            }
            int size = pq.size();
            maxR = Math.max(maxR, 2*size);

            int idx = 0;
            while(!pq.isEmpty()){
                Info info = pq.poll();
                tmp[idx][x] = info.num;
                tmp[idx + 1][x] = info.count;

                idx += 2;
            }
        }

        rowSize = Math.min(maxR, 100);
        arr = new int[rowSize][colSize];
        for(int x = 0; x < colSize; x++){
            for(int y = 0; y < rowSize; y++){
                arr[y][x] = tmp[y][x];
            }
        }
    }

    public static void printArr(){
        for(int y = 0; y < rowSize; y++){
            for(int x = 0; x < colSize; x++){
                System.out.print(arr[y][x] + " ");
            }
            System.out.println();
        }
        System.out.println("----------");
    }

    public static class Info{
        int num;
        int count;

        public Info(int num, int count){
            this.num = num;
            this.count = count;
        }

        @Override
        public String toString() {
            return "Info{" +
                    "num=" + num +
                    ", count=" + count +
                    '}';
        }
    }
}
