package xyz.luan.geometry;

import org.junit.Assert;
import org.junit.Test;

public class IntersectionTest {

    @Test
    public void testEmptyShapeIntersectionRect() {
        Rectangle rect = new Rectangle(new Point(), 20, 50);
        EmptyShape empty = new EmptyShape();

        Shape withRectangle = empty.intersection(rect);
        Assert.assertTrue(withRectangle instanceof EmptyShape);

        withRectangle = rect.intersection(empty);
        Assert.assertTrue(withRectangle instanceof EmptyShape);
    }

    @Test
    public void testEmptyShapeIntersectionPoly() {
        Polygon rect = new Rectangle(new Point(), 20, 50).toPolygon();
        EmptyShape empty = new EmptyShape();

        Shape withRectangle = empty.intersection(rect);
        Assert.assertTrue(withRectangle instanceof EmptyShape);

        withRectangle = rect.intersection(empty);
        Assert.assertTrue(withRectangle instanceof EmptyShape);
    }

    // TODO more tests
}
