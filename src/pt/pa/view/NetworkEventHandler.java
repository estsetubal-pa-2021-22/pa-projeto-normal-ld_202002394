package pt.pa.view;

import com.brunomnsilva.smartgraph.graphview.SmartGraphVertex;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import pt.pa.graph.Edge;
import pt.pa.model.*;
import pt.pa.model.exceptions.ExistingHubException;
import pt.pa.model.exceptions.ExistingRouteException;
import pt.pa.graph.Vertex;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NetworkEventHandler {

    private NetworkController controller;
    private NetworkUI paneBuilder;

    public NetworkEventHandler(NetworkUI paneBuilder) {
        this.paneBuilder = paneBuilder;
        this.controller = this.paneBuilder.getController();
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
            paneBuilder.setElementInfoBar(new NetworkElementInfo());
            controller.getGraphView().setEdgeDoubleClickAction(graphEdge -> {
                paneBuilder.setElementInfoBar(new NetworkElementInfo(controller.getManager(), graphEdge.getUnderlyingEdge().element()));
            });
            controller.getGraphView().setVertexDoubleClickAction((SmartGraphVertex<Hub> graphVertex) -> {
                paneBuilder.setElementInfoBar(new NetworkElementInfo(controller.getManager(), graphVertex.getUnderlyingVertex().element()));
            });
        });
    }

    public void disableElementsInfoEvent() {
        controller.getGraphView().setOnMousePressed(null);
    }

    // Evento - Criar Hub
    public void createHubEvent(MenuItem menuItem) {
        menuItem.setOnAction(actionEvent1 -> {
            paneBuilder.getMenuBar().setDisable(true);
            paneBuilder.updateGraphStyle();
            Circle puppet = new Circle(5);
            puppet.getStyleClass().add("vertex");
            controller.getGraphView().getChildren().add(puppet);
            controller.getGraphView().setOnMouseMoved(mouseMovedEvent -> {
                puppet.setCenterX(mouseMovedEvent.getSceneX());
                if (mouseMovedEvent.getSceneY() < 25 + puppet.getRadius())
                    puppet.setCenterY(puppet.getRadius());
                else if (mouseMovedEvent.getSceneY() > 715 - puppet.getRadius())
                    puppet.setCenterY(715 - puppet.getRadius());
                else
                    puppet.setCenterY(mouseMovedEvent.getSceneY() - 25);

            });
            controller.getGraphView().setOnMouseClicked(mouseClickedEvent -> {
                paneBuilder.getMenuBar().setDisable(false);
                Point coordinates = new Point((int) puppet.getCenterX(), (int) puppet.getCenterY());
                controller.getGraphView().setOnMouseMoved(null);
                controller.getGraphView().setOnMouseClicked(null);
                controller.getGraphView().enableDoubleClickListener();
                createElementsInfoEvent();

                final Stage dialog = new Stage();
                dialog.setTitle("New Hub");
                dialog.initModality(Modality.APPLICATION_MODAL);
                dialog.initOwner(controller.getStage());
                VBox dialogVBox = new VBox(20);
                TextField hubCityNameTextField = createField(dialogVBox, "City Name:");
                TextField hubPopulationTextField = createField(dialogVBox, "Population:");
                HBox buttonsHBox = new HBox(30);
                Button createHubButton = new Button("Create Hub");
                buttonsHBox.getChildren().addAll(createHubButton);
                dialogVBox.getChildren().add(buttonsHBox);
                Scene dialogScene = new Scene(dialogVBox,300,200);
                dialog.setScene(dialogScene);
                dialog.show();

                dialog.setOnCloseRequest(e -> {
                    controller.getGraphView().getChildren().remove(puppet);
                });

                createHubButton.setOnAction(actionEvent2 -> {
                    // Validations
                    try {
                        if (hubCityNameTextField.getText().trim().isEmpty())
                            System.out.println("\"City Name\" field is empty!");
                        else if (hubPopulationTextField.getText().trim().isEmpty())
                            System.out.println("\"Population\" field is empty!");
                        else if (controller.getManager().getHub(hubCityNameTextField.getText().trim()) != null)
                            throw new ExistingHubException(); //System.out.println("This Hub already exists!");
                        else if (Integer.valueOf(hubPopulationTextField.getText().trim()) <= 0)
                            System.out.println("\"Population\" should be greater than 0!");
                        else {
                            Hub hub = new Hub(hubCityNameTextField.getText().trim(),Integer.valueOf(hubPopulationTextField.getText().trim()),coordinates);

                            // Save action
                            Action action = new Action(Operation.INSERT_HUB,hub);
                            paneBuilder.getMenuBar().saveAction(action);

                            Vertex<Hub> vertex1 = controller.getManager().createVertex(hub);
                            controller.getGraphView().getChildren().remove(puppet);
                            controller.getGraphView().updateAndWait();
                            controller.getGraphView().setVertexPosition(vertex1,coordinates.getX(),coordinates.getY());
                            dialog.close();
                            paneBuilder.updateMetrics();
                            System.out.println("Hub created!");
                        }
                    } catch (ExistingHubException exception) {
                        System.out.println(exception.getMessage());
                    } catch (RuntimeException runtimeException) {
                        System.out.println(runtimeException.getMessage());
                    }
                });
            });
        });
    }

    // Evento - Criar Route
    public void createRouteEvent(MenuItem menuItem) {
        menuItem.setOnAction(actionEvent1 -> {
            paneBuilder.updateGraphStyle();
            final Stage dialog = new Stage();
            dialog.setTitle("New Route");
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(controller.getStage());
            VBox dialogVBox = new VBox(20);
            TextField originHubTextField = createField(dialogVBox, "Origin Hub:");
            TextField destinationHubTextField = createField(dialogVBox, "Destination Hub:");
            TextField distanceTextField = createField(dialogVBox, "Distance:");
            HBox buttonsHBox = new HBox(30);
            Button createRouteButton = new Button("Create Route");
            buttonsHBox.getChildren().addAll(createRouteButton);
            dialogVBox.getChildren().add(buttonsHBox);
            Scene dialogScene = new Scene(dialogVBox,300,200);
            dialog.setScene(dialogScene);
            dialog.show();

            createRouteButton.setOnAction(actionEvent2 -> {
                // Validations
                try {
                    if (originHubTextField.getText().trim().isEmpty())
                        System.out.println("\"Origin Hub\" field is empty!");
                    else if (destinationHubTextField.getText().trim().isEmpty())
                        System.out.println("\"Destination Hub\" field is empty!");
                    else if (controller.getManager().getHub(originHubTextField.getText().trim()) == null)
                        System.out.println("\"Origin Hub\" doesn't exist!");
                    else if (controller.getManager().getHub(destinationHubTextField.getText().trim()) == null)
                        System.out.println("\"Destination Hub\" doesn't exist!");
                    else if (Integer.valueOf(distanceTextField.getText().trim()) <= 0)
                        System.out.println("\"Distance\" should be greater than 0!");
                    else if (controller.getManager().getRoute(originHubTextField.getText().trim(),destinationHubTextField.getText().trim()) != null)
                        throw new ExistingRouteException();
                    else {
                        Hub origin = controller.getManager().getHub(originHubTextField.getText().trim());
                        Hub destination = controller.getManager().getHub(destinationHubTextField.getText().trim());
                        Route route = new Route(Integer.valueOf(distanceTextField.getText().trim()));

                        controller.getManager().createEdge(origin,destination,route);

                        // Save action
                        Action action = new Action(Operation.INSERT_ROUTE,controller.getManager().getEdge(route));
                        paneBuilder.getMenuBar().saveAction(action);

                        controller.getGraphView().updateAndWait();
                        dialog.close();
                        paneBuilder.updateMetrics();
                        System.out.println("Route created!");
                    }
                } catch (ExistingRouteException exception) {
                    System.out.println(exception.getMessage());
                } catch (RuntimeException runtimeException) {
                    System.out.println(runtimeException.getMessage());
                }
            });
        });
    }

    // Evento - Remover Hub
    public void removeHubEvent(MenuItem menuItem) {
        menuItem.setOnAction(actionEvent1 -> {
            paneBuilder.updateGraphStyle();
            final Stage dialog = new Stage();
            dialog.setTitle("Remove Hub");
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(controller.getStage());
            VBox dialogVBox = new VBox(20);
            TextField hubTextField = createField(dialogVBox, "Hub:");
            HBox buttonsHBox = new HBox(30);
            Button removeHubButton = new Button("Remove Hub");
            buttonsHBox.getChildren().addAll(removeHubButton);
            dialogVBox.getChildren().add(buttonsHBox);
            Scene dialogScene = new Scene(dialogVBox, 300, 200);
            dialog.setScene(dialogScene);
            dialog.show();
            removeHubButton.setOnAction(actionEvent2 -> {
                try {
                    if (hubTextField.getText().trim().isEmpty())
                        System.out.println("\"Hub\" field is empty!");
                    else if (controller.getManager().getHub(hubTextField.getText().trim()) == null)
                        System.out.println("\"Hub\" doesn't exist!");
                    else {
                        Hub hub = controller.getManager().getHub(hubTextField.getText().trim());

                        // Save action
                        Map<Route,Hub> adjacentRoutes = new HashMap<>();
                        Vertex<Hub> vertex = controller.getManager().getVertex(hub);
                        for (Edge edge : controller.getManager().getGraph().incidentEdges(controller.getManager().getVertex(hub)))
                            adjacentRoutes.put((Route)edge.element(),(Hub)controller.getManager().getGraph().opposite(vertex,edge).element());
                        Action action = new Action(Operation.REMOVE_HUB,hub,adjacentRoutes,new ArrayList<>(controller.getManager().getHubs()));
                        paneBuilder.getMenuBar().saveAction(action);

                        controller.getManager().removeVertex(hub);
                        controller.getGraphView().updateAndWait();
                        dialog.close();
                        paneBuilder.updateMetrics();

                        System.out.println("Hub removed!");
                    }
                } catch (RuntimeException exception) {
                    System.out.println(exception.getMessage());
                }
            });
        });
    }

    // Evento - Remover Route
    public void removeRouteEvent(MenuItem menuItem) {
        menuItem.setOnAction(actionEvent1 -> {
            paneBuilder.updateGraphStyle();
            final Stage dialog = new Stage();
            dialog.setTitle("Remove Route");
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(controller.getStage());
            VBox dialogVBox = new VBox(20);
            TextField originHubTextField = createField(dialogVBox, "Origin Hub:");
            TextField destinationHubTextField = createField(dialogVBox, "Destination Hub:");
            HBox buttonsHBox = new HBox(30);
            Button removeRouteButton = new Button("Remove Route");
            buttonsHBox.getChildren().addAll(removeRouteButton);
            dialogVBox.getChildren().add(buttonsHBox);
            Scene dialogScene = new Scene(dialogVBox, 300, 200);
            dialog.setScene(dialogScene);
            dialog.show();
            removeRouteButton.setOnAction(actionEvent2 -> {
                try {
                    if (originHubTextField.getText().trim().isEmpty())
                        System.out.println("\"Origin Hub\" field is empty!");
                    else if (destinationHubTextField.getText().trim().isEmpty())
                        System.out.println("\"Destination Hub\" field is empty!");
                    else if (controller.getManager().getHub(originHubTextField.getText().trim()) == null)
                        System.out.println("\"Origin Hub\" doesn't exist!");
                    else if (controller.getManager().getHub(destinationHubTextField.getText().trim()) == null)
                        System.out.println("\"Destination Hub\" doesn't exist!");
                    else if (controller.getManager().getRoute(originHubTextField.getText().trim(),destinationHubTextField.getText().trim()) == null)
                        System.out.println("This route doesn't exist!");
                    else {
                        Hub origin = controller.getManager().getHub(originHubTextField.getText().trim());
                        Hub destination = controller.getManager().getHub(destinationHubTextField.getText().trim());
                        Route route = controller.getManager().getRoute(origin,destination);

                        // Save action
                        List<Hub> hubs = new ArrayList<>();
                        hubs.add(origin);
                        hubs.add(destination);
                        Action action = new Action(Operation.REMOVE_ROUTE,origin,destination,route);
                        paneBuilder.getMenuBar().saveAction(action);

                        controller.getManager().removeEdge(route);
                        controller.getGraphView().updateAndWait();
                        dialog.close();
                        paneBuilder.updateMetrics();
                        System.out.println("Route removed!");
                    }
                } catch (RuntimeException exception) {
                    System.out.println(exception.getMessage());
                }
            });
        });
    }

    // Evento - Importar Routes
    public void importRoutesEvent(MenuItem menuItem) {
        menuItem.setOnAction(actionEvent1 -> {
            paneBuilder.updateGraphStyle();
            final Stage dialog = new Stage();
            dialog.setTitle("Import Routes");
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(controller.getStage());
            VBox dialogVBox = new VBox(20);
            TextField importRoutesField = createField(dialogVBox, "Routes file path:");
            HBox buttonsHBox = new HBox(30);
            Button importRoutesButton = new Button("Import Routes");
            buttonsHBox.getChildren().addAll(importRoutesButton);
            dialogVBox.getChildren().add(buttonsHBox);
            Scene dialogScene = new Scene(dialogVBox, 300, 200);
            dialog.setScene(dialogScene);
            dialog.show();
            importRoutesButton.setOnAction(actionEvent2 -> {
                try {
                    if (importRoutesField.getText().trim().isEmpty())
                        System.out.println("\"Routes file path\" field is empty!");
                    else {
                        DatasetReader datasetReader = new DatasetReader(importRoutesField.getText().trim(),controller.getManager().getHubs());
                        controller.getManager().createGraphElements(datasetReader);
                        controller.getGraphView().updateAndWait();
                        controller.setCoordinates();
                        dialog.close();
                        paneBuilder.updateMetrics();
                        System.out.println("Routes imported!");
                    }
                } catch (RuntimeException exception) {
                    System.out.println(exception.getMessage());
                }
            });
        });
    }

    // Evento - Exportar Routes
    public void exportRoutesEvent(MenuItem menuItem) {
        menuItem.setOnAction(actionEvent1 -> {
            paneBuilder.updateGraphStyle();
            try {
                System.out.println("Routes file saved in: " + controller.getManager().saveRoutes("saved_routes"));
            } catch (RuntimeException exception) {
                System.out.println(exception.getMessage());
            }
        });
    }

    // Evento - Calcular Shortest Path
    public void calculateShortestPathEvent(MenuItem menuItem) {
        menuItem.setOnAction(actionEvent1 -> {
            paneBuilder.updateGraphStyle();
            final Stage dialog = new Stage();
            dialog.setTitle("New Route");
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(controller.getStage());
            VBox dialogVBox = new VBox(20);
            TextField originHubTextField = createField(dialogVBox, "Origin Hub:");
            TextField destinationHubTextField = createField(dialogVBox, "Destination Hub:");
            HBox buttonsHBox = new HBox(30);
            Button calculateButton = new Button("Calculate");
            buttonsHBox.getChildren().addAll(calculateButton);
            dialogVBox.getChildren().add(buttonsHBox);
            Scene dialogScene = new Scene(dialogVBox, 300, 200);
            dialog.setScene(dialogScene);
            dialog.show();
            calculateButton.setOnAction(actionEvent2 -> {
                try {
                    if (originHubTextField.getText().trim().isEmpty())
                        System.out.println("\"Origin Hub\" field is empty!");
                    else if (destinationHubTextField.getText().trim().isEmpty())
                        System.out.println("\"Destination Hub\" field is empty!");
                    else if (controller.getManager().getHub(originHubTextField.getText().trim()) == null)
                        System.out.println("\"Origin Hub\" doesn't exist!");
                    else if (controller.getManager().getHub(destinationHubTextField.getText().trim()) == null)
                        System.out.println("\"Destination Hub\" doesn't exist!");
                    else {
                        Hub origin = controller.getManager().getHub(originHubTextField.getText().trim());
                        Hub destination = controller.getManager().getHub(destinationHubTextField.getText().trim());
                        List<Hub> path = controller.getManager().shortestPath(origin,destination);
                        for (int i = 0; i < path.size(); i++)
                            if (i < path.size() - 1) {
                                Route route = controller.getManager().getRoute(path.get(i), path.get(i + 1));
                                Edge edge = controller.getManager().getEdge(route);
                                controller.getGraphView().getStylableEdge(edge).setStyle("-fx-stroke: black; -fx-stroke-width: 2;");
                            }
                        controller.getGraphView().updateAndWait();
                        dialog.close();
                        System.out.println("Path calculated!");
                        final Stage popup = new Stage();
                        popup.setTitle("Shortest Path Distance");
                        popup.initModality(Modality.APPLICATION_MODAL);
                        popup.initOwner(controller.getStage());
                        Label infoLabel = new Label("Shortest path distance is " + controller.getManager().shortestPathTotalDistance(origin,destination));
                        Scene popupScene = new Scene(infoLabel, 300, 200);
                        popup.setScene(popupScene);
                        popup.show();
                    }
                } catch (RuntimeException exception) {
                    System.out.println(exception.getMessage());
                }
            });
        });
    }

    /*
    // Evento - Calcular Distance Path
    public void calculateDistancePathEvent(MenuItem menuItem) {
        System.out.println("Evento calcular distância");
    }
    */

    // Evento - Mostrar hubs mais distantes
    public void showFarthestHubsEvent(MenuItem menuItem) {
        menuItem.setOnAction(actionEvent1 -> {
            paneBuilder.updateGraphStyle();
            try {
                System.out.println("Farthest Hubs: " + controller.getManager().farthestHubs());
            } catch (RuntimeException exception) {
                System.out.println(exception.getMessage());
            }
        });
    }

    // Evento - Mostrar top 5 hubs com mais vizinhos
    public void showHubsWithMostNeighborsEvent(MenuItem menuItem) {
        menuItem.setOnAction(actionEvent1 -> {
            paneBuilder.updateGraphStyle();
            try {
                System.out.println("Hubs with most neighbors: " + controller.getManager().top5Centrality());
            } catch (RuntimeException exception) {
                System.out.println(exception.getMessage());
            }
        });
    }

    // Evento - Fazer undo da última ação
    public void undoEvent(MenuItem menuItem) {
        menuItem.setOnAction(actionEvent1 -> {
            paneBuilder.updateGraphStyle();
            try {
                Action action = paneBuilder.getMenuBar().undoAction();
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
                    paneBuilder.updateMetrics();
                    System.out.println("Undo successful!");
                }
            } catch (RuntimeException exception) {
                System.out.println(exception.getMessage());
            }
        });
    }
}
