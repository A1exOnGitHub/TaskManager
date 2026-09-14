import java.util.List;

public interface TaskRepository
{
    void addTask(Task task);

    void deleteTask(Task task);

    void updateTask(Task task);

    List<Task> getAllTasks();
}