package com.apps.onlinemp3;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.apps.adapter.AdapterSongList;
import com.apps.item.ItemSong;
import com.apps.utils.Constant;
import com.apps.utils.DBHelper;
import com.apps.utils.JsonUtils;
import com.apps.utils.RecyclerItemClickListener;
import com.apps.utils.ZProgressHUD;

import java.io.File;
import java.util.ArrayList;

public class FragmentDownloads extends Fragment {

    DBHelper dbHelper;
    RecyclerView recyclerView;
    ArrayList<ItemSong> arrayList;
    public static AdapterSongList adapterSongList;
    ZProgressHUD progressHUD;
    LinearLayoutManager linearLayoutManager;
    SearchView searchView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View rootView = inflater.inflate(R.layout.fragment_downloads, container, false);

        dbHelper = new DBHelper(getActivity());

        progressHUD = ZProgressHUD.getInstance(getActivity());
        progressHUD.setMessage(getActivity().getResources().getString(R.string.loading));
        progressHUD.setSpinnerType(ZProgressHUD.FADED_ROUND_SPINNER);

        arrayList = new ArrayList<>();
        recyclerView = (RecyclerView)rootView.findViewById(R.id.recyclerView_downloads);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        loadDownloaded();

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Constant.isOnline = false;
                Constant.frag = "download";
                Constant.arrayList_play.clear();
                Constant.arrayList_play.addAll(arrayList);
                Constant.playPos = getPosition(adapterSongList.getID(position));
                ((MainActivity)getActivity()).changeText(arrayList.get(position).getMp3Name(),arrayList.get(position).getArtist(),position+1,arrayList.size(),arrayList.get(position).getDuration(),arrayList.get(position).getBitmap(),"download");

                Constant.context = getActivity();
                Intent intent = new Intent(getActivity(), PlayerService.class);
                intent.setAction(PlayerService.ACTION_FIRST_PLAY);
                getActivity().startService(intent);
            }
        }));

        setHasOptionsMenu(true);
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);

        MenuItem item = menu.findItem(R.id.menu_search);
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        searchView =
                (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setOnQueryTextListener(queryTextListener);
    }

    SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String s) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String s) {

            if (searchView.isIconified()) {
                recyclerView.setAdapter(adapterSongList);
                adapterSongList.notifyDataSetChanged();
            } else {
                adapterSongList.getFilter().filter(s);
                adapterSongList.notifyDataSetChanged();
            }
            return true;
        }
    };

    private void loadDownloaded() {

        File root = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + getString(R.string.download_desti));
        File[] songs = root.listFiles();

        if(songs != null) {
            for (int i=0; i<songs.length; i++) {

                MediaMetadataRetriever md = new MediaMetadataRetriever();
                md.setDataSource(songs[i].getAbsolutePath());
                String title = songs[i].getName();
                String duration = md.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
                duration = JsonUtils.milliSecondsToTimer(Long.parseLong(duration));
                String artist = md.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
                String url = songs[i].getAbsolutePath();

                byte[] byte_image = md.getEmbeddedPicture();
                Bitmap songImage = BitmapFactory.decodeByteArray(byte_image, 0, byte_image.length);

                ItemSong itemSong = new ItemSong(String.valueOf(i),artist,url,songImage,title,duration);
                arrayList.add(itemSong);

            }
        }


        adapterSongList = new AdapterSongList(arrayList);
        recyclerView.setAdapter(adapterSongList);
        if(arrayList.size() == 0) {
            recyclerView.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    private int getPosition(String id) {
        int count=0;
        for(int i=0;i<arrayList.size();i++)
        {
            if(id.equals(arrayList.get(i).getId()))
            {
                count = i;
                break;
            }
        }
        return count;
    }
}
