package Review.Silver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Silver 1:
 * <p>
 * 문제
 * 해커 김지민은 잘 알려진 어느 회사를 해킹하려고 한다. 이 회사는 N개의 컴퓨터로 이루어져 있다. 김지민은 귀찮기 때문에, 한 번의 해킹으로 여러 개의 컴퓨터를 해킹 할 수 있는 컴퓨터를 해킹하려고 한다.
 * 이 회사의 컴퓨터는 신뢰하는 관계와, 신뢰하지 않는 관계로 이루어져 있는데, A가 B를 신뢰하는 경우에는 B를 해킹하면, A도 해킹할 수 있다는 소리다.
 * 이 회사의 컴퓨터의 신뢰하는 관계가 주어졌을 때, 한 번에 가장 많은 컴퓨터를 해킹할 수 있는 컴퓨터의 번호를 출력하는 프로그램을 작성하시오.
 * <p>
 * 입력
 * 첫째 줄에, N과 M이 들어온다. N은 10,000보다 작거나 같은 자연수, M은 100,000보다 작거나 같은 자연수이다.
 * 둘째 줄부터 M개의 줄에 신뢰하는 관계가 A B와 같은 형식으로 들어오며, "A가 B를 신뢰한다"를 의미한다. 컴퓨터는 1번부터 N번까지 번호가 하나씩 매겨져 있다.
 * <p>
 * 출력
 * 첫째 줄에, 김지민이 한 번에 가장 많은 컴퓨터를 해킹할 수 있는 컴퓨터의 번호를 오름차순으로 출력한다.
 */
public class Review_Prob1325 {

    static public Map<Integer, List<Integer>> edges = new HashMap<>();
    static public StringBuffer result = new StringBuffer();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] conditions = br.readLine().split(" ");
        int n = Integer.valueOf(conditions[0]);
        int m = Integer.valueOf(conditions[1]);

        Integer[] hackable = new Integer[n + 1];
        for (int i = 0; i < n + 1; i++) {
            edges.put(i, new ArrayList<>());
            hackable[i] = 1;
        }
        for (int i = 0; i < m; i++) {
            String[] edge = br.readLine().split(" ");
            int a = Integer.valueOf(edge[0]);
            int b = Integer.valueOf(edge[1]);

            List<Integer> trusted = edges.get(b);
            trusted.add(a);
            edges.put(b, trusted);
        }

        for (int i = 1; i < n + 1; i++) {
            boolean[] visited = new boolean[n + 1];

            Deque<Integer> nextCom = new ArrayDeque<>();
            nextCom.offer(i);
            visited[i] = true;
            while (!nextCom.isEmpty()) {
                Integer cur = nextCom.poll();
                List<Integer> connected = edges.get(cur);
                for (Integer c : connected) {
                    if (!visited[c]) {
                        visited[c] = true;
                        nextCom.offer(c);
                        hackable[i]++;
                    }
                }
            }
        }

        int max = 0;
        for (int i = 1; i < n + 1; i++) {
            max = Math.max(max, hackable[i]);
        }

        for (int i = 1; i < n + 1; i++) {
            if (max == hackable[i]) {
                result.append(i + " ");
            }
        }

        System.out.println(result);
    }
}
