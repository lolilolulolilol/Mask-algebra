package net.akami.mask.operation;

import net.akami.mask.utils.ExpressionUtils;
import net.akami.mask.utils.FormatterFactory;
import net.akami.mask.utils.MathUtils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

public class Multiplication extends BinaryOperationHandler {

    private static final Multiplication INSTANCE = new Multiplication();

    @Override
    protected String operate(String a, String b) {

        LOGGER.error("Operating mult {} * {}", a, b);
        if(b.length() == 1 && ExpressionUtils.TRIGONOMETRY_SHORTCUTS.contains(b)) {
            LOGGER.info("Trigonometry calculation with {} and {}", a, b);
            String result = trigonometryOperation(a.isEmpty() ? "0" : a, b);
            LOGGER.info("Trigonometry result : {}", result);
            return result;
        }
        LOGGER.info("Multiplication process of {} |*| {}: \n", a, b);

        List<String> aMonomials = ExpressionUtils.toMonomials(a);
        List<String> bMonomials = ExpressionUtils.toMonomials(b);
        for(String part : aMonomials)
            for(String part2 : bMonomials)
                LOGGER.info("To perform : {} times {}", part, part2);
        LOGGER.info("Monomials of a : {}", aMonomials);
        LOGGER.info("Monomials of b : {}", bMonomials);
        // We can't use the constant BUILDER, because it is cleared repeatedly inside the loop
        StringBuilder builder = new StringBuilder();

        for (String part : aMonomials) {
            for (String part2 : bMonomials) {
                LOGGER.debug("Treating simple mult : {} |*| {}", part, part2);
                String result = simpleMult(part, part2);
                LOGGER.info("Result of simple mult between {} and {} : {}", part, part2, result);
                boolean first = part.equals(aMonomials.get(0)) && part2.equals(bMonomials.get(0));
                if (result.startsWith("+") || result.startsWith("-") || first) {
                    builder.append(result);
                } else {
                    builder.append("+" + result);
                }
            }
        }
        String unReducedResult = builder.toString();
        LOGGER.info("FINAL RESULT : {}", unReducedResult);
        String finalResult = Sum.getInstance().operate(unReducedResult, "");
        LOGGER.info("- Result of mult {} |*| {} : {}", a, b, finalResult);
        return finalResult;
    }

    /**
     * Calculates a*b. both strings must not be polynomials. If you don't know whether a and b are monomials,
     * call {@link MathUtils#mult(String, String)} instead.
     *
     * @param a the first value
     * @param b the second value
     * @return the result of the multiplication between a and b
     * @throws IllegalArgumentException if a and b are not monomials
     */
    public String simpleMult(String a, String b) {

        String concatenated = a + "*" + b;
        String originalVars = ExpressionUtils.toVariables(concatenated);
        LOGGER.debug("Variables of {} and {} : {}", a, b, originalVars);
        a = ExpressionUtils.toNumericValue(a);
        b = ExpressionUtils.toNumericValue(b);

        BigDecimal aValue = new BigDecimal(a);
        BigDecimal bValue = new BigDecimal(b);
        String floatResult = MathUtils.cutSignificantZero(aValue.multiply(bValue, MathContext.DECIMAL64).toString());
        if (MathUtils.roundPeriodicSeries(floatResult).equals("1")) {
            if (!originalVars.isEmpty()) {
                return originalVars;
            }
        }
        LOGGER.debug("Float result : {}", floatResult);
        return floatResult + originalVars;
    }

    private static String trigonometryOperation(String a, String b) {
        switch (b) {
            case "@":
                return MathUtils.sin(a);
            case "#":
                return MathUtils.cos(a);
            default:
                return MathUtils.tan(a);
        }
    }

    @Override
    public String inFormat(String origin) {
        LOGGER.info("Formatting : {}", origin);
        String result = FormatterFactory.removeFractions(origin);
        LOGGER.info("{} became {}", origin, result);
        return result;
    }

    @Override
    public String outFormat(String origin) {
        return ExpressionUtils.addMultShortcut(origin);
    }

    public static Multiplication getInstance() {
        return INSTANCE;
    }
}