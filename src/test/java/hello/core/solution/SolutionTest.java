package hello.core.solution;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class SolutionTest {

    private final Solution solution = new Solution();

    @Test
    public void test() {
//        Assertions.assertArrayEquals(new int[] {2,4,6,8,10}, solution.solution(2, 5));
        solution.solution(2500, 10, 2500);
    }
}
