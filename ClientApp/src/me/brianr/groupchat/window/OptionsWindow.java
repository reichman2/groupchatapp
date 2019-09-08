package me.brianr.groupchat.window;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import me.brianr.groupchat.GroupChat;
import me.brianr.groupchat.filemanager.FileManager;

/**
 * This class is the window that you can change options from.
 * @author Brian Reich
 * @version 1.0
 * @since 1.0
 */
public class OptionsWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7917371778011336507L;
	private JPanel contentPane;
	private JTextField addressField;
	private JTextField portField;

	/**
	 * Create the frame.
	 */
	public OptionsWindow() {
		FileManager fm = new FileManager("files/config.yml");
		
		setAlwaysOnTop(true);
		setTitle("Options");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblServerProperties = new JLabel("Server Properties");
		lblServerProperties.setBounds(10, 11, 106, 14);
		panel.add(lblServerProperties);
		
		addressField = new JTextField();
		addressField.setBounds(20, 52, 153, 20);
		addressField.setText(GroupChat.getInstance().getConfig().getServerAddress());
		panel.add(addressField);
		addressField.setColumns(10);
		
		JLabel lblServerAddress = new JLabel("Server Address");
		lblServerAddress.setBounds(20, 36, 121, 14);
		panel.add(lblServerAddress);
		
		JLabel lblServerPort = new JLabel("Server Port");
		lblServerPort.setBounds(23, 95, 78, 14);
		panel.add(lblServerPort);
		
		portField = new JTextField();
		portField.setBounds(20, 109, 86, 20);
		portField.setText(GroupChat.getInstance().getConfig().getServerPort());
		portField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO only numbers
//				switch (e.getKeyCode()) {
//					case KeyEvent.VK_0:
//					case KeyEvent.VK_1:
//					case KeyEvent.VK_2:
//					case KeyEvent.VK_3:
//					case KeyEvent.VK_4:
//					case KeyEvent.VK_5:
//					case KeyEvent.VK_6:
//					case KeyEvent.VK_7:
//					case KeyEvent.VK_8:
//					case KeyEvent.VK_9:
//						break;
//					default:
//						
//				}
				
				portField.setText(portField.getText().replaceAll("[^\\d\\.]", ""));
			}
		});
		panel.add(portField);
		portField.setColumns(10);
		
		JButton btnSave = new JButton("Save");
		btnSave.setBounds(335, 227, 89, 23);
		btnSave.addActionListener(e -> {
			fm.setString("server-properties.address", addressField.getText());
			fm.setString("server-properties.port", portField.getText());
			
			JOptionPane.showMessageDialog(null, "The application must be restarted for changes to take place", "", JOptionPane.INFORMATION_MESSAGE);
			System.exit(0);
		});
		panel.add(btnSave);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(236, 227, 89, 23);
		btnCancel.addActionListener(e -> {
			this.setVisible(false);
			this.dispose();
		});
		panel.add(btnCancel);
	}
}
