package todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import todo.model.Task;
import todo.model.User;
import todo.service.TasksService;
import todo.util.UserName;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@AllArgsConstructor
@Controller
public class TasksController {
    private final TasksService tasksService;

    @GetMapping("/create_task")
    public String createTask(Model model, HttpSession httpSession) {
        UserName.userSessionSetName(model, httpSession);
        model.addAttribute("task",
                new Task());
        return "create_task";
    }

    @PostMapping("/addTask")
    public String addTask(@ModelAttribute Task task, HttpSession httpSession) {
        task.setUser((User) httpSession.getAttribute("user"));
        task.setCreated(LocalDateTime.now());
        task.setDone(false);
        tasksService.add(task);
        return "redirect:/all_tasks";
    }

    @GetMapping("/all_tasks")
    public String allTasks(Model model, HttpSession httpSession) {
        UserName.userSessionSetName(model, httpSession);
        model.addAttribute("tasks", tasksService.findAll());
        return "/all_tasks";
    }

    @GetMapping("/completed_tasks")
    public String completedTasks(Model model, HttpSession httpSession) {
        UserName.userSessionSetName(model, httpSession);
        model.addAttribute("tasks", tasksService.findAllCompletedTasks());
        return "/completed_tasks";
    }

    @GetMapping("/unexecuted_task")
    public String unexecutedTasks(Model model, HttpSession httpSession) {
        UserName.userSessionSetName(model, httpSession);
        model.addAttribute("tasks", tasksService.findAllUnexecutedTask());
        return "/unexecuted_task";
    }

    @GetMapping("/detail_task/{taskId}")
    public String detailTask(Model model, HttpSession httpSession,
                             @PathVariable("taskId") int id) {
        UserName.userSessionSetName(model, httpSession);
        model.addAttribute("task", tasksService.findById(id));
        return "/detail_task";
    }

    @PostMapping("/completeTask/{taskId}")
    public String completedTask(@PathVariable("taskId") int id) {
        tasksService.executeTask(id);
        return "redirect:/all_tasks";
    }

    @PostMapping("/deleteTask/{taskId}")
    public String deletedTask(@PathVariable("taskId") int id) {
        tasksService.deleteTask(id);
        return "redirect:/all_tasks";
    }

    @GetMapping("/formEdit/{taskId}")
    public String formEdit(Model model, HttpSession httpSession, @PathVariable("taskId") int id) {
        UserName.userSessionSetName(model, httpSession);
        model.addAttribute("task", tasksService.findById(id));
        return "formEdit";
    }

    @PostMapping("/updateTask")
    public String updateTask(@ModelAttribute Task task) {
        tasksService.updateTask(task.getId(), task);
        return "redirect:/all_tasks";
    }
}
