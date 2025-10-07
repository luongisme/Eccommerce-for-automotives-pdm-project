package com.UI.login;

import com.model.User;
import com.service.AuthService;
import com.service.UserSession;

import java.util.regex.Pattern;

public class LoginController {

    private final LoginScreen view;
    private final Runnable onSuccess;

    public LoginController(LoginScreen view, Runnable onSuccess) {
        this.view = view;
        this.onSuccess = onSuccess;
        attach();
    }

    private void attach() {
        view.getSubmitButton().addActionListener(e -> handleSubmit());
    }

    private void handleSubmit() {
        String email = view.getEmail();
        String pass  = view.getPassword();

        // Validate đơn giản
        if (email.isBlank()) {
            view.setError("Email is required.");
            return;
        }
        if (!isEmail(email)) {
            view.setError("Invalid email format.");
            return;
        }
        if (pass.isBlank()) {
            view.setError("Password is required.");
            return;
        }
        if (pass.length() < 6) {
            view.setError("Password must be at least 6 characters.");
            return;
        }

        // Authenticate with AuthService
        AuthService authService = AuthService.getInstance();
        User user = authService.authenticate(email, pass);

        if (user != null) {
            // Login successful
            UserSession.getInstance().login(user);
            view.clearError();
            if (onSuccess != null) onSuccess.run();
        } else {
            view.setError("Invalid email or password.");
        }
    }

    private static final Pattern EMAIL_PTN =
            Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    private boolean isEmail(String s) {
        return EMAIL_PTN.matcher(s).matches();
    }
}
