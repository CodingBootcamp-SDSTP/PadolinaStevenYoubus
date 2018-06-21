import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.lang.StringBuffer;
import java.util.ArrayList;
import java.lang.reflect.Field;
import java.lang.Class;

public class AllCrewsServlet extends HttpServlet
{
	private YoubusDatabase youbusDB;
	private StringBuffer sb;

	public void init() throws ServletException {
		youbusDB = YoubusDatabase.instance();
		sb = new StringBuffer();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		sb.append("[ ");
		ArrayList<BusCrew> crews = youbusDB.getAllCrews(2);
		appendAsJSON(crews);
		sb.append(" ]");
		out.println(sb.toString());
		sb.delete(0, sb.length());
	}

	public void appendAsJSON(ArrayList<BusCrew> crews) {
		try {
			for(int i = 0; i < crews.size(); i++) {
				sb.append((i == 0) ? "{" : ", {");
				Field[] fields = crews.get(i).getClass().getSuperclass().getDeclaredFields();
				for(int j = 0; j < fields.length; j++) {
					fields[j].setAccessible(true);
					Object val = fields[j].get(crews.get(i));
					if(val != null) {
						sb.append(" \"" + fields[j].getName() + "\" : \"" + val + "\",");
					}
				}
				sb.append(" \"type\" : " + ((crews.get(i) instanceof BusDriver) ? "\"driver\"" : "\"conductor\""));
				sb.append( " }");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void destroy() {
		youbusDB = null;
		sb = null;
	}
}
