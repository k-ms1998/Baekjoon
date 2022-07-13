package Silver;

import java.util.*;

/**
 * 문제
 * 널리 잘 알려진 자료구조 중 최소 힙이 있다. 최소 힙을 이용하여 다음과 같은 연산을 지원하는 프로그램을 작성하시오.
 * 배열에 자연수 x를 넣는다.
 * 배열에서 가장 작은 값을 출력하고, 그 값을 배열에서 제거한다.
 * 프로그램은 처음에 비어있는 배열에서 시작하게 된다.
 *
 * 입력
 * 첫째 줄에 연산의 개수 N(1 ≤ N ≤ 100,000)이 주어진다. 다음 N개의 줄에는 연산에 대한 정보를 나타내는 정수 x가 주어진다.
 * 만약 x가 자연수라면 배열에 x라는 값을 넣는(추가하는) 연산이고, x가 0이라면 배열에서 가장 작은 값을 출력하고 그 값을 배열에서 제거하는 경우이다.
 * x는 231보다 작은 자연수 또는 0이고, 음의 정수는 입력으로 주어지지 않는다.
 *
 * 출력
 * 입력에서 0이 주어진 횟수만큼 답을 출력한다. 만약 배열이 비어 있는 경우인데 가장 작은 값을 출력하라고 한 경우에는 0을 출력하면 된다.
 *
 * Solution:
 * 자바에서 우선순위 큐(PriorityQueue)는 기본이 최소 힙이기 때문에 우선순위 큐에 값들을 저장시키면 해결 가능
 *
 */
public class Prob1927 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StringBuilder ans = new StringBuilder();
        StringBuilder maxAns = new StringBuilder();

        int n = sc.nextInt();

        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        /**
         * 참고: new PriorityQueue<>(Collections.reverseOrder()) -> 최대힙(Max Heap) 구현
         */
//        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());

        for (int i = 0; i < n; i++) {
            int x = sc.nextInt();

            if (x == 0) {
                if (minHeap.isEmpty()) {
                    ans.append(0 + "\n");
//                    maxAns.append(0 + "\n");
                }else {
                    ans.append(minHeap.poll() + "\n");
//                    maxAns.append(maxHeap.poll() + "\n");
                }
            } else {
                minHeap.add(x);
//                maxHeap.add(x);
            }
        }

        System.out.println(ans);
//        System.out.println(maxAns);
    }

}
