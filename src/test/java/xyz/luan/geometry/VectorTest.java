package xyz.luan.geometry;

import org.junit.Assert;
import org.junit.Test;

public class VectorTest {

    private static final double E = Math.pow(10, -4);

    @Test
    public void testNoramlize() {
        Point vector = new Point(2, 0);
        Point normalized = vector.normalize();
        Assert.assertEquals(normalized.x, 1, E);
        Assert.assertEquals(normalized.y, 0, E);
    }
}
