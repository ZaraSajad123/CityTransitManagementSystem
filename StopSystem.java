import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;

public class StopSystem extends JFrame {

    public StopSystem() 
    {

        setTitle("Stops System");
        setSize(350, 200);
        setLayout(new GridLayout(2, 1, 10, 10));

        JButton byBus = new JButton("Find Stops by Bus");
        JButton byStop = new JButton("Find Buses by Stop");

        add(byBus);
        add(byStop);

        final BusStopDAO dao = new BusStopDAO(DBConnection.getConnection());

       
        byBus.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
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
            public void actionPerformed(ActionEvent e) 
            {
                String stopID = JOptionPane.showInputDialog("Enter Stop ID:");
                if (stopID != null && !stopID.isEmpty()) 
                {
                    dao.showBusesByStops(stopID);
                }
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }
}