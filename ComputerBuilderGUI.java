package computerbuilder;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

public class ComputerBuilderGUI {
    private JFrame mainFrame;
    private JPanel mainPanel;
    private JLabel partImageLabel;
    private JTextArea statsTextArea;
    private JList<String> partsList;
    private DefaultListModel<String> listModel;
    private JComboBox<String> partTypeComboBox;
    private JComboBox<String> specificPartComboBox;

    private List<ComputerPart> availableParts;
    private List<ComputerPart> selectedParts;
    private double totalPrice;
    private String userName;
    private JLabel runningTotalLabel;

// first int of program, starts GUI
    // added debug program and build type program
    public ComputerBuilderGUI() {
        selectedParts = new ArrayList<>();
        initializePartsDatabase();
        debugResourcesFolder();
        String buildType = showBuildTypeDialog();
        this.userName = getUserName();



        initializeGUI();
    }
    private String getUserName() {
        String name = "";
        while (name == null|| name.trim().isEmpty()) {
            name = JOptionPane.showInputDialog(null,
                    "Please enter your name:",
                    "User Information",
                    JOptionPane.PLAIN_MESSAGE);
            if (name == null) {
                name = "";
            }
            if (name.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "Name cannot be empty. Please enter your name.");
            }
        }
        return name.trim();

    }

    private String showBuildTypeDialog() {
        Object[] options = {"PC Build", "Laptop Build"};
        int choice = JOptionPane.showOptionDialog(null,
                "What type of computer are you building?",
                "Build Type Selection",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]
        );
        return choice == 0 ? "PC Build" : "Laptop Build";

    }


        // reformat to have a prompt for PC or LAPTOP first
    private void initializePartsDatabase() {
        availableParts = new ArrayList<>();


        availableParts.add(new ComputerPart("Full Tower Case", "Large ATX case", 120.00, "tower.png"));
        availableParts.add(new ComputerPart("Mid Tower Case", "Medium ATX case", 80.00, "tower.png"));
        availableParts.add(new ComputerPart("Mini Tower Case", "Small micro-ATX case", 60.00, "tower.png"));


        availableParts.add(new ComputerPart("AMD Ryzen 5", "6-core processor", 200.00, "cpu.png"));
        availableParts.add(new ComputerPart("Intel i5", "6-core processor", 220.00, "cpu.png"));
        availableParts.add(new ComputerPart("AMD Ryzen 7", "8-core processor", 300.00, "cpu.png"));
        availableParts.add(new ComputerPart("Intel i7", "8-core processor", 320.00, "cpu.png"));


        availableParts.add(new ComputerPart("24-inch Monitor", "1080p display", 120.00, "monitor.png"));
        availableParts.add(new ComputerPart("27-inch Monitor", "1440p display", 200.00, "monitor.png"));
        availableParts.add(new ComputerPart("32-inch Monitor", "4K display", 350.00, "monitor.png"));


        availableParts.add(new ComputerPart("500GB SSD", "Solid State Drive", 60.00, "hdd.png"));
        availableParts.add(new ComputerPart("1TB SSD", "Solid State Drive", 100.00, "hdd.png"));
        availableParts.add(new ComputerPart("2TB HDD", "Hard Disk Drive", 80.00, "hdd.png"));


        availableParts.add(new ComputerPart("Basic Desktop", "Entry-level desktop", 500.00, "desktop.png"));
        availableParts.add(new ComputerPart("Gaming Desktop", "High-performance desktop", 1200.00, "desktop.png"));
        availableParts.add(new ComputerPart("Office Laptop", "Business laptop", 600.00, "laptop.png"));
        availableParts.add(new ComputerPart("Gaming Laptop", "High-performance laptop", 1500.00, "laptop.png"));
    }

    private void initializeGUI() {

        mainFrame = new JFrame("Computer Part Builder");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(1000, 700);
        mainFrame.setLayout(new BorderLayout());


        JLabel titleLabel = new JLabel("PC Part Picker", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.BLACK);
        mainFrame.add(titleLabel, BorderLayout.NORTH);


        mainPanel = new JPanel(new GridLayout(1, 3));

        // int for different panel sections
        JPanel leftPanel = createPartSelectionPanel();


        JPanel middlePanel = createImagePanel();


        JPanel rightPanel = createBuildSummaryPanel();

        mainPanel.add(leftPanel);
        mainPanel.add(middlePanel);
        mainPanel.add(rightPanel);

        mainFrame.add(mainPanel, BorderLayout.CENTER);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }
// debugpicture or resources method
    private void debugResourcesFolder(){
        System.out.println(" DEBUG ");
        File resourcesDir = new File("resources");
        System.out.println("resources folder exists: " + resourcesDir.exists());
        System.out.println("resources folder path: " + resourcesDir.getAbsolutePath());
        if (resourcesDir.exists()){
            String[] files = resourcesDir.list();
            System.out.println("files in reasources folder:");
            if (files != null&& files.length > 0){
                for (String file : files){
                    System.out.println(" - " + file);
                }
            } else{
                System.out.println(" ( folder is empty)");
            }
        }
        File testImage = new File("resources/default_computer.jpg");
        System.out.println("default_computer.jpg exists: " + testImage.exists());

    }
    private JPanel createPartSelectionPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Part Selection"));


        JPanel categoryPanel = new JPanel(new GridLayout(7, 1, 5, 15));
        categoryPanel.add(new JLabel("Part Category:"));

        String[] categories = {"Select Category", "Cases", "Processors", "Monitors", "Storage", "Pre-built Systems"};
        partTypeComboBox = new JComboBox<>(categories);
        categoryPanel.add(partTypeComboBox);


        categoryPanel.add(new JLabel("Specific Part:"));
        specificPartComboBox = new JComboBox<>();
        categoryPanel.add(specificPartComboBox);


        JButton addPartButton = new JButton("Add to Build");
        categoryPanel.add(addPartButton);


        JButton removePartButton = new JButton("Remove Selected");
        categoryPanel.add(removePartButton);


        JButton clearAllButton = new JButton("Clear All Parts");
        categoryPanel.add(clearAllButton);


        partTypeComboBox.addActionListener(e -> updatePartSelection());
        addPartButton.addActionListener(e -> addSelectedPart());
        removePartButton.addActionListener(e -> removeSelectedPart());
        clearAllButton.addActionListener(e -> clearAllParts());

        panel.add(categoryPanel, BorderLayout.NORTH);
        return panel;
    }
            //upload PNG files for image panel
    private JPanel createImagePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Part Preview"));

        partImageLabel = new JLabel();
        partImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        partImageLabel.setPreferredSize(new Dimension(300, 250));
        updateImage("default_computer.jpg");

        panel.add(partImageLabel, BorderLayout.CENTER);


        statsTextArea = new JTextArea(8, 30);
        statsTextArea.setEditable(false);
        statsTextArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        statsTextArea.setText("Select a part to see details...");

        JScrollPane statsScrollPane = new JScrollPane(statsTextArea);
        panel.add(statsScrollPane, BorderLayout.SOUTH);

        return panel;
    }
    // summary panel doesnt accurately present price (delete or add a loop that checks and calculates as you add)
    private JPanel createBuildSummaryPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Your Build"));


        listModel = new DefaultListModel<>();
        partsList = new JList<>(listModel);
        partsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane listScrollPane = new JScrollPane(partsList);
        panel.add(listScrollPane, BorderLayout.CENTER);

       runningTotalLabel = new JLabel(calculateRunningTotal("Current Total"));
       runningTotalLabel.setFont(new Font("Arial", Font.BOLD, 14));
       runningTotalLabel.setForeground(Color.BLUE);

        JPanel totalPanel = new JPanel(new BorderLayout());
        JLabel totalLabel = new JLabel("Calculate total");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalLabel.setForeground(Color.RED);

        JButton calculateButton = new JButton("Calculate Total");
        calculateButton.addActionListener(e -> calculateTotal());

        totalPanel.add(runningTotalLabel, BorderLayout.NORTH);
        totalPanel.add(totalLabel, BorderLayout.CENTER);
        totalPanel.add(calculateButton, BorderLayout.SOUTH);

        panel.add(totalPanel, BorderLayout.SOUTH);

        return panel;
    }
    private void updateRunningTotalDisplay(){
        if (runningTotalLabel != null){
            runningTotalLabel.setText(calculateRunningTotal("Current Total"));
        }
    }

    private void updatePartSelection() {
        String category = (String) partTypeComboBox.getSelectedItem();
        specificPartComboBox.removeAllItems();

        if (!"Select Category".equals(category)) {
            for (ComputerPart part : availableParts) {
                if (partMatchesCategory(part, category)) {
                    specificPartComboBox.addItem(part.toString());
                }
            }
        }
    }

    private boolean partMatchesCategory(ComputerPart part, String category) {
        String name = part.getName().toLowerCase();
        switch (category) {
            case "Cases": return name.contains("tower") || name.contains("case");
            case "Processors": return name.contains("ryzen") || name.contains("i5") || name.contains("i7");
            case "Monitors": return name.contains("monitor") || name.contains("inch");
            case "Storage": return name.contains("ssd") || name.contains("hdd") || name.contains("tb");
            case "Pre-built Systems": return name.contains("desktop") || name.contains("laptop");
            default: return false;
        }
    }

    private void addSelectedPart() {
        if (specificPartComboBox.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(mainFrame, "Please select a part first!");
            return;
        }

        String selectedPartString = (String) specificPartComboBox.getSelectedItem();
        ComputerPart selectedPart = findPartByString(selectedPartString);

        if (selectedPart != null) {
            selectedParts.add(selectedPart);
            listModel.addElement(selectedPartString);
            updateImage(selectedPart.getImagePath());
            updateStatsDisplay(selectedPart);
            updateRunningTotalDisplay();
            String runningTotal = calculateRunningTotal(true);

            JOptionPane.showMessageDialog(mainFrame,
                    "Added: " + selectedPart.getName() + "\n" +
                            "Price: $" + selectedPart.getPrice()+"\n" + runningTotal);
        }
    }
// removes a single part from list
    private void removeSelectedPart() {
        int selectedIndex = partsList.getSelectedIndex();
        if (selectedIndex != -1) {
            ComputerPart removedPart = selectedParts.get(selectedIndex);
            selectedParts.remove(selectedIndex);
            listModel.remove(selectedIndex);
            updateRunningTotalDisplay();

            JOptionPane.showMessageDialog(mainFrame,
                    "Removed: " + removedPart.getName());
        } else {
            JOptionPane.showMessageDialog(mainFrame, "Please select a part to remove!");
        }
    }
 // clears entire list
    private void clearAllParts() {
        if (!selectedParts.isEmpty()) {
            int confirm = JOptionPane.showConfirmDialog(mainFrame,
                    "Clear all parts from your build?", "Confirm Clear", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                selectedParts.clear();
                listModel.clear();
                updateImage("default_computer.jpg");
                statsTextArea.setText("Build cleared!\nSelect new parts to get started.");
                updateRunningTotalDisplay();
            }
        }
    }

    private ComputerPart findPartByString(String partString) {
        for (ComputerPart part : availableParts) {
            if (part.toString().equals(partString)) {
                return part;
            }
        }
        return null;
    }
        // displays part stats, price, etc
    private void updateStatsDisplay(ComputerPart part) {
        statsTextArea.setText("PART DETAILS:\n\n" +
                "Name: " + part.getName() + "\n" +
                "Description: " + part.getDescription() + "\n" +
                "Price: $" + part.getPrice() + "\n\n" +
                "Total parts in build: " + selectedParts.size());
    }
    // calculates the total of all parts in cart
    private void calculateTotal() {
        totalPrice = calculateRunningTotal();

        System.out.printf("%n========== COMPUTER BUILD RECEIPT ==========%n");
        System.out.printf("Customer: %s%n", userName);
        System.out.printf("=============================================%n%n");

        for (int i = 0; i < selectedParts.size(); i++) {
            ComputerPart part = selectedParts.get(i);
            System.out.printf("%d. %-25s $%8.2f%n", i + 1, part.getName(), part.getPrice());
        }

        String finalTotal = calculateRunningTotal("Final Total");
        System.out.printf("%n%-25s $%8.2f%n", "TOTAL:", totalPrice);
        System.out.printf("=============================================%n%n");
        StringBuilder receipt = new StringBuilder();
        receipt.append("FINAL BUILD RECEIPT\n");
        receipt.append("===================\n\n");

        for (int i = 0; i < selectedParts.size(); i++) {
            ComputerPart part = selectedParts.get(i);
            receipt.append(String.format("%d. %-25s $%7.2f%n",
                    i + 1, part.getName(), part.getPrice()));
        }

        receipt.append(String.format("%n%-25s $%7.2f%n", "TOTAL:", totalPrice));

        statsTextArea.setText(receipt.toString());
        JOptionPane.showMessageDialog(mainFrame,
                String.format("Build Complete!\nTotal Price: $%.2f", totalPrice));
    }
    private double calculateTotalPrice() {
        return selectedParts.stream().mapToDouble(ComputerPart::getPrice).sum();
    }
    private double calculateRunningTotal(){
        return selectedParts.stream().mapToDouble(ComputerPart::getPrice).sum();
    }
    private String calculateRunningTotal(boolean formatAsString){
        double total = calculateRunningTotal();
        if (formatAsString) {
            return String.format("Current Total: $%.2f", total);
        }
        return String.valueOf(total);
    }
    private String calculateRunningTotal(String customMessage){
        double total = calculateRunningTotal();
        return String.format("%s: $%.2f", customMessage, total);
    }
    // *************************************** saved for posterity
    // private void updateImage(String imageName) {
    //  try {
    //   ImageIcon icon = new ImageIcon("resources/" + imageName);
    //    Image image = icon.getImage().getScaledInstance(300, 200, Image.SCALE_SMOOTH);
    //     partImageLabel.setIcon(new ImageIcon(image));
    //  } catch (Exception e) {
    //      partImageLabel.setText("<html><div style='text-align: center;'>" +
    //              "Part Image<br>" +
    //              "(" + imageName + ")</div></html>");
    //   }
    // }
    // potential fix below

    private void updateImage(String imageName) {
        try{
            java.io.File file = new java.io.File("resources/" + imageName);
                if (file.exists()) {
                        System.out.println("Found image: " + file.getAbsolutePath());
                        ImageIcon icon = new ImageIcon(file.getAbsolutePath());
                        Image image = icon.getImage().getScaledInstance(300,200, Image.SCALE_SMOOTH);
                            partImageLabel.setIcon(new ImageIcon(image));
                            partImageLabel.setText("");
                } else {
                    System.out.println("Image not found: resoureces/" + imageName);
                    partImageLabel.setText("Image not found: " + imageName);
                }
        } catch (Exception ex) {
            System.out.println("Error loading image: " + imageName);
            partImageLabel.setText("Error loading : " + imageName);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ComputerBuilderGUI());
    }
}