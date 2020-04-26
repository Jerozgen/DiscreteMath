package jerozgen.math.types;

import jerozgen.math.types.enums.Positivity;

import static jerozgen.math.types.enums.CompareResult.LESS;
import static jerozgen.math.types.enums.Positivity.*;

public class Integer {

    public static Integer ZERO_VALUE = new Integer(new int[]{0}, false);
    public static Integer ONE_VALUE = new Integer(new int[]{1}, false);

    private boolean isNegative;
    private int[] digits;

    public Integer(String str) {
        char[] chars;
        if (str.startsWith("-")) {
            if (str.length() == 1) throw new NumberFormatException("Нулевая длина числа");
            isNegative = true;
            chars = new char[str.length() - 1];
            str.getChars(1, str.length(), chars, 0);
        } else {
            if (str.length() == 0) throw new NumberFormatException("Нулевая длина числа");
            isNegative = false;
            chars = new char[str.length()];
            str.getChars(0, str.length(), chars, 0);
        }
        boolean zeroRemove = true;
        for (int i = 0; i < chars.length; i++) {
            if (zeroRemove) {
                if (chars[i] == '0') {
                    if (i != chars.length - 1) continue;
                    else isNegative = false;
                }
                digits = new int[chars.length - i];
                zeroRemove = false;
            }
            try {
                digits[chars.length - i - 1] = java.lang.Integer.parseInt(String.valueOf(chars[i]));
            } catch (NumberFormatException ex) {
                throw new NumberFormatException("Неверный формат целого: " + str);
            }
        }
    }

    public Integer(int[] digits, boolean isNegative) {
        this.digits = digits.clone();
        this.isNegative = isNegative;
    }

    public int[] getDigits() {
        return digits;
    }


    // ABS_Z_N
    // Абсолютная величина числа.
    // Ганюшкин Тимофей, 9374
    public Natural abs() {
        return new Natural(this.digits);
    }


    // POZ_Z_D
    // Определение положительности числа.
    // Ганюшкин Тимофей, 9374
    public Positivity getPositivity() {
        if (digits.length == 1 && digits[0] == 0) return ZERO;
        if (isNegative) return NEGATIVE;
        return POSITIVE;
    }


    // MUL_ZM_Z
    // Умножение числа на -1.
    // Ганюшкин Тимофей, 9374
    public Integer negate() {
        if (this.getPositivity() == ZERO) return this;
        return new Integer(this.digits, !isNegative);
    }

    // ADD_ZZ_Z
    // Сложение чисел.
    // Ганюшкин Тимофей, 9374
    public Integer add(Integer b) {
        Positivity positivityA = this.getPositivity();
        Positivity positivityB = b.getPositivity();

        // Если оба числа положительны, результат положителен.
        if (positivityA == POSITIVE && positivityB == POSITIVE)
            return this.abs().add(b.abs()).toInteger();
        // Если оба числа отрицательны, результат отрицателен.
        if (positivityA == NEGATIVE && positivityB == NEGATIVE)
            return this.abs().add(b.abs()).toInteger().negate();

        // Если одно из чисел равно нулю, результат равен другому числу.
        if (positivityB == ZERO) return this;
        if (positivityA == ZERO) return b;

        // Если числа разных знаков, то вычитаем одно из другого.
        if (positivityA == POSITIVE && positivityB == NEGATIVE) {
            Natural absA = this.abs();
            Natural absB = b.abs();
            Integer result = absA.subtract(absB).toInteger();
            if (absA.compareTo(absB) == LESS) return result.negate();
            return result;
        }
        // Если предыдущие условия не выполнены, складываем числа, поменяв их местами.
        return b.add(this);
    }


    // SUB_ZZ_Z
    // Вычитание чисел.
    // Ганюшкин Тимофей, 9374
    public Integer subtract(Integer b) {
        // Прибавляем число, сменив его знак на противоположный.
        return this.add(b.negate());
    }


    // MUL_ZZ_Z
    // Умножение чисел.
    // Ганюшкин Тимофей, 9374
    public Integer multiply(Integer b) {
        // Если оба числа одного знака, результат положителен.
        if (this.getPositivity() == b.getPositivity())
            return this.abs().multiply(b.abs()).toInteger();
        // Если оба числа разных знаков, результат отрицателен.
        return this.abs().multiply(b.abs()).toInteger().negate();
    }


    // DIV_ZZ_Z
    // Частное от деления целого на натуральное с остатком.
    // Ганюшкин Тимофей, 9374
    public Integer divide(Natural b) {
        Natural absA = this.abs();
        // Если оба числа одного знака, результат положителен.
        if (this.getPositivity() == POSITIVE)
            return absA.divide(b).toInteger();
        // Если оба числа разных знаков, результат отрицателен и меньше на единицу (если остаток не равен нулю).
        if (absA.remainder(b).isNotZero())
            return absA.divide(b).addOne().toInteger().negate();
        return absA.divide(b).toInteger().negate();
    }


    // MOD_ZZ_Z
    // Остаток от деления целого на натуральное с остатком.
    // Ганюшкин Тимофей, 9374
    public Integer remainder(Natural b) {
        // Находим r из выражения: a = b * q + r.
        return this.subtract(b.toInteger().multiply(this.divide(b)));
    }


    // TRANS_Z_N
    // Преобразование целого неотрицательного в натуральное.
    // Ганюшкин Тимофей, 9374
    public Natural toNatural() {
        if (this.getPositivity() == NEGATIVE)
            throw new UnsupportedOperationException("Невозможно преобразовать отрицательное к натуральному: " + this);
        return new Natural(this.digits);
    }


    // TRANS_Z_Q
    // Преобразование целого в дробное.
    // Ганюшкин Тимофей, 9374
    public Fraction toFraction() {
        return new Fraction(this, Natural.ONE_VALUE);
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int digit : digits) builder.insert(0, digit);
        if (isNegative) builder.insert(0, '-');
        return builder.toString();
    }
}
