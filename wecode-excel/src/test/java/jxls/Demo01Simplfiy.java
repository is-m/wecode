package jxls;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;

import jxls.dto.Employee;

public class Demo01Simplfiy {

	public static void main(String[] args) throws IOException {
		Context context = new Context();
		context.putVar("employees", buildEmployees());
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			fis = new FileInputStream("D:\\excel\\tl\\t.xlsx");
			fos = new FileOutputStream("D:\\excel\\tg\\tg.xlsx");
			JxlsHelper.getInstance().processTemplate(fis, fos, context);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			if (fis != null)
				fis.close();
			if (fos != null)
				fos.close();
		}
	}

	private static List<Employee> buildEmployees() {
		int size = (int) (Math.random() * 20) + 1;
		List<Employee> result = new ArrayList<>(size);

		while (size-- > 0) {
			Calendar c = Calendar.getInstance();
			c.add(Calendar.MONTH, size);
			result.add(new Employee("test-" + size, c.getTime(), BigDecimal.valueOf(Math.random() * 10000),
					BigDecimal.valueOf(Math.random() * 100)));
		}
		return result;
	}

}
