public class MainActivity 
	extends AppCompatActivity { 

	private Button btnToggleDark; 

	@Override
	protected void onCreate( 
		Bundle savedInstanceState) 
	{ 
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.activity_main); 

		btnToggleDark 
			= findViewById(R.id.btnToggleDark); 

		btnToggleDark.setOnClickListener( 
			new View.OnClickListener() { 
				@Override
				public void onClick(View view) 
				{ 
					AppCompatDelegate 
						.setDefaultNightMode( 
							AppCompatDelegate 
								.MODE_NIGHT_YES); 
				} 
			}); 
	} 
} 







public class MainActivity 
	extends AppCompatActivity { 

	private Button btnToggleDark; 

	@SuppressLint("SetTextI18n") 
	@Override
	protected void onCreate( 
		Bundle savedInstanceState) 
	{ 
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.activity_main); 
		btnToggleDark = findViewById(R.id.btnToggleDark); 

		// Saving state of our app 
		// using SharedPreferences 
		
		SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE); 
		final SharedPreferences.Editor editor = sharedPreferences.edit(); 
		final boolean isDarkModeOn = sharedPreferences.getBoolean("isDarkModeOn", false); 
		

		// When user reopens the app 
		// after applying dark/light mode 
		
		if (isDarkModeOn) {
			AppCompatDelegate.setDefaultNightMode( 
					AppCompatDelegate.MODE_NIGHT_YES); 
			btnToggleDark.setText("Disable Dark Mode"); 
		} 
		else { 
			AppCompatDelegate.setDefaultNightMode( 
					AppCompatDelegate.MODE_NIGHT_NO); 
			btnToggleDark.setText("Enable Dark Mode"); 
		} 

		btnToggleDark.setOnClickListener( 
			new View.OnClickListener() { 

				@Override
				public void onClick(View view) 
				{ 
					// When user taps the enable/disable 
					// dark mode button 
					if (isDarkModeOn) { 

						// if dark mode is on it 
						// will turn it off 
						
						AppCompatDelegate.setDefaultNightMode( 
								AppCompatDelegate.MODE_NIGHT_NO); 
								
						// it will set isDarkModeOn 
						// boolean to false 
						
						editor.putBoolean("isDarkModeOn", false); 
						editor.apply(); 

						// change text of Button 
						btnToggleDark.setText("Enable Dark Mode"); 
					} 
					else {

						// if dark mode is off 
						// it will turn it on 
						
						AppCompatDelegate.setDefaultNightMode( 
								AppCompatDelegate.MODE_NIGHT_YES); 

						// it will set isDarkModeOn 
						// boolean to true 
						
						editor.putBoolean("isDarkModeOn", true); 
						editor.apply(); 

						// change text of Button 
						btnToggleDark.setText( 
							"Disable Dark Mode"); 
					} 
				}
			}); 
	} 
} 
