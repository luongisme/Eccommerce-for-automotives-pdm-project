package com.UI.OrderHistory;

import com.Main.AppFrame;
import com.Main.Screen;
import com.UI.store.StoreScreen;

import javax.swing.*;
import java.awt.*;
import com.UI.*;
import com.UI.Profile.ProfilePage;

import java.util.List;
import java.util.function.Supplier;

public class OrderHistoryScreen extends Screen {
    private OrderHistoryPanel view;
    private OrderHistoryController controller;

    public OrderHistoryScreen(AppFrame appFrame) {
        super(appFrame);
        initUI();
    }

    @Override
    protected void initUI() {
        this.panel = new JPanel(new BorderLayout());
        view = new OrderHistoryPanel();

        // Mock data (để bạn thấy chế độ LIST khi test). Set thành List.of() để thấy EMPTY.
        Supplier<List<OrderHistoryPanel.OrderVM>> dataSource = () -> List.of(); 
       

        Runnable goShop = () -> appFrame.setScreen(new StoreScreen(appFrame));

        controller = new OrderHistoryController(view, dataSource, goShop);
        controller.refresh();

        view.onTabProfile(() -> appFrame.setScreen(new ProfilePage(appFrame)));

        panel.add(view, BorderLayout.CENTER);
    }
}
