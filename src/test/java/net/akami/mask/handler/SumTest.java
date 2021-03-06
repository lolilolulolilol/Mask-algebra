package net.akami.mask.handler;

import net.akami.mask.handler.Adder;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class SumTest {

    @Test
    public void sum() {
        Adder sum = new Adder();
        Assertions.assertThat(sum.rawOperate("2", "5")).isEqualTo("7");
        Assertions.assertThat(sum.rawOperate("2x", "5")).isEqualTo("2x+5");
        Assertions.assertThat(sum.rawOperate("3x", "5x")).isEqualTo("8x");
        Assertions.assertThat(sum.rawOperate("x/2", "x/2")).isEqualTo("1.0x");
        Assertions.assertThat(sum.rawOperate("1/2", "1/2")).isEqualTo("1");
        Assertions.assertThat(sum.rawOperate("x+1", "-1")).isEqualTo("x");
    }

    @Test
    public void inFormatTest() {
        Assertions.assertThat(Adder.getInstance().inFormat("5x")).isEqualTo("5x");
        Assertions.assertThat(Adder.getInstance().inFormat("5x/5")).isEqualTo("1x");
        Assertions.assertThat(Adder.getInstance().inFormat("3x/4")).isEqualTo("0.75x");
    }
}
