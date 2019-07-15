package ui;

import model.Config;

import javax.swing.*;
import java.awt.*;

public class Cutter extends JComponent {
    private Config config;
    private Drugger startDragger;
    private Drugger endDragger;
    private Color c0 = new Color(43, 196, 75);
    private Color c1 = new Color(21, 188, 255);
    private Color c2 = new Color(255, 0, 82);
    private int minDistance = 25;
    private int druggingSize = 12;
    private int width=Config.MIN_IMAGE_WIDTH;
    private int height=Config.MIN_IMAGE_HEIGHT;

    public Cutter(Config config) {
        super();
        this.config = config;
        resetDruggerPos();
    }

    public void resetDruggerPos() {
        startDragger = new Drugger(new Point(0,0), c0, druggingSize);
        endDragger = new Drugger(new Point(Config.MIN_IMAGE_WIDTH,Config.MIN_IMAGE_HEIGHT), c1, druggingSize);
        checkDraggers();
    }


    public void draw(Graphics2D g2) {
        startDragger.draw(g2);
        endDragger.draw(g2);

        g2.setColor(c2);
        g2.drawRect(startDragger.pos.x, startDragger.pos.y,
                width, height);
    }

    public void mousePressed(Point point) {
        startDragger.mouseClicked(point);
        endDragger.mouseClicked(point);
    }

    public void mouseReleased(Point point) {
        startDragger.mouseReleased(point);
        endDragger.mouseReleased(point);
    }

    public void mouseMoving(Point point) {
        startDragger.mouseMoving(point);
        endDragger.mouseMoving(point);
        checkDraggers();
    }

    private void checkDraggers() {

        //startDraggerが動いているときは大きさを変えられない
        if (startDragger.dragging) {
            endDragger.pos.x = startDragger.pos.x + width;
            endDragger.pos.y = startDragger.pos.y + height;
        }

        //endDraggerが動いているときは大きさが自由に変えられる
        if (endDragger.dragging) {
            width = endDragger.pos.x - startDragger.pos.x;
            height = endDragger.pos.y - startDragger.pos.y;

            if (config.cutAspect != CutAspect.nothing) {//アスペクト比の調整
                double nowRatio = (double) height / (double) width;
                double targetRatio = config.cutAspect.getRatio();
                if (nowRatio < targetRatio) {//アスペクト比が縦に短い
                    endDragger.pos.x = ((int) (startDragger.pos.x + height / targetRatio));
                }
                if (nowRatio > targetRatio) {//アスペクト比が横に短い
                    endDragger.pos.y = ((int) (startDragger.pos.y + width * targetRatio));
                }
            }

            width = endDragger.pos.x - startDragger.pos.x;
            height = endDragger.pos.y - startDragger.pos.y;
            if (width <= minDistance) {//横に短い
                endDragger.pos.x = (startDragger.pos.x + minDistance);
                width = endDragger.pos.x - startDragger.pos.x;
            }
            if (height <= minDistance) {//縦に短い
                endDragger.pos.y = (startDragger.pos.y + minDistance);
                height = endDragger.pos.y - startDragger.pos.y;
            }
        }
    }

    public Point getStartPos() {
        return startDragger.pos;
    }

    public Point getEndPos() {
        return endDragger.pos;
    }

    private class Drugger {
        private Point pos;
        private Color color;
        private int size;
        private int sizeof2;
        private boolean dragging = false;

        public Drugger(Point p0, Color color, int size) {
            super();
            pos = p0;
            this.color = color;
            this.size = size;
            sizeof2 = size / 2;
        }

        public void draw(Graphics g) {
            g.setColor(color);
            g.fillOval(pos.x - sizeof2, pos.y - sizeof2, size, size);
        }

        public void mouseClicked(Point mousePos) {
            if (mousePos.distance(pos) <= size) {
                dragging = true;
                return;
            }
            dragging = false;
        }

        public void mouseMoving(Point mousePos) {
            if (dragging) {
                pos = mousePos;
            }
        }

        public void mouseReleased(Point mousePos) {
            dragging = false;
        }

    }

}
