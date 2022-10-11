package Gold;

import java.io.*;
import java.util.*;

/**
 * Gold 2(보석 도둑)
 *
 * https://www.acmicpc.net/problem/1202
 *
 * Solution: Sort + PriorityQueue
 *
 * 1. 보석들을 무게 기준 오름차순으로, 가방들도 오름차순으로 정렬
 * 2. 정렬한 가방을 순서대로 탐색하고, 해당 가방에 들어갈 수 있는 보석들을 모두 우선순위 큐(PQ)에 저장 (이때, PQ는 Max Heap)
 *  -> 이때, 보석들도 무게 순으로 정렬 됐기 때문에 보석들도 앞에서부터 순서대로 탐색하면 됨; 가방에 들어갈 수 있는 보석 => 가방 무게 >= 보석 무게
 * 3. 현재 가방에 더 이상 둘어갈 수 없을때, PQ에서 poll()한 값을 추가
 *  -> PQ에 있는 보석들의 무게는 모두 현재 가방에 담을 수 있는 최대 무게보다 같거나 작다 => PQ에 있는 보석들은 모두 현재 가방에 들어갈 수 있는 보석들
 *      -> 그러므로, 현재 가방에 들어갈 수 있는 최대 가치를 가진 값은 PQ의 최상단 값
 */
public class Prob1202 {

    static int n;
    static int k;

    static List<Gem> gems = new ArrayList<>();
    static int[] bags;

    static PriorityQueue<Integer> queue = new PriorityQueue<>(Collections.reverseOrder());
    static long ans = 0L;

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        /**
         * 보석의 무게에 따라 오름차순으로 정렬
         */
        for(int i = 0; i < n; i++){
            st = new StringTokenizer(br.readLine());
            int curW = Integer.parseInt(st.nextToken());
            int curV = Integer.parseInt(st.nextToken());

            gems.add(new Gem(curW, curV));
        }
        Collections.sort(gems, new Comparator<Gem>(){
            @Override
            public int compare(Gem g1, Gem g2) {
                if (g1.weight == g2.weight) {
                    return g2.value - g1.value;
                }

                return g1.weight - g2.weight;
            }
        });

        /**
         * 가방에 담을 수 있는 최대 무게에 따라 오름차순으로 가방 정렬
         */
        bags = new int[k];
        for(int i = 0; i < k; i++){
            bags[i] = Integer.parseInt(br.readLine());
        }
        Arrays.sort(bags);

        int gemIdx = 0;
        for (int i = 0; i < k; i++) {
            if(gemIdx < n){
                /**
                 * 현재 가방에 담을 수 있는 보석들을 찾아서 queue 에 저장
                 */
                while(gems.get(gemIdx).weight <= bags[i]){
                    queue.offer(gems.get(gemIdx).value);
                    ++gemIdx;
                    if (gemIdx >= n) {
                        break;
                    }
                }
            }

            /**
             * queue 에서 가치가 가장 높은 값을 가방에 추가
             */
            if(!queue.isEmpty()){
                ans += queue.poll();
            }
        }


        System.out.println(ans);
    }

    public static class Gem{
        int weight;
        int value;

        public Gem(int weight, int value){
            this.weight = weight;
            this.value = value;
        }

        @Override
        public String toString(){
            return "Gem = {weight="+ weight + ", value = " + value +"}";
        }
    }
}