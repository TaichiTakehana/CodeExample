package se.hig.ndi12erd.projectlibrary;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

/**
 * Klassen {@link MainActivity}vity som startar appen och implementerar {@link FragmentListener}
 * @author Ferhat Sevim
 * @author Taichi Takehana
 * @author Elias Rönnlund
 * @version 20.0
 */

public class MainActivity extends AppCompatActivity implements FragmentListener {
    private FragmentTransaction fragmentTransaction;
    private View fragmentContainer;
    private Toolbar toolbar;

    /**
     * Det här är onCreate som initierar activity.
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewSearchIcon();
        viewMainFragment();
    }

    /**
     * Metod som visar sökikonen i toolbar.
     */

    public void viewSearchIcon(){
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(toolbar != null){
            IconFragment iconFragment = new IconFragment();
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.toolbar, iconFragment);
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    /**
     * Metod som visar MainFragment.
     */

    public void viewMainFragment(){
        fragmentContainer = findViewById(R.id.main_fragment);
        if(fragmentContainer != null){
            MainFragment mainFragment = new MainFragment();
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.main_fragment, mainFragment);
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    /**
     * Metod som visar SearchResultFragment efter man klickar.
     *
     * @param searchURL
     * @param searchText
     */

    @Override
    public void onButtonClick(String searchURL, String searchText){
        fragmentContainer = findViewById(R.id.fragment_container);
        if(fragmentContainer != null) {
            SearchResultFragment searchResultFragment = new SearchResultFragment();
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            searchResultFragment.changeProperties(searchURL, searchText);
            fragmentTransaction.replace(R.id.fragment_container, searchResultFragment);
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    /**
     * Metod som visar ProfileFragment efter man väljer en bok från ListView.
     *
     * @param profileURL
     */

    @Override
    public void onItemClick(String profileURL){
        fragmentContainer = findViewById(R.id.fragment_container);
        if(fragmentContainer != null) {
            ProfileFragment profileFragment = new ProfileFragment();
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            profileFragment.changeProperties(profileURL);
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragmentTransaction.replace(R.id.fragment_container, profileFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    /**
     * Metod som visar FavoritesFragment efter man klickar ikonen hjärta.
     *
     */

    @Override
    public void onFavoriteClick(){
        fragmentContainer = findViewById(R.id.fragment_container);
        if(fragmentContainer != null){
            FavoritesFragment favoritesFragment = new FavoritesFragment();
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragmentTransaction.replace(R.id.fragment_container, favoritesFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    /**
     * Metod som visar SearchFragment.
     */

    @Override
    public void viewSearchFragment(){
        fragmentContainer = findViewById(R.id.search_fragment);
        if(fragmentContainer != null){
            SearchFragment searchFragment = new SearchFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.search_fragment, searchFragment);
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    /**
     * Metod som gömmer MainFragment efter man klickar ikonen sök eller favorit.
     */

    @Override
    public void hideFragment(){
        fragmentContainer = findViewById(R.id.main_fragment);
        if(fragmentContainer != null){
            MainFragment mainFragment = new MainFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.main_fragment, mainFragment);
            fragmentTransaction.hide(mainFragment);
            fragmentTransaction.commit();
        }
    }

}
