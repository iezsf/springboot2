package cn.hust.utils;

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.Clock;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JWTUtil {
	// 过期时间5分钟
	private static final long EXPIRE_TIME = 1 * 60 * 1000;

	/**
	 * 校验token是否正确
	 *
	 * @param token  密钥
	 * @param secret 用户的密码
	 * @return 是否正确
	 */
	public static boolean verify(String token, String username, String secret) {
		try {

			DecodedJWT jwt = null;
			Algorithm algorithm = Algorithm.HMAC256(secret);
			JWTVerifier verifier = JWT.require(algorithm).withClaim("username", username).build();
			jwt = verifier.verify(token);
			
		} catch (Exception exception) {
			return false;
		}
		return true;
	}

	public static void verifyToken(String token, String key) throws Exception {
		JWTVerifier verifier = JWT.require(Algorithm.HMAC256(key)).build();
		DecodedJWT jwt = verifier.verify(token);
		Map<String, Claim> claims = jwt.getClaims();
		System.out.println(claims.get("name").asString());
	}

	/**
	 * 获得token中的信息无需secret解密也能获得
	 *
	 * @return token中包含的用户名
	 */
	public static String getUsername(String token) {
		try {
			DecodedJWT jwt = JWT.decode(token);
			return jwt.getClaim("username").asString();
		} catch (JWTDecodeException e) {
			return null;
		}
	}

	/**
	 * 生成签名,5min后过期
	 *
	 * @param username 用户名
	 * @param secret   用户的密码
	 * @return 加密的token
	 */
	public static String sign(String username, String secret) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("alg", "HS256");
			map.put("typ", "JWT");
			Algorithm algorithm = Algorithm.HMAC256(secret);
			// 附带username信息
			Date expiresAt = new Date(System.currentTimeMillis()+2*1000);
			return JWT.create().withHeader(map).withClaim("username", username).withExpiresAt(expiresAt).sign(algorithm);
			
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}
	
	/*
	public static void main(String[] args) throws UnsupportedEncodingException, InterruptedException{
		System.out.println("jwt test");
		String token = sign("admin","admin");
		String old_token = token;
		System.out.println("token="+token);
		DecodedJWT jwt = null;
		Algorithm algorithm = Algorithm.HMAC256("admin");
		JWTVerifier verifier = JWT.require(algorithm).build();
		jwt = verifier.verify(token);
	
		LocalTime lt_old = UDateToLocalTime(jwt.getExpiresAt());
		LocalTime lt_now = LocalTime.now();
		Date token_sign_time = jwt.getExpiresAt();
		System.out.println("token_sign_time:" +lt_old);
		TimeUnit.SECONDS.sleep(10);
		System.out.print("-");
		if(lt_now.isAfter(lt_old))
		{
//				重新签名
			if(lt_now.plusSeconds(5).isAfter(lt_old))
			{
				token = sign("admin","admin");
				System.out.println("new token:" + token);
			}
		}
		System.out.println("check new token");
		jwt = verifier.verify(token);
		System.out.println("check old token ");
		jwt = verifier.verify(old_token);
		System.out.println("done~~");
	}
	//*/
 
	public static LocalTime UDateToLocalTime(Date date) {
	    Instant instant = date.toInstant();
	    ZoneId zone = ZoneId.systemDefault();
	    LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
	    LocalTime localTime = localDateTime.toLocalTime();
	    return localTime;
	}
}
