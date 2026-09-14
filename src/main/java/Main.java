import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.HashMap;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.control.ListCell;

public class Main extends Application {

    @Override
    public void start(Stage stage) {

        HashMap<String, Integer> priorityOrder = new HashMap<>();
        priorityOrder.put("High", 1);
        priorityOrder.put("Medium", 2);
        priorityOrder.put("Low", 3);

        DateTimeFormatter dateFormatter =
                DateTimeFormatter.ofPattern("MMM dd, yyyy");

        TaskManager manager = new TaskManager(new PostgresTaskRepository());

        ListView<Task> taskList = new ListView<>(manager.getTasks());

        ObservableList<Task> searchResults = FXCollections.observableArrayList();

        taskList.setCellFactory(listView -> new ListCell<Task>() {
            @Override
            protected void updateItem(Task task, boolean empty) {
                super.updateItem(task, empty);

                if (empty || task == null) {
                    setGraphic(null);
                    setText(null);
                }
                else
                {
                    Text titleText = new Text(task.getTitle());
                    Text priorityText = new Text(task.getPriority());
                    Text dateText = new Text(task.getDueDate().format(dateFormatter));
                    Text statusText = new Text(task.getStatus());

                    titleText.setWrappingWidth(180);
                    priorityText.setWrappingWidth(80);
                    dateText.setWrappingWidth(120);
                    statusText.setWrappingWidth(100);

                    if (task.getPriority().equals("High"))
                        priorityText.setStyle("-fx-fill: red;");
                    else if(task.getPriority().equals("Medium"))
                        priorityText.setStyle("-fx-fill: orange;");
                    else
                        priorityText.setStyle("-fx-fill: green;");


                    if (task.getStatus().equals("OVERDUE"))
                        statusText.setStyle("-fx-fill: red;");
                    else if (task.getStatus().equals("DUE TODAY"))
                        statusText.setStyle("-fx-fill: orange;");
                    else if (task.getStatus().equals("COMPLETED"))
                    {
                        statusText.setStyle("-fx-fill: gray;");
                        statusText.setStrikethrough(true);
                    }
                    else
                        statusText.setStyle("-fx-fill: green;");

                    GridPane taskText = new GridPane();
                    taskText.setHgap(15);

                    ColumnConstraints titleColumn = new ColumnConstraints();
                    titleColumn.setPrefWidth(250);

                    ColumnConstraints priorityColumn = new ColumnConstraints();
                    priorityColumn.setPrefWidth(100);

                    ColumnConstraints dateColumn = new ColumnConstraints();
                    dateColumn.setPrefWidth(130);

                    ColumnConstraints statusColumn = new ColumnConstraints();
                    statusColumn.setPrefWidth(120);

                    taskText.getColumnConstraints().addAll(
                            titleColumn,
                            priorityColumn,
                            dateColumn,
                            statusColumn);

                    taskText.add(titleText, 0, 0);
                    taskText.add(priorityText, 1, 0);
                    taskText.add(dateText, 2, 0);
                    taskText.add(statusText, 3, 0);

                    setGraphic(taskText);
                    setText(null);
                }
            }
        });

        taskList.setPrefHeight(300);
        taskList.setPrefWidth(600);

        Label titleHeader = new Label("TITLE");
        Label priorityHeader = new Label("PRIORITY");
        Label dateHeader = new Label("DUE DATE");
        Label statusHeader = new Label("STATUS");

        titleHeader.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        priorityHeader.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        dateHeader.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        statusHeader.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        GridPane header = new GridPane();
        header.setHgap(15);
        header.setPadding(new Insets(0, 0, 5, 0));

        ColumnConstraints headerTitle = new ColumnConstraints();
        headerTitle.setPrefWidth(250);

        ColumnConstraints headerPriority = new ColumnConstraints();
        headerPriority.setPrefWidth(100);

        ColumnConstraints headerDate = new ColumnConstraints();
        headerDate.setPrefWidth(130);

        ColumnConstraints headerStatus = new ColumnConstraints();
        headerStatus.setPrefWidth(120);

        header.getColumnConstraints().addAll(
                headerTitle,
                headerPriority,
                headerDate,
                headerStatus
        );

        header.add(titleHeader, 0, 0);
        header.add(priorityHeader, 1, 0);
        header.add(dateHeader, 2, 0);
        header.add(statusHeader, 3, 0);

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));

        HBox buttons = new HBox(10);
        HBox taskInput = new HBox(10);
        HBox searchRow = new HBox(10);
        HBox sortRow = new HBox(10);

        Label label = new Label("Task: ");
        Label messageLabel = new Label();

        TextField textField = new TextField();
        TextField searchField = new TextField();

        DatePicker datePicker = new DatePicker();

        textField.setPromptText("Enter title...");
        datePicker.setValue(LocalDate.now());
        searchField.setPromptText("Search tasks...");


        ComboBox<String> priorityBox = new ComboBox<>();
        priorityBox.getItems().addAll("Low", "Medium", "High");
        priorityBox.setValue("Medium");

        ComboBox<String> prioritySearch = new ComboBox<>();
        prioritySearch.getItems().addAll("All", "Low", "Medium", "High");
        prioritySearch.setValue("All");

        ComboBox<String> statusSearch = new ComboBox<>();
        statusSearch.getItems().addAll("All", "UPCOMING", "DUE TODAY", "OVERDUE", "COMPLETED");
        statusSearch.setValue("All");

        ComboBox<String> sortBox = new ComboBox<>();
        sortBox.getItems().addAll("Due Date", "Title", "Status", "Priority");
        sortBox.setPromptText("Sort by...");

        // button initializers
        Button addButton = new Button("Add");
        Button deleteButton = new Button("Delete");
        Button completeButton = new Button("Complete");
        Button editButton = new Button("Edit");
        Button saveButton = new Button("Save");
        Button searchButton = new Button("Search");
        Button clearSearchButton = new Button("Clear Search");
        Button sortButton = new Button("Sort");

        // defines behavior for 'add' button
        addButton.setOnAction(event -> {

            String title = textField.getText();
            String priority = priorityBox.getValue();
            LocalDate dueDate = datePicker.getValue();

            // input validation
            if (title.isBlank())
            {
                messageLabel.setText("Please enter a task title.");
                return;
            }
            else if (priority == null)
            {
                messageLabel.setText("Please enter a valid priority.");
                return;
            }
            else if (dueDate == null)
            {
                messageLabel.setText("Please enter a valid due date.");
                return;
            }

            try {
                manager.addTask(
                        textField.getText(),
                        priorityBox.getValue(),
                        datePicker.getValue()
                );
                messageLabel.setText("Task added successfully.");

                textField.clear();
                priorityBox.setValue("Medium");
                datePicker.setValue(LocalDate.now());
            }

            catch (DatabaseException e)
            {
                messageLabel.setText(
                        "Could not save task. Please try again."
                );
            }
        });


        // defines behavior for 'delete' button
        deleteButton.setOnAction(event -> {
            Task selectedTask = taskList.getSelectionModel().getSelectedItem();

            try {
                if (selectedTask != null) {
                    manager.deleteTask(selectedTask);
                    searchResults.remove(selectedTask);
                    messageLabel.setText("Task deleted.");
                } else {
                    messageLabel.setText("Please select a task to delete.");
                }
            }

            catch (DatabaseException e)
            {
                messageLabel.setText(
                        "Could not delete task. Please try again.");
            }
        });

        // defines behavior for 'complete' button
        completeButton.setOnAction(event -> {
                    Task selectedTask = taskList.getSelectionModel().getSelectedItem();

                    if (selectedTask != null) {
                        manager.completeTask(selectedTask);
                        taskList.refresh();
                        messageLabel.setText("Task completed.");
                    }
                    else
                        messageLabel.setText("Please select a task.");
        });

        // defines behavior for 'edit' button
        editButton.setOnAction(event -> {
            Task selectedTask = taskList.getSelectionModel().getSelectedItem();

            try {
                if (selectedTask != null) {
                    textField.setText(selectedTask.getTitle());
                    priorityBox.setValue(selectedTask.getPriority());
                    datePicker.setValue(selectedTask.getDueDate());
                } else
                    messageLabel.setText("Please select a task to edit.");
            }
            catch (DatabaseException e)
            {
                messageLabel.setText(
                        "Could not complete task. Please try again.");
            }
        });

        // defines behavior for 'save' button
        saveButton.setOnAction(event -> {
            Task selectedTask = taskList.getSelectionModel().getSelectedItem();

            if (selectedTask != null)
            {
                // input validation
                if (textField.getText().isBlank())
                {
                    messageLabel.setText("Please enter a task title.");
                    return;
                }
                if (priorityBox.getValue() == null)
                {
                    messageLabel.setText("Please enter a valid priority.");
                    return;
                }
                if (datePicker.getValue() == null)
                {
                    messageLabel.setText("Please enter a valid due date.");
                    return;
                }
                try {
                    manager.updateTask(selectedTask, textField.getText(), priorityBox.getValue(), datePicker.getValue());
                    taskList.refresh();
                    textField.clear();
                    messageLabel.setText("Task edited.");
                }
                catch (DatabaseException e)
                {
                    messageLabel.setText(
                            "Could not update task. Please try again.");
                }
            }
            else
                messageLabel.setText("Please select task to save.");

        });

        // defines behavior for 'search' button
        searchButton.setOnAction(event -> {
            searchResults.setAll(
                    manager.searchTasks(
                            searchField.getText(),
                            prioritySearch.getValue(),
                            statusSearch.getValue()
                    )
            );

            taskList.setItems(searchResults);
        });

        // defines behavior for 'clearSearch' button
        clearSearchButton.setOnAction(event -> {
            taskList.setItems(manager.getTasks());
            searchField.clear();
            prioritySearch.setValue("All");
            statusSearch.setValue("All");
            messageLabel.setText("Search cleared.");
        });

        // defines behavior for 'sort' button
        sortButton.setOnAction(event -> {
            Comparator<Task> dueDateComparator = (task1, task2) -> task1.getDueDate().compareTo(task2.getDueDate());
            Comparator<Task> titleComparator = (task1, task2) -> task1.getTitle().compareTo(task2.getTitle());
            Comparator<Task> statusComparator = (task1, task2) -> task1.getStatus().compareTo(task2.getStatus());
            Comparator<Task> priorityComparator = (task1, task2) -> priorityOrder.get(task1.getPriority()).compareTo(priorityOrder.get(task2.getPriority()));

            if (sortBox.getValue() == null) {
                messageLabel.setText("Please select a sorting mode.");
                return;
            }
            if (sortBox.getValue().equals("Due Date"))
                taskList.getItems().sort(dueDateComparator);
            else if (sortBox.getValue().equals("Title"))
                taskList.getItems().sort(titleComparator);
            else if (sortBox.getValue().equals("Status"))
                taskList.getItems().sort(statusComparator);
            else
                taskList.getItems().sort(priorityComparator);
        });

        // add components to HBox's
        buttons.getChildren().addAll(
                addButton,
                editButton,
                saveButton,
                deleteButton,
                completeButton);

        taskInput.getChildren().addAll(
                label,
                textField,
                priorityBox,
                datePicker);

        searchRow.getChildren().addAll(
                searchField,
                searchButton,
                clearSearchButton,
                prioritySearch,
                statusSearch);

        sortRow.getChildren().addAll(
                sortBox,
                sortButton);


        // adds all HBox's to the VBox
        layout.getChildren().addAll(
                taskInput,
                buttons,
                searchRow,
                sortRow,
                header,
                taskList,
                messageLabel);

        // position each row in the center
        buttons.setAlignment(Pos.CENTER);
        taskInput.setAlignment(Pos.CENTER);
        searchRow.setAlignment(Pos.CENTER_LEFT);

        // position entire layout center vertically
        layout.setAlignment(Pos.CENTER);



        Scene scene = new Scene(layout, 700, 500);

        stage.setTitle("Task Manager");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}