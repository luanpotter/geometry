package xyz.luan.geometry.de.lighti.clipper;

import java.util.List;
import java.util.stream.Collectors;

import lombok.experimental.UtilityClass;
import xyz.luan.geometry.EmptyShape;
import xyz.luan.geometry.MultiShape;
import xyz.luan.geometry.OpType;
import xyz.luan.geometry.Point;
import xyz.luan.geometry.Polygon;
import xyz.luan.geometry.Shape;
import xyz.luan.geometry.de.lighti.clipper.Clipper.PolyType;

@UtilityClass
public class PolygonClipperHelper {

    public Shape clip(Polygon thiz, Polygon that, OpType type) {
        Clipper clip = new DefaultClipper();
        clip.addPath(thiz.toPath(), PolyType.SUBJECT, true);
        clip.addPath(that.toPath(), PolyType.CLIP, true);
        Paths paths = new Paths();
        clip.execute(type, paths);
        if (paths.isEmpty()) {
            return new EmptyShape();
        }
        List<Shape> polys = paths.stream().map(p -> toShape(p)).collect(Collectors.toList());
        if (polys.size() == 1) {
            return polys.get(0);
        }
        return MultiShape.buildNoCheck(polys);
    }

    private Shape toShape(Path p) {
        if (p.isEmpty()) {
            return new EmptyShape();
        }
        return p.getPolygon();
    }


}
