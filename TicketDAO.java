import java.sql.*;

class TicketDAO {

    Connection con;

    public TicketDAO(Connection con) 
    {
        this.con = con;
    }

   
    public String insertPassenger(String cnic, String name, String phone) 
    {
        String passengerID = null;
        try 
        {
            String checkSql = "SELECT Passenger_ID FROM Passengers WHERE Passenger_CNIC = ?";
            PreparedStatement checkPs = con.prepareStatement(checkSql);
            checkPs.setString(1, cnic);
            ResultSet rs = checkPs.executeQuery();

            if (rs.next()) 
            {
                passengerID = rs.getString("Passenger_ID");
            }

             else 
             {
                String sql = "SELECT Passenger_ID FROM Passengers ORDER BY Passenger_ID DESC LIMIT 1";
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs2 = ps.executeQuery();
                if (rs2.next()) 
                {
                    String lastId = rs2.getString(1);
                    int num = Integer.parseInt(lastId.split("-")[1]);
                    passengerID = String.format("PAS-%03d", num + 1);
                }
                 else 
                 {
                    passengerID = "PAS-001";
                 }
                rs2.close();
                ps.close();

                String insertSql = "INSERT INTO Passengers (Passenger_ID, Passenger_Name, Passenger_CNIC, Passenger_Phone) VALUES (?, ?, ?, ?)";
                PreparedStatement ps2 = con.prepareStatement(insertSql);
                ps2.setString(1, passengerID);
                ps2.setString(2, name);
                ps2.setString(3, cnic);
                ps2.setString(4, phone);
                ps2.executeUpdate();
                ps2.close();
            }
            rs.close();
            checkPs.close();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }

        if (passengerID == null) {
            throw new RuntimeException("Passenger ID generation failed!");
        }
        return passengerID;
    }

    
    public String generateTicketId() 
    {
        String ticketID = "TKT-001";
        try 
        {
            String sql = "SELECT Ticket_No FROM Ticket ORDER BY Ticket_No DESC LIMIT 1";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) 
            {
                String lastId = rs.getString(1);
                int num = Integer.parseInt(lastId.split("-")[1]);
                ticketID = String.format("TKT-%03d", num + 1);
            }
            rs.close();
            ps.close();
        } 

        catch (Exception e)
        {
            e.printStackTrace();
        }
        return ticketID;
    }

    
    public String insertTicket(Ticket t, Bus bus)
     {
        String ticketNo = generateTicketId();
        try
         {
            String query = "INSERT INTO Ticket (Ticket_No, Passenger_ID, Bus_ID, Boarding_Point, Destination_Point, Seat_Number, Journey_Date, Price, Issue_DateTime) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, ticketNo);
            ps.setString(2, t.getPassengerID());
            ps.setString(3, t.getBusID());
            ps.setString(4, t.getBoardingPoint());
            ps.setString(5, t.getDestinationPoint());
            ps.setString(6, t.getSeatNumber());
            ps.setString(7, t.getJourneyDate());
            ps.setDouble(8, t.getPrice());
            ps.setString(9, t.getIssueDateTime());

            ps.executeUpdate();
            ps.close();

            System.out.println("Ticket Inserted! Ticket_No = " + ticketNo);
            return ticketNo;
        } 
        catch (Exception e) 
        {
            System.out.println("ERROR INSERT TICKET:");
            e.printStackTrace();
            return null;
        }
    }

    
    public void cancelTicket(String ticketNo, Bus bus) 
    {
        try 
        {
            String query = "DELETE FROM Ticket WHERE Ticket_No = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, ticketNo);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                bus.incrementSeats();
                System.out.println("Ticket Deleted! Ticket_No = " + ticketNo);
            } else {
                System.out.println("Ticket Not Found! Ticket_No = " + ticketNo);
            }

            ps.close();
        }
         catch (Exception e)
        {
            e.printStackTrace();
        }
    }

   
    public String getBusIdByName(String busName) {
        String busId = null;
        try {
            String sql = "SELECT Bus_ID FROM Buses WHERE Bus_Name = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, busName);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                busId = rs.getString("Bus_ID");
            }

            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return busId;
    }

  
    public String[] getBusNames() 
    {
        try 
        {
            String sql = "SELECT Bus_Name FROM Buses";
            PreparedStatement ps = con.prepareStatement(sql,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = ps.executeQuery();

            rs.last();
            int size = rs.getRow();
            rs.beforeFirst();

            String[] busNames = new String[size];
            int i = 0;
            while (rs.next()) {
                busNames[i++] = rs.getString("Bus_Name");
            }
            rs.close();
            ps.close();
            return busNames;
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            return new String[0];
        }
    }

   
    public String[] getBoardingPoints()
     {
        try 
        {
            String sql = "SELECT DISTINCT Bus_Departure_Point FROM Buses";
            PreparedStatement ps = con.prepareStatement(sql,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = ps.executeQuery();

            rs.last();
            int size = rs.getRow();
            rs.beforeFirst();

            String[] boardingPoints = new String[size];
            int i = 0;
            while (rs.next()) {
                boardingPoints[i++] = rs.getString("Bus_Departure_Point");
            }
            rs.close();
            ps.close();
            return boardingPoints;
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            return new String[0];
        }
    }

   
    public String[] getDestinationPoints() 
    {
        try {
            String sql = "SELECT DISTINCT Bus_Destination_Point FROM Buses";
            PreparedStatement ps = con.prepareStatement(sql,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = ps.executeQuery();

            rs.last();
            int size = rs.getRow();
            rs.beforeFirst();

            String[] destinationPoints = new String[size];
            int i = 0;
            while (rs.next()) {
                destinationPoints[i++] = rs.getString("Bus_Destination_Point");
            }
            rs.close();
            ps.close();
            return destinationPoints;
        } catch (Exception e) {
            e.printStackTrace();
            return new String[0];
        }
    }

    // Get Tickets by Passenger
    public void getTicketsByPassenger(String passengerId) {
        try (PreparedStatement ps = con.prepareStatement(
                "SELECT Ticket_No, Bus_ID, Price FROM Ticket WHERE Passenger_ID=?")) {
            ps.setString(1, passengerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println("Ticket_No: " + rs.getString("Ticket_No") +
                                   ", Bus: " + rs.getString("Bus_ID") +
                                   ", Price: " + rs.getDouble("Price"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}