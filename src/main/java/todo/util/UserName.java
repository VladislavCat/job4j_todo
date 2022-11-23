package todo.util;

import org.springframework.ui.Model;
import todo.model.User;

import javax.servlet.http.HttpSession;

public final class UserName {

    private UserName() {
    }

    public static void userSessionSetName(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setUsername("Гость");
        }
        model.addAttribute("user", user);
    }
}
