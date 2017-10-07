package com.sdlxmusic.onlinemp3;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class FragmentSongRequest extends Fragment {

    private EditText editTextSongName;
    private EditText editTextArtistName;
    private Button songReqSubmitButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_song_req, container, false);
    }


   /* @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_req);

        initEditTexts();
        initButtons();

        registerButtonClickListener();

    }


    private void initEditTexts() {
        editTextSongName = (EditText)findViewById(R.id.edit_txt_song_name);
        editTextArtistName = (EditText) findViewById(R.id.edit_text_artist_name);
    }

    private void initButtons() {
        songReqSubmitButton = (Button) findViewById(R.id.btn_song_request);
    }

    private void registerButtonClickListener() {

        songReqSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editFieldsEmpty()) {
                    Toast.makeText(FragmentSongRequest.this, "Fill out both artist and song name fields", Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(FragmentSongRequest.this, "Successfully submitted", Toast.LENGTH_LONG).show();
            }
        });

    }

    private boolean editFieldsEmpty() {
        return editTextSongName.getText().toString().length() <= 0 || editTextArtistName.getText().toString().length() <= 0;
    }

}

 class SubmitSongRequestTask extends AsyncTask<String, Void, Void> {
     @Override
     protected Void doInBackground(String... params) {
         URL url;
         HttpURLConnection urlConnection;
         try {
             url = new URL("");
             urlConnection = (HttpURLConnection) url.openConnection();
             urlConnection.setDoInput(true);
             urlConnection.setDoOutput(true);
             urlConnection.setRequestProperty("Content-Type", "application/json");
             urlConnection.setRequestMethod("POST");

             OutputStreamWriter writer = new OutputStreamWriter(urlConnection.getOutputStream());
             writer.write("data");
             writer.flush();

             int statusCode = urlConnection.getResponseCode();
             if (statusCode == 200) {

             }

         } catch (Exception e) {

         }
         return null;
     }*/
 }

