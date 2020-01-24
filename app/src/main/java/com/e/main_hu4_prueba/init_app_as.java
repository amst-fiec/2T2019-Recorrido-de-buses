package com.e.main_hu4_prueba;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class init_app_as extends AppCompatActivity {
    private Button btn_pasajero,btn_conductor, btn_registro;
    public static String Listener;
    private VideoView videoBG;
    MediaPlayer mMediaPlayer;
    int mCurrentVideoPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init_app_as);
        btn_pasajero=(Button)findViewById(R.id.btn_init_pasajero);
        btn_conductor=(Button)findViewById(R.id.btn_init_conductor);
        btn_registro= (Button) findViewById(R.id.btn_init_regis);
        //metodo para ver el video
        Videothing();

        btn_pasajero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Listener para saber si el boton pasajero fue presionado
                Listener="btn_pasajero";
                //lanza a menu inicio sesion (login)
                startActivity(new Intent(com.e.main_hu4_prueba.init_app_as.this, login_user.class));
            }
        });

        btn_conductor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Listener para saber si el boton pasajero fue presionado
                Listener="btn_conductor";
                //lanza a menu inicio sesion (login)
                startActivity(new Intent(com.e.main_hu4_prueba.init_app_as.this, login_user.class));
            }
        });
        btn_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //lamza a menu registrarse (signin)
                startActivity(new Intent(com.e.main_hu4_prueba.init_app_as.this, Signin_user.class));
            }
        });
    }


    public static void setListener(String listener){
        Listener=listener;
    }

    public String getListener(){
        return this.Listener;
    }

    //hasta abajo lo del video

    private void Videothing() {

        videoBG = (VideoView) findViewById(R.id.videoView);
        Uri uri = Uri.parse("android.resource://" // First start with this,
                + getPackageName() // then retrieve your package name,
                + "/" // add a slash,
                + R.raw.fire);
        videoBG.setVideoURI(uri);
        // Start the VideoView
        videoBG.start();
        videoBG.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mMediaPlayer = mediaPlayer;
                // We want our video to play over and over so we set looping to true.
                mMediaPlayer.setLooping(true);
                // We then seek to the current posistion if it has been set and play the video.
                if (mCurrentVideoPosition != 0) {
                    mMediaPlayer.seekTo(mCurrentVideoPosition);
                    mMediaPlayer.start();
                }
            }
        });
    }
    @Override
    protected void onPause() {
        super.onPause();
        // Capture the current video position and pause the video.
        mCurrentVideoPosition = mMediaPlayer.getCurrentPosition();
        videoBG.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Restart the video when resuming the Activity
        videoBG.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // When the Activity is destroyed, release our MediaPlayer and set it to null.
        mMediaPlayer.release();
        mMediaPlayer = null;
    }


}
