import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PaymentDAO 
{
    private Connection con;

    public PaymentDAO(Connection con) 
    {
        this.con = con;
    }

    public void makePayment(String passengerId, String ticketNo, double amount, String method) 
    {
        try (PreparedStatement ps = con.prepareStatement("INSERT INTO payment(Passenger_ID, Ticket_No, Amount, Payment_Method, Payment_Status) VALUES(?,?,?,?,?)")) 
        {
            ps.setString(1, passengerId);
            ps.setString(2, ticketNo);
            ps.setDouble(3, amount);
            ps.setString(4, method);
            ps.setString(5, "Paid");
            ps.executeUpdate();
        }

        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }

    public ResultSet getPaymentsByPassenger(String passengerId) {
        try 
        {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM payment WHERE Passenger_ID=?");
            ps.setString(1, passengerId);
            return ps.executeQuery();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            return null;
        }
    }

    public void refundPayment(String ticketNo) 
    {
        try (PreparedStatement ps = con.prepareStatement("UPDATE payment SET status='Refunded' WHERE Tiicket_No=?"))
        {
            ps.setString(1, ticketNo);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

public void showPaymentsByPassenger(String passengerId) {
    try {
        String sql = "SELECT * FROM payment WHERE Passenger_ID = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, passengerId);

        ResultSet rs = ps.executeQuery();

        String[] columns = {"Payment ID", "Passenger ID", "Ticket No","Amount", "Method", "Status" };

        DefaultTableModel model = new DefaultTableModel(columns, 0);

        boolean found = false;

        while (rs.next()) 
        {
            found = true;
            model.addRow(new Object[]{rs.getString("Payment_ID"),rs.getString("Passenger_ID"), rs.getString("Ticket_No"),  rs.getDouble("Amount"),rs.getString("Payment_Method"), rs.getString("Payment_Status")});
        }

        if (!found) 
        {
            JOptionPane.showMessageDialog(null, "No payment history found for this passenger!");
            return;
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        JFrame frame = new JFrame("My Payment History");
        frame.setSize(600, 400);
        frame.add(scrollPane);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        rs.close();
        ps.close();

    } 
    catch (Exception e)
    {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error loading payments!");
    }
}
}