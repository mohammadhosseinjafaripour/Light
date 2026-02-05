package com.jfp.light.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.jfp.light.Config;
import com.jfp.light.Custom.TypeWriter;
import com.jfp.light.EnglishNumberToWords;
import com.jfp.light.Model.ClueModel;
import com.jfp.light.R;
import com.jfp.light.Receiver.NetworkChangeReceiver;
import com.jfp.light.Security;
import com.jfp.light.Utils;
import com.jfp.light.VolleyErrorHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity {

    private int currentApiVersion;
    ImageView intro_img, full_screen_img, loading_img;
    Button start_btn, ok_btn;
    TextView title, clue_tv, level_tv;
    TypeWriter message_tp, question_title, notice_tv;
    boolean net_status = false;
    boolean is_first_time = true;
    NetworkChangeReceiver networkChangeReceiver = null;
    MediaPlayer mediaPlayer = null;
    Utils utils;
    SignInButton sign_in_button;
    int RC_SIGN_IN = 007;
    GoogleApiClient mGoogleApiClient;
    private GoogleSignInClient googleSignInClient;
    SharedPreferences.Editor editor;
    LinearLayout first_row;
    RelativeLayout level_relative;
    Boolean dont_work = true;
    EditText[] edt_id = null;
    String user_phrase;
    int t;
    int b;
    int l;
    int r;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentApiVersion = Build.VERSION.SDK_INT;
        Utils.fullScreen(MainActivity.this);
        Utils.changeStatusBar(MainActivity.this);
        setContentView(R.layout.activity_main);
        find();
        init();
        initKeyBoardListener();

        List<String> pack = utils.checkApp();
        if (pack.size() != 0) {
            message_tp.setText("");
            message_tp.setCharacterDelay(70);
            message_tp.animateText("Your phone contains hacking/cracking tools uninstall them and try again.");
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    dont_work = false;
                    ok_btn.animate().setDuration(1000).alpha(1);
                }
            }, 6000);
        } else {

            init_intro();
            Listener();
            connectivityChecker(MainActivity.this);
        }

    }

    public void find() {
        intro_img = findViewById(R.id.intro_img);
        start_btn = findViewById(R.id.start_btn);
        full_screen_img = findViewById(R.id.full_screen_img);
        title = findViewById(R.id.title);
        ok_btn = findViewById(R.id.ok_btn);
        message_tp = findViewById(R.id.message_tp);
        sign_in_button = findViewById(R.id.sign_in_button);
        intro_img.animate().setDuration(0).alpha(0);
        start_btn.animate().setDuration(0).alpha(0);
        full_screen_img.animate().setDuration(0).alpha(0);
        title.animate().setDuration(0).alpha(0);
        ok_btn.animate().setDuration(0).alpha(0);
        sign_in_button.animate().setDuration(0).alpha(0);

        if (isAlreadyLogin()) {
            sign_in_button.setVisibility(View.GONE);
        }
    }

    public void init_intro() {
        Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                intro_img.animate().setDuration(1000).alpha(1);
                title.animate().setDuration(1000).alpha(1);
                Glide.with(MainActivity.this).asGif().load(R.raw.loader).into(intro_img);
                mediaPlayer = Utils.playMp3(MainActivity.this, "background.mp3");
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        intro_img.animate().setDuration(1000).alpha(0);
                        title.animate().setDuration(1000).alpha(0);
                        Handler handler1 = new Handler();
                        handler1.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mediaPlayer.stop();

                                if (isAlreadyLogin()) {
                                    start_btn.animate().setDuration(1000).alpha(1);
                                } else {
                                    start_btn.animate().setDuration(3000).alpha(1);
                                }
                                sign_in_button.animate().setDuration(1000).alpha(1);
                                full_screen_img.animate().setDuration(1000).alpha(1);
                                if (Utils.isNetworkAvailable(MainActivity.this)) {
                                    Glide.with(MainActivity.this).asGif().load(R.raw.white_earth).into(full_screen_img);
                                    start_btn.setText("Start");
                                    start_btn.setTextColor(Color.BLACK);
                                    net_status = true;
                                } else {
                                    Glide.with(MainActivity.this).asGif().load(R.raw.red_earth).into(full_screen_img);
                                    start_btn.setText("Connection Error");
                                    start_btn.setTextColor(Color.parseColor("#e20000"));
                                }
                                is_first_time = false;
                            }
                        }, 1000);
                    }
                }, 5000);
            }
        }, 1000);

    }

    public void init() {
        utils = new Utils(MainActivity.this);

        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!dont_work)
                    finish();
            }
        });

    }

    public void Listener() {
        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (net_status) {
                    start_btn.animate().setDuration(1000).alpha(0);
                    Glide.with(MainActivity.this).asGif().load(R.raw.explode).listener(new RequestListener<GifDrawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<GifDrawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GifDrawable resource, Object model, Target<GifDrawable> target, DataSource dataSource, boolean isFirstResource) {
                            resource.setLoopCount(1);
                            resource.registerAnimationCallback(new Animatable2Compat.AnimationCallback() {
                                @Override
                                public void onAnimationEnd(Drawable drawable) {
                                    full_screen_img.animate().alpha(0).setDuration(0);
                                    prePareQuestion();
                                }
                            });
                            return false;
                        }
                    }).into(full_screen_img);
                }
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.google_api_key_me))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(MainActivity.this)
                .enableAutoManage(MainActivity.this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toasty.error(MainActivity.this, "Error in Login", Toast.LENGTH_SHORT).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);


        sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isNetworkAvailable(MainActivity.this)) {

                    Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                    startActivityForResult(signInIntent, RC_SIGN_IN);
                }
            }
        });

    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();

            String email = acct.getEmail();
            String token = acct.getIdToken();
            String name = acct.getDisplayName();
            String url = Config.url + getString(R.string.register);

            Map<String, String> params = new HashMap();
            params.put("google_token", token);
            params.put("email", email);
            params.put("name", name);

            JSONObject parameters = new JSONObject(params);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(final JSONObject response) {
                    try {
                        editor = getSharedPreferences("Light", Context.MODE_PRIVATE).edit();
                        if (response.getString("status").equals("true")) {

                            editor.putString("token", response.getString("token"));
                            editor.putString("is_login", "true");
                            editor.putString("is_cheater", "false");
                            editor.apply();
                            start_btn.animate().setDuration(0).alpha(0);
                            sign_in_button.animate().setDuration(1000).alpha(0).setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    sign_in_button.setVisibility(View.GONE);
                                    super.onAnimationEnd(animation);
                                }
                            });
                            start_btn.animate().setDuration(2000).alpha(1);
                        } else {

                            googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    editor.putString("is_login", "false");
                                    editor.apply();
                                }
                            });

                            editor.putString("is_cheater", "true");
                            editor.apply();
                            final String message = response.getString("message");
                            sign_in_button.animate().alpha(0).setDuration(1000);
                            full_screen_img.animate().alpha(0).setDuration(1000);
                            start_btn.animate().alpha(0).setDuration(1000);
                            Handler handler2 = new Handler();
                            handler2.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    sign_in_button.setVisibility(View.GONE);
                                    full_screen_img.setVisibility(View.GONE);
                                    start_btn.setVisibility(View.GONE);
                                    message_tp.setText("");
                                    message_tp.setCharacterDelay(70);
                                    message_tp.animateText(message);
                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            dont_work = false;
                                            ok_btn.animate().setDuration(1000).alpha(1);
                                        }
                                    }, 5000);
                                }
                            }, 1000);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    String error_string = VolleyErrorHelper.getMessage(error, MainActivity.this);
                    Toasty.error(MainActivity.this, error_string, Toast.LENGTH_SHORT).show();

                }
            });
            request.setShouldCache(false);
            Volley.newRequestQueue(MainActivity.this).add(request);

        } else {
            // Signed out, show unauthenticated UI.
        }
    }

    @SuppressLint("NewApi")
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (currentApiVersion >= Build.VERSION_CODES.KITKAT && hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    public void connectivityChecker(Context context) {
        IntentFilter intentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        networkChangeReceiver = new NetworkChangeReceiver();
        context.registerReceiver(networkChangeReceiver, intentFilter);
        networkChangeReceiver.setConnectionChangeCallback(new NetworkChangeReceiver.ConnectionChangeCallback() {
            @Override
            public void onConnectionChange(boolean isConnected) {
                if (!is_first_time) {
                    if (isConnected) {
                        full_screen_img.animate().setDuration(1000).alpha(0);
                        sign_in_button.animate().alpha(1).setDuration(1000);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                full_screen_img.animate().setDuration(500).alpha(1);
                                Glide.with(MainActivity.this).asGif().load(R.raw.white_earth).into(full_screen_img);
                                start_btn.setText("Start");
                                start_btn.setTextColor(Color.BLACK);
                                net_status = true;
                            }
                        }, 1000);

                    } else {
                        full_screen_img.animate().setDuration(1000).alpha(0);
                        sign_in_button.animate().alpha(0).setDuration(1000);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                full_screen_img.animate().setDuration(500).alpha(1);
                                Glide.with(MainActivity.this).asGif().load(R.raw.red_earth).into(full_screen_img);
                                start_btn.setText("Connection Error");
                                start_btn.setTextColor(Color.parseColor("#e20000"));
                                net_status = false;
                            }
                        }, 1000);
                    }
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (networkChangeReceiver != null)
            unregisterReceiver(networkChangeReceiver);
        if (mediaPlayer != null)
            mediaPlayer.stop();
        super.onDestroy();
    }

    public boolean isAlreadyLogin() {
        GoogleSignInAccount alreadyloggedAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (alreadyloggedAccount != null) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }

    }

    public void prePareQuestion() {

        loading_img = findViewById(R.id.loading_img);

        loading_img.animate().setDuration(0).alpha(0);

        SharedPreferences prefs = getSharedPreferences("Light", Context.MODE_PRIVATE);
        String google_user_id = prefs.getString("token", "null");

        Map<String, String> params = new HashMap();
        params.put("google_user_id", google_user_id);

        String url = Config.url + getString(R.string.clue);
        JSONObject parameters = new JSONObject(params);

        Glide.with(MainActivity.this).asGif().load(R.raw.loading).into(loading_img);

        loading_img.animate().alpha(1).setDuration(1000);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getString("status").equals("true")) {
                        loading_img.animate().setDuration(1000).alpha(0);

                        final ClueModel clueModel = new ClueModel(
                                response.getString("id"),
                                response.getString("title"),
                                response.getString("answer"),
                                response.getString("length"),
                                response.getString("bounty"),
                                response.getString("max_attempt"),
                                response.getString("package_url"));

                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                setContentView(R.layout.question);

                                clue_tv = findViewById(R.id.clue_tv);
                                question_title = findViewById(R.id.question_title);
                                first_row = findViewById(R.id.first_row);

                                level_tv = findViewById(R.id.level_tv);
                                notice_tv = findViewById(R.id.notice_tv);
                                level_relative = findViewById(R.id.level_relative);


                                clue_tv.animate().alpha(0).setDuration(0);
                                question_title.animate().alpha(0).setDuration(0);
                                first_row.animate().alpha(0).setDuration(0);
                                level_tv.animate().alpha(0).setDuration(0);
                                notice_tv.animate().alpha(0).setDuration(0);


                                level_tv.setText("Level " + EnglishNumberToWords.convert(Long.parseLong(clueModel.getId())));
                                notice_tv.setText("Notice that you've only got " + EnglishNumberToWords.convert(Long.parseLong(clueModel.getMax_attempt())) + " attempt each day.");
                                level_tv.animate().setDuration(1000).alpha(1);


                                Handler handler4 = new Handler();
                                handler4.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        notice_tv.animate().alpha(1).setDuration(1000);

                                    }
                                }, 1000);

                                Handler handler5 = new Handler();
                                handler5.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        level_tv.animate().setDuration(1000).alpha(0);
                                    }
                                }, 5000);
                                Handler handler6 = new Handler();
                                handler6.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        notice_tv.animate().alpha(0).setDuration(1000);
                                        Handler handler6 = new Handler();
                                        handler6.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                level_relative.setVisibility(View.GONE);
                                            }
                                        }, 1000);
                                    }
                                }, 6000);

                                Handler handler8 = new Handler();
                                handler8.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {


                                        //here
                                        Animation hide = AnimationUtils.loadAnimation(MainActivity.this, R.anim.top_to_bottom);
                                        clue_tv.animate().setDuration(0).alpha(1);
                                        clue_tv.startAnimation(hide);

                                        Handler handler1 = new Handler();
                                        handler1.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                question_title.animate().alpha(1).setDuration(1000);
                                                question_title.setText("");
                                                question_title.setCharacterDelay(70);
                                                String title = clueModel.getTitle();
                                                question_title.animateText(Security.decrypt(title, getString(R.string.parsing_key)));
                                            }
                                        }, 1000);

                                        String length = clueModel.getLength();
                                        edt_id = new EditText[Integer.parseInt(Security.decrypt(length, getString(R.string.parsing_key)))];
                                        for (int i = 0; i < Integer.parseInt(Security.decrypt(length, getString(R.string.parsing_key))); i++) {

                                            EditText editText = new EditText(new ContextThemeWrapper(MainActivity.this, R.style.EditTextTheme));
                                            edt_id[i] = editText;
                                            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(131, 131);
                                            lp.setMargins(13, 13, 13, 13);
                                            editText.setLayoutParams(lp);
                                            editText.setGravity(Gravity.CENTER);
                                            editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});
                                            editText.setTextSize(30);
                                            editText.setId(i);
                                            editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
                                            editText.setTextColor(Color.WHITE);
                                            if (i == (Integer.parseInt(Security.decrypt(length, getString(R.string.parsing_key))) - 1)) {
                                                editText.setImeOptions(EditorInfo.IME_ACTION_DONE | EditorInfo.IME_FLAG_NO_EXTRACT_UI);
                                            } else {
                                                editText.setImeOptions(EditorInfo.IME_ACTION_NEXT | EditorInfo.IME_FLAG_NO_EXTRACT_UI);
                                            }


                                            first_row.addView(editText);
                                        }
                                        for (int i = 0; i < Integer.parseInt(Security.decrypt(length, getString(R.string.parsing_key))); i++) {
                                            final int finalI = i;
                                            edt_id[i].addTextChangedListener(new TextWatcher() {
                                                @Override
                                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                                }

                                                @Override
                                                public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                    if (s.toString().length() == 1) {
                                                        if (finalI < edt_id.length - 1) {
                                                            edt_id[finalI].clearFocus();
                                                            edt_id[finalI + 1].requestFocus();
                                                            edt_id[finalI + 1].setCursorVisible(true);
                                                        }
                                                    }
                                                }

                                                @Override
                                                public void afterTextChanged(Editable s) {
                                                    if (s.toString().length() == 0) {
                                                        if (finalI < edt_id.length) {
                                                            if (finalI > 0) {
                                                                edt_id[finalI].clearFocus();
                                                                edt_id[finalI - 1].requestFocus();
                                                                edt_id[finalI - 1].setCursorVisible(true);
                                                            }
                                                        }
                                                    }
                                                }
                                            });
                                        }


                                        edt_id[Integer.parseInt(Security.decrypt(length, getString(R.string.parsing_key))) - 1].setOnEditorActionListener(new TextView.OnEditorActionListener() {
                                            @Override
                                            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                                                if (actionId == EditorInfo.IME_ACTION_DONE) {
                                                    user_phrase = "";
                                                    for (int i = 0; i < edt_id.length; i++) {
                                                        user_phrase += edt_id[i].getText().toString();
                                                    }
                                                    if (user_phrase.toLowerCase().equals(Security.decrypt(clueModel.getAnswer(), getString(R.string.parsing_key)))) {
                                                        for (int i = 0; i < edt_id.length; i++) {
                                                            edt_id[i].setTextColor(Color.parseColor("#388E3C"));
                                                        }
                                                    } else {
                                                        for (int i = 0; i < edt_id.length; i++) {
                                                            edt_id[i].setTextColor(Color.RED);
                                                        }
                                                    }
                                                }
                                                return false;
                                            }
                                        });


                                        Handler handler2 = new Handler();
                                        handler2.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                first_row.animate().alpha(1).setDuration(1000);

                                            }
                                        }, title.length() * 70 * 10);

                                        //end


                                    }
                                }, 8000);


                            }
                        }, 1000);


                    } else {
                        Toasty.error(MainActivity.this, response.getString("message"), Toasty.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String error_string = VolleyErrorHelper.getMessage(error, MainActivity.this);
                Toasty.error(MainActivity.this, error_string, Toast.LENGTH_SHORT).show();
            }
        });

        request.setShouldCache(false);
        Volley.newRequestQueue(MainActivity.this).add(request);

    }

    private void initKeyBoardListener() {
        // Минимальное значение клавиатуры.
        // Threshold for minimal keyboard height.
        final int MIN_KEYBOARD_HEIGHT_PX = 150;
        // Окно верхнего уровня view.
        // Top-level window decor view.
        final View decorView = getWindow().getDecorView();
        // Регистрируем глобальный слушатель. Register global layout listener.
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            // Видимый прямоугольник внутри окна.
            // Retrieve visible rectangle inside window.
            private final Rect windowVisibleDisplayFrame = new Rect();
            private int lastVisibleDecorViewHeight;

            @Override
            public void onGlobalLayout() {
                decorView.getWindowVisibleDisplayFrame(windowVisibleDisplayFrame);
                final int visibleDecorViewHeight = windowVisibleDisplayFrame.height();

                if (lastVisibleDecorViewHeight != 0) {

                    if (lastVisibleDecorViewHeight > visibleDecorViewHeight + MIN_KEYBOARD_HEIGHT_PX) {
                        Log.d("Pasha", "SHOW");
                         t = ((LinearLayout.LayoutParams) question_title.getLayoutParams()).topMargin;
                         b = ((LinearLayout.LayoutParams) question_title.getLayoutParams()).bottomMargin;
                         l = ((LinearLayout.LayoutParams) question_title.getLayoutParams()).leftMargin;
                         r = ((LinearLayout.LayoutParams) question_title.getLayoutParams()).rightMargin;
                        ((LinearLayout.LayoutParams) question_title.getLayoutParams()).setMargins(l,t+120,r,b);

                    } else if (lastVisibleDecorViewHeight + MIN_KEYBOARD_HEIGHT_PX < visibleDecorViewHeight) {
                        Log.d("Pasha", "HIDE");
                        ((LinearLayout.LayoutParams) question_title.getLayoutParams()).setMargins(l,t,r,b);

                    }
                }
                // Сохраняем текущую высоту view до следующего вызова.
                // Save current decor view height for the next call.
                lastVisibleDecorViewHeight = visibleDecorViewHeight;
            }
        });
    }
}
