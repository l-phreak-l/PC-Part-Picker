package computerbuilder;


import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new ComputerBuilderGUI();
            System.out.println("Computer Builder GUI Started!");
        });
    }
}
