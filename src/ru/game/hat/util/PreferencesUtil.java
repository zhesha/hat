package ru.game.hat.util;

import ru.game.hat.MainActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PreferencesUtil {
	private final SharedPreferences preferences;
	
	public PreferencesUtil(Context context) {
		preferences = context.getSharedPreferences(MainActivity.PREFERENCES_FILE_KEY, Context.MODE_PRIVATE);
	}
	
	public void set(String key, Object value) {
		final Editor editor = preferences.edit();
		if (value instanceof String) {
			editor.putString(key, (String) value);
		} else if (value instanceof Integer) {
			editor.putInt(key, (Integer) value);
		} else {
			throw new RuntimeException("Unhandled data type for preferences: " + value.getClass());
		}
		editor.commit();
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get(String key, Class<?> type) {
		if (type == String.class) {
			return (T) preferences.getString(key, null);
		} 
		if (type == Integer.class) {
			return (T) ((Integer)preferences.getInt(key, -1));
		}
		throw new RuntimeException("Unhandled data type for preferences: " + type);
	}
}
