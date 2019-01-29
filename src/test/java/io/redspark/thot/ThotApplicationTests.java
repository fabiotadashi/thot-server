package io.redspark.thot;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.redspark.thot.enums.Role;
import io.redspark.thot.model.User;
import io.redspark.thot.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
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

	private String jwtToken;

	@Autowired
	protected MockMvc mvc;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	protected ModelMapper modelMapper;

	@Autowired
	protected ObjectMapper objectMapper;

	@Test
	public void contextLoads() {
	}

	@Before
	public void before() throws Exception {
		User user = new User();
		user.setName("fabio");
		user.setPassword(passwordEncoder.encode("123456"));
		user.setRoles(Collections.singletonList(Role.ROLE_USER));

		userRepository.save(user);
	}

	protected String getJwtToken() throws Exception {
		if(jwtToken == null) {
			MvcResult authResult = mvc.perform(post("/login")
					.content("{\"name\":\"fabio\", \"password\":\"123456\"}")
					.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())
					.andReturn();

			jwtToken = authResult.getResponse().getHeader(HttpHeaders.AUTHORIZATION);
		}
		return jwtToken;
	}

	protected String getVendorJwtToken() throws Exception {
		User user = new User();
		user.setName("daniel");
		user.setPassword(passwordEncoder.encode("123456"));
		user.setRoles(Collections.singletonList(Role.ROLE_VENDOR));

		userRepository.save(user);

		MvcResult authResult = mvc.perform(post("/login")
				.content("{\"name\":\"daniel\", \"password\":\"123456\"}")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();

		String vendorJwtToken = authResult.getResponse().getHeader(HttpHeaders.AUTHORIZATION);

		return vendorJwtToken;
	}
}

