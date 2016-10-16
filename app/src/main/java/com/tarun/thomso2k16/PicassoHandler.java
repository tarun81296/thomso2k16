package com.tarun.thomso2k16;

import com.squareup.picasso.Callback;

/**
 * Created by tarun on 13-10-2016.
 */

public class PicassoHandler {
  /*  private String mDirectory;
    private String mFileName;
    private ImageView mContainer;
    @Inject
    App mApplication;

    public PicassoHandler(String fileName, ImageView container, String directory) {
        this.mFileName = fileName;
        this.mContainer = container;
        this.mDirectory = directory;
        this.getStorageDir();
    }

    @Override
    public void onSuccess() {
        if (this.isExternalStorageWritable()) {
            final Bitmap bitmap = ((BitmapDrawable) this.mContainer.getDrawable()).getBitmap();
            new AsyncTask<Void, Void, File>() {
                @Override
                protected File doInBackground(Void... params) {
                    File file = null;
                    try {
                        file = new File(PicassoHandler.this.getStorageDir().getPath().concat("/").concat(PicassoHandler.this.mFileName.concat(Constants.MEDIA_EXTENSION)));
                        file.createNewFile();
                        FileOutputStream ostream = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, ostream);
                        ostream.close();
                    } catch (Exception e) {
                        Log.e("debug", "External Storage is not available");
                    }
                    return file;
                }
            }.execute();
        } else {
            Log.e("debug", "External Storage is not available");
        }
    }

    @Override
    public void onError() {

    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public File getStorageDir() {
        File file = new File(Environment.getExternalStorageDirectory(), Constants.MEDIA_DIRECTORY.concat(this.mDirectory));
        if (!file.mkdirs()) {
        }
        return file;
    }*/
}
