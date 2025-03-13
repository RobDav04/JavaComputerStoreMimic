import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Set;

public class HardwareTable {

    private static DefaultTableModel model;
    private static HardwareType selectedItem;

    // This is used to create our table and show it
    public static JScrollPane createAndShowTable() {
        model = getDefaultTableModel();
        JTable table = new JTable(model);
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 1) {
                    JTable targetedRow = (JTable) e.getSource();
                    int row = targetedRow.getSelectedRow();
                    if(row != -1) {
                        // Here we are getting the ID at the targeted row then finding the correct ID by ID.
                        // This is because if we don't do this the table still thinks that its "All". So by selecting
                        // the first item its picking as if the table was still "All"
                        String selectedID = (String) targetedRow.getValueAt(row, 2);
                        selectedItem = HardwareType.findHardwareByID(selectedID);

                        // Update the fields if the item is found
                        if(selectedItem != null) {
                            CheckUpdateTab.updateFields(selectedItem);
                            UserInterface.setPane(1);
                        }
                    }
                }
            }
        });

        return new JScrollPane(table);
    }

    // We set up what the default table will look like
    public static DefaultTableModel getDefaultTableModel() {
        String[] columnNames = {"Category", "Type", "ID", "Brand", "CPU", "Price"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        updateTableModel(model, "All", "All");
        return model;
    }

    // We update the table details by resetting the rows
    public static void updateTableDetails(String category, String type) {
        model.setRowCount(0);
        updateTableModel(model, category, type);
    }

    public static void updateTableModel(DefaultTableModel model, String category, String type) {
        // This is used to update our table model depending on what the user selected in the dropdown menus
        model.setRowCount(0);

        for(HardwareType h : ComputerUtils.getHardware()) {
            boolean currentCategory = category.equals("All") || h.getCategory().equals(category);
            boolean matchesType = type.equals("All") || h.getType().equals(type);
            // We have an object array that gets the details and then adds it to the new updated table.
            if(currentCategory && matchesType) {
                Object[] rowData = {
                        h.getCategory(),
                        h.getType(),
                        h.getID(),
                        h.getBrand(),
                        h.getCPU(),
                        h.getPrice()
                };
                model.addRow(rowData);
            }
        }
    }

    public static HardwareType getSelectedItem() {
        System.out.println(selectedItem);
        return selectedItem;
    }

    public static void clearTable() {
        model.setRowCount(0);
    }
}
