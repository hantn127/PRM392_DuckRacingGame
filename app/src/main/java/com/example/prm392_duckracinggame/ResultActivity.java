package com.example.prm392_duckracinggame;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {
    private TextView tvWinningDuck;
    private TextView tvAmountWon;
    private TextView tvAmountLost;
    private TextView tvFinalBalance;
    private Button btnConfirm;
    private MediaPlayer mediaPlayer;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.resultpage);

        tvWinningDuck = findViewById(R.id.tvWinningDuck);
        tvAmountWon = findViewById(R.id.tvAmountWon);
        tvAmountLost = findViewById(R.id.tvAmountLost);
        tvFinalBalance = findViewById(R.id.tvFinalBalance);
        btnConfirm = findViewById(R.id.btnConfirm);

        mediaPlayer = MediaPlayer.create(this, R.raw.ringtone);
//        mediaPlayer.setLooping(true); // Lặp lại nhạc
//        mediaPlayer.setVolume(1.0f, 1.0f); // Âm lượng tối đa
        mediaPlayer.start();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            int winnerDuck = bundle.getInt("winner_duck");
            int winnings = bundle.getInt("winnings");
            int lostAmount = bundle.getInt("lostAmount");
            int balance = bundle.getInt("balance");
            tvWinningDuck.setText("🏁 Winning Duck: #" + winnerDuck);
            tvAmountWon.setText("💰 You Won: $" + winnings);
            tvAmountLost.setText("💵 You Lost: $" + lostAmount);
            tvFinalBalance.setText("🏦 Remaining Balance: $" + balance);
            btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mediaPlayer != null) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("balance", balance);
                    setResult(RESULT_OK, returnIntent);
                    finish();
                }
            });
        }

    }
}
