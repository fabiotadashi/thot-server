package io.redspark.thot;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.redspark.thot.enums.Role;
import io.redspark.thot.model.User;
import io.redspark.thot.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ThotApplicationTests {

	@Autowired
	protected MockMvc mockMvc;

	@Autowired
	protected ObjectMapper objectMapper;

	@Autowired
	protected PasswordEncoder passwordEncoder;

	@Autowired
	protected UserRepository userRepository;

	private String jwtToken;

	@Test
	public void contextLoads() {
	}

	public String getJwtToken() throws Exception {
		if(jwtToken == null){
			User user = new User();
			user.setName("Fabio");
			user.setRoles(Collections.singletonList(Role.ROLE_USER));
			user.setPassword(passwordEncoder.encode("123456"));

			userRepository.save(user);

			MvcResult authMvcResult = mockMvc.perform(post("/login")
					.contentType(MediaType.APPLICATION_JSON)
					.content("{\"name\":\"Fabio\", \"password\":\"123456\"}"))
					.andExpect(status().isOk())
					.andReturn();

			jwtToken = authMvcResult.getResponse()
					.getHeader(HttpHeaders.AUTHORIZATION);
		}

		return jwtToken;
	}

}

