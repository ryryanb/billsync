package com.localtide.billsync.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.localtide.billsync.entity.User;
import com.localtide.billsync.entity.User.USER_STATUS;
import com.localtide.billsync.repository.UserRepository;
import com.localtide.billsync.utils.Constants;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AjaxAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	@Autowired
	private UserRepository userRepository;

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {

		User user = userRepository.findByUserNameIgnoreCase(request.getParameter(Constants.usernameParams));

		if (user == null) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Bad Credentials");
			return;
		}
		user.setLoginAttempts(user.getLoginAttempts() + 1);
		if (user.getLoginAttempts() >= Constants.LOGIN_ATTEMPTS) {
			user.setStatus(USER_STATUS.L);
		}

		user = userRepository.save(user);

		if (user.getStatus().equals(USER_STATUS.L)) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN,
					"Your account has been locked. Please contact our Administrator.");
		} else {
			int attempts = Constants.LOGIN_ATTEMPTS - user.getLoginAttempts();
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
					"Incorrect password. " + attempts + " attempts remaining.");
		}
	}

}
