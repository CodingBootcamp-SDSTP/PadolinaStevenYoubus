import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.lang.StringBuffer;

public class LogoutServlet extends HttpServlet
{
	YoubusDatabase youbusDB;
	StringBuffer sb;
	SessionManager sm;

	public void init() throws ServletException {
		youbusDB = YoubusDatabase.instance();
		sb = new StringBuffer();
		sm = SessionManager.instance();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		String accountID = request.getPathInfo().split("/")[2];
		if(sm.endSession(accountID)) {
			sb.append("{ \"logout\" : \"ok\" }");
		}
		out.println(sb.toString());
		sb.delete(0, sb.length());
	}

	public void destroy() {
		youbusDB = null;
		sb = null;
	}
}
