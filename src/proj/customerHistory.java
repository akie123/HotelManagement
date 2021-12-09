package proj;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class customerHistory extends JFrame implements CommonMethods {
    private JTable table1;
    private JPanel panel1;
    private JTextField textField1;
    public customerHistory() throws SQLException {
        setContentPane(panel1);
        setDefaultCloseOperation(checkOut.DISPOSE_ON_CLOSE);
        pack();
        setSize(1400, 800);
        setVisible(true);
        createTable();
        takeFromDatabase();
    }
   public void createTable() {
        table1.setModel(new DefaultTableModel(
                null, new String[]{"Name", "Age", "Hotel", "Room Type", "CheckIn-Date", "CheckOut-Date", "Amount"}
        ));
    }
    public void takeFromDatabase() throws SQLException {
        DefaultTableModel model = (DefaultTableModel) table1.getModel();
        Connection connection = proj.connection.getConnection();
        Statement statement = connection.createStatement();
        String sql = "select * from history";
        ResultSet resultSet = statement.executeQuery(sql);
        int sum=0;
        while (resultSet.next()) {
            model.addRow(new String[]{resultSet.getString("name"), resultSet.getString("age"), resultSet.getString("hotel"), resultSet.getString("type"), resultSet.getString("checkin"), resultSet.getString("checkout"), resultSet.getString("amount")});
            sum=sum+ (Integer.parseInt(resultSet.getString("amount")));
        }
        textField1.setText("Total Revenue: Rs "+ Integer.toString(sum) +" only");
    }
    @Override
    public void deleteFromDatabase(int idrooms) throws SQLException {}
}
