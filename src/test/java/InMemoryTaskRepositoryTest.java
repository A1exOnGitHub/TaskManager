import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class InMemoryTaskRepositoryTest
{
    @Test
    void repositoryShouldStoreTask()
    {
        InMemoryTaskRepository repository =
                new InMemoryTaskRepository();

        Task task = new Task(
                "Study Java",
                "High",
                LocalDate.now()
        );

        repository.addTask(task);

        assertEquals(1, repository.getAllTasks().size());
        assertEquals(task, repository.getAllTasks().get(0));
    }

    @Test
    void repositoryShouldDeleteTask()
    {
        InMemoryTaskRepository repository = new InMemoryTaskRepository();

        Task task = new Task("Study Java", "High", LocalDate.now());

        repository.addTask(task);

        repository.deleteTask(task);

        assertTrue(repository.getAllTasks().isEmpty());
    }

    @Test
    void repositoryShouldReflectUpdatedTask()
    {
        InMemoryTaskRepository repository =
                new InMemoryTaskRepository();

        Task task = new Task(
                "Study Java",
                "High",
                LocalDate.now()
        );

        repository.addTask(task);

        task.setTitle("Do Homework");
        task.setPriority("Medium");

        repository.updateTask(task);

        Task storedTask = repository.getAllTasks().get(0);

        assertEquals("Do Homework", storedTask.getTitle());
        assertEquals("Medium", storedTask.getPriority());
    }
}
