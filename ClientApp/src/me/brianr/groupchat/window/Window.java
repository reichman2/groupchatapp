package me.brianr.groupchat.window;

import java.awt.Adjustable;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;
import javax.swing.text.html.HTMLEditorKit;

import me.brianr.groupchat.GroupChat;
import me.brianr.groupchat.chat.Message;
import me.brianr.groupchat.filemanager.FileManager;

/**
 * The main chat window.
 * @author Brian Reich
 * @version 1.0
 * @since 1.0
 */
public class Window extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9172908563463585027L;
	
	private JPanel contentPane;
	private JEditorPane msgDisp;
	private JScrollPane scrollPane;
	

	/**
	 * Create the frame.
	 */
	public Window() {
		
		setMinimumSize(new Dimension(640, 480));
		setTitle("ChatApp");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// 0 = Yes, 1 = no.
				int conf = JOptionPane.showConfirmDialog(null, "Are you sure you want to leave?\nYou will be disconnected from the server.", "Close Confirmation", JOptionPane.YES_NO_OPTION);
				
				if (conf == 0) { // If the user wants to quit.
					// TODO make this safely disconnect and end all threads.
					GroupChat.getClientConnection().close();
					
					
					System.exit(0);
				}
			}
		});
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnChat = new JMenu("Chat");
		menuBar.add(mnChat);
		
		JMenuItem mntmOptions = new JMenuItem("Options");
		mnChat.add(mntmOptions);
		
		JSeparator separator = new JSeparator();
		mnChat.add(separator);
		
		JMenuItem mntmLogout = new JMenuItem("Logout");
		mnChat.add(mntmLogout);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mnHelp.add(mntmAbout);
		
		JMenuItem mntmReportABug = new JMenuItem("Report A Bug");
		mnHelp.add(mntmReportABug);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		scrollPane = new JScrollPane();
		scrollPane.scrollRectToVisible(new Rectangle(0, getBounds(null).height, 1, 1));
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		msgDisp = new JEditorPane();
		msgDisp.setContentType("text/html;charset=UTF-8");
		msgDisp.setEditable(false);
		msgDisp.setEditorKit(new HTMLEditorKit());
		scrollPane.setViewportView(msgDisp);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane_1 = new JScrollPane();
		panel.add(scrollPane_1, BorderLayout.CENTER);
		
		JTextArea textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "none");
		textArea.addKeyListener(new KeyAdapter() {
			
			private boolean isCtrlPressed = false;
			private boolean isEntrPressed = false;

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				isCtrlPressed = (e.getKeyCode() == KeyEvent.VK_CONTROL)? true : isCtrlPressed;
				isEntrPressed = (e.getKeyCode() == KeyEvent.VK_ENTER)? true : isEntrPressed;
				
				// Set stuff to do when control and/or enter is pressed.
				if (isCtrlPressed && isEntrPressed) { // If control and enter are pressed.
					textArea.setText(textArea.getText() + "\n"); // Line Break
					
				} else if (isEntrPressed && !isCtrlPressed) { // If only enter is pressed.
					sendMessage(textArea.getText()); // send the message
					textArea.setText(""); // clear the text area.
					updateMessages(); // update the messages. 
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				isCtrlPressed = (e.getKeyCode() == KeyEvent.VK_CONTROL)? false : isCtrlPressed;
				isEntrPressed = (e.getKeyCode() == KeyEvent.VK_ENTER)? false : isEntrPressed;
			}
			
		});
		
		scrollPane_1.setViewportView(textArea);
		
		JButton sendBtn = new JButton("Send");
		sendBtn.setActionCommand("send");
		sendBtn.addActionListener(e -> {
			// On Click
			sendMessage(textArea.getText());
			textArea.setText("");
			
			updateMessages();
		});
		panel.add(sendBtn, BorderLayout.EAST);
		
		
		updateMessages();
	}
	
	/**
	 * Get the message panel display area.
	 * @return the message panel display area.
	 */
	public JEditorPane getMsgDisp() {
		return this.msgDisp;
	}
	
	/**
	 * Add a message to the display.
	 * @param msg the message to add.
	 */
	public void addMessage(Message msg) {
		msgDisp.setText(msgDisp.getText() + msg.toString());
	}
	
	/**
	 * Update all of the messages on the panel.
	 */
	public void updateMessages() {
		FileManager fm = new FileManager("files/room-00.yml");
		StringBuilder sb = new StringBuilder();
		
		for (Message m : fm.getMessages()) {
			m.htmlSpecialChars();
			sb.append(m.toString() + "<br />");
		}
		
		msgDisp.setText(sb.toString());
		
		scrollPane.getVerticalScrollBar().setValue(((Adjustable) scrollPane.getVerticalScrollBar()).getMaximum() - 1);
	}
	
	
	/**
	 * Send a message.
	 * @param text the text of the message to send.
	 */
	public synchronized void sendMessage(String text) {
		if (GroupChat.getInstance().getCurrentUser() == null) {
			return;
		}
		
		switch (GroupChat.getInstance().getCurrentUser().getRole()) {
			case SERVER:
				break;
			case ADMIN:
				break;
			default: 
				Message.htmlSpecialChars(text);
		}
		
		if (text.replaceAll(" ", "").replaceAll("\\n", "").equals(""))
			return;
		
		Message msg = new Message(text, GroupChat.getInstance().getCurrentUser(), new Date().getTime());
		
//		FileManager fm = new FileManager("files/room-00.yml", FileManager.YAML_FILE);
//		fm.saveMessage(msg); // TODO this may not be needed if the plan is to set it up so it will receive the message sent through the socket.
		
		// TODO send the message through the socket to other users.
		GroupChat.getClientConnection().sendMessage(msg);
	}

}
