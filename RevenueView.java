import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

class RevenueView extends JFrame {

    private JButton Btntotal;
    private JButton Btndate;
    private JButton Btnroute;

    public RevenueView() {

        setTitle("Revenue Dashboard");
        setSize(400, 300);
        setLayout(new GridLayout(3, 1, 10, 10));

        Btntotal = new JButton("Total Revenue");
        Btndate = new JButton("Date Wise Revenue");
        Btnroute = new JButton("Route Wise Revenue");

        add(Btntotal);
        add(Btndate);
        add(Btnroute);

        Btntotal.addActionListener(new TotalHandler());
        Btndate.addActionListener(new DateHandler());
        Btnroute.addActionListener(new RouteHandler());

        setLocationRelativeTo(null);
        setVisible(true);
    }

    class TotalHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                OperatorDAO dao = new OperatorDAO(DBConnection.getConnection());
                dao.showTotalRevenue();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    class DateHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                OperatorDAO dao = new OperatorDAO(DBConnection.getConnection());
                dao.showDailyRevenue();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    class RouteHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                OperatorDAO dao = new OperatorDAO(DBConnection.getConnection());
                dao.showRouteRevenue();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}