package Gold.Basic;

import java.io.*;
import java.util.*;

/**
 * Gold 4(이중 우선순위 큐)
 *
 * https://www.acmicpc.net/problem/7662
 *
 * Solution:
 */
public class Gold7662 {

    static PriorityQueue<Long> maxHeap;
    static PriorityQueue<Long> minHeap;
    static Map<Long, Integer> numCnt;

    static StringBuilder ans = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int t = Integer.parseInt(br.readLine());

        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine());

            maxHeap = new PriorityQueue<>(Collections.reverseOrder());
            minHeap = new PriorityQueue<>();
            numCnt = new HashMap<>();

            for (int i = 0; i < n; i++) {
                st = new StringTokenizer(br.readLine());
                String command = st.nextToken();
                if (command.equals("I")) {
                    long num = Long.parseLong(st.nextToken());

                    maxHeap.offer(num);
                    minHeap.offer(num);

                    if (numCnt.containsKey(num)) {
                        int cnt = numCnt.get(num);
                        numCnt.put(num, cnt + 1);
                    } else {
                        numCnt.put(num, 1);
                    }
                } else {
                    int num = Integer.parseInt(st.nextToken());
                    if (num == 1) {
                        removeMax();

                    } else {
                        removeMin();
                    }
                }
            }

            boolean isEmpty = true;
            long ansMax = Long.MIN_VALUE;
            long ansMin = Long.MAX_VALUE;

            while (!maxHeap.isEmpty()) {
                long tmp = maxHeap.poll();
                int tmpCnt = numCnt.get(tmp);
                if (tmpCnt == 0) {
                    continue;
                }

                if(isEmpty){
                    isEmpty = false;
                }
                ansMax = Math.max(ansMax, tmp);
                ansMin = Math.min(ansMin, tmp);
            }
            while (!minHeap.isEmpty()) {
                long tmp = minHeap.poll();
                int tmpCnt = numCnt.get(tmp);
                if (tmpCnt == 0) {
                    continue;
                }

                if (isEmpty) {
                    isEmpty = false;
                }
                ansMax = Math.max(ansMax, tmp);
                ansMin = Math.min(ansMin, tmp);
            }

            if (isEmpty) {
                ans.append("EMPTY").append("\n");
            } else {
                ans.append(ansMax).append(" ").append(ansMin).append("\n");
            }

        }

        System.out.println(ans);
    }

    public static long removeMax(){
        long curMax = Long.MIN_VALUE;

        while (!maxHeap.isEmpty()) {
            long tmp = maxHeap.peek();
            int tmpCnt = numCnt.get(tmp);
            if (tmpCnt > 0) {
                if (tmpCnt - 1 == 0) {
                    maxHeap.poll();
                }
                numCnt.put(tmp, tmpCnt - 1);
                curMax = tmp;
                break;
            }

            maxHeap.poll();
        }

        return curMax;
    }

    public static long removeMin() {
        long curMin = Long.MAX_VALUE;

        while (!minHeap.isEmpty()) {
            long tmp = minHeap.peek();
            int tmpCnt = numCnt.get(tmp);
            if (tmpCnt > 0) {
                if (tmpCnt - 1 == 0) {
                    minHeap.poll();
                }
                numCnt.put(tmp, tmpCnt - 1);
                curMin = tmp;
                break;
            }

            minHeap.poll();
        }

        return curMin;
    }

}
