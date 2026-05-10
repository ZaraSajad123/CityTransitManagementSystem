import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

class OperatorView extends JFrame
{
    private String operatorID;

    private JButton revenueBtn;
    private JButton loadBtn;
    private JButton busesBtn;
    private JButton reportsBtn;

    public OperatorView(String operatorID)
    {
        this.operatorID = operatorID;

        setTitle("Operator Dashboard");
        setSize(500, 300);
        setLayout(new GridLayout(4, 1, 10, 10));

        Color blue = new Color(25, 42, 86);
        Color teal = new Color(0, 168, 150);

        revenueBtn = new JButton("View Revenue");
        loadBtn = new JButton("Passenger Load");
        busesBtn = new JButton("Manage Buses");
        reportsBtn = new JButton("Reports");

        style(revenueBtn, blue);
        style(loadBtn, teal);
        style(busesBtn, blue);
        style(reportsBtn, teal);

        add(revenueBtn);
        add(loadBtn);
        add(busesBtn);
        add(reportsBtn);

        revenueBtn.addActionListener(new RevenueHandler());
        loadBtn.addActionListener(new LoadHandler());
        busesBtn.addActionListener(new BusesHandler());
        reportsBtn.addActionListener(new ReportsHandler());

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void style(JButton btn, Color color)
    {
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private class RevenueHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            new RevenueView();
        }
    }

    private class LoadHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            OperatorDAO dao = new OperatorDAO(DBConnection.getConnection());
            dao.showPassengerLoad();
        }
    }

    private class BusesHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            OperatorDAO dao = new OperatorDAO(DBConnection.getConnection());
            dao.showAllBuses();
        }
    }

    private class ReportsHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            OperatorDAO dao = new OperatorDAO(DBConnection.getConnection());
            dao.showReports();
        }
    }
}