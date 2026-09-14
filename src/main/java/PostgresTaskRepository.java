import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class PostgresTaskRepository implements TaskRepository
{
    @Override
    public void addTask(Task task)
    {
        String sql = "INSERT INTO tasks (title, priority, due_date, completed) " +
                "VALUES (?, ?, ?, ?)";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)
                )
        {
            statement.setString(1, task.getTitle());
            statement.setString(2, task.getPriority());
            statement.setDate(3, java.sql.Date.valueOf(task.getDueDate()));
            statement.setBoolean(4, task.isCompleted());

            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();

            if (generatedKeys.next())
            {
                task.setId(generatedKeys.getInt(1));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteTask(Task task)
    {
        String sql = "DELETE FROM tasks " +
                "WHERE id = ?";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
                )
        {
            statement.setInt(1, task.getId());

            statement.executeUpdate();
        }


        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void updateTask(Task task)
    {
        String sql = "UPDATE tasks " +
                "SET title = ?, priority = ?, due_date = ?, completed = ? " +
                "WHERE id = ?";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
                )
        {
            statement.setString(1, task.getTitle());
            statement.setString(2, task.getPriority());
            statement.setDate(3, java.sql.Date.valueOf(task.getDueDate()));
            statement.setBoolean(4, task.isCompleted());
            statement.setInt(5, task.getId());

            statement.executeUpdate();
        }

        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public List<Task> getAllTasks()
    {
        List<Task> tasks = new ArrayList<>();

        String sql = "SELECT id, title, priority, due_date, completed " +
                "FROM tasks";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet results = statement.executeQuery()
                ) {
            while (results.next()) {
                Task task = new Task(
                        results.getInt("id"),
                        results.getString("title"),
                        results.getString("priority"),
                        results.getDate("due_date").toLocalDate(),
                        results.getBoolean("completed"));

                tasks.add(task);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return tasks;
    }
}
