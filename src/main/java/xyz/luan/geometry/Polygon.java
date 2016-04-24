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
import xyz.luan.geometry.de.lighti.clipper.Path;

@Data
@EqualsAndHashCode(callSuper = false)
public class Polygon extends ShapeBase {

    private List<Point> points;

    // caches
    private transient Point center;
    private transient Rectangle bounds;
    private transient Path path;
    private transient double area;

    public Polygon(List<Point> points) {
        this.points = points;

        this.invalidate();
    }

    public final void invalidate() {
        this.bounds = this.recalculateBounds();
        this.path = new Path(this);
	this.center = this.bounds.getCenter();
        this.area = this.recalculateArea();
    }

    public Polygon(Point... points) {
        this(Arrays.asList(points));
    }

    public Polygon(Rectangle r) {
	Point p = r.getPoint();
	double w = r.getWidth(), h = r.getHeight();

        Point c1 = new Point(p, w, 0);
        Point c2 = new Point(p, w, h);
        Point c4 = new Point(p, 0, h);

        this.points = Arrays.asList(p, c1, c2, c4);
	this.bounds = r;
        this.path = new Path(this);
        this.center = new Point(p.x + w/2, p.y + h/2);
        this.area = w * h;
    }

    public Rectangle getBounds() {
        return this.bounds;
    }

    public Path toPath() {
        return path;
    }

    public Rectangle recalculateBounds() {
        double minx, maxx, miny, maxy;
        minx = maxx = points.get(0).getX();
        miny = maxy = points.get(0).getY();
        for (int i = 1; i < points.size(); i++) {
            Point p = points.get(i);
            if (p.getX() > maxx) {
                maxx = p.getX();
            } else if (p.getX() < minx) {
                minx = p.getX();
            }

            if (p.getY() > maxy) {
                maxy = p.getY();
            } else if (p.getY() < miny) {
                miny = p.getY();
            }
        }

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
        return this.area;
    }

    public double recalculateArea() {
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
        return this.center;
    }

    @Override
    public void translate(Point vector) {
        points.forEach(p -> p.translate(vector));
        bounds.translate(vector);
        center.translate(vector);
    }

    public void rotate(double angle) {
        for (Point p : this.points) {
            p.rotate(this.center, angle);
        }
        this.bounds = this.recalculateBounds();
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
