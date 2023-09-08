package EAgency.GUI;

import EAgency.ObjectPlus;
import EAgency.Person;
import EAgency.Property;
import lombok.Getter;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The type Card gui layout.
 */
@Getter
public class CardGUILayout {
    private JPanel cardPanel;
    private JPanel chooseAgentPanel;
    private JPanel filterResultsPanel;
    private JPanel foundResultsPanel;
    private JPanel propertyDetailsPanel;
    private JPanel mainInnerPanel;
    private JPanel mainSouthPanel;
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private JTextField minPriceTA;
    private JTextField maxPriceTA;
    private JComboBox typeComboBox;
    private JTable agentTable;
    private JTable propertyTable;
    private JTextField minAreaTA;
    private JTextField maxAreaTA;
    private JButton filterButton;
    private JButton goBackToChoosingAgentButton;
    private JLabel errorLabel;
    private JLabel resultLabel;
    private JPanel panel4;
    private JList propertyList;
    private JPanel panel5;
    private JButton selectPropertyButton;
    private JLabel selectionLabel;
    private JButton buyPropertyButton;
    private JPanel successPanel;
    private JButton goToChoosingAgentButton;
    private JButton seeMyPropertiesButton;
    private JPanel mainAppPanel;
    private JPanel innerNorth;
    private JPanel innerSouth;
    private JLabel helloLabel;
    private JButton pickAgentButton;
    private JButton goBackToFilterButton;
    private JButton goBackToFoundPropertiesButton;
    private JPanel iconPanel;
    private JPanel panel6;
    private JPanel panel7;
    private JPanel panel8;
    private JPanel panel9;
    private JPanel panel10;
    private JPanel panel11;
    private JPanel panel12;
    private JPanel panel13;
    private JLabel chooseLabel;
    private JLabel areYouSureLabel;
    private Person agent1;
    private Property boughtProperty;


    /**
     * Instantiates a new Card gui layout.
     *
     * @param client the client
     */
    public CardGUILayout(Person client) {

        helloLabel.setText(String.format("Hello %s. Your budget is %d zł.", client.getName(), (int) client.getClient().getBudget()));
        loadData();
        pickAgentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                java.util.List<Person> people = ObjectPlus.getExtentForClass(Person.class).stream().filter(i -> i.getEstateAgent() != null).toList();
                int index = agentTable.convertRowIndexToModel(agentTable.getSelectedRow());
                if (!agentTable.getSelectionModel().isSelectionEmpty()) {
                    Person agent = people.get(index);
                    agent1 = agent;
                } else {
                    chooseLabel.setText("Pick an agent");
                    return;
                }
                repaintView(filterResultsPanel);
            }
        });
        goBackToChoosingAgentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaintView(chooseAgentPanel);

            }
        });

        filterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Set<Property> agentProperties = agent1.getEstateAgent().getProperties();
                Set<Property> filteredProperties;
                if (typeComboBox.getSelectedItem().equals("House"))
                    filteredProperties = agentProperties.stream().filter(i -> i.getClass().getSimpleName().equals("House")).collect(Collectors.toSet());
                else if (typeComboBox.getSelectedItem().equals("Apartment"))
                    filteredProperties = agentProperties.stream().filter(i -> i.getClass().getSimpleName().equals("Apartment")).collect(Collectors.toSet());
                else
                    filteredProperties = agentProperties;
                try {
                    filteredProperties = getFilteredProperties(minAreaTA, filteredProperties, false, "Area");
                    filteredProperties = getFilteredProperties(maxAreaTA, filteredProperties, true, "Area");
                    filteredProperties = getFilteredProperties(minPriceTA, filteredProperties, false, "Price");
                    filteredProperties = getFilteredProperties(maxPriceTA, filteredProperties, true, "Price");

                } catch (NumberFormatException i) {
                    errorLabel.setText("Data should be numeric");
                    return;
                }
                if (filteredProperties.size() == 0) {
                    loadData(agentProperties);
                    selectionLabel.setText("No results found. Here are all agent's properties.");
                } else {
                    loadData(filteredProperties);
                    selectionLabel.setText(String.format("We found %d properties.", filteredProperties.size()));
                }
                repaintView(foundResultsPanel);
            }
        });
        goBackToFilterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaintView(filterResultsPanel);
            }
        });

        selectPropertyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                areYouSureLabel.setText("");
                if (propertyList.isSelectionEmpty()) {
                    selectionLabel.setText("Pick a property");
                    return;
                } else {
                    Property property = (Property) propertyList.getSelectedValue();
                    boughtProperty = property;
                    loadData(property);
                }

                repaintView(propertyDetailsPanel);
            }
        });

        goBackToFoundPropertiesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaintView(foundResultsPanel);
            }
        });
        buyPropertyButton.addActionListener(new ActionListener() {
            int i = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (i % 2 == 0) {
                    areYouSureLabel.setText("Are you sure you want to buy this house?");
                    i++;
                } else {
                    if (client.getClient().getBudget() < boughtProperty.getPrice()) {
                        areYouSureLabel.setText("You don't have enough money!");
                        i = 0;
                    } else {
                        client.getClient().buyProperty(boughtProperty);
                        helloLabel.setText(String.format("Hello %s. Your budget is %d zł.", client.getName(), (int) client.getClient().getBudget()));
                        repaintView(successPanel);
                    }
                }
            }
        });
        goToChoosingAgentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaintView(chooseAgentPanel);
            }
        });
        seeMyPropertiesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(client.getClient().getSaleSet());
                System.out.println(boughtProperty.getEstateAgent());
                System.out.println(boughtProperty.getSaleSet());

            }
        });
    }


    private Set<Property> getFilteredProperties(JTextField area, Set<Property> properties, boolean less, String field) {
        if (area.getText().isEmpty()) return properties;

        double d = Double.parseDouble(area.getText());
        if (less) {
            if (field.equals("Price"))
                properties = properties.stream().filter(e -> e.getPrice() < d).collect(Collectors.toSet());
            else properties = properties.stream().filter(e -> e.getArea() < d).collect(Collectors.toSet());
        } else {
            if (field.equals("Price"))
                properties = properties.stream().filter(e -> e.getPrice() > d).collect(Collectors.toSet());
            else properties = properties.stream().filter(e -> e.getArea() > d).collect(Collectors.toSet());
        }

        return properties;
    }

    private void loadData(Set<Property> properties) {
        DefaultListModel<Property> defaultListModel = new DefaultListModel<>();
        for (Property property : properties)
            defaultListModel.addElement(property);
        DefaultListCellRenderer renderer = (DefaultListCellRenderer) propertyList.getCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        renderer.setBorder(BorderFactory.createLineBorder(Color.black));
        propertyList.setOpaque(false);
        propertyList.setModel(defaultListModel);
    }

    private void loadData(Property property) {
        String address = "";
        String[] columnName = {""};
        String apartmentNumber = property.getClass().getSimpleName().equals("Apartment") ? ("/" + property.getRoomNumber()): "";
        Object[][] rowData = {{"Property type: " + property.getClass().getSimpleName()},
                {"Address: " + property.getAddress() + apartmentNumber},
                {"Construction Year: " + property.getConstructionYear()},
                {"Area: " + property.getArea()},
                {"Neighbourhood: " + property.getNeighbourhood().getName()},
                {"Number of rooms: " + property.getRoomNumber()},
                {"Price: " + property.getPrice() + " zł"}};

        TableModel data = new AbstractTableModel() {
            @Override
            public int getRowCount() {
                return rowData.length;
            }

            @Override
            public int getColumnCount() {
                return columnName.length;
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                return rowData[rowIndex][columnIndex];
            }

        };
        propertyTable.setBackground(new Color(0, 0, 0, 0));
        propertyTable.setOpaque(false);
        propertyTable.setModel(data);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        cardPanel = new TestPanel();
        chooseAgentPanel = new TestPanel();
        filterResultsPanel = new TestPanel();
        foundResultsPanel = new TestPanel();
        propertyDetailsPanel = new TestPanel();
        mainInnerPanel = new TestPanel();
        mainSouthPanel = new TestPanel();
        panel1 = new TestPanel();
        panel2 = new TestPanel();
        panel3 = new TestPanel();
        panel4 = new TestPanel();
        panel5 = new TestPanel();
        successPanel = new TestPanel();
        mainAppPanel = new TestPanel();
        innerNorth = new TestPanel();
        innerSouth = new TestPanel();
        iconPanel = new TestPanel();
        panel6 = new TestPanel();
        panel7 = new TestPanel();
        panel8 = new TestPanel();
        panel9 = new TestPanel();
        panel10 = new TestPanel();
        panel11 = new TestPanel();
        panel12 = new TestPanel();
        panel13 = new TestPanel();
        this.propertyList = new JList<Property>();
        this.propertyList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        typeComboBox = new JComboBox<String>();
        ((JLabel) typeComboBox.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        minAreaTA = new HorizontalTextField();
        minPriceTA = new HorizontalTextField();
        maxAreaTA = new HorizontalTextField();
        maxPriceTA = new HorizontalTextField();

        propertyList.setBackground(new Color(0, 0, 0, 0));
    }

    private void repaintView(JPanel panel) {
        cardPanel.removeAll();
        cardPanel.add(panel);
        cardPanel.repaint();
        cardPanel.revalidate();
    }

    private void loadData() {
        java.util.List<Person> agents = ObjectPlus.getExtentForClass(Person.class).stream().filter(e -> e.getEstateAgent() != null).toList();
        String[] columnName = {"Agents"};
        DefaultTableModel model = new DefaultTableModel(new Object[0][0], columnName);
        for (Object agent : agents) {
            Object[] o = new Object[1];
            o[0] = agent;
            model.addRow(o);
        }
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(JLabel.CENTER);
        agentTable.setBackground(new Color(0, 0, 0, 0));
        agentTable.setOpaque(false);
        agentTable.setRowHeight(30);
        agentTable.setDefaultEditor(Object.class, null);
        agentTable.setModel(model);
        agentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

}
