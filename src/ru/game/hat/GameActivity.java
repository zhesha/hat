package ru.game.hat;

import ru.game.hat.support.Lang;
import ru.game.hat.support.Level;
import ru.game.hat.support.Word;
import ru.game.hat.support.WordSupport;
import ru.game.hat.support.WordSupport.Game;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class GameActivity extends Activity {
	
	private Game game;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		
		final Intent intent = getIntent();
		final Bundle bundle = intent.getBundleExtra(getString(R.string.game_bundle));
		
		// for test purposes:
		final int wordCount = 20;
		final Level level = Level.NORMAL;
		
//		final int wordCount = bundle.getInt(getString(R.string.game_word_count));
//		final Level level = Level.valueOf(bundle.getString(getString(R.string.game_level)));
		final Lang lang = Lang.RUS;
		
		try {
			game = WordSupport.newGame(wordCount, level, lang);
		} catch (Exception e) {
			throw new RuntimeException("Failed to create game", e);
		}
	}
	
	public void wordGuessed(View view) {
		if (game.hasWord()) {
			// TODO count points for player, show next word
			final Word word = game.next();
		} else {
			// TODO finish game
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game, menu);
		return true;
	}

}
