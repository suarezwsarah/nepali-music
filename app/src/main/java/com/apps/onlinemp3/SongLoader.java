package com.apps.onlinemp3;

import com.apps.item.ItemSong;
import com.apps.utils.Constant;
import com.apps.utils.JsonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class SongLoader {

    public static String doLoadSong(String theUrl, List<ItemSong> songList) {
        try {
            String json = JsonUtils.getJSONString(theUrl);

            JSONObject mainJson = new JSONObject(json);
            JSONArray jsonArray = mainJson.getJSONArray(Constant.TAG_ROOT);
            JSONObject objJson = null;
            for (int i = 0; i < jsonArray.length(); i++) {
                objJson = jsonArray.getJSONObject(i);

                String id = objJson.getString(Constant.TAG_ID);
                String cid = objJson.getString(Constant.TAG_CAT_ID);
                String cname = objJson.getString(Constant.TAG_CAT_NAME);
                String artist = objJson.getString(Constant.TAG_ARTIST);
                String name = objJson.getString(Constant.TAG_SONG_NAME);
                String url = objJson.getString(Constant.TAG_MP3_URL);
                String desc = objJson.getString(Constant.TAG_DESC);
                String duration = objJson.getString(Constant.TAG_DURATION);
                String image = objJson.getString(Constant.TAG_THUMB_B).replace(" ","%20");
                String image_small = objJson.getString(Constant.TAG_THUMB_S).replace(" ","%20");

                ItemSong objItem = new ItemSong(id,cid,cname,artist,url,image,image_small,name,duration,desc);
                songList.add(objItem);
            }

            return "1";
        } catch (JSONException e) {
            e.printStackTrace();
            return "0";
        } catch (Exception ee) {
            ee.printStackTrace();
            return "0";
        }
    }
}
