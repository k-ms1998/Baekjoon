package Gold.Graph;

import java.io.*;
import java.util.*;

/**
 * Gold 3(나무 제테크)
 *
 * https://www.acmicpc.net/problem/16235
 *
 * Solution: BFS + 구현
 */
public class Prob16235 {

    static int n, m, k;
    static int[][] grid;
    static int[][] tmpGrid;
    static int[][] robot;
    static Deque<Tree> trees;

    static int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
    static int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        grid = new int[n + 1][n + 1];
        robot = new int[n + 1][n + 1];
        trees = new ArrayDeque<>();
        for (int y = 1; y < n + 1; y++) {
            st = new StringTokenizer(br.readLine());
            for (int x = 1; x < n + 1; x++) {
                grid[y][x] = 5;
                robot[y][x] = Integer.parseInt(st.nextToken());
            }
        }

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());

            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int z = Integer.parseInt(st.nextToken());

            trees.offer(new Tree(x, y, z));
        }

        for (int i = 0; i < k; i++) {
            updateTrees();
            addNutrients();
        }

        System.out.println(trees.size());
    }

    public static void updateTrees(){
        Stack<Tree> stack = new Stack<>();

        int treeSize = trees.size();
        tmpGrid = new int[n + 1][n + 1];
        while (treeSize > 0) {
            treeSize--;
            Tree tree = trees.poll();
            int x = tree.x;
            int y = tree.y;
            long age = tree.age;

            /**
             * 봄 + 여름
             * 1. 현재 나무의 나이가 땅의 양분보다 같거나 작으면 나이만큼 양분을 흡수하고 성장 -> 봄
             * 2. 현재 나무의 나이가 땅의 양분보다 크면 죽음 -> 여름
             *  -> 봄이 지난 이후에 나무가 죽기 때문에, 나무가 죽으면 땅의 양분이 증가하는 부분은 나중에 해줘야 함
             *      안그러면, 같은 위치에 있는 다른 나무들이 봄일때 영향을 받기 때문에
             *          -> 같은 위치에 있는 다른 나무들도 봄에 죽고 여름에 양분을 줘야되는데, 앞서 같은 위치의 다른 나무의 양분을 미리 땅에 추가하면 현재 나무가 양분이 충분해져서 죽지 않을수도 있음
             *          그러므로, tmpGrid 에 나무가 죽음으로써 양분이 추가된 양을 저장하고, 나중에 겨울에 양분이 추가될때 같이 양분을 추가해줌
             */
            if (age <= grid[x][y]) {
                grid[x][y] -= age;
                trees.offer(new Tree(x, y, age + 1));
                ++age;
            } else {
                tmpGrid[x][y] += (age / 2);
                continue;
            }

            /**
             * 가을
             * 인접한 8개의 칸으로 나무가 번식함
             * 번식할때, 임의의 스텍에 추가해줌
             * 이유는, 같은 위치에 나무가 여러개가 있으면 나이가 가장 적은 나무가 먼저 양분을 먹어야하는데, 바로 trees에 넣게 되면 양분이 적은 순으로 나무가 추가되는 것을 보장 못함
             * 그러므로, 스텍에 새로운 나무들을 임시로 넣고, 나중에 trees 의 앞에 추가
             */
            if (age % 5 == 0) {
                for (int j = 0; j < 8; j++) {
                    int nx = x + dx[j];
                    int ny = y + dy[j];

                    if (nx <= 0 || ny <= 0 || nx > n || ny > n) {
                        continue;
                    }

                    stack.push(new Tree(nx, ny, 1));
                }
            }
        }

        /**
         * 번식에서 새로 생긴 나무들을 trees의 앞에 추가
         */
        while (!stack.isEmpty()) {
            trees.offerFirst(stack.pop());
        }
    }

    private static void addNutrients() {
        for (int y = 1; y < n + 1; y++) {
            for (int x = 1; x < n + 1; x++) {
                /**
                 * 로봇이 추가해주는 양분 + 나무가 죽어서 추가되는 양분
                 */
                grid[x][y] += (robot[x][y] + tmpGrid[x][y]);
            }
        }
    }

    public static class Tree{
        int x;
        int y;
        long age;

        public Tree(int x, int y, long age) {
            this.x = x;
            this.y = y;
            this.age = age;
        }
    }
}