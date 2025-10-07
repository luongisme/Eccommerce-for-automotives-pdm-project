package com.UI.Profile;

import com.Main.AppFrame;
import com.Main.Screen;

import javax.swing.*;

public class ProfilePage extends Screen {
    private ProfileScreen view;

    public ProfilePage(AppFrame appFrame) {
        super(appFrame);
        initUI();
    }

    @Override
    protected void initUI() {
        view = new ProfileScreen();
        this.panel = view; 
        new ProfileController(appFrame, view);
    }
}

