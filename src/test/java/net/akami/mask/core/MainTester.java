package net.akami.mask.core;

import net.akami.mask.handler.PowCalculator;
import net.akami.mask.logging.LoggerManager;
import net.akami.mask.operation.MaskExpression;
import net.akami.mask.tree.BinaryTree;
import net.akami.mask.utils.MathUtils;
import net.akami.mask.utils.ReducerFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;

import java.util.Collection;
import java.util.Scanner;

public class MainTester {

    public static void main(String... args) {
        Scanner sc = new Scanner(System.in);
        String expression;
        LoggerManager.disableAll();
        System.out.println("Next expression to reduce : ");
        while(!(expression = sc.nextLine()).isEmpty()) {
            float time = System.nanoTime();
            System.out.println("Result : "+ ReducerFactory.reduce(expression));
            float deltaTime = (System.nanoTime() - time) / 1000000000f;
            System.out.println("Calculations ended after "+deltaTime+" seconds");
            System.out.println("Next expression to reduce : ");
        }
    }

    public static void test() {

    }
}
