package com.tobosonet.android.english;

import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private String [] names;
    private Button[] mButtonNames;
    private int BUTTON_NAME = 333;
    private ImageView mTauroImage;
    private MediaPlayer FXPlayer;


    private int mCurrIndex;
    private int listIndex = 0;
    ArrayList<Integer> shuffledList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        names = getResources().getStringArray(R.array.taurons_names);
        mButtonNames = new Button[names.length];

        //shuffled list

        shuffledList = new ArrayList<Integer>();
        for (int i=0; i < names.length; i++) {
            shuffledList.add(i);
        }
        Collections.shuffle(shuffledList);

       // Random rn  = new Random();

       // mCurrIndex = rn.nextInt(names.length);
        mCurrIndex = shuffledList.get(0);
        mTauroImage = (ImageView) findViewById(R.id.iv_tauro_pic);

        TypedArray picArray = getResources().obtainTypedArray(R.array.taurons_pics);

        int pr = picArray.getResourceId(mCurrIndex,-1);
        mTauroImage.setImageResource(pr);

        LinearLayout layout = (LinearLayout) findViewById(R.id.ll_name_buttons);

        for (int i= 0; i < names.length; i++) {
            mButtonNames[i] = new Button(this);
            mButtonNames[i].setText(names[i]);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(5,5,5,5);
            mButtonNames[i].setLayoutParams(lp);
            mButtonNames[i].setTag(i);
            mButtonNames[i].setTextColor(Color.WHITE);
            mButtonNames[i].setBackgroundColor(Color.parseColor("#303F9F"));
            layout.addView(mButtonNames[i]);
            mButtonNames[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int button = (int) v.getTag();
                    if (button == mCurrIndex) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                        builder.setMessage("MOLT BÉ!");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                success();

                                if(FXPlayer != null)
                                {
                                    FXPlayer.stop();
                                    FXPlayer.release();
                                    FXPlayer = null;
                                }
                                return;
                            }
                        });

                        builder.setView(getLayoutInflater().inflate(R.layout.success_dialog, null));

                        AlertDialog dialog = builder.create();
                        //dialog.getWindow().setBackgroundDrawableResource(R.drawable.balloons);
                        //                    dialog.setContentView(R.layout.success_dialog);
                        dialog.show();
                        playSound(R.raw.applause2);
                    }
                    else {
                        playSound(R.raw.fail);
                    }
                }
            });
        }


    }
    public void playSound(int _id)
    {
        if(FXPlayer != null)
        {
            FXPlayer.stop();
            FXPlayer.release();
        }
        FXPlayer = MediaPlayer.create(this, _id);
        if(FXPlayer != null)
            FXPlayer.start();
    }

    public void success() {
        listIndex++;
        if (listIndex == names.length) listIndex = 0;
        mCurrIndex = shuffledList.get(listIndex);
        TypedArray picArray = getResources().obtainTypedArray(R.array.taurons_pics);
        int pr = picArray.getResourceId(mCurrIndex, -1);
        mTauroImage.setImageResource(pr);
    }
}
