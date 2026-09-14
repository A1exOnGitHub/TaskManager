import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class Status {

    @Test
    void newTaskShouldBeUpcoming()
    {
        Task task = new Task(
                "Study Java",
                "High",
                LocalDate.now().plusDays(3));

        assertEquals("UPCOMING", task.getStatus());
    }

    @Test
    void newTaskShouldBeOverdue()
    {
        Task task = new Task(
                "Study Java",
                "High",
                LocalDate.now().minusDays(3));


        assertEquals("OVERDUE", task.getStatus());
    }

    @Test
    void newTaskShouldBeDueToday()
    {
        Task task = new Task(
                "Study Java",
                "High",
                LocalDate.now());

        assertEquals("DUE TODAY", task.getStatus());
    }

    @Test
    void newTaskShouldBeCompleted()
    {
        Task task = new Task(
                "Study Java",
                "High",
                LocalDate.now());

        task.setCompleted(true);

        assertEquals("COMPLETED", task.getStatus());
    }
}