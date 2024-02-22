package Review.Gold.Bruteforce;

import java.io.*;
import java.util.*;

/**
 * 1. 각 구역이 1~N번 번호가 매겨져 있다
 * 2. 구역을 두 개의 선거구로 나눠야 한다
 * 	2-0. 같은 구역 안에 있는 선거구 들은 모두 연결되어 있어야 한다
 * 	2-1. 모든 노드에 대해서 시작 노드로 누고 탐색을 시작한다
 * 	2-2. 현재 노드에서 인접한 노드로 이동
 * 	2-3. 현재 선거구에 들어 있는 구역의 수가 1개에서 N-1개 될때까지 탐색
 * 	2-4. 원하는 숫자만큼 구역을 추가했으면, 추가되지 않은 구역들이 연결되어 있는지 확인
 * 3. 선거구을 나눴을때 두 선거구의 인구의 차리를 최소화한다
 * 4. 노드들을 2개의 선서구로 나눌 수 있는 모든 경우의 수 구하기
 * 	4-0. 하나의 선거구만 정해지면 나머지 선거구도 자동으로 정해짐
 * 	4-1. 각 선거구에 대해서 모두 연결되어 있는지 확인
 * 		4-1-1. DFS로 확인 -> 하나의 노드에서 시작해서 방문 체크
 * 		4-1-2. 이때, T자로 연결되어 있는 노드들도 있기 때문에 방문 -> 방문 체크 해제하지 않는다
 * 5. 두개의 선거구 모두 연결되어 있으면 각 선거구의 인구수를 구한다
 */
public class Prob17471 {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    static int size;
    static int[] population;
    static List<Integer>[] edges;
    static boolean[] checked;
    static int visitedBit;
    static int totalPopulation = 0;
    static int populationA = 0;

    static final int INF = 100_000_000;

    public static void main(String[] args) throws IOException{
        size = Integer.parseInt(br.readLine());

        population = new int[size];
        edges = new List[size];
        checked = new boolean[(1 << size)];
        st = new StringTokenizer(br.readLine());
        for(int idx = 0; idx < size; idx++) {
            population[idx] = Integer.parseInt(st.nextToken());
            totalPopulation += population[idx];

            edges[idx] = new ArrayList<>();
        }

        for(int idx = 0; idx < size; idx++) {
            st = new StringTokenizer(br.readLine());
            int adjCount = Integer.parseInt(st.nextToken());
            for(int count = 0; count < adjCount; count++) {
                int nextIdx = Integer.parseInt(st.nextToken()) - 1;
                edges[idx].add(nextIdx);
                edges[nextIdx].add(idx);
            }
        }

        int answer = INF;
        int allIncludedBit = (1 << size) - 1;
        for(int bit = 0; bit <= allIncludedBit; bit++) { // 모든 경우의 수 구하기(비트마스킹)
            int reverseBit = allIncludedBit^bit;
            if(checked[bit] || checked[reverseBit]) {
                continue;
            }

            checked[bit] = true;
            checked[reverseBit] = true;

            boolean validA = false;
            for(int idx = 0; idx < size; idx++) {
                if((bit & (1 << idx)) == (1 << idx)) { // 시작 노드 정하기
                    visitedBit = (1 << idx);
                    populationA = population[idx];
                    checkValid(idx, bit, true);
                    if(visitedBit == bit) {
                        validA = true;
                    }

                    break;
                }
            }
            if(!validA) {
                continue;
            }

            boolean validB = false;
            for(int idx = 0; idx < size; idx++) {
                if((reverseBit & (1 << idx)) == (1 << idx)) { // 시작 노드 정하기
                    visitedBit = (1 << idx);
                    checkValid(idx, reverseBit, false);
                    if(visitedBit == reverseBit) {
                        validB = true;
                    }

                    break;
                }
            }
            if(validA && validB) { // 두 개의 선거구 모두 모든 구역들이 연결되어 있음
                int populationB = totalPopulation - populationA; // 선거구 B의 인구수 = 전체 인구수 - 선거구 A의 인구수

                answer = Math.min(answer, Math.abs(populationA - populationB));
            }

            checked[bit] = true;
            checked[reverseBit] = true;
        }

        System.out.println(answer == INF ? -1 : answer);

    }

    public static boolean checkValid(int node, int target, boolean isA) {
        if(visitedBit == target) {
            return true;
        }

        for(int next : edges[node]) {
            int nextBit = (1 << next);
            if(((visitedBit & nextBit) != nextBit)
                    && (target & nextBit) == nextBit) { // 다음 노드를 확인한 상태가 아니면서 현재 선거구에 포함되어 있는 지역인 경우
                visitedBit |= (1 << next);
                if(isA) {
                    populationA += population[next];
                }
                if(checkValid(next, target, isA)) {
                    return true;
                }
            }
        }

        return false;
    }

}
