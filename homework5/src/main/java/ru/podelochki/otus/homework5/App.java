package ru.podelochki.otus.homework5;

import ru.podelochki.otus.homework5.runners.ClassTestRunner;
import ru.podelochki.otus.homework5.runners.PackageTestRunner;
import ru.podelochki.otus.homework5.tests.CorrectTestClass;
import ru.podelochki.otus.homework5.tests.IncorrectInitialization;
import ru.podelochki.otus.homework5.tests.MultipleAnnotations;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) {
        System.out.println("ClassTestRunner:");
        ClassTestRunner classTestRunner = new ClassTestRunner(CorrectTestClass.class);
        try {
            classTestRunner.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        classTestRunner = new ClassTestRunner(IncorrectInitialization.class);
        try {
            classTestRunner.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        classTestRunner = new ClassTestRunner(MultipleAnnotations.class);
        try {
            classTestRunner.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("PackageTestRunner:");
        PackageTestRunner packageTestRunner = new PackageTestRunner("ru.podelochki.otus.homework5.tests");
        packageTestRunner.run();

    }
}
