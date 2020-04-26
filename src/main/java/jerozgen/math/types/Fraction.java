package jerozgen.math.types;

import jerozgen.math.types.enums.Positivity;

import static jerozgen.math.types.enums.Positivity.NEGATIVE;
import static jerozgen.math.types.enums.Positivity.ZERO;

public class Fraction {
    public static Fraction ZERO_VALUE = new Fraction(Integer.ZERO_VALUE, Natural.ONE_VALUE);
    public static Fraction ONE_VALUE = new Fraction(Integer.ONE_VALUE, Natural.ONE_VALUE);

    private final Integer numerator;
    private final Natural denominator;

    public Fraction(String str) {
        int i = str.indexOf("/");
        if (i == 0 || i == str.length()) throw new NumberFormatException("Неверный формат дроби: " + str);
        try {
            if (i == -1) {
                this.numerator = new Integer(str);
                this.denominator = Natural.ONE_VALUE;
            } else {
                this.numerator = new Integer(str.substring(0, i));
                this.denominator = new Natural(str.substring(i + 1));
            }
        } catch (NumberFormatException ex) {
            throw new NumberFormatException("Неверный формат дроби: " + str);
        }
        if (!this.denominator.isNotZero()) throw new NumberFormatException("Ноль в знаменателе: " + str);
    }

    public Fraction(Integer numerator, Natural denominator) {
        if (!denominator.isNotZero()) throw new NumberFormatException("Ноль в знаменателе");

        this.numerator = numerator;
        this.denominator = denominator;
    }

    public Integer getNumerator() {
        return numerator;
    }

    public Natural getDenominator() {
        return denominator;
    }

    public Fraction negate() {
        return new Fraction(numerator.negate(), denominator);
    }


    // RED_Q_Q
    // Сокращение дроби.
    // Ганюшкин Тимофей, 9374
    public Fraction reduce() {
        // Делим числитель и знаменатель на их НОД. Возвращаем результат.
        Natural gcf = numerator.abs().gcf(denominator);
        return new Fraction(numerator.divide(gcf), denominator.divide(gcf));
    }


    // INT_Q_B
    // Проверка на целое.
    // Ганюшкин Тимофей, 9374
    public boolean isInteger() {
        // Возвращаем проверку, что знаментаель равен единице.
        return denominator.isOne();
    }


    // ADD_QQ_Q
    // Сложение дробей.
    // Ганюшкин Тимофей, 9374
    public Fraction add(Fraction b) {
        // Находим НОК знаменателей.
        Natural lcm = this.denominator.lcm(b.denominator);

        // Находим множители каждой дроби, чтобы привести их к общему знаменателю.
        Natural scaleA = lcm.divide(this.denominator);
        Natural scaleB = lcm.divide(b.denominator);

        // Подводим дроби под общий знаменатель и складываем числители. Возвращаем результат.
        return new Fraction(
                this.numerator.multiply(scaleA.toInteger()).add(b.numerator.multiply(scaleB.toInteger())),
                this.denominator.multiply(scaleA)
        ).reduce();
    }


    // SUB_QQ_Q
    // Вычитание дробей.
    // Ганюшкин Тимофей, 9374
    public Fraction subtract(Fraction b) {
        // Прибавляем дробь, сменив её знак на противоположный.
        return this.add(b.negate());
    }


    // MUL_QQ_Q
    // Умножение дробей.
    // Ганюшкин Тимофей, 9374
    public Fraction multiply(Fraction b) {
        // Перемножаем числители и знаменатели дробей.
        return new Fraction(this.numerator.multiply(b.numerator), this.denominator.multiply(b.denominator)).reduce();
    }


    // DIV_QQ_Q
    // Деление дробей.
    // Ганюшкин Тимофей, 9374
    public Fraction divide(Fraction b) {
        Positivity positivityB = b.numerator.getPositivity();
        if (positivityB == ZERO) throw new ArithmeticException("Деление на ноль");

        // Умножаем на дробь, обратную второй дроби. Возвращаем результат.
        return new Fraction(
                this.numerator.multiply((positivityB == NEGATIVE) ?
                        b.denominator.toInteger().negate() : b.denominator.toInteger()),
                this.denominator.multiply(b.numerator.abs())
        ).reduce();
    }


    // TRANS_Q_Z
    // Преобразование дробного в целое.
    // Ганюшкин Тимофей, 9374
    public Integer toInteger() {
        if (this.isInteger()) return numerator;
        throw new UnsupportedOperationException("Невозможно преобразовать дробное в целое; знаменатель не равен 1: " + this);
    }


    @Override
    public String toString() {
        if (this.isInteger())
            return numerator.toString();
        return numerator.toString() + '/' + denominator;
    }
}
