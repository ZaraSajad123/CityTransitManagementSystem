import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class Ticket 
{

    private String ticket_No;
    private String passenger_ID;
    private String bus_ID;
    private String boarding_Point;
    private String destination_Point;
    private String seat_Number;
    private String journey_Date;
    private double price;
    private String issue_DateTime;

    public Ticket() {}

    public Ticket(String ticket_No, String passenger_ID, String bus_ID, String boarding_Point, String destination_Point,String seat_Number, String journey_Date, double price, String issue_DateTime)
     {

        this.ticket_No = ticket_No;
        this.passenger_ID = passenger_ID;
        this.bus_ID = bus_ID;
        this.boarding_Point = boarding_Point;
        this.destination_Point = destination_Point;
        this.seat_Number = seat_Number;
        this.journey_Date = journey_Date;
        this.price = price;
        this.issue_DateTime = issue_DateTime;
    }

    public String getTicketNo() 
    { 
        return ticket_No;
    }

    public String getPassengerID() 
    {
        return passenger_ID;
    }

    public String getBusID() 
    {
        return bus_ID; 
    }

    public String getBoardingPoint() 
    { 
        return boarding_Point;
    }

    public String getDestinationPoint() 
    {
        return destination_Point; 
    }

    public String getSeatNumber() 
    { 
        return seat_Number;
    }

    public String getJourneyDate() 
    {
     return journey_Date;
    }

    public double getPrice() 
    { 
        return price; 
    }

    public String getIssueDateTime() 
    {
        return issue_DateTime; 
    }


 static class TicketSystem implements ActionListener 
 {

        Bus bus;        
        TicketDAO dao;
        JComboBox busDropdown, boardingDropdown, destinationDropdown;
        Connection con;
        String passengerName, passengerCNIC, passengerPhone;
        JTextField ticketNoField;

        TicketSystem(Bus bus, Connection con,  JComboBox busDropdown,JComboBox boardingDropdown,JComboBox destinationDropdown,  String passengerName,  String passengerCNIC, String passengerPhone,  JTextField ticketNoField) 
        {
            this.bus = bus;
            this.con = con;
            this.dao = new TicketDAO(con);
            this.busDropdown = busDropdown;
            this.boardingDropdown = boardingDropdown;
            this.destinationDropdown = destinationDropdown;
            this.passengerName = passengerName;
            this.passengerCNIC = passengerCNIC;
            this.passengerPhone = passengerPhone;
            this.ticketNoField = ticketNoField;
        }

        public void actionPerformed(ActionEvent ae) 
        {

            String click = ae.getActionCommand();

            if (click.equals("Book Ticket"))
             {

                if (bus.bookSeat())
                 {

                    String busName = (String) busDropdown.getSelectedItem();
                    String busId = dao.getBusIdByName(busName);

                    String seatNo = "S-01";

                    try 
                    {
                        String sql = "SELECT Seat_Number FROM Ticket WHERE Bus_ID = ? ORDER BY Seat_Number DESC LIMIT 1";
                        PreparedStatement ps = con.prepareStatement(sql);
                        ps.setString(1, busId);
                        ResultSet rs = ps.executeQuery();

                        if (rs.next())
                         {
                            String lastSeat = rs.getString("Seat_Number");
                            int num = Integer.parseInt(lastSeat.split("-")[1]);
                            seatNo = String.format("S-%02d", num + 1);
                        }

                        rs.close();
                        ps.close();
                    } 

                    catch (Exception ex) 
                    {
                        ex.printStackTrace();
                    }

                    bus.assignSeat();

                    String boarding = (String) boardingDropdown.getSelectedItem();
                    String destination = (String) destinationDropdown.getSelectedItem();
                    String passengerIDFinal =dao.insertPassenger(passengerCNIC, passengerName, passengerPhone);
                    String ticketNo = dao.generateTicketId();
                    String journeyDate = LocalDateTime.now().toLocalDate().toString();
                    String issueDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

                    Ticket ticket = new Ticket(ticketNo,passengerIDFinal,busId,boarding,destination,seatNo,journeyDate,500.0,issueDateTime);

                    dao.insertTicket(ticket, bus);

                    generateTicket(passengerName, passengerCNIC, passengerPhone,busName, boarding, destination, seatNo);

                    JOptionPane.showMessageDialog(null, "Ticket Booked Successfully!");
                }
            }

            if (click.equals("Cancel Ticket")) 
            {
                String ticketNo = ticketNoField.getText();
                dao.cancelTicket(ticketNo, bus);
            }
        }
    }

   
    public static void main(String args[]) 
    {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(  "jdbc:mysql://localhost:3306/city_transit", "root",  "#GreatZara1" );

            Bus bus_1 = new Bus("B-01", "Daewoo", null, 40);

            JFrame frame = new JFrame("Ticket System");
            frame.setSize(600, 600);
            frame.setLayout(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JLabel nameLabel = new JLabel("Passenger Name:");
            nameLabel.setBounds(50, 50, 150, 30);
            JTextField nameField = new JTextField();
            nameField.setBounds(200, 50, 150, 30);

            JLabel cnicLabel = new JLabel("CNIC:");
            cnicLabel.setBounds(50, 100, 150, 30);
            JTextField cnicField = new JTextField();
            cnicField.setBounds(200, 100, 150, 30);

            JLabel phoneLabel = new JLabel("Phone:");
            phoneLabel.setBounds(50, 150, 150, 30);
            JTextField phoneField = new JTextField();
            phoneField.setBounds(200, 150, 150, 30);

            JLabel ticketNoLabel = new JLabel("Ticket No (Cancel):");
            ticketNoLabel.setBounds(50, 350, 150, 30);
            JTextField ticketNoField = new JTextField();
            ticketNoField.setBounds(200, 350, 150, 30);

            TicketDAO dao = new TicketDAO(con);

            JComboBox busDropdown = new JComboBox(dao.getBusNames());
            busDropdown.setBounds(200, 200, 150, 30);

            JComboBox boardingDropdown = new JComboBox(dao.getBoardingPoints());
            boardingDropdown.setBounds(200, 250, 150, 30);

            JComboBox destinationDropdown = new JComboBox(dao.getDestinationPoints());
            destinationDropdown.setBounds(200, 300, 150, 30);

            JButton book = new JButton("Book Ticket");
            book.setBounds(100, 400, 150, 50);

            JButton cancel = new JButton("Cancel Ticket");
            cancel.setBounds(300, 400, 150, 50);

            frame.add(nameLabel);
            frame.add(nameField);
            frame.add(cnicLabel);
            frame.add(cnicField);
            frame.add(phoneLabel);
            frame.add(phoneField);
            frame.add(ticketNoLabel);
            frame.add(ticketNoField);

            frame.add(new JLabel("Select Bus:")).setBounds(50, 200, 150, 30);
            frame.add(busDropdown);

            frame.add(new JLabel("Boarding Point:")).setBounds(50, 250, 150, 30);
            frame.add(boardingDropdown);

            frame.add(new JLabel("Destination:")).setBounds(50, 300, 150, 30);
            frame.add(destinationDropdown);

            frame.add(book);
            frame.add(cancel);

            
            Ticket.TicketSystem handler = new Ticket.TicketSystem(bus_1, con,  busDropdown,  boardingDropdown, destinationDropdown, nameField.getText(), cnicField.getText(), phoneField.getText(), ticketNoField );

            book.addActionListener(handler);
            cancel.addActionListener(handler);

            frame.setVisible(true);

        } 

        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void generateTicket(String name, String cnic, String phone,String bus, String boarding,String destination, String seatNo)
     {
        System.out.println("\n===== TICKET =====");
        System.out.println("Name: " + name);
        System.out.println("CNIC: " + cnic);
        System.out.println("Phone: " + phone);
        System.out.println("Bus: " + bus);
        System.out.println("From: " + boarding);
        System.out.println("To: " + destination);
        System.out.println("Seat: " + seatNo);
        System.out.println("====================");
    }
}