package Gold.Greedy;

import java.io.*;
import java.util.*;

/**
 * Gold 2(연료 채우기)
 * 
 * https://www.acmicpc.net/problem/1826
 * 
 * Solution: 우선순위큐 + 그리디
 * (가장 긴 증가하는 부분수열 문제와 비슷)
 * 1. 순서대로 주유소를 방문하면서, max heap 에 방문한 가능한 주요소의 연료의 양 넣기
 * 2. Max heap 에서의 최대값을 pop 해서, 현제 연료의 양에 더하기
 * 3. 현재 연료의 양이 도착지점보다 크거나 같으면 종료
 *
 * ex:
 * 4
 * 4 4
 * 5 2
 * 11 5
 * 15 10
 * 25 10
 * => 0 - 4(4) - 5(2) - 11(5) - 15(10) - 25
 * 1. 시작지점 0에서 연료의 양이 10일때, 도달 가능한 주유소 = 4,5
 * 2. 4랑 5에서의 연료의 양을 max heap에 추가 -> max heap = {4, 2}
 * 3. Max heap에서 최댓값 pop해서, 현재 연료의 양에 추가 -> 4 pop & 연료의 양 = 14, max heap = {2}
 * 4. 연료의 양이 14일때 도달 가능한 주유소 = 11, 15
 * 5. 1랑 15에서의 연료의 양을 max heap에 추가 -> max heap = {10, 5, 2}
 * 6. Max heap에서 최댓값 pop해서, 현재 연료의 양에 추가 -> 10 pop & 연료의 양 = 24, max heap = {5, 2}
 * 7. 모든 주요소를 확인 했기 떄문에, 그냥 max heap에서 pop해서 현재 연료의 양에 추가 -> 5 pop & 연료의 양 = 29, max heap = {2}
 * 8. 도착 지점 도달
 *
 * ex: 0 - 4(2) - 5(4) - 25 , p = 10일때:
 * 1. 시작지점 0에서 연료의 양이 10일때, 도달 가능한 주유소 = 4,5 -> MaxHeap = {4, 2}
 * 2. 4 pop -> 연료의 양 14
 * 3. 2 pop -> 연료의 양 16 => 그러므로, 최대 갈수 있는 거리 = 16 -> 0 -> 4 -> 5 순서로 방문하면 됨
 * => MaxHeap을 통해서 연료의 양을 업데이트하면 주유소 5부터 방문하는 것처럼 보이지만, 연료의 양 2까지 pop해서 더하면 사실은 0 -> 4 -> 5 순으로 방문하는 것
 *
 * 초기에 주어진 연료의 양에서 시작해서, 도달 가능한 모든 주유소들을 확인해서 MaxHeap에 넣어서 충전 가능한 연료의 양들을 저장
 * MaxHeap에서 최대값들을 빼고 연료의 양에 더해줌으로써, 얻을 수 있는 최대의 연료의 양 확인
 * 연료의 양만으로도 시작지점에서 도달 가능한 점들을 알 수 있기 때문에 따로 현재 위치를 저장할 필요 X
 * 참고: https://velog.io/@abc5259/%EB%B0%B1%EC%A4%80-1826-%EC%97%B0%EB%A3%8C-%EC%B1%84%EC%9A%B0%EA%B8%B0-JAVA
 */
public class Prob1826 {

    static int n;
    static int l, p; // l == 도착지점, p == 처음에 있는 연료의 양
    static Station[] stations;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());

        stations = new Station[n];
        for(int i = 0; i < n; i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            stations[i] = new Station(a, b);
        }
        Arrays.sort(stations, new Comparator<Station>(){
            @Override
            public int compare(Station i1, Station i2) {
                return i1.x - i2.x;
            }
        }); // 주유소 위치들을 순서대로 정렬

        st = new StringTokenizer(br.readLine());
        l = Integer.parseInt(st.nextToken());
        p = Integer.parseInt(st.nextToken()); // 초기 연료의 양

        int answer = 0;
        PriorityQueue<Integer> queue = new PriorityQueue<>(Collections.reverseOrder()); // Max Heap
        queue.offer(0);
        int idx = 0;
        while(true){
            if(queue.isEmpty()){
                /*
                도착지점에 도달하기 전에 queue 가 비어있으면 도착지점에 도달 불가능
                -> queue 가 비어있으면 연료를 충전할 수 있는 주유소가 더 없는 것
                 */
                answer = -1;
                break;
            }
            int cur = queue.poll();
            p += cur;
            if(p >= l){ // 현재까지 얻은 연료의 양이 도착지점의 위치보다 크거나 같으면 종료
                break;
            }
            for(int i = idx; i < n; i++){ // x 값 기준으로 정렬했기 때문에, idx 이전의 주유소들은 이미 방문 가능하기 때문에 idx 이후의 주유소들만 확인
                if(p >= stations[i].x){
                    queue.offer(stations[i].fuel);
                }else{
                    idx = i;
                    break;
                }
            }
            ++answer;
        }

        System.out.println(answer);
    }

    public static class Station{
        int x;
        int fuel;

        public Station(int x, int fuel) {
            this.x = x;
            this.fuel = fuel;
        }

    }
}
