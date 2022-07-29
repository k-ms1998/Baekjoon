package Gold.Backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Gold 5
 *
 * 문제
 * 바로 어제 최백준 조교가 방 열쇠를 주머니에 넣은 채 깜빡하고 서울로 가 버리는 황당한 상황에 직면한 조교들은, 702호에 새로운 보안 시스템을 설치하기로 하였다.
 * 이 보안 시스템은 열쇠가 아닌 암호로 동작하게 되어 있는 시스템이다.
 * 암호는 서로 다른 L개의 알파벳 소문자들로 구성되며 최소 한 개의 모음(a, e, i, o, u)과 최소 두 개의 자음으로 구성되어 있다고 알려져 있다.
 * 또한 정렬된 문자열을 선호하는 조교들의 성향으로 미루어 보아 암호를 이루는 알파벳이 암호에서 증가하는 순서로 배열되었을 것이라고 추측된다. 즉, abc는 가능성이 있는 암호이지만 bac는 그렇지 않다.
 * 새 보안 시스템에서 조교들이 암호로 사용했을 법한 문자의 종류는 C가지가 있다고 한다.
 * 이 알파벳을 입수한 민식, 영식 형제는 조교들의 방에 침투하기 위해 암호를 추측해 보려고 한다. C개의 문자들이 모두 주어졌을 때, 가능성 있는 암호들을 모두 구하는 프로그램을 작성하시오.
 *
 * 입력
 * 첫째 줄에 두 정수 L, C가 주어진다. (3 ≤ L ≤ C ≤ 15) 다음 줄에는 C개의 문자들이 공백으로 구분되어 주어진다. 주어지는 문자들은 알파벳 소문자이며, 중복되는 것은 없다.
 *
 * 출력
 * 각 줄에 하나씩, 사전식으로 가능성 있는 암호를 모두 출력한다.
 * 
 * Solution: Backtracking + DFS
 * 1. 암호가 오름차순이어야 하기 때문에 입력 받은 앞파벳들을 오름차순으로 정렬
 * 2. DFS 로 가능한 모든 암호 조합
 * 3. 암호가 조건들을 만족하는지 확인 => 자음 갯수 1개 이상 && 모음 갯수 2개 이상
 */
public class Prob1759 {

    static int l;
    static int c;
    static String[] alpha;
    static List<String> combinations = new ArrayList<>();
    static StringBuilder ans = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] conditions = br.readLine().split(" ");

        l = Integer.valueOf(conditions[0]);
        c = Integer.valueOf(conditions[1]);

        /**
         * 1. 알파벳 정렬
         */
        alpha = br.readLine().split(" ");
        Arrays.sort(alpha);

        backtrack(0, "");
        System.out.println(ans);
    }

    /**
     * 2. DFS 로 가능한 모든 암호 조합
     * @param idx
     * @param password
     */
    public static void backtrack(int idx, String password) {

        if (password.length() == l) {
            /**
             * 3. 암호가 조건들을 만족하는지 확인 => 자음 갯수 1개 이상 && 모음 갯수 2개 이상
             */
            int vowelsCnt = 0;
            int consonantsCnt = 0;

            String[] passwordArr = password.split("");
            for (String s : passwordArr) {
                if (s.equals("a") || s.equals("e") || s.equals("i") || s.equals("o") || s.equals("u")) {
                    vowelsCnt++;
                } else {
                    consonantsCnt++;
                }
            }

            if (vowelsCnt >= 1 && consonantsCnt >= 2) {
                if (!combinations.contains(password)) {
                    combinations.add(password);
                    ans.append(password+"\n");
                }
            }

            return;
        }

        /**
         * ex: 정렬한 앞파벳 : a c i s t w 일때:
         * a
         * ac
         * aci
         * acis
         * acit
         * aciw
         * -> return
         * acs
         * acst
         * actw
         * -> return
         * act
         * actw
         * ->return
         * ai
         * aist
         * aisw
         * .....
         */
        for (int i = idx; i < c; i++) {
            backtrack(++idx, password + alpha[i]);
        }
    }
}
