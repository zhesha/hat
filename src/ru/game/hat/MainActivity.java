package ru.game.hat;

import ru.game.hat.util.PreferencesUtil;
import ru.game.hat.util.StringUtils;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseArray;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

public class MainActivity extends BaseActivity implements OnItemSelectedListener {
	public static String PREFERENCES_FILE_KEY = "rugamehat_preferences_ui";
	private PreferencesUtil prefs;
	private String typeKey;
	
	private SparseArray<String> players = new SparseArray<String>();
	private int playersCount = 1;
	
	private final class PlayerTextChangeListener implements TextWatcher {
		private final int id;
		public PlayerTextChangeListener(int id) {
			this.id = id;
		}
		
		@Override
		public void afterTextChanged(Editable s) {
			final String name = s.toString();
			
			if (StringUtils.isEmpty(name)) {
				players.remove(id);
				return;
			}
			
			savePlayer(id, name);
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		prefs = new PreferencesUtil(this);
		typeKey = getString(R.string.preferences_game_type);
		
		setContentView(R.layout.activity_main);
		initLevelSpinner();
		
		// select preferred game type
		int type = -1;//prefs.get(typeKey, Integer.class); TODO remember text, not element id
		if (type == -1) {
			type = R.id.teamRadio;
			prefs.set(typeKey, type);
		} else {
			final RadioButton radio = view(type);
			radio.setChecked(true);
		}
		if (type == R.id.roundRadio) hideHint(R.id.teamHint);
		
		restorePlayers();
		
		// select preferred level 
		final Spinner spinner = view(R.id.levelSpinner);
		final String levelKey = getString(R.string.preferences_level);
		Integer levelPos = prefs.get(levelKey, Integer.class);
		if (levelPos == -1) {
			levelPos = 0;
			prefs.set(levelKey, levelPos);
		}
		spinner.setSelection(levelPos);
	}
	
	/**
	 * Restores players from prefs. <br>
	 * Assigns text watcher for inputs. <br>
	 * Disabled start button if there is no player or count of players not even for Team game.
	 */
	private void restorePlayers() {
		int i = 1;
		final String playerKey = getString(R.string.preferences_player);
		String playerName = prefs.get(playerKey + i, String.class);
		while (playerName != null) {
			players.put(i, playerName);
			final EditText edit = view(R.id.player1);//TODO create new edit texts for players
			edit.setText(playerName);
			assignPlayerListener(i, R.id.player1);
			
			i += 1;
			playerName = prefs.get(playerKey + i, String.class);
		}
		
		// don't start game without players
		if (players.size() == 0) {
			assignPlayerListener(1, R.id.player1);
			
			final Button startButton = view(R.id.startButton);
//			startButton.setEnabled(false);
		}
	}
	
	private void assignPlayerListener(int id, int inputId) {
		final EditText playerInput = view(inputId);
		playerInput.addTextChangedListener(new PlayerTextChangeListener(id));
	}
	
	private void savePlayer(int id, String name) {
		players.put(id, name);
		prefs.set(getString(R.string.preferences_player) + id, name);
	}

	private void initLevelSpinner() {
		final Spinner spinner = view(R.id.levelSpinner);
		spinner.setOnItemSelectedListener(this);
		
		// Create an ArrayAdapter using the string array and a default spinner layout
		final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.levelsArray, android.R.layout.simple_spinner_item);
		
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void onTypeChange(View view) {
		boolean checked = ((RadioButton) view).isChecked();
		
		final int id = view.getId();
		prefs.set(typeKey, id);
		if (checked && id == R.id.roundRadio) {
			hideHint(R.id.teamHint);
		} else if (checked && id == R.id.teamRadio) {
			showHint(R.id.teamHint);
		}
	}
	
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
		prefs.set(getString(R.string.preferences_level), pos);
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		parent.setSelection(0);
	}
	
	public void startGame(View view) {
	    // Do something in response to button
		final Intent intent = new Intent(this, ru.game.hat.GameActivity.class);
		final Bundle bundle = new Bundle();
//		bundle.putSparseParcelableArray(getString(R.string.game_players), players);
		bundle.putString("val", "some string");
		intent.putExtra(getString(R.string.game_bundle), bundle);
		startActivity(intent);
	}
	
}
