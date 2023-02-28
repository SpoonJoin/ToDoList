package main;

import main.model.Task;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class AllTasks
{
    private static int currentId = 1;
    private static ConcurrentHashMap<Integer, Task> allTasks = new ConcurrentHashMap<>();

    public static CopyOnWriteArrayList<Task> getAllTasks() {
        CopyOnWriteArrayList<Task> list = new CopyOnWriteArrayList<>(allTasks.values());
        return list;
    }

    public static int addTask(Task task) {
        task.setAddDate(new Date(System.currentTimeMillis()));
        int id = currentId++;
        task.setId(id);
        allTasks.put(id, task);
        return id;
    }

    public static Task getTask(int id)
    {
        if (allTasks.containsKey(id)) return allTasks.get(id);
        return null;
    }

    public static ConcurrentHashMap<Integer, Task> removeAllTasks()
    {
        allTasks = new ConcurrentHashMap<>();
        return allTasks;
    }

    public static CopyOnWriteArrayList<Task> removeTask(int id)
    {
        if (allTasks.containsKey(id)) allTasks.remove(id);
        CopyOnWriteArrayList<Task> list = new CopyOnWriteArrayList<>(allTasks.values());
        return list;
    }

    public static Task updateTask(int id, String name) {
        if (allTasks.containsKey(id)) {
            allTasks.get(id).setAddDate(new Date(System.currentTimeMillis()));
            allTasks.get(id).setName(name);
            return allTasks.get(id);
        }
        return null;
    }

    public static CopyOnWriteArrayList<Task> updateAll() {
        for (Map.Entry<Integer, Task> entry : allTasks.entrySet())
        {
            Task task = entry.getValue();
            task.setName(task.getName() + " (UPD)");
            task.setAddDate(new Date(System.currentTimeMillis()));
        }
        return new CopyOnWriteArrayList<>(allTasks.values());
    }
}
