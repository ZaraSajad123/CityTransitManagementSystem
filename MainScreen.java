import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.io.IOException;

public class MainScreen extends JFrame
{
    private JButton passengerBtn;
    private JButton operatorBtn;

    public MainScreen()
    {
        setTitle("City Transit Management System");
        setSize(650, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel header = new JPanel();
        header.setBackground(new Color(25, 42, 86));
        header.setPreferredSize(new Dimension(650, 80));
        header.setLayout(new BorderLayout());

        JLabel title = new JLabel("City Transit Management System", JLabel.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        header.add(title, BorderLayout.CENTER);

        add(header, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        centerPanel.setBackground(new Color(245, 246, 250));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(25, 40, 25, 40));

        JLabel welcome = new JLabel(
                "<html><center>Welcome to City Transit System<br>" +
                "<span style='color:gray;font-size:12px;'>Manage passengers, routes and operations efficiently</span></center></html>",
                JLabel.CENTER);

        welcome.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        centerPanel.add(welcome, BorderLayout.NORTH);

        JLabel centerIcon = new JLabel();
        ImageIcon icon = loadIcon("download.jfif", 120, 120);

        if (icon != null) {
            centerIcon.setIcon(makeCircularIcon(icon, 120));
            centerIcon.setHorizontalAlignment(JLabel.CENTER);
        }

        centerPanel.add(centerIcon, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 10));
        buttonPanel.setBackground(new Color(245, 246, 250));

        passengerBtn = new JButton("Passenger Login");
        operatorBtn = new JButton("Operator Login");

        styleButton(passengerBtn, new Color(0, 168, 150));
        styleButton(operatorBtn, new Color(25, 42, 86));

        buttonPanel.add(passengerBtn);
        buttonPanel.add(operatorBtn);

        centerPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(centerPanel, BorderLayout.CENTER);

        passengerBtn.addActionListener(new PassengerHandler());
        operatorBtn.addActionListener(new OperatorHandler());

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void styleButton(JButton btn, Color color)
    {
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(180, 45));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private ImageIcon loadIcon(String path, int width, int height)
    {
        try {
            File file = new File(path);
            if (file.exists()) {
                BufferedImage img = ImageIO.read(file);
                Image scaled = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                return new ImageIcon(scaled);
            }
        } catch (IOException e) {}
        return null;
    }

    private ImageIcon makeCircularIcon(ImageIcon icon, int size)
    {
        Image img = icon.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);

        BufferedImage output = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = output.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setClip(new Ellipse2D.Double(0, 0, size, size));
        g2.drawImage(img, 0, 0, null);
        g2.dispose();

        return new ImageIcon(output);
    }

    private class PassengerHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            new PassengerLogin();
        }
    }

    private class OperatorHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            new OperatorLogin();
        }
    }

    public static void main(String[] args)
    {
        new MainScreen();
    }
}