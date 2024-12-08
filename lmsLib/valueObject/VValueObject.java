package valueObject;

import java.io.Serializable;
import java.lang.reflect.Field;

public class VValueObject implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public VValueObject() {
	}

	public String[] toLogMessage() {
		try {
			Field[] fields = this.getClass().getDeclaredFields();
			// serialVersionUID 필드를 제외하고 남은 필드 개수만큼 배열 생성
			long nonExcludedFieldCount = java.util.Arrays.stream(fields)
					.filter(field -> !field.getName().equals("serialVersionUID"))
					.count();
			String[] logMessages = new String[(int) nonExcludedFieldCount];

			int index = 0;
			for (Field field : fields) {
				field.setAccessible(true); // private 필드 접근 허용

				// "serialVersionUID" 필드를 제외
				if (field.getName().equals("serialVersionUID")) {
					continue;
				}

				Object value = field.get(this); // 필드 값 가져오기
				logMessages[index++] = field.getName() + "=" + (value != null ? value.toString() : "null");
			}

			return logMessages;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return new String[]{"Error accessing fields."};
		}
	}
}
