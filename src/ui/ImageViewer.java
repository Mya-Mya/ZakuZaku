package ui;

import model.Config;
import model.ImageEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Observable;
import java.util.Observer;

public class ImageViewer extends JPanel implements MouseListener, MouseMotionListener {
    private ImageEditor imageModel;
    private Cutter cutter;

    public ImageViewer(Config config,ImageEditor imageEditor) {
        super();
        imageModel=imageEditor;
        addMouseListener(this);
        addMouseMotionListener(this);
        cutter = new Cutter(config);
    }

    public void imageEditorChanged() {
        Dimension preSize = new Dimension(
                imageModel.getOriginal().getWidth(), imageModel.getOriginal().getWidth());
        setPreferredSize(preSize);
        cutter.setPreferredSize(preSize);
        cutter.resetDruggerPos();
        updateUI();
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imageModel == null) {
            return;
        }
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(imageModel.getOriginal(), null, 0, 0);
        cutter.draw(g2);
    }


    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (imageModel == null) {
            return;
        }
        cutter.mousePressed(e.getPoint());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (imageModel == null) {
            return;
        }
        cutter.mouseReleased(e.getPoint());
        updateUI();
        imageModel.setStartCut(cutter.getStartPos());
        imageModel.setEndCut(cutter.getEndPos());
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (imageModel == null) {
            return;
        }
        cutter.mouseMoving(e.getPoint());
        updateUI();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
