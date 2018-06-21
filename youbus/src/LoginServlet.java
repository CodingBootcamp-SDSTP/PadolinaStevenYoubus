import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.lang.StringBuffer;
import java.util.ArrayList;
import java.lang.reflect.Field;
import java.lang.Class;

public class LoginServlet extends HttpServlet
{
	YoubusDatabase youbusDB;
	StringBuffer sb;
	SessionManager sm;
	ArrayList<UserAccount> accounts;

	public void init() throws ServletException {
		youbusDB = YoubusDatabase.instance();
		sb = new StringBuffer();
		sm = SessionManager.instance();
		accounts = new ArrayList<UserAccount>();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		String username = request.getPathInfo().split("/")[2];
		UserAccount account = youbusDB.getAccount(username);
		account.setSessionID(sm.newSession(String.valueOf(account.ID)));
		accounts.add(account);
		JSONFormatter.appendAsJSON(sb, accounts);
		accounts.clear();
		out.println(sb.toString());
		sb.delete(0, sb.length());
	}

	public void destroy() {
		youbusDB = null;
		sb = null;
	}
}
