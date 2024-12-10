package model;
import aspect.LogManager;
import constants.Configuration;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.Vector;


public class DaoDB implements Dao {

	private static final String URL = Configuration.loadConfig("URL");
	private static final String USER = Configuration.loadConfig("USER");
	private static final String PASSWORD = Configuration.loadConfig("PASSWORD");

	private Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, USER, PASSWORD);
	}

	@Override
	public MModel getARow(String tableName, String key, Class<?> clazz) {
		String query = "SELECT * FROM " + tableName + " WHERE field1 = ?";
		LogManager.getInstance().log(query); // 로그 출력
		try (Connection conn = getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setString(1, key); // field1 값으로 검색
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				MModel mModel = (MModel) clazz.getConstructor().newInstance();
				Field[] fields = clazz.getDeclaredFields();

				// ResultSet 데이터를 객체 필드에 동적으로 매핑
				for (int i = 0; i < fields.length; i++) {
					if (i < rs.getMetaData().getColumnCount()) { // 컬럼 수만큼 매핑
						fields[i].setAccessible(true);
						fields[i].set(mModel, rs.getObject(i + 1)); // ResultSet은 1-based index
					}
				}
				return mModel;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Vector<MModel> getRows(String tableName, Class<?> clazz) {
		String query = "SELECT * FROM " + tableName;
		LogManager.getInstance().log(query); // 로그 출력
		Vector<MModel> mModels = new Vector<>();
		try (Connection conn = getConnection();
			 Statement stmt = conn.createStatement();
			 ResultSet rs = stmt.executeQuery(query)) {

			while (rs.next()) {
				MModel mModel = (MModel) clazz.getConstructor().newInstance();
				Field[] fields = clazz.getDeclaredFields();

				// ResultSet 데이터를 객체 필드에 동적으로 매핑
				for (int i = 0; i < fields.length; i++) {
					if (i < rs.getMetaData().getColumnCount()) { // 컬럼 수만큼 매핑
						fields[i].setAccessible(true);
						fields[i].set(mModel, rs.getObject(i + 1)); // ResultSet은 1-based index
					}
				}
				mModels.add(mModel);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mModels;
	}

	@Override
	public void setARow(String tableName, String key, Vector<MModel> mModels) {
		if (mModels.isEmpty()) return;

		StringBuilder queryBuilder = new StringBuilder("INSERT INTO " + tableName + " VALUES (");
		int fieldsCount = mModels.get(0).getClass().getDeclaredFields().length;
		queryBuilder.append("?,".repeat(fieldsCount));
		queryBuilder.setLength(queryBuilder.length() - 1); // 마지막 쉼표 제거
		queryBuilder.append(")");
		String query = queryBuilder.toString();
		LogManager.getInstance().log(query); // 로그 출력

		try (Connection conn = getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(query)) {

			for (MModel mModel : mModels) {
				int index = 1;
				for (Field field : mModel.getClass().getDeclaredFields()) {
					field.setAccessible(true);
					pstmt.setObject(index++, field.get(mModel));
				}
				pstmt.addBatch();
			}
			pstmt.executeBatch();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setRows(String tableName, Vector<MModel> mModels) {
		setARow(tableName, null, mModels);
	}

	private String generatePlaceholders(MModel mModel) {
		int fieldsCount = mModel.getClass().getDeclaredFields().length;
		return String.join(",", "?".repeat(fieldsCount).split(""));
	}
}
