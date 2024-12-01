package model;

import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.Scanner;

public class MModel {
	public MModel() {
	}
	
	public String read(Scanner scanner) {
		String key = null;
		try {
			Field[] fields = this.getClass().getDeclaredFields();
			for (Field field: fields) {
				String fieldValue = scanner.next();
				field.setAccessible(true);
				field.set(this, fieldValue);
			}
			key = (String) fields[0].get(this);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return key;
	}
	
	public void save(PrintWriter printWriter) {	
		try {
			Field[] fields = this.getClass().getDeclaredFields();
			for (Field field: fields) {
				field.setAccessible(true);
				printWriter.print(field.get(this)+" ");
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
