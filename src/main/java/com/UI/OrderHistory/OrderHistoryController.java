package com.UI.OrderHistory;

import java.util.List;
import java.util.function.Supplier;

public class OrderHistoryController {
    private final OrderHistoryPanel view;
    private final Supplier<List<OrderHistoryPanel.OrderVM>> loadOrders;
    private final Runnable goShop;

    public OrderHistoryController(OrderHistoryPanel view,
                                  Supplier<List<OrderHistoryPanel.OrderVM>> loadOrders,
                                  Runnable goShop) {
        this.view = view;
        this.loadOrders = loadOrders;
        this.goShop = goShop;

        this.view.onReturnToShop(goShop);
    }

    public void refresh() {
        List<OrderHistoryPanel.OrderVM> orders =
                (loadOrders != null) ? loadOrders.get() : List.of();
        view.showOrders(orders);
    }
}
