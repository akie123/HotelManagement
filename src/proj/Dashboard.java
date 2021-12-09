package proj;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Dashboard extends JFrame {
    private JButton ADDREMOVEROOMSButton;
    private JPanel panel1;
    private JButton CHECKINButton;
    private JButton CHECKOUTButton;
    private JButton CHECKOUTHISTORYButton;
    public Dashboard() {
        setContentPane(panel1);
        setDefaultCloseOperation(Dashboard.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
        ADDREMOVEROOMSButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new UpdateRooms().setSize(1500,800);;
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        CHECKINButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new CheckIn().setSize(1500,800);;
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        CHECKOUTButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new checkOut().setSize(1500,800);;
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        CHECKOUTHISTORYButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new customerHistory().setSize(1500,800);;

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
    }
}
