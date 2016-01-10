package xyz.luan.geometry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.scene.canvas.GraphicsContext;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.luan.geometry.de.lighti.clipper.PolygonClipperHelper;

@Data
@EqualsAndHashCode(callSuper = false)
public class Polygon extends ShapeBase {

    private List<Point> points;

    public Polygon(List<Point> points) {
        this.points = points;
    }

    public Polygon(Point... points) {
        this(Arrays.asList(points));
    }

    public Rectangle getBounds() {
        double minx = points.stream().mapToDouble(p -> p.getX()).min().getAsDouble();
        double miny = points.stream().mapToDouble(p -> p.getY()).min().getAsDouble();
        double maxx = points.stream().mapToDouble(p -> p.getX()).max().getAsDouble();
        double maxy = points.stream().mapToDouble(p -> p.getY()).max().getAsDouble();

        return new Rectangle(minx, maxx, miny, maxy);
    }

    @Override
    public Shape op(Shape that, OpType type) {
        if (that instanceof Polygon) {
            return op((Polygon) that, type);
        }
        if (that instanceof Rectangle) {
            return op(((Rectangle) that).toPolygon(), type);
        }
        if (that instanceof EmptyShape || that instanceof MultiShape) {
            return ((ShapeBase) that).op(this, type);
        }
        throw new RuntimeException("Unknown shape type...");
    }

    private Shape op(Polygon that, OpType type) {
        if (type == OpType.INTERSECTION) {
            if (!getBounds().overlaps(that.getBounds())) {
                return new EmptyShape();
            }
        }
        return PolygonClipperHelper.clip(this, that, type);
    }

    @Override
    public double area() {
        double sum = 0;
        int last = points.size() - 1;
        for (int i = 0; i < last; i++) {
            sum += points.get(i).getX() * points.get(i + 1).getY() - points.get(i).getY() * points.get(i + 1).getX();
        }
        sum += points.get(last).getX() * points.get(0).getY() - points.get(last).getY() * points.get(0).getX();

        return Math.abs(sum) / 2d;
    }

    @Override
    public void draw(GraphicsContext g) {
        double[] xs = points.stream().mapToDouble(p -> p.getX()).toArray();
        double[] ys = points.stream().mapToDouble(p -> p.getY()).toArray();
        g.strokePolygon(xs, ys, points.size());
    }

    @Override
    public void fill(GraphicsContext g) {
        double[] xs = points.stream().mapToDouble(p -> p.getX()).toArray();
        double[] ys = points.stream().mapToDouble(p -> p.getY()).toArray();
        g.fillPolygon(xs, ys, points.size());
    }

    public Point center() {
        return getBounds().getCenter();
    }

    @Override
    public void translate(Point vector) {
        points.forEach(p -> p.translate(vector));
    }

    public List<Point> intersections(Line line) {
        Set<Point> intersections = new HashSet<>();
        for (Line side : sides()) {
            try {
                intersections.add(side.intersection(line));
            } catch (ParallelLinesException ex) {
                // ignore
            }
        }
        return new ArrayList<>(intersections);
    }

    public List<Line> sides() {
        List<Line> sides = new ArrayList<>();
        for (int i = 0; i < points.size(); i++) {
            int next = (i + 1) % points.size();
            sides.add(Line.fromPoints(points.get(i), points.get(next)));
        }
        return sides;
    }
}
