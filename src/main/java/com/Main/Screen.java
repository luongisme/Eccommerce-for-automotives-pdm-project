package com.Main;

import javax.swing.*;

public abstract class Screen {
    protected final AppFrame appFrame;
    protected JPanel panel;

    public Screen(AppFrame appFrame) {
        this.appFrame = appFrame;
        this.panel = new JPanel(null); // default panel; có thể thay ở subclass
    }

    public JPanel getPanel() {
        return panel;
    }

    /** Mỗi màn hình tự vẽ UI tại đây */
    protected abstract void initUI();
}
