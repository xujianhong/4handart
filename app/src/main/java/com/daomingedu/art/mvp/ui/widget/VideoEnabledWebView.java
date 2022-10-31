package com.daomingedu.art.mvp.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import android.util.AttributeSet;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.daomingedu.art.bean.ConfirmBean;
import com.daomingedu.art.db.VideoList;
import com.daomingedu.art.mvp.ui.activity.CommonWebActivity;
import com.daomingedu.art.mvp.ui.activity.LocalVideoActivity;
import com.daomingedu.art.mvp.ui.activity.UploadActivity;
import com.daomingedu.art.mvp.ui.activity.UploadVideoListActivity;
import com.daomingedu.art.util.MyOkGoUtil;
import com.daomingedu.art.util.SharedPreferencesUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lzy.okgo.model.HttpParams;

import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.LitePal;

import java.io.File;
import java.util.Map;

import timber.log.Timber;

/**
 * This class serves as a WebView to be used in conjunction with a VideoEnabledWebChromeClient.
 * It makes possible:
 * - To detect the HTML5 video ended event so that the VideoEnabledWebChromeClient can exit full-screen.
 *
 * Important notes:
 * - Javascript is enabled by default and must not be disabled with getSettings().setJavaScriptEnabled(false).
 * - setWebChromeClient() must be called before any loadData(), loadDataWithBaseURL() or loadUrl() method.
 *
 * @author Cristian Perez (http://cpr.name)
 *
 */

public class VideoEnabledWebView extends WebView {
    public class JavascriptInterface
    {
        @android.webkit.JavascriptInterface
        public void shootVideo(){
            UploadVideoListActivity.Companion.startUploadVideoListActivity((AppCompatActivity) context, 0, 0,"",0);
        }

        @android.webkit.JavascriptInterface
        public void dropOutH5(){
//            Toast.makeText(context,"dropOutH5",Toast.LENGTH_LONG).show();
            ((CommonWebActivity)context).finish();
        }

        @android.webkit.JavascriptInterface
        public void notifyVideoEnd() // Must match Javascript interface method of VideoEnabledWebChromeClient
        {
            // This code is not executed in the UI thread, so we must force that to happen
            new Handler(Looper.getMainLooper()).post(new Runnable()
            {
                @Override
                public void run()
                {
                    if (videoEnabledWebChromeClient != null)
                    {
                        videoEnabledWebChromeClient.onHideCustomView();
                    }
                }
            });
        }

        @android.webkit.JavascriptInterface
        public void appFolder(String data){
            Timber.d(data);

            JsonObject json = new Gson().fromJson(data,JsonObject.class);
            Timber.d(json.get("folder").toString());
            LocalVideoActivity.Companion.startLocalVideoActivity((AppCompatActivity) context,json.get("folder").getAsString());
        }

        @android.webkit.JavascriptInterface
        public void emptyVideo(){
            new AlertDialog.Builder(context).setTitle("注意")
                    .setMessage("清除缓存,将清除拍摄的缓存文件!\n确定清除缓存吗?")
                    .setNegativeButton("取消",null)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            LitePal.deleteAll(VideoList.class);
                            File file = context.getExternalFilesDir("Movies");
                            if (file != null) {
                                if (file.exists()){
                                    File[] list = file.listFiles();
                                    if (list != null){
                                        for (File item : list) {
                                            if (item.getName().endsWith(".mp4")){
                                                item.delete();
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    })
                    .show();
        }

        @android.webkit.JavascriptInterface
        public void uploadVideoAppPage(String data) // Must match Javascript interface method of VideoEnabledWebChromeClient
        {
            Log.d("test", "1111");
            try {
                JSONObject jsonObject = new JSONObject(data);
                Log.d("test", new Gson().toJson(jsonObject));
                String url = jsonObject.optString("APIUrl");
                String videoId = jsonObject.optString("videoId");
                int isShowSongList = jsonObject.optInt("isShowSongList");
                int isShowImport = jsonObject.optInt("isShowImport");

                String folder = jsonObject.optString("folder");//拍摄视频的缓存文件夹名称

                /**
                 * 是1文件夹内只保存最后一次录制的视频；
                 * 是0就不限制存放的视频数量，按照目前的列表存放视频。
                 */
                int videoNum = jsonObject.optInt("videoNum");
                Log.d("test", url);
                Log.d("test", videoId);
                SharedPreferencesUtil.INSTANCE.setIsShowImport(context, isShowImport);
                getConfirmStatus(videoId, url, isShowSongList, isShowImport,folder,videoNum);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Log.d("test", new Gson().toJson(jsonObject));
            // This code is not executed in the UI thread, so we must force that to happen
        }

    }

    private void getConfirmStatus(String videoId, String url, int isShowSongList, int isShowImport,String folder,int videoNum){
        HttpParams httpParams = new HttpParams();
        httpParams.put("key", "715c714249094c5fb8c90a50f92c8bcd");
        httpParams.put("videoId", videoId);
        MyOkGoUtil.INSTANCE.postnew(context, url + "/uploadConfirm.do", httpParams, new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 0){
                    ConfirmBean confirmBean = ConfirmBean.Companion.getData((String) msg.obj);
                    if (confirmBean.isConfirm() == 1){
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage(confirmBean.getConfirmMsg());
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.create().show();
                    }else if (confirmBean.isConfirm() == 0){
                        UploadActivity.Companion.startUploadActivity((AppCompatActivity) context, videoId, url, isShowSongList, isShowImport,folder,videoNum);
                    }
                }
            }
        });
    }

    private VideoEnabledWebChromeClient videoEnabledWebChromeClient;
    private boolean addedJavascriptInterface;
    private Context context;

    public VideoEnabledWebView(Context context)
    {
        super(context);
        this.context = context;
        addedJavascriptInterface = false;
    }

    @SuppressWarnings("unused")
    public VideoEnabledWebView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.context = context;
        addedJavascriptInterface = false;
    }

    @SuppressWarnings("unused")
    public VideoEnabledWebView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        this.context = context;
        addedJavascriptInterface = false;
    }

    /**
     * Indicates if the video is being displayed using a custom view (typically full-screen)
     * @return true it the video is being displayed using a custom view (typically full-screen)
     */
    public boolean isVideoFullscreen()
    {
        return videoEnabledWebChromeClient != null && videoEnabledWebChromeClient.isVideoFullscreen();
    }

    /**
     * Pass only a VideoEnabledWebChromeClient instance.
     */
    @Override
    @SuppressLint("SetJavaScriptEnabled")
    public void setWebChromeClient(WebChromeClient client)
    {
        getSettings().setJavaScriptEnabled(true);

        if (client instanceof VideoEnabledWebChromeClient)
        {
            this.videoEnabledWebChromeClient = (VideoEnabledWebChromeClient) client;
        }

        super.setWebChromeClient(client);
    }

    @Override
    public void loadData(String data, String mimeType, String encoding)
    {
        addJavascriptInterface();
        super.loadData(data, mimeType, encoding);
    }

    @Override
    public void loadDataWithBaseURL(String baseUrl, String data, String mimeType, String encoding, String historyUrl)
    {
        addJavascriptInterface();
        super.loadDataWithBaseURL(baseUrl, data, mimeType, encoding, historyUrl);
    }

    @Override
    public void loadUrl(String url)
    {
        addJavascriptInterface();
        super.loadUrl(url);
    }

    @Override
    public void loadUrl(String url, Map<String, String> additionalHttpHeaders)
    {
        addJavascriptInterface();
        super.loadUrl(url, additionalHttpHeaders);
    }

    private void addJavascriptInterface()
    {
        if (!addedJavascriptInterface)
        {
            // Add javascript interface to be called when the video ends (must be done before page load)
            addJavascriptInterface(new JavascriptInterface(), "_VideoEnabledWebView"); // Must match Javascript interface name of VideoEnabledWebChromeClient

            addedJavascriptInterface = true;
        }
    }
}
