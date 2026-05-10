import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class OperatorLogin extends JFrame 
{
    private JTextField idField;
    private JPasswordField passField;
    private JButton loginBtn;

   public OperatorLogin() 
{
    setTitle("Operator Login");
    setSize(420, 300);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setLayout(new BorderLayout());

    // ===== HEADER =====
    JPanel header = new JPanel();
    header.setBackground(new Color(25, 42, 86));
    header.setPreferredSize(new Dimension(420, 60));

    JLabel title = new JLabel("Operator Login");
    title.setForeground(Color.WHITE);
    title.setFont(new Font("Segoe UI", Font.BOLD, 18));
    header.add(title);

    add(header, BorderLayout.NORTH);

    // ===== CENTER =====
    JPanel center = new JPanel();
    center.setLayout(new GridLayout(3, 2, 10, 10));
    center.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
    center.setBackground(new Color(245, 246, 250));

    JLabel idLabel = new JLabel("Operator ID:");
    idField = new JTextField();

    JLabel passLabel = new JLabel("Password:");
    passField = new JPasswordField();

    loginBtn = new JButton("LOGIN");
    styleButton(loginBtn);

    center.add(idLabel);
    center.add(idField);
    center.add(passLabel);
    center.add(passField);
    center.add(new JLabel(""));
    center.add(loginBtn);

    add(center, BorderLayout.CENTER);

    loginBtn.addActionListener(new LoginHandler());

    setLocationRelativeTo(null);
    setVisible(true);
}
private void styleButton(JButton btn)
{
    btn.setBackground(new Color(0, 168, 150));
    btn.setForeground(Color.WHITE);
    btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
    btn.setFocusPainted(false);
    btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
}

   
   private class LoginHandler implements ActionListener
    {
       public void actionPerformed(ActionEvent e) 
       {
        try 
        {
            Connection con = DBConnection.getConnection();

            String enteredId = idField.getText().trim();
            String enteredPass = new String(passField.getPassword()).trim();

            String sql = "SELECT Operator_Password FROM Operator WHERE Operator_ID=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, enteredId);

            ResultSet rs = ps.executeQuery();

            if (rs.next())
             {
                String dbPass = rs.getString("Operator_Password");

                if (enteredPass.equals(dbPass)) 
                {
                    JOptionPane.showMessageDialog(OperatorLogin.this, "Login successful!");
                    new OperatorView(enteredId);
                    dispose();
                } 
                else
                {
                    JOptionPane.showMessageDialog(OperatorLogin.this, "Incorrect password");
                }
            } 
            else
             {
                JOptionPane.showMessageDialog(OperatorLogin.this, "Operator ID not found");
            }

            rs.close();
            ps.close();
            con.close();

        } 

        catch (Exception ex) 
        {
            ex.printStackTrace();
        }
    }
}}