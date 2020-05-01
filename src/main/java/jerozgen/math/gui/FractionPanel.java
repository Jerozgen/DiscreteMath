package jerozgen.math.gui;

import jerozgen.math.types.Fraction;

import javax.swing.*;

public class FractionPanel extends JPanel {
    public FractionPanel() {
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

        JButton help = new JButton("?");
        help.addActionListener(event -> {
            JOptionPane.showMessageDialog(this,"Формат дробей: I/N, I\nгде I — целое, " +
                    "N — натуральное.\n\nПримеры: 1/2, -3/4, 5.","Дроби", JOptionPane.INFORMATION_MESSAGE);
        });
        add(help);

        JButton RED_Q_Q = new JButton("Сократить A");
        RED_Q_Q.setToolTipText("Сокращение дроби");
        RED_Q_Q.addActionListener(event -> {
            try {
                result.setText(new Fraction(first.getText()).reduce().toString());
            } catch (Exception ex) {
                result.setText(ex.getMessage());
            }
        });
        add(RED_Q_Q);

        JButton INT_Q_B = new JButton("A ∈ ℤ?");
        INT_Q_B.setToolTipText("Проверка на целое");
        INT_Q_B.addActionListener(event -> {
            try {
                if (new Fraction(first.getText()).reduce().isInteger())
                    result.setText("Является целым");
                else result.setText("Не является целым");
            } catch (Exception ex) {
                result.setText(ex.getMessage());
            }
        });
        add(INT_Q_B);

        JButton ADD_QQ_Q = new JButton("A + B");
        ADD_QQ_Q.setToolTipText("Сложение дробей");
        ADD_QQ_Q.addActionListener(event -> {
            try {
                result.setText(new Fraction(first.getText()).add(new Fraction(second.getText())).toString());
            } catch (Exception ex) {
                result.setText(ex.getMessage());
            }
        });
        add(ADD_QQ_Q);

        JButton SUB_QQ_Q = new JButton("A – B");
        SUB_QQ_Q.setToolTipText("Вычитание дробей");
        SUB_QQ_Q.addActionListener(event -> {
            try {
                result.setText(new Fraction(first.getText()).subtract(new Fraction(second.getText())).toString());
            } catch (Exception ex) {
                result.setText(ex.getMessage());
            }
        });
        add(SUB_QQ_Q);

        JButton MUL_QQ_Q = new JButton("A × B");
        MUL_QQ_Q.setToolTipText("Умножение дробей");
        MUL_QQ_Q.addActionListener(event -> {
            try {
                result.setText(new Fraction(first.getText()).multiply(new Fraction(second.getText())).toString());
            } catch (Exception ex) {
                result.setText(ex.getMessage());
            }
        });
        add(MUL_QQ_Q);

        JButton DIV_QQ_Q = new JButton("A / B");
        DIV_QQ_Q.setToolTipText("Деление дробей ");
        DIV_QQ_Q.addActionListener(event -> {
            try {
                result.setText(new Fraction(first.getText()).divide(new Fraction(second.getText())).toString());
            } catch (Exception ex) {
                result.setText(ex.getMessage());
            }
        });
        add(DIV_QQ_Q);
    }
}
