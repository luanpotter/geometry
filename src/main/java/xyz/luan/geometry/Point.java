package xyz.luan.geometry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Point {
    public double x, y;

    public Point(Point p) {
        set(p);
    }

    public Point(Point p, double dx, double dy) {
        this(p);
        translate(new Point(dx, dy));
    }

    public void set(Point p) {
        this.x = p.x;
        this.y = p.y;
    }

    public void scale(double m) {
        this.x *= m;
        this.y *= m;
    }

    public Point scaleTo(double m) {
        Point copy = new Point(this);
        copy.scale(m);
        return copy;
    }

    public void translate(Point vector) {
        this.x += vector.x;
        this.y += vector.y;
    }

    public Point translateTo(Point vector) {
        Point copy = new Point(this);
        copy.translate(vector);
        return copy;
    }

    public void rotate(double angle) {
        double nx = x * Math.cos(angle) - y * Math.sin(angle);
        double ny = x * Math.sin(angle) + y * Math.cos(angle);

        this.x = nx;
        this.y = ny;
    }

    public void rotate(Point center, double angle) {
        translate(center.scaleTo(-1));
        rotate(angle);
        translate(center);
    }

    public Point rotateTo(double angle) {
        Point copy = new Point(this);
        copy.rotate(angle);
        return copy;
    }

    public Point rotateTo(Point center, double angle) {
        Point copy = new Point(this);
        copy.rotate(center, angle);
        return copy;
    }

    public double angle(Point other) {
        return Math.atan2(other.y, other.x) - Math.atan2(y, x);
    }

    public double magnitue() {
        return Math.sqrt(x * x + y * y);
    }
}