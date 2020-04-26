package jerozgen.math.gui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame() throws HeadlessException {
        super("Дискретная математика");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int sizeX = 854;
        int sizeY = 480;
        int locationX = (screenSize.width - sizeX) / 2;
        int locationY = (screenSize.height - sizeY) / 2;
        setBounds(locationX, locationY, sizeX, sizeY);
        setPreferredSize(new Dimension(sizeX, sizeY));
        setResizable(false);
        setVisible(true);

        JTabbedPane tabbedPane = new JTabbedPane();
        setContentPane(tabbedPane);
        ToolTipManager.sharedInstance().setInitialDelay(5);
        tabbedPane.addTab("Натуральные", new NaturalPanel());
        tabbedPane.addTab("Целые", new IntegerPanel());
        tabbedPane.addTab("Дроби", new FractionPanel());
        tabbedPane.addTab("Многочлены", new PolynomialPanel());
    }
}
