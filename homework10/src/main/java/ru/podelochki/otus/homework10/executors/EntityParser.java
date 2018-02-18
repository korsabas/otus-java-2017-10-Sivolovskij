package ru.podelochki.otus.homework10.executors;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import ru.podelochki.otus.homework10.models.DataSet;


public class  EntityParser {
	
	private final String selectSql;
	private final String insertSql;
	private final Map<String, String> fieldsMap;
	private final String table;
	
	public <T extends DataSet> EntityParser(Class<T> clazz) {
		boolean isEntity = false;
		String tableValue = null;
		Annotation[] classAannotations= clazz.getDeclaredAnnotations();
		for(Annotation classAnnotation: classAannotations) {
			if (classAnnotation.annotationType().isAssignableFrom(Entity.class)) {
				isEntity = true;
				tableValue = clazz.getName();
			} else if (classAnnotation.annotationType().isAssignableFrom(Table.class) && isEntity) {
				tableValue = ((Table) classAnnotation).name();
			} else {
				tableValue = null;
			}
			
		}
		table = tableValue;
		fieldsMap = createFieldsMap(clazz);
		selectSql = getSelectString();
		insertSql = getInsertString();
	}
	
	//private String sql;
	
	private <T extends DataSet> Map<String, String> createFieldsMap(Class<T> clazz) {
		Map<String, String> fieldsMap = new LinkedHashMap<>();;
		Field[] fields = clazz.getDeclaredFields();
		for (Field field: fields) {
			field.setAccessible(true);	
			if (Modifier.isTransient(field.getModifiers())) continue;;
			if (Modifier.isStatic(field.getModifiers())) continue;
			if (!field.getType().isPrimitive() && !field.getType().isAssignableFrom(String.class)) continue;
			String fieldName = field.getName();
			Annotation[] fieldAnnotations = field.getDeclaredAnnotations();
			for (Annotation fieldAnnotation: fieldAnnotations) {
				if (fieldAnnotation.annotationType().isAssignableFrom(Column.class)) {
					fieldName = ((Column) fieldAnnotation).name();
				}
			}
			fieldsMap.put(field.getName(), fieldName);
		}
		return fieldsMap;
	}

	private String getSelectString() {
		
		String sql = "select id, ";
		for (Entry<String, String> item : fieldsMap.entrySet()) {
			sql += (item.getValue() + ", ");
		}
		sql = sql.substring(0, sql.length() -2) + " from " + table + " where id=?";
		return sql;
	}
	public Map<String, String> getFieldsMap() {
		return this.fieldsMap;
	}
	private String getInsertString() {
		String sql = "insert into ";

		sql += (table + " (");

		for (Entry<String, String> item : fieldsMap.entrySet()) {
			sql += (item.getValue() + ", ");
		}
		sql = sql.substring(0, sql.length() -2) + ") values (";
		for (int i = 0; i < fieldsMap.size(); i++) {
			sql += "?, ";
		}
		sql = sql.substring(0, sql.length() -2) + ")";
		return sql;
	}

	public String getSelectSql() {
		return selectSql;
	}

	public String getInsertSql() {
		return insertSql;
	}
	
	

}
