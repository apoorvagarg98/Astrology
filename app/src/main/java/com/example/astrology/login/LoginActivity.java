package com.example.astrology.login;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.permissionx.guolindev.PermissionX;
import com.zego.express.ExpressManager;
import com.zego.express.ZegoMediaOptions;

import org.json.JSONObject;

import com.example.astrology.databinding.ActivityLoginBinding;
import com.example.astrology.express.AppCenter;
import im.zego.zegoexpress.callback.IZegoRoomLoginCallback;
import im.zego.zegoexpress.entity.ZegoUser;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private static final String TAG = "LoginActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.username.setText(Build.MANUFACTURER);

        initZEGOExpressSDK();
        binding.username.setText(getIntent().getStringExtra("name"));
        binding.roomid.setText(getIntent().getStringExtra("roomid"));
        binding.loginVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onJoinButtonClicked(true);
            }
        });

        binding.loginAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onJoinButtonClicked(false);
            }
        });
    }

    private void onJoinButtonClicked(boolean video) {
        if (!checkAppID()) {
            Toast.makeText(getApplication(),
                "please set your appID to AppCenter.java", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!validateInput()) {
            Toast.makeText(getApplication(),
                "input cannot be null", Toast.LENGTH_SHORT).show();
            return;
        }
        PermissionX.init(LoginActivity.this)
            .permissions(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
            .onExplainRequestReason((scope, deniedList) -> {
                scope.showRequestReasonDialog(deniedList, "Core fundamental are based on these permissions",
                    "OK", "Cancel");
            })
            .request((allGranted, grantedList, deniedList) -> {
                if (allGranted) {
                    int mediaOptions;
                    if (video) {
                        mediaOptions = ZegoMediaOptions.autoPlayAudio | ZegoMediaOptions.autoPlayVideo |
                            ZegoMediaOptions.publishLocalAudio | ZegoMediaOptions.publishLocalVideo;
                    } else {
                        mediaOptions = ZegoMediaOptions.autoPlayAudio | ZegoMediaOptions.publishLocalAudio;
                    }

                    String username = binding.username.getText().toString();
                    String roomID = binding.roomid.getText().toString();
                    joinRoom(roomID, username, mediaOptions, new IZegoRoomLoginCallback() {
                        @Override
                        public void onRoomLoginResult(int errorCode, JSONObject jsonObject) {
                            if (errorCode == 0) {
                                Intent intent;
                                if (video) {
                                    intent = new Intent(LoginActivity.this, VideoActivity.class);
                                } else {
                                    intent = new Intent(LoginActivity.this, AudioActivity.class);
                                }
                                startActivity(intent);
                            }
                        }
                    });
                }
            });
    }

    private void joinRoom(String roomID, String username, int mediaOptions, IZegoRoomLoginCallback callback) {
        binding.loading.setVisibility(View.VISIBLE);
        String userID = System.currentTimeMillis() + "";
        ZegoUser user = new ZegoUser(userID, username);
        String token = ExpressManager.generateToken(userID, AppCenter.appID, AppCenter.serverSecret);

        ExpressManager.getInstance().joinRoom(roomID, user, token, mediaOptions, new IZegoRoomLoginCallback() {
            @Override
            public void onRoomLoginResult(int errorCode, JSONObject jsonObject) {
                binding.loading.setVisibility(View.GONE);
                if (errorCode != 0) {
                    Toast.makeText(getApplication(), "join room failed,errorCode :" + errorCode,
                        Toast.LENGTH_LONG).show();
                }
                if (callback != null) {
                    callback.onRoomLoginResult(errorCode, jsonObject);
                }
            }
        });
    }

    private void initZEGOExpressSDK() {
        ExpressManager.getInstance().createEngine(getApplication(), AppCenter.appID);
        PermissionX.init(this)
            .permissions(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
            .request((allGranted, grantedList, deniedList) -> {
            });
    }

    private boolean validateInput() {
        return binding.username.getText().length() > 0 && binding.roomid.getText().length() > 0;
    }

    private boolean checkAppID() {
        return AppCenter.appID != 0L && !TextUtils.isEmpty(AppCenter.serverSecret);
    }

}