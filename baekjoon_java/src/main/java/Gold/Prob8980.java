package Gold;

import java.io.*;
import java.util.*;

/**
 * Gold 2(택배)
 *
 * https://www.acmicpc.net/problem/8980
 *
 * Solution: Sort + Greedy
 * 1. 도착 지점이 작은 순, 같으면 출발 지점이 높은 순으로 정렬
 * -> 도착 지점이 빠를 수록, 택배를 트록에서 빨리 제거 시킬 수 있음
 *  -> 배달 지점이 더 멀리 있는 지점에 배달 해야 하는 택배들을 더 빨리 트럭에 넣을 수 있음
 * -> 도착 지점이 같을때, 출발 지점이 더 높을 수록 배달 해야되는 거리가 더 짧음
 *  -> 택배를 넣고 짧은 거리를 이동해야 더 많은 양의 택배를 배달할 수 있음
 *      -> EX: (1->6, 30), (3->6, 30) 인 택배들이 있을때:
 *          ->첫번째 택배를 먼저 넣으면 1~6까지 30을 들고 있느 상태에서 다른 배달들을 해야함
 *          ->두번째 택배를 먼저 넣으면 3~6까지 30일 들고 있는 상태에서 다른 배들들을 해야함
 * 2. 1에서 정렬한 순서대로 택배 배달
 * -> 이때, 각 택배의 시작 지점에서 도착 지점 직전까지의 점까지 들고 있는 택배의 수 업데이트
 *  -> 도착 지점에서는 택배를 배달을 완료하기 때문에 delivered 업데이트 X
 *      -> ex: (1->4, 30)을 배달할때, 마을 1, 2, 3을 지날때 택배 30이 트럭에 실려 있으며, 마을 4에 도착하면 내려놓기 때문에 마을 4에서는 트럭에 실리 택배는 0
 *          -> delivered = {30, 30, 30, 0}
 *  -> ex: delivered 에 1~n 의 각 지점에서 트럭에 실어져 있는 총 택배의 수를 나타낼떄:
 *      초기: delivered = {0, 0, 0, 0}, (n = 4, cap = 40)
 *      정렬된 택배 = (1->2, 10), (2->3, 10), (1->3, 20), (3->4, 20), (2->4, 20), (1->4, 30)
 *     1. (1->2, 10) 넣기
 *     delivered = (10, 0, 0, 0) => 10 배달
 *
 *     2. (2->3, 10) 넣기
 *     delivered = (10, 10, 0, 0) => 10 배달
 *
 *     3. (1->3, 20) 넣기
 *     delivered = (30, 30, 0, 0) => 20 배달
 *
 *     4. (3->4, 20) 넣기
 *     delivered = (30, 30, 20, 0) => 20 배달
 *
 *     5. (2->4, 20) 넣기
 *     delivered = (30, 40, 30, 0) => 10 배달
 *     => !! 현재 택배는 20이지만, 2~4구간에서 2번 마을에서는 20을 넣으면 트럭 용량 초과 => 그러므로 택배의 일부분인 10만 추가 가능
 *
 *     61. (1->4, 30) 넣기
 *     delivered = (30, 40, 30, 0) => 0 배달
 *     => !! 1~4 구간에서 2번 마을에서 이미 트럭의 용량을 초과 했기 때문에 더 이상 2번 마을을 지나는 경우에는 택배를 추가로 배달 할 수 없음
 *
 *     => answer = 10 + 10 + 20 + 20 + 10 + 0
 *               = 70
 */
public class Prob8980 {

    static int n, cap;
    static List<Box> boxes = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        cap = Integer.parseInt(st.nextToken());

        int m = Integer.parseInt(br.readLine());
        for(int i = 0; i < m; i++){
            st = new StringTokenizer(br.readLine());
            int src = Integer.parseInt(st.nextToken());
            int dst = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());

            boxes.add(new Box(src, dst, cost));
        }

        Collections.sort(boxes, new Comparator<Box>(){ // 택배들 정렬
            @Override
            public int compare(Box b1, Box b2) {
                if(b1.dst == b2.dst){
                    return b2.src - b1.src;
                }

                return b1.dst - b2.dst;
            }
        });

        int answer = 0;
        int[] delivered = new int[n + 1];
        for(Box box : boxes){
            int src = box.src;
            int dst = box.dst;
            int cost = box.cost;

            int maxDelivered = 0; // 탐색 할 구간에서 (src <= x < dst) 트럭이 마을을 지날때 싣고 있는 가장 많은 양의 택배
            for(int i = src; i < dst; i++){
                if(delivered[i] >= cap){ // 이미 해당 마을을 지날때 트럭의 용량 초과
                    maxDelivered = -1;
                    break;
                }
                maxDelivered = Math.max(maxDelivered, delivered[i]);
            }

            if(maxDelivered != -1){
                int updatedMax = maxDelivered + cost; // 최대 택배의 양 + 추가할려는 택배의 양
                int additional = 0; //추가로 실을 수 있는 택배의 양
                if(updatedMax > cap){ // 최대 택배의 양 + 추가할려는 택배의 양 > cap 이면 추가할려는 택배 중 일부만 추가할 수 있음
                    additional = cap - maxDelivered;
                }else{
                    additional = cost;
                }

                for(int i = src; i < dst; i++){
                    delivered[i] += additional;
                }

                answer += additional;
            }

        }

        System.out.println(answer);
    }

    public static class Box{
        int src;
        int dst;
        int cost;

        public Box(int src, int dst, int cost) {
            this.src = src;
            this.dst = dst;
            this.cost = cost;
        }

        @Override
        public String toString() {
            return "{" + src + "->" + dst + ", cost=" + cost + "}";
        }
    }
}
