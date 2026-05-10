import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class Passenger extends User implements BookAble, SeeInfo, Payable {
    private String passenger_ID;
    private String passenger_Phone;
    private String passenger_Name;
    private String passenger_CNIC;
    private String currentTicketNo;
    private double currentAmount;

    

public Passenger()
{
    super("", "", "");
}

public Passenger(String passenger_ID, String passenger_Phone, String passenger_Name, String passenger_CNIC)
 {
    super(passenger_ID, passenger_Name, ""); // adjust to match User’s constructor
    this.passenger_ID = passenger_ID;
    this.passenger_Phone = passenger_Phone;
    this.passenger_Name = passenger_Name;
    this.passenger_CNIC = passenger_CNIC;
}

    // getters and setters...
String getPassengerID() 
{ 
  return passenger_ID;
}

void setPassengerName(String passenger_Name)
 { 
  this.passenger_Name=passenger_Name;
}

String getPassengerName() 
{ 
    return passenger_Name;
}
void setPassengerPhone(String passenger_Phone) 
{ 
    this.passenger_Phone=passenger_Phone; 
}

String getPassengerPhone()
 { 
    return passenger_Phone;
}
void setPassengerCNIC(String passenger_CNIC) 
{ 
    this.passenger_CNIC=passenger_CNIC;
}

String getPassengerCNIC()
{ 
    return passenger_CNIC;
}
    

     @Override
    public void TicketBooking(Ticket ticket, Bus bus) 
    {
         TicketDAO dao = new TicketDAO(DBConnection.getConnection());
         dao.insertTicket(ticket, bus);
         System.out.println("Ticket booked successfully for " + passenger_Name);
    }


    @Override
    public void TicketCancelling(String ticketId, Bus bus) 
    {
        TicketDAO dao = new TicketDAO(DBConnection.getConnection());
        dao.cancelTicket(ticketId, bus);
        System.out.println("Ticket " + ticketId + " cancelled for " + passenger_Name);
    }

    @Override
    public void ViewBookingHistory()
    {
        TicketDAO dao = new TicketDAO(DBConnection.getConnection());
        dao.getTicketsByPassenger(passenger_ID); // no .forEach
    }

    @Override
    public void viewSchedule() 
    {
        ScheduleDAO dao = new ScheduleDAO(DBConnection.getConnection());
        dao.showSchedules();
    }

    public void viewScheduleByBus(String busID) 
    {
        ScheduleDAO dao = new ScheduleDAO(DBConnection.getConnection());
        dao.showSchedulesByBus(busID);
    }

   @Override
    public void viewBusStops() 
    {
        BusStopDAO dao = new BusStopDAO(DBConnection.getConnection());
        dao.showAllStops(); 
    }

    public void viewBusByStops(String stopID)
    {
        BusStopDAO dao = new BusStopDAO(DBConnection.getConnection());
        dao.showBusesByStops(stopID);
    }

   
    public void setCurrentPayment(String ticketNo, double amount) 
    {
        this.currentTicketNo = ticketNo;
        this.currentAmount = amount;
    }


    @Override
   public void processPayment()
    {

        String ticketNo = this.currentTicketNo;   
        double amount   = this.currentAmount;     
        PaymentDAO dao = new PaymentDAO(DBConnection.getConnection());
        dao.makePayment(passenger_ID, ticketNo, amount, "Cash");

        JOptionPane.showMessageDialog(null,"Payment of " + amount + " processed for Ticket " + ticketNo,"Payment Successful", JOptionPane.INFORMATION_MESSAGE);
    }


    @Override
    public void viewPaymentHistory() 
    {
        PaymentDAO dao = new PaymentDAO(DBConnection.getConnection());
        try (ResultSet rs = dao.getPaymentsByPassenger(passenger_ID))
         {
            String[] columns = {"Payment ID", "Ticket No", "Status", "Method", "Amount"};
            DefaultTableModel model = new DefaultTableModel(columns, 0);

            while (rs.next()) 
            {
                model.addRow(new Object[]{ rs.getString("Payment_ID"),rs.getString("Ticket_No"),rs.getString("Payment_Status"),rs.getString("Payment_Method"),rs.getDouble("Amount")});
            }

            JTable table = new JTable(model);
            JScrollPane scrollPane = new JScrollPane(table);

            JFrame frame = new JFrame("Payment History for " + passenger_Name);
            frame.setSize(600, 400);
            frame.add(scrollPane);
            frame.setVisible(true);
        } 

        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }

    @Override
    public void requestRefund(String ticketNo) 
    {
        PaymentDAO dao = new PaymentDAO(DBConnection.getConnection());
        dao.refundPayment(ticketNo);
        JOptionPane.showMessageDialog(null,"Refund requested for Ticket " + ticketNo,"Refund Request", JOptionPane.WARNING_MESSAGE);
    }

    @Override
    public void viewOptions()
    {
        System.out.println("Passenger Options: Book Ticket, Cancel Ticket, View Schedule, View Stops, Payments");
    }
}


