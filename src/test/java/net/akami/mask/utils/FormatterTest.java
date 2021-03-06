package net.akami.mask.utils;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class FormatterTest {

    @Test
    public void trigonometryTest() {
        String s1 = "5sin(25)+3sin(20)+4x";
        String result = FormatterFactory.formatForCalculations(s1);
        Assertions.assertThat(result).isEqualTo("5*((25)@)+3*((20)@)+4*x");
        Assertions.assertThat(FormatterFactory.formatForVisual(result)).isEqualTo("5sin(25)+3sin(20)+4x");
    }

    @Test
    public void coefficientTest() {
        Assertions.assertThat(FormatterFactory.addAllCoefficients("x")).isEqualTo("1x");
        Assertions.assertThat(FormatterFactory.addAllCoefficients("xy")).isEqualTo("1xy");
    }
}
