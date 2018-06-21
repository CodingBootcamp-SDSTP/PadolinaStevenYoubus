import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.lang.StringBuffer;

public class SessionServlet extends HttpServlet
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
		String accountid = request.getPathInfo().split("/")[2];
		sb.append("{ ");
		if(sm.doesExist(accountid)) {
			sb.append("\"id\" : \"" + accountid + "\", \"sessionID\" : \"" + sm.getSessionID(accountid) + "\"");
		}
		sb.append(" }");
		out.println(sb.toString());
		sb.delete(0, sb.length());
	}

	public void destroy() {
		youbusDB = null;
		sb = null;
	}
}
