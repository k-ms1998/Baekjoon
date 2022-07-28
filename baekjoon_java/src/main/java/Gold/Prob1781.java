package Gold;

import java.util.*;

/**
 * Gold 2
 *
 * 문제:
 * 상욱 조교는 동호에게 N개의 문제를 주고서, 각각의 문제를 풀었을 때 컵라면을 몇 개 줄 것인지 제시 하였다. 하지만 동호의 찌를듯한 자신감에 소심한 상욱 조교는 각각의 문제에 대해 데드라인을 정하였다.
 *
 * 문제 번호	1	2	3	4	5	6	7
 * 데드라인	1	1	3	3	2	2	6
 * 컵라면 수	6	7	2	1	4	5	1
 * 위와 같은 상황에서 동호가 2, 6, 3, 1, 7, 5, 4 순으로 숙제를 한다면 2, 6, 3, 7번 문제를 시간 내에 풀어 총 15개의 컵라면을 받을 수 있다.
 *
 * 문제는 동호가 받을 수 있는 최대 컵라면 수를 구하는 것이다. 위의 예에서는 15가 최대이다.
 *
 * 문제를 푸는데는 단위 시간 1이 걸리며, 각 문제의 데드라인은 N이하의 자연수이다. 또, 각 문제를 풀 때 받을 수 있는 컵라면 수와 최대로 받을 수 있는 컵라면 수는 모두 231보다 작거나 같은 자연수이다.
 *
 * 입력:
 * 첫 줄에 숙제의 개수 N (1 ≤ N ≤ 200,000)이 들어온다. 다음 줄부터 N+1번째 줄까지 i+1번째 줄에 i번째 문제에 대한 데드라인과 풀면 받을 수 있는 컵라면 수가 공백으로 구분되어 입력된다.
 *
 * 출력:
 * 첫 줄에 동호가 받을 수 있는 최대 컵라면 수를 출력한다.
 */
public class Prob1781 {

    static List<Problem> problems = new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        for (int i = 0; i < n; i++) {
            int deadLine = sc.nextInt();
            int noodle = sc.nextInt();

            Problem problem = new Problem(deadLine, noodle);
            problems.add(problem);
        }

        Collections.sort(problems, new Comparator<Problem> () {
            @Override
            public int compare(Problem o1, Problem o2) {
                if (o1.deadLine == o2.deadLine) {
                    if(o1.noodle > o2.noodle){
                        return 1;
                    } else if (o1.noodle < o2.noodle) {
                        return -1;
                    } else {
                        return 0;
                    }
                } else if (o1.deadLine > o2.deadLine) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });

        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for (Problem problem : problems) {
            int deadLine = problem.deadLine;
            int noodle = problem.noodle;

            queue.add(noodle);
            if (queue.size() > deadLine) {
                queue.poll();
            }
        }

        int ans = 0;
        while (!queue.isEmpty()) {
            ans += queue.poll();
        }
        System.out.println(ans);
    }

    public static class Problem{
        int deadLine;
        int noodle;

        public Problem(int deadLine, int noodle) {
            this.deadLine = deadLine;
            this.noodle = noodle;
        }

        @Override
        public String toString() {
            return "{ " + deadLine +" : " + noodle + " }";
        }

    }
}
