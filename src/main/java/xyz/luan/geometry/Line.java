package xyz.luan.geometry;

public class Line {

    private Point direction, origin;

    private Line(Point direction, Point origin) {
        this.direction = direction;
        this.origin = origin;
    }

    public static Line fromDirection(Point direction, Point origin) {
        return new Line(direction.normalize(), origin);
    }

    public static Line fromPoints(Point p1, Point p2) {
        return fromDirection(p1.vectorTo(p2), p1);
    }

    public Point getDirection() {
        return direction;
    }

    public Point getOrigin() {
        return origin;
    }

    public Line normalOn(Point p) {
        return new Line(direction.normal(), p);
    }

    public boolean isVertical() {
        return direction.equals(new Point(0, 1));
    }

    public boolean isHorizontal() {
        return direction.equals(new Point(1, 0));
    }

    public double getM() {
        return getAngularCoefficient();
    }

    public double getAngularCoefficient() {
        return direction.getAngularCoefficient();
    }
}
