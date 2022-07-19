package Gold;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Stack;

/**
 * Gold 3 : Dynamic Programming + LIS(Longest Increasing Subsequence)(가장 긴 중가하는 부분 수열)
 *
 * 문제
 * 밑면이 정사각형인 직육면체 벽돌들을 사용하여 탑을 쌓고자 한다. 탑은 벽돌을 한 개씩 아래에서 위로 쌓으면서 만들어 간다.
 * 아래의 조건을 만족하면서 가장 높은 탑을 쌓을 수 있는 프로그램을 작성하시오.
 * 벽돌은 회전시킬 수 없다. 즉, 옆면을 밑면으로 사용할 수 없다.
 * 밑면의 넓이가 같은 벽돌은 없으며, 또한 무게가 같은 벽돌도 없다.
 * 벽돌들의 높이는 같을 수도 있다.
 * 탑을 쌓을 때 밑면이 좁은 벽돌 위에 밑면이 넓은 벽돌은 놓을 수 없다.
 * 무게가 무거운 벽돌을 무게가 가벼운 벽돌 위에 놓을 수 없다.
 *
 * 입력
 * 첫째 줄에는 입력될 벽돌의 수가 주어진다. 입력으로 주어지는 벽돌의 수는 최대 100개이다.
 * 둘째 줄부터는 각 줄에 한 개의 벽돌에 관한 정보인 벽돌 밑면의 넓이, 벽돌의 높이 그리고 무게가 차례대로 양의 정수로 주어진다.
 * 각 벽돌은 입력되는 순서대로 1부터 연속적인 번호를 가진다. 벽돌의 넓이, 높이 무게는 10,000보다 작거나 같은 자연수이다.
 *
 * 출력
 * 탑을 쌓을 때 사용된 벽돌의 수를 빈칸없이 출력하고, 두 번째 줄부터는 탑의 가장 위 벽돌부터 가장 아래 벽돌까지 차례로 한 줄에 하나씩 벽돌 번호를 빈칸없이 출력한다.
 */
public class Prob2655 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.valueOf(br.readLine());

        Brick[] bricks = new Brick[n];
        for(int i = 0; i < n; i++){
            //a = area, h = height, w = weight
            String brick = br.readLine();
            String[] brickSplit = brick.split(" ");
            int a = Integer.valueOf(brickSplit[0]);
            int h = Integer.valueOf(brickSplit[1]);
            int w = Integer.valueOf(brickSplit[2]);

            bricks[i] = new Brick(i+1, a, h, w);
        }

        /**
         * 벽돌들의 넓이가 오름차순이 되도록 정렬
         */
        Arrays.sort(bricks, (b1, b2) -> {
            if (b1.area > b2.area) {
                return 1;
            } else {
                return -1;
            }
        });

        /**
         * DP 초기화:
         * 자신의 벽돌하나만 놓여 있을때로 초기화
         * -> i번째 벽돌 위로 쌓을 수 있는 벽돌이 없으면, dp[i]는 i번째 벽돌의 높이
         */
        Integer[] dp = new Integer[n];
        for(int i = 0; i < n; i++){
            dp[i] = bricks[i].height;
        }

        /**
         * 넓이가 작은 벽돌부터 위에서 쌓는 방식으로 해결
         */
        int maxH = bricks[0].height;
        for (int i = 1; i < n; i++) {
            /**
             * currentBrick -> 현재 쌓은 벽돌 중에서 가장 밑에 추가할 벽돌
             */
            Brick currentBrick = bricks[i];
            for (int j = 0; j < i; j++) {
                Brick cmpBrick = bricks[j];
                /**
                 * i번째 벽돌 위로 벽돌을 쌓을 수 있는 무게로 확인. 현재 넓이가 작은 순서대로 쌓았기 때문에 넓이는 추가로 고려 X
                 * 이때, dp[j]는 j번쨰 벽돌까지 높이가 최대가 되도록 벽돌들을 쌓았을때의 높이
                 * ex)
                 * 벽돌들이 있을때:
                 * 25 3 4
                 * 4 4 6
                 * 9 2 3
                 * 16 2 5
                 * 1 5 2
                 * 벽돌들을 넓이 오름차순으로 정렬 시:
                 * 1 5 2
                 * 4 4 6
                 * 9 2 3
                 * 16 2 5
                 * 25 3 4
                 * -> if i == 5, j == 3
                 *  -> dp[j] == 7 -> 벽돌 1,2,3 을 사용해서 쌓았을때 가능한 조합 중 높이가 가장 높은 값 저장 -> 벽돌 1, 3을 쌓은 상태에서의 높이(벽돌 2의 무게가 벽돌 3의 무게보다 크므로 쌓을 수 없음)
                 *      -> 이 상태에서, 벽돌 i(==5)를 쌓을 수 있는지 확인하고, 무게를 확인해서 쌓을 수 있으면 dp[i] 값 업데이트
                 *
                 */
                if (currentBrick.weight > cmpBrick.weight) {
                    dp[i] = dp[i] > dp[j] + currentBrick.height ? dp[i] : dp[j] + currentBrick.height;
                }
            }
            if (dp[i] > maxH) {
                maxH = dp[i];
            }
        }

        Stack<Integer> st = new Stack<>();
        int curIdx = n-1;
        while (curIdx >= 0) {
            if (dp[curIdx] == maxH) {
                maxH -= bricks[curIdx].height;
                st.push(bricks[curIdx].num);
            }
            curIdx--;
        }
        System.out.println(st.size());
        while (!st.isEmpty()) {
            System.out.println(st.pop());
        }
        
    }
    public static class Brick {
        /**
         * num : 몇번째 벽돌인지 저장 -> 벽돌을 넓이 순으로 정렬할 경우, 순서가 바뀌기 때문에 몇번째 벽돌인지 추적할 필요가 있음
         * area: 벽돌의 넓이 저장
         * height: 벽돌의 높이 저장
         * weight: 벽돌의 무게 저장
         */
        int num;
        int area;
        int height;
        int weight;

        public Brick() {
        }

        public Brick(int num, int area, int height, int weight) {
            this.num = num;
            this.area = area;
            this.height = height;
            this.weight = weight;
        }
    }
}
