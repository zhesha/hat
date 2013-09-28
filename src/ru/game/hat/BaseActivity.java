package ru.game.hat;

import android.app.Activity;
import android.view.View;

public abstract class BaseActivity extends Activity {
	@SuppressWarnings("unchecked")
	protected <T> T view(int id) {
		return (T) findViewById(id);
	}
	
	protected void hide(int id) {
		final View hint = view(id);
		hint.setVisibility(View.GONE);
	}
	
	protected void show(int id) {
		final View hint = view(id);
		hint.setVisibility(0);
	}
}
