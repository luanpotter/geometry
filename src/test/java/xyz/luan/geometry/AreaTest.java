package xyz.luan.geometry;

import org.junit.Assert;
import org.junit.Test;

public class AreaTest {

    @Test
    public void testEmptyShapeArea() {
        Assert.assertEquals(0, new EmptyShape().area(), 0);
    }

    @Test
    public void testRectangleArea() {
        Shape rectangle = new Rectangle(new Point(), 25, 30);
        Assert.assertEquals(750, rectangle.area(), 0);
        rectangle.translate(new Point(10, 10));
        Assert.assertEquals(750, rectangle.area(), 0);
        rectangle.translate(new Point(100, -20));
        Assert.assertEquals(750, rectangle.area(), 0);
    }

    @Test
    public void testPolygonAreaRect() {
        Shape rectangle = new Polygon(new Point(0, 0), new Point(25, 0), new Point(25, 30), new Point(0, 30));
        Assert.assertEquals(750, rectangle.area(), 0);
    }

    @Test
    public void testPolygonAreaTriangle() {
        Shape rectangle = new Polygon(new Point(0, 0), new Point(30, 0), new Point(0, 40));
        Assert.assertEquals(600, rectangle.area(), 0);
    }
}
