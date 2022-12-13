package todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import todo.model.Category;
import todo.model.Task;
import todo.model.User;
import todo.service.CategoryService;
import todo.service.PriorityService;
import todo.service.TasksService;
import todo.util.UserName;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@AllArgsConstructor
@Controller
@RequestMapping("/tasks")
public class TaskController {
    private final TasksService tasksService;
    private final PriorityService priorityService;
    private final CategoryService categoryService;

    @GetMapping("/create")
    public String createTask(Model model, HttpSession httpSession) {
        UserName.userSessionSetName(model, httpSession);
        model.addAttribute("priorities", priorityService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("task",
                new Task());
        return "tasks/create";
    }

    @PostMapping("/add")
    public String addTask(HttpSession httpSession, @RequestParam("categoryId") List<Integer> categoryId,
                          @ModelAttribute Task task, HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<Category> categories = new ArrayList<>();
        for (int i : categoryId) {
            categories.add(categoryService.findById(i).orElseThrow(() -> {
                return new NoSuchElementException("Категория не найдена " + i);
            }));
        }
        task.setCategories(categories);
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
        Optional<Task> taskOptional = tasksService.findById(id);
        if (taskOptional.isEmpty()) {
            model.addAttribute("message", "Данная задача не найдена");
            return "redirect:/404?fail=true";
        }
        model.addAttribute("task", taskOptional.get());
        return "tasks/detail";
    }

    @PostMapping("/complete/{Id}")
    public String completedTask(Model model, @PathVariable("Id") int id) {
        boolean isCompleted = tasksService.executeTask(id);
        if (!isCompleted) {
            model.addAttribute("message", "Состояние не было изменено!");
            return "redirect:/404?fail=true";
        }
        return "redirect:/tasks/all";
    }

    @PostMapping("/delete/{Id}")
    public String deletedTask(Model model, @PathVariable("Id") int id) {
        boolean isDeleted = tasksService.deleteTask(id);
        if (!isDeleted) {
            model.addAttribute("message", "Состояние не было изменено!");
            return "redirect:/404?fail=true";
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
        boolean isUpdated = tasksService.updateTask(task.getId(), task);
        if (!isUpdated) {
            model.addAttribute("message", "Состояние не было изменено!");
            return "redirect:/404?fail=true";
        }
        return "redirect:/tasks/all";
    }

    @GetMapping("/404")
    public String errorView(Model model, @RequestParam(name = "fail", required = false) Boolean fail) {
        model.addAttribute("fail", fail != null);
        return "/404";
    }
}
