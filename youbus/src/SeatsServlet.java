import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.lang.StringBuffer;

public class SeatsServlet extends HttpServlet
{
	YoubusDatabase youbusDB;
	StringBuffer sb;

	public void init() throws ServletException {
		youbusDB = YoubusDatabase.instance();
		sb = new StringBuffer();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		sb.append("[ ");
		String[] details = request.getPathInfo().split("/");
		JSONFormatter.appendAsJSON(sb, youbusDB.getSeats(details[1], details[2]));
		sb.append("]");
		out.println(sb.toString());
		sb.delete(0, sb.length());
	}

	public void destroy() {
		youbusDB = null;
		sb = null;
	}
}
