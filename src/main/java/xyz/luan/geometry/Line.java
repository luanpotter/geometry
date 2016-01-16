package xyz.luan.geometry;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Line {

    /**
     * This is a unique way to represent a Line. The direction is normalized and
     * the origin is either y-intersection if any or x-intersection. With this
     * design, checking for equality is trivial.
     */
    private Point direction, origin;

    private Line(Point direction, Point origin) {
        this.direction = direction;
        this.origin = origin;
    }

    public static Line fromDirection(Point direction, Point origin) {
        Line nonNormalLine = new Line(direction.normalizeTo(), origin);
        Point newOrigin;
        try {
            newOrigin = yAxis().intersection(nonNormalLine);
        } catch (ParallelLinesException ex) {
            newOrigin = xAxis().intersection(nonNormalLine);
        }
        return new Line(nonNormalLine.direction, newOrigin);
    }

    public static Line yAxis() {
        return new Line(new Point(0, 1), new Point());
    }

    public static Line xAxis() {
        return new Line(new Point(1, 0), new Point());
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

    public Point intersection(Line l) {
        if (this.direction.equals(l.getDirection())) {
            throw new ParallelLinesException();
        }
        double t = l.origin.minusTo(this.origin).crossTo(l.direction) / this.direction.crossTo(l.direction);
        return this.origin.plusTo(this.direction.scaleTo(t));
    }

    public boolean contains(Point p) {
        return this.origin.minusTo(p).normal().equals(this.direction, Math.pow(10, -2));
    }

    @Override
    public String toString() {
        return "{ dir: " + direction + "; orig: " + origin + "}";
    }
}
