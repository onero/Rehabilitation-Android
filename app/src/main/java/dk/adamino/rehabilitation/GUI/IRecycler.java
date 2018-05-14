package dk.adamino.rehabilitation.GUI;

/**
 * Created by Adamino.
 */
public interface IRecycler<T> {

    /**
     * Handle clicked item
     * @param item
     */
    void onItemClicked(T item);
}
