import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class TaskManagerTest
{
    @Test
    void taskShouldBeAdded()
    {
        TaskManager manager = new TaskManager(new InMemoryTaskRepository());

        LocalDate dueDate = LocalDate.now().plusDays(3);

        manager.addTask(
                "Study Java",
                "High",
                dueDate);

        assertEquals(1, manager.getTasks().size());

        Task task = manager.getTasks().get(0);

        assertEquals("Study Java", task.getTitle());
        assertEquals("High", task.getPriority());
        assertEquals(dueDate, task.getDueDate());
    }

    @Test
    void taskShouldBeDeleted()
    {
        TaskManager manager = new TaskManager(new InMemoryTaskRepository());

        manager.addTask(
                "Study Java",
                "High",
                LocalDate.now());

        assertEquals(1, manager.getTasks().size());

        manager.deleteTask(manager.getTasks().get(0));

        assertEquals(0, manager.getTasks().size());
    }

    @Test
    void taskShouldBeUpdated()
    {
        TaskManager manager = new TaskManager(new InMemoryTaskRepository());

        LocalDate dueDate = LocalDate.now().plusDays(3);

        manager.addTask(
                "Study Java",
                "High",
                dueDate);

        Task task = manager.getTasks().get(0);

        dueDate = dueDate.minusDays(3);
        manager.updateTask(task, "Do Homework", "Medium", dueDate);

        assertEquals("Do Homework", task.getTitle());
        assertEquals("Medium", task.getPriority());
        assertEquals(dueDate, task.getDueDate());
    }

    @Test
    void taskShouldBeFound()
    {
        TaskManager manager = new TaskManager(new InMemoryTaskRepository());

        LocalDate dueDate = LocalDate.now();

        manager.addTask(
                "Study Java",
                "High",
                dueDate);

        manager.addTask(
                "Do Homework",
                "Medium",
                dueDate);

        ObservableList<Task> results = manager.searchTasks("java");

        assertEquals(1, results.size());
        assertEquals("Study Java", results.get(0).getTitle());
    }

    @Test
    void searchShouldBeCaseInsensitive()
    {
        TaskManager manager = new TaskManager(new InMemoryTaskRepository());

        manager.addTask(
                "Study Java",
                "High",
                LocalDate.now());

        ObservableList<Task> results = manager.searchTasks("JAVA");

        assertEquals(1, results.size());
        assertEquals("Study Java", results.get(0).getTitle());
    }

    @Test
    void searchShouldReturnNoResultsWhenTaskDoesNotExist()
    {
        TaskManager manager = new TaskManager(new InMemoryTaskRepository());

        manager.addTask(
                "Study Java",
                "High",
                LocalDate.now());

        ObservableList<Task> results = manager.searchTasks("Python");

        assertEquals(0, results.size());
    }

    @Test
    void searchShouldFilterByPriority()
    {
        TaskManager manager = new TaskManager(new InMemoryTaskRepository());

        manager.addTask(
                "Study Java",
                "High",
                LocalDate.now());

        manager.addTask(
                "Do Homework",
                "Low",
                LocalDate.now());

        ObservableList<Task> results =
                manager.searchTasks("", "High", "All");

        assertEquals(1, results.size());
        assertEquals("Study Java", results.get(0).getTitle());
    }

    @Test
    void searchShouldFilterByStatus()
    {
        TaskManager manager = new TaskManager(new InMemoryTaskRepository());

        manager.addTask(
                "Overdue Task",
                "High",
                LocalDate.now().minusDays(3));

        manager.addTask(
                "Upcoming Task",
                "Low",
                LocalDate.now().plusDays(3));

        ObservableList<Task> results =
                manager.searchTasks("", "All", "OVERDUE");

        assertEquals(1, results.size());
        assertEquals("Overdue Task", results.get(0).getTitle());
    }

    @Test
    void taskShouldBeCompleted()
    {
        TaskManager manager = new TaskManager(new InMemoryTaskRepository());

        manager.addTask(
                "Study Java",
                "High",
                LocalDate.now());

        Task selectedTask = manager.getTasks().get(0);

        manager.completeTask(selectedTask);

        assertTrue(selectedTask.isCompleted());
    }
}
