package com.panel.custom;

import java.awt.HeadlessException;
import javafx.scene.text.Text;

public class TextView extends Text {

    public TextView() throws HeadlessException {
    }

    public TextView(String text) throws HeadlessException {
        super(text);
        setText(text);
    }
}

