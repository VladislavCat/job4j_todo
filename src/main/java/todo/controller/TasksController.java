package todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import todo.model.Task;
import todo.model.User;
import todo.service.TasksService;
import todo.util.UserName;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Optional;

@AllArgsConstructor
@Controller
@RequestMapping("/tasks")
public class TasksController {
    private final TasksService tasksService;

    @GetMapping("/create")
    public String createTask(Model model, HttpSession httpSession) {
        UserName.userSessionSetName(model, httpSession);
        model.addAttribute("task",
                new Task());
        return "tasks/create";
    }

    @PostMapping("/add")
    public String addTask(@ModelAttribute Task task, HttpSession httpSession) {
        task.setUser((User) httpSession.getAttribute("user"));
        task.setCreated(LocalDateTime.now());
        task.setDone(false);
        tasksService.add(task);
        return "redirect:/tasks/all";
    }

    @GetMapping("/all")
    public String allTasks(Model model, HttpSession httpSession) {
        UserName.userSessionSetName(model, httpSession);
        model.addAttribute("tasks", tasksService.findAll());
        return "tasks/all";
    }

    @GetMapping("/completed")
    public String completedTasks(Model model, HttpSession httpSession) {
        UserName.userSessionSetName(model, httpSession);
        model.addAttribute("tasks", tasksService.findAllCompletedTasks());
        return "tasks/completed";
    }

    @GetMapping("/unexecuted")
    public String unexecutedTasks(Model model, HttpSession httpSession) {
        UserName.userSessionSetName(model, httpSession);
        model.addAttribute("tasks", tasksService.findAllUnexecutedTask());
        return "tasks/unexecuted";
    }

    @GetMapping("/detail/{Id}")
    public String detailTask(Model model, HttpSession httpSession,
                             @PathVariable("Id") int id) {
        UserName.userSessionSetName(model, httpSession);
        model.addAttribute("task", tasksService.findById(id));
        return "tasks/detail";
    }

    @PostMapping("/complete/{Id}")
    public String completedTask(Model model, @PathVariable("Id") int id) {
        Optional<Integer> i = tasksService.executeTask(id);
        if (i.isPresent() && i.get() <= 0) {
            model.addAttribute("message", "Состояние не было изменено!");
            return "redirect:/tasks/404?fail=true";
        }
        return "redirect:tasks/all";
    }

    @PostMapping("/delete/{Id}")
    public String deletedTask(Model model, @PathVariable("Id") int id) {
        Optional<Integer> i = tasksService.deleteTask(id);
        if (i.isPresent() && i.get() <= 0) {
            model.addAttribute("message", "Состояние не было изменено!");
            return "redirect:/tasks/404?fail=true";
        }
        return "redirect:/tasks/all";
    }

    @GetMapping("/formEdit/{Id}")
    public String formEdit(Model model, HttpSession httpSession, @PathVariable("Id") int id) {
        UserName.userSessionSetName(model, httpSession);
        model.addAttribute("task", tasksService.findById(id));
        return "tasks/formEdit";
    }

    @PostMapping("/update")
    public String updateTask(Model model, @ModelAttribute Task task) {
        Optional<Integer> i = tasksService.updateTask(task.getId(), task);
        if (i.isPresent() && i.get() <= 0) {
            model.addAttribute("message", "Состояние не было изменено!");
            return "redirect:/tasks/404?fail=true";
        }
        return "redirect:/tasks/all";
    }

    @GetMapping("/404")
    public String errorView(Model model, @RequestParam(name = "fail", required = false) Boolean fail) {
        model.addAttribute("fail", fail != null);
        return "/tasks/404";
    }
}
