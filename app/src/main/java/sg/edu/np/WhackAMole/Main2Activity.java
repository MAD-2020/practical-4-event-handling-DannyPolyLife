package sg.edu.np.WhackAMole;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Main2Activity extends AppCompatActivity {
    /* Hint
        - The function setNewMole() uses the Random class to generate a random value ranged from 0 to 8.
        - The function doCheck() takes in button selected and computes a hit or miss and adjust the score accordingly.
        - The functions readyTimer() and placeMoleTimer() are to inform the user X seconds before starting and loading new mole.
        - Feel free to modify the function to suit your program.
    */
    public int score;
    private Button b;
    private Button bCheck;
    private TextView scoresDisplay;
    CountDownTimer myCountDown;
    CountDownTimer moleSecTimer;
    private static final String TAG = "Whack-A-Mole!";
    private static final int[] BUTTON_IDS = {
            /* HINT:
                Stores the 9 buttons IDs here for those who wishes to use array to create all 9 buttons.
                You may use if you wish to change or remove to suit your codes.*/

            R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5,
            R.id.button6, R.id.button7, R.id.button8, R.id.button9
    };

    private void readyTimer(){
        /*  HINT:
            The "Get Ready" Timer.
            Log.v(TAG, "Ready CountDown!" + millisUntilFinished/ 1000);
            Toast message -"Get Ready In X seconds"
            Log.v(TAG, "Ready CountDown Complete!");
            Toast message - "GO!"
            belongs here.
            This timer countdown from 10 seconds to 0 seconds and stops after "GO!" is shown.
         */

        myCountDown = new CountDownTimer(10000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                final Toast ToastMessage;
                Log.v(TAG, "Countdown: " + millisUntilFinished/1000);
                ToastMessage = Toast.makeText(getApplicationContext(),"Get Ready In " + millisUntilFinished/1000 + " seconds", Toast.LENGTH_SHORT);
                ToastMessage.show();

                Timer cancelToast = new Timer();
                cancelToast.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        ToastMessage.cancel();
                    }
                }, 1000);

            }

            @Override
            public void onFinish() {
                Log.v(TAG, "Countdown stopped");
                Toast.makeText(getApplicationContext(),"GO!", Toast.LENGTH_SHORT).show();
                myCountDown.cancel();
                placeMoleTimer();
            }


        };
        myCountDown.start();
    }
    private void placeMoleTimer(){
        /* HINT:
           Creates new mole location each second.
           Log.v(TAG, "New Mole Location!");
           setNewMole();
           belongs here.
           This is an infinite countdown timer.
         */

        moleSecTimer = new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long l) {
                Log.v(TAG, "New Mole Location!");
                setNewMole();
            }

            @Override
            public void onFinish() {
                moleSecTimer.start();
            }
        };

        moleSecTimer.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*Hint:
            This starts the countdown timers one at a time and prepares the user.
            This also prepares the existing score brought over.
            It also prepares the button listeners to each button.
            You may wish to use the for loop to populate all 9 buttons with listeners.
         */

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        Intent advanceStage = getIntent();
        score = advanceStage.getIntExtra("Score",10);
        Log.v(TAG, "Current User Score: " + String.valueOf(score));

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
                    moleSecTimer.cancel();
                    moleSecTimer.start();
                    setNewMole();
                }
            });

            scoresDisplay = findViewById(R.id.Scoring);

        }
    }

    @Override
    protected void onStart(){
        super.onStart();
        scoresDisplay.setText("" + score);
        readyTimer();
        setNewMole();

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
        }

    public void setNewMole() {
        /* Hint:
            Clears the previous mole location and gets a new random location of the next mole location.
            Sets the new location of the mole.
         */
        Random ran = new Random();
        int randomLocation = ran.nextInt(9);
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

