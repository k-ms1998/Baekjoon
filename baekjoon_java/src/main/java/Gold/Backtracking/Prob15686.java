package Gold.Backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Gold 5
 *
 * 문제
 * 크기가 N×N인 도시가 있다. 도시는 1×1크기의 칸으로 나누어져 있다. 도시의 각 칸은 빈 칸, 치킨집, 집 중 하나이다.
 * 도시의 칸은 (r, c)와 같은 형태로 나타내고, r행 c열 또는 위에서부터 r번째 칸, 왼쪽에서부터 c번째 칸을 의미한다. r과 c는 1부터 시작한다.
 * 이 도시에 사는 사람들은 치킨을 매우 좋아한다. 따라서, 사람들은 "치킨 거리"라는 말을 주로 사용한다.
 * 치킨 거리는 집과 가장 가까운 치킨집 사이의 거리이다. 즉, 치킨 거리는 집을 기준으로 정해지며, 각각의 집은 치킨 거리를 가지고 있다. 도시의 치킨 거리는 모든 집의 치킨 거리의 합이다.
 * 임의의 두 칸 (r1, c1)과 (r2, c2) 사이의 거리는 |r1-r2| + |c1-c2|로 구한다.
 *
 * 예를 들어, 아래와 같은 지도를 갖는 도시를 살펴보자.
 * 0 2 0 1 0
 * 1 0 1 0 0
 * 0 0 0 0 0
 * 0 0 0 1 1
 * 0 0 0 1 2
 *
 * 0은 빈 칸, 1은 집, 2는 치킨집이다.
 *
 * (2, 1)에 있는 집과 (1, 2)에 있는 치킨집과의 거리는 |2-1| + |1-2| = 2, (5, 5)에 있는 치킨집과의 거리는 |2-5| + |1-5| = 7이다. 따라서, (2, 1)에 있는 집의 치킨 거리는 2이다.
 * (5, 4)에 있는 집과 (1, 2)에 있는 치킨집과의 거리는 |5-1| + |4-2| = 6, (5, 5)에 있는 치킨집과의 거리는 |5-5| + |4-5| = 1이다. 따라서, (5, 4)에 있는 집의 치킨 거리는 1이다.
 * 이 도시에 있는 치킨집은 모두 같은 프랜차이즈이다. 프렌차이즈 본사에서는 수익을 증가시키기 위해 일부 치킨집을 폐업시키려고 한다.
 * 오랜 연구 끝에 이 도시에서 가장 수익을 많이 낼 수 있는  치킨집의 개수는 최대 M개라는 사실을 알아내었다.
 * 도시에 있는 치킨집 중에서 최대 M개를 고르고, 나머지 치킨집은 모두 폐업시켜야 한다. 어떻게 고르면, 도시의 치킨 거리가 가장 작게 될지 구하는 프로그램을 작성하시오.
 *
 * 입력
 * 첫째 줄에 N(2 ≤ N ≤ 50)과 M(1 ≤ M ≤ 13)이 주어진다.
 * 둘째 줄부터 N개의 줄에는 도시의 정보가 주어진다.
 * 도시의 정보는 0, 1, 2로 이루어져 있고, 0은 빈 칸, 1은 집, 2는 치킨집을 의미한다. 집의 개수는 2N개를 넘지 않으며, 적어도 1개는 존재한다. 치킨집의 개수는 M보다 크거나 같고, 13보다 작거나 같다.
 *
 * 출력
 * 첫째 줄에 폐업시키지 않을 치킨집을 최대 M개를 골랐을 때, 도시의 치킨 거리의 최솟값을 출력한다.
 *
 * Solution: Brute Force + Backtracking
 */
public class Prob15686 {

    static int n;
    static int m;
    static List<Pos> house = new ArrayList<>();
    static List<Pos> chicken = new ArrayList<>();
    static int ans = Integer.MAX_VALUE;
    static Integer[][] hDist;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        for (int y = 0; y < n; y++) {
            st = new StringTokenizer(br.readLine());
            for (int x = 0; x < n; x++) {
                int cur = Integer.parseInt(st.nextToken());
                if (cur == 1) {
                    house.add(new Pos(x, y));
                } else if(cur == 2) {
                    chicken.add(new Pos(x, y));
                }
            }
        }

        /**
         * 시간 절약을 위해 각 집과 각 치킨집 까지의 거리를 계산해서 저장
         */
        hDist = new Integer[house.size()][chicken.size()];
        for (int h = 0; h < house.size(); h++) {
            Pos curHouse = house.get(h);
            for (int c = 0; c < chicken.size(); c++) {
                Pos curChicken = chicken.get(c);

                int dist = Math.abs(curHouse.x - curChicken.x) + Math.abs(curHouse.y - curChicken.y);
                hDist[h][c] = dist;
            }
        }

        List<Integer> arr = new ArrayList<>();
        backTrack(-1, arr);
        System.out.println(ans);
    }

    private static void backTrack(int s, List<Integer> arr) {
        if (arr.size() > m) {
            return;
        }

        /**
         * 3. 남길 치킨집들의 Idx 값들의 조합들에 대해서 최단 거리 계산
         */
        if (arr.size() == m) {
            int curCnt = 0;
            for (int h = 0; h < house.size(); h++) {
                int tmpCnt = Integer.MAX_VALUE;
                /**
                 * arr 에는 남길 치킨집들의 Idx 값들의 조합 저장
                 */
                for (int i = 0; i < arr.size(); i++) {
                    int cIdx = arr.get(i);
                    /**
                     * 각 집에 대해서, 남길 치킨집들 중에서 가장 짧은 거리 찾기
                     *  -> tmpCnt 에 현재 집에서 남길 치킨집들 중에서 가장 가까운 거리에 있는 치킨집 까지의 거리 저장
                     */
                    if(tmpCnt > hDist[h][cIdx]){
                        tmpCnt = hDist[h][cIdx];
                    }
                }
                /**
                 * curCnt 에 각 집에 대해서 남길 치킨집들 중 가장 가까운 거리에 있는 치킨집 까지의 거리 더하기
                 *  -> 그리므로, curCnt 에는 현재 조합의 치킨집들이 남았을 경우, 치킨 거리 계산한 값이 저장됨
                 */
                curCnt += tmpCnt;
            }

            if (ans > curCnt) {
                ans = curCnt;
            }

            return;
        }

        /**
         * 2. Backtracking 으로 남길 치킨집들의 Idx 값들의 조합 저장 !! (IMPORTANT) !!
         * ex) m == 3 이면,
         * 남길 치킨집들의 Idx == {0, 1, 2}, {0, 1, 3}, {0, 1, 4}, {0, 2, 3}, {0, 2, 4}, {0, 3, 4}, {1, 2, 3}, {1, 2, 4}, {1, 3, 4}, {2, 3, 4}
         */
        for (int i = s + 1; i < chicken.size(); i++) {
            arr.add(i);
            backTrack(i, arr);
            arr.remove(arr.size()-1);
        }
    }

    static class Pos{
        int x;
        int y;

        public Pos(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "{ " + x + ", " + y + " }";
        }
    }
}
