package model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;

public class ImageEditor  {
    private BufferedImage original;
    private Point startCut = new Point(0, 0);
    private Point endCut = new Point(0, 0);

    public ImageEditor() {
    }

    public void openFile(File imageFile)throws Exception{
        original = ImageIO.read(imageFile);
    }
    public boolean isMeetingMinimumDimension(){
        return original.getWidth()>Config.MIN_IMAGE_WIDTH&&original.getHeight()>Config.MIN_IMAGE_HEIGHT;
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

    public void saveCutImage(File directory,String name){
        File target=new File(directory.toString()+"\\"+name+".png");
        BufferedImage out=original.getSubimage(startCut.x,startCut.y,getCutWidth(),getCutHeight());
        try{
            ImageIO.write(out,"png",target);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Deprecated
    public BufferedImage cut() {
        int[] originalMap = new int[original.getWidth() * original.getHeight()];
        original.getRGB(0, 0, original.getWidth(), original.getHeight(),
                originalMap, 0, original.getWidth());

        int width = getCutWidth();
        int height = getCutHeight();
        int[] outMap = new int[width * height];
        BufferedImage out = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int originalR = startCut.y + i;
                int originalC = startCut.x + j;
                int originalInd = originalR * original.getWidth() + originalC;
                int outInd = i * width + j;
                outMap[outInd] = originalMap[originalInd];
            }
        }
        out.setRGB(0, 0, original.getWidth(), original.getHeight(), outMap, 0, original.getWidth());
        return out;
    }

}
