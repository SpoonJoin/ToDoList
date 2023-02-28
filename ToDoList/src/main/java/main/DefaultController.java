package main;

import main.model.Task;
import main.model.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.concurrent.CopyOnWriteArrayList;

@Controller
public class DefaultController
{
    @Autowired
    TaskRepository taskRepository;
    @RequestMapping("/")
    public String index(Model model)
    {
        Iterable<Task> taskIterable = taskRepository.findAll();
        CopyOnWriteArrayList<Task> list = new CopyOnWriteArrayList<>();
        for (Task task : taskIterable) {
            list.add(task);
        }
        model.addAttribute("tasks", list);
        model.addAttribute("tasksCount", list.size());
        return "index";
    }
}
