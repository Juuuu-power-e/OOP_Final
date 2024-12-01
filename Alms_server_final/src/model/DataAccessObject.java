package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;
import java.util.Vector;

public class DataAccessObject {

	public MModel getAModel(String fileName, Class<?> clazz, String key) {
		try {			
			Scanner scanner = new Scanner(new File("data/"+ fileName+".txt"));
			Constructor<?> constructor = clazz.getConstructor();
			MModel mModel = (MModel) constructor.newInstance();
			while (scanner.hasNext()) {
				String mModelKey = mModel.read(scanner);
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

	public Vector<MModel> getModels(String fileName, Class<?> clazz) {
		Vector<MModel> mModels = new Vector<MModel>();
		try {			
			Scanner scanner = new Scanner(new File("data/"+ fileName+".txt"));
			while (scanner.hasNext()) {
				Constructor<?> contstructor = clazz.getConstructor();
				MModel mModel = (MModel) contstructor.newInstance();
				mModel.read(scanner);
				mModels.add(mModel);
			}
			scanner.close();
		} catch (FileNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return mModels;
	}

	public void save(String fileName, Vector<MModel> mModels) {
		try {
			PrintWriter printWriter = new PrintWriter(new File("data/"+fileName+".txt"));
			for (MModel mModel: mModels) {
				mModel.save(printWriter);
			}
			printWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
}
