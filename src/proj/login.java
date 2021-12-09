package proj;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class login extends JFrame {
    private JPanel panel1;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton signinButton;
    public login() {
        setContentPane(panel1);
        setVisible(true);
        setDefaultCloseOperation(signup.EXIT_ON_CLOSE);
        pack();
        signinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userName=textField1.getText();
                String password=passwordField1.getText();
                boolean check=false;
                try {
                     check=checkLogin(userName,password);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                if(userName.equals(""))
                    check=false;
                if(check)
                {
                    JOptionPane.showMessageDialog(null,"USER LOGIN SUCCESSFUL");
                    setVisible(false);
                    new Dashboard().setSize(1500,800);

                }
                else
                    JOptionPane.showMessageDialog(null,"INVALID CREDENTIAL");
            }
        });
    }
    public  boolean checkLogin(String userId,String password) throws SQLException {
        Connection connection= proj.connection.getConnection();
        Statement statement=connection.createStatement();
        String sql = "select * from userlogin where username='" + userId + "'";
        ResultSet resultSet = statement.executeQuery(sql);
        if (resultSet.next()) {
            if((resultSet.getString("username").equals(userId))&&(resultSet.getString("password").equals(password)))
                return true;
            else
                return false;
        }
        else
            return false;
    }
}