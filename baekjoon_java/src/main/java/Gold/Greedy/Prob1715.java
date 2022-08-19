package Gold.Greedy;

import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * Gold 4
 *
 * 문제
 * 정렬된 두 묶음의 숫자 카드가 있다고 하자. 각 묶음의 카드의 수를 A, B라 하면 보통 두 묶음을 합쳐서 하나로 만드는 데에는 A+B 번의 비교를 해야 한다.
 * 이를테면, 20장의 숫자 카드 묶음과 30장의 숫자 카드 묶음을 합치려면 50번의 비교가 필요하다.
 *
 * 매우 많은 숫자 카드 묶음이 책상 위에 놓여 있다. 이들을 두 묶음씩 골라 서로 합쳐나간다면, 고르는 순서에 따라서 비교 횟수가 매우 달라진다.
 * 예를 들어 10장, 20장, 40장의 묶음이 있다면 10장과 20장을 합친 뒤, 합친 30장 묶음과 40장을 합친다면 (10 + 20) + (30 + 40) = 100번의 비교가 필요하다.
 * 그러나 10장과 40장을 합친 뒤, 합친 50장 묶음과 20장을 합친다면 (10 + 40) + (50 + 20) = 120 번의 비교가 필요하므로 덜 효율적인 방법이다.
 *
 * N개의 숫자 카드 묶음의 각각의 크기가 주어질 때, 최소한 몇 번의 비교가 필요한지를 구하는 프로그램을 작성하시오.
 *
 * 입력
 * 첫째 줄에 N이 주어진다. (1 ≤ N ≤ 100,000) 이어서 N개의 줄에 걸쳐 숫자 카드 묶음의 각각의 크기가 주어진다. 숫자 카드 묶음의 크기는 1,000보다 작거나 같은 양의 정수이다.
 *
 * 출력
 * 첫째 줄에 최소 비교 횟수를 출력한다.
 *
 * Solution:
 * Min Heap + Greedy
 * 카드 두 묶음을 묶을때 크기가 가장 작은 카드들끼리 비교하고 묶어야 가장 적은 횟수의 비교로 묶을 수 있음 => Greedy
 * 크기가 가장 작은 카드들을 쉽게 탐색 => Min Heap
 * !! 이때, 두개의 카드를 묶으면 하나의 묶음이 됨 -> 이렇게 묶인 카드들도 계속 다른 카드들과 비교하고 묶을 수 있는 묶음 -> Min Heap에 다시 추가 !!
 *
 * Ex) 10 50 20 40 카드가 주어 졌을때:
 * Min Heap: 10 20 40 50 -> 10 + 20 = 30
 * Min Heap: 30 40 50 -> 30 + 40 = 70
 * Min Heap: 50 70 -> 50 + 70 = 120
 *
 * Answer = 30 + 70 + 120 = 220
 *
 */
public class Prob1715 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for (int i = 0; i < n; i++) {
            queue.add(sc.nextInt());
        }

        int ans = 0;
        while (queue.size() > 1) {
            int firstInt = queue.poll();
            int secondInt = queue.poll();
            int nextInt = firstInt + secondInt;
            ans += nextInt;
            queue.add(nextInt);
        }

        System.out.println(ans);
    }
}
