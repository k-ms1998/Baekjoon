package Review.Gold;

import java.util.*;

/**
 * Greedy:
 *
 * 1. 한번에 두 개의 책을 옮길 수 있어서, 좌표 x, yㅇ에 책들을 둘려고 할때
 *  -> if x > y (x > 0 && y > 0) || x < y (x < 0 && y < 0)
 *         -> x로 가는 길에 y 가 있기 때문에 y까지 걸리는 거리는 무시
 *          -> x랑 y에 책을 갖다 놓을때 걸리는 거리 == x
 * 2. 마지막을 제외하고, 나머지 경우들에서는 책을 갖다 놓고 다시 원점으로 돌아오기 때문에 가장 멀리갈 경우를 마지막 순서로 둠 => Greedy
 *
 */
public class Prob1461 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int m = sc.nextInt();

        List<Integer> books = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            books.add(sc.nextInt());
        }
        Collections.sort(books);

        PriorityQueue<Integer> dist = new PriorityQueue<>();

        for (int i = 0; i < n; i++) {
            int flag = 0;
            List<Integer> inHand = new ArrayList<>();
            for (int j = 0; j < m; j++) {
                if (i + j >= n) {
                    flag = 1;
                    break;
                }

                if (books.get(i+j) < 0) {
                    inHand.add(books.get(i+j));
                } else {
                    flag = 1;
                    break;
                }
            }

            if (inHand.size() > 0) {
                i += inHand.size() - 1;
                dist.add(-inHand.get(0));
            }
            if (flag != 0) {
                break;
            }
        }

        for (int i = n - 1; i >= 0; i--) {
            int flag = 0;
            List<Integer> inHand = new ArrayList<>();
            for (int j = 0; j < m; j++) {
                if (i-j < 0) {
                    flag = 1;
                    break;
                }

                if (books.get(i-j) > 0) {
                    inHand.add(books.get(i-j));
                } else {
                    flag = 1;
                    break;
                }
            }

            if (inHand.size() > 0) {
                i -= (inHand.size() - 1);
                dist.add(inHand.get(0));
            }
            if (flag != 0) {
                break;
            }
        }

        int ans = 0;
        int distSize = dist.size();
        for (int i = 0; i < distSize - 1; i++) {
            ans += 2*dist.poll();
        }
        ans += dist.poll();

        System.out.println(ans);
    }
}
