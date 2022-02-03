package hello.core.solution;


public class Solution {
    public static void main(String[] args) {
        String s = "try hello world";
        String[] split = s.split("", -1);
        for (int i = 0; i < split.length; i++) {
            String[] chars = split[i].split("");
            for (int j = 0; j < chars.length; j++) {
                chars[j] = j % 2 == 0 ? chars[j].toUpperCase() : chars[j].toLowerCase();
            }
            split[i] = String.join("", chars);
        }

    }
}
