package ca.utoronto.utm.assignment2.paint;

import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;

public final class SceneryLibrary {
    private SceneryLibrary() {}

    /** Scene 1: Beach Sunset */
    public static List<Shape> beachSunset(double W, double H) {
        List<Shape> s = new ArrayList<>();

        // Sky
        s.add(new Rectangle(new Point(0,0), new Point(W, H*0.6), Color.web("#87CEEB")));

        // Sea
        s.add(new Rectangle(new Point(0, H*0.6), new Point(W, H), Color.web("#1E90FF")));

        // Sun
        double sunR = Math.min(W,H)*0.08;
        s.add(new Circle(new Point(W*0.75, H*0.25), sunR, Color.web("#FFD166")));

        // Clouds
        var cloud = (java.util.function.BiConsumer<Double,Double>) (cx, cy) -> {
            s.add(new Oval(new Point(cx-50, cy-20), new Point(cx+50, cy+15), Color.web("#F5F7FA"), true));
            s.add(new Oval(new Point(cx-90, cy-10), new Point(cx-10, cy+20), Color.web("#F5F7FA"), true));
            s.add(new Oval(new Point(cx+10, cy-15), new Point(cx+90, cy+18), Color.web("#F5F7FA"), true));
        };
        cloud.accept(W*0.25, H*0.18);
        cloud.accept(W*0.55, H*0.14);
        cloud.accept(W*0.68, H*0.28);

        // Beach
        s.add(new Rectangle(new Point(0, H*0.8), new Point(W, H), Color.web("#E2C275")));

        // Beach ball with stripes
        double ballR = Math.min(W,H)*0.075;
        double ballCX = W*0.38, ballCY = H*0.90;
        s.add(new Circle(new Point(ballCX, ballCY), ballR, Color.web("#FFFFFF")));
        Polyline stripe1 = new Polyline(Color.web("#F44336"));
        stripe1.addPoint(new Point(ballCX - ballR, ballCY));
        stripe1.addPoint(new Point(ballCX + ballR, ballCY));
        Strokeable st4 = (Strokeable) stripe1;
        st4.setStrokeWidth(3);
        s.add(stripe1);
        Polyline stripe2 = new Polyline(Color.web("#2196F3"));
        stripe2.addPoint(new Point(ballCX, ballCY - ballR));
        stripe2.addPoint(new Point(ballCX, ballCY + ballR));
        Strokeable st5 = (Strokeable) stripe2;
        st5.setStrokeWidth(3);
        s.add(stripe2);

        // Birds
        Polyline b1 = new Polyline(Color.web("#333333"));
        b1.addPoint(new Point(W*0.20, H*0.18)); b1.addPoint(new Point(W*0.23, H*0.16)); b1.addPoint(new Point(W*0.26, H*0.18)); s.add(b1);
        Polyline b2 = new Polyline(Color.web("#333333"));
        b2.addPoint(new Point(W*0.30, H*0.22)); b2.addPoint(new Point(W*0.33, H*0.20)); b2.addPoint(new Point(W*0.36, H*0.22)); s.add(b2);

        // Seashells + Pebbles
        for (int i=0;i<5;i++) {
            double cx = W*(0.08 + i*0.06), cy = H*(0.88 + (i%2)*0.02);
            s.add(new Oval(new Point(cx-8, cy-5), new Point(cx+8, cy+5), Color.web("#FFD1DF"), true));
        }
        for (int i=0;i<10;i++) {
            double cx = W*(0.05 + i*0.08), cy = H*(0.95 - (i%3)*0.01);
            s.add(new Circle(new Point(cx, cy), 3.5, Color.web("#C2A477")));
        }

        // Sailboat: Hull, Mast, Sail
        double hullW = W*0.10, hullH = H*0.02;
        double hullX = W*0.20, hullY = H*0.66;
        s.add(new Rectangle(new Point(hullX, hullY), new Point(hullX+hullW, hullY+hullH), Color.web("#2D3142")));

        s.add(new Rectangle(new Point(hullX + hullW*0.5 - 2, hullY - H*0.08),
                new Point(hullX + hullW*0.5 + 2, hullY), Color.web("#4F5D75")));

        Polyline sail = new Polyline(Color.web("#E9F1FF"));
        sail.addPoint(new Point(hullX + hullW*0.5, hullY - H*0.08));
        sail.addPoint(new Point(hullX + hullW*0.85, hullY - H*0.02));
        sail.addPoint(new Point(hullX + hullW*0.5, hullY - H*0.02));
        sail.addPoint(new Point(hullX + hullW*0.5, hullY - H*0.08));
        Strokeable st1 = (Strokeable) sail;
        st1.setStrokeWidth(2);
        s.add(sail);

        // Umbrella + Towel
        double umbX = W*0.55, umbY = H*0.82;
        s.add(new Rectangle(
                new Point(umbX+38, umbY-92),
                new Point(umbX+42, umbY+10),
                Color.web("#6D6D6D")));
        double apexX = umbX+40, apexY = umbY-100;
        double rimL  = umbX-10, rimR  = umbX+90;
        double rimY  = umbY-92;
        int panels   = 10;
        java.util.List<Point> rim = new java.util.ArrayList<>();
        for (int i=0; i<=panels; i++) {
            double u = (double)i/panels;
            double x = rimL + u*(rimR - rimL);
            double y = rimY + Math.sin(u*Math.PI)*7;
            rim.add(new Point(x, y));
        }
        Color c1 = Color.web("#D7263D");
        Color c2 = Color.web("#F9A825");
        for (int i=0; i<panels; i++) {
            Triangle tri = new Triangle();
            tri.addVertex(new Point(apexX, apexY));
            tri.addVertex(rim.get(i));
            tri.addVertex(rim.get(i+1));
            Color fill = (i % 2 == 0) ? c1 : c2;
            tri.applyFill(fill);
            if (tri instanceof Strokeable st) st.setStrokeWidth(1.5);
            s.add(tri);
        }
        for (int i=0; i<panels; i++) {
            Point a = rim.get(i), b = rim.get(i+1);
            double mx = (a.getX()+b.getX())/2.0, my = (a.getY()+b.getY())/2.0;
            double w = (b.getX()-a.getX());
            double r = Math.abs(w)*0.18;
            s.add(new Oval(new Point(mx - r, my - r*0.4),
                    new Point(mx + r, my + r*0.9),
                    Color.web("#FFFFFF"), true));
        }
        for (int i=0; i<=panels; i+=2) {
            Polyline rib = new Polyline(Color.web("#4F5D75"));
            rib.addPoint(new Point(apexX, apexY));
            rib.addPoint(rim.get(i));
            if (rib instanceof Strokeable st) st.setStrokeWidth(2);
            s.add(rib);
        }
        s.add(new Circle(new Point(apexX, apexY), Math.min(W,H)*0.006, Color.web("#4F5D75")));
        s.add(new Rectangle(new Point(umbX-10, H*0.90), new Point(umbX+90, H*0.96), Color.web("#4FC3F7")));
        s.add(new Rectangle(new Point(umbX-10, H*0.90), new Point(umbX+90, H*0.96), Color.web("#4FC3F7")));

        return s;
    }

    /** Scene 2: Flower Garden */
    public static List<Shape> flowerGarden(double W, double H) {
        List<Shape> s = new ArrayList<>();
        // Sky
        s.add(new Rectangle(new Point(0, 0), new Point(W, H*0.55), Color.web("#CDEFFF")));

        // Distant hedgerow
        s.add(new Rectangle(new Point(0, H*0.52), new Point(W, H*0.60), Color.web("#7CB342")));

        // Grass foreground
        s.add(new Rectangle(new Point(0, H*0.60), new Point(W, H), Color.web("#66BB6A")));

        // Garden path
        s.add(new Rectangle(new Point(W*0.12, H*0.62), new Point(W*0.88, H*0.74), Color.web("#E6D4B8")));
        Squiggle pathEdge = new Squiggle(); pathEdge.setColor(Color.web("#D1BF9B"));
        if (pathEdge instanceof Strokeable pst) pst.setStrokeWidth(2);
        for (int i=0;i<=20;i++) {
            double x = W*(0.12 + (0.76*i/20.0));
            double y = H*(0.68 + Math.sin(i*0.5)*0.02);
            pathEdge.addPoint(new Point(x,y));
        }
        s.add(pathEdge);

        // Low picket fence
        double fy = H*0.57;
        for (int i=0;i<18;i++) {
            double fx = W*(0.05 + i*0.05);
            s.add(new Rectangle(new Point(fx, fy-35), new Point(fx+8, fy), Color.web("#F5F5F5"))); // picket
        }
        s.add(new Rectangle(new Point(W*0.05, fy-8), new Point(W*0.95, fy-2), Color.web("#EEEEEE"))); // top rail

        // Helpers
        java.util.function.BiConsumer<Double,Double> grassClump = (gx, gy) -> {
            // three thin triangles as blades for grass
            for (int k=0;k<3;k++) {
                Triangle t = new Triangle();
                double dx = (k-1)*6;
                t.addVertex(new Point(gx+dx, gy));
                t.addVertex(new Point(gx+dx+3, gy-18 - k*3));
                t.addVertex(new Point(gx+dx+6, gy));
                // fill green
                try { t.applyFill(Color.web("#2E7D32")); }
                catch (Exception ignore) { /* fallback */ }
                if (t instanceof Strokeable st) st.setStrokeWidth(1);
                s.add(t);
            }
        };

        // Peonies
        java.util.function.BiConsumer<Double, Double> peony = (cx, cy) -> {
            // leaves
            s.add(new Oval(new Point(cx-35, cy+8), new Point(cx-5, cy+28), Color.web("#2E7D32"), true));
            s.add(new Oval(new Point(cx+5, cy+8), new Point(cx+35, cy+28), Color.web("#2E7D32"), true));

            // stem
            s.add(new Rectangle(new Point(cx-2, cy), new Point(cx+2, cy+40), Color.web("#2E7D32")));

            // petal rings (overlapping circles)
            s.add(new Circle(new Point(cx, cy), 22, Color.web("#FFBDCE")));
            s.add(new Circle(new Point(cx-8, cy-6), 18, Color.web("#FFD7E1")));
            s.add(new Circle(new Point(cx+7, cy-5), 16, Color.web("#FFA4BB")));
            s.add(new Circle(new Point(cx, cy+3), 19, Color.web("#FF8ABC")));
            // center
            s.add(new Circle(new Point(cx, cy), 6, Color.web("#FFD54F")));
        };

        // Lilies
        java.util.function.BiConsumer<Double, Double> lily = (cx, cy) -> {
            // leaves
            s.add(new Oval(new Point(cx-28, cy+10), new Point(cx-2, cy+28), Color.web("#2E7D32"), true));
            s.add(new Oval(new Point(cx+2, cy+10), new Point(cx+28, cy+28), Color.web("#2E7D32"), true));
            // stem
            s.add(new Rectangle(new Point(cx-2, cy), new Point(cx+2, cy+42), Color.web("#2E7D32")));

            // 6 pointed petals made via triangles to simulate star shape
            Color petal = Color.web("#F8BBD0");
            for (int i=0;i<6;i++) {
                double ang = Math.toRadians(i*60.0);
                double rx = Math.cos(ang), ry = Math.sin(ang);
                Triangle p = new Triangle();
                p.addVertex(new Point(cx, cy));
                p.addVertex(new Point(cx + rx*20 - ry*6, cy + ry*20 + rx*6));
                p.addVertex(new Point(cx + rx*34,          cy + ry*34));
                try { p.applyFill(petal); } catch (Exception ignore) {}
                if (p instanceof Strokeable st) st.setStrokeWidth(1.2);
                s.add(p);
            }
            // pistil
            s.add(new Circle(new Point(cx, cy), 4.5, Color.web("#FFEB3B")));
        };

        // Tulips
        java.util.function.BiConsumer<Double, Double> tulip = (cx, cy) -> {
            // leaves
            s.add(new Oval(new Point(cx-20, cy+8), new Point(cx-2, cy+30), Color.web("#2E7D32"), true));
            s.add(new Oval(new Point(cx+2, cy+8), new Point(cx+20, cy+30), Color.web("#2E7D32"), true));
            // stem
            s.add(new Rectangle(new Point(cx-2, cy), new Point(cx+2, cy+40), Color.web("#2E7D32")));
            // petals
            s.add(new Oval(new Point(cx-14, cy-10), new Point(cx+14, cy+12), Color.web("#E53935"), true));
            s.add(new Oval(new Point(cx-12, cy-6),  new Point(cx+12, cy+10), Color.web("#EF5350"), true));
            for (int k=-1;k<=1;k++) {
                Triangle crown = new Triangle();
                crown.addVertex(new Point(cx + k*8, cy-10));
                crown.addVertex(new Point(cx + k*8 - 6, cy-2));
                crown.addVertex(new Point(cx + k*8 + 6, cy-2));
                try { crown.applyFill(Color.web("#D32F2F")); } catch (Exception ignore) {}
                s.add(crown);
            }
        };

        // Back row (taller lilies + peonies)
        for (double x = 0.14; x <= 0.86; x += 0.12) {
            lily.accept(W*x, H*0.62);
            grassClump.accept(W*x, H*0.60);
            if ((int)Math.round(x*100) % 24 == 0) peony.accept(W*(x+0.04), H*0.60);
        }
        // Middle bed (mix tulips + peonies)
        for (double x = 0.18; x <= 0.82; x += 0.10) {
            tulip.accept(W*x, H*0.70);
            if ((int)Math.round(x*100) % 20 == 0) peony.accept(W*(x+0.03), H*0.69);
            grassClump.accept(W*x, H*0.74);
        }
        // Front bed (dense tulips)
        for (double x = 0.12; x <= 0.88; x += 0.08) {
            tulip.accept(W*x, H*0.78);
            grassClump.accept(W*x, H*0.82);
        }

        // Butterflies
        java.util.function.BiConsumer<Double, Double> butterfly = (bx, by) -> {
            // body
            s.add(new Rectangle(new Point(bx-2, by-6), new Point(bx+2, by+6), Color.web("#4E342E")));
            // wings
            s.add(new Oval(new Point(bx-16, by-10), new Point(bx-2, by+2), Color.web("#FF8A65"), true));
            s.add(new Oval(new Point(bx+2, by-10),  new Point(bx+16, by+2), Color.web("#FF8A65"), true));
            s.add(new Oval(new Point(bx-14, by+0),  new Point(bx-2, by+12), Color.web("#FFD180"), true));
            s.add(new Oval(new Point(bx+2, by+0),   new Point(bx+14, by+12), Color.web("#FFD180"), true));
            // antennae
            Polyline antL = new Polyline(Color.web("#4E342E"));
            antL.addPoint(new Point(bx, by-6)); antL.addPoint(new Point(bx-6, by-12));
            if (antL instanceof Strokeable st) st.setStrokeWidth(1.5);
            s.add(antL);
            Polyline antR = new Polyline(Color.web("#4E342E"));
            antR.addPoint(new Point(bx, by-6)); antR.addPoint(new Point(bx+6, by-12));
            if (antR instanceof Strokeable st2) st2.setStrokeWidth(1.5);
            s.add(antR);
        };
        butterfly.accept(W*0.28, H*0.40);
        butterfly.accept(W*0.72, H*0.36);

        // Clouds
        var cloud = (java.util.function.BiConsumer<Double,Double>) (cx, cy) -> {
            s.add(new Oval(new Point(cx-50, cy-15), new Point(cx+50, cy+15), Color.web("#F5F7FA"), true));
            s.add(new Oval(new Point(cx-85, cy-5),  new Point(cx-15, cy+18), Color.web("#F5F7FA"), true));
            s.add(new Oval(new Point(cx+10, cy-10), new Point(cx+85, cy+18), Color.web("#F5F7FA"), true));
        };
        cloud.accept(W*0.30, H*0.16);
        cloud.accept(W*0.60, H*0.12);

        return s;
    }

    /** Scene 3: City Night (enhanced) */
    public static List<Shape> cityNight(double W, double H) {
        List<Shape> s = new ArrayList<>();
        // sky
        s.add(new Rectangle(new Point(0,0),        new Point(W, H*0.35), Color.web("#060A18"))); // top
        s.add(new Rectangle(new Point(0,H*0.35),   new Point(W, H*0.55), Color.web("#0A1130"))); // mid
        s.add(new Rectangle(new Point(0,H*0.55),   new Point(W, H*0.70), Color.web("#0B1026"))); // lower sky

        // Moon
        double moonR = Math.min(W,H)*0.05;
        double moonX = W*0.15, moonY = H*0.18;
        s.add(new Circle(new Point(moonX, moonY), moonR*1.8, Color.web("#FAFAD2")));
        s.add(new Circle(new Point(moonX, moonY), moonR, Color.web("#FAFAD2")));

        // Stars
        double[][] stars = {
                {0.08,0.10},{0.12,0.06},{0.18,0.13},{0.26,0.08},{0.33,0.05},
                {0.41,0.11},{0.47,0.07},{0.55,0.09},{0.63,0.04},{0.72,0.12},
                {0.80,0.06},{0.90,0.10}
        };
        for (double[] st : stars) {
            s.add(new Circle(new Point(W*st[0], H*st[1]), Math.max(1.2, W*0.002), Color.web("#F8F8E7")));
        }

        // Distant harbor glow
        s.add(new Rectangle(new Point(0, H*0.67), new Point(W, H*0.70), Color.web("#0A0E1F")));

        // Road + Sidewalks
        s.add(new Rectangle(new Point(0, H*0.70), new Point(W, H*0.96), Color.web("#2C2C2C"))); // road
        s.add(new Rectangle(new Point(0, H*0.96), new Point(W, H),       Color.web("#3A3A3A"))); // front sidewalk strip

        // Crosswalk
        double cwY1 = H*0.86, stripeW = W*0.02, gap = W*0.015;
        for (double x = W*0.10; x <= W*0.90; x += stripeW+gap) {
            s.add(new Rectangle(new Point(x, cwY1), new Point(x+stripeW, cwY1+H*0.03), Color.web("#E8E8E8")));
        }

        // Buildings
        double baseY = H*0.70;
        double[] xs = {0.06, 0.16, 0.28, 0.40, 0.52, 0.66, 0.78, 0.88};
        double[] bw = {0.08, 0.07, 0.065, 0.075, 0.07, 0.065, 0.07, 0.05}; // widths as fractions
        double[] hs = {0.50, 0.60, 0.46, 0.68, 0.52, 0.58, 0.44, 0.48};   // heights as fractions of H

        for (int i=0; i<xs.length; i++) {
            double x1 = W*xs[i];
            double x2 = x1 + W*bw[i];
            double y2 = baseY;
            double y1 = baseY - H*hs[i];
            s.add(new Rectangle(new Point(x1, y1), new Point(x2, y2), Color.web("#1E1E1E")));

            // Different rooftops --> triangle, oval, antennas
            if (i % 3 == 0) {
                Triangle tip = new Triangle();
                tip.addVertex(new Point((x1+x2)/2, y1 - H*0.06));
                tip.addVertex(new Point(x1 + W*0.015, y1));
                tip.addVertex(new Point(x2 - W*0.015, y1));
                try { tip.applyFill(Color.web("#222533")); } catch (Exception ignore) {}
                s.add(tip);
            } else if (i % 3 == 1) {
                s.add(new Oval(new Point(x1 + W*0.01, y1 - H*0.03),
                        new Point(x2 - W*0.01, y1 + H*0.01),
                        Color.web("#23263A"), true));
            } else {
                s.add(new Rectangle(new Point((x1+x2)/2 - 2, y1 - H*0.05),
                        new Point((x1+x2)/2 + 2, y1),
                        Color.web("#2B2F47")));
                s.add(new Circle(new Point((x1+x2)/2, y1 - H*0.05), Math.max(2, W*0.003), Color.web("#FF5252")));
            }

            // Neon billboard
            if (i == 2 || i == 5) {
                double bx1 = x1 + W*0.01, bx2 = x1 + W*0.055;
                double by1 = y1 + H*0.04,  by2 = by1 + H*0.08;
                s.add(new Rectangle(new Point(bx1, by1), new Point(bx2, by2), Color.web("#0D2140"))); // backing
                s.add(new Rectangle(new Point(bx1, by1), new Point(bx2, by1+H*0.005), Color.web("#00E5FF")));
                s.add(new Rectangle(new Point(bx1, by2-H*0.005), new Point(bx2, by2), Color.web("#00E5FF")));
                s.add(new Rectangle(new Point(bx1, by1), new Point(bx1+H*0.005, by2), Color.web("#00E5FF")));
                s.add(new Rectangle(new Point(bx2-H*0.005, by1), new Point(bx2, by2), Color.web("#00E5FF")));
            }

            // Windows
            double cx = x1 + W*0.012;
            while (cx + W*0.012 < x2 - W*0.01) {
                double cy = y1 + H*0.02;
                while (cy + W*0.012 < y2 - H*0.02) {
                    // alternate lit/unlit windows
                    boolean lit = (((int)((cx - x1) / (W*0.02))) + ((int)((cy - y1) / (H*0.04)))) % 2 == 0;
                    Color wcol = lit ? Color.web("#FFD54F") : Color.web("#1A1A1A");
                    s.add(new Square(new Point(cx, cy), new Point(cx + W*0.012, cy + W*0.012), wcol));
                    cy += H*0.045;
                }
                cx += W*0.022;
            }
        }

        // Train + train tracks
        double trackY = H*0.62;
        s.add(new Rectangle(new Point(W*0.04, trackY-6), new Point(W*0.96, trackY), Color.web("#303A4A"))); // rail
        for (double x = W*0.08; x < W*0.95; x += W*0.12) {
            s.add(new Rectangle(new Point(x-6, trackY), new Point(x+6, trackY+H*0.08), Color.web("#2A2F3E")));
        }
        double carY1 = trackY - H*0.045, carH = H*0.04, carW = W*0.12;
        for (int i=0; i<3; i++) {
            double carX1 = W*(0.12 + i*0.16);
            s.add(new Rectangle(new Point(carX1, carY1), new Point(carX1+carW, carY1+carH), Color.web("#37474F")));
            double wx = carX1 + W*0.01;
            while (wx + W*0.02 < carX1 + carW - W*0.01) {
                s.add(new Rectangle(new Point(wx, carY1 + H*0.008), new Point(wx + W*0.02, carY1 + H*0.03), Color.web("#B3E5FC")));
                wx += W*0.03;
            }
        }

        // Street lamps
        for (double x = W*0.12; x <= W*0.88; x += W*0.19) {
            s.add(new Rectangle(new Point(x-2, H*0.74), new Point(x+2, H*0.96), Color.web("#4B4B4B")));
            s.add(new Rectangle(new Point(x-6, H*0.74-12), new Point(x+6, H*0.74), Color.web("#616161")));
            s.add(new Circle(new Point(x, H*0.74-6), Math.max(3, W*0.004), Color.web("#FFF9C4")));
            s.add(new Circle(new Point(x, H*0.74-6), Math.max(10, W*0.015), Color.web("#EEE8AA")));
        }

        // center lane dashes
        for (int i=0;i<12;i++) {
            double segW = W*0.05, segH = H*0.01, gap2 = W*0.03;
            double x = i*(segW+gap2) + W*0.02;
            s.add(new Rectangle(new Point(x, H*0.83), new Point(x+segW, H*0.84), Color.web("#E0E0E0")));
        }

        // cars
        class Car {
            Car(double cx, double cy, Color body) {
                s.add(new Rectangle(new Point(cx, cy), new Point(cx + W*0.08, cy + H*0.025), body));
                s.add(new Rectangle(new Point(cx + W*0.018, cy + H*0.004), new Point(cx + W*0.06, cy + H*0.018), Color.web("#B3E5FC")));
            }
        }
        new Car(W*0.14, H*0.76, Color.web("#546E7A"));
        new Car(W*0.46, H*0.78, Color.web("#455A64"));
        new Car(W*0.70, H*0.76, Color.web("#607D8B"));

        // soft clouds near moon
        s.add(new Oval(new Point(moonX-60, moonY-18), new Point(moonX+40, moonY+10), Color.web("#1C2136"), true));
        s.add(new Oval(new Point(moonX+10,  moonY-10), new Point(moonX+90, moonY+16), Color.web("#1C2136"), true));

        return s;
    }
}
