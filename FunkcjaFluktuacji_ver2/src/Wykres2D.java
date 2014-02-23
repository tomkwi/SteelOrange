
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

//dostaje obrazek i udostępnia metody rysujące na tym obrazku
public class Wykres2D {

    AffineTransform at;
    Graphics gr;
    int width, height;
    Font horizontalFont;
    int commentX, commentY;
    int fieldWidth;
    double scaleY;

    final void drawFontBounds(Graphics g, int topToBase, int bottomToBase) {
        int x = 10, y = 50, l = 400;
        gr.setColor(Color.blue);
        gr.drawLine(x, y, x + l, y);
        gr.drawLine(x, y - topToBase, x + l, y - topToBase);
        gr.drawLine(x, y + bottomToBase, x + l, y + bottomToBase);
        gr.setColor(Color.white);
        gr.drawString("ABPQRacdefghijklmpqrstuyz", x, y);
    }

    final int transformX(double x) {
        double pts[] = {x, 0};
        double pts2[] = new double[2];
        at.transform(pts, 0, pts2, 0, 1);
        return (int) (pts2[0]);
    }

    final int transformY(double y) {
        double pts[] = {0, y};
        double pts2[] = new double[2];
        at.transform(pts, 0, pts2, 0, 1);
        return (int) (pts2[1]);
    }

    final int max(int a, int b) {
        return a > b ? a : b;
    }

    Wykres2D(BufferedImage im,
            double minX,
            double maxX,
            double minY,
            double maxY,
            String labelX,
            String labelY,
            double unitX,
            double unitY,
            Color background, Color markings, Color lines) {

        //margines zależny od wielkości czcionki
        gr = im.getGraphics();
        Font basicFont = new Font("SansSerif", Font.PLAIN, 10);
        AffineTransform fontAT = new AffineTransform();
        double skala = 1.5;
        fontAT.scale(skala, skala);
        horizontalFont = basicFont.deriveFont(fontAT);
        fontAT.rotate(Math.PI * 1.5);
        //Font verticalFont = basicFont.deriveFont(fontAT);
        FontMetrics fm = gr.getFontMetrics(horizontalFont);
        Rectangle2D labelXrect = fm.getStringBounds(labelX, gr),
                labelYrect = fm.getStringBounds(labelY, gr);

        int ascent = fm.getAscent(),
                descent = fm.getDescent();
        gr.setFont(horizontalFont);
        int yScaleWidth = 0;
        for (int i = 0; i <= (maxY - minY) / unitY; i++) {
            double y = minY + i * unitY;
            String l = String.format("%.2g", y);
            yScaleWidth = max(yScaleWidth, (int) (fm.getStringBounds(l, gr).getWidth()));
        }
        int unitsFontHeight = fm.getMaxAscent() + fm.getMaxDescent(),
                marginTop = unitsFontHeight,
                marginLeft = yScaleWidth + (int) (descent * 4),
                marginRight = unitsFontHeight,
                marginBottom = unitsFontHeight * 2,
                marginHorz = marginLeft + marginRight,
                marginVert = marginTop + marginBottom;

        //odwzorowanie zależne od marginesu
        width = im.getWidth();
        height = im.getHeight();
        double sizeX = maxX - minX,
                sizeY = maxY - minY,
                centerX = (maxX + minX) / 2,
                centerY = (maxY + minY) / 2,
                imageSizeX = width - marginHorz,
                imageSizeY = height - marginVert,
                imageCenterX = imageSizeX / 2 + marginLeft,
                imageCenterY = imageSizeY / 2 + marginTop;
        at = new AffineTransform();
        at.translate(imageCenterX, imageCenterY);
        at.scale(imageSizeX / sizeX, -imageSizeY / sizeY);
        scaleY=imageSizeY / sizeY;
        at.translate(-centerX, -centerY);
        fieldWidth=transformX(maxX)-transformX(minX);

        //linie
        int left = marginLeft - 1,
                top = marginTop + 1,
                right = width - marginRight - 1,
                bottom = height - marginBottom,
                arrowDx = 3,
                arrowDy = 10;
        gr.setColor(background);
        gr.fillRect(0, 0, width, height);
        gr.setColor(lines);
        for (int i = 0; i < sizeX / unitX; i++) {
            double x = minX + i * unitX;
            gr.drawLine(transformX(x), bottom, transformX(x), top);
        }
        //gr.setFont(verticalFont);
        for (int i = 0; i < sizeY / unitY; i++) {
            double y = minY + i * unitY;
            gr.drawLine(left, transformY(y), right, transformY(y));
        }

        //rysunek osi

        gr.setColor(markings);
        gr.drawLine(left, top, left, bottom);
        gr.drawLine(left, bottom, right, bottom);
        gr.drawLine(left - arrowDx, top + arrowDy, left, top);
        gr.drawLine(left, top, left + arrowDx, top + arrowDy);
        gr.drawLine(right - arrowDy, bottom - arrowDx, right, bottom);
        gr.drawLine(right, bottom, right - arrowDy, bottom + arrowDx);

        //opis osi
        int labelXCenter = (int) (labelXrect.getCenterX()),
                labelYCenter = (int) (labelYrect.getCenterX()),
                halfAxisX = marginLeft + (width - marginHorz) / 2,
                halfAxisY = marginTop + (height - marginVert) / 2,
                labelYJustifiedX = left,
                labelYJustifiedY = ascent,
                labelXJustifiedX = right
                - (int) (fm.getStringBounds(labelX, gr).getWidth()),
                labelXJustifiedY = height - descent;
        gr.setFont(horizontalFont);
        gr.drawString(labelX, labelXJustifiedX, labelXJustifiedY);
        gr.drawString(labelY, labelYJustifiedX, labelYJustifiedY);
        //drawFontBounds(gr, ascent, descent);

        //opis jednostek
        gr.setFont(horizontalFont);
        int markerLength = descent;
        //cyfry mają descent = 0, więc można je obniżyć i jest miejsce na marker
        for (int i = 0; i <= sizeX / unitX; i++) {
            double x = minX + i * unitX;
            gr.drawLine(transformX(x), bottom, transformX(x), bottom + markerLength);
            DecimalFormat formatter = new DecimalFormat("#.##");
            String l = formatter.format(x);
            gr.drawString(l,
                    transformX(x) - (int) (fm.getStringBounds(l, gr).getCenterX()),
                    bottom + ascent + markerLength);
        }
        //gr.setFont(verticalFont);
        for (int i = 0; i <= sizeY / unitY; i++) {
            double y = minY + i * unitY;
            gr.drawLine(left, transformY(y), left - markerLength, transformY(y));
            DecimalFormat formatter = new DecimalFormat("#.##");
            String l = formatter.format(y);
            gr.drawString(l,
                    marginLeft - (int) (fm.getStringBounds(l, gr).getWidth() + descent * 2.5),
                    transformY(y) + descent);
        }

        commentX = right;
        commentY = top + ascent;
    }

    void drawLine(double x1,
            double y1,
            double x2,
            double y2) {
        double pts[] = {x1, y1, x2, y2};
        double pts2[] = new double[4];
        at.transform(pts, 0, pts2, 0, 2);
        gr.drawLine((int) pts2[0], (int) pts2[1], (int) pts2[2], (int) pts2[3]);
    }

    void drawPixel(double x1, double y1) {
        double pts[] = {x1, y1};
        double pts2[] = new double[2];
        at.transform(pts, 0, pts2, 0, 1);
        gr.drawLine((int) pts2[0], (int) pts2[1], (int) pts2[0], (int) pts2[1]);
    }

    void fillRect(double x1,
            double y1,
            double w,
            double h) {
        double pts[] = {x1, y1, x1 + w, y1 + h};
        double pts2[] = new double[4];
        at.transform(pts, 0, pts2, 0, 2);
        double wi = pts2[2] - pts2[0], he = pts2[1] - pts2[3];
        gr.fillRect((int) pts2[0], (int) pts2[1],
                (int) (wi),
                (int) (he));
    }

    void setColor(Color c) {
        gr.setColor(c);
    }

    void drawComment(String s) {
        AffineTransform fontAT = new AffineTransform();
        double skala = 2;
        fontAT.scale(skala, skala);
        Font commentFont = horizontalFont.deriveFont(fontAT);
        gr.setFont(commentFont);
        gr.drawString(s,
                (int) (commentX - gr.getFontMetrics(commentFont).getStringBounds(s, gr).getWidth()),
                commentY);
    }
}
