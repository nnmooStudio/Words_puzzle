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
    String[] Words;
    private int Words_array_MAX;
    private static int Words_array_Subscript;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        //////////////////////////////////////////////////
        //Words array
        Words_array_MAX = 100;
        Words_array_Subscript = 0;
        Words=new String[Words_array_MAX];
        Initialize_Words();
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
                if (drum_song.isPlaying())
                {
                    if (Words_array_Subscript < Words.length)
                    {
                        textView_show_words.setText( Words[Words_array_Subscript]);
                        Words_array_Subscript++;
                    }
                    else {
                        Words_array_Subscript = 0;
                        Initialize_Words();
                        textView_show_words.setText (Words[Words_array_Subscript]);
                        Words_array_Subscript++;
                    }
                }
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
        long time_to_stop=rand*1000+45000;
        timer.schedule(new game_time_event(), time_to_stop);
    }

    //stop_Animation
    private void back_to_wait_model() {
        drum_song.stop();
        imageView_play.setImageResource(R.drawable.play);
        imageView_play.clearAnimation();
    }




//定时器任务线程
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
    //Resort the words array

    //////////////////////////////////////////////
    //Show different Words
    private void Initialize_Words()
    {
        Words[0] = "生气";
        Words[1] = "快乐";
        Words[2] = "伟大";
        Words[3] = "虎头蛇尾";
        Words[4] = "虚与委蛇";
        Words[5] = "大小通吃";
        Words[6] = "遗骸";
        Words[7] = "地鼠";
        Words[8] = "小心翼翼";
        Words[9] = "大胆";
        Words[10] = "锐利";
        Words[11] = "石块";
        Words[12] = "小人";
        Words[13] = "背包客";
        Words[14] = "雷霆";
        Words[15] = "惊雷";
        Words[16] = "痛快";
        Words[17] = "准备";
        Words[18] = "走了";
        Words[19] = "偶遇";
        Words[20] = "东北";
        Words[21] = "公园";
        Words[22] = "白杨树";
        Words[23] = "微风";
        Words[24] = "拜访";
        Words[25] = "冬瓜";
        Words[26] = "洋人";
        Words[27] = "扮猪吃老虎";
        Words[28] = "犹犹豫豫";
        Words[29] = "形容词";
        Words[30] = "诺基亚";
        Words[31] = "耳机";
        Words[32] = "低音炮";
        Words[33] = "超级赛亚人";
        Words[34] = "唐三藏";
        Words[35] = "坟墓";
        Words[36] = "长江";
        Words[37] = "九寨沟";
        Words[38] = "猜字谜";
        Words[39] = "消消乐";
        Words[40] = "封神榜";
        Words[41] = "高楼大厦";
        Words[42] = "娓娓道来";
        Words[43] = "功夫熊猫";
        Words[44] = "平板电脑";
        Words[45] = "古墓丽影";
        Words[46] = "维生素c";
        Words[47] = "抽油烟机";
        Words[48] = "野外露营";
        Words[49] = "吃西瓜";
        Words[50] = "晾衣服";
        Words[51] = "该睡觉了";
        Words[52] = "九点钟方向";
        Words[53] = "排队";
        Words[54] = "珠穆朗玛峰";
        Words[55] = "电脑桌";
        Words[56] = "测试";
        Words[57] = "火车票";
        Words[58] = "柏拉图";
        Words[59] = "阿拉丁神灯";
        Words[60] = "铁人三项";
        Words[61] = "龙卷风";
        Words[62] = "自由落体";
        Words[63] = "潸然泪下";
        Words[64] = "长颈鹿";
        Words[65] = "姜太公钓鱼";
        Words[66] = "伊甸园";
        Words[67] = "洗礼";
        Words[68] = "分析";
        Words[69] = "八字胡";
        Words[70] = "温酒斩华雄";
        Words[71] = "既生瑜何生亮";
        Words[72] = "南柯一梦";
        Words[73] = "曹操";
        Words[74] = "希腊";
        Words[75] = "常常喜乐";
        Words[76] = "唐老鸭";
        Words[77] = "安全帽";
        Words[78] = "亲，包邮不";
        Words[79] = "十二生肖";
        Words[80] = "知了";
        Words[81] = "批评";
        Words[82] = "新官上任三把火";
        Words[83] = "一诺千金";
        Words[84] = "福利";
        Words[85] = "诺亚方舟";
        Words[86] = "奥林匹克";
        Words[87] = "金字塔";
        Words[88] = "感冒发烧";
        Words[89] = "扭秧歌";
        Words[90] = "卓别林";
        Words[91] = "直升飞机";
        Words[92] = "旗袍";
        Words[93] = "外贸";
        Words[94] = "防御";
        Words[95] = "三分球";
        Words[96] = "耳钉";
        Words[97] = "钻石戒指";
        Words[98] = "纹身";
        Words[99] = "温故而知新";

        //打乱顺序
        Random rand = new Random();
        int tmp_int = 0;
        String tmp_string = "";
        for (int i = 0; i < Words.length; i++) {
            tmp_int = rand.nextInt(Words.length);
            if (Words[tmp_int]!=Words[i]) {
                tmp_string=Words[tmp_int];
                Words[tmp_int] = Words[i];
                Words[i] = tmp_string;
            }
        }

    }
    }

