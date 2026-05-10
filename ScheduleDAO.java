import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class ScheduleDAO {
    private Connection con;

    public ScheduleDAO(Connection con) 
    {
        this.con = con;
    }


    public void showSchedules() {
        try {
            String sql = "SELECT * FROM Schedule";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

           
            String[] columnNames = {"Bus ID", "Scheduled Time", "Estimated Time"};
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);

           
            while (rs.next()) 
            {
                String busID = rs.getString("Bus_ID");
                String scheduled = rs.getString("Bus_Scheduled_Time");
                String estimated = rs.getString("Bus_Estimated_Time");
                model.addRow(new Object[]{busID, scheduled, estimated});
            }

            JTable table = new JTable(model);
            table.setRowHeight(30); 
            table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

            JScrollPane scrollPane = new JScrollPane(table);

            JFrame frame = new JFrame("Bus Schedules");
            frame.setSize(600, 400);
            frame.add(scrollPane);
            frame.setVisible(true);

            rs.close();
            st.close();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }

  
    public void showSchedulesByBus(String busID) 
    {
        try {
            String sql = "SELECT * FROM Schedule WHERE Bus_ID = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, busID);
            ResultSet rs = ps.executeQuery();

            String[] columnNames = {"Bus ID", "Scheduled Time", "Estimated Time"};
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);

            while (rs.next())
            {
                String scheduled = rs.getString("Bus_Scheduled_Time");
                String estimated = rs.getString("Bus_Estimated_Time");
                model.addRow(new Object[]{busID, scheduled, estimated});
            }

            JTable table = new JTable(model);
            table.setRowHeight(30);

            JScrollPane scrollPane = new JScrollPane(table);

            JFrame frame = new JFrame("Schedules for Bus " + busID);
            frame.setSize(600, 400);
            frame.add(scrollPane);
            frame.setVisible(true);

            rs.close();
            ps.close();
        } 
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}