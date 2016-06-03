package com.nnmoo.www.words_puzzle;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ImageView imageView_start_game;
    ImageView imageView_rule;
    ImageView imageView_logo;

    MediaPlayer beep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //beep声音
        beep=MediaPlayer.create(this,R.raw.beep);

        //开始游戏按钮事件
        imageView_start_game=(ImageView) findViewById(R.id.imageView_start_game);
        imageView_start_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation_alpha=new AlphaAnimation(1.0f,0.0f);
                animation_alpha.setDuration(300);
                imageView_start_game.startAnimation(animation_alpha);
                beep.start();
                Intent intent_sart_game=new Intent("com.nnmoo.www.words_puzzle.GameActivity");
                startActivity(intent_sart_game);
            }
        });
        //规则按钮事件
        imageView_rule=(ImageView) findViewById(R.id.imageView_rule);
        imageView_rule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation_alpha=new AlphaAnimation(1.0f,0.0f);
                animation_alpha.setDuration(300);
                imageView_rule.startAnimation(animation_alpha);
                beep.start();
                Intent intent_rule=new Intent("com.nnmoo.www.words_puzzle.GuideActivity");
                startActivity(intent_rule);
            }
        });
        //词logo动画事件
        imageView_logo=(ImageView)findViewById(R.id.imageView_words_logo);
        final Animation animation_alpha=new AlphaAnimation(1.0F,0.0F);
        final Animation animation_alpha2=new AlphaAnimation(0.0F,1.0F);
        imageView_logo.startAnimation(animation_alpha);
        animation_alpha.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imageView_logo.startAnimation(animation_alpha2);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        animation_alpha2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imageView_logo.startAnimation(animation_alpha);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        // imageView_logo.startAnimation(animation_alpha2);
        animation_alpha.setDuration(5000);
        //animation_alpha.setRepeatCount(Animation.INFINITE);
        animation_alpha2.setDuration(5000);
        // animation_alpha2.setRepeatCount(Animation.INFINITE);
        // animation_alpha2.setStartOffset(5000);


    }
}
