package pt.pa.view;

import javafx.scene.control.MenuBar;
import pt.pa.model.Action;

public class NetworkMenu extends MenuBar {

    private NetworkUI networkUI;

    private NetworkMenuCreate menuCreate;
    private NetworkMenuRemove menuRemove;
    private NetworkMenuRoutes menuRoutes;
    private NetworkMenuCalculate menuCalculate;
    private NetworkMenuShow menuShow;
    private NetworkMenuUndo menuUndo;

    public NetworkMenu(NetworkUI networkUI) {

        this.networkUI = networkUI;

        this.menuCreate = new NetworkMenuCreate();
        this.menuRemove = new NetworkMenuRemove();
        this.menuRoutes = new NetworkMenuRoutes();
        this.menuCalculate = new NetworkMenuCalculate();
        this.menuShow = new NetworkMenuShow();
        this.menuUndo = new NetworkMenuUndo();
        this.getMenus().addAll(menuCreate,menuRemove,menuRoutes,menuCalculate,menuShow,menuUndo);
        createHandlers();
    }

    public NetworkUI getPaneBuilder() {
        return this.networkUI;
    }

    public void saveAction(Action action) {
        menuUndo.saveAction(action);
    }

    public Action undoAction() {
        return menuUndo.undoAction();
    }

    public void createHandlers() {
        networkUI.getEventHandler().createHubEvent(menuCreate.getCreateHubItem());
        networkUI.getEventHandler().createRouteEvent(menuCreate.getCreateRouteItem());
        networkUI.getEventHandler().removeHubEvent(menuRemove.getRemoveHubItem());
        networkUI.getEventHandler().removeRouteEvent(menuRemove.getRemoveRouteItem());
        networkUI.getEventHandler().importRoutesEvent(menuRoutes.getImportRoutesItem());
        networkUI.getEventHandler().exportRoutesEvent(menuRoutes.getExportRoutesItem());
        networkUI.getEventHandler().calculateShortestPathEvent(menuCalculate.getShortestPathItem());
        //networkUI.getEventHandler().calculateDistancePathEvent(menuCalculate.getDistancePathItem());
        networkUI.getEventHandler().showFarthestHubsEvent(menuShow.getFarthestHubsItem());
        networkUI.getEventHandler().showCentrality(menuShow.getCentralityItem());
        networkUI.getEventHandler().showHubsWithMostNeighborsEvent(menuShow.getHubsWithMostNeighborsItem());
        networkUI.getEventHandler().undoEvent(menuUndo.getUndoActionItem());
    }

    public void removeHandlers() {
        networkUI.getEventHandler().removeHandler(menuCreate.getCreateHubItem());
        networkUI.getEventHandler().removeHandler(menuCreate.getCreateRouteItem());
        networkUI.getEventHandler().removeHandler(menuRemove.getRemoveHubItem());
        networkUI.getEventHandler().removeHandler(menuRemove.getRemoveRouteItem());
        networkUI.getEventHandler().removeHandler(menuRoutes.getImportRoutesItem());
        networkUI.getEventHandler().removeHandler(menuRoutes.getExportRoutesItem());
        networkUI.getEventHandler().removeHandler(menuCalculate.getShortestPathItem());
        //networkUI.getEventHandler().removeHandler(menuCalculate.getDistancePathItem());
        networkUI.getEventHandler().removeHandler(menuShow.getFarthestHubsItem());
        networkUI.getEventHandler().removeHandler(menuShow.getCentralityItem());
        networkUI.getEventHandler().removeHandler(menuShow.getHubsWithMostNeighborsItem());
        networkUI.getEventHandler().removeHandler(menuUndo.getUndoActionItem());
    }

}

