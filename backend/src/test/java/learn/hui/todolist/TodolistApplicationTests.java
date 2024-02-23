package learn.hui.todolist;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class TodolistApplicationTests {
	@Test
	void generateJwt() {
		String name = "root";
		String jwt = Jwts.builder()
				.signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(
						"K2iYMhd0Gnwn2N+JNwTT37v/6ea09WyQY/KDCq4ezSRhUNM6tT/YRcpDtPxz7MmP")))
				.subject(name)
				.expiration(new Date(System.currentTimeMillis() + 3600 * 1000))
				.compact();
		System.out.println(jwt);
	}

	@Test
	void parseJwt() {
		String jwt = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJyb290IiwiZXhwIjoxNzA4NDE5MTgyfQ.3U2WHzle7ll9JDaqHkcGUFYppRNo1e_UXzoBLP_aFWfhZdSsAqWsJAT_irvhrriM";
		String name = (String) Jwts.parser().verifyWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(
				"K2iYMhd0Gnwn2N+JNwTT37v/6ea09WyQY/KDCq4ezSRhUNM6tT/YRcpDtPxz7MmP")))
				.build()
				.parseSignedClaims(jwt)
				.getPayload()
				.get("sub");
		System.out.println(name);
	}

}
