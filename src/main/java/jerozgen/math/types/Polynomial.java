package jerozgen.math.types;

import jerozgen.math.types.enums.Positivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Polynomial {
    private final Fraction[] coefficients; // Нулевой индекс — коэффициент старшей степени многочлена.
    private final int degree;

    public static Polynomial ZERO_VALUE = new Polynomial(new Fraction[]{Fraction.ZERO_VALUE}, 0);
    public static Polynomial ONE_VALUE = new Polynomial(new Fraction[]{Fraction.ONE_VALUE}, 0);

    private static final String PATTERN = "([+-]?(?:(?:[\\d/]+x\\^-?\\d+)|(?:[\\d/]+x)|(?:[\\d/]+)|(?:x\\^-?\\d+)|(?:x)))";
    private static final String SINGLE_PATTERN = "([+-])?([\\d/]+)?(x)?(?:\\^(-?\\d+))?";

    public static Polynomial parse(String str) {
        str = str.replaceAll("\\s+", "").toLowerCase();

        if (!str.matches(PATTERN + "+"))
            throw new NumberFormatException("Неверный формат многочлена: " + str);

        Matcher matcher = Pattern.compile(PATTERN, Pattern.MULTILINE).matcher(str);

        Polynomial result = ZERO_VALUE;
        while (matcher.find()) {
            Matcher singleMatcher = Pattern.compile(SINGLE_PATTERN).matcher(matcher.group(0));
            if (singleMatcher.find()) {
                String sign = singleMatcher.group(1);
                String coefficientStr = singleMatcher.group(2);
                String x = singleMatcher.group(3);
                String degreeStr = singleMatcher.group(4);

                int degree = 0;
                if (degreeStr != null) degree = java.lang.Integer.parseInt(degreeStr);
                else if (x != null) degree = 1;

                Fraction coefficient;
                if (coefficientStr != null)
                    coefficient = new Fraction(coefficientStr).reduce();
                else coefficient = Fraction.ONE_VALUE;

                Polynomial polynomial = new Polynomial(new Fraction[]{coefficient}, degree);
                if (sign != null && sign.equals("-"))
                    result = result.subtract(polynomial);
                else result = result.add(polynomial);
            }
        }
        return result;
    }

    public Polynomial(Fraction[] coefficients, int degree) {
        this.coefficients = coefficients;
        this.degree = degree;
    }

    public Polynomial negate() {
        Fraction[] newCoefficients = new Fraction[coefficients.length];
        for (int i = 0; i < coefficients.length; i++)
            newCoefficients[i] = coefficients[i].negate();
        return new Polynomial(newCoefficients, degree);
    }

    private Polynomial removeLeadingAndEndingZeros() {
        int leadingZeros = 0;
        for (Fraction coefficient : coefficients) {
            if (coefficient.getNumerator().abs().isNotZero()) break;
            leadingZeros++;
        }
        if (leadingZeros == coefficients.length) return ZERO_VALUE;
        int endingZeros = 0;
        for (int i = coefficients.length - 1; i >= 0; i--) {
            if (coefficients[i].getNumerator().abs().isNotZero()) break;
            endingZeros++;
        }
        if (leadingZeros > 0 || endingZeros > 0) {
            int contractedLength = coefficients.length - leadingZeros - endingZeros;
            Fraction[] contractedCoefficients = new Fraction[contractedLength];
            System.arraycopy(coefficients, leadingZeros, contractedCoefficients, 0, contractedLength);
            return new Polynomial(contractedCoefficients, degree - leadingZeros);
        }
        return this;
    }


    // ADD_PP_P
    // Сложение многочленов.
    // Ганюшкин Тимофей, 9374
    public Polynomial add(Polynomial b){
        // Определяем максимальный и минимальный многочлен по их степени.
        Polynomial max, min;
        if (this.degree < b.degree)
            { max = b; min = this; }
        else
            { max = this; min = b; }

        // Определяем размер конечного многочлена.
        int degreeRange = max.degree - min.degree;
        int size = Math.max(degreeRange + min.coefficients.length, max.coefficients.length);
        Fraction[] newCoefficients = new Fraction[size];

        // Складываем многочлены почленно.
        for (int i = 0; i < size; i++) {
            if (i < degreeRange) {
                if (i < max.coefficients.length)
                    newCoefficients[i] = max.coefficients[i];
                else
                    newCoefficients[i] = Fraction.ZERO_VALUE;
            }
            else {
                if (i < max.coefficients.length) {
                    if (i - degreeRange < min.coefficients.length)
                        newCoefficients[i] = max.coefficients[i].add(min.coefficients[i - degreeRange]);
                    else
                        newCoefficients[i] = max.coefficients[i];
                }
                else
                    newCoefficients[i] = min.coefficients[i - degreeRange];
            }
        }

        // Возвращаем результат, убрав лишние нули в начале и конце.
        return new Polynomial(newCoefficients, max.degree).removeLeadingAndEndingZeros();
    }


    // SUB_PP_P
    // Вычитание многочленов.
    // Ганюшкин Тимофей, 9374
    public Polynomial subtract(Polynomial b){
        // Прибавляем многочлен, сменив его знак на противоположный.
        return this.add(b.negate());
    }


    // MUL_PQ_P
    // Умножение многочлена на дробь.
    // Ганюшкин Тимофей, 9374
    public Polynomial multiplyByFraction(Fraction b){
        Fraction[] newCoefficients = new Fraction[coefficients.length];
        // Почленно умножаем многочлен на дробь.
        for (int i = 0; i < newCoefficients.length; i++)
            newCoefficients[i] = coefficients[i].multiply(b);

        // Возвращаем результат, убрав лишние нули в начале и конце.
        return new Polynomial(newCoefficients, degree).removeLeadingAndEndingZeros();
    }


    // MUL_Pxk_P
    // Умножение многочлена на x^k.
    // Ганюшкин Тимофей, 9374
    public Polynomial multiplyByX(int degree){
        // Возвращаем многочлен с увеличенной на degree степенью.
        return new Polynomial(coefficients, this.degree + degree);
    }


    // LED_P_Q
    // Старший коэффициент многочлена.
    // Ганюшкин Тимофей, 9374
    public Fraction getLeadingCoefficient(){
        return coefficients[0];
    }


    // DEG_P_N
    // Степень многочлена.
    // Ганюшкин Тимофей, 9374
    public int getDegree(){
        return degree;
    }

    // FAC_P_Q
    // Вынесение из многочлена НОК знаменателей коэффициентов и НОД числителей.
    // Ганюшкин Тимофей, 9374
    public Fraction getGeneralCoefficient(){
        Natural gcfNumerator = coefficients[0].getNumerator().abs();
        Natural lcmDenominator = coefficients[0].getDenominator();

        for (int i = 1; i < coefficients.length; i++) {
            gcfNumerator = gcfNumerator.gcf(coefficients[i].getNumerator().abs());
            lcmDenominator = lcmDenominator.lcm(coefficients[i].getDenominator());
        }

        return new Fraction(gcfNumerator.toInteger(), lcmDenominator);
    }


    // MUL_PP_P
    // Умножение многочленов.
    // Ганюшкин Тимофей, 9374
    public Polynomial multiply(Polynomial b){
        // Почленно перемножаем коэффициенты друг с другом, добавлаем результат в новый многочлен на место
        // со степенью суммы степеней переменоженных членов.
        Fraction[] newCoefficients = new Fraction[this.coefficients.length + b.coefficients.length - 1];
        for (int i = 0; i < this.coefficients.length; i++)
            for (int j = 0; j < b.coefficients.length; j++) {
                int sum = i + j;
                if (newCoefficients[sum] != null)
                    newCoefficients[sum] = newCoefficients[sum].add(this.coefficients[i].multiply(b.coefficients[j]));
                else
                    newCoefficients[sum] = this.coefficients[i].multiply(b.coefficients[j]);
            }
        return new Polynomial(newCoefficients, this.degree + b.degree);
    }


    // DIV_PP_P
    // Частное от деления многочленов с остатком.
    // Ганюшкин Тимофей, 9374
    public Polynomial divide(Polynomial b){
        if (b.coefficients.length == 1 && !b.coefficients[0].getNumerator().abs().isNotZero())
            throw new ArithmeticException("Деление на ноль");

        // Если степень первого меньше степени второго, возвращаем ноль.
        if (this.degree < b.degree) return ZERO_VALUE;

        // Получаем начальные частное и остаток.
        Fraction[] qCoefficients = new Fraction[this.coefficients.length - b.coefficients.length + 1];
        Polynomial remainder = this;

        // Выполняем деление многочленов столбиком.
        for (int i = 0; i < this.coefficients.length - b.coefficients.length + 1; i++) {
            qCoefficients[i] = remainder.getLeadingCoefficient().divide(b.getLeadingCoefficient());
            remainder = remainder.subtract(b.multiplyByFraction(qCoefficients[i]).multiplyByX(remainder.degree - b.degree));
        }

        return new Polynomial(qCoefficients, this.degree - b.degree);
    }


    // MOD_PP_P
    // Остаток от деления многочленов с остатком.
    // Ганюшкин Тимофей, 9374
    public Polynomial remainder(Polynomial b){
        // Находим r из выражения: a = b * q + r.
        return this.subtract(b.multiply(this.divide(b)));
    }


    // GCF_PP_P
    // НОД многочленов.
    // Ганюшкин Тимофей, 9374
    public Polynomial gcf(Polynomial b){
        // Вычисляем рекурсивным алгоритмом Евклида.
        if (b.coefficients.length != 1 || b.coefficients[0].getNumerator().getPositivity() != Positivity.ZERO)
            return b.gcf(this.remainder(b));
        return this;
    }


    // DER_P_P
    // Производная многочлена.
    // Ганюшкин Тимофей, 9374
    public Polynomial differentiate(){
        Fraction[] newCoefficients = new Fraction[coefficients.length];
        // Находим производную для каждого члена.
        for (int i = 0; i < coefficients.length; i++) {
            newCoefficients[i] = coefficients[i].multiply(new Integer(String.valueOf(degree - i)).toFraction());
        }
        return new Polynomial(newCoefficients, degree - 1).removeLeadingAndEndingZeros();
    }


    // NMR_P_P
    // Преобразование многочлена — кратные корни в простые.
    // Ганюшкин Тимофей, 9374
    public Polynomial squareFreeFactorization(){
        return this.divide(this.gcf(this.differentiate()))
                .multiplyByFraction(Fraction.ONE_VALUE.divide(this.getGeneralCoefficient()));
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < coefficients.length; i++) {
            Positivity numeratorPositivity = coefficients[i].getNumerator().getPositivity();
            if (i != 0 && numeratorPositivity == Positivity.ZERO) continue;

            int currentDegree = degree - i;

            if (coefficients[i].isInteger() && coefficients[i].getNumerator().abs().isOne()
                    && currentDegree != 0) {
                if (numeratorPositivity == Positivity.NEGATIVE) builder.append("-");
                else if (i != 0) builder.append("+");
            } else {
                if (i != 0 && numeratorPositivity != Positivity.NEGATIVE)
                    builder.append("+");
                builder.append(coefficients[i]);
            }

            if (currentDegree != 0) {
                builder.append("x");
                if (currentDegree != 1)
                    builder.append("^").append(currentDegree);
            }
        }

        return builder.toString();
    }

}
