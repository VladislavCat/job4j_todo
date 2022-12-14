package todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import todo.model.User;
import todo.service.UserService;
import todo.util.TimeZoneCheck;
import todo.util.UserName;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/loginPage")
    public String loginPage(Model model, @RequestParam(name = "fail", required = false) Boolean fail) {
        model.addAttribute("fail", fail != null);
        return "/login_page";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpServletRequest req) {
        Optional<User> userDb = userService.findUserByUsernameAndPassword(
                user.getUsername(), user.getPassword()
        );
        if (userDb.isEmpty()) {
            return "redirect:/loginPage?fail=true";
        }
        HttpSession session = req.getSession();
        session.setAttribute("user", userDb.get());
        return "redirect:/tasks/all";
    }

    @GetMapping("/formRegistration")
    public String createUser(Model model, @RequestParam(name = "fail", required = false) Boolean fail) {
        model.addAttribute("user", new User("username", "password", "UTC"));
        model.addAttribute("fail", fail != null);
        return "formRegistration";
    }

    @PostMapping("/registration")
    public String registration(Model model, @ModelAttribute User user) {
        Optional<User> regUser = userService.add(user);
        if (regUser.isEmpty()) {
            model.addAttribute("message", "Пользователь с таким именем уже существует");
            return "redirect:/formRegistration?fail=true";
        }
        return "redirect:/all_tasks";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/profile")
    public String profile(Model model, HttpSession session) {
        UserName.userSessionSetName(model, session);
        return "profile";
    }

    @GetMapping("/updateProfile/{userId}")
    public String updateProfile(Model model, HttpSession httpSession, @PathVariable("userId") int id,
                                @RequestParam(name = "fail", required = false) Boolean fail) {
        UserName.userSessionSetName(model, httpSession);
        model.addAttribute("userUpdate", userService.findById(id));
        model.addAttribute("fail", fail != null);
        return "updateProfile";
    }

    @PostMapping("/update")
    public String update(Model model, @ModelAttribute User user, HttpSession session) {
        if (!TimeZoneCheck.check(user.getTimeZone())) {
            return "redirect:/updateProfile/" + user.getId() + "?fail=true";
        }
        boolean isUpdated = userService.update(user.getId(), user);
        if (!isUpdated) {
            model.addAttribute("message", "Состояние не было изменено! "
                    + " Такого профиля не существует или такой временно пояс некорректен");
            return "redirect:/404?fail=true";
        }
        session.setAttribute("user", user);
        return "redirect:/profile";
    }
}
