package com.apps.onlinemp3;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.adapter.AdapterRecent;
import com.apps.item.ItemSong;
import com.apps.utils.Constant;
import com.apps.utils.DBHelper;
import com.apps.utils.JsonUtils;
import com.apps.utils.RecyclerItemClickListener;
import com.apps.utils.ZProgressHUD;
import com.google.android.gms.ads.AdListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class FragmentHome extends Fragment {

    DBHelper dbHelper;
    RecyclerView recyclerView;
    ArrayList<ItemSong> songList;
    ArrayList<ItemSong> arrayList_recent;
    AdapterRecent adapterRecent;
    ZProgressHUD progressHUD;
    LinearLayoutManager linearLayoutManager;
    public ViewPager viewpager;
    ImagePagerAdapter adapter;
    TextView textView_empty;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        setHasOptionsMenu(true);

        dbHelper = new DBHelper(getActivity());

        progressHUD = ZProgressHUD.getInstance(getActivity());
        progressHUD.setMessage(getActivity().getResources().getString(R.string.loading));
        progressHUD.setSpinnerType(ZProgressHUD.FADED_ROUND_SPINNER);

        textView_empty = (TextView)rootView.findViewById(R.id.textView_recent_empty);

        adapter = new ImagePagerAdapter();
        viewpager = (ViewPager)rootView.findViewById(R.id.viewPager_home);
        viewpager.setPadding(80,20,80,20);
        viewpager.setClipToPadding(false);
        viewpager.setPageMargin(40);
        viewpager.setClipChildren(false);

        songList = new ArrayList<ItemSong>();
        arrayList_recent = new ArrayList<ItemSong>();
        recyclerView = (RecyclerView)rootView.findViewById(R.id.recyclerView_home_recent);
        linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        if (JsonUtils.isNetworkAvailable(getActivity())) {
            new LoadLatestNews().execute(Constant.URL_LATEST);
        } else {
            Toast.makeText(getActivity(), getResources().getString(R.string.internet_not_conn), Toast.LENGTH_SHORT).show();
        }

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Constant.isOnline = false;
                Constant.arrayList_play.clear();
                Constant.arrayList_play.addAll(arrayList_recent);
                Constant.playPos = position;
                ((MainActivity)getActivity()).changeText(arrayList_recent.get(position).getMp3Name(),arrayList_recent.get(position).getCategoryName(),position+1,arrayList_recent.size(),arrayList_recent.get(position).getDuration(),arrayList_recent.get(position).getImageBig(),"home");

                Constant.context = getActivity();
                if(position == 0) {
                    Intent intent = new Intent(getActivity(), PlayerService.class);
                    intent.setAction(PlayerService.ACTION_FIRST_PLAY);
                    getActivity().startService(intent);
                }
            }
        }));

        return rootView;
    }

    private class LoadLatestNews extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            progressHUD.show();
            songList.clear();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            return SongLoader.doLoadSong(strings[0], songList);
        }

        @Override
        protected void onPostExecute(String s) {
            boolean isSuccessFull = s.equals("1");
            if(isSuccessFull) {
                handleSuccessSongLoad();
            } else {
                handleSongLoadFailure();
            }
            super.onPostExecute(s);
        }

        private void handleSongLoadFailure() {
            progressHUD.dismissWithFailure(getResources().getString(R.string.error));
            Toast.makeText(getActivity(), getResources().getString(R.string.server_no_conn), Toast.LENGTH_SHORT).show();
        }

        private void handleSuccessSongLoad() {
            progressHUD.dismissWithSuccess(getResources().getString(R.string.success));
            if(Constant.isAppFirst) {
                if(songList.size()>0) {
                    Constant.isAppFirst = false;
                    Constant.arrayList_play.addAll(songList);
                    ((MainActivity)getActivity()).changeText(songList.get(0).getMp3Name(), songList.get(0).getCategoryName(),1, songList.size(), songList.get(0).getDuration(), songList.get(0).getImageBig(),"home");
                    Constant.context = getActivity();
                }
            }

            viewpager.setAdapter(adapter);
            loadRecent();
        }
    }

    private void loadRecent() {
        arrayList_recent = dbHelper.loadDataRecent();
        adapterRecent = new AdapterRecent(getActivity(),arrayList_recent);
        recyclerView.setAdapter(adapterRecent);

        if(arrayList_recent.size() == 0) {
            recyclerView.setVisibility(View.GONE);
            textView_empty.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            textView_empty.setVisibility(View.GONE);
        }
    }

    private class ImagePagerAdapter extends PagerAdapter {

        private LayoutInflater inflater;

        public ImagePagerAdapter() {
            // TODO Auto-generated constructor stub

            inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return songList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            View imageLayout = inflater.inflate(R.layout.viewpager_home, container, false);
            assert imageLayout != null;
            ImageView imageView = (ImageView) imageLayout.findViewById(R.id.imageView_pager_home);
            final ProgressBar spinner = (ProgressBar) imageLayout.findViewById(R.id.loading_home);
            TextView title = (TextView) imageLayout.findViewById(R.id.textView_pager_home_title);
            TextView cat = (TextView) imageLayout.findViewById(R.id.textView_pager_home_cat);
            RelativeLayout rl = (RelativeLayout)imageLayout.findViewById(R.id.rl_homepager);

            title.setText(songList.get(position).getMp3Name());
            cat.setText(songList.get(position).getCategoryName());

            Picasso.with(getActivity())
                .load(songList.get(position).getImageBig())
                .placeholder(R.mipmap.app_icon)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        spinner.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        spinner.setVisibility(View.GONE);
                    }
                });

            rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showInter();
                }
            });

            container.addView(imageLayout, 0);
            return imageLayout;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    private void showInter() {
        Constant.adCount = Constant.adCount + 1;
        if(Constant.adCount % Constant.adDisplay == 0) {
            ((MainActivity)getActivity()).mInterstitial.setAdListener(new AdListener() {

                @Override
                public void onAdClosed() {
                    playIntent();
                    super.onAdClosed();
                }
            });
            if(((MainActivity)getActivity()).mInterstitial.isLoaded()) {
                ((MainActivity)getActivity()).mInterstitial.show();
                ((MainActivity)getActivity()).loadInter();
            } else {
                playIntent();
            }
        } else {
            playIntent();
        }
    }

    private void playIntent() {
        Constant.isOnline = true;
        int pos = viewpager.getCurrentItem();
        Constant.arrayList_play.clear();
        Constant.arrayList_play.addAll(songList);
        Constant.playPos = pos;
        ((MainActivity)getActivity()).changeText(songList.get(pos).getMp3Name(), songList.get(pos).getCategoryName(),pos+1, songList.size(), songList.get(pos).getDuration(), songList.get(pos).getImageBig(),"home");

        Constant.context = getActivity();
        if(pos == 0) {
            Intent intent = new Intent(getActivity(), PlayerService.class);
            intent.setAction(PlayerService.ACTION_FIRST_PLAY);
            getActivity().startService(intent);
        }
    }
}