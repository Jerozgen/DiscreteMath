package jerozgen.math.gui;

import jerozgen.math.types.Natural;

import javax.swing.*;

public class NaturalPanel extends JPanel {
    public NaturalPanel() {
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

        JButton COM_NN_D = new JButton("A ? B");
        COM_NN_D.setToolTipText("Сравнение чисел");
        COM_NN_D.addActionListener(event -> {
            try {
                switch (new Natural(first.getText()).compareTo(new Natural(second.getText()))) {
                    case EQUAL:
                        result.setText("Равно");
                        break;
                    case LESS:
                        result.setText("Меньше");
                        break;
                    case MORE:
                        result.setText("Больше");
                        break;
                }
            } catch (Exception ex) {
                result.setText(ex.getMessage());
            }
        });
        add(COM_NN_D);

        JButton NZER_N_B = new JButton("A = 0?");
        NZER_N_B.setToolTipText("Проверка на ноль");
        NZER_N_B.addActionListener(event -> {
            try {
                if (new Natural(first.getText()).isNotZero())
                    result.setText("Не равно нулю");
                else result.setText("Равно нулю");
            } catch (Exception ex) {
                result.setText(ex.getMessage());
            }
        });
        add(NZER_N_B);

        JButton ADD_1N_N = new JButton("A + 1");
        ADD_1N_N.setToolTipText("Добавление единицы к числу");
        ADD_1N_N.addActionListener(event -> {
            try {
                result.setText(new Natural(first.getText()).addOne().toString());
            } catch (Exception ex) {
                result.setText(ex.getMessage());
            }
        });
        add(ADD_1N_N);

        JButton ADD_NN_N = new JButton("A + B");
        ADD_NN_N.setToolTipText("Сложение чисел");
        ADD_NN_N.addActionListener(event -> {
            try {
                result.setText(new Natural(first.getText()).add(new Natural(second.getText())).toString());
            } catch (Exception ex) {
                result.setText(ex.getMessage());
            }
        });
        add(ADD_NN_N);

        JButton SUB_NN_N = new JButton("|A – B|");
        SUB_NN_N.setToolTipText("Абсолютная разность чисел");
        SUB_NN_N.addActionListener(event -> {
            try {
                result.setText(new Natural(first.getText()).subtract(new Natural(second.getText())).toString());
            } catch (Exception ex) {
                result.setText(ex.getMessage());
            }
        });
        add(SUB_NN_N);

        JButton MUL_ND_N = new JButton("A × d");
        MUL_ND_N.setToolTipText("Умножение числа на цифру");
        MUL_ND_N.addActionListener(event -> {
            try {
                int digit = Integer.parseInt(second.getText());
                try {
                    result.setText(new Natural(first.getText()).multiplyByDigit(digit).toString());
                } catch (Exception ex) {
                    result.setText(ex.getMessage());
                }
            } catch (NumberFormatException ex) {
                result.setText("Неверный формат числа: " + second.getText());
            }
        });
        add(MUL_ND_N);

        JButton MUL_Nk_N = new JButton("A × 10^k");
        MUL_Nk_N.setToolTipText("Умножение числа на 10^k");
        MUL_Nk_N.addActionListener(event -> {
            try {
                int k = Integer.parseInt(second.getText());
                try {
                    result.setText(new Natural(first.getText()).appendZeros(k).toString());
                } catch (Exception ex) {
                    result.setText(ex.getMessage());
                }
            } catch (NumberFormatException ex) {
                result.setText("Неверный формат числа: " + second.getText());
            }
        });
        add(MUL_Nk_N);

        JButton MUL_NN_N = new JButton("A × B");
        MUL_NN_N.setToolTipText("Умножение чисел");
        MUL_NN_N.addActionListener(event -> {
            try {
                result.setText(new Natural(first.getText()).multiply(new Natural(second.getText())).toString());
            } catch (Exception ex) {
                result.setText(ex.getMessage());
            }
        });
        add(MUL_NN_N);

        JButton DIV_NN_Dk = new JButton("1-я цифра деления на 10^k");
        DIV_NN_Dk.setToolTipText("Вычисление первой цифры деления чисел, помноженной на 10^k, где k — номер позиции " +
                "этой цифры (номер считается с нуля)");
        DIV_NN_Dk.addActionListener(event -> {
            try {
                result.setText(new Natural(first.getText()).getScaledFirstQuotientDigit(new Natural(second.getText()))
                        .toString());
            } catch (Exception ex) {
                result.setText(ex.getMessage());
            }
        });
        add(DIV_NN_Dk);

        JButton DIV_NN_N = new JButton("A div B");
        DIV_NN_N.setToolTipText("Частное от деления чисел с остатком");
        DIV_NN_N.addActionListener(event -> {
            try {
                result.setText(new Natural(first.getText()).divide(new Natural(second.getText())).toString());
            } catch (Exception ex) {
                result.setText(ex.getMessage());
            }
        });
        add(DIV_NN_N);

        JButton MOD_NN_N = new JButton("A mod B");
        MOD_NN_N.setToolTipText("Остаток от деления чисел с остатком");
        MOD_NN_N.addActionListener(event -> {
            try {
                result.setText(new Natural(first.getText()).remainder(new Natural(second.getText())).toString());
            } catch (Exception ex) {
                result.setText(ex.getMessage());
            }
        });
        add(MOD_NN_N);

        JButton GCF_NN_N = new JButton("НОД(A, B)");
        GCF_NN_N.setToolTipText("НОД чисел");
        GCF_NN_N.addActionListener(event -> {
            try {
                result.setText(new Natural(first.getText()).gcf(new Natural(second.getText())).toString());
            } catch (Exception ex) {
                result.setText(ex.getMessage());
            }
        });
        add(GCF_NN_N);

        JButton LCM_NN_N = new JButton("НОК(A, B)");
        LCM_NN_N.setToolTipText("НОК чисел");
        LCM_NN_N.addActionListener(event -> {
            try {
                result.setText(new Natural(first.getText()).lcm(new Natural(second.getText())).toString());
            } catch (Exception ex) {
                result.setText(ex.getMessage());
            }
        });
        add(LCM_NN_N);
    }
}
