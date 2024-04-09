import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;

public class RandomGeneratorTest {

    @Test
    public void testGeradorAula() {
        int[] got = randomGenerator.ExemploAula(10);
        int[] want = {7, 5, 6, 1, 8, 0, 4, 2, 3, 7};

        assertArrayEquals(want, got);
    }
}
