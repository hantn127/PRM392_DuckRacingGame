package com.example.prm392_duckracinggame;
import android.content.Intent;
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
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.resultpage);
        tvWinningDuck = findViewById(R.id.tvWinningDuck);
        tvAmountWon = findViewById(R.id.tvAmountWon);
        tvAmountLost = findViewById(R.id.tvAmountLost);
        tvFinalBalance = findViewById(R.id.tvFinalBalance);
        btnConfirm = findViewById(R.id.btnConfirm);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            int winnerDuck = bundle.getInt("winner_duck");
            int bet1 = bundle.getInt("bet1");
            int bet2 = bundle.getInt("bet2");
            int bet3 = bundle.getInt("bet3");
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
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("balance", balance);
                    setResult(RESULT_OK, returnIntent);
                    finish();
                }
            });
        }

    }
}
