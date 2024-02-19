package Gold.Basic.TwoPointer.SlidingWindow;

import java.io.*;
import java.util.*;

/**
 * 1. 다음과 같은 두 가지 행사를 진행한다
 * 	1-1. 벨트의 임의의 한 위치부터 k개의 접시를 연속해서 먹을 경우 할인된 정액 가격으로 제공
 * 	1-2. 1-1번 행상에 참여할 경우 쿠폰에 적혀진 종류의 초밥 하나리를 무료로 제공
 * 2. 벨트의 상태, 메누에 있는 초밥의 가짓수, 연속해서 먹는 접시의 개수, 쿠폰 번호가 주어졌을때 손님이 먹을 수 있는 초밥 가짓수의 최댓값을 구하기
 * 3. 슬라이딩 윈도우 사용
 *  3-1. maxSelect 크기의 윈도우 만들기 -> lIdx는 윈도우의 처음 인덱스, rIdx는 윈도우의 마지막 인덱스
 *  3-2. 우선 lIdx는 0으로 두고, rIdx가 maxSelect가 될때까지 회전 초밥의 초받들을 순서대로 탐색
 *  3-3. 그 다음부터는 현재 lIdx의 초밥을 반환하고, lIdx를 1 증가해서 윈도우에서 가장 앞에 있던 초밥을 뺸다 && rIdx를 1 증가 시키고 해당 인덱스의 초밥을 윈도우에 넣는다
 *  3-4. 3-2랑 3-3에서 값들을 추가하고 뺼때 다음과 같은 연산을 한다
 *      3-4-1. 값을 뺼떄는 윈도우 안에 해당 초밥의 개수를 1 감소시킨다
 *          3-4-1-1. 값을 감소시킨 후에 윈도우 안에 0개 있으면 연속으로 선택한 접시 중 해당 초밥은 없음 -> cnt 감소
 *  *       3-4-1-2. 값을 감소시킨 후에 윈도우 안에 0개 보다 크면 여전히 연속으로 선택한 접시 중 해당 초밥은 있음 -> cnt 유지
 *      3-4-2. 값을 추가할 때는 윈도우 안에 해당 초밥의 개수를 확인하고 1 증가시킨다
 *          3-4-2-1. 값을 증가시키기 전에 윈도우 안에 0개 있으면 연속으로 선택한 접시 중 해당 초밥은 없음 -> cnt 증가
 *          3-4-2-2. 값을 증가시키기 전에 윈도우 안에 0개보다 크면 연속으로 선택한 접시 중 해당 초밥은 있음 -> cnt 유지
 *      3-4-3. 3-4-1과 3-4-2 연산을 통해 언제나 해당 윈도우 안에 중복되지 않는 초밥이 총 몇개 있는지 항상 저장하고 있음
 *  3-5. 회전 초밥이기 때문에 다시 앞으로 갈 수 있음 -> rIdx가 size 만큼의 초밥을 확인 후, 다시 maxSelect - 1이 될때까지 연속되는 접시들을 확인
 */
public class Prob15961 {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    static int size, types, maxSelect, coupon;
    static int[] belt;
    static int[] selected; // selected[sushi] -> 윈도우 안에 sushi가 몇개 있는지 저장

    public static void main(String[] args) throws IOException{
        st = new StringTokenizer(br.readLine());
        size = Integer.parseInt(st.nextToken());
        types = Integer.parseInt(st.nextToken());
        maxSelect = Integer.parseInt(st.nextToken());
        coupon = Integer.parseInt(st.nextToken());

        selected = new int[types + 1];
        belt = new int[size];

        for(int idx = 0; idx < size; idx++) {
            belt[idx] = Integer.parseInt(br.readLine());
        }

        int answer = 0;
        int cnt = 0;
        int lIdx = 0;
        int rIdx = 0;
        for(int idx = 0; idx < maxSelect; idx++){ // maxSelect 크기의 윈도우 만들기
            int sushi = belt[rIdx];
            if(selected[sushi] == 0){
                cnt++;
            }
            selected[sushi]++;
            rIdx++;
        }
        for(int idx = maxSelect; idx < size + maxSelect; idx++){
            if(answer == maxSelect + 1){ // 윈도우의 수 만큼 + 쿠폰의 초밥까지 먹을 수 있는 상태이면 최댓값 => 탐색 종료
                break;
            }
            if(selected[coupon] == 0){ // 현재 윈도우에 coupon 초밥이 없는 경우 현재 윈도우안에 있는 초밥의 종류 + 1의 종류를 먹을 수 있음
                answer = Math.max(answer, cnt + 1);
            }else{
                answer = Math.max(answer, cnt);
            }
            int sushi = belt[rIdx % size];
            int firstSushi = belt[lIdx];
            selected[firstSushi]--; // 윈도우에서 가장 앞에 있는 초밥 빼기
            if(selected[firstSushi] == 0){ // 윈도우에는 해당 초밥이 더 이상 없기 떄문에 중복되지 않는 초밥의 종류 수가 1 감소됨
                cnt--;
            }

            if(selected[sushi] == 0){ //  윈도우에는 새로 추가할려는 초밥이 없기 떄문에, 해당 초밥이 윈도우에 없으므로 중복되지 않는 초밥의 종류 수가 1 증가됨
                cnt++;
            }
            selected[sushi]++; // 윈도우에 새로운 초밥 추가
            
            // 윈도우 이동시키기
            lIdx++; 
            rIdx++;
        }
        if(selected[coupon] == 0){
            answer = Math.max(answer, cnt + 1);
        }else{
            answer = Math.max(answer, cnt);
        }

        System.out.println(answer);
    }
}
