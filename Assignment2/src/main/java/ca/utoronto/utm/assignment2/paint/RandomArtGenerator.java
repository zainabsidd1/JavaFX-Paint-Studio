package ca.utoronto.utm.assignment2.paint;

import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class RandomArtGenerator {
    public enum ColorMode { ANY, TINTS_OF_CURRENT }
    public enum FillMode  { FILL, STROKE, BOTH }

    private RandomArtGenerator() {}

    /** Generate N shapes fully inside [0..W]×[0..H]. */
    public static List<Shape> generate(
            int count, double W, double H,
            Long seedOrNull,
            double minSize, double maxSize,
            boolean allowOverlap,
            ColorMode colorMode,
            FillMode fillMode,
            Color currentColor,
            double strokeWidth
    )
    {
        Random rng = (seedOrNull == null) ? new Random() : new Random(seedOrNull);
        List<Shape> out = new ArrayList<>(count);
        List<double[]> bboxes = new ArrayList<>();

        String[] types = {"Circle","Rectangle","Square","Oval","Polyline","Squiggle","Triangle"};

        for (int i=0;i<count;i++) {
            String type = types[rng.nextInt(types.length)];
            double s = lerp(minSize, maxSize, rng.nextDouble());
            double w = s, h = s;

            if (type.equals("Rectangle") || type.equals("Oval")) {
                double aspect = 0.6 + rng.nextDouble()*0.8;
                if (rng.nextBoolean()) h = s*aspect; else w = s*aspect;
            } else if (type.equals("Polyline") || type.equals("Squiggle") || type.equals("Triangle")) {
                // use a local bbox to contain all points
                w = s; h = s;
            }

            double x = rng.nextDouble()*Math.max(1, W - w);
            double y = rng.nextDouble()*Math.max(1, H - h);

            if (!allowOverlap) {
                int attempts = 0;
                while (intersectsAny(x,y,w,h,bboxes) && attempts < 12) {
                    x = rng.nextDouble()*Math.max(1, W - w);
                    y = rng.nextDouble()*Math.max(1, H - h);
                    attempts++;
                }
            }

            Color color = switch (colorMode) {
                case ANY -> randomHSV(rng);
                case TINTS_OF_CURRENT -> tintOf(currentColor, rng);
            };
            Shape shape;
            switch (type) {
                case "Circle" -> {
                    double r = Math.min(w, h)/2.0;
                    shape = makeCircle(x+r, y+r, r, color, fillMode, strokeWidth);
                    bboxes.add(new double[]{x,y,2*r,2*r});
                }
                case "Rectangle" -> {
                    shape = makeRectangle(x, y, w, h, color, fillMode, strokeWidth);
                    bboxes.add(new double[]{x,y,w,h});
                }
                case "Square" -> {
                    double sSide = Math.min(w,h);
                    shape = makeSquare(x, y, sSide, color, fillMode, strokeWidth);
                    bboxes.add(new double[]{x,y,sSide,sSide});
                }
                case "Oval" -> {
                    shape = makeOval(x, y, w, h, color, fillMode, strokeWidth);
                    bboxes.add(new double[]{x,y,w,h});
                }
                case "Polyline" -> {
                    shape = makePolyline(x, y, w, h, color, strokeWidth, rng);
                    bboxes.add(new double[]{x,y,w,h});
                }
                case "Squiggle" -> {
                    shape = makeSquiggle(x, y, w, h, color, strokeWidth, rng);
                    bboxes.add(new double[]{x,y,w,h});
                }
                case "Triangle" -> {
                    shape = makeTriangle(x, y, w, h, color, fillMode, strokeWidth, rng);
                    bboxes.add(new double[]{x,y,w,h});
                }
                default -> throw new IllegalStateException("Unknown type: "+type);
            }
            out.add(shape);
        }
        return out;
    }

    // shape builders for AI
    private static Shape makeCircle(double cx, double cy, double r, Color c, FillMode fm, double sw) {
        Circle circle = new Circle(new Point(cx,cy), r, c);
        circle.setStrokeWidth(sw);
        applyFillMode(circle, c, fm);
        return circle;
    }

    private static Shape makeRectangle(double x, double y, double w, double h, Color c, FillMode fm, double sw) {
        Rectangle rect = new Rectangle(new Point(x,y), new Point(x+w,y+h), c);
        rect.setStrokeWidth(sw);
        applyFillMode(rect, c, fm);
        return rect;
    }

    private static Shape makeSquare(double x, double y, double s, Color c, FillMode fm, double sw) {
        Square sq = new Square(new Point(x,y), new Point(x+s,y+s), c);
        sq.setStrokeWidth(sw);
        applyFillMode(sq, c, fm);
        return sq;
    }

    private static Shape makeOval(double x, double y, double w, double h, Color c, FillMode fm, double sw) {
        // your Oval has (Point p1, Point p2, Color color, boolean filled)
        boolean filled = (fm != FillMode.STROKE);
        Oval ov = new Oval(new Point(x,y), new Point(x+w,y+h), c, filled);
        ov.setStrokeWidth(sw);
        if (filled) ov.setFillColor(c);
        return ov;
    }

    private static Shape makePolyline(double x, double y, double w, double h, Color c, double sw, Random rng) {
        Polyline pl = new Polyline(c);
        int n = 3 + rng.nextInt(6);
        for (int i=0;i<n;i++) pl.addPoint(new Point(x + rng.nextDouble()*w, y + rng.nextDouble()*h));
        pl.setStrokeWidth(sw);
        return pl;
    }

    private static Shape makeSquiggle(double x, double y, double w, double h, Color c, double sw, Random rng) {
        Squiggle sq = new Squiggle();
        sq.setColor(c);
        int n = 12 + rng.nextInt(18);
        double px = x + rng.nextDouble()*w;
        double py = y + rng.nextDouble()*h;
        sq.addPoint(new Point(px,py));
        for (int i=1;i<n;i++) {
            px = clamp(px + (rng.nextDouble()-0.5)*w*0.25, x, x+w);
            py = clamp(py + (rng.nextDouble()-0.5)*h*0.25, y, y+h);
            sq.addPoint(new Point(px,py));
        }
        sq.setStrokeWidth(sw);
        return sq;
    }

    private static Shape makeTriangle(double x, double y, double w, double h, Color c, FillMode fm, double sw, Random rng) {
        Triangle t = new Triangle();
        t.addVertex(new Point(x + rng.nextDouble()*w, y + rng.nextDouble()*h));
        t.addVertex(new Point(x + rng.nextDouble()*w, y + rng.nextDouble()*h));
        t.addVertex(new Point(x + rng.nextDouble()*w, y + rng.nextDouble()*h));
        t.setStrokeWidth(sw);
        if (fm == FillMode.STROKE) {
            t.setColor(c);
            t.setFilled(false);
        } else {
            t.applyFill(c); // sets fill + stroke + filled=true in your class
        }
        return t;
    }

    private static void applyFillMode(Fillable f, Color c, FillMode fm) {
        if (fm == FillMode.STROKE) {
            if (f instanceof Colorable col) col.setColor(c);
            f.setFilled(false);
        } else if (fm == FillMode.FILL) {
            f.applyFill(c);
        } else { // BOTH
            f.applyFill(c);
            if (f instanceof Colorable col) col.setColor(c);
        }
    }

    // ----- helpers -----
    private static boolean intersectsAny(double x,double y,double w,double h,List<double[]> bb) {
        for (double[] r : bb) {
            if (x < r[0] + r[2] && x + w > r[0] && y < r[1] + r[3] && y + h > r[1]) {
                return true;
            }
        }
        return false;
    }
    private static double lerp(double a,double b,double t){ return a + (b-a)*t; }
    private static Color randomHSV(Random rng) {
        double h = rng.nextDouble()*360.0;
        double s = 0.35 + rng.nextDouble()*0.6;
        double v = 0.30 + rng.nextDouble()*0.65;
        return Color.hsb(h, s, v);
    }
    private static Color tintOf(Color base, Random rng) {
        if (base == null) return randomHSV(rng);
        double h = base.getHue();
        double s = base.getSaturation();
        double v = base.getBrightness();
        h = (h + (rng.nextDouble()*10 - 5) + 360) % 360;
        s = clamp(s + (rng.nextDouble()*0.3 - 0.15), 0, 1);
        v = clamp(v + (rng.nextDouble()*0.4 - 0.2), 0, 1);
        return Color.hsb(h, s, v);
    }
    private static double clamp(double v,double lo,double hi){
        return Math.max(lo, Math.min(hi, v));
    }
}
