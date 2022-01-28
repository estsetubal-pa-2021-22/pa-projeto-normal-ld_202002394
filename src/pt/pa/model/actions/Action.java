package pt.pa.model.actions;
/**
 * Class related to the Factory Pattern, working as the "Product" entity for this pattern.
 * <br>
 * Contains information of an action. Works like a "History" functionality.
 *
 * @author LD_202002394
 * @version Final
 */
public interface Action {

    /**
     * Method responsible for the undo functionality.
     *
     */
    void undo();

}
