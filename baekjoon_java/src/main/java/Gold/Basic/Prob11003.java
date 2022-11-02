package Gold.Basic;

import java.io.*;
import java.util.*;

/**
 * Gold 1(최솟값 찾기)
 *
 * https://www.acmicpc.net/problem/11003
 *
 * Solution: PriorityQueue
 * (1 <= n, l <= 5,000,000 이므로, O(n) 에 풀어야함)
 */
public class Prob11003 {
    static int n, l;

    static PriorityQueue<Node> pq;

    static StringBuilder ans = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        l = Integer.parseInt(st.nextToken());

        pq = new PriorityQueue<>(new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return o1.value - o2.value;
            }
        });
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i < n + 1; i++) {
            int num = Integer.parseInt(st.nextToken());

            /**
             * 현재 pq에 저장된 최솟값이 현재 값인 num 보다 크거나 같으면 pq에 저장된 값들은 모두 필요 없는 값들이 되기 때문에 clear 해줌
             * clear 해줌으로써 pq를 탐색해야될때 탐색해야되는 Node 의 갯수를 최소화 할 수 있음
             * => 시간 초과가 발생하는 문제를 피할 수 있음
             */
            if(!pq.isEmpty()){
                if (num <= pq.peek().value) {
                    pq.clear();
                }
            }
            pq.offer(new Node(i, num));
            /**
             * idx 의 값이 범위 밖에 있는 Node 들을 삭제해주기
             */
            while(!pq.isEmpty()){
                if (pq.peek().idx >= i - l + 1) {
                    break;
                }
                pq.poll();
            }
            ans.append(pq.peek().value).append(" ");
        }


        System.out.println(ans);
    }

    public static class Node{
        int idx;
        int value;

        public Node(int idx, int value) {
            this.idx = idx;
            this.value = value;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "idx=" + idx +
                    ", value=" + value +
                    '}';
        }
    }

}
