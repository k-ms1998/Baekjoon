package Gold.Basic;

import java.io.*;
import java.util.*;

/**
 * Gold 2(중앙값 구하기)
 *
 * https://www.acmicpc.net/problem/2696
 *
 * Solution: PriorityQueue(Min Heap + Max Heap)
 * 두 개의 우선순위 큐를 이용해서 바로 중앙값을 알 수 있도록 풀이
 * Min Heap, Max Heap 각각 하나씩 이용해서, Min Heap 의 첫번째 값이 항상 중앙값이 되도록 유지
 */
public class Prob2696 {

    static PriorityQueue<Integer> left;
    static PriorityQueue<Integer> right;
    static StringBuilder ans = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int t = Integer.parseInt(br.readLine());
        while(t-- > 0){
            int n = Integer.parseInt(br.readLine());
            int lines = n / 10;
            int remainder = n % 10;
            if (remainder == 0) {
                lines--;
            }

            int cnt = 1;
            left = new PriorityQueue<>(Collections.reverseOrder());
            right = new PriorityQueue<>();

            ans.append((n + 1) / 2).append("\n");
            for (int i = 0; i < lines; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < 10; j++) {
                    int num = Integer.parseInt(st.nextToken());

                    insertQueue(num);
                    sortQueue();
                    getMedian(cnt);

                    if (cnt % 20 == 0) {
                        ans.append("\n");
                    }
                    ++cnt;
                }
            }
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < remainder; i++) {
                int num = Integer.parseInt(st.nextToken());

                insertQueue(num);
                sortQueue();
                getMedian(cnt);

                ++cnt;
            }
            ans.append("\n");
        }

        System.out.println(ans);
    }

    /**
     * 우선순위 큐에 값을 넣기
     * 최소힙과 최대힙에 저장된 숫자의 갯수는 항상 같아야하기 때문에 두 개의 사이즈를 비교해서 값을 추가
     */
    private static void insertQueue(int num) {
        if (left.size() <= right.size()) {
            left.offer(num);
        }else{
            right.offer(num);
        }
    }

    /**
     * left 에 저장된 값들은 항상 right 에 저장된 값들보다 작게 유지해야됨
     * 그래야, left = {1, 2, 3}, right = {4, 5} 이런석으로 유지가 됨
     * left 에 저장된 값들이 right 에 저장된 값들보다 작게 유지되기 위해서는 left 의 최댓값이 right 의 최솟값보다 작게 유지되어야 함
     */
    private static void sortQueue() {
        if(!left.isEmpty() && !right.isEmpty()){
            while (left.peek() > right.peek()) {
                int firstLeft = left.poll();
                int firstRight = right.poll();
                right.offer(firstLeft);
                left.offer(firstRight);
            }
        }
    }

    /**
     * 중앙 값은 항상 left 의 최댓값임
     * 입력 받은 수들이 1, 5, 4, 3, 2 이면:
     * left = 3     right = 4
     *        2             5
     *        1
     * (맨 위에 있는 숫자가 각 우선순위 큐의 첫번째 원소)
     */
    private static void getMedian(int cnt) {
        if(cnt % 2 == 1){
            ans.append(left.peek()).append(" ");
        }
    }

}
