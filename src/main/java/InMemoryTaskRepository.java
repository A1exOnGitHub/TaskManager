import java.util.ArrayList;
import java.util.List;

public class InMemoryTaskRepository implements TaskRepository
{
    private List<Task> tasks;

    public InMemoryTaskRepository()
    {
        tasks = new ArrayList<>();
    }

    @Override
    public void addTask(Task task)
    {
        tasks.add(task);
    }

    @Override
    public void deleteTask(Task task)
    {
        tasks.remove(task);
    }

    @Override
    public void updateTask(Task task)
    {

    }

    @Override
    public List<Task> getAllTasks()
    {
        return tasks;
    }
}