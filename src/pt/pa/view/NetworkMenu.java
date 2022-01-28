package pt.pa.view;

import javafx.scene.control.MenuBar;
import pt.pa.model.actions.Action;

/**
 * Class that initializes the events available in the application.
 *
 * @author LD_202002394
 * @version Final
 */

public class NetworkMenu extends MenuBar {

    private final NetworkUI networkUI;

    private final NetworkMenuCreate menuCreate;
    private final NetworkMenuRemove menuRemove;
    private final NetworkMenuRoutes menuRoutes;
    private final NetworkMenuCalculate menuCalculate;
    private final NetworkMenuShow menuShow;
    private final NetworkMenuUndo menuUndo;

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

    public void saveAction(Action action) {
        menuUndo.saveAction(action);
    }

    public Action undoAction() {
        return menuUndo.undoAction();
    }

    private void createHandlers() {
        networkUI.getEventHandler().createHubEvent(menuCreate.getCreateHubItem());
        networkUI.getEventHandler().createRouteEvent(menuCreate.getCreateRouteItem());
        networkUI.getEventHandler().removeHubEvent(menuRemove.getRemoveHubItem());
        networkUI.getEventHandler().removeRouteEvent(menuRemove.getRemoveRouteItem());
        networkUI.getEventHandler().importRoutesEvent(menuRoutes.getImportRoutesItem());
        networkUI.getEventHandler().exportRoutesEvent(menuRoutes.getExportRoutesItem());
        networkUI.getEventHandler().calculateShortestPathEvent(menuCalculate.getShortestPathItem());
        networkUI.getEventHandler().showFarthestHubEvent(menuCalculate.getFarthestHubItem());
        networkUI.getEventHandler().showFarthestHubsEvent(menuCalculate.getFarthestHubsItem());
        networkUI.getEventHandler().showCloseHubsEvent(menuCalculate.getCloseHubsItem());
        networkUI.getEventHandler().showCentrality(menuShow.getCentralityItem());
        networkUI.getEventHandler().showHubsWithMostNeighborsEvent(menuShow.getHubsWithMostNeighborsItem());
        networkUI.getEventHandler().undoEvent(menuUndo.getUndoActionItem());
        networkUI.getEventHandler().defaultStylingEvent(menuUndo.getDefaultStylingItem());
    }

}

