import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.lang.StringBuffer;

public class RegisterServlet extends HttpServlet
{
	private StringBuffer details;
	private StringBuffer sb;
	private BufferedReader br;
	private YoubusDatabase youbusDB;

	public void init() throws ServletException {
		youbusDB = YoubusDatabase.instance();
		details = new StringBuffer();
		sb = new StringBuffer();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		br = request.getReader();
		generateObjectString(readContent());
		try {
			out.println((youbusDB.createObject(sb.toString())) ? "Registration successful" : "Registration failed.");
		}
		catch(Exception e) {
			e.printStackTrace();
			out.println("Action Failed.");
		}
		finally {
			details.delete(0, details.length());
			sb.delete(0, sb.length());
			br = null;
		}
	}

	public String readContent() {
		try {
			String fileLine;
			while((fileLine = br.readLine()) != null) {
				details.append(fileLine);
			}
			br.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return(details.toString());
	}

	public void generateObjectString(String line) {
		String[] a = line.split("&");
		for(int i = 0; i < a.length; i++) {
			String[] b = a[i].split("=");
			sb.append(b[1] + "~");
		}
	}

	public void destroy() {
		details = null;
		sb = null;
	}
}
