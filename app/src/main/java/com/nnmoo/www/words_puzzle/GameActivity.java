package com.nnmoo.www.words_puzzle;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {
    private int red_scores = 0;
    private int blue_scores = 0;
    private TextView textView_red;
    private TextView textView_blue;
    private ImageView imageView_next;
    private ImageView imageView_red;
    private ImageView imageView_blue;
    private TextView textView_show_words;
    MediaPlayer beep;
    MediaPlayer drum_song;
    MediaPlayer drum_end;
    ImageView imageView_play;
    Animation animation_alpha;
    Animation animation_scale;
    AnimationSet animation_set;
    Animation animation_rotate;
    Animation animation_alpha_play;
    Timer timer;
    Random random;
    String[] words;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        //////////////////////////////////////////////////
        //textView_show_words
        textView_show_words = (TextView) findViewById(R.id.textView_show_words);
        //beep声音
        beep = MediaPlayer.create(this, R.raw.beep);
        //drum声音设置
        drum_song = MediaPlayer.create(this, R.raw.drum_song);
        drum_end = MediaPlayer.create(this, R.raw.drum_for_end);
        AnimationSetup();//动画设置
        //next按钮
        imageView_next = (ImageView) findViewById(R.id.imageView_next);
        imageView_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView_next.startAnimation(animation_alpha);
                beep.start();
            }
        });
        //red按钮事件
        imageView_red = (ImageView) findViewById(R.id.imageView_red);
        imageView_red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView_red.startAnimation(animation_scale);
                red_scores++;
                textView_red.setText("红队："+red_scores + "分");
                beep.start();
            }
        });
        //blue按钮事件
        imageView_blue = (ImageView) findViewById(R.id.imageView_blue);
        imageView_blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView_blue.startAnimation(animation_scale);
                blue_scores++;
                textView_blue.setText("蓝队："+blue_scores + "分");
                beep.start();
            }
        });
        //play按钮事件
        imageView_play = (ImageView) findViewById(R.id.imageView_play);
        imageView_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                beep.start();
                if (drum_song.isPlaying()) {
                    drum_song.stop();
                    imageView_play.clearAnimation();
                    textView_show_words.setText("点播放按钮开始");
                    imageView_play.setImageResource(R.drawable.play);
                    if(timer!=null)
                        timer.cancel();
                } else {
                    reset_drum_song();
                    imageView_play.startAnimation(animation_set);
                    drum_song.start();
                    textView_show_words.setText("点下一个按钮");
                    imageView_play.setImageResource(R.drawable.pause);
                    start_game();
                }
            }
        });
        //textView_red/blue事件
        textView_red = (TextView) findViewById(R.id.textView_red);
        textView_blue = (TextView) findViewById(R.id.textView_blue);
    }
        //重置声音资源
    private void reset_drum_song() {
        drum_song = MediaPlayer.create(this, R.raw.drum_song);
    }

    protected void onPause() {
        super.onPause();
        back_to_wait_model();
    }

    //页面回来事件
    protected void onRestart() {
        super.onRestart();
        reset_drum_song();
    }

    //动画设置
    private void AnimationSetup() {
        animation_alpha = new AlphaAnimation(1.0F, 0.5F);
        animation_alpha.setDuration(200);
        animation_scale = new ScaleAnimation(1.0F, 0.95F, 1.0F, 0.95F);
        animation_scale.setDuration(200);
        animation_rotate = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation_alpha_play = new AlphaAnimation(1.0F, 0.5F);
        animation_rotate.setDuration(1000);
        animation_alpha_play.setDuration(1000);
        animation_rotate.setRepeatCount(Animation.INFINITE);
        animation_alpha_play.setRepeatCount(Animation.INFINITE);
        animation_set = new AnimationSet(false);
        animation_set.addAnimation(animation_rotate);
        animation_set.addAnimation(animation_alpha_play);
    }

    //start_game_event
    private void start_game() {
        random = new Random();
        timer = new Timer();
        int  rand= random.nextInt(60);
        long time_to_stop=rand*1000+1000;
        timer.schedule(new game_time_event(), time_to_stop);
    }

    //stop_Animation
    private void back_to_wait_model() {
        drum_song.stop();
        imageView_play.setImageResource(R.drawable.play);
        imageView_play.clearAnimation();
    }





    class game_time_event extends TimerTask {
        public void run() {

            drum_end.start();
            timer.cancel();
            /////////////////////////////////////////////////////////////////////////////////////
            //TimerTask runs on a different thread. So update ui on the main ui thread. Use runonuithread
            ////////////////////////////////////////////////////////////////////////////////////
            runOnUiThread(new Runnable() //run on ui thread
            {
                public void run()
                {
                    back_to_wait_model();
                }
            });

        }
    }
    }

