package cn.edu.sjtu.zang;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Path.FillType;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.FrameLayout.LayoutParams;

public class MainLayout extends GridLayout {
	
	private Rec[][] recs = new Rec[4][4];
	private List<Point> eRec = new ArrayList<Point>();
	private boolean move = false;
	private static MainLayout mlLayout;
	private TextView restartTextView;

	public MainLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public MainLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public MainLayout(Context context) {
		super(context);
		init();
	}

	private void init() {
		mlLayout = this;
		setColumnCount(4);
		setBackgroundColor(0xffbbada0);
		
		setOnTouchListener(new View.OnTouchListener() {
			private float x, y, offsetX, offsetY;
			
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				switch (arg1.getAction()) {
				case MotionEvent.ACTION_DOWN:
					x = arg1.getX();
					y = arg1.getY();
					break;
				case MotionEvent.ACTION_UP:
					offsetX = arg1.getX() - x;
					offsetY = arg1.getY() - y;
					
					if (Math.abs(offsetX) > Math.abs(offsetY)) {
						if (offsetX > 3)
							sRight();
						else if (offsetX < -3)
							sLeft();
					} else {
						if (offsetY > 3)
							sDown();
						else if (offsetY < -3)
							sUp();
					}
					break;
				}
				return true;
			}
		});
//		setOnKeyListener(new View.OnKeyListener() {
//			
//			@Override
//			public boolean onKey(View v, int keyCode, KeyEvent event) {
//				switch (keyCode) {
//				case KeyEvent.KEYCODE_DPAD_RIGHT:
//					sRight();
//					break;
//				case KeyEvent.KEYCODE_DPAD_LEFT:
//					sLeft();
//					break;
//				case KeyEvent.KEYCODE_DPAD_UP:
//					sUp();
//					break;
//				case KeyEvent.KEYCODE_DPAD_DOWN:
//					sDown();
//					break;
//				}
//				return true;
//			}
//		});
	}
	
	public static MainLayout getMainLayout() {
		return mlLayout;
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		int side = (Math.min(w, h) - 10)/4;
		addRecs(side);
//		restartTextView = new TextView(getContext());
//		restartTextView.setBackgroundColor(0xffedc22e);
//		restartTextView.setGravity(Gravity.CENTER);
//		restartTextView.setText("Replay!");
//		restartTextView.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				start();
//			}
//		});
//		LayoutParams l = new LayoutParams();
//		l.width = LayoutParams.WRAP_CONTENT;
//		l.height = LayoutParams.WRAP_CONTENT;
//		addView(restartTextView, l);
		start();
	}
	
	private void addRecs(int side) {
		Rec rec;
		for (int j = 0; j < 4; j++)
			for (int i = 0; i < 4; i++) {
				rec = new Rec(getContext());
				rec.setNumber(0);
				addView(rec, side, side);
				recs[i][j] = rec;
			}
				
	}
	
	private void newRec() {
		eRec.clear();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (recs[i][j].getNumber() == 0)
					eRec.add( new Point(i, j));
			}
		}
		Point point = eRec.remove((int)(Math.random()*eRec.size()));
		recs[point.x][point.y].setNumber(Math.random()>0.05?2:4);
	}
	
	private void start() {
		MainActivity.getMainActivity().clearScore();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				recs[i][j].setNumber(0);
			}
		}
		newRec();
		newRec();
	}
	
	private void isover() {
		boolean over = true;
		Outmost:
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (recs[i][j].getNumber() == 0||
						(i > 0 && recs[i][j].getNumber() == recs[i-1][j].getNumber())||
						(i < 3 && recs[i][j].getNumber() == recs[i+1][j].getNumber())||
						(j > 0 && recs[i][j].getNumber() == recs[i][j-1].getNumber())||
						(j < 3 && recs[i][j].getNumber() == recs[i][j+1].getNumber())) {
					over = false;
					break Outmost;
				}
			}
		}
		
		if (over) {
			new AlertDialog.Builder(getContext()).setTitle("INFO").setMessage("Game is over!").setPositiveButton("Replay!", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					start();
				}
			}).show();
		}
	}
	
	private void sRight() {
		move = false;
		for (int j = 0; j < 4; j++) {
			for (int i = 3; i >= 0; i--) {
				for (int k = i - 1; k >= 0; k--) {
					if (recs[k][j].getNumber() > 0) {
						if (recs[i][j].getNumber() == 0) {
							recs[i][j].setNumber(recs[k][j].getNumber());
							recs[k][j].setNumber(0);
							i++;
							move = true;
						} else if (recs[i][j].getNumber() == recs[k][j].getNumber()) {
							recs[i][j].setNumber(recs[i][j].getNumber() * 2);
							recs[k][j].setNumber(0);
							MainActivity.getMainActivity().scoring(recs[i][j].getNumber());
							move = true;
						}
						break;
					}
				}
			}
		}
		if (move) {
			newRec();
			isover();
		}
	}
	
	private void sLeft() {
		move = false;
		for (int j = 0; j < 4; j++) {
			for (int i = 0; i < 4; i++) {
				for (int k = i + 1; k < 4; k++) {
					if (recs[k][j].getNumber() > 0) {
						if (recs[i][j].getNumber() == 0) {
							recs[i][j].setNumber(recs[k][j].getNumber());
							recs[k][j].setNumber(0);
							i--;
							move = true;
						} else if (recs[i][j].getNumber() == recs[k][j].getNumber()) {
							recs[i][j].setNumber(recs[i][j].getNumber() * 2);
							recs[k][j].setNumber(0);
							MainActivity.getMainActivity().scoring(recs[i][j].getNumber());
							move = true;
						}
						break;
					}
				}
			}
		}
		if (move) {
			newRec();
			isover();
		}
	}
	
	private void sDown() {
		move = false;
		for (int i = 0; i < 4; i++) {
			for (int j = 3; j >= 0; j--) {
				for (int k = j - 1; k >= 0; k--) {
					if (recs[i][k].getNumber() > 0) {
						if (recs[i][j].getNumber() == 0) {
							recs[i][j].setNumber(recs[i][k].getNumber());
							recs[i][k].setNumber(0);
							j++;
							move = true;
						} else if (recs[i][j].getNumber() == recs[i][k].getNumber()) {
							recs[i][j].setNumber(recs[i][j].getNumber() * 2);
							recs[i][k].setNumber(0);
							MainActivity.getMainActivity().scoring(recs[i][j].getNumber());
							move = true;
						}
						break;
					}
				}
			}
		}
		if (move) {
			newRec();
			isover();
		}
	}
	
	private void sUp() {
		move = false;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				for (int k = j + 1; k < 4; k++) {
					if (recs[i][k].getNumber() > 0) {
						if (recs[i][j].getNumber() == 0) {
							recs[i][j].setNumber(recs[i][k].getNumber());
							recs[i][k].setNumber(0);
							j--;
							move = true;
						} else if (recs[i][j].getNumber() == recs[i][k].getNumber()) {
							recs[i][j].setNumber(recs[i][j].getNumber() * 2);
							recs[i][k].setNumber(0);
							MainActivity.getMainActivity().scoring(recs[i][j].getNumber());
							move = true;
						}
						break;
					}
				}
			}
		}
		if (move) {
			newRec();
			isover();
		}
	}
	
	public void sRightOut() {
		sRight();
	}
	
	public void sLeftOut() {
		sLeft();
	}
	
	public void sDownOut() {
		sDown();
	}
	
	public void sUpOut() {
		sUp();
	}
	
}
