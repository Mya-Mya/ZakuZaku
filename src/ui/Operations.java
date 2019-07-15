package ui;

import model.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Operations extends JToolBar implements ActionListener,SavingFileNameGetter {
    private JButton btLoad;
    private JComboBox<String> cmbCutAspect;
    private JButton btSave;
    private JTextField tfFileName;

    private CutImageSaveable cutImageSaver;
    private String latestFileName;
    private FileNamer fileNamer;
    private ImageLoadable imageLoader;

    public Operations(CutImageSaveable cutImageSaver, ImageLoadable imageLoader) {
        super();
        this.cutImageSaver = cutImageSaver;
        this.imageLoader=imageLoader;
        fileNamer=new DefaultFileNamer();

        btLoad=new JButton("画像を開く");
        add(btLoad);

        cmbCutAspect = new JComboBox<>();
        cmbCutAspect.addItem(CutAspect._16x9.toString());
        cmbCutAspect.addItem(CutAspect._4x3.toString());
        cmbCutAspect.addItem(CutAspect._1x1.toString());
        cmbCutAspect.addItem(CutAspect.nothing.toString());
        add(cmbCutAspect);
        tfFileName = new JTextField(fileNamer.getFirst());
        add(tfFileName);
        btSave = new JButton("保存");
        add(btSave);

        btLoad.addActionListener(this);
        cmbCutAspect.addActionListener(this);
        tfFileName.addActionListener(this);
        btSave.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==btLoad){
            imageLoader.loadOriginalImage();
        }
        if (e.getSource() == cmbCutAspect) {
            Repository.inst().cutAspect = CutAspect.fromString((String) cmbCutAspect.getSelectedItem());
        }
        if (e.getSource() == btSave) {
            latestFileName=getSavingFileName();
            cutImageSaver.saveCutImage(Repository.inst().currentDirectory, latestFileName);
            tfFileName.setText(fileNamer.getNext(latestFileName));
        }
        if (e.getSource() == tfFileName) {
            if (tfFileName.getText().isEmpty()) {
                btSave.setEnabled(false);
            } else {
                btSave.setEnabled(true);
            }
        }
    }

    @Override
    public String getSavingFileName() {
        return tfFileName.getText();
    }
}
