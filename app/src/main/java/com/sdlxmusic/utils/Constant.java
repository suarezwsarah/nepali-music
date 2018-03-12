package com.sdlxmusic.utils;

import android.content.Context;

import com.sdlxmusic.item.ItemAbout;
import com.sdlxmusic.item.ItemSong;
import com.google.android.exoplayer2.SimpleExoPlayer;

import java.io.Serializable;
import java.util.ArrayList;

public class Constant implements Serializable {

	private static final long serialVersionUID = 1L;

	public static String SERVER_URL="http://tipstechblog.com/";

	public static final String URL_LATEST = SERVER_URL + "api.php?latest";
	public static final String URL_ARTIST = SERVER_URL + "api.php?artist_list";
	public static final String URL_CAT = SERVER_URL + "api.php?cat_list";
	public static final String URL_SONG_BY_CAT = SERVER_URL + "api.php?cat_id=";
	public static final String URL_SONG_BY_ARTIST = SERVER_URL + "api.php?artist_name=";
	public static final String URL_SONG = SERVER_URL + "api.php?mp3_id=";


	public static final String APP_DETAILS_URL = SERVER_URL +  "api.php";
	public static final String URL_ABOUT_US_LOGO = SERVER_URL + "images/";



	public static final String TAG_ROOT="ONLINE_MP3";

	public static final String TAG_ID="id";
	public static final String TAG_CAT_ID="cat_id";
	public static final String TAG_CAT_NAME="category_name";
	public static final String TAG_MP3_URL="mp3_url";
	public static final String TAG_DURATION="mp3_duration";
	public static final String TAG_SONG_NAME="mp3_title";
	public static final String TAG_DESC="mp3_description";
	public static final String TAG_THUMB_B="mp3_thumbnail_b";
	public static final String TAG_THUMB_S="mp3_thumbnail_s";
	public static final String TAG_ARTIST="mp3_artist";

	public static final String TAG_CID="cid";

	public static final String TAG_ARTIST_NAME="artist_name";
	public static final String TAG_ARTIST_IMAGE="artist_image";
	public static final String TAG_ARTIST_THUMB="artist_image_thumb";

	public static ItemAbout itemAbout;

	public static SimpleExoPlayer exoPlayer;

	// Number of columns of Grid View
	public static final int NUM_OF_COLUMNS = 3;

	// Gridview image padding
	public static final int GRID_PADDING = 3; // in dp

	public static int columnWidth = 0;

//	public static PlayerService playerService;
	public static int playPos = 0;
	public static ArrayList<ItemSong> arrayList_play = new ArrayList<>();
	public static Boolean isRepeat = false, isSuffle = false, isPlaying = false, isFav = false, isScrolled = false, isAppFirst = true,
			isPlayed = false, isFromNoti = false, isFromPush = false, isBackStack = false, isAppOpen = false, isOnline = true;
	public static long currentProgress = 0;
	public static long secondaryProgress = 0;
	public static Context context;
	public static int volume = 25;
	public static String frag = "", pushID = "";

	public static String loadedSongPage = "";
	public static int adCount = 0;
	public static int adDisplay = 2;

}
