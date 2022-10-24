package Gold.Basic;

import java.io.*;
import java.util.*;

/**
 * Gold 2(가운데를 말해요)
 *
 * https://www.acmicpc.net/problem/1655
 *
 * Solution: PQ
 * 1. 두개의 우선순위 큐를 이용해서 가운데를 바로 찾을 수 있게 함
 *  1-1. Min Heap 과 Max Heap 각각 하나씩 사용해서 가운데 값 찾기
 * 2. Min Heap, Max Heap 의 크기가 같도록 유지시킴
 *  2-1. 두 개의 크기가 같으면 Max Heap 에 값을 넣고, Max Heap 의 크기가 더 커지면 Min Heap 에 값을 추가
 *  2-2. 값을 추가 후, Max Heap 의 root 값이 Min Heap 의 root 값보다 작으면 두 개의 값을 swap 해준다
 *  2-3. 이렇게 해서, Max Heap 의 root 값이 가운데 값이 유지됨
 * => ex: 입력 값 = {1, 2, 5, 10, -99, 7, 5}
 * 이때, Max 의 root 값은 가장 마지막 인덱스, Min 의 root 값은 가장 첫번째 인덱스
 * Max = {1}, Min = {} -> mid = 1
 * Max = {1}, Min = {2} -> mid = 1
 * Max = {1, 5}, Min = {2} -> Swap -> Max = {1, 2}, Min = {5} -> mid = 2
 * Max = {1, 2}, Min = {5, 10} -> mid = 2;
 * Max = {-99, 1, 2}, Min = {5, 10} -> mid = 2
 * Max = {-99, 1, 2}, Min = {5, 7, 10} -> mid = 2
 * Max = {-99, 1, 2, 5}, Min = {5, 7, 10} -> mid = 5
 *  => ans = 1, 1, 2, 2, 2, 2, 5
 *  => 즉, 현재까지 입력 받은 값을 포함해서 좌우(Max Heap, Min Heap)로 나누게 되고,
 *  두 힙의 크기가 같거나 Max Heap 이 하나 크기 때문에 가운데 값은 항상 Max Heap 의 root 값이 됨
 */
public class Prob1655 {

    static StringBuilder ans = new StringBuilder();
    static PriorityQueue<Integer> lQueue = new PriorityQueue<>(Collections.reverseOrder());
    static PriorityQueue<Integer> rQueue = new PriorityQueue<>();

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());

        int num = Integer.parseInt(br.readLine());
        lQueue.offer(num);
        ans.append(num).append("\n");
        for(int i = 1; i < n; i++){
            num = Integer.parseInt(br.readLine());
            if (lQueue.size() <= rQueue.size()) {
                lQueue.offer(num);
            }else{
                rQueue.offer(num);
            }
            ans.append(findMid()).append("\n");
        }

        System.out.println(ans);
    }

    public static int findMid() {
        if(lQueue.peek() > rQueue.peek()){
            int tmpL = lQueue.poll();
            int tmpR = rQueue.poll();
            lQueue.offer(tmpR);
            rQueue.offer(tmpL);
        }
        return lQueue.peek();
    }
}