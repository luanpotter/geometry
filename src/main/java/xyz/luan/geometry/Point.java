package xyz.luan.geometry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Point {

    public static final double E = Math.pow(10, -8);

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

    public double magnitude() {
        return Math.sqrt(x * x + y * y);
    }

    public Point vectorTo(Point p) {
        return p.translateTo(this.scaleTo(-1));
    }

    public Point normalizeTo() {
        double mag = this.magnitude();
        if (mag == 0) {
            return new Point(this);
        }
        double signal = this.x == 0 ? (this.y < 0 ? -1 : +1) : (this.x < 0 ? -1 : +1);
        return this.scaleTo(signal / mag);
    }

    public Point normal() {
        double mag = this.magnitude();
        double y = Math.sqrt(mag * Math.pow(this.x / this.y, 2) - 1);
        double x = Math.sqrt(mag - Math.pow(y, 2));
        return new Point(x, y);
    }

    public double getAngularCoefficient() {
        return this.y / this.x;
    }

    public double crossTo(Point p) {
        return this.x * p.y - this.y * p.x;
    }

    public Point plusTo(Point p) {
        return translateTo(p);
    }

    public Point minusTo(Point p) {
        return translateTo(p.scaleTo(-1));
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final long temp1 = Double.doubleToLongBits(this.x);
        final long temp2 = Double.doubleToLongBits(this.y);
        result = (result * PRIME) + (int) (temp1 ^ (temp1 >>> 32));
        result = (result * PRIME) + (int) (temp2 ^ (temp2 >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Point)) {
            return false;
        }
        Point other = (Point) o;
        if (Math.abs(this.x - other.x) > E) {
            return false;
        }
        if (Math.abs(this.y - other.y) > E) {
            return false;
        }
        return true;
    }
}