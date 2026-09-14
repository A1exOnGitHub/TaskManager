import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalDate;

public class TaskManager {

    private TaskRepository repository;
    private ObservableList<Task> tasks;

    public TaskManager(TaskRepository repository) {
        this.repository = repository;
        tasks = FXCollections.observableArrayList(repository.getAllTasks());
    }

    public ObservableList<Task> getTasks() {
        return tasks;
    }

    public void addTask(String title, String priority, LocalDate dueDate)
    {
        Task task = new Task(title, priority, dueDate);
        repository.addTask(task);
        tasks.add(task);
    }

    public void deleteTask(Task task)
    {
        repository.deleteTask(task);
        tasks.remove(task);
    }

    public void updateTask(Task task, String title, String priority, LocalDate dueDate)
    {
        task.setTitle(title);
        task.setPriority(priority);
        task.setDueDate(dueDate);

        repository.updateTask(task);
    }

    public ObservableList<Task> searchTasks(String searchTitle)
    {
        ObservableList<Task> searchResults = FXCollections.observableArrayList();

        for (Task task : tasks)
            if (task.getTitle().toLowerCase().contains(searchTitle.toLowerCase()))
                searchResults.add(task);

        return searchResults;
    }

    public ObservableList<Task> searchTasks(String searchText, String priority, String status)
    {
        ObservableList<Task> results = FXCollections.observableArrayList();

        for (Task task : tasks)
        {
            boolean titleMatch =
                    task.getTitle().toLowerCase()
                            .contains(searchText.toLowerCase());

            boolean priorityMatch =
                    priority.equals("All") ||
                            task.getPriority().equals(priority);

            boolean statusMatch =
                    status.equals("All") ||
                            task.getStatus().equals(status);

            if (titleMatch && priorityMatch && statusMatch)
            {
                results.add(task);
            }
        }

        return results;
    }

    public void completeTask(Task task)
    {
        task.setCompleted(true);

        repository.updateTask(task);
    }


}