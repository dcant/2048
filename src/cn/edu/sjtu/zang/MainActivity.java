package cn.edu.sjtu.zang;

import android.os.Bundle;
import android.R.integer;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {

	private TextView scoreTextView;
	private int score = 0;
	private static MainActivity mainActivity;
	private static MainLayout mLayout;
	public MainActivity() {
		mainActivity = this;
	}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scoreTextView = (TextView) findViewById(R.id.scoren);
        mLayout = MainLayout.getMainLayout();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public static MainActivity getMainActivity() {
    	return mainActivity;
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	switch (keyCode) {
    	case KeyEvent.KEYCODE_DPAD_RIGHT:
    		mLayout.sRightOut();
    		break;
    	case KeyEvent.KEYCODE_DPAD_LEFT:
    		mLayout.sLeftOut();
    		break;
    	case KeyEvent.KEYCODE_DPAD_DOWN:
    		mLayout.sDownOut();
    		break;
    	case KeyEvent.KEYCODE_DPAD_UP:
    		mLayout.sUpOut();
    		break;
    	}
    	return super.onKeyDown(keyCode, event);
    }
    
    public void clearScore() {
    	score = 0;
    	showScore();
    }
    
    public void showScore() {
    	scoreTextView.setText(score + "");
    }
    
    public void scoring(int score) {
    	this.score += score;
    	showScore();
    }
}
