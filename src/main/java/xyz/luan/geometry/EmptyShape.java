package xyz.luan.geometry;

import java.util.Collections;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;

public class EmptyShape extends ShapeBase {

    @Override
    public double area() {
        return 0;
    }

    @Override
    public void draw(GraphicsContext g) {
    }

    @Override
    public void fill(GraphicsContext g) {
    }

    @Override
    public void translate(Point vector) {
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(0, 0, 0, 0);
    }

    @Override
    protected Shape op(Shape shape, OpType type) {
        if (type == OpType.XOR || type == OpType.UNION) {
            return shape;
        } else if (type == OpType.DIFFERENCE || type == OpType.INTERSECTION) {
            return this;
        }
        throw new RuntimeException("Unknown operation");
    }

    @Override
    public List<Point> intersections(Line line) {
        return Collections.emptyList();
    }

    @Override
    public List<Line> sides() {
        return Collections.emptyList();
    }
}
