package com.cosmo.common.util;

import java.util.Objects;

public class MathUtil {

    public static Double zeroIfNegative(Double number) {
        if(number > 0) {
            return number;
        }
        return 0.0D;
    }

    public static Integer zeroIfNegative(Integer number) {
        if(number > 0) {
            return number;
        }
        return 0;
    }

    public static Double zerIfNull(Double value) {
        return Objects.isNull(value) ? 0.0D : value;
    }
}
