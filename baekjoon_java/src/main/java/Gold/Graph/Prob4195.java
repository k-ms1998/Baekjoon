package Gold.Graph;

import java.util.*;

/**
 * Gold 2
 *
 * 문제
 * 민혁이는 소셜 네트워크 사이트에서 친구를 만드는 것을 좋아하는 친구이다. 우표를 모으는 취미가 있듯이, 민혁이는 소셜 네트워크 사이트에서 친구를 모으는 것이 취미이다.
 * 어떤 사이트의 친구 관계가 생긴 순서대로 주어졌을 때, 두 사람의 친구 네트워크에 몇 명이 있는지 구하는 프로그램을 작성하시오.
 * 친구 네트워크란 친구 관계만으로 이동할 수 있는 사이를 말한다.
 *
 * 입력
 * 첫째 줄에 테스트 케이스의 개수가 주어진다. 각 테스트 케이스의 첫째 줄에는 친구 관계의 수 F가 주어지며, 이 값은 100,000을 넘지 않는다.
 * 다음 F개의 줄에는 친구 관계가 생긴 순서대로 주어진다.
 * 친구 관계는 두 사용자의 아이디로 이루어져 있으며, 알파벳 대문자 또는 소문자로만 이루어진 길이 20 이하의 문자열이다.
 *
 * 출력
 * 친구 관계가 생길 때마다, 두 사람의 친구 네트워크에 몇 명이 있는지 구하는 프로그램을 작성하시오.
 *
 * Input:
 * 2
 * 3
 * Fred Barney
 * Barney Betty
 * Betty Wilma
 * 3
 * Fred Barney
 * Betty Wilma
 * Barney Betty
 *
 * Output:
 * 2
 * 3
 * 4
 * 2
 * 2
 * 4
 */
public class Prob4195 {

    public void solve() {
        Scanner sc = new Scanner(System.in);

        int t = sc.nextInt();
        for (int i = 0; i < t; i++) {
            Map<String, String> parent = new HashMap<>();
            Map<String, Integer> networks = new HashMap<>();
            int f = sc.nextInt();
            for (int j = 0; j < f; j++) {
                String k = sc.next();
                String v = sc.next();

                // 네크워크에 처음으로 추가됐을때 초기화
                if (!parent.containsKey(k)) {
                    parent.put(k, k);
                    networks.put(k, 1);
                }
                if (!parent.containsKey(v)) {
                    parent.put(v, v);
                    networks.put(v, 1);
                }

                // Find -> 노드의 Parent 찾기
                String tmpK = k;
                String parentK;
                while (true) {
                    parentK = parent.get(k);
                    if (parentK.equals(k)) {
                        break;
                    }
                    k = parentK;
                }
                parent.put(tmpK, parentK);

                String tmpV = v;
                String parentV;
                while (true) {
                    parentV = parent.get(v);
                    if (parentV.equals(v)) {
                        break;
                    }
                    v = parentV;
                }
                parent.put(tmpV, parentV);

                // Union -> 서로 다른 트리일때 하나로 병합
                Integer kNetworkCnt = networks.get(parentK);
                Integer vNetworkCnt = networks.get(parentV);
                if (parentK.equals(parentV)) {
                    networks.put(k, kNetworkCnt);
                    System.out.println(kNetworkCnt);
                } else {
                    parent.put(v, parentK);
                    networks.put(k, kNetworkCnt + vNetworkCnt);
                    System.out.println(kNetworkCnt + vNetworkCnt);
                }
                /**
                 * Input:
                 * 1
                 * 3
                 * A B
                 * B C
                 * C A
                 *
                 * Output:
                 * 2
                 * 3
                 * 3
                 */
            }
        }
    }

    /**
     * HashSet 으로 사용자마다 네트워크에 있는 사용자들을 담고, 추가될때마다 업데이트 시키기
     * But, 이런 경우 사용자는 다르지만 같은 네트워크에 있을 경우, 중복으로 유지 됨 -> 입력 값이 많아지면 메모리 초과 발생
     * ex) A -> B && B -> C 순서로 추가를 할때, A->{A,B,C}, B->{A,B,C}, C->{A,B,C} 로 모든 사용자들이 하나의 네트워크에 있게되면, Set에서 Value 값으로 모두 같은 값을 갖게 됨
     * -> 그러므로, 같은 네트워크에 있게 되면, 값을 중복으로 유지하지 않게 함으로써 메모리 초과 문제 해결 -> Union-Find
     */
    public void solve_deprecated() {
        Scanner sc = new Scanner(System.in);

        int t = sc.nextInt();
        for (int i = 0; i < t; i++) {
            Map<String, Set<String>> friends = new HashMap<>();
            int f = sc.nextInt();
            for (int j = 0; j < f; j++) {
                String k = sc.next();
                String v = sc.next();

                Set<String> friendListK = new HashSet<>();
                if (friends.containsKey(k)) {
                    friendListK = friends.get(k);
                }
                friendListK.add(v);

                Set<String> friendListV = new HashSet<>();
                if (friends.containsKey(v)) {
                    friendListV = friends.get(v);
                }
                friendListV.add(k);

                friendListK.addAll(friendListV);
                friendListV.addAll(friendListK);

                friends.put(k, friendListK);
                friends.put(v, friendListV);

                System.out.println(friendListK.size());
            }
        }
    }
}
