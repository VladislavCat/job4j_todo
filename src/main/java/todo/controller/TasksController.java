package todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import todo.service.TasksService;

import javax.servlet.http.HttpSession;

@AllArgsConstructor
@Controller
public class TasksController {
    private final TasksService tasksService;

    @GetMapping("/all_tasks")
    public String allTasks(Model model, HttpSession httpSession) {
        model.addAttribute("tasks", tasksService.findAll());
        return "/all_tasks";
    }

    @GetMapping("/completed_tasks")
    public String completedTasks(Model model, HttpSession httpSession) {
        model.addAttribute("tasks", tasksService.findAllCompletedTasks());
        return "/completed_tasks";
    }

    @GetMapping("/unexecuted_task")
    public String unexecutedTasks(Model model, HttpSession httpSession) {
        model.addAttribute("tasks", tasksService.findAllUnexecutedTask());
        return "/unexecuted_task";
    }

    @GetMapping("/detail_task/{taskId}")
    public String detailTask(Model model, HttpSession httpSession,
                             @PathVariable("taskId") int id) {
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
}
