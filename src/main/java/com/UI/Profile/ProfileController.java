package com.UI.Profile;

import com.Main.AppFrame;
import com.UI.OrderHistory.OrderHistoryScreen;

import javax.swing.*;

/** Controller: toggle Edit / Save / Cancel (UI only) + navigation. */
public class ProfileController {
    private final ProfileScreen view;
    private final AppFrame appFrame;

    private String sFirst, sLast, sPhone, sStreet, sCity, sDistrict, sZip, sCountry;

    public ProfileController(AppFrame appFrame, ProfileScreen view) {
        this.appFrame = appFrame;
        this.view = view;
        attach();
    }

    private void attach() {
        view.btnEdit.addActionListener(e -> {
            snapshot();
            view.setEditing(true);
        });

        view.btnCancel.addActionListener(e -> {
            restore();
            view.setEditing(false);
        });

        view.btnSave.addActionListener(e -> {
            view.setEditing(false);
            JOptionPane.showMessageDialog(view, "Profile updated (UI only).");
        });

        // connect tab orrder history
        view.tabOrders.addActionListener(e -> {
            // chuyển màn hình sang OrderHistoryScreen
            appFrame.setScreen(new OrderHistoryScreen(appFrame));
        });

        // if profile on then order history off
        view.tabProfile.addActionListener(e -> {
            
            view.tabProfile.setSelected(true);
            view.tabOrders.setSelected(false);
        });
    }

    // Snapshot / Restore 
    private void snapshot() {
        sFirst    = view.tfFirstName.getText();
        sLast     = view.tfLastName.getText();
        sPhone    = view.tfPhone.getText();
        sStreet   = view.tfStreet.getText();
        sCity     = view.tfCity.getText();
        sDistrict = view.tfDistrict.getText();
        sZip      = view.tfZip.getText();
        sCountry  = view.tfCountry.getText();
    }

    private void restore() {
        view.tfFirstName.setText(sFirst);
        view.tfLastName.setText(sLast);
        view.tfPhone.setText(sPhone);
        view.tfStreet.setText(sStreet);
        view.tfCity.setText(sCity);
        view.tfDistrict.setText(sDistrict);
        view.tfZip.setText(sZip);
        view.tfCountry.setText(sCountry);
    }
}
