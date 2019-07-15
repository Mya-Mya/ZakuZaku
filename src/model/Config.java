package model;

import ui.CutAspect;

import java.io.File;

public class Config {
    public CutAspect cutAspect=CutAspect._16x9;
    public File currentDirectory= new File(System.getProperty("user.dir"));
    public static final int MIN_IMAGE_WIDTH=100;
    public static final int MIN_IMAGE_HEIGHT=100;

}
