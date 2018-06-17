import java.lang.StringBuffer;
import java.lang.reflect.Field;
import java.lang.Class;
import java.util.ArrayList;

public class JSONFormatter
{
	public static void appendAsJSON(StringBuffer sb, ArrayList<?> objects) {
		try {
			for(int i = 0; i < objects.size(); i++) {
				sb.append("{");
				Field[] fields = objects.get(i).getClass().getDeclaredFields();
				for(int j = 0; j < fields.length; j++) {
					fields[j].setAccessible(true);
					Object val = fields[j].get(objects.get(i));
					if(val != null) {
						sb.append(" \"" + fields[j].getName() + "\" : \"" + val + ((j < fields.length - 1) ? "\"," : "\""));
					}
				}
				sb.append((i < objects.size() - 1) ? " }, " : " } ");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
