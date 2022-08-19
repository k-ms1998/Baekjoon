package Gold.Greedy;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Gold 5: Greedy
 * 문제
 * 한국도로공사는 고속도로의 유비쿼터스화를 위해 고속도로 위에 N개의 센서를 설치하였다.
 * 문제는 이 센서들이 수집한 자료들을 모으고 분석할 몇 개의 집중국을 세우는 일인데, 예산상의 문제로, 고속도로 위에 최대 K개의 집중국을 세울 수 있다고 한다.
 * 
 * 각 집중국은 센서의 수신 가능 영역을 조절할 수 있다. 집중국의 수신 가능 영역은 고속도로 상에서 연결된 구간으로 나타나게 된다.
 * N개의 센서가 적어도 하나의 집중국과는 통신이 가능해야 하며, 집중국의 유지비 문제로 인해 각 집중국의 수신 가능 영역의 길이의 합을 최소화해야 한다.
 *
 * 편의를 위해 고속도로는 평면상의 직선이라고 가정하고, 센서들은 이 직선 위의 한 기점인 원점으로부터의 정수 거리의 위치에 놓여 있다고 하자.
 * 따라서, 각 센서의 좌표는 정수 하나로 표현된다. 이 상황에서 각 집중국의 수신 가능영역의 거리의 합의 최솟값을 구하는 프로그램을 작성하시오. 단, 집중국의 수신 가능영역의 길이는 0 이상이며 모든 센서의 좌표가 다를 필요는 없다.
 *
 * 입력
 * 첫째 줄에 센서의 개수 N(1 ≤ N ≤ 10,000),
 * 둘째 줄에 집중국의 개수 K(1 ≤ K ≤ 1000)가 주어진다.
 * 셋째 줄에는 N개의 센서의 좌표가 한 개의 정수로 N개 주어진다. 각 좌표 사이에는 빈 칸이 하나 있으며, 좌표의 절댓값은 1,000,000 이하이다.
 *
 * 출력
 * 첫째 줄에 문제에서 설명한 최대 K개의 집중국의 수신 가능 영역의 길이의 합의 최솟값을 출력한다.
 * 
 * Solution: Greedy
 * 1. 센서들의 위치를 오름차순으로 정렬 => 그리디 문제는 정렬으로 시작하는 문제가 많음
 * 2. 각 센서들 간의 거리를 구해서 저장 (dist[])
 * 3. 센서들의 거리를 정렬
 * 4. 거리가 가장 큰 K-1개 무시
 * 5. 나머지 거리들의 합 구함
 *
 * !! 중요 !!
 * ex:
 * n = 6, k = 2, 센서들의 위치 = 1, 6, 9, 3, 6, 7
 *
 * 1. 센서들 정렬
 * -> 1, 3, 6, 6, 7, 9
 *
 * 2. 거리 구해서 dist 에 저장
 * -> dist[] = {2, 3, 0, 1, 2}
 *
 * 3. dist 졍렬
 * -> dist[] = {0, 1, 2, 2, 3}
 *
 * !! 4. 거리가 가장 큰 K-1개 무시 !! (핵심)
 * -> dist[4] 무시
 * => 정렬한 센서들 1, 3, 6, 6, 7, 9 에서 거리가 가장 큰 구간 == 3 과 6 사이
 *  => 그러므로, 3 과 6 사이를 파티셔닝 해서 각각 3 과 6 에 집중국 설치
 *      => 거리가 3인 3 과 6 사이의 거리 무시할 수 있음 ( == dist[4])
 *          => 집중국의 수신 가능 영역 길이 최소화
 *      => K 개의 집중국에 대해서 , K-1개의 파티션이 있으면 K 개의 집중을 설치하게 됨
 *          => 그러므로, K-1개의 가장 큰 거리 무시
 *
 * 5. 합 구하기
 * -> 0 + 1 + 2 + 2 = 5
 */
public class Prob2212 {

    static int n;
    static int k;
    static Integer[] sensors;
    static Integer[] dist;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        n = sc.nextInt();
        k = sc.nextInt();

        sensors = new Integer[n];
        int maxX = 0;
        int minX = 1000000 ;
        for (int i = 0; i < n; i++) {
            int x = sc.nextInt();
            sensors[i] = x;
            if (maxX < x) {
                maxX = x;
            }
            if (minX > x) {
                minX = x;
            }
        }

        Arrays.sort(sensors);

        dist = new Integer[n - 1];
        for (int i = 0; i < n - 1; i++) {
            dist[i] = (sensors[i + 1] - sensors[i]);
        }
        Arrays.sort(dist);

        int kCnt = 0;
        int ans = 0;
        for (int i = n - 2; i >= 0; i--) {
            kCnt++;
            if (kCnt <= k - 1) {
                continue;
            }
            ans += dist[i];
        }

        System.out.println(ans);
    }
}
