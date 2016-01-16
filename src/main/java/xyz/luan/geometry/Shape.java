package xyz.luan.geometry;

import java.util.List;

import javafx.scene.canvas.GraphicsContext;

public interface Shape {

    public double area();

    public Rectangle getBounds();

    public void draw(GraphicsContext g);

    public void fill(GraphicsContext g);

    public void translate(Point vector);

    public Shape intersection(Shape shape);

    public List<Point> intersections(Line line);

    public List<Line> sides();

    public Shape diff(Shape shape);

    public Shape xor(Shape shape);

    public Shape union(Shape shape);
}
