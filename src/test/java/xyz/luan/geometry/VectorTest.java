package xyz.luan.geometry;

import org.junit.Assert;
import org.junit.Test;

public class VectorTest {

    @Test
    public void testNoramlize() {
        Point vector = new Point(2, 0);
        Point normalized = vector.normalizeTo();
        Assert.assertEquals(normalized.x, 1, Point.E);
        Assert.assertEquals(normalized.y, 0, Point.E);
    }
}
