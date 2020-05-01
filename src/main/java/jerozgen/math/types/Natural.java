package jerozgen.math.types;

import jerozgen.math.types.enums.CompareResult;

import static jerozgen.math.types.enums.CompareResult.*;

public class Natural {
    public static final Natural ZERO_VALUE = new Natural(new int[]{0});
    public static final Natural ONE_VALUE = new Natural(new int[]{1});

    private int[] digits; // Массив цифр. По меньшему индексу располагаются цифры меньших разрядов.

    public Natural(String str) {
        if (str.length() == 0) throw new NumberFormatException("Нулевая длина числа");

        digits = new int[str.length()];
        char[] chars = new char[str.length()];
        str.getChars(0, str.length(), chars, 0);

        boolean zeroRemove = true;
        for (int i = 0; i < chars.length; i++) {
            if (zeroRemove) {
                if (chars[i] == '0' && i != chars.length - 1) continue;
                digits = new int[chars.length - i];
                zeroRemove = false;
            }
            try {
                digits[chars.length - i - 1] = java.lang.Integer.parseInt(String.valueOf(chars[i]));
            } catch (NumberFormatException ex) {
                throw new NumberFormatException("Неверный формат натурального: " + str);
            }
        }
    }

    public Natural(int[] digits) {
        this.digits = digits.clone();
    }

    public int[] getDigits() {
        return digits;
    }

    public boolean isOne(){
        return digits.length == 1 && digits[0] == 1;
    }


    // COM_NN_D
    // Сравнение чисел.
    // Ганюшкин Тимофей, 9374
    public CompareResult compareTo(Natural b) {
        // Сравниваем длины чисел.
        if (this.digits.length > b.digits.length) return MORE;
        if (this.digits.length < b.digits.length) return LESS;

        // Если длины равны, поочерёдно сравниваем цифры.
        for (int i = digits.length - 1; i >= 0; i--) {
            if (this.digits[i] > b.digits[i]) return MORE;
            if (this.digits[i] < b.digits[i]) return LESS;
        }
        return EQUAL;
    }


    // NZER_N_B
    // Проверка на ноль.
    // Ганюшкин Тимофей, 9374
    public boolean isNotZero(){
        return !(digits.length == 1 && digits[0] == 0);
    }


    // ADD_1N_N
    // Добавление единицы к числу.
    // Ганюшкин Тимофей, 9374
    public Natural addOne(){
        int[] result = new int[digits.length];

        for (int i = 0; i < digits.length; i++) {
            // Если цифра 9, записываем в результат 0.
            if (digits[i] == 9) result[i] = 0;
            // Иначе прибавляем 1, копируем оставшуюся часть в результат и возвращаем его.
            else {
                result[i] = digits[i] + 1;
                System.arraycopy(digits, i + 1, result, i + 1, digits.length - i - 1);
                return new Natural(result);
            }
        }

        // Если всё число состояло из «9», возвращаем увеличенный
        // результат из единицы и следующих за ней нулей.
        result = new int[digits.length + 1];
        result[0] = 1;
        return new Natural(result);
    }


    // ADD_NN_N
    // Сложение чисел.
    // Ганюшкин Тимофей, 9374
    public Natural add(Natural b) {
        // Определяем максимальное и минимальное числа.
        int[] max;
        int[] min;
        if (this.compareTo(b) == LESS) {
            max = b.digits;
            min = this.digits;
        }
        else {
            max = this.digits;
            min = b.digits;
        }

        int[] result;
        result = new int[max.length];

        // Поочерёдно складываем каждую цифру. Если результат больше 9,
        // добавляем дополнительную единицу в следующей итерации.
        int r = 0;
        for (int i = 0; i < max.length; i++) {
            if (i < min.length)
                r += max[i] + min[i];
            else
                r += max[i];
            if (r >= 10) {
                result[i] = r - 10;
                r = 1;
            }
            else {
                result[i] = r;
                r = 0;
            }
        }

        // Если добавлять единицу больше не надо, возвращаем результат...
        if(r == 0) return new Natural(result);

        // ...иначе создаём расширенную копию результата и возвращаем её.
        int[] expandedResult = new int[max.length + 1];
        System.arraycopy(result, 0, expandedResult, 0, max.length);
        expandedResult[max.length] = r;
        return new Natural(expandedResult);
    }


    // SUB_NN_N
    // Абсолютная разность чисел.
    // Ганюшкин Тимофей, 9374
    public Natural subtract(Natural b) {
        // Определяем максимальное и минимальное числа. Если они равны возвращаем 0.
        int[] max;
        int[] min;
        switch (this.compareTo(b)){
            case EQUAL:
                return new Natural(new int[]{0});
            case LESS:
                max = b.digits;
                min = this.digits;
                break;
            case MORE:
            default:
                max = this.digits;
                min = b.digits;
        }

        int[] result;
        result = new int[max.length];

        // Поочерёдно вычитаем каждую цифру. Если результат меньше 0,
        // вычитаем дополнительную единицу в следующей итерации.
        int r = 0;
        for (int i = 0; i < max.length; i++) {
            if (i < min.length)
                r += max[i] - min[i];
            else
                r += max[i];
            if (r < 0) {
                result[i] = r + 10;
                r = -1;
            }
            else {
                result[i] = r;
                r = 0;
            }
        }

        // Убираем лидирующие нули.
        int zeroCount = 0;
        for (int i = result.length - 1; i >= 0; i--) {
            if (result[i] == 0) {
                zeroCount++;
            }
            else break;
        }
        if (zeroCount == 0) return new Natural(result);

        int[] contractedResult = new int[max.length - zeroCount];
        System.arraycopy(result, 0, contractedResult, 0,max.length - zeroCount);
        return new Natural(contractedResult);
    }


    // MUL_ND_N
    // Умножение числа на цифру.
    // Ганюшкин Тимофей, 9374
    public Natural multiplyByDigit(int digit) {
        if (digit < 0 || digit > 9)
            throw new IllegalArgumentException("Ожидалась цифра: " + digit);

        int[] result = new int[digits.length];

        // Поочерёдно умножаем каждую цифру. Если результат больше 9,
        // добавляем цифру разряда десяток в следующей итерации.
        int r = 0;
        for (int i = 0; i < digits.length; i++) {
            r += digits[i] * digit;
            if (r >= 10) {
                result[i] = r % 10;
                r /= 10;
            }
            else {
                result[i] = r;
                r = 0;
            }
        }

        // Если добавлять r больше не надо, возвращаем результат...
        if(r == 0) return new Natural(result);

        // ...иначе создаём расширенную копию результата и возвращаем её.
        int[] expandedResult = new int[digits.length + 1];
        System.arraycopy(result, 0, expandedResult, 0, digits.length);
        expandedResult[digits.length] = r;
        return new Natural(expandedResult);
    }


    // MUL_Nk_N
    // Умножение числа на 10^k.
    // Ганюшкин Тимофей, 9374
    public Natural appendZeros(int k) {
        if (k < 0) throw new IllegalArgumentException("Ожидалось неотрицательное число: " + k);
        if (!this.isNotZero()) return this;

        // Создаём массив на k элементов больше исходного и копируем туда цифры.
        int[] result = new int[digits.length + k];
        System.arraycopy(digits, 0, result, k, digits.length);
        return new Natural(result);
    }


    // MUL_NN_N
    // Умножение чисел.
    // Ганюшкин Тимофей, 9374
    public Natural multiply(Natural b) {
        if (!b.isNotZero()) return b;

        // Умножаем первое на каждую цифру второго и, приписав нули, складываем результаты.
        Natural result = this.multiplyByDigit(b.digits[0]);
        for (int i = 1; i < b.digits.length; i++)
            result = result.add(this.multiplyByDigit(b.digits[i]).appendZeros(i));
        return result;
    }


    // SUB_NDN_N
    // Вычитание из числа другого, умноженного на цифру.
    // Ганюшкин Тимофей, 9374
    public Natural subtractScaled(Natural b, int digit) {
        if (digit < 0 || digit > 9)
            throw new IllegalArgumentException("Ожидалась цифра: " + digit);
        return this.subtract(b.multiplyByDigit(digit));
    }


    // DIV_NN_Dk
    // Вычисление первой цифры деления чисел, помноженной на 10^k,
    // где k — номер позиции этой цифры (номер считается с нуля).
    // Ганюшкин Тимофей, 9374
    public Natural getScaledFirstQuotientDigit(Natural b) {
        if (!b.isNotZero()) throw new ArithmeticException("Деление на ноль");
        if (this.compareTo(b) == LESS) throw new ArithmeticException("Деление на большее: " + this + " < " + b);

        // Копируем начальные цифры первого числа в количестве цифр второго числа.
        int[] cloneDigits = new int[b.digits.length];
        System.arraycopy(digits, digits.length - b.digits.length, cloneDigits, 0, b.digits.length);
        Natural cloneNatural = new Natural(cloneDigits);

        // Сравниваем полученную копию со вторым числом...
        CompareResult compareCloneBz = cloneNatural.compareTo(b);
        // ...Если равны, возвращаем единицу с приписанными нулями.
        if (compareCloneBz == EQUAL)
            return new Natural(new int[]{1}).appendZeros(digits.length - cloneDigits.length);;
        // ...Если меньше, делаем новую копию с увеличенной длиной числа.
        if (compareCloneBz == LESS) {
            cloneDigits = new int[b.digits.length + 1];
            System.arraycopy(digits, digits.length - b.digits.length - 1, cloneDigits, 0, b.digits.length + 1);
            cloneNatural = new Natural(cloneDigits);
        }

        // Пока можно вычесть второе число из копии, добавляем единицу в результат.
        int[] result = {0};
        do {
            cloneNatural = cloneNatural.subtract(b);
            result[0] += 1;
        } while (cloneNatural.compareTo(b) != LESS);

        // Возвращаем результат с приписанными нулями.
        return new Natural(result).appendZeros(digits.length - cloneDigits.length);
    }


    // DIV_NN_N
    // Частное от деления чисел с остатком.
    // Ганюшкин Тимофей, 9374
    public Natural divide(Natural b) {
        if (!b.isNotZero()) throw new ArithmeticException("Деление на ноль");

        // Получаем начальные частное и остаток.
        Natural quotient = this.getScaledFirstQuotientDigit(b);
        Natural remainder = this.subtract(quotient.multiply(b));

        // Пока остаток больше или равен второму числу, получаем первую цифру деления остатка на второе число
        // с приписанными нулями, результат добавляем к частному и вычитаем из остатка.
        while (remainder.compareTo(b) != LESS) {
            Natural scaledFirstQuotientDigit = remainder.getScaledFirstQuotientDigit(b);
            quotient = quotient.add(scaledFirstQuotientDigit);
            remainder = remainder.subtract(scaledFirstQuotientDigit.multiply(b));
        }

        return quotient;
    }


    // MOD_NN_N
    // Остаток от деления чисел с остатком.
    // Ганюшкин Тимофей, 9374
    public Natural remainder(Natural b) {
        // Находим r из выражения: a = b * q + r.
        return this.subtract(b.multiply(this.divide(b)));
    }


    // GCF_NN_N
    // НОД чисел.
    // Ганюшкин Тимофей, 9374
    public Natural gcf(Natural b) {
        // Вычисляем рекурсивным алгоритмом Евклида.
        if (b.isNotZero()) return b.gcf(this.remainder(b));
        return this;
    }


    // LCM_NN_N
    // НОК чисел.
    // Ганюшкин Тимофей, 9374
    public Natural lcm(Natural b) {
        // Вычисляем по формуле: a × b / НОД(a, b).
        return this.multiply(b).divide(this.gcf(b));
    }


    // TRANS_N_Z
    // Преобразование натурального в целое.
    // Ганюшкин Тимофей, 9374
    public Integer toInteger() {
        return new Integer(this.digits, false);
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int digit : digits) builder.insert(0, digit);
        return builder.toString();
    }
}
