package com.daomingedu.ijkplayertest;

/**
 * Created by jianhongxu on 2017/7/24.
 */

public interface OnBtnListener  {
    int TYPE_INIT_PLAY =3;
    int TYPE_PASUE = 0;
    int TYPE_PLAY = 1;
    int TYPE_COMPLETE = 2;
    void onBtn(int type);

//    void onTime(String time);
}
