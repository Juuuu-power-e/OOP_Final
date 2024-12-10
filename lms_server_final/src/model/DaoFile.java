package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;
import java.util.Vector;

public class DaoFile implements Dao {

	private final String path = System.getProperty("user.dir") + "/lms_server_final/data/";

	public MModel getARow(String fileName, String key, Class<?> clazz) {
		try {

			Scanner scanner = new Scanner(new File(path + fileName+".txt"));
			Constructor<?> constructor = clazz.getConstructor();
			MModel mModel = (MModel) constructor.newInstance();
			while (scanner.hasNext()) {
				String mModelKey = read(mModel, scanner);
				if (key.contentEquals(mModelKey)) {
					return mModel;
				}
			}
			scanner.close();
		} catch (FileNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Vector<MModel> getRows(String fileName, Class<?> clazz) {
		Vector<MModel> mModels = new Vector<MModel>();
		try {			
			Scanner scanner = new Scanner(new File(path + fileName+".txt"));
			while (scanner.hasNext()) {
				Constructor<?> constructor = clazz.getConstructor();
				MModel mModel = (MModel) constructor.newInstance();
				read(mModel, scanner);
				mModels.add(mModel);
			}
			scanner.close();
		} catch (FileNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return mModels;
	}

	@Override
	public void setARow(String fileName, String key, Vector<MModel> mModels) {

	}

	public void setRows(String fileName, Vector<MModel> mModels) {
		try {
			PrintWriter printWriter = new PrintWriter(new File(path + fileName+".txt"));
			for (MModel mModel: mModels) {
				save(mModel, printWriter);
			}
			printWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	public String read(MModel mModel, Scanner scanner) {
		String key = null;
		try {
			Field[] fields = mModel.getClass().getDeclaredFields();
			for (Field field: fields) {
				String fieldValue = scanner.next();
				field.setAccessible(true);
				field.set(mModel, fieldValue);
			}
			key = (String) fields[0].get(mModel);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return key;
	}

	private void save(MModel mModel, PrintWriter printWriter) {
		try {
			Field[] fields = mModel.getClass().getDeclaredFields();
			for (Field field: fields) {
				field.setAccessible(true);
				printWriter.print(field.get(mModel)+" ");
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
