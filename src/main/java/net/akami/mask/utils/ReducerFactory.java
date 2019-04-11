package net.akami.mask.utils;

import net.akami.mask.affection.MaskContext;
import net.akami.mask.handler.sign.BinaryOperationSign;
import net.akami.mask.tree.BinaryTree;
import net.akami.mask.tree.Branch;
import net.akami.mask.tree.ReducerTree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReducerFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReducerFactory.class);
    public static final BinaryOperationSign[] PROCEDURAL_OPERATIONS;

    static {
        PROCEDURAL_OPERATIONS = new BinaryOperationSign[]{
                BinaryOperationSign.SUM, BinaryOperationSign.SUBTRACT,
                BinaryOperationSign.MULT, BinaryOperationSign.DIVIDE,
                BinaryOperationSign.POW, BinaryOperationSign.NONE
        };
    }

    public static String reduce(String exp) {
        return reduce(exp, MaskContext.DEFAULT);
    }

    public static String reduce(String exp, MaskContext context) {
        long time = System.nanoTime();

        // deletes all the spaces, adds the necessary '*' and formats trigonometry
        String localExp = FormatterFactory.formatForCalculations(exp);
        BinaryTree<Branch> tree = new ReducerTree(localExp);
        LOGGER.info("Initial branch added : {}", tree.getBranches().get(0));

        TreeUtils.printBranches(tree);
        LOGGER.debug("Now merging branches");
        String result;
        try {
            result = tree.merge();
        } catch (ArithmeticException | NumberFormatException e) {
            e.printStackTrace();
            if(e instanceof ArithmeticException) {
                throw new IllegalArgumentException("Non solvable mathematical expression given : "+ exp);
            } else {
                throw new IllegalArgumentException("Wrong inFormat in the expression "+ exp);
            }
        }

        float deltaTime = (System.nanoTime() - time) / 1000000000f;
        LOGGER.info("Expression successfully reduced in {} seconds.", deltaTime);
        return FormatterFactory.formatForVisual(result);
    }
}
