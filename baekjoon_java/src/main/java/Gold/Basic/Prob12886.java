package Gold.Basic;
import java.io.*;
import java.util.*;

/**
 * Gold 4(돌 그룹)
 *
 * https://www.acmicpc.net/problem/12886
 *
 * Solution: DFS
 * 
 * a + b + c는 항상 일정함을 이용해서 3차원 배열이 아닌 2차원 배열로 지금까지 확인한 조함을 확인할 수 있음 -> 메모리 초과 해결
 */
public class Prob12886 {

    static boolean[][] visited;
    static boolean answer = false;

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        int a = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());
        int size = a + b + c;
        visited = new boolean[size + 1][size + 1];

        findAnswer(a, b, c);

        System.out.println(answer ? 1 : 0);
    }

    public static void findAnswer(int a, int b, int c){
        if(answer){
            return;
        }
        if(a == b && b == c){
            answer = true;

            return;
        }
        if(visited[a][b]){
            return;
        }

        visited[a][b] = true;
        if(a != b){
            int min = Math.min(a, b);
            int max = Math.max(a, b);
            findAnswer(min + min, max - min, c);
        }
        if(a != c){
            int min = Math.min(a, c);
            int max = Math.max(a, c);
            findAnswer(min + min, b, max - min);
        }
        if(b != c){
            int min = Math.min(b, c);
            int max = Math.max(b, c);
            findAnswer(a, min + min, max - min);
        }

    }
}
