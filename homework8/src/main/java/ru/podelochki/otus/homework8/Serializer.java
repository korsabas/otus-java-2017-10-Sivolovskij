package ru.podelochki.otus.homework8;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;


import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

public class Serializer {

	private Serializer() {
	}
	
	private static JsonObjectBuilder getJson(String name,Object object) {
		JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
		Class<?> objClass = object.getClass();
		try {
			
			if (object.getClass().isAssignableFrom(String.class)) {
				jsonObjectBuilder.add(name,object.toString());
				return jsonObjectBuilder;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Field[] fields = objClass.getDeclaredFields();
		for (Field field: fields) {
			field.setAccessible(true);	
			if (Modifier.isTransient(field.getModifiers())) continue;;
	        if (Modifier.isStatic(field.getModifiers())) continue;
	        
	        String fieldName = field.getName();
	         
			try {
				Object fieldValue = field.get(object);
				if (fieldValue == null) {
					jsonObjectBuilder.addNull(field.getName());
				}else if (field.getType().isAssignableFrom(String.class)) {
					jsonObjectBuilder.add(field.getName(),field.get(object).toString());
				} else if (field.getType().isArray()) {
					JsonArrayBuilder bTemp = createArray(field.getName(), field.get(object));
					jsonObjectBuilder.add(field.getName(), bTemp);
				}  else if ((field.get(object) instanceof Collection)) {
		        	JsonArrayBuilder bTemp = createArray(field.getName(), ((Collection) field.get(object)).toArray());
					jsonObjectBuilder.add(field.getName(), bTemp);
		        }   else if (field.getType() == Byte.TYPE || field.getType() == Byte.class){
		            jsonObjectBuilder.add(fieldName,(Byte) fieldValue);
		        }else if (field.getType() == Short.TYPE || field.getType() == Short.class){
		            jsonObjectBuilder.add(fieldName,(Short) fieldValue);
		        }else if (field.getType() == Character.TYPE || field.getType() == Character.class){
		            jsonObjectBuilder.add(fieldName, fieldValue.toString());
		        }else if (field.getType() == Integer.TYPE || field.getType() == Integer.class){
		            jsonObjectBuilder.add(fieldName,(Integer) fieldValue);
		        }else if (field.getType() == Long.TYPE || field.getType() == Long.class){
		            jsonObjectBuilder.add(fieldName,(Long) fieldValue);
		        }else if (field.getType() == Float.TYPE || field.getType() == Float.class){
		            jsonObjectBuilder.add(fieldName,(Float) fieldValue);
		        }else if (field.getType() == Double.TYPE || field.getType() == Double.class){
		            jsonObjectBuilder.add(fieldName,(Double) fieldValue);
		        }else if ((field.get(object) instanceof Object)) {
		        	jsonObjectBuilder.add(fieldName, Serializer.getJson(fieldName, field.get(object)));
				}
			} catch (Exception e) {
				e.printStackTrace();
			} 	
		}

		return jsonObjectBuilder;
	}
	private static JsonArrayBuilder createArray(String name, Object array) {
		JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

        if (array instanceof  byte[]) for (byte b : (byte[]) array) jsonArrayBuilder.add(b);
        else if (array instanceof  short[]) for (short b : (short[]) array) jsonArrayBuilder.add(b);
        else if (array instanceof  char[]) for (char b : (char[]) array) jsonArrayBuilder.add(b);
        else if (array instanceof  int[]) for (int b : (int[]) array) jsonArrayBuilder.add(b);
        else if (array instanceof  long[]) for (long b : (long[]) array) jsonArrayBuilder.add(b);
        else if (array instanceof  float[]) for (float b : (float[]) array) jsonArrayBuilder.add(b);
        else if (array instanceof  double[]) for (double b : (double[]) array) jsonArrayBuilder.add(b);
        else if (array instanceof Byte[]) for (Byte b : (Byte[]) array){
            if (b == null) jsonArrayBuilder.addNull();
            else jsonArrayBuilder.add(b);
        }
        else if (array instanceof Short[]) for (Short b : (Short[]) array){
            if (b == null) jsonArrayBuilder.addNull();
            else jsonArrayBuilder.add(b);
        }
        else if (array instanceof Character[]) for (Character b : (Character[]) array){
            if (b == null) jsonArrayBuilder.addNull();
            else jsonArrayBuilder.add(b);
        }
        else if (array instanceof Integer[]) for (Integer b : (Integer[]) array){
            if (b == null) jsonArrayBuilder.addNull();
            else jsonArrayBuilder.add(b);
        }
        else if (array instanceof Long[]) for (Long b : (Long[]) array){
            if (b == null) jsonArrayBuilder.addNull();
            else jsonArrayBuilder.add(b);
        }
        else if (array instanceof Float[]) for (Float b : (Float[]) array){
            if (b == null) jsonArrayBuilder.addNull();
            else jsonArrayBuilder.add(b);
        }
        else if (array instanceof Double[]) for (Double b : (Double[]) array){
            if (b == null) jsonArrayBuilder.addNull();
            else jsonArrayBuilder.add(b);
        }
        else if (array instanceof Boolean[]) for (Boolean b : (Boolean[]) array){
            if (b == null) jsonArrayBuilder.addNull();
            else jsonArrayBuilder.add(b);
        }
        else if (array instanceof String[]) for (String b : (String[]) array){
            if (b == null) jsonArrayBuilder.addNull();
            else jsonArrayBuilder.add(b);
        } else if (array instanceof Object) for (Object obj : (Object[]) array){
            if (obj == null) jsonArrayBuilder.addNull();
            else jsonArrayBuilder.add(Serializer.getJson(name, obj));
        }
        return jsonArrayBuilder;
	}

	public static String getJson(Object object) {
		Class<?> objClass = object.getClass();
		if (objClass.isArray()){
            JsonArrayBuilder jsonArrayBuilder = Serializer.createArray("", object);
            return jsonArrayBuilder.build().toString();
        } else if (object instanceof Boolean || object instanceof Character || object instanceof Number){
        	return object.toString();
        } else {
		return getJson(object.getClass().getName(), object).build().toString();
        }
		
	}
 }
