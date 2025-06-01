package gui;

import threadpool.Task;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class FactoryGUI extends JFrame {
    private JSlider body_supplier_speed;
    private JSlider motor_supplier_speed;
    private JSlider accessory_supplier_speed;
    private JSlider dealer_speed;

    private JLabel body_speed_label;
    private JLabel motor_speed_label;
    private JLabel accessory_speed_label;
    private JLabel dealer_speed_label;

    private JLabel body_storage_label;
    private JLabel motor_storage_label;
    private JLabel accessory_storage_label;
    private JLabel car_storage_label;

    private JLabel sold_cars_label;
    private JProgressBar car_storage_pb;

    private final int body_storage_capacity;
    private final int motor_storage_capacity;
    private final int accessory_storage_capacity;
    private final int car_storage_capacity;

    private final Task supplyBodies;
    private final Task supplyMotos;
    private final Task supplyAccessories;
    private final Task orderSell;

    public FactoryGUI(Task supplyBodies, Task supplyMotos, Task supplyAccessories, Task orderSell,
                      int body_storage_capacity, int motor_storage_capacity,
                      int accessory_storage_capacity, int car_storage_capacity,
                      int body_supplier_delay, int motor_supplier_delay,
                      int accessory_supplier_delay, int dealer_delay) {

        this.body_storage_capacity = body_storage_capacity;
        this.motor_storage_capacity = motor_storage_capacity;
        this.accessory_storage_capacity = accessory_storage_capacity;
        this.car_storage_capacity = car_storage_capacity;

        this.supplyBodies = supplyBodies;
        this.supplyMotos = supplyMotos;
        this.supplyAccessories = supplyAccessories;
        this.orderSell = orderSell;

        setTitle("Factory Control Panel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setMinimumSize(new Dimension(900, 650));
        setLayout(new BorderLayout(15, 15));
        getContentPane().setBackground(new Color(30, 30, 40));

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Header panel with gradient
        JPanel headerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, new Color(0, 120, 215), 0, getHeight(), new Color(0, 80, 180));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        JLabel titleLabel = new JLabel("AUTOMOBILE FACTORY CONTROL PANEL");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);

        // Panels
        JPanel stats_panel = createStatsPanel();
        JPanel control_panel = createControlPanel(body_supplier_delay, motor_supplier_delay,
                accessory_supplier_delay, dealer_delay);

        add(headerPanel, BorderLayout.NORTH);
        add(stats_panel, BorderLayout.CENTER);
        add(control_panel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
    }

    private JPanel createStatsPanel() {
        JPanel stats_panel = new JPanel();
        stats_panel.setLayout(new GridLayout(6, 1, 10, 10));
        stats_panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(60, 60, 80), 1),
                new EmptyBorder(20, 20, 20, 20)
        ));
        stats_panel.setBackground(new Color(40, 40, 50));

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 16);
        Color labelColor = new Color(200, 200, 220);

        body_storage_label = createStyledLabel("Body storage: 0/" + body_storage_capacity, labelFont, labelColor);
        motor_storage_label = createStyledLabel("Motor storage: 0/" + motor_storage_capacity, labelFont, labelColor);
        accessory_storage_label = createStyledLabel("Accessory storage: 0/" + accessory_storage_capacity, labelFont, labelColor);
        car_storage_label = createStyledLabel("Car storage: 0/" + car_storage_capacity, labelFont, labelColor);

        car_storage_pb = new JProgressBar(0, car_storage_capacity);
        styleProgressBar(car_storage_pb);

        sold_cars_label = createStyledLabel("Sold cars: 0", labelFont, labelColor);

        stats_panel.add(body_storage_label);
        stats_panel.add(motor_storage_label);
        stats_panel.add(accessory_storage_label);
        stats_panel.add(car_storage_label);
        stats_panel.add(car_storage_pb);
        stats_panel.add(sold_cars_label);

        return stats_panel;
    }

    private JPanel createControlPanel(int body_delay, int motor_delay, int accessory_delay, int dealer_delay) {
        JPanel control_panel = new JPanel();
        control_panel.setLayout(new GridLayout(4, 2, 20, 20));
        control_panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(60, 60, 80), 1),
                new EmptyBorder(20, 20, 20, 20)
        ));
        control_panel.setBackground(new Color(40, 40, 50));

        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        Color labelColor = new Color(200, 200, 220);

        body_speed_label = createStyledLabel("Body supplier delay (ms): " + body_delay, labelFont, labelColor);
        body_supplier_speed = createSlider(100, 10000, body_delay, body_speed_label);

        motor_speed_label = createStyledLabel("Motor supplier delay (ms): " + motor_delay, labelFont, labelColor);
        motor_supplier_speed = createSlider(100, 10000, motor_delay, motor_speed_label);

        accessory_speed_label = createStyledLabel("Accessory supplier delay (ms): " + accessory_delay, labelFont, labelColor);
        accessory_supplier_speed = createSlider(100, 10000, accessory_delay, accessory_speed_label);

        dealer_speed_label = createStyledLabel("Dealer delay (ms): " + dealer_delay, labelFont, labelColor);
        dealer_speed = createSlider(100, 10000, dealer_delay, dealer_speed_label);

        control_panel.add(body_speed_label);
        control_panel.add(body_supplier_speed);
        control_panel.add(motor_speed_label);
        control_panel.add(motor_supplier_speed);
        control_panel.add(accessory_speed_label);
        control_panel.add(accessory_supplier_speed);
        control_panel.add(dealer_speed_label);
        control_panel.add(dealer_speed);

        return control_panel;
    }

    private JLabel createStyledLabel(String text, Font font, Color color) {
        JLabel label = new JLabel(text, JLabel.LEFT);
        label.setFont(font);
        label.setForeground(color);
        label.setBackground(new Color(50, 50, 60));
        label.setOpaque(true);
        label.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(80, 80, 100), 1),
                new EmptyBorder(10, 15, 10, 15)
        ));
        return label;
    }

    private void styleProgressBar(JProgressBar progressBar) {
        progressBar.setStringPainted(true);
        progressBar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        progressBar.setValue(0);
        progressBar.setString("0/" + car_storage_capacity);
        progressBar.setForeground(new Color(0, 150, 136));
        progressBar.setBackground(new Color(50, 50, 60));
        progressBar.setBorder(BorderFactory.createLineBorder(new Color(80, 80, 100), 1));
    }

    private JSlider createSlider(int min, int max, int value, JLabel label) {
        JSlider slider = new JSlider(min, max, value);
        slider.setMajorTickSpacing((max - min) / 10);
        slider.setMinorTickSpacing((max - min) / 20);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setBackground(new Color(40, 40, 50));
        slider.setForeground(new Color(200, 200, 220));

        slider.setUI(new javax.swing.plaf.basic.BasicSliderUI(slider) {
            @Override
            public void paintTrack(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                Rectangle trackBounds = trackRect;
                int trackHeight = 10;

                g2d.setColor(new Color(50, 50, 60));
                g2d.fillRoundRect(trackBounds.x, trackBounds.y + (trackBounds.height - trackHeight)/2,
                        trackBounds.width, trackHeight, trackHeight, trackHeight);

                int fillWidth = thumbRect.x + thumbRect.width/2 - trackBounds.x;
                g2d.setColor(new Color(0, 120, 215));
                g2d.fillRoundRect(trackBounds.x, trackBounds.y + (trackBounds.height - trackHeight)/2,
                        fillWidth, trackHeight, trackHeight, trackHeight);
            }

            @Override
            protected Dimension getThumbSize() {
                return new Dimension(20, 20);
            }

            @Override
            public void paintThumb(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2d.setColor(new Color(0, 120, 215));
                g2d.fillOval(thumbRect.x, thumbRect.y, thumbRect.width, thumbRect.height);

                g2d.setColor(new Color(200, 200, 220));
                g2d.fillOval(thumbRect.x + 4, thumbRect.y + 4, thumbRect.width - 8, thumbRect.height - 8);
            }
        });

        slider.addChangeListener(e -> {
            int slider_value = slider.getValue();
            label.setText(label.getText().split(":")[0] + ": " + slider_value);

            if (e.getSource() == body_supplier_speed) {
                supplyBodies.setParameters(slider_value);
            } else if (e.getSource() == motor_supplier_speed) {
                supplyMotos.setParameters(slider_value);
            } else if (e.getSource() == accessory_supplier_speed) {
                supplyAccessories.setParameters(slider_value);
            } else if (e.getSource() == dealer_speed) {
                orderSell.setParameters(slider_value);
            }
        });

        return slider;
    }

    public void updateStats(int body_storage, int motor_storage, int accessory_storage,
                            int car_storage, int sold_cars) {
        SwingUtilities.invokeLater(() -> {
            body_storage_label.setText("Body storage: " + body_storage + "/" + body_storage_capacity);
            motor_storage_label.setText("Motor storage: " + motor_storage + "/" + motor_storage_capacity);
            accessory_storage_label.setText("Accessory storage: " + accessory_storage + "/" + accessory_storage_capacity);
            car_storage_label.setText("Car storage: " + car_storage + "/" + car_storage_capacity);
            sold_cars_label.setText("Sold cars: " + sold_cars);
            car_storage_pb.setValue(car_storage);
            car_storage_pb.setString(car_storage + "/" + car_storage_capacity);

            double ratio = (double)car_storage / car_storage_capacity;
            if (ratio > 0.8) {
                car_storage_pb.setForeground(new Color(239, 83, 80));
            } else if (ratio > 0.6) {
                car_storage_pb.setForeground(new Color(255, 167, 38));
            } else {
                car_storage_pb.setForeground(new Color(0, 150, 136));
            }
        });
    }
}