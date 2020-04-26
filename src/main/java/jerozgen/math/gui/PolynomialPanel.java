package jerozgen.math.gui;

import jerozgen.math.types.Fraction;
import jerozgen.math.types.Polynomial;

import javax.swing.*;

public class PolynomialPanel extends JPanel {
    public PolynomialPanel() {
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
            JOptionPane.showMessageDialog(this,"Формат слагаемых многочлена: " +
                    "Qx^D, x^D, Qx, Q, x,\nгде Q — дробь, D — 4-байтовое целое со знаком." +
                    "\n\nПример: 2x^3 + 1/2x^2 - x + 3/4 - 2x^-1.","Многочлены", JOptionPane.INFORMATION_MESSAGE);
        });
        add(help);

        JButton ADD_PP_P = new JButton("ADD_PP_P");
        ADD_PP_P.setToolTipText("Сложение многочленов");
        ADD_PP_P.addActionListener(event -> {
            try {
                result.setText(Polynomial.parse(first.getText()).add(Polynomial.parse(second.getText())).toString());
            } catch (Exception ex) {
                result.setText(ex.getMessage());
            }
        });
        add(ADD_PP_P);

        JButton SUB_PP_P = new JButton("SUB_PP_P");
        SUB_PP_P.setToolTipText("Вычитание многочленов");
        SUB_PP_P.addActionListener(event -> {
            try {
                result.setText(Polynomial.parse(first.getText()).subtract(Polynomial.parse(second.getText())).toString());
            } catch (Exception ex) {
                result.setText(ex.getMessage());
            }
        });
        add(SUB_PP_P);

        JButton MUL_PQ_P = new JButton("MUL_PQ_P");
        MUL_PQ_P.setToolTipText("Умножение многочлена на дробь");
        MUL_PQ_P.addActionListener(event -> {
            try {
                result.setText(Polynomial.parse(first.getText()).multiplyByFraction(new Fraction(second.getText()))
                        .toString());
            } catch (Exception ex) {
                result.setText(ex.getMessage());
            }
        });
        add(MUL_PQ_P);

        JButton MUL_Pxk_P = new JButton("MUL_Pxk_P");
        MUL_Pxk_P.setToolTipText("Умножение многочлена на x^k");
        MUL_Pxk_P.addActionListener(event -> {
            try {
                int k = Integer.parseInt(second.getText());
                try {
                    result.setText(Polynomial.parse(first.getText()).multiplyByX(k).toString());
                } catch (Exception ex) {
                    result.setText(ex.getMessage());
                }
            } catch (NumberFormatException ex) {
                result.setText("Неверный формат числа: " + second.getText());
            }
        });
        add(MUL_Pxk_P);

        JButton LED_P_Q = new JButton("LED_P_Q");
        LED_P_Q.setToolTipText("Старший коэффициент многочлена");
        LED_P_Q.addActionListener(event -> {
            try {
                result.setText(Polynomial.parse(first.getText()).getLeadingCoefficient().toString());
            } catch (Exception ex) {
                result.setText(ex.getMessage());
            }
        });
        add(LED_P_Q);

        JButton DEG_P_N = new JButton("DEG_P_N");
        DEG_P_N.setToolTipText("Степень многочлена");
        DEG_P_N.addActionListener(event -> {
            try {
                result.setText(String.valueOf(Polynomial.parse(first.getText()).getDegree()));
            } catch (Exception ex) {
                result.setText(ex.getMessage());
            }
        });
        add(DEG_P_N);

        JButton FAC_P_Q = new JButton("FAC_P_Q");
        FAC_P_Q.setToolTipText("Вынесение из многочлена НОК знаменателей коэффициентов и НОД числителей");
        FAC_P_Q.addActionListener(event -> {
            try {
                result.setText(Polynomial.parse(first.getText()).getGeneralCoefficient().toString());
            } catch (Exception ex) {
                result.setText(ex.getMessage());
            }
        });
        add(FAC_P_Q);

        JButton MUL_PP_P = new JButton("MUL_PP_P");
        MUL_PP_P.setToolTipText("Умножение многочленов");
        MUL_PP_P.addActionListener(event -> {
            try {
                result.setText(Polynomial.parse(first.getText()).multiply(Polynomial.parse(second.getText())).toString());
            } catch (Exception ex) {
                result.setText(ex.getMessage());
            }
        });
        add(MUL_PP_P);

        JButton DIV_PP_P = new JButton("DIV_PP_P");
        DIV_PP_P.setToolTipText("Частное от деления многочленов с остатком");
        DIV_PP_P.addActionListener(event -> {
            try {
                result.setText(Polynomial.parse(first.getText()).divide(Polynomial.parse(second.getText())).toString());
            } catch (Exception ex) {
                result.setText(ex.getMessage());
            }
        });
        add(DIV_PP_P);

        JButton MOD_PP_P = new JButton("MOD_PP_P");
        MOD_PP_P.setToolTipText("Остаток от деления многочленов с остатком");
        MOD_PP_P.addActionListener(event -> {
            try {
                result.setText(Polynomial.parse(first.getText()).remainder(Polynomial.parse(second.getText())).toString());
            } catch (Exception ex) {
                result.setText(ex.getMessage());
            }
        });
        add(MOD_PP_P);

        JButton GCF_PP_P = new JButton("GCF_PP_P");
        GCF_PP_P.setToolTipText("НОД многочленов");
        GCF_PP_P.addActionListener(event -> {
            try {
                result.setText(Polynomial.parse(first.getText()).gcf(Polynomial.parse(second.getText())).toString());
            } catch (Exception ex) {
                result.setText(ex.getMessage());
            }
        });
        add(GCF_PP_P);

        JButton DER_P_P = new JButton("DER_P_P");
        DER_P_P.setToolTipText("Производная многочлена");
        DER_P_P.addActionListener(event -> {
            try {
                result.setText(Polynomial.parse(first.getText()).differentiate().toString());
            } catch (Exception ex) {
                result.setText(ex.getMessage());
            }
        });
        add(DER_P_P);

        JButton NMR_P_P = new JButton("NMR_P_P");
        NMR_P_P.setToolTipText("Преобразование многочлена — кратные корни в простые");
        NMR_P_P.addActionListener(event -> {
            try {
                result.setText(Polynomial.parse(first.getText()).squareFreeFactorization().toString());
            } catch (Exception ex) {
                result.setText(ex.getMessage());
            }
        });
        add(NMR_P_P);
    }
}
