import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

class PassengerView extends JFrame
{
    private String passengerName;
    private String passengerCNIC;
    private String passengerPhone;
    private String passengerID;

    private JButton scheduleBtn;
    private JButton bookingBtn;
    private JButton stopsBtn;
    private JButton paymentBtn;

    public PassengerView(String passengerID)
    {
        this.passengerID = passengerID;
        loadPassengerData();

        setTitle("Passenger Dashboard");
        setSize(500, 300);
        setLayout(new GridLayout(4, 1, 10, 10));

        Color blue = new Color(25, 42, 86);
        Color teal = new Color(0, 168, 150);

        scheduleBtn = new JButton("View Schedule");
        bookingBtn = new JButton("Ticket System (Book/Cancel)");
        stopsBtn = new JButton("View Stops");
        paymentBtn = new JButton("Payments");

        style(scheduleBtn, blue);
        style(bookingBtn, teal);
        style(stopsBtn, blue);
        style(paymentBtn, teal);

        add(scheduleBtn);
        add(bookingBtn);
        add(stopsBtn);
        add(paymentBtn);

        scheduleBtn.addActionListener(new ScheduleHandler());
        bookingBtn.addActionListener(new BookingHandler());
        stopsBtn.addActionListener(new StopsHandler());
        paymentBtn.addActionListener(new PaymentHandler());

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

    private void loadPassengerData()
    {
        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT Passenger_Name, Passenger_CNIC, Passenger_Phone FROM Passengers WHERE Passenger_ID=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, passengerID);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                passengerName = rs.getString(1);
                passengerCNIC = rs.getString(2);
                passengerPhone = rs.getString(3);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class ScheduleHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            new ScheduleDAO(DBConnection.getConnection()).showSchedules();
        }
    }

    private class BookingHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            try {
                Connection con = DBConnection.getConnection();

                JFrame frame = new JFrame("Ticket System");
                frame.setSize(600, 600);
                frame.setLayout(null);

                Bus bus = new Bus("B-01", "Daewoo", null, 40);
                TicketDAO dao = new TicketDAO(con);

                JLabel info = new JLabel("Passenger: " + passengerName + " | CNIC: " + passengerCNIC + " | Phone: " + passengerPhone);
                info.setBounds(50, 20, 500, 30);
                frame.add(info);

                JComboBox<String> busDropdown = new JComboBox<>(dao.getBusNames());
                busDropdown.setBounds(200, 80, 150, 30);

                JComboBox<String> boardingDropdown = new JComboBox<>(dao.getBoardingPoints());
                boardingDropdown.setBounds(200, 130, 150, 30);

                JComboBox<String> destinationDropdown = new JComboBox<>(dao.getDestinationPoints());
                destinationDropdown.setBounds(200, 180, 150, 30);

                JTextField ticketNoField = new JTextField();
                ticketNoField.setBounds(200, 350, 150, 30);

                JButton book = new JButton("Book Ticket");
                JButton cancel = new JButton("Cancel Ticket");

                book.setBounds(100, 400, 150, 50);
                Ticket.TicketSystem handler =
        new Ticket.TicketSystem(
                bus,
                con,
                busDropdown,
                boardingDropdown,
                destinationDropdown,
                passengerName,
                passengerCNIC,
                passengerPhone,
                ticketNoField
        );

book.addActionListener(handler);
cancel.addActionListener(handler);
                cancel.setBounds(300, 400, 150, 50);

                frame.add(busDropdown);
                frame.add(boardingDropdown);
                frame.add(destinationDropdown);
                frame.add(ticketNoField);
                frame.add(book);
                frame.add(cancel);

                frame.setVisible(true);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

   private class StopsHandler implements ActionListener
{
    public void actionPerformed(ActionEvent e)
    {
        try {
            Connection con = DBConnection.getConnection();
            BusStopDAO dao = new BusStopDAO(con);

            JFrame frame = new JFrame("Stop Options");
            frame.setSize(400, 200);
            frame.setLayout(new GridLayout(2, 1, 10, 10));

            JButton byBus = new JButton("Find Stops by Bus");
            JButton byStop = new JButton("Find Buses by Stop");

            frame.add(byBus);
            frame.add(byStop);

            byBus.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent ev)
                {
                    String busID = JOptionPane.showInputDialog("Enter Bus ID:");
                    if (busID != null && !busID.isEmpty())
                    {
                        dao.showStopsByBus(busID);
                    }
                }
            });

            byStop.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent ev)
                {
                    String stopID = JOptionPane.showInputDialog("Enter Stop ID:");
                    if (stopID != null && !stopID.isEmpty())
                    {
                        dao.showBusesByStops(stopID);
                    }
                }
            });

            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

    private class PaymentHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            try {
                new PaymentDAO(DBConnection.getConnection()).showPaymentsByPassenger(passengerID);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}