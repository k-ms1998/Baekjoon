package Review.Gold.Basic;

import java.io.*;
import java.util.*;

/**
 * Gold 4(이중 우선순위 큐(
 *
 * https://www.acmicpc.net/problem/7662
 *
 * Solution: 우선순위 큐
 * 1. MaxHeap, MinHeap 총 2개의 우선순위 큐 선언
 * 2. 입력을 받을 때마다 각각 힙에 추가. 힙에 추가할때마다, 해당 숫자가 나타난 횟수 업데이트
 * 3. 힙에서 최솟값 또는 최댓값을 삭제할때, 힙에서 숫자를 pop하고, 만약 해당 숫자의 남은 갯수가 0보다 크면, 남은 갯수를 1 감소
 * 4. 만약에 남은 갯수가 0개이면, 더 이상 해당 숫자는 큐에 없는 숫자이므로, 큐에서 pop을 한번 더 진행
 * 5. pop해서 나온 숫자의 남은 갯수가 0보다 클때까지 숫자를 pop
 *
 */
public class Prob7662 {

    static StringBuilder ans = new StringBuilder();

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int t = Integer.parseInt(br.readLine());
        while(t-- > 0){
            PriorityQueue<Integer> minHeap = new PriorityQueue<>();
            PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());
            Map<Integer, Integer> map = new HashMap<>();

            int k = Integer.parseInt(br.readLine());
            for(int i = 0; i < k; i++){
                st = new StringTokenizer(br.readLine());
                String command = st.nextToken();
                int n = Integer.parseInt(st.nextToken());

                if(command.equals("I")){
                    minHeap.offer(n);
                    maxHeap.offer(n);
                    if(map.containsKey(n)){
                        int curCnt = map.get(n);
                        map.put(n, curCnt + 1);
                    }else{
                        map.put(n, 1);
                    }
                }else{
                    if(n == 1){
                        if(!maxHeap.isEmpty()){
                            while(true){
                                if(maxHeap.isEmpty()){
                                    break;
                                }
                                int cur = maxHeap.poll();
                                int curCnt = map.get(cur);
                                if(curCnt > 0){
                                    map.put(cur, curCnt - 1);
                                    break;
                                }
                            }
                        }
                    }else{
                        if(!minHeap.isEmpty()){
                            while(true){
                                if(minHeap.isEmpty()){
                                    break;
                                }
                                int cur = minHeap.poll();
                                int curCnt = map.get(cur);
                                if(curCnt > 0){
                                    map.put(cur, curCnt - 1);
                                    break;
                                }
                            }
                        }
                    }
                }

            }

            boolean queueIsEmpty = false;
            if(minHeap.isEmpty() || maxHeap.isEmpty()){
                ans.append("EMPTY").append("\n");
            }else{
                /**
                 * 마지막에 정답을 출력할때도 각 힙에 저장된 값들을 확인해봐야함
                 * 각 힙에 숫자는 저장되어 있어도, 해당 숫자가 남아 있는 갯수가 0이면 사실은 해당 숫자는 힙에 있으면 안되는 숫자
                 * 만약 힙에 있는 모든 숫자들이 남아 있는 갯수가 0개이면 해당 힙은 비어있는 힙
                 */
                int maxNum = 0;
                int minNum = 0;
                while(true){
                    if(maxHeap.isEmpty()){
                        queueIsEmpty = true;
                        break;
                    }
                    int cur = maxHeap.poll();
                    int curCnt = map.get(cur);
                    if(curCnt > 0){
                        maxNum = cur;
                        break;
                    }
                }
                while(true){
                    if(minHeap.isEmpty()){
                        queueIsEmpty = true;
                        break;
                    }
                    int cur = minHeap.poll();
                    int curCnt = map.get(cur);
                    if(curCnt > 0){
                        minNum = cur;
                        break;
                    }
                }

                if(queueIsEmpty){
                    ans.append("EMPTY").append("\n");
                }else{
                    ans.append(maxNum).append(" ").append(minNum).append("\n");
                }

            }
        }

        System.out.println(ans);
    }
}