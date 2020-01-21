package fi.tuni.tamk.objectOrientedProgramming.shoppingListApp;

import fi.tuni.tamk.objectOrientedProgramming.myJson.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Contains all necessary functions of the GUI, including buttons and data handling.
 */
public class ShoppingList extends Application {
    static ObservableList<ShoppingListItem> data = FXCollections.observableArrayList();;
    static DatabaseConnection dbConnection = null;

    /**
     * Initializes a JavaFX window and creates its components.
     * @param stage Primary stage created automatically on program start.
     */
    @Override
    public void start(Stage stage) {
        // Basic stage configuration.
        stage.setTitle("Shopping List");
        stage.setWidth(298);
        stage.setHeight(547);
        stage.setMinWidth(298);
        stage.setMinHeight(547);

        // Product column configuration.
        TableColumn productCol = new TableColumn("Product");
        productCol.setPrefWidth(165);
        productCol.setCellValueFactory(new PropertyValueFactory<ShoppingListItem, String>("product"));
        productCol.setStyle("-fx-alignment: CENTER-LEFT;");

        // Amount column configuration.
        TableColumn amountCol = new TableColumn("Amount");
        amountCol.setPrefWidth(65);
        amountCol.setCellValueFactory(new PropertyValueFactory<ShoppingListItem, String>("amount"));
        amountCol.setStyle("-fx-alignment: CENTER;");

        // Creating a list to store the table items.
        TableView<ShoppingListItem> table = new TableView<>();
        table.setItems(data);
        table.getColumns().addAll(productCol, amountCol);
        table.setEditable(true);

        TableColumn<ShoppingListItem, Void> buttonCol = new TableColumn();
        buttonCol.setPrefWidth(30);
        buttonCol.setStyle( "-fx-alignment: CENTER;");

        // Adds a red 'x' after every row, which can be clicked to remove that row.
        Callback<TableColumn<ShoppingListItem, Void>, TableCell<ShoppingListItem, Void>> cellFactory =
                new Callback<TableColumn<ShoppingListItem, Void>, TableCell<ShoppingListItem, Void>>() {
            @Override
            public TableCell<ShoppingListItem, Void> call(final TableColumn<ShoppingListItem, Void> param) {
                final TableCell<ShoppingListItem, Void> cell = new TableCell<ShoppingListItem, Void>() {
                    private final Hyperlink removeField = new Hyperlink();
                    {
                        removeField.setText("X");
                        removeField.setTextFill(Color.RED);
                        removeField.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                ShoppingListItem item = getTableView().getItems().get(getIndex());
                                data.remove(item);
                            }
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(removeField);
                        }
                    }
                };
                return cell;
            }
        };
        buttonCol.setCellFactory(cellFactory);

        table.getColumns().add(buttonCol);

        // Controls for adding a new item.
        final TextField addProduct = new TextField();
        addProduct.setPromptText("Product");
        addProduct.setPrefWidth(productCol.getPrefWidth() - 2.5);

        final TextField addAmount = new TextField();
        addAmount.setPromptText("#");
        addAmount.setPrefWidth(amountCol.getPrefWidth() - 5);

        final Button addButton = new Button("+");
        addButton.setTextFill(Color.GREEN);
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (!addProduct.getText().trim().equals("") && !addAmount.getText().trim().equals("")) {
                    try {
                        Integer.parseInt(addAmount.getText());
                        data.add(new ShoppingListItem(addProduct.getText(), addAmount.getText()));
                        addProduct.clear();
                        addAmount.clear();
                    } catch (NumberFormatException exception) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Amount Error");
                        alert.setHeaderText(null);
                        alert.setContentText("Amount must be a number!");
                        alert.showAndWait();
                    }
                }
            }
        });
        addButton.setPrefWidth(buttonCol.getPrefWidth() - 2.5);

        HBox hbox = new HBox();
        hbox.setSpacing(5);
        hbox.getChildren().addAll(addProduct, addAmount, addButton);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(table, hbox);

        // Controls for saving shopping lists and loading them.
        final Button saveButton = new Button("Save (Local)");
        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                MyJsonObject obj = new MyJsonObject();

                for (ShoppingListItem sItem : data) {
                    String key = sItem.getProduct();
                    MyJsonElement value = new MyJsonPrimitive(Integer.parseInt(sItem.getAmount()));
                    MyJsonValuePair vPair = new MyJsonValuePair(key, value);
                    obj.add(vPair);
                }

                saveFile(obj, stage);
            }
        });
        saveButton.setMinWidth(127.5);

        final Button loadButton = new Button("Load (Local)");
        loadButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ArrayList<MyJsonElement> values = null;

                try {
                    values = loadFile(stage).getValues();
                } catch (NullPointerException e) {}

                if (values != null) {
                    for (MyJsonElement e : values) {
                        try {
                            String key = ((MyJsonValuePair) e).getKey();
                            String value = ((MyJsonPrimitive) ((MyJsonValuePair) e).getValue()).asString();
                            ShoppingListItem item = new ShoppingListItem(key, value);
                            data.add(item);
                        } catch (ClassCastException exception) {
                            data.clear();

                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error #FL2");
                            alert.setHeaderText("ClassCastException");
                            alert.setContentText("Your file contains data unsupported by the app.");

                            exception.printStackTrace();
                            alert.showAndWait();
                            break;
                        }
                    }
                }
            }
        });
        loadButton.setMinWidth(127.5);

        HBox saveLoadBox = new HBox();
        saveLoadBox.setSpacing(5);
        saveLoadBox.getChildren().addAll(saveButton, loadButton);

        HBox databaseBox = new HBox();
        databaseBox.setSpacing(5);

        final Button connectToDatabaseButton = new Button("Connect to H2 Database");
        connectToDatabaseButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    dbConnection = new DatabaseConnection();

                    if (dbConnection != null) {
                        databaseBox.getChildren().clear();
                        databaseBox.getChildren().addAll(databaseConnected());
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Unable to Connect");
                    alert.setHeaderText("Connection to an H2 database failed.");
                    alert.setContentText("Please make sure the process is running.");
                    alert.showAndWait();
                }
            }
        });
        connectToDatabaseButton.setMinWidth(260);

        databaseBox.getChildren().add(connectToDatabaseButton);

        vbox.getChildren().addAll(saveLoadBox, databaseBox);

        Group group = new Group();
        group.getChildren().addAll(vbox);

        Scene scene = new Scene(group);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * Opens a save dialog and allows the saving of the shopping list in a .json format.
     * @param obj The list converted into a single json object.
     * @param stage Current stage in use.
     */
    private void saveFile(MyJsonObject obj, Stage stage) {
        FileChooser fChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
        fChooser.getExtensionFilters().add(extFilter);

        File file = fChooser.showSaveDialog(stage);

        if (file != null) {
            saveTextToFile(obj.toJson(new MyJsonWriter()), file);
        }
    }

    /**
     * Writes the json object contents into a file.
     * @param content The content of the json object in a String format.
     * @param file File to be written into.
     */
    private void saveTextToFile(String content, File file) {
        try (PrintWriter writer = new PrintWriter(file)) {
            writer.println(content);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error #FS1");
            alert.setHeaderText("IOException");
            alert.setContentText("Unexpected error occurred while saving the file.");

            e.printStackTrace();
            alert.showAndWait();
        }
    }

    /**
     * Opens a load dialog and allows the user to open a previously saved list.
     * @param stage Current stage in use.
     * @return The contents of the chosen file as a json object.
     */
    private MyJsonObject loadFile(Stage stage) {
        boolean confirmation = true;

        if (!data.isEmpty()) {
            confirmation = overwriteAlert();
        }

        if (confirmation) {
            FileChooser fChooser = new FileChooser();
            fChooser.setTitle("Open JSON File");

            File file = fChooser.showOpenDialog(stage);
            if (file != null) {
                try {
                    String oneLine = MyJsonReader.fileToOneLine(file);
                    MyJsonObject result = MyJsonParser.readAndParse(oneLine);
                    return result;
                } catch (IOException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error #FL1");
                    alert.setHeaderText("IOException");
                    alert.setContentText("Unexpected error occurred while opening the file.");

                    e.printStackTrace();
                    alert.showAndWait();
                }
            }
        }

        return null;
    }

    /**
     * Creates buttons to replace the "Connect to the Database" button.
     *
     * Once a connection to the database has been established, removes the "Connect" button and replaces it with
     * save and load buttons.
     *
     * @return "Save to Database" and "Load from Database" buttons.
     */
    public Node[] databaseConnected() {
        Node[] toReturn = new Node[2];

        final Button saveToDatabaseButton = new Button("Save (Database)");
        saveToDatabaseButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dbConnection.saveToDatabase();
            }
        });
        saveToDatabaseButton.setMinWidth(127.5);
        toReturn[0] = saveToDatabaseButton;

        final Button loadFromDatabaseButton = new Button("Load (Database)");
        loadFromDatabaseButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                boolean confirmation = true;

                if (!data.isEmpty()) {
                    confirmation = overwriteAlert();
                }

                if (confirmation) {
                    data.clear();
                    dbConnection.loadFromDatabase();
                }
            }
        });
        loadFromDatabaseButton.setMinWidth(127.5);
        toReturn[1] = loadFromDatabaseButton;

        return toReturn;
    }

    /**
     * Shows an alert if attempting to load a list while there are items currently on the list.
     *
     * @return True if overwriting is allowed, False if not.
     */
    public boolean overwriteAlert() {
        Alert overwriteAlert = new Alert(Alert.AlertType.CONFIRMATION);
        overwriteAlert.setTitle("Overwrite Alert");
        overwriteAlert.setHeaderText("Loading a shopping list will clear all items " +
                "from the current list.");
        overwriteAlert.setContentText("Are you sure?");

        Optional<ButtonType> result = overwriteAlert.showAndWait();
        if (result.get() == ButtonType.OK){
            data.clear();
            return true;
        } else {
            overwriteAlert.close();
            return false;
        }
    }

    /**
     * Starts the JavaFX app and passes any command-line arguments.
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        ShoppingList.launch(args);
    }
}
