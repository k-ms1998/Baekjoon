package Silver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Silver 3
 */
public class Prob16165 {

    static Map<String, List<String>> byGroup = new HashMap<>();
    static Map<String, String> byName = new HashMap<>();
    static StringBuilder ans = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] conditions = br.readLine().split(" ");
        int n = Integer.valueOf(conditions[0]);
        int m = Integer.valueOf(conditions[1]);

        for (int i = 0; i < n; i++) {
            String group = br.readLine();
            List<String> namesInGroup = new ArrayList<>();
            int t = Integer.valueOf(br.readLine());
            for (int j = 0; j < t; j++) {
                String name = br.readLine();
                namesInGroup.add(name);
                byName.put(name, group);
            }
            byGroup.put(group, namesInGroup);
        }

        for (int i = 0; i < m; i++) {
            String quiz = br.readLine();
            int type = Integer.valueOf(br.readLine());
            if (type == 0) {
                List<String> allNamesInGroup = byGroup.get(quiz);
                Collections.sort(allNamesInGroup);
                for (String s : allNamesInGroup) {
                    ans.append(s + "\n");
                }
            } else {
                String associatedGroup = byName.get(quiz);
                ans.append(associatedGroup + "\n");
            }
        }

        System.out.println(ans);
    }
}
