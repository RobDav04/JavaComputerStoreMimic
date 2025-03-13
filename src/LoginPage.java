import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// This class creates the login page and checks if the user entered a correct login
public class LoginPage extends JFrame implements ActionListener {
    private JLabel u;
    private JLabel p;
    private static JTextField usernameTextField;
    private JPasswordField passwordTextField;
    private JButton submit;
    private JButton cancel;
    private static StaffType currentUser;

    // Create the login
    public void createLoginPage() {
        // Add the users to the system.
        StaffType.addUsers();
        setTitle("Login Page");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        u = new JLabel("Username: ");
        p = new JLabel("Password: ");

        usernameTextField = new JTextField(20);
        passwordTextField = new JPasswordField(20);

        submit = new JButton("Submit");
        cancel = new JButton("Cancel");

        // Add an action listener to each of the buttons
        submit.addActionListener(this);
        cancel.addActionListener(this);

        // Set up the grid
        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(u);
        panel.add(usernameTextField);
        panel.add(p);
        panel.add(passwordTextField);
        panel.add(submit);
        panel.add(cancel);

        add(panel);
        pack();
        setLocationRelativeTo(null); // Center the frame
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submit) {
            handleLogin();
            dispose();
        } else if (e.getSource() == cancel) {
            dispose(); // Close the login window
        }
    }

    // This checks if the login is correct or wrong
    private void handleLogin() {
        // Get the entered strings in the text fields
        String username = usernameTextField.getText();
        String password = new String(passwordTextField.getPassword());

        // We loop over each of the staff
        boolean loginSuccess = false;
        for (StaffType s : StaffType.getStaff()) {
            // If it matches any of the stuff we save it as the current user to know if it's a manager or not for
            // future use
            if (s.getUsername().equals(username) && s.getPassword().equals(password)) {
                currentUser = s;
                // We create the main pane GUI and close the main window
                UserInterface.createGUI();
                UserInterface.disposeMainWindow();
                loginSuccess = true;
                break;
            }
        }
        if (!loginSuccess) {
            // If there was no login success we let the user know
            JOptionPane.showMessageDialog(this, "Incorrect login!", "Login Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public static StaffType getCurrentUser() { return currentUser; }
}

