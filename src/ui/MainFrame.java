package ui;

import model.Repository;
import model.ImageEditor;
import model.ImageLoadable;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private ImageEditor imageEditor;
    private ImageViewer imageViewer;
    private Operations operations;

    public MainFrame() {
        super("ZakuZaku");
        setPreferredSize(new Dimension(800, 500));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        Repository.inst().window=this;
        imageEditor=new ImageEditor();
        imageViewer = new ImageViewer(imageEditor);
        add(new JScrollPane(imageViewer, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS),BorderLayout.CENTER);
        operations=new Operations(imageEditor,imageEditor);
        add(operations,BorderLayout.SOUTH);

        pack();
        setVisible(true);

        imageEditor.loadOriginalImage();
    }


}
