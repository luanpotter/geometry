package xyz.luan.geometry;

import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class Rectangle extends ShapeBase {

    private Point point;
    private double width, height;

    public Rectangle(double startx, double endx, double starty, double endy) {
        this(new Point(startx, starty), endx - startx, endy - starty);
    }

    public double getX() {
        return point.getX();
    }

    public double getY() {
        return point.getY();
    }

    public Polygon toPolygon() {
        return new Polygon(this);
    }

    @Override
    public double area() {
        return width * height;
    }

    @Override
    public void draw(GraphicsContext g) {
        g.rect(point.getX(), point.getY(), width, height);
    }

    @Override
    public void fill(GraphicsContext g) {
        g.fillRect(point.getX(), point.getY(), width, height);
    }

    @Override
    public void translate(Point vector) {
        point.translate(vector);
    }

    @Override
    public Rectangle getBounds() {
        return this;
    }

    @Override
    public Shape op(Shape shape, OpType type) {
        return toPolygon().op(shape, type);
    }

    boolean overlaps(Rectangle r) {
        return getX() < r.getX() + r.width && getX() + width > r.getX() && getY() < r.getY() + r.height
                && getY() + height > r.getY();
    }

    public Point getCenter() {
        return point.translateTo(new Point(width / 2, height / 2));
    }

    public boolean contains(Point p) {
        return contains(p.x, p.y);
    }

    public boolean contains(double x, double y) {
        double px = this.point.x;
        double py = this.point.y;
        return x >= px && x <= px + this.width && y >= py && y <= py + this.height;
    }

    @Override
    public List<Point> intersections(Line line) {
        return toPolygon().intersections(line);
    }

    @Override
    public List<Line> sides() {
        return toPolygon().sides();
    }
}
