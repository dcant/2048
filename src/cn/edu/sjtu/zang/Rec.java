package cn.edu.sjtu.zang;

import android.content.Context;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

public class Rec extends FrameLayout {
	
	private int number = 0;
	private TextView text;

	public Rec(Context context) {
		super(context);
		text = new TextView(getContext());
		text.setTextSize(32);
		text.setGravity(Gravity.CENTER);
		text.setBackgroundColor(0xffeee4da);
		
		LayoutParams l = new LayoutParams(-1, -1);
		l.setMargins(10, 10, 0, 0);
		addView(text, l);
		
		setNumber(0);
	}
	
	public int getNumber() {
		return this.number;
	}
	
	public void setNumber(int number) {
		this.number = number;
		if (number == 0) {
			text.setBackgroundColor(0xffeee4da);
			text.setText("");
		}
		else {
			text.setText(number+"");
			switch (number) {
			case 2:
				text.setBackgroundColor(0xffffffff);
				break;
			case 4:
				text.setBackgroundColor(0xffede0c8);
				break;
			case 8:
				text.setBackgroundColor(0xfff2b179);
				break;
			case 16:
				text.setBackgroundColor(0xfff59563);
				break;
			case 32:
				text.setBackgroundColor(0xfff67c5f);
				break;
			case 64:
				text.setBackgroundColor(0xfff65e3b);
				break;
			case 128:
				text.setBackgroundColor(0xffedcf72);
				break;
			case 256:
				text.setBackgroundColor(0xffedcc61);
				break;
			case 512:
				text.setBackgroundColor(0xffedc850);
				break;
			case 1024:
				text.setBackgroundColor(0xffedc53f);
				break;
			case 2048:
				text.setBackgroundColor(0xffedc22e);
				break;

			default:
				break;
			}
		}
	}
	
	public boolean equals(Rec o) {
		return this.getNumber() == o.getNumber();
	}

}
