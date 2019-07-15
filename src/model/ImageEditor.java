package model;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ImageEditor implements CutImageSaveable,ImageLoadable {
    private BufferedImage original;
    private Point startCut = new Point(0, 0);
    private Point endCut = new Point(0, 0);
    private java.util.List<ImageEditorListener> listenerList=new ArrayList<>();

    public ImageEditor() {
    }

    public void openImage(File imageFile) throws Exception {
        original = ImageIO.read(imageFile);
    }

    public boolean isMeetingMinimumDimension() {
        return original.getWidth() > Repository.MIN_IMAGE_WIDTH && original.getHeight() > Repository.MIN_IMAGE_HEIGHT;
    }

    public BufferedImage getOriginal() {
        return original;
    }

    public void setStartCut(Point p) {
        startCut = p;
    }

    public void setEndCut(Point p) {
        endCut = p;
    }


    public int getCutWidth() {
        return Math.abs(endCut.x - startCut.x);
    }

    public int getCutHeight() {
        return Math.abs(endCut.y - startCut.y);
    }

    @Override
    public void saveCutImage(File directory, String name) {
        File target = new File(directory.toString() + "\\" + name + ".png");
        BufferedImage out = original.getSubimage(startCut.x, startCut.y, getCutWidth(), getCutHeight());
        try {
            ImageIO.write(out, "png", target);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadOriginalImage() {
        JFileChooser fc = new JFileChooser(Repository.inst().currentDirectory);
        int selected = fc.showOpenDialog(Repository.inst().window);
        if (selected == JFileChooser.APPROVE_OPTION) {
            Repository.inst().currentDirectory=fc.getCurrentDirectory();
            try{
                openImage(fc.getSelectedFile());
            }catch (Exception e){
                JOptionPane.showMessageDialog(Repository.inst().window,"お前なんかしたか");
                loadOriginalImage();
            }
            if(!isMeetingMinimumDimension()){
                JOptionPane.showMessageDialog(Repository.inst().window,"小さすぎ");
                loadOriginalImage();
            }
        } else {
            System.exit(0);
        }
        notifyImageEditorListener();
    }

    public void addImageEditorListener(ImageEditorListener l){
        listenerList.add(l);
    }
    private void notifyImageEditorListener(){
        for(ImageEditorListener l:listenerList)l.originalImageChanged();
    }
}
