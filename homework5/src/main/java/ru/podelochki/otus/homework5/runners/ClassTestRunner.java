package ru.podelochki.otus.homework5.runners;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import ru.podelochki.otus.homework5.annotations.After;
import ru.podelochki.otus.homework5.annotations.Before;
import ru.podelochki.otus.homework5.annotations.Test;
import ru.podelochki.otus.homework5.exceptions.IncorrectAnnotationException;
import ru.podelochki.otus.homework5.exceptions.MultipleAnnotationsException;

public class ClassTestRunner {
	private Class testClass;
	private Method mBefore;
	private Method mAfter;
	private List<Method> mTestList = new ArrayList<>();
	
	private static final String packageName = "ru.podelochki.otus.homework5.annotations";
	
	public ClassTestRunner(Class testClass) {
		this.testClass = testClass;
	}
	
	public void run() {
		prepareTests();
		try {
			runTests();
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new IncorrectAnnotationException();
        }
	}
	private void prepareTests() {
		Method[] methods = testClass.getDeclaredMethods();
		for (Method method: methods) {
			checkAnnotations(method);
		}
	}
	private void runTests() throws InstantiationException, IllegalAccessException, InvocationTargetException{
		System.out.println(testClass.getName() + " started");
		if (mTestList.size() < 1) {
			throw new IncorrectAnnotationException("No @Test annotations have been found");
		}
        for (Method method : mTestList){
            Object testClassObject = testClass.newInstance();
            if (mBefore != null) mBefore.invoke(testClassObject);
            method.invoke(testClassObject);
            if (mAfter != null) mAfter.invoke(testClassObject);
        }
	}
	
	public void checkAnnotations(Method method) {
		int testAnnotationCounter = 0;
		for (Annotation annotation: method.getAnnotations()) {
			if (annotation.annotationType().getCanonicalName().contains(packageName)) testAnnotationCounter++;
			if (testAnnotationCounter > 1) {
				throw new MultipleAnnotationsException("Multiple annotations at method: " + method.getName());
			}
			Class annotationClass = annotation.annotationType();
			if (annotationClass == Before.class) {
				addBeforeMethod(method);
			} else if(annotationClass == After.class) {
				addAfterMethod(method);
			} else if(annotationClass == Test.class) {
				addTestMethod(method);
			}
		}
	}
	
	private void addBeforeMethod(Method method) {
		mBefore = method;
	}
	private void addAfterMethod(Method method) {
		mAfter = method;
	}
	private void addTestMethod(Method method) {
		mTestList.add(method);
	}
}
