package ru.podelochki.otus.homework5.runners;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

public class PackageTestRunner {
	private String packageName;
    private Set<Class<? extends Object>> allClasses;

    public PackageTestRunner(String packageName) {
        this.packageName = packageName;
    }

    public void run() {
        fillTestClassesList();
        runTests();
    }

    private void fillTestClassesList() {
    	List<ClassLoader> classLoadersList = new LinkedList<ClassLoader>();
    	classLoadersList.add(ClasspathHelper.contextClassLoader());
    	classLoadersList.add(ClasspathHelper.staticClassLoader());

    	Reflections reflections = new Reflections(new ConfigurationBuilder()
    	    .setScanners(new SubTypesScanner(false /* don't exclude Object.class */), new ResourcesScanner())
    	    .setUrls(ClasspathHelper.forClassLoader(classLoadersList.toArray(new ClassLoader[0])))
    	    .filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix(packageName))));

    	 allClasses = reflections.getSubTypesOf(Object.class);

    }
    private void runTests() {
        for (Class testClass : allClasses) {
            ClassTestRunner runner = new ClassTestRunner(testClass);
            try {
                runner.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
