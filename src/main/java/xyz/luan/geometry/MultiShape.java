package xyz.luan.geometry;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.canvas.GraphicsContext;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class MultiShape extends ShapeBase {

    /*
     * Attention: cannot hold intersecting shapes, because the area() method
     * doesn't check for that. This should be only used internally. There is no
     * reason to export a MultiShape allowing overlapping shapes, just work with
     * Shape directly.
     */
    private List<Shape> nonOverlappingShapes;

    private MultiShape(List<Shape> shapes) {
        this.nonOverlappingShapes = shapes;
    }

    /**
     * The shapes provided must not overlap!
     * 
     * @param nonOverlappingShapes
     *            a list of shapes that do not overlap
     * @return a Shape representing the union of all the shapes
     */
    public static MultiShape buildNoCheck(List<Shape> nonOverlappingShapes) {
        return new MultiShape(nonOverlappingShapes);
    }

    public static MultiShape build(List<Shape> shapes) {
        for (int i = 0; i < shapes.size(); i++) {
            for (int j = i + 1; j < shapes.size(); j++) {
                shapes.set(i, shapes.get(i).diff(shapes.get(j)));
            }
        }
        return new MultiShape(shapes);
    }

    @Override
    public double area() {
        return nonOverlappingShapes.stream().mapToDouble(s -> s.area()).sum();
    }

    @Override
    public void draw(GraphicsContext g) {
        nonOverlappingShapes.forEach(s -> s.draw(g));
    }

    @Override
    public void fill(GraphicsContext g) {
        nonOverlappingShapes.forEach(s -> s.fill(g));
    }

    @Override
    public void translate(Point vector) {
        nonOverlappingShapes.forEach(s -> s.translate(vector));
    }

    @Override
    public Rectangle getBounds() {
        double minx = nonOverlappingShapes.stream().mapToDouble(s -> s.getBounds().getX()).min().getAsDouble();
        double miny = nonOverlappingShapes.stream().mapToDouble(s -> s.getBounds().getY()).min().getAsDouble();
        double maxx = nonOverlappingShapes.stream().mapToDouble(s -> s.getBounds().getX() + s.getBounds().getWidth())
                .max().getAsDouble();
        double maxy = nonOverlappingShapes.stream().mapToDouble(s -> s.getBounds().getY() + s.getBounds().getHeight())
                .max().getAsDouble();

        return new Rectangle(minx, maxx, miny, maxy);
    }

    @Override
    public Shape op(Shape shape, OpType type) {
        switch (type) {
        case DIFFERENCE:
            return new MultiShape(nonOverlappingShapes.stream().map(s -> s.diff(shape)).collect(Collectors.toList()));
        case INTERSECTION:
            return nonOverlappingShapes.stream().reduce(shape, (s, t) -> s.intersection(t));
        case UNION: {
            List<Shape> unsafe = nonOverlappingShapes.stream().map(s -> s.union(shape)).collect(Collectors.toList());
            return MultiShape.build(unsafe);
        }
        case XOR: {
            List<Shape> unsafe = nonOverlappingShapes.stream().map(s -> s.xor(shape)).collect(Collectors.toList());
            return MultiShape.build(unsafe);
        }
        default:
            throw new RuntimeException("Unknown OpType");
        }
    }

    @Override
    public List<Point> intersections(Line line) {
        return nonOverlappingShapes.stream().map(s -> s.intersections(line)).reduce(new ArrayList<>(), (list, curr) -> {
            list.addAll(curr);
            return list;
        });
    }

    @Override
    public List<Line> sides() {
        List<Line> sides = nonOverlappingShapes.stream().map(s -> s.sides()).reduce(new ArrayList<>(), (list, curr) -> {
            list.addAll(curr);
            return list;
        });
        return uniq(sides);
    }

    private <T> List<T> uniq(List<T> sides) {
        return new ArrayList<>(new HashSet<>(sides));
    }
}
