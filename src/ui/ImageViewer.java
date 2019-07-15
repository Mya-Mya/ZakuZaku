package ui;

import model.ImageEditor;
import model.ImageEditorListener;

import javax.swing.*;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.awt.event.*;

public class ImageViewer extends JPanel implements MouseListener, MouseMotionListener, ImageEditorListener, MouseWheelListener {
    private ImageEditor imageEditor;
    private Cutter cutter;
    private JPopupMenu popup;

    private int originalWidth;
    private int originalHeight;
    private double zoom=1;

    public ImageViewer(ImageEditor imageEditor) {
        super();
        this.imageEditor = imageEditor;
        this.imageEditor.addImageEditorListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
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
        g2.drawImage(imageEditor.getOriginal(),0,0,(int)(originalWidth*zoom),(int)(originalHeight*zoom),this);
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
        imageEditor.setStartCut(new Point((int)(cutter.getStartPos().x/zoom),(int)(cutter.getStartPos().y/zoom)));
        imageEditor.setEndCut(new Point((int)(cutter.getEndPos().x/zoom),(int)(cutter.getEndPos().y/zoom)));
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
        changeZoom(0);
        cutter.resetDruggerPos();
    }

    private void changeZoom(double deltaZoom){
        double oldZoom=zoom;
        zoom+=deltaZoom;
        originalWidth=imageEditor.getOriginal().getWidth();
        originalHeight=imageEditor.getOriginal().getHeight();
        Dimension preSize = new Dimension((int)(originalWidth*zoom), (int)(originalHeight*zoom));
        setPreferredSize(preSize);
        cutter.setPreferredSize(preSize);
        //cutter.changeDruggerPosSize(zoom/oldZoom);
        updateUI();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (e.isControlDown()) {
            changeZoom(e.getWheelRotation()*0.01);
        }
    }
}
