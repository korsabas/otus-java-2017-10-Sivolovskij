package ru.podelochki.otus.homework9.executors;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import ru.podelochki.otus.homework9.models.DataSet;

public class EntityParser {
	
	private String table;
	private boolean isEntity;
	private String sql;
	private Map<String, String> fieldsMap;
	public <T extends DataSet> String getSelectSQL(Class<T> clazz) {
		fieldsMap = new HashMap<>();
		sql = "select id, ";
		Annotation[] classAannotations= clazz.getDeclaredAnnotations();
		for(Annotation classAnnotation: classAannotations) {
			if (classAnnotation.annotationType().isAssignableFrom(Entity.class)) {
				isEntity = true;
				table = clazz.getName();
			} else if (classAnnotation.annotationType().isAssignableFrom(Table.class) && isEntity) {
				table = ((Table) classAnnotation).name();
			} else {
				return null;
			}
			
		}
		Field[] fields = clazz.getDeclaredFields();
		for (Field field: fields) {
			field.setAccessible(true);	
			if (Modifier.isTransient(field.getModifiers())) continue;;
			if (Modifier.isStatic(field.getModifiers())) continue;
			String fieldName = field.getName();
			Annotation[] fieldAnnotations = field.getDeclaredAnnotations();
			for (Annotation fieldAnnotation: fieldAnnotations) {
				if (fieldAnnotation.annotationType().isAssignableFrom(Column.class)) {
					fieldName = ((Column) fieldAnnotation).name();
				}
			}
			fieldsMap.put(field.getName(), fieldName);
			sql += (fieldName + ", ");
		}
		sql = sql.substring(0, sql.length() -2) + " from " + table + " where id=?";
		return sql;
	}
	public Map<String, String> getFiledsMap() {
		return this.fieldsMap;
	}
	public <T extends DataSet> String getInsertSql(Class<T> clazz) {
		fieldsMap = new LinkedHashMap<>();
		sql = "insert into ";
		Annotation[] classAannotations= clazz.getDeclaredAnnotations();
		for(Annotation classAnnotation: classAannotations) {
			if (classAnnotation.annotationType().isAssignableFrom(Entity.class)) {
				isEntity = true;
				table = clazz.getName();
			} else if (classAnnotation.annotationType().isAssignableFrom(Table.class) && isEntity) {
				table = ((Table) classAnnotation).name();
			} else {
				return null;
			}
			
		}
		sql += (table + " (");
		Field[] fields = clazz.getDeclaredFields();
		for (Field field: fields) {
			field.setAccessible(true);	
			if (Modifier.isTransient(field.getModifiers())) continue;;
			if (Modifier.isStatic(field.getModifiers())) continue;
			String fieldName = field.getName();
			Annotation[] fieldAnnotations = field.getDeclaredAnnotations();
			for (Annotation fieldAnnotation: fieldAnnotations) {
				if (fieldAnnotation.annotationType().isAssignableFrom(Column.class)) {
					fieldName = ((Column) fieldAnnotation).name();
				}
			}
			fieldsMap.put(field.getName(), fieldName);
			sql += (fieldName + ", ");
		}
		sql = sql.substring(0, sql.length() -2) + ") values (";
		for (int i = 0; i < fields.length; i++) {
			sql += "?, ";
		}
		sql = sql.substring(0, sql.length() -2) + ")";
		return sql;
	}
	

}
