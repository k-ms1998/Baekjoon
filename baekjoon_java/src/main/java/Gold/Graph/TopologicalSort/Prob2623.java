package Gold.Graph.TopologicalSort;

import java.io.*;
import java.util.*;

/**
 * Gold 3(음악 프로그램)
 *
 * https://www.acmicpc.net/problem/2623
 *
 * Solution: Topological Sort
 *
 * 우선순위를 정해주는 문제이므로, 위상 정렬(Topological Sort) 으로 풀이
 * 1. 각 노드들에 대해서 간선과 진입 차수 입력
 *  1-1. 우선 순위가 높은 노드 -> 우선 순위가 낮은 노드 으로 되는 간선 (ex: 1이 2보다 앞서 있어야 되면, 1->2로 가는 간선 추가)
 *  1-2. 우선 순위가 낮은 노드의 진입 차수 증가 (ex: 1이 2보다 앞서 있어야 되면, 1->2로 가는 간선 추가 후 2의 진입 차수 증가)
 * 2. 큐에 진입 차수가 0인 값들 추가
 * 3. 큐에 있는 모든 노드들을 확인 하면서, 해당 노드에서 나가는 간선들을 확인
 * 4. 해당 간선들을 relax (-> 인접한 노드의 진입 차수 감소)
 * 5. 간서 relax 후, 인접한 노드의 진입 차수가 0이면 큐에 추가
 */
public class Prob2623 {

    static int n;
    static int m;

    static List<Integer>[] order;
    static int[] inCount;

    static Deque<Integer> queue = new ArrayDeque<>();

    static StringBuilder ans = new StringBuilder();

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        order = new List[n + 1];
        inCount = new int[n + 1];
        for(int i = 1; i < n + 1; i++){
            order[i] =  new ArrayList<>();
        }

        for(int i = 0; i < m; i++){
            st = new StringTokenizer(br.readLine());
            int tmpSize = Integer.parseInt(st.nextToken());
            int prev = Integer.parseInt(st.nextToken());
            for(int j = 0; j < tmpSize - 1; j++){
                int cur = Integer.parseInt(st.nextToken());

                /**
                 * prev 가 cur 보다 먼저 와야됨 
                 * => prev 의 우선순위가 더 높음
                 *  => prev -> cur 간선 추가
                 *  => cur 의 집입 차수 증가
                 */
                order[prev].add(cur);
                inCount[cur]++;
                prev = cur;
            }
        }
        for(int i = 1; i < n + 1; i++){
            if(inCount[i] == 0){
                queue.offer(i);
            }
        }

        int cnt = queue.size();
        while(!queue.isEmpty()){
            int cur = queue.poll();
            ans.append(cur).append("\n");

            /**
             * 간선들 relax 해주기
             */
            List<Integer> outs = order[cur];
            for(int adj : outs){
                --inCount[adj];
                if(inCount[adj] == 0){
                    ++cnt;
                    queue.offer(adj);
                }
            }
            order[cur] = new ArrayList<>();
        }

        /**
         * cnt 는 자리 배치가 완료된 노드들
         * -> cnt == n 이면 총 n개의 자리 배치가 끝남 -> 순서가 정해졌음
         * -> cnt != n 이면 모든 n개의 노드에 대해서 자리 배치가 끝나지 않음 -> 순서가 정해지지 않은 노드들 존재
         */
        if(cnt == n){
            System.out.println(ans);
        }else{
            System.out.println(0);
        }

    }
}