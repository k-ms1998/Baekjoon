package Gold.Greedy;

import java.io.*;
import java.util.*;

/**
 * Gold 2(책 나눠주기)
 * 
 * https://www.acmicpc.net/problem/9576
 * 
 * Solution: 정렬 + 그리디
 */
public class Prob9576 {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    static int n, m;
    static boolean[] books;

    static StringBuilder ans = new StringBuilder();

    public static void main(String[] args) throws IOException {

        int t = Integer.parseInt(br.readLine());
        while(t-- > 0){
            st = new StringTokenizer(br.readLine());
            n = Integer.parseInt(st.nextToken());
            m = Integer.parseInt(st.nextToken());
            books = new boolean[n + 1];
            solve();
        }

        System.out.println(ans);
    }

    public static void solve() throws IOException{
        /**
         * 1. 입력받은 a~b를 b가 오름차순이 되도록 정렬. 만약, b가 같으면 a가 오름차순이 되도록 정렬
         * 2. 이렇게 한후, 각 a~b에 대해서, a부터 시작해서 b까지 해당 책을 나눠줄 수 있는지 확인. 나눠줄수 있으면 책을 나눠주고 다음 학생 확인
         * ex:
         * 1 4
         * 2 5
         * 4 4
         * 2 4 가 주어 졌을때:
         * -> 정렬 이후:
         * 1 4
         * 2 4
         * 4 4
         * 2 5
         * 이때, 첫 번째부터 b를 기준으로 먼저 오름차순을 했기 때문에, (2 5)는 앞에 나온 데이터들이 뭐든 상관없이 무조건 책을 나눠줄 수 있음
         * Because, 앞에 어떤 데이터들이 나와도, 5번 책은 나눠줄 수 없는 상태이므로, 1~4 책들 모두 나눠준 상태더라도  (2 5)에세는 무조건 적어도 5번 책은 주어줄 수 있음
         * (그렇기 때문에 b를 오름차순으로 정렬)
         *
         * (1 4), (2 4), (4 4)에 대해서는, b가 같아서 a에 대해서 오름차순으로 해줬으므로, 앞에서 부터 순서대로 책을 나눠줄 수 있다.
         *
         *
         */
        PriorityQueue<Student> queue = new PriorityQueue<>(new Comparator<Student>(){
            @Override
            public int compare(Student s1, Student s2){
                if (s1.b == s2.b) {
                    return s1.a - s2.a;
                }

                return s1.b - s2.b;
            }
        });
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            queue.offer(new Student(a, b));
        }

        int ansCnt = 0;
        while (!queue.isEmpty()) {
            Student cur = queue.poll();
            int a = cur.a;
            int b = cur.b;

            for (int i = a; i <= b; i++) {
                if (!books[i]) {
                    books[i] = true;
                    ansCnt++;
                    break;
                }
            }
        }

        ans.append(ansCnt).append("\n");
    }

    public static class Student{
        int a;
        int b;

        public Student(int a, int b) {
            this.a = a;
            this.b = b;
        }
    }
}
/*
1
9 4
1 9
1 8
7 9
8 8
 */
