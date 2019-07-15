package model;

import java.awt.*;
import java.io.File;

public class Repository {
    private static Repository instance;
    private Repository(){}
    public static Repository inst(){
        if(instance==null)instance=new Repository();
        return instance;
    }
    public CutAspect cutAspect=CutAspect._16x9;
    public File currentDirectory= new File(System.getProperty("user.dir"));
    public Component window;
    public static final int MIN_IMAGE_HEIGHT=100;
    public static final int MIN_IMAGE_WIDTH=100;
}
