import javax.swing.*;
import java.awt.*;
import java.util.Set;

// This class handles the core userinterface windows. That being the start menu and the panes that we use
// for the tables and the update tab.
public class UserInterface {

    private static JComboBox<String> categoryBox;
    private static JComboBox<String> typeBox;
    private static JTabbedPane pane;
    private static JFrame mainWindow;

    // When we first run the program we make a start page
    public static void startPage() {
        mainWindow = new JFrame("Computer Management System");
        mainWindow.setSize(1200, 250);
        mainWindow.setLayout(new FlowLayout());
        // Ensure that the PNG is in the correct location
        ImageIcon icon = new ImageIcon("computerStore.png");
        JButton button = new JButton("Click to login", icon);
        mainWindow.add(button);
        mainWindow.setVisible(true);
        mainWindow.setLocationRelativeTo(null);
        // When the user clicks the button it goes to the login page
        button.addActionListener(e -> new LoginPage().createLoginPage());
    }

    // This sets up the pane/tabbed windows
    public static void createGUI() {
        // This is for when the user has already logged in. The data has already been read and it will keep all
        // the updates made the manager. If we read the data again the table will get duplicate details.
        if(ComputerUtils.getHardware().isEmpty()) {
            ComputerUtils.readDataFile();
        }
        pane = new JTabbedPane();
        JFrame frame = new JFrame("Computer Products Management System");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel browseProducts = new JPanel(new BorderLayout());
        JPanel checkUpdate = new JPanel();

        browseProducts.add(createTableDropdown(), BorderLayout.NORTH);
        browseProducts.add(HardwareTable.createAndShowTable(), BorderLayout.CENTER);

        checkUpdate.add(CheckUpdateTab.createUpdateDeleteProduct(), BorderLayout.CENTER);

        pane.addTab("Browse Products", browseProducts);
        pane.addTab("Check/Update Products Details", checkUpdate);

        frame.add(pane, BorderLayout.CENTER);
        frame.add(createLogoutPanel(), BorderLayout.SOUTH);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        System.out.println(pane.getSelectedIndex());
    }

    // This is used to create the table dropdown for the user to select what they want to show on the table.
    public static JPanel createTableDropdown() {
        JPanel dropdownPanel = new JPanel();

        // We used a jumbo box for this
        Set<String> categories = ComputerUtils.getUniqueCategories(true);
        categoryBox = new JComboBox<>(categories.toArray(new String[0]));
        typeBox = new JComboBox<>();

        categoryBox.addActionListener(e -> { updateCategory(); });
        typeBox.addActionListener(e -> { updateTable();});

        dropdownPanel.add(new JLabel("Computer Category: "));
        dropdownPanel.add(categoryBox);
        dropdownPanel.add(new JLabel("Computer Type: "));
        dropdownPanel.add(typeBox);

        return dropdownPanel;
    }

    // This is used for the bottom of our page for our logout.
    public static JPanel createLogoutPanel() {
        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        ImageIcon icon = new ImageIcon("computerStore.png");
        JButton button = new JButton("Logout", icon);
        logoutPanel.add(button);
        // If the user clicks on the logout
        button.addActionListener(e -> {
            startPage();
            HardwareTable.clearTable();
            ((JFrame) SwingUtilities.getWindowAncestor(logoutPanel)).dispose();
        });
        return logoutPanel;
    }

    // We get the selected category then get the types associated with that category
    public static void updateCategory() {
        String selectedCategory = (String) categoryBox.getSelectedItem();
        Set<String> types = ComputerUtils.getUniqueTypes(selectedCategory, true);
        typeBox.setModel(new DefaultComboBoxModel<>(types.toArray(new String[0])));
        updateTable(); // Update the table when the category changes
    }

    // We use the selected values within the dropdown box and then update the table details depending on what is selected
    public static void updateTable() {
        String currentCategory = (String) categoryBox.getSelectedItem();
        String currentType = (String) typeBox.getSelectedItem();
        HardwareTable.updateTableDetails(currentCategory, currentType);
    }

    public static void setPane(int index) {
        pane.setSelectedIndex(index);
    }

    public static void disposeMainWindow() {
        mainWindow.dispose();
    }
}
