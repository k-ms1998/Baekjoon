package Bronze;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Bronze 1
 */
public class Prob1236 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String condition = br.readLine();
        String[] conditionSplit = condition.split(" ");
        int n = Integer.valueOf(conditionSplit[0]);
        int m = Integer.valueOf(conditionSplit[1]);

        String[][] grid = new String[n][m];
        for (int i = 0; i < n; i++) {
            String row = br.readLine();
            String[] rowSplit = row.split("");
            for (int j = 0; j < m; j++) {
                grid[i][j] = rowSplit[j];
            }
        }

        int ans = 0;

        int rowCount = 0;
        for (int i = 0; i < n; i++) {
            boolean flag = false;
            for (int j = 0; j < m; j++) {
                if (grid[i][j].equals("X")) {
                    flag = true;
                }
            }

            if (!flag) {
                rowCount++;
            }
        }

        int colCount = 0;
        for (int i = 0; i < m; i++) {
            boolean flag = false;
            for (int j = 0; j < n; j++) {
                if (grid[j][i].equals("X")) {
                    flag = true;
                }
            }

            if (!flag) {
                colCount++;
            }
        }

        ans = rowCount > colCount ? rowCount : colCount;
        System.out.println(ans);
    }
}
