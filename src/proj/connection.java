package proj;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JFrame;

public class connection extends JFrame {
    public static Connection getConnection() throws SQLException {
        Connection connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc", "root", "jaysairaj1");
        return  connection;
    }
}
