package view;

import control.PlayerCtr;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import model.Player;

public class LoginFrm extends JFrame implements ActionListener {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnChange;
    private PlayerCtr myRemoteObject;

    public LoginFrm(PlayerCtr ro) {
        super("TCP Login MVC");
        myRemoteObject = ro;
        txtUsername = new JTextField(15);
        txtPassword = new JPasswordField(15);
        txtPassword.setEchoChar('*');
        btnLogin = new JButton("Login");
        btnChange = new JButton("Change");
        JPanel content = new JPanel();
        content.setLayout(new FlowLayout());
        content.add(new JLabel("Username:"));
        content.add(txtUsername);
        content.add(new JLabel("Password:"));
        content.add(txtPassword);
        content.add(btnLogin);
        btnLogin.addActionListener(this);
        content.add(btnChange);
        btnChange.addActionListener(this);

        this.setContentPane(content);
        this.pack();
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    }

    public void actionPerformed(ActionEvent e) {
        if ((e.getSource() instanceof JButton) && (((JButton) e.getSource()).equals(btnLogin))) {
            //pack the entity
            Player player = new Player();
            player.setUsername(txtUsername.getText());
            player.setPassword(txtPassword.getText());

            //call the remote function
            if (myRemoteObject.remoteLogin(player).getFullname() == null) {
                JOptionPane.showMessageDialog(this, "username or password is wrong!");
            } else if (myRemoteObject.remoteLogin(player) instanceof Player) {
                JOptionPane.showMessageDialog(this, "Login succesfully!");
                player = myRemoteObject.remoteLogin(player);
                myRemoteObject.updatePlayerstatus("1", player.getIdplayer());
                if (!myRemoteObject.checkRank(player.getIdplayer(), player.getFullname())) {
                    myRemoteObject.insertRank(player.getIdplayer(), player.getFullname());
                }
                new PlayerMainFrm(myRemoteObject, player).setVisible(true);
                new PlayerConnectToServer(myRemoteObject, player).setVisible(false);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error when login!");
            }
        }
    }

}
