import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class OperatorDAO {
    private Connection con;

    public OperatorDAO(Connection con) {
        this.con = con;
    }

    public void showRevenueReport() 
    {
        try 
        {
            String sql = "SELECT Route_ID, SUM(Amount) AS Total_Revenue " +"FROM Payment p JOIN Ticket t ON p.Ticket_No = t.Ticket_No " + "GROUP BY Route_ID";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            String[] cols = {"Route ID", "Total Revenue"};
            DefaultTableModel model = new DefaultTableModel(cols, 0);

            while (rs.next()) 
            {
                model.addRow(new Object[]{rs.getString("Route_ID"), rs.getDouble("Total_Revenue")});
            }

            JTable table = new JTable(model);
            JFrame frame = new JFrame("Revenue Report");
            frame.add(new JScrollPane(table));
            frame.setSize(400, 300);
            frame.setVisible(true);

            rs.close(); st.close();
        } 
        catch (Exception e) 
        {
         e.printStackTrace();
        }
    }

    
public void showPassengerLoad() {
    try {
        Connection con = DBConnection.getConnection();

        String sql =  "SELECT Bus_ID, COUNT(*) AS Passenger_Count " +"FROM Ticket " +"GROUP BY Bus_ID";

        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);

        String[] cols = {"Bus ID", "Passenger Count"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);

        while (rs.next())
         {
            model.addRow(new Object[]{rs.getString("Bus_ID"),rs.getInt("Passenger_Count")});
        }

        JTable table = new JTable(model);
        JScrollPane pane = new JScrollPane(table);

        JFrame frame = new JFrame("Passenger Load per Bus");
        frame.setSize(500, 300);
        frame.add(pane);
        frame.setVisible(true);

        rs.close();
        st.close();

    } 

    catch (Exception e) 
    {
        e.printStackTrace();
    }
}

    public void showAllBuses() {
        try {
            String sql = "SELECT * FROM Buses";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            String[] cols = {"Bus ID", "Bus Name", "Departure", "Destination"};
            DefaultTableModel model = new DefaultTableModel(cols, 0);

            while (rs.next()) 
            {
                model.addRow(new Object[]{ rs.getString("Bus_ID"),rs.getString("Bus_Name"), rs.getString("Bus_Departure_Point"), rs.getString("Bus_Destination_Point")});
            }

            JTable table = new JTable(model);
            JFrame frame = new JFrame("Bus Management");
            frame.add(new JScrollPane(table));
            frame.setSize(500, 300);
            frame.setVisible(true);

            rs.close(); st.close();
        } 

       catch (Exception e)
        { 
            e.printStackTrace();
         }
    }

    public void showReports() {
        JOptionPane.showMessageDialog(null, "Reports functionality coming soon...");
    }
    public void showTotalRevenue() {
    try {
        Connection con = DBConnection.getConnection();
        String sql = "SELECT SUM(Amount) AS Total FROM Revenue";

        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);

        rs.next();
        double total = rs.getDouble("Total");

        JOptionPane.showMessageDialog(null, "Total Revenue: " + total);

        rs.close();
        st.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
}

public void showDailyRevenue() {
    try {
        Connection con = DBConnection.getConnection();
        String sql = "SELECT Revenue_Date, SUM(Amount) AS Total FROM Revenue GROUP BY Revenue_Date";

        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);

        String result = "";

        while (rs.next()) {
            result += rs.getString("Revenue_Date") + " = " + rs.getDouble("Total") + "\n";
        }

        JOptionPane.showMessageDialog(null, result);

        rs.close();
        st.close();
    } 
    catch (Exception e) 
    {
        e.printStackTrace();
    }
}

public void showRouteRevenue() {
    try {
        Connection con = DBConnection.getConnection();
        String sql = "SELECT Route_ID, SUM(Amount) AS Total FROM Revenue GROUP BY Route_ID";

        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);

        String result = "";

        while (rs.next()) {
            result += rs.getString("Route_ID") + " = " + rs.getDouble("Total") + "\n";
        }

        JOptionPane.showMessageDialog(null, result);

        rs.close();
        st.close();
    } 
    catch (Exception e) 
    {
        e.printStackTrace();
    }
}
}