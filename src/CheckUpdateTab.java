import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

// This class handles the Check update tab.
public class CheckUpdateTab extends JFrame {

    private static final Map<String, JTextField> textFieldMap = new HashMap<>();
    private static JComboBox<String> categoryBox;
    private static JComboBox<String> typeBox;
    private static JButton addButton;
    private static JButton deleteButton;
    private static JButton clearButton;
    private static JButton updateButton;

    // We first create the main page that has all the text boxes buttons etc
    public static JPanel createUpdateDeleteProduct() {
        // We use a GridBagConstraints shortened to gbc. gbc slows us to set the grid x and y.
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        addRow(formPanel, "Model ID:");
        addDropdownRow(formPanel, "Category:");
        addDropdownRow(formPanel, "Type:");
        addRow(formPanel, "Brand:");
        addRow(formPanel, "CPU:");
        addRow(formPanel, "Memory Size:");
        addRow(formPanel, "SSD Capacity:");
        addRow(formPanel, "Screen Size:");
        addRow(formPanel, "Price:");

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(formPanel, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        addButton = new JButton("Add");
        deleteButton = new JButton("Delete");
        clearButton = new JButton("Clear");
        updateButton = new JButton("Update");

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(updateButton);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        mainPanel.add(buttonPanel, gbc);

        // Listeners for the buttons
        setUpButtonListeners(addButton, deleteButton, clearButton, updateButton);
        checkUser();

        return mainPanel;
    }

    // This function is used to check what the user is. Whether they are a manager or not. If they are not a manager
    // we then lock all the functions in the update tab.
    private static void checkUser() {
        StaffType currentUser = LoginPage.getCurrentUser();
        if (!currentUser.getIsManager()) {
            for (JTextField textField : textFieldMap.values()) {
                textField.setEnabled(false);
            }
            addButton.setEnabled(false);
            deleteButton.setEnabled(false);
            clearButton.setEnabled(false);
            updateButton.setEnabled(false);
            categoryBox.setEnabled(false);
            typeBox.setEnabled(false);
        }
    }

    // We are adding a row to the page.
    private static void addRow(JPanel panel, String labelText) {
        JLabel label = new JLabel(labelText);
        JTextField textField = new JTextField(20);
        panel.add(label);
        panel.add(textField);
        textFieldMap.put(labelText, textField);
    }

    // We are adding a dropdown menu instead of the text field.
    private static void addDropdownRow(JPanel panel, String labelText) {
        JLabel label = new JLabel(labelText);
        JPanel dropdownPanel = createUpdateDropdown(labelText);
        panel.add(label);
        panel.add(dropdownPanel);
    }

    // Here we are adding the actual content to the dropdown
    private static JPanel createUpdateDropdown(String categoryOrType) {
        JPanel dropdownPanel = new JPanel();
        if (categoryOrType.equals("Category:")) {
            Set<String> categories = ComputerUtils.getUniqueCategories(false);
            categoryBox = new JComboBox<>(categories.toArray(new String[0]));
            categoryBox.addActionListener(e -> updateTypeBox());
            dropdownPanel.add(categoryBox);
        } else if (categoryOrType.equals("Type:")) {
            typeBox = new JComboBox<>();
            dropdownPanel.add(typeBox);
        }
        return dropdownPanel;
    }

    // Here we are updating the typeBox depending on what option is selected in the categoryBox
    private static void updateTypeBox() {
        String selectedCategory = (String) categoryBox.getSelectedItem();
        Set<String> types = ComputerUtils.getUniqueTypes(selectedCategory, false);
        typeBox.setModel(new DefaultComboBoxModel<>(types.toArray(new String[0])));
        updateFieldAvailability(selectedCategory);
    }

    // Here we are updating the text fields depending on what the selected category is. We don't want users to
    // add for example screen size to a desktop PC when the desktop PC does not have that option.
    private static void updateFieldAvailability(String selectedCategory) {
        StaffType currentUser = LoginPage.getCurrentUser();
        // We check to see if the user is a manager and if they are not we return as we don't want to unlock any
        // of the text fields.
        if(!currentUser.getIsManager()) {
            return;
        }
        boolean isTablet = "Tablet".equals(selectedCategory);
        boolean isDesktop = "Desktop PC".equals(selectedCategory);

        textFieldMap.get("Memory Size:").setEnabled(true);
        textFieldMap.get("SSD Capacity:").setEnabled(true);
        textFieldMap.get("Screen Size:").setEnabled(true);

        if (isTablet) {
            textFieldMap.get("Memory Size:").setEnabled(false);
            textFieldMap.get("Memory Size:").setText("");
            textFieldMap.get("SSD Capacity:").setEnabled(false);
            textFieldMap.get("SSD Capacity:").setText("");
        } else if (isDesktop) {
            textFieldMap.get("Screen Size:").setEnabled(false);
            textFieldMap.get("Screen Size:").setText("");
        }
    }

    // Simple function to clear all the text fields
    private static void clearTextFields() {
        for (JTextField textField : textFieldMap.values()) {
            textField.setText("");
        }
    }

    // Here we are getting the details and filling in the fields
    public static void updateFields(HardwareType selectedItem) {
        if (selectedItem != null) {
            clearTextFields();
            textFieldMap.get("Model ID:").setText(selectedItem.getID());
            categoryBox.setSelectedItem(selectedItem.getCategory());

            updateFieldAvailability(selectedItem.getCategory());

            updateTypeBox();
            typeBox.setSelectedItem(selectedItem.getType());
            textFieldMap.get("Brand:").setText(selectedItem.getBrand());
            textFieldMap.get("CPU:").setText(selectedItem.getCPU());
            textFieldMap.get("Price:").setText(String.valueOf(selectedItem.getPrice()));
            if (selectedItem instanceof Desktop || selectedItem instanceof Laptop) {
                textFieldMap.get("Memory Size:").setText(String.valueOf(selectedItem.getMemorySize()));
                textFieldMap.get("SSD Capacity:").setText(String.valueOf(selectedItem.getCapacitySize()));
            }
            if (selectedItem instanceof Laptop || selectedItem instanceof Tablet) {
                textFieldMap.get("Screen Size:").setText(String.valueOf(selectedItem.getScreenSize()));
            }
        }
    }

    // This is our button listeners for all the buttons
    private static void setUpButtonListeners(JButton addButton, JButton deleteButton, JButton clearButton, JButton updateButton) {
        addButton.addActionListener(new ActionListener() {
            @Override
            // Add button
            public void actionPerformed(ActionEvent e) {
                System.out.println("Add button clicked");
                try {
                    // Get the details
                    String category = (String) categoryBox.getSelectedItem();
                    String type = (String) typeBox.getSelectedItem();
                    String id = textFieldMap.get("Model ID:").getText();
                    // If the ID is null or empty we give an error and don't proceed any further
                    if (id == null || id.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "The ID is empty");
                        return;
                    }
                    // If the ID already exists then we give an error and don't proceed any further
                    for (HardwareType h : ComputerUtils.getHardware()) {
                        if(id.equals(h.getID())) {
                            JOptionPane.showMessageDialog(null, "This ID already exists!");
                            return;
                        }
                    }
                    String brand = textFieldMap.get("Brand:").getText();
                    String cpu = textFieldMap.get("CPU:").getText();
                    double price = Double.parseDouble(textFieldMap.get("Price:").getText());

                    assert category != null;
                    // We use a switch to check the category then depending on the category we add it to the array list
                    // within the computer utils class. This will update the table as well.
                    switch (category) {
                        case "Desktop PC" -> {
                            int memorySize = Integer.parseInt(textFieldMap.get("Memory Size:").getText());
                            int capacitySize = Integer.parseInt(textFieldMap.get("SSD Capacity:").getText());
                            Desktop desktop = new Desktop(category, type, id, brand, cpu, price, memorySize, capacitySize);
                            ComputerUtils.getHardware().add(desktop);
                        }
                        case "Laptop" -> {
                            int memorySize = Integer.parseInt(textFieldMap.get("Memory Size:").getText());
                            int capacitySize = Integer.parseInt(textFieldMap.get("SSD Capacity:").getText());
                            double screenSize = Double.parseDouble(textFieldMap.get("Screen Size:").getText());
                            Laptop laptop = new Laptop(category, type, id, brand, cpu, price, memorySize, capacitySize, screenSize);
                            ComputerUtils.getHardware().add(laptop);
                        }
                        case "Tablet" -> {
                            double screenSize = Double.parseDouble(textFieldMap.get("Screen Size:").getText());
                            Tablet tablet = new Tablet(category, type, id, brand, cpu, price, screenSize);
                            ComputerUtils.getHardware().add(tablet);
                        }
                    }
                    JOptionPane.showMessageDialog(null, "The item has been added!");
                    HardwareTable.updateTableDetails("All", "All");
                    clearTextFields();
                } catch (NumberFormatException nfe) {
                    // We use NumberFormatException to make sure the user does not enter any values that they
                    // are not meant to. For example entering a string into the price text field as the price text field
                    // is set up as a double. If this happens we alert the user with the error message.
                    JOptionPane.showMessageDialog(null, "Invalid input at the numeric fields: " + nfe.getMessage());
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            // Delete button
            public void actionPerformed(ActionEvent e) {
                System.out.println("Delete button clicked");
                HardwareType selectedItem = HardwareTable.getSelectedItem();
                // We get the selected item then loop through the array list till we find the item within the list
                // to remove. We then save it then remove it from the hardware list.
                if (selectedItem != null) {
                    HardwareType itemToRemove = null;
                    for (HardwareType h : ComputerUtils.getHardware()) {
                        if (Objects.equals(h.getID(), selectedItem.getID())) {
                            itemToRemove = h;
                            break;
                        }
                    }
                    if (itemToRemove != null) {
                        ComputerUtils.getHardware().remove(itemToRemove);
                        HardwareTable.updateTableDetails("All", "All");
                        JOptionPane.showMessageDialog(null,"Item deleted: " + selectedItem.getID());
                        clearTextFields();
                    } else {
                        JOptionPane.showMessageDialog(null, "No item found!");
                    }
                }
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            // Very simple clear making use of the clearTextFields function.
            public void actionPerformed(ActionEvent e) {
                System.out.println("Clear button clicked");
                clearTextFields();
                categoryBox.setSelectedIndex(0);
                typeBox.setModel(new DefaultComboBoxModel<>());
                JOptionPane.showMessageDialog(null, "Fields have been cleared!");
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            // Update button
            public void actionPerformed(ActionEvent e) {
                System.out.println("Update button clicked");
                HardwareType selectedItem = HardwareTable.getSelectedItem();
                try {
                    String category = (String) categoryBox.getSelectedItem();
                    // If no category is selected we alert the user and don't proceed any further
                    if (category == null || category.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "The category is empty!");
                        return;
                    }
                    String type = (String) typeBox.getSelectedItem();
                    String id = textFieldMap.get("Model ID:").getText();
                    // If the ID is empty we alert the user and don't proceed any further
                    if (id == null || id.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "The ID is empty!");
                        return;
                    }
                    // We check to see if we found the item to update
                    boolean found = false;
                    for(HardwareType h : ComputerUtils.getHardware()) {
                        if(id.equals(h.getID())) {
                            found = true;
                            break;
                        }
                    }
                    // If we don't find it alert the user and don't proceed any further
                    if (!found) {
                        JOptionPane.showMessageDialog(null, "No item with that ID exists!");
                        return;
                    }
                    String brand = textFieldMap.get("Brand:").getText();
                    String cpu = textFieldMap.get("CPU:").getText();
                    double price = Double.parseDouble(textFieldMap.get("Price:").getText());

                    selectedItem.setCategory(category);
                    selectedItem.setType(type);
                    selectedItem.setID(id);
                    selectedItem.setBrand(brand);
                    selectedItem.setCPU(cpu);
                    selectedItem.setPrice(price);

                    // Here we update the details
                    if (selectedItem instanceof Desktop) {
                        int memorySize = Integer.parseInt(textFieldMap.get("Memory Size:").getText());
                        int capacitySize = Integer.parseInt(textFieldMap.get("SSD Capacity:").getText());
                        ((Desktop) selectedItem).setMemorySize(memorySize);
                        ((Desktop) selectedItem).setCapacitySize(capacitySize);
                    } else if (selectedItem instanceof Laptop) {
                        int memorySize = Integer.parseInt(textFieldMap.get("Memory Size:").getText());
                        int capacitySize = Integer.parseInt(textFieldMap.get("SSD Capacity:").getText());
                        double screenSize = Double.parseDouble(textFieldMap.get("Screen Size:").getText());
                        ((Laptop) selectedItem).setMemorySize(memorySize);
                        ((Laptop) selectedItem).setCapacitySize(capacitySize);
                        ((Laptop) selectedItem).setScreenSize(screenSize);
                    } else if (selectedItem instanceof Tablet) {
                        double screenSize = Double.parseDouble(textFieldMap.get("Screen Size:").getText());
                        ((Tablet) selectedItem).setScreenSize(screenSize);
                    }

                    HardwareTable.updateTableDetails("All", "All");
                    JOptionPane.showMessageDialog(null,"Item updated: " + selectedItem.getID());
                } catch (NumberFormatException nfe) {
                    // Similar to the add button we do the same and check that the user has entered correct values.
                    JOptionPane.showMessageDialog(null, "Invalid input in one of the numeric fields: " + nfe.getMessage());
                }
            }
        });
    }
}
