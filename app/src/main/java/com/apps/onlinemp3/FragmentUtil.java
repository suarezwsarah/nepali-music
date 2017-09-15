package com.apps.onlinemp3;

import android.support.v4.app.Fragment;

import java.util.HashMap;
import java.util.Map;

public class FragmentUtil {

    private static Map<Integer, Fragment> fragmentMap = new HashMap<>();
    private static Map<Integer, Integer> fragmentTitleMap = new HashMap<>();

     static {
        fragmentMap.put(R.id.nav_home, new FragmentHome());
        fragmentMap.put(R.id.nav_cat, new FragmentCat());
        fragmentMap.put(R.id.nav_artist, new FragmentArtist());
        fragmentMap.put(R.id.nav_fav, new FragmentFav());
        fragmentMap.put( R.id.nav_downlaod, new FragmentDownloads());

        fragmentTitleMap.put(R.id.nav_home, R.string.home);
        fragmentTitleMap.put(R.id.nav_cat, R.string.categories);
        fragmentTitleMap.put(R.id.nav_artist, R.string.artist);
        fragmentTitleMap.put(R.id.nav_fav, R.string.favourite);
        fragmentTitleMap.put(R.id.nav_downlaod, R.string.downloads);
    }

    public static Map<Integer, Fragment> getFragmentMap() {
        return fragmentMap;
    }

    public static Map<Integer, Integer> getFragmentTitleMap() {
        return fragmentTitleMap;
    }
}
