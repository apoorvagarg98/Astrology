package com.example.astrology;

import com.sinch.android.rtc.AudioController;
import com.sinch.android.rtc.ClientRegistration;
import com.sinch.android.rtc.Internals;
import com.sinch.android.rtc.Sinch;
import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.SinchClientListener;
import com.sinch.android.rtc.SinchError;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallClient;
import com.sinch.android.rtc.calling.CallClientListener;
import com.sinch.android.rtc.MissingPermissionException;
import com.sinch.android.rtc.internal.client.DefaultSinchClient;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

public class SinchService extends Service {

    /*
     IMPORTANT!

     This sample application was designed to provide the simplest possible way
     to evaluate Sinch Android SDK right out of the box, omitting crucial feature of handling
     incoming calls via managed push notifications, which requires registering in FCM console and
     procuring google-services.json in order to build and work.

     Android 8.0 (API level 26) imposes limitation on background services and we strongly encourage
     you to use Sinch Managed Push notifications to handle incoming calls when app is closed or in
     background or phone is locked.

     DO NOT USE THIS APPLICATION as a skeleton of your project!

     Instead, use:
     - sinch-rtc-sample-push (for audio calls) and
     - sinch-rtc-sample-video-push (for video calls)

     To ensure that SinchClient is not created more than once, make sure that SinchService lifecycle is managed correctly.
     E.g. in this sample apps we demonstrate that the service is bound in the BaseActivity, which is the parent
     class for all activities that uses SinchClient. So, whenever a new activity starts, it's being bound to SinchService.
     Thus, SinchService always has at least one `client` activity and is never terminated while the relevant activities are
     on the foreground. That ensures that there wwould be at most one instance of the SinchClient, which is especially important
     for the registration step.

     SinchClient entry points are 'start()' to make an outbound call, and `relayRemotePushNotificationPayload()` to handle
     an incoming call received via push notification.

     Useful links:

     Activity lifecycle: https://developer.android.com/guide/components/activities/activity-lifecycle
     Bound services lifecycle: https://developer.android.com/guide/components/bound-services#Lifecycle
     Navigating between activities: https://developer.android.com/guide/components/activities/activity-lifecycle.html#tba

    */

    private static final String APP_KEY = "4718998d-e7cc-49ce-ac0e-0281d438e391";
    private static final String APP_SECRET = "Mc1PXsckP0S2qclX3Oikmw==";
    private static final String ENVIRONMENT = "ocra.api.sinch.com";

    public static final int MESSAGE_PERMISSIONS_NEEDED = 1;
    public static final String REQUIRED_PERMISSION = "REQUIRED_PERMISSION";
    public static final String MESSENGER = "MESSENGER";
    private Messenger messenger;

    public static final String CALL_ID = "CALL_ID";
    static final String TAG = SinchService.class.getSimpleName();

    private SinchServiceInterface mSinchServiceInterface = new SinchServiceInterface();
    private SinchClient mSinchClient;
    private String mUserId;

    private StartFailedListener mListener;
    private PersistedSettings mSettings;

    @Override
    public void onCreate() {
        super.onCreate();
        mSettings = new PersistedSettings(getApplicationContext());
        attemptAutoStart();
    }

    private void attemptAutoStart() {
        String userName = mSettings.getUsername();
        if (!userName.isEmpty() && messenger != null) {
            start(userName);
        }
    }

    @Override
    public void onDestroy() {
        if (mSinchClient != null && mSinchClient.isStarted()) {
            mSinchClient.terminateGracefully();
        }
        super.onDestroy();
    }

    private void start(String userName) {
        boolean permissionsGranted = true;
        if (mSinchClient == null) {
            mSettings.setUsername(userName);
            createClient(userName);

        }
        try {
            //mandatory checks
            mSinchClient.checkManifest();
            // check for bluetooth for automatic audio routing
            if (getBaseContext().checkCallingOrSelfPermission(Manifest.permission.BLUETOOTH)
                    != PackageManager.PERMISSION_GRANTED) {
                throw new MissingPermissionException(Manifest.permission.BLUETOOTH);
            }
        } catch (MissingPermissionException e) {
            permissionsGranted = false;
            if (messenger != null) {
                Message message = Message.obtain();
                Bundle bundle = new Bundle();
                bundle.putString(REQUIRED_PERMISSION, e.getRequiredPermission());
                message.setData(bundle);
                message.what = MESSAGE_PERMISSIONS_NEEDED;
                try {
                    messenger.send(message);
                } catch (RemoteException e1) {
                    e1.printStackTrace();
                }
            }
        }
        if (permissionsGranted) {
            Log.d(TAG, "Starting SinchClient");
            mSinchClient.start();
        }
    }

    private void createClient(String userName) {
        mUserId = userName;
        mSinchClient = Sinch.getSinchClientBuilder().context(getApplicationContext()).userId(userName)
                .applicationKey(APP_KEY)
                .environmentHost(ENVIRONMENT).build();

        ((DefaultSinchClient)mSinchClient).setSupportActiveConnection(true);
        mSinchClient.startListeningOnActiveConnection();

        mSinchClient.addSinchClientListener(new MySinchClientListener());
        // Permission READ_PHONE_STATE is needed to respect native calls.
        mSinchClient.getCallClient().setRespectNativeCalls(true);
        mSinchClient.getCallClient().addCallClientListener(new SinchCallClientListener());
        mSinchClient.start();
    }

    private void stop() {
        if (mSinchClient != null) {
            mSinchClient.terminateGracefully();
            mSinchClient = null;
        }
        mSettings.setUsername("");
    }

    private boolean isStarted() {
        return (mSinchClient != null && mSinchClient.isStarted());
    }

    @Override
    public IBinder onBind(Intent intent) {
        messenger = intent.getParcelableExtra(MESSENGER);
        return mSinchServiceInterface;
    }

    public class SinchServiceInterface extends Binder {

        public Call callPhoneNumber(String phoneNumber) {
            return mSinchClient.getCallClient().callPhoneNumber(phoneNumber);
        }

        public Call callUser(String userId) {
            if (mSinchClient == null) {
                return null;
            }
            return mSinchClient.getCallClient().callUser(userId);
        }

        public String getUserName() {
            return mUserId;
        }

        public boolean isStarted() {
            return SinchService.this.isStarted();
        }

        public void retryStartAfterPermissionGranted() { SinchService.this.attemptAutoStart(); }

        public void startClient(String userName) {
            start(userName);
        }

        public void stopClient() {
            stop();
        }

        public void setStartListener(StartFailedListener listener) {
            mListener = listener;
        }

        public Call getCall(String callId) {
            return mSinchClient != null ? mSinchClient.getCallClient().getCall(callId) : null;
        }

        public AudioController getAudioController() {
            if (!isStarted()) {
                return null;
            }
            return mSinchClient.getAudioController();
        }
    }

    public interface StartFailedListener {
        void onFailed(SinchError error);

        void onStarted();
    }

    private class MySinchClientListener implements SinchClientListener {

        @Override
        public void onClientFailed(SinchClient client, SinchError error) {
            if (mListener != null) {
                mListener.onFailed(error);
            }
            Internals.terminateForcefully(mSinchClient);
            mSinchClient = null;
        }

        @Override
        public void onClientStarted(SinchClient client) {
            Log.d(TAG, "SinchClient started");
            if (mListener != null) {
                mListener.onStarted();
            }
        }

        @Override
        public void onLogMessage(int level, String area, String message) {
            switch (level) {
                case Log.DEBUG:
                    Log.d(area, message);
                    break;
                case Log.ERROR:
                    Log.e(area, message);
                    break;
                case Log.INFO:
                    Log.i(area, message);
                    break;
                case Log.VERBOSE:
                    Log.v(area, message);
                    break;
                case Log.WARN:
                    Log.w(area, message);
                    break;
            }
        }

        // The most secure way is to obtain the credentials is from the backend,
        // since storing the Application Secret in the client app is not safe.
        // Following code demonstrates how the JWT that serves as credential should be created,
        // provided the Application Key (APP_KEY), Application Secret (APP_SECRET) and User ID.
        // NB: JWT.create() should run on your backend, and return either valid JWT or signal that
        // user can't be registered.

        // In the first case, register user with Sinch using acuired JWT via clientRegistration.register(...).
        // In the latter - report failure by calling clientRegistration.registerFailed()

        @Override
        public void onCredentialsRequired(ClientRegistration clientRegistration) {
            clientRegistration.register(JWT.create(APP_KEY, APP_SECRET, mUserId));
        }

        @Override
        public void onUserRegistered() {
            Log.d(TAG, "User registered.");
        }

        @Override
        public void onUserRegistrationFailed(SinchError sinchError) {
            Log.e(TAG, "User registration failed: " + sinchError.getMessage());
        }

        @Override
        public void onPushTokenRegistered() {
            Log.w(TAG, "onPushTokenRegistered() should never been called in the application w/o Managed Push support.");
        }

        @Override
        public void onPushTokenRegistrationFailed(SinchError sinchError) {
            Log.w(TAG, "onPushTokenRegistrationFailed() should never been called in the application w/o Managed Push support.");
        }
    }

    private class SinchCallClientListener implements CallClientListener {

        @Override
        public void onIncomingCall(CallClient callClient, Call call) {
            Log.d(TAG, "Incoming call");
            Intent intent = new Intent(SinchService.this, IncomingCallScreenActivity.class);
            intent.putExtra(CALL_ID, call.getCallId());
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            SinchService.this.startActivity(intent);
        }
    }

    private class PersistedSettings {

        private SharedPreferences mStore;

        private static final String PREF_KEY = "Sinch";

        public PersistedSettings(Context context) {
            mStore = context.getSharedPreferences(PREF_KEY, MODE_PRIVATE);
        }

        public String getUsername() {
            return mStore.getString("Username", "");
        }

        public void setUsername(String username) {
            SharedPreferences.Editor editor = mStore.edit();
            editor.putString("Username", username);
            editor.commit();
        }
    }
}