package se.hig.ndi12erd.projectlibrary;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Klassen {@link IconFragment}Fragment som startas och ärver {@link Fragment}
 * @author Ferhat Sevim
 * @author Taichi Takehana
 * @author Elias Rönnlund
 * @version 20.0
 */

public class IconFragment extends Fragment {
    private Toolbar toolbar;
    private View view;
    private FragmentListener fragmentListener;

    /**
     * En tom konstruktor.
     */

    public IconFragment() {
    }

    /**
     * Metod som skapar och returnerar vyhiearkin som är associerad med fragmentet.
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return view
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_icon, container, false);
        toolbar = view.findViewById(R.id.toolbar);
        return view;
    }



    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        getActivity().getMenuInflater().inflate(R.menu.menu_main, menu);
        toolbar = view.findViewById(R.id.action_search);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        super.onCreateOptionsMenu(menu, inflater);

    }

    /**
     * Metod som kallades när fragmentet är associerat med sin aktivity.
     *
     * @param context
     */

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.fragmentListener = (FragmentListener) context;
    }

    /**
     *  Metod som kallades före fragmentet är inte associreat med sin activity.
     */

    @Override
    public void onDetach() {
        super.onDetach();
        fragmentListener = null;
    }

    /**
     * Metod som använder metod från Interface fragmentListener.
     */

    public void onSearchFragmentViewed(){
        if(fragmentListener != null){
            fragmentListener.viewSearchFragment();
        }
    }

    /**
     * Metod som använder metod från Interface fragmentListener.
     */

    public void onFavoriteIconClicked(){
        if(fragmentListener != null){
            fragmentListener.onFavoriteClick();
        }
    }

    /**
     * Metod som returnerar vald artikel från menun.
     *
     * @param item
     * @return super.onOptionsItemSelected(item)
     */

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_search:
                fragmentListener.hideFragment();
                onSearchFragmentViewed();
                return true;
            case R.id.action_favorite:
                fragmentListener.hideFragment();
                onFavoriteIconClicked();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
