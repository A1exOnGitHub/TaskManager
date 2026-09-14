import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PostgresRepositoryTest
{
    @Test
    void postgresRepositoryShouldUpdateTask()
    {
        PostgresTaskRepository repository =
                new PostgresTaskRepository();

        Task task = new Task(
                "Original Title",
                "Low",
                LocalDate.now().plusDays(3)
        );

        repository.addTask(task);

        List<Task> tasks = repository.getAllTasks();
        Task storedTask = tasks.get(tasks.size() - 1);

        storedTask.setTitle("Updated Title");
        storedTask.setPriority("High");
        storedTask.setDueDate(LocalDate.now().plusDays(7));
        storedTask.setCompleted(true);

        repository.updateTask(storedTask);

        List<Task> updatedTasks = repository.getAllTasks();

        Task updatedTask = updatedTasks.stream()
                .filter(t -> t.getId() == storedTask.getId())
                .findFirst()
                .orElse(null);

        assertNotNull(updatedTask);
        assertEquals("Updated Title", updatedTask.getTitle());
        assertEquals("High", updatedTask.getPriority());
        assertEquals(LocalDate.now().plusDays(7), updatedTask.getDueDate());
        assertTrue(updatedTask.isCompleted());

        repository.deleteTask(updatedTask);
    }
}