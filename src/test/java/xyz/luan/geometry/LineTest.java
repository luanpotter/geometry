package xyz.luan.geometry;

import org.junit.Assert;
import org.junit.Test;

public class LineTest {

    @Test
    public void testEquals() {
        Line l1 = Line.fromDirection(new Point(1, 1), new Point(0, 0));
        Line l2 = Line.fromDirection(new Point(1.5, 1.5), new Point(9, 9));
        Assert.assertTrue(l1.equals(l2));
    }

    @Test
    public void linesIntersect1() {
        Line horizontal = Line.fromDirection(new Point(1, 0), new Point());
        Line vertical = Line.fromDirection(new Point(0, 1), new Point());
        Assert.assertEquals(new Point(), vertical.intersection(horizontal));
    }

    @Test
    public void linesIntersect2() {
        Line horizontal = Line.fromDirection(new Point(1, 1), new Point(2, 1));
        Line vertical = Line.fromDirection(new Point(-1, 1), new Point(2, -1));
        Assert.assertEquals(new Point(1, 0), vertical.intersection(horizontal));
    }

    @Test(expected = ParallelLinesException.class)
    public void linesIntersectNoIntersection() {
        Line l1 = Line.fromDirection(new Point(1, 0), new Point());
        Line l2 = Line.fromDirection(new Point(2, 0), new Point());

        l1.intersection(l2);
    }
}
