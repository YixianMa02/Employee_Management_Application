import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class Login {

	private JFrame frame;
	private JTextField textField_Username;
	private JPasswordField passwordField;
	private Connection connection;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Login() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// DB connection
		connection = sqlConnection.dbConnector();
		
		frame = new JFrame();
		frame.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 12));
		frame.setBounds(100, 100, 165, 233);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textField_Username = new JTextField();
		textField_Username.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textField_Username.setBounds(10, 34, 125, 20);
		frame.getContentPane().add(textField_Username);
		textField_Username.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		passwordField.setBounds(10, 88, 125, 20);
		frame.getContentPane().add(passwordField);
		
		JLabel lblUsername = new JLabel("Username: ");
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblUsername.setBounds(10, 11, 67, 14);
		frame.getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password: ");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPassword.setBounds(10, 63, 67, 14);
		frame.getContentPane().add(lblPassword);
		
		JButton btnLogin = new JButton("LOGIN");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String query = "select * from EmployeeInfo where Username=? and Password=?";
				try {
					PreparedStatement pst = connection.prepareStatement(query);
					pst.setString(1, textField_Username.getText());
					pst.setString(2, passwordField.getText());
					ResultSet rs = pst.executeQuery();
					
					int counter = 0;
					while (rs.next()) {
						counter++;
					}
					
					if (counter == 0) {
						
						JOptionPane.showMessageDialog(null, "Wrong Username and Password");
						textField_Username.setText(null);
						passwordField.setText(null);
						
					} else if(counter == 1){
						
						JOptionPane.showMessageDialog(null, "Username and Password are Correct!");
						frame.dispose();
						EmployeeInfo emp = new EmployeeInfo();
						emp.setVisible(true);

					}else if(counter > 1) {
						
						JOptionPane.showMessageDialog(null, "Deplicated Username and Password...");
						textField_Username.setText(null);
						passwordField.setText(null);
						
					}
					
					pst.close();
					rs.close();
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e1);
				}
				
			}
		});
		btnLogin.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnLogin.setBounds(10, 131, 81, 23);
		frame.getContentPane().add(btnLogin);
	}
}
