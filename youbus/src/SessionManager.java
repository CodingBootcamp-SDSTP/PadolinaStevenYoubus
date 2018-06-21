import redis.clients.jedis.Jedis;
import java.lang.StringBuffer;
import java.util.Random;

public class SessionManager
{
	private static SessionManager _instance = null;

	public static SessionManager instance() {
		if(_instance == null) {
			_instance = new SessionManager();
		}
		return(_instance);
	}

	private Jedis jedis;
	private Random r;
	public SessionManager() {
		jedis = new Jedis("localhost");
		r = new Random();
	}

	public String getServerName() {
		return(jedis.ping());
	}

	public String newSession(String userid) {
		String a = jedis.get(userid);
		if(a == null) {
			a = generateSessionID(userid);
			jedis.set(userid, a);
			a = jedis.get(userid);
		}
		return(a);
	}

	public String getSessionID(String userid) {
		return(jedis.get(userid));
	}

	public boolean doesExist(String userid) {
		if(jedis.get(userid) == null) {
			return(false);
		}
		return(true);
	}

	public boolean endSession(String userid) {
		jedis.del(userid);
		if(jedis.get(userid) == null) {
			return(true);
		}
		return(false);
	}

	public String generateSessionID(String userid) {
		StringBuffer sb = new StringBuffer(userid);
		while(sb.length() < 20) {
			sb.append(Integer.toHexString(r.nextInt()));
		}
		sb.append(System.currentTimeMillis());
		return(sb.toString());
	}
}
