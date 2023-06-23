package Gold.Greedy;

import java.io.*;
import java.util.*;

/**
 * Gold 3(공주님의 정원)
 *
 * https://www.acmicpc.net/problem/2457
 *
 * Solution: 정렬 + 그리디
 * 0. 모든 날짜들을 1월 1일부터 지난 날로 변환(ex: 2월 11일 = 31 + 11 = 42일)
 * 1. 피는 날짜가 가장 빠른 날짜 순으로 정렬 -> 피는 날짜가 같으면 지는 날이 가장 늦은 순으로
 *  -> 피는 날짜가 3월1일 보다 빠르면 3월 1일로 저장
 * 2. 가장 빨리 피는 꽃이 3월 1일 보다 늦게 피면 정답을 구할 수 없음 -> 0출력
 * 3. 직전의 꽃이 지는 날짜보다 더 빨리 피는 꽃들 중에서 지는 날짜가 가장 늦은 꽃을 구하기(Greedy)
 * 4. 직전 꽃 업데이트
 * 5. 마지막 꽃까지 확인을 했을때, 지는 날짜가 11월 30일보다 빠르거나 같으면 정답을 구할 수 없음 -> 0 출력
 * 6. 현재 꽃이 지는 날짜가 11월 30일보다 늦으면 더 이상 탐색할 필요 X
 */
public class Prob2457 {

    static int n;
    static int first, last;
    static List<Flower> flowers = new ArrayList();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        first = convertDate(3, 1);
        last = convertDate(11, 30);


        n = Integer.parseInt(br.readLine());
        for(int i = 0; i < n; i++){
            st = new StringTokenizer(br.readLine());
            int sm = Integer.parseInt(st.nextToken());
            int sd = Integer.parseInt(st.nextToken());
            int em = Integer.parseInt(st.nextToken());
            int ed = Integer.parseInt(st.nextToken());

            int start = Math.max(first, convertDate(sm, sd));
            int end = convertDate(em, ed);
            flowers.add(new Flower(start, end));
        }
        Collections.sort(flowers, new Comparator<Flower>(){
            @Override
            public int compare(Flower f1, Flower f2) {
                if(f1.start == f2.start){
                    return f2.end - f1.end;
                }

                return f1.start - f2.start;
            }
        }); // 정렬

        int answer = 0;
        Flower cur = flowers.get(0);
        if(cur.start <= first){
            int idx = 1;
            answer++;
            while (true) {
                if(idx >= n - 1){ // 마지막 인덱스까지 확인을 했을때
                    if(cur.end <= last){ // 마지막 꽃까지 확인을 했을때 11월 30일보다 빠르거나 같은 날에 지면 정답을 구할 수 없음
                        answer = 0;
                    }
                    break;
                }
                if(cur.end > last){ // 현재 꽃이 지는 날짜가 11월 30일보다 늦으면 더 이상 탐색할 필요 X
                    break;
                }

                Flower update = new Flower(0, 0);
                for (int i = idx; i < n; i++) {
                    Flower next = flowers.get(i);

                    if(next.start <= cur.end){
                        if(update.end <= next.end){
                            update = next;
                        }
                        idx = i;
                    }else{
                        break;
                    }
                }

                cur = update;
                answer++;
            }
        }

        System.out.println(answer);
    }

    public static int convertDate(int m, int d) {
        int day = d;
        int[] days = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        for(int i = 1; i < m; i++){
            day += days[i];
        }

        return day;
    }

    public static String convertToDate(int day){
        int m = 0;
        int d = 0;
        int[] days = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        for(int i = 1; i < 13; i++){
            if(day <= days[i]){
                m = i;
                break;
            }
            day -= days[i];
        }
        d = day;

        return m + ":" + d;
    }

    public static class Flower{
        int start;
        int end;

        public Flower(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public String toString() {
            return "Flower{" +
                    "start=" + convertToDate(start) +
                    ", end=" + convertToDate(end) +
                    '}';
        }
    }

}
