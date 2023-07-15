package Gold.Graph;

import java.io.*;
import java.util.*;

/**
 * Gold 4(게리맨더링)
 *
 * https://www.acmicpc.net/problem/17471
 *
 * Solution: BruteForce + BFS + BitMasking
 * 1. 구역들을 2개의 선거구로 나눌 수 있는 모든 경우의 수 찾기
 * 2. 각 경우의 수에 대해서 조건들을 만족하는지 확인
 *  -> 각 선거구에 있는 모든 구역들을 하나의 노드에서 시작 했을때 모두 도달 가능해야함
 *  -> 모든 구역들을 지날때, 중간에 다른 선거구의 구역을 지나면 안됨
 * 3. 두 선거가 모두 2번의 조건들을 만족하면, 각 선거구의 인구수 확인
 */
public class Prob17471 {

    static int n;
    static int[] population;
    static List<Integer>[] edges;
    static int answer = 0;
    static final int INF = 1000000007;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());

        population = new int[n + 1];
        st = new StringTokenizer(br.readLine());
        for(int i = 1; i < n + 1; i++){
            population[i] = Integer.parseInt(st.nextToken());
        }

        edges = new List[n + 1];
        for(int i = 0; i < n + 1; i++){
            edges[i] = new ArrayList<>();
        }
        for(int i = 1; i < n + 1; i++){
            st = new StringTokenizer(br.readLine());
            int num = Integer.parseInt(st.nextToken());
            for(int j = 0; j < num; j++){
                edges[i].add(Integer.parseInt(st.nextToken()));
            }
        }

        answer = INF;
        for (int i = 1; i <= n / 2; i++) {
            findGroup(0, i, 0, 1);
        }

        System.out.println(answer == INF ? -1 : answer);
    }

    public static void findGroup(int depth, int target, int visited, int idx) {
        if(depth >= target){
            int groupA = visited;
            int groupB = 0;
            for(int i = 1; i < n + 1; i++){
                if((visited & (1 << i)) != (1 << i)){
                    groupB = groupB | (1 << i); // A 선거구에 있는 구역이 아니면 모두 B 선거구에 있다고 간주하면 됨
                }
            }

            boolean checkA = checkGroup(groupA, groupB);
            boolean checkB = checkGroup(groupB, groupA);
            if(checkA && checkB){
                int populationA = 0;
                int populationB = 0;


                for(int i = 1; i < n + 1; i++){
                    if((groupA & (1 << i)) == (1 << i)){
                        populationA += population[i];
                    }
                    if((groupB & (1 << i)) == (1 << i)){
                        populationB += population[i];
                    }
                }

                answer = Math.min(answer, Math.abs(populationA - populationB));
            }

            return;
        }

        for(int i = idx; i < n + 1; i++){
            if((visited & (1 << i)) != (1 << i)){
                findGroup(depth + 1, target, visited | (1 << i), i + 1);
            }
        }
    }

    public static boolean checkGroup(int include, int exclude) {
        int start = 0;
        for(int i = 1; i < n + 1; i++){
            if((include & (1 << i)) == (1 << i)){
                start = i;
                break;
            }
        }

        int visited = (1 << start);
        Deque<Integer> queue = new ArrayDeque<>();
        queue.offer(start);
        while(!queue.isEmpty()){
            int node = queue.poll();

            for(int next : edges[node]){
                if((exclude & (1 << next)) != (1 << next)){
                    if ((visited & (1 << next)) != (1 << next)) {
                        visited = visited | (1 << next);
                        queue.offer(next);
                    }
                }
            }
        }

        for(int i = 1; i < n + 1; i++){
            if((include & (1 << i)) == (1 << i)){
                if((visited & (1 << i)) != (1 << i)){
                    return false;
                }
            }
        }
        return true;
    }
}
