import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.lang.StringBuffer;
import java.util.ArrayList;

public class UserServlet extends HttpServlet
{
	YoubusDatabase youbusDB;
	StringBuffer sb;
	SessionManager sm;
	ArrayList<User> users;

	public void init() throws ServletException {
		youbusDB = YoubusDatabase.instance();
		sb = new StringBuffer();
		sm = SessionManager.instance();
		users = new ArrayList<User>();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		String userid = request.getPathInfo().split("/")[2];
		User user = youbusDB.getUser(userid);
		users.add(user);
		JSONFormatter.appendAsJSON(sb, users);
		users.clear();
		out.println(sb.toString());
		sb.delete(0, sb.length());
	}

	public void destroy() {
		youbusDB = null;
		sb = null;
		users = null;
	}
}
