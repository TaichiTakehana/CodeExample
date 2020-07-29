package se.hig.ndi12erd.projectlibrary;

/**
 * Klassen {@link FragmentListener} Interface som lyssnar fragment.
 * @author Ferhat Sevim
 * @author Taichi Takehana
 * @author Elias Rönnlund
 * @version 20.0
 */

public interface FragmentListener {
    void onButtonClick(String searchURL, String searchText);
    void onItemClick(String profileURL);
    void onFavoriteClick();
    void viewSearchFragment();
    void hideFragment();
}
