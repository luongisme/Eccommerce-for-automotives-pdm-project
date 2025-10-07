package com.UI.Profile;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/** Profile page UI (header title + tabs + card form). */
public class ProfileScreen extends JPanel {

    // Tabs + content
    protected final JToggleButton tabProfile = new JToggleButton("Profile Information");
    protected final JToggleButton tabOrders  = new JToggleButton("Order History");
    private final CardLayout contentCards = new CardLayout();
    private final JPanel content = new JPanel(contentCards);

    // Buttons
    final JButton btnEdit   = new JButton("Edit profile");
    final JButton btnSave   = new JButton("Save");
    final JButton btnCancel = new JButton("Cancel");

    // Fields (expose cho controller)
    final JTextField tfFirstName = new JTextField("Admin");
    final JTextField tfLastName  = new JTextField("Pro");
    final JTextField tfEmail     = new JTextField("admin@gmail.com");
    final JTextField tfPhone     = new JTextField("123456789");

    final JTextField tfStreet   = new JTextField("123 Main Street");
    final JTextField tfCity     = new JTextField("Thu Duc");
    final JTextField tfDistrict = new JTextField("Tan Lap");
    final JTextField tfZip      = new JTextField("1231234321");
    final JTextField tfCountry  = new JTextField("Viet Nam");

    private final List<JTextField> editableFields = new ArrayList<>();

    public ProfileScreen() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        add(buildTitleBar(), BorderLayout.NORTH);
        add(buildContent(), BorderLayout.CENTER);

        // default states
        ButtonGroup g = new ButtonGroup();
        g.add(tabProfile); g.add(tabOrders);
        tabProfile.setSelected(true);
        styleTabs();
        setEditing(false);

        tabProfile.addActionListener(e -> { styleTabs(); contentCards.show(content, "profile");});
        tabOrders.addActionListener(e ->  { styleTabs(); contentCards.show(content, "orders"); });
    }

    //header title + tabs
    private JComponent buildTitleBar() {
        JPanel wrap = new JPanel();
        wrap.setOpaque(false);
        wrap.setLayout(new BoxLayout(wrap, BoxLayout.Y_AXIS));
        wrap.setBorder(new EmptyBorder(16, 24, 8, 24));

        JLabel title = new JLabel("My Account");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 24f));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel tabs = new JPanel(new GridLayout(1, 2, 8, 0));
        tabs.setBorder(new EmptyBorder(12, 0, 12, 0));
        tabs.setOpaque(false);
        tabs.add(tabProfile);
        tabs.add(tabOrders);
        tabs.setAlignmentX(Component.LEFT_ALIGNMENT);

        wrap.add(title);
        wrap.add(tabs);
        return wrap;
    }

    /* --------- main content --------- */
    private JComponent buildContent() {
        JPanel profile = buildProfileCard();
        JPanel orders  = buildOrdersPlaceholder();

        content.add(profile, "profile");
        content.add(orders,  "orders");

        JPanel outer = new JPanel(new BorderLayout());
        outer.setOpaque(false);
        outer.setBorder(new EmptyBorder(0, 24, 24, 24));
        outer.add(content, BorderLayout.CENTER);
        return outer;
    }

    /* --------- profile card --------- */
    private JPanel buildProfileCard() {
        RoundedPanel card = new RoundedPanel(12, Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(0xDD,0xDD,0xDD)),
        new EmptyBorder(16,16,16,16)
        ));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setOpaque(false);

        // header row inside card
        JPanel head = new JPanel(new BorderLayout());
        head.setOpaque(false);
        JLabel iconTitle = new JLabel("  Profile Information");
        iconTitle.setFont(iconTitle.getFont().deriveFont(Font.BOLD, 25f));
        // nút nhóm: Edit | Save | Cancel
        JPanel btns = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        btns.setOpaque(false);
        styleSmall(btnEdit, true);
        styleSmall(btnSave, false);
        styleSmall(btnCancel, false);
        btns.add(btnEdit);
        btns.add(btnSave);
        btns.add(btnCancel);

        head.add(iconTitle, BorderLayout.WEST);
        head.add(btns, BorderLayout.EAST);

        // sections
        JPanel sectionPersonal = section("Personal Information");
        sectionPersonal.add(twoCols(
        field("First name", tfFirstName),
        field("Last name",  tfLastName)
        ));

        JPanel sectionContact = section("Contact Information");
        sectionContact.add(oneCol(field("Email Address", tfEmail)));
        JLabel emailNote = new JLabel(" ");
        emailNote.setForeground(new Color(0x6B,0x72,0x80));
        emailNote.setFont(emailNote.getFont().deriveFont(Font.PLAIN, 11f));
        emailNote.setBorder(new EmptyBorder(4, 2, 0, 2));
        emailNote.setHorizontalAlignment(SwingConstants.LEFT); 
        emailNote.setAlignmentX(Component.LEFT_ALIGNMENT);      // <- key for BoxLayout (Y)
  

        sectionContact.add(emailNote);
        sectionContact.add(oneCol(field("Phone Number", tfPhone)));

        JPanel sectionAddress = section("Shipping Address");
        sectionAddress.add(oneCol(field("Street Address", tfStreet)));
        sectionAddress.add(twoCols(
        field("City",     tfCity),
        field("District", tfDistrict)
        ));
        sectionAddress.add(twoCols(
        field("Zip Code", tfZip),
        field("Country",  tfCountry)
        ));

        // collect editable fields (email readonly)
        editableFields.add(tfFirstName);
        editableFields.add(tfLastName);
        editableFields.add(tfPhone);
        editableFields.add(tfStreet);
        editableFields.add(tfCity);
        editableFields.add(tfDistrict);
        editableFields.add(tfZip);
        editableFields.add(tfCountry);

        // assemble card
        card.add(head);
        card.add(Box.createVerticalStrut(12));
        card.add(sectionPersonal);
        card.add(sectionContact);
        card.add(sectionAddress);
        return card;
    }

    private JPanel buildOrdersPlaceholder() {
        RoundedPanel card = new RoundedPanel(12, Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(0xDD,0xDD,0xDD)),
        new EmptyBorder(24,24,24,24)
        ));
        card.setLayout(new BorderLayout());
        JLabel placeholder = new JLabel("Order History (UI only) — no data yet", SwingConstants.CENTER);
        placeholder.setForeground(new Color(0x6B,0x72,0x80));
        placeholder.setFont(placeholder.getFont().deriveFont(Font.PLAIN, 14f));
        card.add(placeholder, BorderLayout.CENTER);
        JPanel outer = new JPanel(new BorderLayout());
        outer.setOpaque(false);
        outer.add(card, BorderLayout.CENTER);
        return outer;
    }

    /* ---------- small builders ---------- */

    private JPanel section(String title) {
        JPanel wrap = new JPanel();
        wrap.setOpaque(false);
        wrap.setLayout(new BoxLayout(wrap, BoxLayout.Y_AXIS));
        wrap.setBorder(new EmptyBorder(12, 8, 8, 8));

        JLabel t = new JLabel(title, SwingConstants.CENTER);
        t.setFont(t.getFont().deriveFont(Font.BOLD, 14f));
        t.setAlignmentX(Component.CENTER_ALIGNMENT);
        t.setBorder(new EmptyBorder(0, 0, 8, 0));
        t.setMaximumSize(new Dimension(Integer.MAX_VALUE, t.getPreferredSize().height)); // <-- add this

        wrap.add(t);
        return wrap;
    }



    private JPanel oneCol(JPanel fieldPanel) {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);
        p.add(fieldPanel, BorderLayout.CENTER);
        p.setBorder(new EmptyBorder(0, 0, 10, 0));
        return p;
    }

    private JPanel twoCols(JPanel left, JPanel right) {
        JPanel grid = new JPanel(new GridBagLayout());
        grid.setOpaque(false);

        GridBagConstraints gc = new GridBagConstraints();
        gc.gridy = 0;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 0.5;

        // left column
        gc.gridx = 0;
        gc.insets = new Insets(0, 0, 10, 8);   // bottom + right gap
        grid.add(wrapFill(left), gc);

        // right column
        gc.gridx = 1;
        gc.insets = new Insets(0, 8, 10, 0);   // bottom + left gap
        grid.add(wrapFill(right), gc);

        return grid;
    }

    private JPanel wrapFill(JPanel inner) {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);
        p.add(inner, BorderLayout.CENTER);     // makes the inner field panel fill column width
        return p;
    }


    private JPanel field(String label, JTextField input) {
        JPanel box = new JPanel();
        box.setOpaque(false);
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
        box.setAlignmentX(Component.LEFT_ALIGNMENT);        // key: left align the stack itself

        JLabel lb = new JLabel(label);
        lb.setAlignmentX(Component.LEFT_ALIGNMENT);         // key: left align the label
        lb.setFont(lb.getFont().deriveFont(Font.BOLD, 12f));
        lb.setBorder(new EmptyBorder(0, 2, 6, 2));          // a bit more bottom gap

        styleTextField(input);
        input.setAlignmentX(Component.LEFT_ALIGNMENT);      // key: left align the input
        input.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36)); // keep full width

        box.add(lb);
        box.add(input);
        return box;
    }


    private void styleTextField(JTextField f) {
        f.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(0xE5,0xE7,0xEB)),
        new EmptyBorder(8,10,8,10)));
        f.setBackground(new Color(0xF7,0xF8,0xFA));
        f.setOpaque(true);
        f.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
    }

    private void styleSmall(JButton b, boolean primary) {
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        if (primary) {
            b.setForeground(Color.WHITE);
            b.setBackground(Color.BLACK);
        } else {
            b.setBackground(new Color(0xF3,0xF4,0xF6));
        }
        b.setBorder(BorderFactory.createEmptyBorder(6,12,6,12));
    }

    private void styleTabs() {
        styleOneTab(tabProfile, tabProfile.isSelected());
        styleOneTab(tabOrders, tabOrders.isSelected());
    }

    private void styleOneTab(JToggleButton b, boolean active) {
        b.setFocusPainted(false);
        b.setContentAreaFilled(true);
        b.setOpaque(true);
        b.setBorder(BorderFactory.createEmptyBorder(8,16,8,16));
        b.setHorizontalAlignment(SwingConstants.CENTER);
        if (active) {
            b.setBackground(Color.WHITE);
            b.setBorder(BorderFactory.createLineBorder(new Color(0xDD,0xDD,0xDD)));
        } else {
            b.setBackground(new Color(0xF3,0xF4,0xF6));
            b.setBorder(BorderFactory.createEmptyBorder(8,16,8,16));
        }
    }


    /** Bật/tắt chế độ chỉnh sửa (email luôn readonly). */
    public void setEditing(boolean editing) {
        tfEmail.setEditable(false);
        tfEmail.setBackground(new Color(0xF7,0xF8,0xFA));
        for (JTextField f : editableFields) {
            f.setEditable(editing);
            f.setBackground(editing ? Color.WHITE : new Color(0xF7,0xF8,0xFA));
        }
        btnEdit.setVisible(!editing);
        btnSave.setVisible(editing);
        btnCancel.setVisible(editing);
    }

    /* --------- simple rounded panel for card --------- */
    static class RoundedPanel extends JPanel {
        private final int arc; private final Color bg;
        RoundedPanel(int arc, Color bg){ this.arc = arc; this.bg = bg; setOpaque(false); }
        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2=(Graphics2D)g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            // subtle drop shadow
            g2.setColor(new Color(0,0,0,28));
            g2.fillRoundRect(3,5,getWidth()-6,getHeight()-6, arc+4, arc+4);
            // body
            g2.setColor(bg);
            g2.fillRoundRect(0,0,getWidth(),getHeight(), arc, arc);
            g2.dispose();
            super.paintComponent(g);
        }
    }
}
