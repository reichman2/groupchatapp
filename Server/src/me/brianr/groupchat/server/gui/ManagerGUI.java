package me.brianr.groupchat.server.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * The GUI of the server.
 * <p><strong>This class is not currently implemented to be used.</strong></p>
 * @author Brian Reich
 * @version 1.0
 * @since 1.0
 */
public class ManagerGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6150983159548231961L;
	
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public ManagerGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

}
