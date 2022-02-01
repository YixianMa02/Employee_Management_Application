import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JPasswordField;
import com.toedter.calendar.JDateChooser;

import net.proteanit.sql.DbUtils;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.SystemColor;

public class EmployeeInfo extends JFrame {

	private JPanel contentPane;
	private JTextField textField_EID;
	private JTextField textField_Username;
	private JTextField textField_FirstName;
	private JTextField textField_LastName;
	private JPasswordField passwordField;
	private JTable table;
	private JTextField textField_Search;
	private Connection connection;
	private JDateChooser dateChooser;
	private JScrollPane scrollPane;
	private JComboBox comboBoxSearch;
	private JLabel lblEID;
	private JLabel lblFirstname;
	private JLabel lblLastname;
	private JLabel lblUsername;
	private JLabel lblPassword;
	private JLabel lblDateofbirth;
	private JLabel lblSearch;
	private JButton btnADD;
	private JButton btnUpdate;
	private JButton btnDelete;
	private JButton btnLoadDb;
	private DateFormat dateFormat;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EmployeeInfo frame = new EmployeeInfo();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void reloadTable() {
		
		try {	
			String query = "select EID, FirstName, LastName, DOB from EmployeeInfo";
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs =  pst.executeQuery();
			
			table.setModel(DbUtils.resultSetToTableModel(rs));
			
			textField_EID.setText(null);
			textField_FirstName.setText(null);
			textField_LastName.setText(null);
			textField_Username.setText(null);
			passwordField.setText(null);
			dateChooser.setDate(null);
			
			rs.close();
			pst.close();
		} catch (Exception e2) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, e2);
		}
	}
	
	
	/**
	 * Create the frame.
	 */
	public EmployeeInfo() {
		connection = sqlConnection.dbConnector();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 596, 288);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField_EID = new JTextField();
		textField_EID.setSelectedTextColor(SystemColor.desktop);
		textField_EID.setEditable(false);
		textField_EID.setEnabled(false);
		textField_EID.setDisabledTextColor(SystemColor.desktop);
		textField_EID.setSelectionColor(SystemColor.desktop);
		textField_EID.setBackground(SystemColor.menu);
		textField_EID.setForeground(Color.BLACK);
		textField_EID.setFont(new Font("Tahoma", Font.BOLD, 12));
		textField_EID.setBounds(10, 30, 99, 20);
		contentPane.add(textField_EID);
		textField_EID.setColumns(10);
		
		textField_Username = new JTextField();
		textField_Username.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textField_Username.setBounds(10, 74, 99, 20);
		contentPane.add(textField_Username);
		textField_Username.setColumns(10);
		
		textField_FirstName = new JTextField();
		textField_FirstName.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textField_FirstName.setBounds(136, 30, 99, 20);
		contentPane.add(textField_FirstName);
		textField_FirstName.setColumns(10);
		
		textField_LastName = new JTextField();
		textField_LastName.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textField_LastName.setBounds(136, 74, 99, 20);
		contentPane.add(textField_LastName);
		textField_LastName.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		passwordField.setBounds(10, 117, 99, 20);
		contentPane.add(passwordField);
		
		dateChooser = new JDateChooser();
		dateChooser.setBounds(136, 117, 99, 20);
		contentPane.add(dateChooser);
		dateChooser.setDateFormatString("dd-MMM-yyyy");
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(260, 30, 310, 107);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				try {
					int row = table.getSelectedRow();
					String empId = table.getModel().getValueAt(row, 0).toString();
					String query = "select * from EmployeeInfo where EID='" + empId + "'";
					PreparedStatement pst = connection.prepareStatement(query);
					ResultSet rs =  pst.executeQuery();
					
					while(rs.next()) {	
						textField_EID.setText(rs.getString("EID"));
						textField_FirstName.setText(rs.getString("FirstName"));
						textField_LastName.setText(rs.getString("LastName"));
						textField_Username.setText(rs.getString("Username"));
						passwordField.setText(rs.getString("Password"));
						String dd = rs.getString("DOB");
						java.util.Date date2 = new SimpleDateFormat("dd-MMM-yyyy").parse(dd);
						dateChooser.setDate(date2);
					}
					
					pst.close();
					rs.close();	
				}catch(Exception e2) {
					JOptionPane.showMessageDialog(null, e2);
					
				}
			}
		});
		scrollPane.setViewportView(table);
		
		lblEID = new JLabel("EID: ");
		lblEID.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblEID.setBounds(10, 11, 46, 14);
		contentPane.add(lblEID);
		
		lblUsername = new JLabel("Username: ");
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblUsername.setBounds(10, 59, 86, 14);
		contentPane.add(lblUsername);
		
		lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPassword.setBounds(10, 102, 86, 14);
		contentPane.add(lblPassword);
		
		lblFirstname = new JLabel("First Name: ");
		lblFirstname.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblFirstname.setBounds(136, 12, 86, 14);
		contentPane.add(lblFirstname);
		
		lblLastname = new JLabel("Last Name: ");
		lblLastname.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblLastname.setBounds(136, 61, 86, 14);
		contentPane.add(lblLastname);
		
		lblDateofbirth = new JLabel("Date of Birth:");
		lblDateofbirth.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDateofbirth.setBounds(136, 103, 86, 14);
		contentPane.add(lblDateofbirth);
		
		comboBoxSearch = new JComboBox();
		comboBoxSearch.setModel(new DefaultComboBoxModel(new String[] {"EID", "FirstName", "LastName", "DOB"}));
		comboBoxSearch.setFont(new Font("Tahoma", Font.PLAIN, 12));
		comboBoxSearch.setBounds(359, 183, 102, 22);
		contentPane.add(comboBoxSearch);
		
		lblSearch = new JLabel("Search By");
		lblSearch.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblSearch.setBounds(289, 187, 60, 14);
		contentPane.add(lblSearch);
		
		btnADD = new JButton("ADD");
		btnADD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
				if (textField_Username.getText().isEmpty() || textField_FirstName.getText().isEmpty() || textField_LastName.getText().isEmpty() || passwordField.getText().isEmpty() || dateFormat.format(dateChooser.getDate()).isEmpty()) {
					JOptionPane.showMessageDialog(null, "All data field must contains approriate date in order to add a new row into the database ... ");
				} else {
					try {	
						String query = "insert into EmployeeInfo (FirstName, LastName, Username, Password, DOB) values (?,?,?,?,?)";
						PreparedStatement pst = connection.prepareStatement(query);
						pst.setString(1, textField_FirstName.getText());
						pst.setString(2, textField_LastName.getText());
						pst.setString(3, textField_Username.getText());
						pst.setString(4, passwordField.getText());
						
						pst.setString(5, dateFormat.format(dateChooser.getDate()));
						
						pst.execute();
						JOptionPane.showMessageDialog(null, "Data Saved!");
						pst.close();
					} catch (Exception e2) {
						// TODO: handle exception
						JOptionPane.showMessageDialog(null, e2);
					}
					
					reloadTable();
					}
			}
		});
		btnADD.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnADD.setBounds(7, 148, 89, 23);
		btnADD.setFocusable(false);
		contentPane.add(btnADD);
		
		btnUpdate = new JButton("UPDATE");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (textField_EID.getText().isEmpty()) {		
					JOptionPane.showMessageDialog(null, "Please select an employee from the table before doing the update ... ");
				} else {
					try {
						dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
						String query = "update EmployeeInfo set FirstName='" + textField_FirstName.getText() + 
								"', LastName='" + textField_LastName.getText() + "', Username='" + textField_Username.getText() + 
								"', Password='" + passwordField.getText() + "', DOB='" + dateFormat.format(dateChooser.getDate()) + "' where EID='" + textField_EID.getText() + "'";
						PreparedStatement pst = connection.prepareStatement(query);
						pst.execute();
						JOptionPane.showMessageDialog(null, "Data Updated!");
						pst.close();
					} catch (Exception e2) {
						// TODO: handle exception
							JOptionPane.showMessageDialog(null, e2);
					}
					reloadTable();
				}
			}
		});
		btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnUpdate.setBounds(7, 182, 89, 23);
		btnUpdate.setFocusable(false);
		contentPane.add(btnUpdate);
		
		btnDelete = new JButton("DELETE");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int action = JOptionPane.showConfirmDialog(null, "Are You Sure?", "Delete", JOptionPane.YES_NO_OPTION);
				if(action == 0) {
					if (textField_EID.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Please select an employee from the table before doing the delete ... ");
					} else {
						try {
								String query = "delete from EmployeeInfo where EID='" + textField_EID.getText() + "'";
								PreparedStatement pst = connection.prepareStatement(query);
								pst.execute();
								JOptionPane.showMessageDialog(null, "Data Removed!");
								pst.close();			
						} catch (Exception e2) {
							// TODO: handle exception
								JOptionPane.showMessageDialog(null, e2);
						}
						reloadTable();
					}
				}
			}
		});
		btnDelete.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnDelete.setBounds(7, 215, 89, 23);
		btnDelete.setFocusable(false);
		contentPane.add(btnDelete);
		
		btnLoadDb = new JButton("Load DB");
		btnLoadDb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		
				try {
					
					String query = "select EID, FirstName, LastName, DOB from EmployeeInfo";
					PreparedStatement pst = connection.prepareStatement(query);
					ResultSet rs =  pst.executeQuery();
					
					table.setModel(DbUtils.resultSetToTableModel(rs));
					
					rs.close();
					pst.close();	
				} catch (Exception e2) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(null, e2);
				}
			}
		});
		btnLoadDb.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnLoadDb.setBounds(481, 148, 89, 23);
		btnLoadDb.setFocusable(false);
		contentPane.add(btnLoadDb);
		
		textField_Search = new JTextField();
		textField_Search.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				try {
					
					String selected = comboBoxSearch.getSelectedItem().toString();
					
					String query = "select EID, FirstName, LastName, DOB from EmployeeInfo where " + selected + "=?";
					PreparedStatement pst = connection.prepareStatement(query);
					pst.setString(1, textField_Search.getText());
					ResultSet rs =  pst.executeQuery();
					
					table.setModel(DbUtils.resultSetToTableModel(rs));
					
					rs.close();
					pst.close();		
				} catch (Exception e2) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(null, e2);
				}
			}
		});
		textField_Search.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textField_Search.setColumns(10);
		textField_Search.setBounds(471, 184, 99, 20);
		contentPane.add(textField_Search);
		

	}
}
