package xyz.luan.geometry;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class PolygonTest {

    @Test
    public void testGetSides() {
        Polygon square = new Polygon(new Point(), new Point(1, 0), new Point(1, 1), new Point(0, 1));
        List<Line> sides = square.sides();
        Assert.assertEquals(4, sides.size());
        Assert.assertEquals(2, sides.stream().filter(s -> s.isVertical()).count());
        Assert.assertEquals(2, sides.stream().filter(s -> s.isHorizontal()).count());
    }

    @Test
    public void testIntersections() {
        Polygon square = new Polygon(new Point(), new Point(1, 0), new Point(1, 1), new Point(0, 1));
        Line line = Line.fromDirection(new Point(1, 1), new Point());
        List<Point> pts = square.intersections(line);
        Assert.assertEquals(1, pts.stream().filter(pt -> pt.equals(new Point())).count());
        Assert.assertEquals(1, pts.stream().filter(pt -> pt.equals(new Point(1, 1))).count());
    }

}
