package ru.podelochki.otus.homework10.services;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import ru.podelochki.otus.homework10.executors.EntityParser;
import ru.podelochki.otus.homework10.models.DataSet;
import ru.podelochki.otus.homework10.services.DBService;

public class DBServiceMyImpl implements DBService{
	private final Connection connection;

	public DBServiceMyImpl (Connection connection) {
		this.connection = connection;		
	}
	
	@Override
	public <T extends DataSet> T load(long id, Class<T> clazz) {
		EntityParser parser = new EntityParser(clazz);
		String sql = parser.getSelectSql();
		Map<String, String> fieldsMap = parser.getFieldsMap();
		T result = null;
		try (PreparedStatement pSt = connection.prepareStatement(sql)) {
			pSt.setLong(1, id);
			ResultSet rSt = pSt.executeQuery();
			if (rSt.next()) {
				result = clazz.newInstance();
				fillObject(rSt, result, fieldsMap, clazz);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	@Override
	public <T extends DataSet> void save(T object) {
		EntityParser parser = new EntityParser(object.getClass());
		String sql = parser.getInsertSql();
		Map<String, String> fieldsMap = parser.getFieldsMap();
		try (PreparedStatement pSt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			fillStatement(pSt, object, fieldsMap);
			pSt.executeUpdate();
			ResultSet rSt = pSt.getGeneratedKeys();
			if (rSt.next()) {
				object.setId(rSt.getLong(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
 	}
	
	private <T> void fillObject(ResultSet rSt, T object, Map<String, String> fieldsMap, Class<T> clazz) throws IllegalArgumentException, IllegalAccessException, SQLException, NoSuchFieldException, SecurityException {
		for (Field field: clazz.getDeclaredFields()) {
			field.setAccessible(true);
			Class type = field.getType();
			if (type == Byte.TYPE || type == Byte.class) field.set(object, rSt.getByte(fieldsMap.get(field.getName())));
            if (type == Short.TYPE || type == Short.class) field.set(object, rSt.getShort(fieldsMap.get(field.getName())));
            if (type == Integer.TYPE || type == Integer.class) field.set(object, rSt.getInt(fieldsMap.get(field.getName())));
            if (type == Long.TYPE || type == Long.class) field.set(object, rSt.getLong(fieldsMap.get(field.getName())));
            if (type == Boolean.TYPE || type == Boolean.class) field.set(object, rSt.getBoolean(fieldsMap.get(field.getName())));
            if (type == Float.TYPE || type == Float.class) field.set(object, rSt.getFloat(fieldsMap.get(field.getName())));
            if (type == Double.TYPE || type == Double.class) field.set(object, rSt.getDouble(fieldsMap.get(field.getName())));
            if (type == String.class) field.set(object, rSt.getString(fieldsMap.get(field.getName())));
		}
		Class superClass = DataSet.class;
        Field id = superClass.getDeclaredField("id");
        id.setAccessible(true);
        id.set(object, rSt.getLong("id"));
        
		
	}
	private <T> void fillStatement(PreparedStatement pSt, T object, Map<String, String> fieldsMap) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, SQLException {
		Class clazz = object.getClass();
		int i = 1;
		for (String name :fieldsMap.keySet()) {
			Field field = clazz.getDeclaredField(name);
			field.setAccessible(true);
			Class type = field.getType();
			if (type == Byte.TYPE || type == Byte.class) pSt.setByte(i,(Byte) field.get(object));
            if (type == Short.TYPE || type == Short.class) pSt.setShort(i, (Short) field.get(object));
            if (type == Integer.TYPE || type == Integer.class) pSt.setInt(i, (Integer) field.get(object));
            if (type == Long.TYPE || type == Long.class) pSt.setLong(i, (Long) field.get(object));
            if (type == Boolean.TYPE || type == Boolean.class) pSt.setBoolean(i, (Boolean) field.get(object));
            if (type == Float.TYPE || type == Float.class) pSt.setFloat(i, (Float) field.get(object));
            if (type == Double.TYPE || type == Double.class) pSt.setDouble(i, (Double) field.get(object));
            if (type == String.class) pSt.setString(i, (String) field.get(object));
            i++;
		}
	}
	@Override
	public void close() {
		try {
			this.connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
