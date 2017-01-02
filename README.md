# DownloadProgress
An android view showing a download progressBar, total size, downloadedSize, percentage downloaded and a cancel button of the android DownloadManager with just few lines of code. Just like Google Play downloading...

# Version

1.0.1


# Usage
To use this library in your android project, just simply add the following repositories and dependency into your build.gradle

```sh
repositories {
    maven {
        url 'https://dl.bintray.com/ayz4sci/maven/'
    }
}

dependencies {
    compile 'com.ayz4sci.androidfactory:downloadprogress:1.0.2'
}
```

Then place `com.ayz4sci.androidfactory.DownloadProgressView` wherever you want the downloadprogressview to show, for example:

```xml
    <com.ayz4sci.androidfactory.DownloadProgressView
    android:id="@+id/downloadProgressView"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_marginBottom="10dp"
    android:layout_marginTop="10dp"
    android:orientation="horizontal" />
```

You could get a DownloadProgressView instance through `findViewById` method.

```java
DownloadProgressView downloadProgressView = (DownloadProgressView) rootView.findViewById(R.id.downloadProgressView);
```
To show the downloadprogressview, call `show` method and pass the downloadID that you got from DownloadManager when you started the download and also set `DownloadStatusListener` to know when download is successful, failed or cancelled eg.
```java
DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
DownloadManager.Request request = new DownloadManager.Request(Uri.parse("YOUR_DOWNLOAD_URL"));
request.setTitle("TITLE");
request.setDescription("DESCRIPTION");
request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
request.setDestinationInExternalFilesDir(getApplicationContext(), null, "DOWNLOAD_FILE_NAME.mp4");
request.allowScanningByMediaScanner();
downloadID = downloadManager.enqueue(request);

downloadProgressView.show(downloadID, new DownloadProgressView.DownloadStatusListener() {
  @Override
  public void downloadFailed(int reason) {
     //Action to perform when download fails, reason as returned by DownloadManager.COLUMN_REASON
  }

  @Override
  public void downloadSuccessful() {
    //Action to perform on success
  }

  @Override
  public void downloadCancelled() {
    //Action to perform when user press the cancel button
  }
});
```


That's all. DownloadProgressView will handle the rest!

# Change Logs

### v1.0.1

Initial version

# License

Apache 2.0
