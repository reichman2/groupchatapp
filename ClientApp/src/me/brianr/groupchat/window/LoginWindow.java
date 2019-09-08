package me.brianr.groupchat.window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import me.brianr.groupchat.GroupChat;
import me.brianr.groupchat.login.LoginVerifier;
import me.brianr.groupchat.users.Role;
import me.brianr.groupchat.users.User;


/**
 * Class to manage signing into the server.
 * @author Brian Reich
 * @version 1.0
 * @since 1.0
 */
public class LoginWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3525593981367358008L;
	
	public static final String REGISTER_URL = "http://" + GroupChat.getInstance().getConfig().getServerAddress() + "/register.php";
	
	private JPanel contentPane;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JTextField displayNameField;

//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					LoginWindow frame = new LoginWindow();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public LoginWindow() {
		// TODO Focus toggle policy.
		
		setSize(new Dimension(450, 192));
		setResizable(false);
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		usernameField = new JTextField();
		usernameField.setToolTipText("username");
		usernameField.setBounds(10, 30, 150, 20);
		panel.add(usernameField);
		usernameField.setColumns(10);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(10, 12, 67, 16);
		panel.add(lblUsername);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(186, 30, 150, 20);
		passwordField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					login();
				}
				
			}
		});
		panel.add(passwordField);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(186, 12, 67, 16);
		panel.add(lblPassword);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setBounds(324, 57, 98, 26);
		btnLogin.addActionListener(e -> {
			login();
		});
		panel.add(btnLogin);
		
		JLabel lblOr = new JLabel("Or, Connect without Logging In");
		lblOr.setBounds(10, 89, 188, 16);
		panel.add(lblOr);
		
		JLabel lblDisplayname = new JLabel("Displayname");
		lblDisplayname.setBounds(10, 117, 78, 16);
		panel.add(lblDisplayname);
		
		displayNameField = new JTextField();
		displayNameField.setBounds(10, 137, 148, 20);
		displayNameField.setColumns(10);
		displayNameField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					connect();
				}
			}
		});
		panel.add(displayNameField);
		
		JButton btnConnect = new JButton("Connect");
		btnConnect.setBounds(176, 134, 98, 26);
		btnConnect.addActionListener(e -> {
			connect();
		});
		panel.add(btnConnect);
		

		
		JLabel lblRegister = new JLabel("Register");
		lblRegister.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblRegister.setForeground(Color.BLUE);
		lblRegister.setBounds(367, 233, 55, 16);
		lblRegister.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					browse(new URL(REGISTER_URL));
				} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		});
		panel.add(lblRegister);
		
		JButton btnOptions = new JButton("Options");
		btnOptions.setBounds(12, 228, 98, 26);
		btnOptions.addActionListener(e -> {
			SwingUtilities.invokeLater(() -> {
				new OptionsWindow().setVisible(true);
			});
		});
		panel.add(btnOptions);
	}
	
	/**
	 * Log a user in.
	 */
	public void login() {
		// TODO if the user wants to login with a username and password.
		String username = usernameField.getText();
		String password = "";
		
		for (char c : passwordField.getPassword()) {
			password += Character.toString(c);
		}
		
		User user = LoginVerifier.login(username, password);

		
		if (user != null) {
			GroupChat.getInstance().setCurrentUser(user);
			JOptionPane.showMessageDialog(null, "You have logged in successfully!\nWelcome back, " + user.getName(), "Welcome Back!", JOptionPane.PLAIN_MESSAGE);
			GroupChat.getInstance().userIsAnonymous = false;
			
			this.setVisible(false);
			
			GroupChat.getInstance().openChatWindow();
		} else {
			JOptionPane.showMessageDialog(null, "Error: Invalid login information.\nIf you believe this is an error, please contact an administrator.", "Error!", JOptionPane.ERROR_MESSAGE);
			this.setVisible(true);
		}
	}
	
	/**
	 * Connect the user without logging them in as a registered user.
	 */
	public void connect() {
		// TODO if the user wants to connect with only a display name.
		
		// Yes = 0, No = 1.
		int conf = JOptionPane.showConfirmDialog(null, "Are you sure you want to connect with only a display name?\nYour IP Address will be logged by the server.", "Connect?", JOptionPane.YES_NO_OPTION);
//		System.out.println(conf);
		
		if (displayNameField.getText().replaceAll(" ", "").replaceAll("\n", "").equals("")) {
			return;
		}
		
		try {
			InetAddress addr = InetAddress.getLocalHost();
			
			if (conf == 0) {
//				System.out.println(addr.getHostAddress());
				
				User user = new User(addr.getHostAddress(), displayNameField.getText(), Long.parseLong(addr.getHostAddress().replaceAll("\\.", "")), Role.DEFAULT);
				GroupChat.getInstance().setCurrentUser(user);
				GroupChat.getInstance().userIsAnonymous = true;
				
				
				this.setVisible(false);
				
				if (GroupChat.getInstance().isLoggedIn()) {
					GroupChat.getInstance().openChatWindow();
				}
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * Opens a given URL in the OS's browser.
	 * @param url the URL to open in the browser.
	 * @return true if the method completed successfully, otherwise false.
	 */
	private static boolean browse(URL url) {
		Desktop desktop = (Desktop.isDesktopSupported())? Desktop.getDesktop() : null;
		
		try {
			desktop.browse(url.toURI());
			return true;
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
}
