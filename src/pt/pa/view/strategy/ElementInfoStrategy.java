package pt.pa.view.strategy;

import javafx.scene.layout.VBox;
import pt.pa.model.NetworkManager;
/**
 * Class responsible for defining the interface for the Strategy Pattern, works as the "Strategy" entity for this pattern.
 *
 * @author LD_202002394
 * @version Final
 */
public interface ElementInfoStrategy {

    /**
     * Method used to define the update method.
     *
     * @param manager NetworkManager
     * @param vbox    VBox
     * @param element Object
     */
    void update(NetworkManager manager, VBox vbox, Object element);

}
