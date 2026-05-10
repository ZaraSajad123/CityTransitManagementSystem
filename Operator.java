import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
class Operator extends User {
    private String operator_ID;
    private String operator_Name;
    private String operator_Email;
    private String operator_Password;

    public Operator(String id, String name, String email, String operator_ID, String operator_Name, String operator_Email, String operator_Password)
     {
        super(id, name, email);
        this.operator_ID = operator_ID;
        this.operator_Name = operator_Name;
        this.operator_Email = operator_Email;
        this.operator_Password = operator_Password;
    }

    
    public void viewRevenue() 
    {
        OperatorDAO dao = new OperatorDAO(DBConnection.getConnection());
        dao.showRevenueReport();
    }

    public void checkPassengerLoadByRoute() 
    {
        OperatorDAO dao = new OperatorDAO(DBConnection.getConnection());
        dao.showPassengerLoad();
    }

    public void manageBuses() 
    {
        OperatorDAO dao = new OperatorDAO(DBConnection.getConnection());
        dao.showAllBuses();
    }

    public void viewReports() 
    {
        OperatorDAO dao = new OperatorDAO(DBConnection.getConnection());
        dao.showReports();
    }

    @Override
    public void viewOptions() 
    {
        System.out.println("Operator " + operator_Name +
            " Options: View Revenue, Check Passenger Load, Manage Buses, View Reports");
    }

    
}

