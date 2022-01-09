package pt.pa.view;

import com.brunomnsilva.smartgraph.graphview.SmartGraphVertex;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.w3c.dom.Text;
import pt.pa.graph.Edge;
import pt.pa.model.*;
import pt.pa.model.exceptions.ExistingHubException;
import pt.pa.model.exceptions.ExistingRouteException;
import pt.pa.graph.Vertex;
import pt.pa.model.exceptions.IncorrectFieldException;
import pt.pa.view.strategy.ElementInfoHubStrategy;
import pt.pa.view.strategy.ElementInfoNoneStrategy;
import pt.pa.view.strategy.ElementInfoRouteStrategy;

import java.awt.*;
import java.util.*;
import java.util.List;
import javafx.geometry.Insets;

public class NetworkEventHandler {

    private NetworkController controller;
    private NetworkUI ui;

    public NetworkEventHandler(NetworkUI ui) {
        this.ui = ui;
        this.controller = this.ui.getController();

    }

    public void removeHandler(MenuItem menuItem) {
        menuItem.setOnAction(null);
    }

    private TextField createField(VBox vbox, String field) {
        HBox hbox = new HBox();
        Label label = new Label(field);
        TextField textField = new TextField();
        hbox.getChildren().addAll(label,textField);
        hbox.setSpacing(10);
        vbox.getChildren().add(hbox);
        return textField;
    }

    public void createElementsInfoEvent() {
        controller.getGraphView().setOnMousePressed(event ->  {
            ui.getElementInfoBar().setElementInfoStrategy(new ElementInfoNoneStrategy());
            ui.getElementInfoBar().setElement(null, controller.getManager());
            controller.getGraphView().setEdgeDoubleClickAction(graphEdge -> {
                ui.getElementInfoBar().setElementInfoStrategy(new ElementInfoRouteStrategy());
                ui.getElementInfoBar().setElement(graphEdge.getUnderlyingEdge().element(),controller.getManager());
            });
            controller.getGraphView().setVertexDoubleClickAction((SmartGraphVertex<Hub> graphVertex) -> {
                ui.getElementInfoBar().setElementInfoStrategy(new ElementInfoHubStrategy());
                ui.getElementInfoBar().setElement(graphVertex.getUnderlyingVertex().element(),controller.getManager());
            });
        });
    }

    public void disableElementsInfoEvent() {
        controller.getGraphView().setOnMousePressed(null);
    }

    // Evento - Criar Hub
    public void createHubEvent(MenuItem menuItem) {
        menuItem.setOnAction(actionEvent1 -> {
            ui.getMenuBar().setDisable(true);
            defaultStyling();
            Circle puppet = new Circle(5);
            puppet.getStyleClass().add("vertex");
            controller.getGraphView().getChildren().add(puppet);
            controller.getGraphView().setOnMouseMoved(mouseMovedEvent -> {
                puppet.setCenterX(mouseMovedEvent.getSceneX());
                if (mouseMovedEvent.getSceneY() < 25 + puppet.getRadius())
                    puppet.setCenterY(puppet.getRadius());
                else if (mouseMovedEvent.getSceneY() > 735 - puppet.getRadius())
                    puppet.setCenterY(715 - puppet.getRadius());
                else
                    puppet.setCenterY(mouseMovedEvent.getSceneY() - 25);

            });
            controller.getGraphView().setOnMouseClicked(mouseClickedEvent -> {
                ui.getMenuBar().setDisable(false);
                Point coordinates = new Point((int) puppet.getCenterX(), (int) puppet.getCenterY());
                controller.getGraphView().setOnMouseMoved(null);
                controller.getGraphView().setOnMouseClicked(null);
                controller.getGraphView().enableDoubleClickListener();
                createElementsInfoEvent();

                final Stage dialog = new Stage();
                dialog.setResizable(false);
                dialog.setTitle("New Hub");
                dialog.initModality(Modality.APPLICATION_MODAL);
                dialog.initOwner(controller.getStage());
                VBox dialogVBox = new VBox(10);
                dialogVBox.setPadding(new Insets(10, 10, 10, 10));
                TextField hubCityNameTextField = createField(dialogVBox, "City Name:\t");
                TextField hubPopulationTextField = createField(dialogVBox, "Population:\t");
                HBox buttonsHBox = new HBox(20);
                Button createHubButton = new Button("Create Hub");
                buttonsHBox.setPadding(new Insets(0, 0, 0, 162));
                buttonsHBox.getChildren().addAll(createHubButton);
                dialogVBox.getChildren().add(buttonsHBox);
                Label errorMsg = new Label();
                errorMsg.setStyle("-fx-text-fill: red;");
                dialogVBox.getChildren().add(errorMsg);
                Scene dialogScene = new Scene(dialogVBox,270,140);
                dialogScene.getStylesheets().add("events.css");
                dialog.setScene(dialogScene);
                dialog.show();

                dialog.setOnCloseRequest(e -> {
                    controller.getGraphView().getChildren().remove(puppet);
                });

                createHubButton.setOnAction(actionEvent2 -> {
                    // Validations
                    try {
                        defaultTextField(hubCityNameTextField,hubPopulationTextField);
                        if (hubCityNameTextField.getText().trim().isEmpty()) {
                            hubCityNameTextField.getStyleClass().add("incorrect-text-field");
                            throw new IncorrectFieldException("\"City Name\" field is empty!");
                        }else if (hubPopulationTextField.getText().trim().isEmpty()) {
                            hubPopulationTextField.getStyleClass().add("incorrect-text-field");
                            throw new IncorrectFieldException("\"Population\" field is empty!");
                        }else if (controller.getManager().getHub(hubCityNameTextField.getText().trim()) != null) {
                            hubCityNameTextField.getStyleClass().add("incorrect-text-field");
                            throw new ExistingHubException(); //System.out.println("This Hub already exists!");
                        }else if (Integer.valueOf(hubPopulationTextField.getText().trim()) <= 0) {
                            hubPopulationTextField.getStyleClass().add("incorrect-text-field");
                            throw new IncorrectFieldException("\"Population\" should be greater than 0!");
                        }else {
                            Hub hub = new Hub(hubCityNameTextField.getText().trim(),Integer.valueOf(hubPopulationTextField.getText().trim()),coordinates);

                            // Save action
                            Action action = new Action(Operation.INSERT_HUB,hub);
                            ui.getMenuBar().saveAction(action);

                            Vertex<Hub> vertex1 = controller.getManager().createVertex(hub);
                            controller.getGraphView().getChildren().remove(puppet);
                            controller.getGraphView().updateAndWait();
                            controller.getGraphView().setVertexPosition(vertex1,coordinates.getX(),coordinates.getY());
                            dialog.close();
                            System.out.println("Hub created!");
                        }
                    } catch (ExistingHubException exception) {
                        errorMsg.setText(exception.getMessage());
                        System.out.println(exception.getMessage());
                    } catch (IncorrectFieldException incorrectFieldException){
                        errorMsg.setText(incorrectFieldException.getMessage());
                        System.out.println("Invalid field: " + incorrectFieldException.getMessage());
                    } catch (RuntimeException runtimeException) {
                        hubPopulationTextField.getStyleClass().add("incorrect-text-field");
                        errorMsg.setText(runtimeException.getMessage());
                        System.out.println(runtimeException.getMessage());
                    }
                });
            });
        });
    }

    // Evento - Criar Route
    public void createRouteEvent(MenuItem menuItem) {
        menuItem.setOnAction(actionEvent1 -> {
            defaultStyling();
            final Stage dialog = new Stage();
            dialog.setResizable(false);
            dialog.setTitle("New Route");
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(controller.getStage());
            VBox dialogVBox = new VBox(20);
            dialogVBox.setPadding(new Insets(10, 10, 10, 10));
            TextField originHubTextField = createField(dialogVBox, "Origin Hub:\t\t");
            TextField destinationHubTextField = createField(dialogVBox, "Destination Hub:\t");
            TextField distanceTextField = createField(dialogVBox, "Distance:\t\t\t");
            HBox buttonsHBox = new HBox(30);
            buttonsHBox.setPadding(new Insets(0, 0, 0, 170));
            Button createRouteButton = new Button("Create Route");
            buttonsHBox.getChildren().addAll(createRouteButton);
            dialogVBox.getChildren().add(buttonsHBox);
            Label errorMsg = new Label();
            errorMsg.setStyle("-fx-text-fill: red;");
            dialogVBox.getChildren().add(errorMsg);
            Scene dialogScene = new Scene(dialogVBox,300,220);
            dialogScene.getStylesheets().add("events.css");
            dialog.setScene(dialogScene);
            dialog.show();

            createRouteButton.setOnAction(actionEvent2 -> {
                // Validations
                try {
                    defaultTextField(originHubTextField,destinationHubTextField,distanceTextField);
                    if (originHubTextField.getText().trim().isEmpty()) {
                        originHubTextField.getStyleClass().add("incorrect-text-field");
                        throw new IncorrectFieldException("\"Origin Hub\" field is empty!");
                    }else if (controller.getManager().getHub(originHubTextField.getText().trim()) == null) {
                        originHubTextField.getStyleClass().add("incorrect-text-field");
                        throw new IncorrectFieldException("\"Origin Hub\" doesn't exist!");
                    }else if (destinationHubTextField.getText().trim().isEmpty()) {
                        destinationHubTextField.getStyleClass().add("incorrect-text-field");
                        throw new IncorrectFieldException("\"Destination Hub\" field is empty!");
                    }else if (controller.getManager().getHub(destinationHubTextField.getText().trim()) == null) {
                        destinationHubTextField.getStyleClass().add("incorrect-text-field");
                        throw new IncorrectFieldException("\"Destination Hub\" doesn't exist!");
                    }else if (Integer.valueOf(distanceTextField.getText().trim()) <= 0) {
                        distanceTextField.getStyleClass().add("incorrect-text-field");
                        throw new IncorrectFieldException("\"Distance\" should be greater than 0!");
                    }else if (controller.getManager().getRoute(originHubTextField.getText().trim(),destinationHubTextField.getText().trim()) != null) {
                        destinationHubTextField.getStyleClass().add("incorrect-text-field");
                        originHubTextField.getStyleClass().add("incorrect-text-field");
                        throw new ExistingRouteException();
                    }else if (originHubTextField.getText().trim().equals(destinationHubTextField.getText().trim())) {
                        destinationHubTextField.getStyleClass().add("incorrect-text-field");
                        originHubTextField.getStyleClass().add("incorrect-text-field");
                        throw new IncorrectFieldException("\"Origin Hub\" and \"Destination Hub\" can't be the same!");
                    }else {
                        Hub origin = controller.getManager().getHub(originHubTextField.getText().trim());
                        Hub destination = controller.getManager().getHub(destinationHubTextField.getText().trim());
                        Route route = new Route(Integer.valueOf(distanceTextField.getText().trim()));

                        controller.getManager().createEdge(origin,destination,route);

                        // Save action
                        Action action = new Action(Operation.INSERT_ROUTE,controller.getManager().getEdge(route));
                        ui.getMenuBar().saveAction(action);

                        controller.getGraphView().updateAndWait();
                        dialog.close();
                        System.out.println("Route created!");
                    }
                } catch (NumberFormatException numberFormatException){
                    distanceTextField.getStyleClass().add("incorrect-text-field");
                    errorMsg.setText("Distance must be a number!");
                    System.out.println("Distance must be a number!");
                }
                catch (RuntimeException runtimeException) {
                    errorMsg.setText(runtimeException.getMessage());
                    System.out.println(runtimeException.getMessage());
                }
            });
        });
    }

    // Evento - Remover Hub
    public void removeHubEvent(MenuItem menuItem) {
        menuItem.setOnAction(actionEvent1 -> {
            defaultStyling();
            final Stage dialog = new Stage();
            dialog.setResizable(false);
            dialog.setTitle("Remove Hub");
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(controller.getStage());
            VBox dialogVBox = new VBox(20);
            dialogVBox.setPadding(new Insets(10, 10, 10, 10));
            TextField hubTextField = createField(dialogVBox, "Hub:\t");
            hubTextField.setMaxWidth(135);
            HBox buttonsHBox = new HBox();
            Button removeHubButton = new Button("Remove Hub");
            buttonsHBox.setPadding(new Insets(0, 0, 0, 86));
            buttonsHBox.getChildren().addAll(removeHubButton);
            dialogVBox.getChildren().add(buttonsHBox);
            Label errorMsg = new Label();
            errorMsg.setStyle("-fx-text-fill: red;");
            dialogVBox.getChildren().add(errorMsg);
            Scene dialogScene = new Scene(dialogVBox, 203, 123);
            dialogScene.getStylesheets().add("events.css");
            dialog.setScene(dialogScene);
            dialog.show();
            removeHubButton.setOnAction(actionEvent2 -> {
                try {
                    defaultTextField(hubTextField);
                    if (hubTextField.getText().trim().isEmpty())
                        throw new IncorrectFieldException("\"Hub\" field is empty!");
                    else if (controller.getManager().getHub(hubTextField.getText().trim()) == null)
                        throw new IncorrectFieldException("\"Hub\" doesn't exist!");
                    else {
                        Hub hub = controller.getManager().getHub(hubTextField.getText().trim());

                        // Save action
                        Map<Route, Hub> adjacentRoutes = new HashMap<>();
                        Vertex<Hub> vertex = controller.getManager().getVertex(hub);
                        for (Edge edge : controller.getManager().getGraph().incidentEdges(controller.getManager().getVertex(hub)))
                            adjacentRoutes.put((Route) edge.element(), (Hub) controller.getManager().getGraph().opposite(vertex, edge).element());
                        Action action = new Action(Operation.REMOVE_HUB, hub, adjacentRoutes, new ArrayList<>(controller.getManager().getHubs()));
                        ui.getMenuBar().saveAction(action);

                        controller.getManager().removeVertex(hub);
                        controller.getGraphView().updateAndWait();
                        dialog.close();

                        System.out.println("Hub removed!");
                    }
                } catch (IncorrectFieldException incorrectFieldException){
                    hubTextField.getStyleClass().add("incorrect-text-field");
                    errorMsg.setText(incorrectFieldException.getMessage());
                    System.out.println("Invalid field: " + incorrectFieldException.getMessage());
                } catch (RuntimeException exception) {
                    hubTextField.getStyleClass().add("incorrect-text-field");
                    errorMsg.setText(exception.getMessage());
                    System.out.println(exception.getMessage());

                }
            });
        });
    }

    // Evento - Remover Route
    public void removeRouteEvent(MenuItem menuItem) {
        menuItem.setOnAction(actionEvent1 -> {
            defaultStyling();
            final Stage dialog = new Stage();
            dialog.setResizable(false);
            dialog.setTitle("Remove Route");
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(controller.getStage());
            VBox dialogVBox = new VBox(20);
            TextField originHubTextField = createField(dialogVBox, "Origin Hub:\t\t");
            TextField destinationHubTextField = createField(dialogVBox, "Destination Hub:\t");
            dialogVBox.setPadding(new Insets(10, 10, 10, 10));
            HBox buttonsHBox = new HBox(30);
            Button removeRouteButton = new Button("Remove Route");
            buttonsHBox.setPadding(new Insets(0, 0, 0, 170));
            buttonsHBox.getChildren().addAll(removeRouteButton);
            dialogVBox.getChildren().add(buttonsHBox);
            Label errorMsg = new Label();
            errorMsg.setStyle("-fx-text-fill: red;");
            dialogVBox.getChildren().add(errorMsg);
            Scene dialogScene = new Scene(dialogVBox, 300, 170);
            dialogScene.getStylesheets().add("events.css");
            dialog.setScene(dialogScene);
            dialog.show();
            removeRouteButton.setOnAction(actionEvent2 -> {
                try {
                    defaultTextField(originHubTextField,destinationHubTextField);
                    if (originHubTextField.getText().trim().isEmpty()) {
                        originHubTextField.getStyleClass().add("incorrect-text-field");
                        throw new IncorrectFieldException("\"Origin Hub\" field is empty!");
                    }else if (controller.getManager().getHub(originHubTextField.getText().trim()) == null) {
                        originHubTextField.getStyleClass().add("incorrect-text-field");
                        throw new IncorrectFieldException("\"Origin Hub\" doesn't exist!");
                    }else if (destinationHubTextField.getText().trim().isEmpty()) {
                        destinationHubTextField.getStyleClass().add("incorrect-text-field");
                        throw new IncorrectFieldException("\"Destination Hub\" field is empty!");
                    }else if (controller.getManager().getHub(destinationHubTextField.getText().trim()) == null) {
                        destinationHubTextField.getStyleClass().add("incorrect-text-field");
                        throw new IncorrectFieldException("\"Destination Hub\" doesn't exist!");
                    }else if (controller.getManager().getRoute(originHubTextField.getText().trim(),destinationHubTextField.getText().trim()) == null) {
                        originHubTextField.getStyleClass().add("incorrect-text-field");
                        throw new IncorrectFieldException("This route doesn't exist!");
                    }else {
                        Hub origin = controller.getManager().getHub(originHubTextField.getText().trim());
                        Hub destination = controller.getManager().getHub(destinationHubTextField.getText().trim());
                        Route route = controller.getManager().getRoute(origin,destination);

                        // Save action
                        Action action = new Action(Operation.REMOVE_ROUTE,origin,destination,route);
                        ui.getMenuBar().saveAction(action);

                        controller.getManager().removeEdge(route);
                        controller.getGraphView().updateAndWait();
                        dialog.close();
                        System.out.println("Route removed!");
                    }
                } catch (IncorrectFieldException incorrectFieldException){
                    errorMsg.setText(incorrectFieldException.getMessage());
                    System.out.println("Invalid field: " + incorrectFieldException.getMessage());
                } catch (RuntimeException exception) {
                    errorMsg.setText(exception.getMessage());
                    System.out.println(exception.getMessage());
                }
            });
        });
    }

    // Evento - Importar Routes
    public void importRoutesEvent(MenuItem menuItem) {
        menuItem.setOnAction(actionEvent1 -> {
            defaultStyling();
            final Stage dialog = new Stage();
            dialog.setResizable(false);
            dialog.setTitle("Import Routes");
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(controller.getStage());
            VBox dialogVBox = new VBox(20);
            TextField importRoutesField = createField(dialogVBox, "Routes file path:");
            HBox buttonsHBox = new HBox(30);
            dialogVBox.setPadding(new Insets(10, 10, 10, 10));
            Button importRoutesButton = new Button("Import Routes");
            buttonsHBox.setPadding(new Insets(0, 0, 0, 153));
            buttonsHBox.getChildren().addAll(importRoutesButton);
            dialogVBox.getChildren().add(buttonsHBox);
            Label errorMsg = new Label();
            errorMsg.setStyle("-fx-text-fill: red;");
            dialogVBox.getChildren().add(errorMsg);
            Scene dialogScene = new Scene(dialogVBox, 275, 127);
            dialogScene.getStylesheets().add("events.css");
            dialog.setScene(dialogScene);
            dialog.show();
            importRoutesButton.setOnAction(actionEvent2 -> {
                try {
                    defaultTextField(importRoutesField);
                    if (importRoutesField.getText().trim().isEmpty())
                        throw new IncorrectFieldException("Route's file is empty!");
                    else {
                        DatasetReader datasetReader = new DatasetReader(importRoutesField.getText().trim(),controller.getManager().getHubs());
                        controller.getManager().createGraphElements(datasetReader);
                        controller.getGraphView().updateAndWait();
                        controller.setCoordinates();
                        dialog.close();
                        System.out.println("Routes imported!");
                    }
                } catch (IncorrectFieldException incorrectFieldException){
                    importRoutesField.getStyleClass().add("incorrect-text-field");
                    errorMsg.setText(incorrectFieldException.getMessage());
                    System.out.println("Invalid field: " + incorrectFieldException.getMessage());
                } catch (RuntimeException exception) {
                    importRoutesField.getStyleClass().add("incorrect-text-field");
                    errorMsg.setText("Error: "+exception.getMessage());
                    System.out.println(exception.getMessage());
                }
            });
        });
    }

    // Evento - Exportar Routes
    public void exportRoutesEvent(MenuItem menuItem) {
        menuItem.setOnAction(actionEvent1 -> {
            defaultStyling();
            final Stage dialog = new Stage();
            dialog.setResizable(false);
            dialog.setTitle("Export Routes");
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(controller.getStage());
            VBox dialogVBox = new VBox(10);
            HBox buttonsHBox = new HBox(10);
            TextField txLink = new TextField();
            Label lblQuestion = new Label("Are you sure you want to export the routes?");
            dialogVBox.getChildren().add(lblQuestion);
            dialogVBox.setPadding(new Insets(10, 10, 10, 10));
            Button importRoutesButton = new Button("Export Routes");
            buttonsHBox.setPadding(new Insets(0, 0, 0, 80));
            buttonsHBox.getChildren().addAll(importRoutesButton);
            dialogVBox.getChildren().add(buttonsHBox);
            Label errorMsg = new Label();
            errorMsg.setStyle("-fx-text-fill: green;");
            dialogVBox.getChildren().add(errorMsg);
            dialogVBox.getChildren().add(txLink);
            txLink.setEditable(false);
            Scene dialogScene = new Scene(dialogVBox, 280, 140);
            dialog.setScene(dialogScene);
            dialog.show();
            importRoutesButton.setOnAction(actionEvent2 -> {
                try {
                    errorMsg.setText("Route Saved successfully in the path below");
                    txLink.setText(controller.getManager().saveRoutes("saved_routes"));
                } catch (RuntimeException exception) {
                    errorMsg.setStyle("-fx-text-fill: green;");
                    errorMsg.setText(exception.getMessage());
                    System.out.println(exception.getMessage());
                }
            });
        });
    }

    // Evento - Calcular Shortest Path
    public void calculateShortestPathEvent(MenuItem menuItem) {
        menuItem.setOnAction(actionEvent1 -> {
            defaultStyling();
            final Stage dialog = new Stage();
            dialog.setResizable(false);
            dialog.setTitle("Shortest Path");
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(controller.getStage());
            VBox dialogVBox = new VBox(20);
            dialogVBox.setPadding(new Insets(10, 10, 10, 10));
            TextField originHubTextField = createField(dialogVBox, "Origin Hub:\t\t");
            TextField destinationHubTextField = createField(dialogVBox, "Destination Hub:\t");
            HBox buttonsHBox = new HBox(30);
            Button calculateButton = new Button("Calculate");
            buttonsHBox.getChildren().addAll(calculateButton);
            buttonsHBox.setPadding(new Insets(0, 0, 0, 200));
            dialogVBox.getChildren().add(buttonsHBox);
            Label errorMsg = new Label();
            errorMsg.setStyle("-fx-text-fill: red;");
            dialogVBox.getChildren().add(errorMsg);
            Scene dialogScene = new Scene(dialogVBox, 300, 170);
            dialogScene.getStylesheets().add("events.css");
            dialog.setScene(dialogScene);
            dialog.show();
            calculateButton.setOnAction(actionEvent2 -> {
                try {

                    defaultTextField(originHubTextField, destinationHubTextField);
                    if (originHubTextField.getText().trim().isEmpty()) {
                        originHubTextField.getStyleClass().add("incorrect-text-field");
                        throw new IncorrectFieldException("\"Origin Hub\" field is empty!");
                    } else if (controller.getManager().getHub(originHubTextField.getText().trim()) == null) {
                        originHubTextField.getStyleClass().add("incorrect-text-field");
                        throw new IncorrectFieldException("\"Origin Hub\" doesn't exist!");
                    } else if (destinationHubTextField.getText().trim().isEmpty()) {
                        destinationHubTextField.getStyleClass().add("incorrect-text-field");
                        throw new IncorrectFieldException("\"Destination Hub\" field is empty!");
                    } else if (controller.getManager().getHub(destinationHubTextField.getText().trim()) == null){
                        destinationHubTextField.getStyleClass().add("incorrect-text-field");
                    throw new IncorrectFieldException("\"Destination Hub\" doesn't exist!");
                    }else {
                        Hub origin = controller.getManager().getHub(originHubTextField.getText().trim());
                        Hub destination = controller.getManager().getHub(destinationHubTextField.getText().trim());
                        List<Hub> path = controller.getManager().shortestPath(origin,destination);
                        pathStyling(path);
                        controller.getGraphView().updateAndWait();
                        dialog.close();
                        System.out.println("Shortest Path calculated!");
                        final Stage popup = new Stage();

                        VBox newBox = new VBox(10);
                        newBox.setPadding(new Insets(10, 10, 10, 10));

                        popup.setResizable(false);
                        popup.setTitle("Shortest Path Distance");
                        popup.initModality(Modality.APPLICATION_MODAL);
                        popup.initOwner(controller.getStage());
                        Label infoLabel = new Label("Shortest path distance is " + controller.getManager().shortestPathTotalDistance(origin,destination));
                        Button closeButton = new Button("Close");
                        buttonsHBox.getChildren().addAll(closeButton);
                        newBox.getChildren().addAll(infoLabel,closeButton);
                        Scene popupScene = new Scene(newBox, 200, 80);
                        popup.setScene(popupScene);
                        popup.show();
                        closeButton.setOnAction(actionEvent3 -> {

                            popup.close();

                        });
                    }
                } catch (IncorrectFieldException incorrectFieldException){
                    errorMsg.setText(incorrectFieldException.getMessage());
                    System.out.println("Invalid field: " + incorrectFieldException.getMessage());
                } catch (RuntimeException exception) {
                    errorMsg.setText(exception.getMessage());
                    System.out.println(exception.getMessage());
                }
            });
        });
    }

    // Evento - Hub mais distante de uma origem
    public void showFarthestHubEvent(MenuItem menuItem) {
        menuItem.setOnAction(actionEvent1 -> {
            defaultStyling();
            final Stage dialog = new Stage();
            dialog.setResizable(false);
            dialog.setTitle("Farthest Hub");
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(controller.getStage());
            VBox dialogVBox = new VBox(10);
            dialogVBox.setPadding(new Insets(10, 10, 10, 10));
            TextField originHubTextField = createField(dialogVBox, "Origin Hub:");
            HBox buttonsHBox = new HBox(10);
            Button calculateButton = new Button("Calculate");
            buttonsHBox.getChildren().addAll(calculateButton);
            dialogVBox.getChildren().add(buttonsHBox);
            buttonsHBox.setPadding(new Insets(0, 0, 0, 155));
            Label errorMsg = new Label();
            errorMsg.setStyle("-fx-text-fill: red;");
            dialogVBox.getChildren().add(errorMsg);
            Scene dialogScene = new Scene(dialogVBox, 250, 105);
            dialogScene.getStylesheets().add("events.css");
            dialog.setScene(dialogScene);
            dialog.show();
            calculateButton.setOnAction(actionEvent2 -> {
                try {
                    defaultTextField(originHubTextField);
                    if (originHubTextField.getText().trim().isEmpty())
                        throw new IncorrectFieldException("\"Origin Hub\" field is empty!");
                    else if (controller.getManager().getHub(originHubTextField.getText().trim()) == null)
                        throw new IncorrectFieldException("\"Origin Hub\" doesn't exist!");
                    else {
                        Hub origin = controller.getManager().getHub(originHubTextField.getText().trim());
                        List<Hub> path = controller.getManager().farthestHub(origin);
                        pathStyling(path);
                        controller.getGraphView().updateAndWait();
                        dialog.close();
                        System.out.println("Farthest Hub calculated!");
                    }
                } catch (IncorrectFieldException incorrectFieldException) {
                    originHubTextField.getStyleClass().add("incorrect-text-field");
                    errorMsg.setText(incorrectFieldException.getMessage());
                    System.out.println("Invalid field: " + incorrectFieldException.getMessage());
                } catch (RuntimeException exception) {
                    originHubTextField.getStyleClass().add("incorrect-text-field");
                    errorMsg.setText(exception.getMessage());
                    System.out.println(exception.getMessage());
                }
            });
        });
    }

    // Evento - Hubs mais distantes do grafo
    public void showFarthestHubsEvent(MenuItem menuItem) {
        menuItem.setOnAction(actionEvent1 -> {
            defaultStyling();
            try {
                List<Hub> path = controller.getManager().farthestHubs();
                pathStyling(path);
                controller.getGraphView().updateAndWait();
                System.out.println("Farthest Hubs calculated!");
            } catch (IncorrectFieldException incorrectFieldException) {
                System.out.println("Invalid field: " + incorrectFieldException.getMessage());
            } catch (RuntimeException exception) {
                System.out.println(exception.getMessage());
            }
        });
    }

    // Evento - Mostrar centralidade (todos os hubs)
    public void showCentrality(MenuItem menuItem) {
        menuItem.setOnAction(actionEvent1 -> {
            defaultStyling();
            try {
                final Stage dialog = new Stage();
                dialog.setResizable(false);
                dialog.setTitle("Hub Centrality");
                dialog.initModality(Modality.APPLICATION_MODAL);
                dialog.initOwner(controller.getStage());
                VBox dialogVBox = new VBox(20);

                ListView<String> centralityList = new ListView<>();
                centralityList.setMaxHeight(300);
                centralityList.setMinWidth(200);
                Map<Hub,Integer> centralityMap = controller.getManager().getCentrality();
                for (Hub hub : centralityMap.keySet())
                    centralityList.getItems().add(hub.toString() + " (" + centralityMap.get(hub) + ")");
                dialogVBox.getChildren().add(centralityList);
                Scene dialogScene = new Scene(dialogVBox, 300, 200);
                dialog.setScene(dialogScene);
                dialog.show();
                System.out.println("Hubs Centrality calculated!");
            } catch (RuntimeException exception) {
                System.out.println(exception.getMessage());
            }
        });
    }

    // Evento - Close Hubs
    public void showCloseHubsEvent(MenuItem menuItem) {
        menuItem.setOnAction(actionEvent1 -> {
            defaultStyling();
            final Stage dialog = new Stage();
            dialog.setResizable(false);
            dialog.setTitle("Close Hubs");
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(controller.getStage());
            VBox dialogVBox = new VBox(15);
            TextField originHubTextField = createField(dialogVBox, "Origin Hub:\t");
            TextField thresholdTextField = createField(dialogVBox, "Threshold:\t");
            dialogVBox.setPadding(new Insets(10, 10, 10, 10));
            HBox buttonsHBox = new HBox(10);
            Button calculateButton = new Button("Calculate");
            buttonsHBox.getChildren().addAll(calculateButton);
            dialogVBox.getChildren().add(buttonsHBox);
            buttonsHBox.setPadding(new Insets(0, 0, 0, 173));
            Label errorMsg = new Label();
            errorMsg.setStyle("-fx-text-fill: red;");
            dialogVBox.getChildren().add(errorMsg);
            Scene dialogScene = new Scene(dialogVBox, 260, 160);
            dialogScene.getStylesheets().add("events.css");
            dialog.setScene(dialogScene);
            dialog.show();
            calculateButton.setOnAction(actionEvent2 -> {
                try {
                    defaultTextField(originHubTextField,thresholdTextField);

                    if (originHubTextField.getText().trim().isEmpty()) {
                        originHubTextField.getStyleClass().add("incorrect-text-field");
                        throw new IncorrectFieldException("\"Origin Hub\" field is empty!");
                    }else if (controller.getManager().getHub(originHubTextField.getText().trim()) == null) {
                        originHubTextField.getStyleClass().add("incorrect-text-field");
                        throw new IncorrectFieldException("\"Origin Hub\" doesn't exist!");
                    }else if (thresholdTextField.getText().trim().isEmpty()) {
                        thresholdTextField.getStyleClass().add("incorrect-text-field");
                        throw new IncorrectFieldException("\"Threshold\" field is empty!");
                    }else if (Integer.valueOf(thresholdTextField.getText().trim()) <= 0) {
                        thresholdTextField.getStyleClass().add("incorrect-text-field");
                        throw new IncorrectFieldException("\"Threshold\" should be greater than 0!");
                    }else {
                        Hub origin = controller.getManager().getHub(originHubTextField.getText().trim());
                        int threshold = Integer.valueOf(thresholdTextField.getText().trim());
                        List<Hub> hubs = controller.getManager().closeHubs(origin,threshold);
                        vertexStyling(hubs);
                        controller.getGraphView().updateAndWait();
                        dialog.close();
                        System.out.println("Close Hubs calculated!");
                    }
                } catch (IncorrectFieldException incorrectFieldException) {
                    errorMsg.setText(incorrectFieldException.getMessage());
                    System.out.println("Invalid field: " + incorrectFieldException.getMessage());
                } catch (Exception exception) {
                    errorMsg.setText(exception.getMessage());
                    System.out.println(exception.getMessage());
                }
            });
        });
    }

    // Evento - Mostrar top 5 hubs com mais vizinhos
    public void showHubsWithMostNeighborsEvent(MenuItem menuItem) {
        menuItem.setOnAction(actionEvent1 -> {
            defaultStyling();
            try {
                final Stage dialog = new Stage();
                dialog.setResizable(false);
                dialog.setTitle("Hubs with most neighbors");
                dialog.initModality(Modality.APPLICATION_MODAL);
                dialog.initOwner(controller.getStage());
                // Bar Chart
                StackPane pane = new StackPane();
                CategoryAxis xAxis = new CategoryAxis();
                NumberAxis yAxis = new NumberAxis();
                BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
                barChart.setTitle("Top Centrality");
                xAxis.setLabel("Hubs");
                yAxis.setLabel("Value");
                XYChart.Series<String, Number> data = new XYChart.Series<>();
                data.setName("Number of neighbors");
                for (Hub hub : controller.getManager().topCentrality())
                    data.getData().add(new XYChart.Data<>(hub.toString(), controller.getManager().countNeighbors(hub)));
                barChart.getData().add(data);
                pane.getChildren().add(barChart);
                Scene dialogScene = new Scene(pane, 800, 500);
                dialog.setScene(dialogScene);
                dialog.show();
                System.out.println("Top 5 centrality calculated!");
            } catch (RuntimeException exception) {
                System.out.println(exception.getMessage());
            }
        });
    }

    // Evento - Fazer undo da última ação
    public void undoEvent(MenuItem menuItem) {
        menuItem.setOnAction(actionEvent1 -> {
            defaultStyling();
            try {
                Action action = ui.getMenuBar().undoAction();
                if (action == null)
                    System.out.println("There is no action to undo!");
                else {
                    switch (action.getReverseOperation()) {
                        case INSERT_HUB:
                            Hub insertHub = (Hub)action.element()[0];
                            Map<Route,Hub> adjacentRoutes = (Map<Route,Hub>)action.element()[1];
                            List<Hub> hubs = (List<Hub>)action.element()[2];
                            Vertex<Hub> vertex = controller.getManager().createVertex(insertHub,hubs);
                            for (Route route : adjacentRoutes.keySet())
                                controller.getManager().createEdge(insertHub,adjacentRoutes.get(route),route);
                            controller.getGraphView().updateAndWait();
                            controller.getGraphView().setVertexPosition(vertex,insertHub.getCoordinates().getX(),insertHub.getCoordinates().getY() - 25);
                            break;
                        case REMOVE_HUB:
                            Hub removeHub = (Hub)action.element()[0];
                            controller.getManager().removeVertex(removeHub);
                            controller.getGraphView().updateAndWait();
                            break;
                        case INSERT_ROUTE:
                            Hub origin = (Hub)action.element()[0];
                            Hub destination = (Hub)action.element()[1];
                            Route route = (Route)action.element()[2];
                            controller.getManager().createEdge(origin,destination,route);
                            controller.getGraphView().updateAndWait();
                            break;
                        case REMOVE_ROUTE:
                            Edge<Route,Hub> removeEdge = (Edge)action.element()[0];
                            controller.getManager().removeEdge(removeEdge.element());
                            controller.getGraphView().updateAndWait();
                            break;
                    }
                    System.out.println("Undo successful!");
                }
            } catch (RuntimeException exception) {
                System.out.println(exception.getMessage());
            }
        });
    }

    // Evento - Reset do estilo do grafo
    public void defaultStylingEvent(MenuItem menuItem) {
        menuItem.setOnAction(actionEvent1 -> {
           try {
               defaultStyling();
               System.out.println("Default Styling applied!");
           } catch (RuntimeException exception) {
               System.out.println(exception.getMessage());
           }
        });
    }

    // Alterar estilo do grafo para o default
    private void defaultStyling() {
        for (Vertex vertex : controller.getManager().getGraph().vertices())
            controller.getGraphView().getStylableVertex(vertex).setStyleClass("vertex");
        for (Edge edge : controller.getManager().getGraph().edges())
            controller.getGraphView().getStylableEdge(edge).setStyleClass("edge");
    }

    // Alterar estilo do caminho dado como argumento
    private void pathStyling(List<Hub> path) {
        vertexStyling(path);
        for (int i = 0; i < path.size(); i++)
            if (i < path.size() - 1) {
                Route route = controller.getManager().getRoute(path.get(i), path.get(i + 1));
                Edge edge = controller.getManager().getEdge(route);
                controller.getGraphView().getStylableEdge(edge).setStyleClass("edge-path");
            }
    }

    // Alterar estilo dos hubs dados como argumento
    private void vertexStyling(List<Hub> hubs) {
        for (Hub hub : hubs)
            controller.getGraphView().getStylableVertex(controller.getManager().getVertex(hub)).setStyleClass("vertex-path");
    }

    public void defaultTextField(TextField... txField){
        for(TextField e : txField) {
            if(e.getStyleClass().size()>2)
                e.getStyleClass().remove(e.getStyleClass().get(2));
        }
    }
}
