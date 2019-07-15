package ui;

import jdk.nashorn.internal.scripts.JO;
import model.Config;
import model.ImageEditor;
import model.ImageLoadable;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MainFrame extends JFrame implements ImageLoadable {
    private ImageEditor imageEditor;
    private ImageViewer imageViewer;
    private Operations operations;
    private Config config;

    public MainFrame() {
        super("ZakuZaku");
        setPreferredSize(new Dimension(800, 500));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());
        imageEditor=new ImageEditor();
        config=new Config();
        imageViewer = new ImageViewer(config,imageEditor);
        add(new JScrollPane(imageViewer, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS)
                ,BorderLayout.CENTER
        );
        operations=new Operations(config,imageEditor,this);
        add(operations,BorderLayout.SOUTH);

        pack();
        setVisible(true);

        loadImage();
    }

    @Override
    public void loadImage() {

        JFileChooser fc = new JFileChooser(config.currentDirectory);
        int selected = fc.showOpenDialog(this);
        if (selected == JFileChooser.APPROVE_OPTION) {
            config.currentDirectory=fc.getCurrentDirectory();
            try{
            imageEditor.openFile(fc.getSelectedFile());
            }catch (Exception e){
                JOptionPane.showMessageDialog(this,"お前なんかしたか");
                loadImage();
            }
            if(!imageEditor.isMeetingMinimumDimension()){
                JOptionPane.showMessageDialog(this,"小さすぎ");
                loadImage();
            }
            imageViewer.imageEditorChanged();
        } else {
            System.exit(0);
        }

    }
}
