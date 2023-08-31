package Gold.Graph;

import java.io.*;
import java.util.*;

/**
 * Gold 4(숨바꼭질 4)
 *
 * https://www.acmicpc.net/problem/13913
 *
 * Solution:
 * (0 <= n, k <= 100000) -> 재귀로 풀이 X
 * 
 * 시간 단축으로 위해 dist[x]가 업데이트될때 마다 dist[x]의 직전 위치를 prev[x]에 저장
 */
public class Prob13913 {

    static int n, k;
    static int[] dist;
    static int[] prev;
    static final int INF = 100000000;
    static Stack<Integer> path = new Stack<>();


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        int size = Math.max(n, k) + 2;
        dist = new int[size];
        prev = new int[size];
        for(int i = 0; i < size; i++){
            dist[i] = INF;
        }

        dist[n] = 0;
        prev[n] = -1;
        Deque<Info> queue = new ArrayDeque<>();
        queue.offer(new Info(n, 0));
        while(!queue.isEmpty()){
            Info info = queue.poll();
            int x = info.x;
            int c = info.c;

            if(x == k){
                break;
            }

            int nx1 = x - 1;
            int nx2 = x + 1;
            int nx3 = 2 * x;

            if(nx1 >= 0){
                if(dist[nx1] > c + 1){
                    dist[nx1] = c + 1;
                    prev[nx1] = x;
                    queue.offer(new Info(nx1, c + 1));
                }
            }
            if(x < k){
                if(nx2 < size){
                    if(dist[nx2] > c + 1){
                        dist[nx2] = c + 1;
                        prev[nx2] = x;
                        queue.offer(new Info(nx2, c + 1));
                    }
                }
                if(nx3 < size){
                    if(dist[nx3] > c + 1){
                        dist[nx3] = c + 1;
                        prev[nx3] = x;
                        queue.offer(new Info(nx3, c + 1));

                    }
                }
            }
        }

        StringBuilder answer = new StringBuilder();
        answer.append(dist[k]).append("\n");
        findPath(k);
        while(!path.isEmpty()){
            answer.append(path.pop()).append(" ");
        }

        System.out.println(answer);
    }

    public static void findPath(int node) {
        if(node == n){
            path.push(node);
            return;
        }

        path.push(node);
        findPath(prev[node]);
    }

    public static class Info{
        int x;
        int c;

        public Info(int x, int c){
            this.x = x;
            this.c = c;
        }
    }

}
