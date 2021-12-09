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
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class checkOut extends JFrame implements CommonMethods{
    private JPanel panel1;
    private JTable table1;
    private JTextField textField1;
    private JButton GetBillButton;
    private JTextField textField2;
    private JButton CHECKOUTButton;
    int balance;
    public checkOut() throws SQLException {
        setContentPane(panel1);
        setDefaultCloseOperation(checkOut.DISPOSE_ON_CLOSE);
        pack();
        setSize(1400,800);
        setVisible(true);
        createTable();
        takeFromDatabase();
        GetBillButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 String idd=textField1.getText();

              int  bill=-1;
                try {
                    bill=getBill(idd);
                    if(bill==-1)
                        JOptionPane.showMessageDialog(null,"Invalid id");
                    else{
                        balance=bill;
                        textField2.setText(Integer.toString(bill));
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        CHECKOUTButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idd=textField1.getText();
                String hotel="",roomType="",name="",inDate="",age="";
                Connection connection= null;
                try {
                    connection = proj.connection.getConnection();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                Statement statement= null;
                try {
                    statement = connection.createStatement();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                String sql = "select * from checkin where id= '"+idd+"'";
                System.out.println(sql);
                try {
                    ResultSet resultSet = statement.executeQuery(sql);
                    while(resultSet.next())
                    {
                        hotel=resultSet.getString("hotel");
                        roomType=resultSet.getString("roomtype");
                        name=resultSet.getString("name");
                        inDate=resultSet.getString("date");
                        age=resultSet.getString("age");
                    }
                    String sql1="update rooms set available=available+1 where type='" + roomType + "' and hotel='"+hotel+"'";
                    statement.executeUpdate(sql1);
                    deleteFromDatabase(Integer.parseInt(idd));
                    String sql3="insert into history values('"+name+"' , "+age+" ,'"+hotel+"','"+roomType+"','"+inDate+"', curdate() ,"+balance+")";
                    statement.executeUpdate(sql3);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                JOptionPane.showMessageDialog(null,"Check-Out Successful Bill received!");
                dispose();
            }
        });
    }

    public void createTable()
    {
        table1.setModel(new DefaultTableModel(
                null,new String[]{"Customer Id","Name","Age","Hotel","Room Type","CheckIn-Date"}
        ));
    }
    public int getBill(String id) throws SQLException {
        Connection connection= proj.connection.getConnection();
        Statement statement=connection.createStatement();
        String sql = "select datediff(curdate(),date) as days ,hotel,roomtype from checkin where id= '"+id+"'";
        ResultSet resultSet = statement.executeQuery(sql);
        int days=-1,price=-1;
        String hotel="",roomType="";
        while(resultSet.next())
        {
            days=resultSet.getInt("days");
            hotel=resultSet.getString("hotel");
            roomType=resultSet.getString("roomtype");
        }
        if(days==-1)
            return -1;
        if(days==0)
            days++;
        String sql1="select price from rooms where hotel='"+hotel+"' and type= '"+roomType+"'";
        ResultSet resultSet1 = statement.executeQuery(sql1);
        while (resultSet1.next())
        {
            price=resultSet1.getInt("price");
        }
        if(price==-1)
            return -1;

        return price*days;
    }
    public void takeFromDatabase() throws SQLException {
        DefaultTableModel model=(DefaultTableModel)table1.getModel();
        Connection connection= proj.connection.getConnection();
        Statement statement=connection.createStatement();
        String sql = "select * from checkin";
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            model.addRow(new String[]{resultSet.getString("id"),resultSet.getString("name"),resultSet.getString("age"),resultSet.getString("hotel"),resultSet.getString("roomtype"),resultSet.getString("date")});
        }
    }
    public void deleteFromDatabase(int idd) throws SQLException {
        Connection connection= proj.connection.getConnection();
        Statement statement=connection.createStatement();
        String sql2 = "delete from checkin where id=" + idd;
        statement.executeUpdate(sql2);
    }
}
