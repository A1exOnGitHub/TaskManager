import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class Update
{

    @Test
    void newTaskShouldBeRenamed()
    {
        Task task = new Task("Do Homework",
                "High",
                LocalDate.now());

        task.setTitle("Study Java");

        assertEquals("Study Java", task.getTitle());
    }

    @Test
    void newTaskShouldBeReprioritized()
    {
        Task task = new Task("Study Java",
                "Low",
                LocalDate.now());

        task.setPriority("High");

        assertEquals("High", task.getPriority());
    }

    @Test
    void newTaskShouldUpdateDueDate()
    {
        Task task = new Task("Study Java",
                "High",
                LocalDate.now().minusDays(3));

        task.setDueDate(task.getDueDate().plusDays(3));

        assertEquals(LocalDate.now(), task.getDueDate());
    }

    @Test
    void newTaskShouldNotBeCompleted()
    {
        Task task = new Task("Study Java",
                "High",
                LocalDate.now().minusDays(3));

        task.setCompleted(true);
        task.setCompleted(false);

        assertFalse(task.isCompleted());
    }

    @Test // Status updates with due date changes
    void newTaskShouldBecomeOverdue()
    {
        Task task = new Task("Study Java",
                "High",
                LocalDate.now().plusDays(3));

        task.setDueDate(task.getDueDate().minusDays(10));

        assertEquals("OVERDUE", task.getStatus());
    }
}
