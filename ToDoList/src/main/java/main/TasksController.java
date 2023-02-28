package main;

import main.model.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import main.model.Task;

import java.sql.Date;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
public class TasksController
{
    @Autowired
    private TaskRepository taskRepository;

    @PostMapping("/tasks/")
    public int add(Task task)
    {
        task.setAddDate(new Date(System.currentTimeMillis()));
        Task newTask = taskRepository.save(task);
        return newTask.getId();
    }

    @GetMapping("/tasks/")
    public CopyOnWriteArrayList<Task> getAllTask()
    {
        Iterable<Task> iterableTasks = taskRepository.findAll();
        CopyOnWriteArrayList<Task> tasks = new CopyOnWriteArrayList<>();
        for (Task t : iterableTasks) {
            tasks.add(t);
        }
        return tasks;
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity get(@PathVariable int id)
    {
        Optional<Task> taskId = taskRepository.findById(id);
        if (taskId.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        return new ResponseEntity(taskId.get(), HttpStatus.OK);
    }

    @DeleteMapping("/tasks/")
    public ConcurrentHashMap<Integer, Task> removeAll()
    {
        taskRepository.deleteAll();
        return new ConcurrentHashMap<>();
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity remove(@PathVariable int id)
    {
        Optional<Task> taskId = taskRepository.findById(id);
        if (taskId.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        CopyOnWriteArrayList<Task> tasks = new CopyOnWriteArrayList<>();
        Iterable<Task> iterableTasks = taskRepository.findAll();
        for (Task task : iterableTasks) {
            tasks.add(task);
        }
        return new ResponseEntity(tasks, HttpStatus.OK);
    }

    @PutMapping("/tasks/{id}")
    public ResponseEntity update(@PathVariable int id, String name)
    {
        Optional<Task> taskId = taskRepository.findById(id);
        if (taskId.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        Task task = taskId.get();
        task.setName(name);
        task.setAddDate(new Date(System.currentTimeMillis()));
        taskRepository.save(task);
        return new ResponseEntity(task, HttpStatus.OK);
    }

    @PutMapping("/tasks/")
    public CopyOnWriteArrayList<Task> updateAll()
    {
        Iterable<Task> iterableTasks = taskRepository.findAll();
        CopyOnWriteArrayList<Task> tasks = new CopyOnWriteArrayList<>();
        for (Task t : iterableTasks) {
            t.setName(t.getName() + " (UPD)");
            t.setAddDate(new Date(System.currentTimeMillis()));
            taskRepository.save(t);
            tasks.add(t);
        }
        return tasks;
    }
}
