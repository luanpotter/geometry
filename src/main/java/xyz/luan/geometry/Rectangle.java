package xyz.luan.geometry;

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
        Point c1 = new Point(point, width, 0);
        Point c2 = new Point(point, width, height);
        Point c4 = new Point(point, 0, height);
        return new Polygon(point, c1, c2, c4);
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
        return getX() < r.getX() + r.width && getX() + width > r.getX() && getY() < r.getY() + r.height && getY() + height > r.getY();
    }

    public Point getCenter() {
        return point.translateTo(new Point(width / 2, height / 2));
    }

    public boolean contains(Point p) {
        return contains(p.x, p.y);
    }

    public boolean contains(double x, double y) {
        return x >= this.point.x && x <= this.point.x + this.width && y >= this.point.y && y <= this.point.y + this.height;
    }
}
