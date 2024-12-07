package model;

import java.util.Vector;

public interface Dao {

	String path = System.getProperty("user.dir") + "/lms_server_final/data/";

	MModel getARow(String name, String key, Class<?> clazz);
	Vector<MModel> getRows(String name, Class<?> clazz);
	void setARow(String fileName, String key, Vector<MModel> mModels);
	void setRows(String fileName, Vector<MModel> mModels);
}
