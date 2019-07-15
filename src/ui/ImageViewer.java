package ui;

import model.ImageEditor;
import model.ImageEditorListener;

import javax.swing.*;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.awt.event.*;

public class ImageViewer extends JPanel implements MouseListener, MouseMotionListener, ImageEditorListener {
    private ImageEditor imageEditor;
    private Cutter cutter;
    private JPopupMenu popup;

    public ImageViewer(ImageEditor imageEditor) {
        super();
        this.imageEditor = imageEditor;
        this.imageEditor.addImageEditorListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        popup=new JPopupMenu();
        popup.add("保存");
        add(popup);
        cutter = new Cutter();
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imageEditor == null) {
            return;
        }
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(imageEditor.getOriginal(), null, 0, 0);
        cutter.draw(g2);
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        if(SwingUtilities.isRightMouseButton(e)){
            popup.setLocation(e.getX(),e.getY());
            popup.setVisible(true);//todo 消せない押せない
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (imageEditor == null) {
            return;
        }
        cutter.mousePressed(e.getPoint());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (imageEditor == null) {
            return;
        }
        cutter.mouseReleased(e.getPoint());
        updateUI();
        imageEditor.setStartCut(cutter.getStartPos());
        imageEditor.setEndCut(cutter.getEndPos());
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (imageEditor == null) {
            return;
        }
        cutter.mouseMoving(e.getPoint());
        updateUI();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void originalImageChanged() {
        Dimension preSize = new Dimension(imageEditor.getOriginal().getWidth(), imageEditor.getOriginal().getWidth());
        setPreferredSize(preSize);
        cutter.setPreferredSize(preSize);
        cutter.resetDruggerPos();
        updateUI();
    }
}
