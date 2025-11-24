package com.UI.Profile;

import com.DAO.addressDAOimpl;
import com.DAO.userDAOimpl;
import com.Main.AppFrame;
import com.UI.OrderHistory.OrderHistoryScreen;
import com.model.Address;
import com.model.User;
import com.service.UserSession;

import javax.swing.*;
import java.util.List;


public class ProfileController {
    private final ProfileScreen view;
    private final AppFrame appFrame;

    // DAOs
    private final addressDAOimpl addressDAO;
    private final userDAOimpl userDAO;

    // Current data
    private User currentUser;
    private Address currentAddress;

    // Snapshot for cancel
    private String sFirst, sLast, sStreet, sCity, sPostalCode, sCountry;

    public ProfileController(AppFrame appFrame, ProfileScreen view) {
        this.appFrame = appFrame;
        this.view = view;
        this.addressDAO = new addressDAOimpl();
        this.userDAO = new userDAOimpl();

        loadUserData();
        attach();
    }

    /**
     * Load user and address data from database
     */
    private void loadUserData() {
        // Get current logged-in user from session
        currentUser = UserSession.getInstance().getCurrentUser();

        if (currentUser == null) {
            JOptionPane.showMessageDialog(view,
                "No user logged in!",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Load user info into fields
        view.tfFirstName.setText(currentUser.getFirstName() != null ? currentUser.getFirstName() : "");
        view.tfLastName.setText(currentUser.getLastName() != null ? currentUser.getLastName() : "");
        view.tfEmail.setText(currentUser.getEmail() != null ? currentUser.getEmail() : "");

        // Load address from database
        List<Address> addresses = addressDAO.findByUserId(currentUser.getUserID());

        if (!addresses.isEmpty()) {
            // Get the default shipping address or the first one
            currentAddress = addresses.stream()
                .filter(Address::isDefaultShipping)
                .findFirst()
                .orElse(addresses.get(0));

            // Populate address fields
            view.tfStreet.setText(currentAddress.getStreet() != null ? currentAddress.getStreet() : "");
            view.tfCity.setText(currentAddress.getCity() != null ? currentAddress.getCity() : "");
            view.tfPostalCode.setText(currentAddress.getPostalCode() != null ? currentAddress.getPostalCode() : "");
            view.tfCountry.setText(currentAddress.getCountry() != null ? currentAddress.getCountry() : "");
        } else {
            // No address found, initialize empty address
            currentAddress = new Address();
            currentAddress.setUserID(currentUser.getUserID());
            currentAddress.setDefaultShipping(true);
        }

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
            saveUserData();
        });

        // connect tab order history
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

    /**
     * Save user data to database
     */
    private void saveUserData() {
        if (currentUser == null) {
            JOptionPane.showMessageDialog(view,
                "No user logged in!",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Update user info
            currentUser.setFirstName(view.tfFirstName.getText().trim());
            currentUser.setLastName(view.tfLastName.getText().trim());
            // Email is typically not editable after registration

            // Update user in database
            boolean userUpdated = userDAO.update(currentUser);

            // Update address info
            currentAddress.setStreet(view.tfStreet.getText().trim());
            currentAddress.setCity(view.tfCity.getText().trim());
            currentAddress.setPostalCode(view.tfPostalCode.getText().trim());
            currentAddress.setCountry(view.tfCountry.getText().trim());

            boolean addressUpdated;
            if (currentAddress.getAid() == null || currentAddress.getAid().isEmpty()) {
                // Generate new AID for new address
                currentAddress.setAid("A" + System.currentTimeMillis());
                addressUpdated = addressDAO.insert(currentAddress);
            } else {
                addressUpdated = addressDAO.update(currentAddress);
            }

            if (userUpdated && addressUpdated) {
                view.setEditing(false);
                JOptionPane.showMessageDialog(view,
                    "Profile updated successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);

                // Update session
                UserSession.getInstance().login(currentUser);
            } else {
                JOptionPane.showMessageDialog(view,
                    "Failed to update profile. Please try again.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(view,
                "Error updating profile: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    // Snapshot / Restore
    private void snapshot() {
        sFirst      = view.tfFirstName.getText();
        sLast       = view.tfLastName.getText();
        sStreet     = view.tfStreet.getText();
        sCity       = view.tfCity.getText();
        sPostalCode = view.tfPostalCode.getText();
        sCountry    = view.tfCountry.getText();
    }

    private void restore() {
        view.tfFirstName.setText(sFirst);
        view.tfLastName.setText(sLast);
        view.tfStreet.setText(sStreet);
        view.tfCity.setText(sCity);
        view.tfPostalCode.setText(sPostalCode);
        view.tfCountry.setText(sCountry);
    }
}
