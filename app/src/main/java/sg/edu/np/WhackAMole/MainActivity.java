package sg.edu.np.WhackAMole;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    /* Hint
        - The function setNewMole() uses the Random class to generate a random value ranged from 0 to 2.
        - The function doCheck() takes in button selected and computes a hit or miss and adjust the score accordingly.
        - The function doCheck() also decides if the user qualifies for the advance level and triggers for a dialog box to ask for user to decide.
        - The function nextLevelQuery() builds the dialog box and shows. It also triggers the nextLevel() if user selects Yes or return to normal state if user select No.
        - The function nextLevel() launches the new advanced page.
        - Feel free to modify the function to suit your program.
    */

    public int score = 0;
    private Button b;
    private Button bCheck;
    private TextView scoresDisplay;

    private static final int[] BUTTON_IDS = {
            R.id.Button1,R.id.Button2,R.id.Button3
    };
    private static final String TAG = "Whack-A-Mole";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scoresDisplay = (TextView) findViewById(R.id.Scoring);
        Log.v(TAG, "Finished Pre-Initialisation!");


    }

    @Override
    protected void onStart(){
        super.onStart();
        setNewMole();


        for(final int id : BUTTON_IDS){
            /*  HINT:
            This creates a for loop to populate all 9 buttons with listeners.
            You may use if you wish to remove or change to suit your codes.
            */

            b = (Button) findViewById(id);
            Log.v(TAG,"Populated!" + id);

            b.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {

                    bCheck = findViewById(view.getId());
                    Log.v(TAG,"" + bCheck.getText().toString());
                    doCheck(bCheck);
                    setNewMole();
                }
            });
        }

        Log.v(TAG, "Starting GUI!");
    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.v(TAG, "Paused Whack-A-Mole!");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.v(TAG, "Stopped Whack-A-Mole!");
        finish();
    }

    private void doCheck(Button checkButton) {

        //Adjust Scores
        if(checkButton.getText().toString().equals("*")){
            score += 1;
            scoresDisplay.setText("" + score);
        }
        else{
            score -= 1;
            scoresDisplay.setText("" + score);
        }
        //Checks for advance stage
        if(score >= 10){
            nextLevelQuery();
        }
    }

    private void nextLevelQuery(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("You are now eligible for advancement");
        builder.setMessage("Would you like to advance to advanced mode?");
        builder.setCancelable(false); //closable without an option
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int id) {
                Log.v(TAG, "User accepts!");
                nextLevel();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.v(TAG, "User decline!");
            }
        });

        AlertDialog alert = builder.create();
        Log.v(TAG, "Advance option given to user!");
        alert.show();
    }

    private void nextLevel(){
        /* Launch advanced page */
        Intent advance = new Intent(this, Main2Activity.class);
        advance.putExtra("Score", Integer.parseInt(scoresDisplay.getText().toString()));
        startActivity(advance);
    }

    private void setNewMole() {
        Random ran = new Random();
        int randomLocation = ran.nextInt(3);
        for(int i=0;i<BUTTON_IDS.length;i++){
            b = (Button) findViewById(BUTTON_IDS[i]);
            if(i != randomLocation){
                b.setText("O");
            }
            else{
                b.setText("*");
            }
        }
    }
}