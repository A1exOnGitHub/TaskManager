import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Task
{
    private static int nextId = 1;
    private String title;
    private String priority;
    private boolean completed;
    private int id;
    private LocalDate dueDate;
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");

    public Task(String title, String priority, LocalDate dueDate)
    {
        this.title = title;
        this.priority = priority;
        this.completed = false;
        this.id = nextId++;
        this.dueDate = dueDate;
    }

    public Task(int id, String title, String priority, LocalDate dueDate, boolean completed)
    {
        this.id = id;
        this.title = title;
        this.priority = priority;
        this.dueDate = dueDate;
        this.completed = completed;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority)
    {
        this.priority = priority;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public int getId()
    {
        return this.id;
    }

    public LocalDate getDueDate()
    {
        return this.dueDate;
    }

    public void setDueDate(LocalDate dueDate)
    {
        this.dueDate = dueDate;
    }

    public String getStatus()
    {
        LocalDate today = LocalDate.now();

        if (completed)
            return "COMPLETED";
        else if (this.dueDate.isBefore(today))
            return "OVERDUE";
        else if (this.dueDate.equals(today))
            return "DUE TODAY";
        return "UPCOMING";
    }

    @Override
    public String toString()
    {

        return this.id + " | " + this.title + " | " + this.priority + " | " + this.dueDate.format(dateFormatter) + " | " + this.getStatus();
    }
}
