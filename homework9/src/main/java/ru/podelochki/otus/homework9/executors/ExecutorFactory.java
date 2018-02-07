package ru.podelochki.otus.homework9.executors;

import java.lang.annotation.Annotation;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import ru.podelochki.otus.homework9.annotations.DataComponents;
import ru.podelochki.otus.homework9.exceptions.IncorrectExecutorAnnotationException;

public class ExecutorFactory {
	
	private ExecutorFactory() {
		try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
		
	}
	public static DBExecutor getExecutor(String url) {
		//String dtoPath = null;
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(url);
		} catch (Exception e) {
            e.printStackTrace();
        }
		/*
		for(Annotation classAnnotation: DBExecutor.class.getDeclaredAnnotations()) {
			if(classAnnotation.annotationType().isAssignableFrom(DataComponents.class)) {
				dtoPath = ((DataComponents)classAnnotation).name();
			}
		}
		if (dtoPath == null) {
			throw  new IncorrectExecutorAnnotationException();
		}
		Set<Class<? extends Object>> allClasses;
		List<ClassLoader> classLoadersList = new LinkedList<ClassLoader>();
    	classLoadersList.add(ClasspathHelper.contextClassLoader());
    	classLoadersList.add(ClasspathHelper.staticClassLoader());
		Reflections reflections = new Reflections(new ConfigurationBuilder()
	    	    .setScanners(new SubTypesScanner(false ), new ResourcesScanner())
	    	    .setUrls(ClasspathHelper.forClassLoader(classLoadersList.toArray(new ClassLoader[0])))
	    	    .filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix(dtoPath))));
		allClasses = reflections.getSubTypesOf(Object.class);
		
		*/
		
		return new DBExecutor(connection);
	}
	

}
