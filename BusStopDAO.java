import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class BusStopDAO
 {
    private Connection con;

    public BusStopDAO(Connection con) 
    {
        this.con = con;
    }

    public void showStopsByBus(String busID)
     {
       try 
       {
        String sql = "SELECT s.Stop_ID, s.Stop_Name " +"FROM Bus_Stop bs " +"JOIN Stops s ON bs.Stop_ID = s.Stop_ID " + "WHERE bs.Bus_ID = ?";
         PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, busID);
        ResultSet rs = ps.executeQuery();

        String[] columnNames = {"Stop ID", "Stop Name"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        boolean found = false;

        while (rs.next()) 
        {
            found = true;
            model.addRow(new Object[]{ rs.getString("Stop_ID"), rs.getString("Stop_Name")});
        }

        if (!found) 
        {
            JOptionPane.showMessageDialog(null,"No stops found for Bus ID: " + busID);
            return;
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        JFrame frame = new JFrame("Stops for Bus " + busID);
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
        JOptionPane.showMessageDialog(null, "Error loading stops!");
    }
}

   public void showBusesByStops(String stopId)
    {
    try {
        String sql = "SELECT b.Bus_ID, b.Bus_Name " +  "FROM Bus_Stop bs " +  "JOIN Buses b ON bs.Bus_ID = b.Bus_ID " + "WHERE bs.Stop_ID = ?";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, stopId);
        ResultSet rs = ps.executeQuery();

        String[] cols = {"Bus ID", "Bus Name"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);

        boolean found = false;

        while (rs.next())
         {
            found = true;
            model.addRow(new Object[]{ rs.getString("Bus_ID"), rs.getString("Bus_Name") });
        }

        if (!found) {
            JOptionPane.showMessageDialog(null,
                    "No buses found for Stop ID: " + stopId);
            return;
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        JFrame frame = new JFrame("Buses for Stop " + stopId);
        frame.setSize(500, 300);
        frame.add(scrollPane);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        rs.close();
        ps.close();

    } 
    catch (Exception e) 
    {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error loading buses!");
    }
}
public void showAllStops() {
    try {
        String sql = "SELECT Stop_ID, Stop_Name FROM Stops";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);

        String[] cols = {"Stop ID", "Stop Name"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);

        while (rs.next()) {
            model.addRow(new Object[]{ rs.getString("Stop_ID"),rs.getString("Stop_Name")  });
        }

        JTable table = new JTable(model);
        JScrollPane pane = new JScrollPane(table);

        JFrame frame = new JFrame("All Stops");
        frame.setSize(500, 300);
        frame.add(pane);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        rs.close();
        st.close();

    } 

    catch (Exception e) 
    {
        e.printStackTrace();
    }
}
}