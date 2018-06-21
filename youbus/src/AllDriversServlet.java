import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.lang.StringBuffer;
import java.util.ArrayList;
import java.lang.reflect.Field;
import java.lang.Class;

public class AllDriversServlet extends HttpServlet
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
		ArrayList<BusCrew> drivers = youbusDB.getAllCrews(0);
		appendAsJSON(drivers);
		sb.append(" ]");
		out.println(sb.toString());
		sb.delete(0, sb.length());
	}

	public void appendAsJSON(ArrayList<BusCrew> drivers) {
		try {
			for(int i = 0; i < drivers.size(); i++) {
				if(drivers.get(i) instanceof BusDriver) {
					sb.append((i == 0) ? "{" : ", {");
					Field[] fields = drivers.get(i).getClass().getDeclaredFields();
					Field[] sfields = drivers.get(i).getClass().getSuperclass().getDeclaredFields();
					for(int j = 0; j < sfields.length; j++) {
						sfields[j].setAccessible(true);
						Object val = sfields[j].get(drivers.get(i));
						if(val != null) {
							sb.append(" \"" + sfields[j].getName() + "\" : \"" + val + ((j < sfields.length - 1) ? "\"," : "\""));
						}
					}
					for(int j = 0; j < fields.length; j++) {
						fields[j].setAccessible(true);
						Object val = fields[j].get(drivers.get(i));
						if(val != null) {
							sb.append(((sfields.length > 0) ? ", \"" : " \"") + fields[j].getName() + "\" : \"" + val + ((j < fields.length - 1) ? "\"," : "\""));
						}
					}
					sb.append( " }");
				}
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
