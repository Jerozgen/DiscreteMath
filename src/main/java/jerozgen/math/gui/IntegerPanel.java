package jerozgen.math.gui;

import jerozgen.math.types.Integer;
import jerozgen.math.types.Natural;

import javax.swing.*;

public class IntegerPanel extends JPanel {
    public IntegerPanel() {
        super();

        add(new JLabel("Первое:"));
        JTextField first = new JTextField(16);
        add(first);

        add(new JLabel("Второе:"));
        JTextField second = new JTextField(16);
        add(second);

        add(new JLabel("Результат:"));
        JTextField result = new JTextField(24);
        result.setEditable(false);
        add(result);

        JButton ABS_Z_N = new JButton("ABS_Z_N");
        ABS_Z_N.setToolTipText("Абсолютная величина числа");
        ABS_Z_N.addActionListener(event -> {
            try {
                result.setText(new Integer(first.getText()).abs().toString());
            } catch (Exception ex) {
                result.setText(ex.getMessage());
            }
        });
        add(ABS_Z_N);

        JButton POZ_Z_D = new JButton("POZ_Z_D");
        POZ_Z_D.setToolTipText("Определение положительности числа");
        POZ_Z_D.addActionListener(event -> {
            try {
                switch (new Integer(first.getText()).getPositivity()) {
                    case ZERO:
                        result.setText("Ноль");
                        break;
                    case NEGATIVE:
                        result.setText("Отрицательное");
                        break;
                    case POSITIVE:
                        result.setText("Положительное");
                        break;
                }
            } catch (Exception ex) {
                result.setText(ex.getMessage());
            }
        });
        add(POZ_Z_D);

        JButton MUL_ZM_Z = new JButton("MUL_ZM_Z");
        MUL_ZM_Z.setToolTipText("Умножение числа на -1");
        MUL_ZM_Z.addActionListener(event -> {
            try {
                result.setText(new Integer(first.getText()).negate().toString());
            } catch (Exception ex) {
                result.setText(ex.getMessage());
            }
        });
        add(MUL_ZM_Z);

        JButton ADD_ZZ_Z = new JButton("ADD_ZZ_Z");
        ADD_ZZ_Z.setToolTipText("Сложение чисел");
        ADD_ZZ_Z.addActionListener(event -> {
            try {
                result.setText(new Integer(first.getText()).add(new Integer(second.getText())).toString());
            } catch (Exception ex) {
                result.setText(ex.getMessage());
            }
        });
        add(ADD_ZZ_Z);

        JButton SUB_ZZ_Z = new JButton("SUB_ZZ_Z");
        SUB_ZZ_Z.setToolTipText("Вычитание чисел");
        SUB_ZZ_Z.addActionListener(event -> {
            try {
                result.setText(new Integer(first.getText()).subtract(new Integer(second.getText())).toString());
            } catch (Exception ex) {
                result.setText(ex.getMessage());
            }
        });
        add(SUB_ZZ_Z);

        JButton MUL_ZZ_Z = new JButton("MUL_ZZ_Z");
        MUL_ZZ_Z.setToolTipText("Умножение чисел");
        MUL_ZZ_Z.addActionListener(event -> {
            try {
                result.setText(new Integer(first.getText()).multiply(new Integer(second.getText())).toString());
            } catch (Exception ex) {
                result.setText(ex.getMessage());
            }
        });
        add(MUL_ZZ_Z);

        JButton DIV_ZZ_Z = new JButton("DIV_ZZ_Z");
        DIV_ZZ_Z.setToolTipText("Частное от деления целого на натуральное с остатком");
        DIV_ZZ_Z.addActionListener(event -> {
            try {
                result.setText(new Integer(first.getText()).divide(new Natural(second.getText())).toString());
            } catch (Exception ex) {
                result.setText(ex.getMessage());
            }
        });
        add(DIV_ZZ_Z);

        JButton MOD_ZZ_Z = new JButton("MOD_ZZ_Z");
        MOD_ZZ_Z.setToolTipText("Остаток от деления целого на натуральное с остатком");
        MOD_ZZ_Z.addActionListener(event -> {
            try {
                result.setText(new Integer(first.getText()).remainder(new Natural(second.getText())).toString());
            } catch (Exception ex) {
                result.setText(ex.getMessage());
            }
        });
        add(MOD_ZZ_Z);
    }
}
