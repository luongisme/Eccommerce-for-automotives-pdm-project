package com.UI.Profile;

import javax.swing.*;

/** Controller: toggle Edit / Save / Cancel (UI only). */
public class ProfileController {
    private final ProfileScreen view;

    // snapshot để khôi phục khi Cancel
    private String sFirst, sLast, sPhone, sStreet, sCity, sDistrict, sZip, sCountry;

    public ProfileController(ProfileScreen view) {
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
    }

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
