package Gold.Greedy;

import java.io.*;
import java.util.*;
import java.util.Map.*;

/**
 * Gold 5
 *
 * 문제
 * 세준이는 도서관에서 일한다. 도서관의 개방시간이 끝나서 세준이는 사람들이 마구 놓은 책을 다시 가져다 놓아야 한다.
 * 세준이는 현재 0에 있고, 사람들이 마구 놓은 책도 전부 0에 있다.
 * 각 책들의 원래 위치가 주어질 때, 책을 모두 제자리에 놔둘 때 드는 최소 걸음 수를 계산하는 프로그램을 작성하시오.
 * 세준이는 한 걸음에 좌표 1칸씩 가며, 책의 원래 위치는 정수 좌표이다. 책을 모두 제자리에 놔둔 후에는 다시 0으로 돌아올 필요는 없다.
 * 그리고 세준이는 한 번에 최대 M권의 책을 들 수 있다.
 *
 * 입력
 * 첫째 줄에 책의 개수 N과, 세준이가 한 번에 들 수 있는 책의 개수 M이 주어진다.
 * 둘째 줄에는 책의 위치가 주어진다. N과 M은 50보다 작거나 같은 자연수이다. 책의 위치는 0이 아니며, 절댓값은 10,000보다 작거나 같은 정수이다.
 *
 * 출력
 * 첫째 줄에 정답을 출력한다.
 */
public class Prob1461 {

    public void solve() {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int m = sc.nextInt();
        int part = n / m;

        List<Integer> books = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            books.add(sc.nextInt());
        }

        /**
         * 1. 책들을 오름 차순으로 정렬
         * [-37, 2, -6, -39, -29, 11, -28] -> [-39, -37, -29, -28, -6, 2, 11]
         * 2. 음수 위치에 있는 책들과 양수 위치에 있는 책들을 각각 M개씩 묶기
         * -> (-39, -37), (-29, -28), (-6), (2, 11)
         * -> 답 == 2*(39 + 29 + 6 + 11) - 39 = 170 - 39 = 131
         * -> 묶은 수들 중, 절대 값이 가장 큰 값만큼만 왔다 갔다 하고, 마지막 책을 갖다 놓고 다시 0으로 올 필요는 없기 때문에
         * -> ex: (-39, -37)에서 어차피 -37은 -39로 가는 길에 있기 때문에 -39까지 갔다가 다시 0으로 오는 거리만 생각하면 됨 -> |-39| * 2
         * -> 움직인 거리를 최소 값을 구하기 귀해서는 결국 책을 갖다 놓고 제자리로 오는 거리도 최소화되어야 하기 때문에, 절대 값이 가장 큰 값을 마지막에 가도록 해야 합니다
         */
        
        // Books 정렬
        Collections.sort(books);

        int ans = 0;
        int lastBook = 0;
        while (!books.isEmpty()) {
            // 책의 위치가 음수이면, 절댓값이 가장 큰 값은 0번째 인덱스이고, 책의 위치가 양수이면 절댓값이 가장 큰 값은 size-1번째 인덱스에 위치
            int removeIdx = books.get(0) < 0 ? 0 : books.size() - 1;
            int book = books.remove(removeIdx);
            int absBook = Math.abs(book);
            int addBook = absBook;
            
            for (int i = 1; i < m; i++) {
                if (books.isEmpty()) {
                    break;
                }

                Integer nextBook = books.get(0);
                if ((book < 0 && nextBook > 0) || (book < 0 && nextBook > 0)) {
                    break;
                }
                /**
                 * 책의 위치가 음수이면, 절댓값이 가장 큰 값은 0번째 인덱스이고, 책의 위치가 양수이면 절댓값이 가장 큰 값은 size-1번째 인덱스에 위치
                 * 책의 위치가 음수이면 위치의 절댓값이 가장 큰 순서대로 묵어야 이동거리가 최소가되므로, 앞에서부터 m개씩 묵어야 합니다
                 * 책의 위치가 양수이면 위치의 값이 가장 큰 순서대로 묶어야 이동거리가 최소가되므로, 뒤에서부터 m개씩 묶어야 합니다
                 */

                removeIdx = books.get(0) < 0 ? 0 : books.size() - 1;
                nextBook = books.remove(removeIdx);
                if (addBook < Math.abs(nextBook)) {
                    addBook = Math.abs(nextBook);
                }
            }
            ans += addBook;
            lastBook = lastBook > addBook ? lastBook : addBook;
        }
        ans = 2 * ans - lastBook;

        System.out.println(ans);
    }
}
