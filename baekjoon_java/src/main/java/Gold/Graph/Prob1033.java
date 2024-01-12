import java.io.*;
import java.util.*;

/**
 * 1. 칵테일의 재료의 개수 입력: N (1 <= N <= 10)
 * 2. 재료 쌍의 비율을 입력:  N-1줄
 *  2-1. a b p q 입력 => a번 재료랑 b번 재료의 질량을 나눈 값 = p/q
 *      2-1-1. p > q이면 a가 b보다 더 많이 필요함 => b->a 간선 생성
 *      2-1-2. p < q이면 b가 a보다 더 많이 필요함 => a->b 간선 생성
 * 3. 필요한 재료의 질량을 모두 더한 값을 출력
 */
public class Main {
    
    static List<Edge>[] edges;
    static Ratio[] ratios;
    static int N;
    
    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        
        // 1. 칵테일의 재료의 개수 입력
        N = Integer.parseInt(br.readLine());
        edges = new List[N];
        ratios = new Ratio[N];
        for(int idx = 0; idx < N; idx++){
            edges[idx] = new ArrayList<>();
        }
        
        // 2. 재료 쌍의 비율을 입력
        for(int pair = 0; pair < N -1; pair++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int p = Integer.parseInt(st.nextToken());
            int q = Integer.parseInt(st.nextToken());
            
            long gcd = getGCD(p, q);
            p /= gcd;
            q /= gcd;
                        
            edges[a].add(new Edge(b, p, q)); // b = a * (q/p);
            edges[b].add(new Edge(a, q, p)); // a = b * (p/q);
        }

        // 임의로 한 노드에서 시작 -> 해당 노드에서 다른 모든 노드들까지 도달해서 비율 계산하기
        boolean[] v = new boolean[N];
        Deque<Edge> queue = new ArrayDeque<>();
        queue.offer(new Edge(0, 1, 1));
        ratios[0] = new Ratio(1, 1);
        while(!queue.isEmpty()){
            Edge edge = queue.poll();
            int node = edge.next;
            long curP = edge.p;
            long curQ = edge.q;
            
            for(Edge nextEdge : edges[node]){
                int next = nextEdge.next;
                long nextP = nextEdge.p;
                long nextQ = nextEdge.q;
                
                if(!v[next]){
                    v[next] = true;
                    Ratio ratio = new Ratio(curP * nextP, curQ * nextQ);
                    ratios[next] = ratio;
                    queue.offer(new Edge(next, ratio.p, ratio.q));
                }
            }
        }
        
        // 각 재료에 대한 비율을 약분하기
        for(int idx = 0; idx < N; idx++){
            long gcd = getGCD(ratios[idx].p, ratios[idx].q);
            ratios[idx].p /= gcd;
            ratios[idx].q /= gcd;
            // System.out.println(ratios[i]);
        }
        
        //모든 분모들의 최소공배수 구하기
        long commonD = findCommonD();
        
        // 필요한 i번째 재료의 질량 구하기
        for(int idx = 0; idx < N; idx++){
            long curAmount = commonD / ratios[idx].p * ratios[idx].q;
            sb.append(curAmount).append(" ");
        }
    
        System.out.println(sb);
    }
    
    /*
    모든 분모들의 최소공배수 구하기
    
    - 숫자 a랑 b의 최소공배수 구하기:
        1. a랑 b의 최대공약수 구하기 -> gcd
        2. a랑 b의 최소공배수 = gcd * (a/gcd) * (b/gcd);
    - 0~n-1번까지의 있을때, 0~n-1까지의 최소공배수 구하기:
        1. 0번과 1번의 최소공배수 구하기
        2. 1에서 구한 값과 2번의 최소공배수 구하기
        3. 2에서 구한 값과 3번의 최소공배수 구하기
        ...
        n-1. n-2에서 구한 값과 n-1번의 최소공배수 구하기
    */
    public static long findCommonD(){
        long commonD = ratios[0].p;
        for(int i = 1; i < N; i++){
            long curD = ratios[i].p;
            long gcd = getGCD(curD, commonD);
            
            long tmp = gcd * (commonD/gcd) * (curD/gcd);
            commonD = tmp;
        }
        
        return commonD;
    }
    
    /* 
    두개의 숫자에서 최대공약수 구하기
    */
    static long getGCD(long a, long b) {
        if (b == 0) {
            return a;
        }
        return getGCD(b, a % b);
    }
    
    public static class Edge{
        int next;
        long p;
        long q;
        
        public Edge(int next, long p, long q){
            this.next = next;
            this.p = p;
            this.q = q;
        }
        
        @Override
        public String toString(){
            return "{next=" + next + ", p=" + p + ", q=" + q + "}";
        }
    }
    
    public static class Ratio{
        long p;
        long q;
        
        public Ratio(long p, long q){
            this.p = p;
            this.q = q;
        }
        
        @Override
        public String toString(){
            return "[p=" + p + ", q=" + q + "]"; 
        }
    }
}
/*
10
4 0 1 1
4 1 3 1
4 2 5 1
4 3 7 1
3 5 1 9
3 6 8 2
5 7 2 1
7 8 5 8
5 9 1 6

420 140 84 60 420 540 15 270 432 3240 
*/