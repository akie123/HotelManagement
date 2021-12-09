package proj;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class UpdateRooms extends JFrame implements CommonMethods{
    private JPanel panel1;
    private JTable table1;
    private JTextField textField1;
    private JButton deleteButton;
    private JTextField textField2;
    private JTextField textField5;
    private JComboBox comboBox1;
    private JSpinner spinner1;
    private JComboBox comboBox2;
    private JButton insertButton;
    private JButton editPriceButton;
    private JButton editAvailablityButton;
    private JTextField textField6;
    private JTextField textField7;
    private JTextField textField3;
    private JSpinner spinner2;
    public UpdateRooms() throws SQLException {
        super.setContentPane(panel1);
        super.setDefaultCloseOperation(UpdateRooms.DISPOSE_ON_CLOSE);
        super.pack();
        super.setVisible(true);
        createTable();
        takeFromDatabase();
        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 int id=Integer.parseInt(textField2.getText());
                 String hotel=(String) comboBox2.getSelectedItem();
                 String type=(String)comboBox1.getSelectedItem();
                 int price=Integer.parseInt(textField5.getText());
                 int available=(Integer)spinner1.getValue();
                try {
                    addToDatabase(id,type,hotel,price,available);
                    JOptionPane.showMessageDialog(null,"Rooms added successfully");

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id =Integer.parseInt(textField1.getText());
                try {
                    deleteFromDatabase(id);
                    JOptionPane.showMessageDialog(null,"Rooms Removed!");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        editPriceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id =Integer.parseInt(textField7.getText());
                int price=Integer.parseInt(textField3.getText());
                try {
                    updatePrice(id, price);
                    JOptionPane.showMessageDialog(null,"price updated");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        editAvailablityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id=Integer.parseInt(textField6.getText());
                int available=(Integer)spinner2.getValue();
                try {
                    updateAvailablity(id,available);
                    JOptionPane.showMessageDialog(null,"updated availability");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
    }
    public void createTable()
    {
        table1.setModel(new DefaultTableModel(
                null,new String[]{"ROOM ID","HOTEL","ROOM TYPE","PRICE","AVAILABILITY"}
        ));
    }
   public void takeFromDatabase() throws SQLException {
        ArrayList<Room> rooms=new ArrayList<>();
        DefaultTableModel model=(DefaultTableModel)table1.getModel();
        Connection connection= proj.connection.getConnection();
        Statement statement=connection.createStatement();
        String sql = "select * from rooms";
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
           rooms.add(new Room(resultSet.getInt("idrooms"),resultSet.getString("hotel"),resultSet.getString("type"),resultSet.getInt("price"),resultSet.getInt("available")));
        }
        for(int i=0;i<rooms.size();i++){
            model.addRow(new String[]{Integer.toString(rooms.get(i).getId()),rooms.get(i).getHotel(),rooms.get(i).getType(),Integer.toString(rooms.get(i).getPrice()),Integer.toString(rooms.get(i).getAvailable())});
        }
    }
    public   void  addToDatabase(int idrooms,String type,String hotel,int price,int availability) throws SQLException {
        String sql = "insert into rooms values('"+idrooms+"','"+hotel+"','"+type+"','"+price+"','"+availability+"')";
        //System.out.println(sql);
        Connection connection= proj.connection.getConnection();
        Statement statement=connection.createStatement();
        statement.executeUpdate(sql);
        DefaultTableModel model=(DefaultTableModel)table1.getModel();
        model.addRow(new String[]{Integer.toString(idrooms),hotel,type,Integer.toString(price),Integer.toString(availability)});
    }
    public void deleteFromDatabase(int idrooms) throws SQLException {
        Connection connection= proj.connection.getConnection();
        Statement statement=connection.createStatement();
        String sql = "delete from rooms where idrooms=" + idrooms ;
        statement.executeUpdate(sql);
        DefaultTableModel model=(DefaultTableModel)table1.getModel();
        for(int i=0;i<model.getRowCount();i++) {
            String ak= (String) model.getValueAt(i,0);
            if(Integer.parseInt(ak)==idrooms){
                model.removeRow(i);
                break;
            }
        }
    }
    private  void updatePrice(int idrooms,int price) throws SQLException
    {
        Connection connection= proj.connection.getConnection();
        Statement statement=connection.createStatement();
        String sql = "update rooms set price= "+price+" where idrooms= " + idrooms ;
        statement.executeUpdate(sql);
        DefaultTableModel model=(DefaultTableModel)table1.getModel();
        for(int i=0;i<model.getRowCount();i++) {
            String ak= (String) model.getValueAt(i,0);
            if(Integer.parseInt(ak)==idrooms){
               model.setValueAt(Integer.toString(price),i,3);
                break;
            }
        }
    }
    private void updateAvailablity(int idrooms,int available)throws SQLException
    {
        Connection connection= proj.connection.getConnection();
        Statement statement=connection.createStatement();
        String sql = "update rooms set available= "+available+" where idrooms= " + idrooms ;
        statement.executeUpdate(sql);
        DefaultTableModel model=(DefaultTableModel)table1.getModel();
        for(int i=0;i<model.getRowCount();i++) {
            String ak= (String) model.getValueAt(i,0);
            if(Integer.parseInt(ak)==idrooms){
                model.setValueAt(Integer.toString(available),i,4);
                break;
            }
        }
    }
}