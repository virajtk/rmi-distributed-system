package desktop_forms;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

public class WelcomeForm {

	private JFrame frame;

	/**
	 * Start the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WelcomeForm window = new WelcomeForm();
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
	public WelcomeForm() {
		initialize();
	}

	/**
	 *  contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(350, 250, 382, 469);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("Welcome!");
		lblNewLabel.setFont(new Font("Serif", Font.BOLD, 35));
		lblNewLabel.setBounds(106,121, 169, 48);
		frame.getContentPane().add(lblNewLabel);

		JButton btnNewButton = new JButton("Admin");
		btnNewButton.setIcon(new ImageIcon(WelcomeForm.class.getResource("/img/admin.png")));
		btnNewButton.setFont(new Font("Serif", Font.PLAIN, 25));
		btnNewButton.addActionListener(new ActionListener() {
			// add event listner to the button
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				//  Admin login form
				AdminLoginForm adminLoginForm = new AdminLoginForm();
				adminLoginForm.setVisible(true);
			}
		});
		btnNewButton.setBounds(45, 248, 273, 55);
		frame.getContentPane().add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Guest");
		btnNewButton_1.setIcon(new ImageIcon(WelcomeForm.class.getResource("/img/user.png")));
		btnNewButton_1.setFont(new Font("Serif", Font.PLAIN, 25));
		btnNewButton_1.addActionListener(new ActionListener() {
			// add event listner to the button
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				//  guest user
				DashBoardForm dashboardForm = new DashBoardForm(false);
				dashboardForm.main(null);
			}
		});
		btnNewButton_1.setBounds(45, 321, 273, 55);
		frame.getContentPane().add(btnNewButton_1);

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setIcon(new ImageIcon(WelcomeForm.class.getResource("/img/logo.jpg")));
		lblNewLabel_1.setBounds(21, 10, 324, 410);
		frame.getContentPane().add(lblNewLabel_1);
	}
}
