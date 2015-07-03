package com.ayz4sci.downloadprogressexample;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.ayz4sci.downloadprogressview.DownloadProgressView;

import java.io.File;


/**
 * Created by Ayoola Ajebeku on 6/30/15.
 */
public class MainActivity extends ActionBarActivity implements DownloadProgressView.DownloadStatusListener {

    private Button downloadButton, playButton;
    private long downloadID;
    private String downloadURL = "http://bit.ly/1g5BfyR";
    private DownloadManager downloadManager;
    private DownloadProgressView downloadProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);

        downloadProgressView = (DownloadProgressView) findViewById(R.id.downloadProgressView);
        downloadButton = (Button) findViewById(R.id.downloadButton);
        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(downloadURL));
                request.setTitle("You garrit");
                request.setDescription("DownloadProgress sample");
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalFilesDir(getApplicationContext(), null, "you_garrit.mp4");
                request.allowScanningByMediaScanner();

                downloadID = downloadManager.enqueue(request);
                downloadProgressView.show(downloadID, MainActivity.this);
                downloadButton.setVisibility(View.GONE);
            }
        });

        playButton = (Button) findViewById(R.id.playButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(getFileUri()), "video/*");
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public File getFileUri() {
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(downloadID);
        Cursor cur = downloadManager.query(query);

        if (cur.moveToFirst()) {
            int columnIndex = cur.getColumnIndex(DownloadManager.COLUMN_STATUS);
            if (DownloadManager.STATUS_SUCCESSFUL == cur.getInt(columnIndex)) {
                String uriString = cur.getString(cur.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                return new File(Uri.parse(uriString).getPath());
            }
        }

        return null;
    }

    @Override
    public void downloadFailed(int reason) {
        System.err.println("Failed :" + reason);
        downloadButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void downloadSuccessful() {
        playButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void downloadCancelled() {
        downloadButton.setVisibility(View.VISIBLE);
    }
}
