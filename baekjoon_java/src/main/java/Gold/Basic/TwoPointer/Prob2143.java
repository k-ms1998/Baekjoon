package Gold.Basic.TwoPointer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Gold 3 (두 배열의 합)
 *
 * https://www.acmicpc.net/problem/2143
 *
 * Solution: 누적합 + 투 포인터
 */
public class Prob2143 {

    static int t;
    static int n;
    static int[] arrN;
    static int m;
    static int[] arrM;

    static List<Integer> sumN = new ArrayList<>();
    static List<Integer> sumM = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;


        t = Integer.parseInt(br.readLine());

        n = Integer.parseInt(br.readLine());
        arrN = new int[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            int num = Integer.parseInt(st.nextToken());
            arrN[i] = num;
        }

        m = Integer.parseInt(br.readLine());
        arrM = new int[m];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < m; i++) {
            int num = Integer.parseInt(st.nextToken());
            arrM[i] = num;
        }

        /**
         * sumN 에는 arrN 의 값들로 만들 수 있는 모든 합의 경우의 수 저장
         * sumM 에는 arrM 의 값들로 만들 수 있는 모든 합의 경우의 수 저장
         */
        for (int i = 0; i < n; i++) {
            int tmp = 0;
            for (int j = i; j < n; j++) {
                tmp += arrN[j];
                sumN.add(tmp);
            }
        }
        for (int i = 0; i < m; i++) {
            int tmp = 0;
            for (int j = i; j < m; j++) {

                tmp += arrM[j];
                sumM.add(tmp);
            }
        }

        /**
         * 정렬 필수!
         * 합의 값에 따라서 포인터의 위치를 이동 시키기 때문에:
         * 합의 값이 t 값보다 작으면 sumN 을 가르키는 포인터를 오른쪽으로 한 자리 옮겨서 합의 값 증가시킴
         * 합의 값이 t 값보다 크면 sumM 을 가르키는 포인터를 왼쪽으로 한 자리 옮겨서 합의 값 증가시킴
         */
        Collections.sort(sumN);
        Collections.sort(sumM);

        long ans = 0L;
        int sizeN = sumN.size();
        int sizeM = sumM.size();
        /**
         * idxN: sumN 에서 가능한 합 중 하나의 값을 가르키는 위치
         * idxM: sumM 에서 가능한 합 중 하나의 값을 가르키는 위치
         * 
         * sumN 의 값 중 하나 + sumM 의 값 중 하나를 골라서 t 값을 만들어야 됨
         * 이때, idxN 은 sumN 의 가장 작은 값을 가르키고, idxM 은 sumM 의 가장 큰 값을 가르키도록 초기화
         */
        int idxN = 0;
        int idxM = sizeM - 1;
        while (idxN < sizeN && idxM >= 0) {
            long curSum = sumN.get(idxN) + sumM.get(idxM);
            /**
             * curSum 의 값에 따라 포인터의 위치 변경
             */
            if (curSum > t) {
                idxM--;
            } else if (curSum < t) {
                idxN++;
            } else {
                int curSumN = sumN.get(idxN);
                int curIdxNCnt = 0;
                int curSumM = sumM.get(idxM);
                int curIdxMCnt = 0;

                /**
                 * 이때, 각각 sumN 과 sumM 이 같은 합을 만드는 경우의 수가 여러가지 일 수도 있기 때문에 이 부분은 고려해야됨
                 * ex: 3을 만들기 위해 sumN 은 1이고, sumM은 2일 수도 있다
                 * 그러나, 만약에 sumN 에 1의 값이 2개 있으면, arrN 의 값들로 1을 만들 수 있는 경우의 수는 2가지
                 * 그리고, 만약에 sumM 에 2의 값이 3개 있으면, arrN 의 값들로 2를 만들 수 있는 경우의 수는 3가지
                 *  => 그러므로, arrN 의 값들로 1을 만들 수 있는 경우의 수 * arrM 의 값들로 2를 만들 수 있는 경우의 수를 구해야 됨
                 *      => arrN 의 값들로 1을 만들 수 있는 경우의 수 == sumN 에서 1의 갯수
                 *      => arrM 의 값들로 2를 만들 수 있는 경우의 수 == sumM 에서 2의 갯수
                 */
                while (true) {
                    if(idxN >= sizeN){
                        break;
                    }
                    if(sumN.get(idxN) != curSumN){
                        break;
                    }
                    idxN++;
                    curIdxNCnt++;
                }
                while (true) {
                    if(idxM < 0){
                        break;
                    }
                    if(sumM.get(idxM) != curSumM){
                        break;
                    }

                    idxM--;
                    curIdxMCnt++;

                }

                ans += (curIdxNCnt * curIdxMCnt);
            }
        }

        System.out.println(ans);
    }
}
/*
5
4
1 3 1 2
3
1 3 2
 */