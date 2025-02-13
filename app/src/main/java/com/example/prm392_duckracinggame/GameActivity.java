package com.example.prm392_duckracinggame;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private SeekBar seekBar1, seekBar2, seekBar3;
    private Button btnStart, btnReset;
    private CheckBox cbBet1, cbBet2, cbBet3;
    private EditText etBet1, etBet2, etBet3;
    private TextView tvBalance;

    private int balance = 1000; // Số dư ban đầu
    private Handler handler = new Handler();
    private Random random = new Random();
    private boolean raceRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gamepage);

        // Ánh xạ View
        seekBar1 = findViewById(R.id.seekBarRace1);
        seekBar2 = findViewById(R.id.seekBarRace2);
        seekBar3 = findViewById(R.id.seekBarRace3);
        btnStart = findViewById(R.id.btnStart);
        btnReset = findViewById(R.id.btnReset);
        cbBet1 = findViewById(R.id.cbBet1);
        cbBet2 = findViewById(R.id.cbBet2);
        cbBet3 = findViewById(R.id.cbBet3);
        etBet1 = findViewById(R.id.etBet1);
        etBet2 = findViewById(R.id.etBet2);
        etBet3 = findViewById(R.id.etBet3);
        tvBalance = findViewById(R.id.tvInitialBalance);

        // Cập nhật số dư ban đầu
        tvBalance.setText("💰 Balance: $" + balance);

        btnStart.setOnClickListener(v -> startRace());
        btnReset.setOnClickListener(v -> resetGame());
    }

    private void startRace() {
        if (raceRunning) return; // Nếu đang chạy, không bắt đầu lại

        // Kiểm tra xem người chơi đã đặt cược hợp lệ chưa
        int betCount = 0;
        if (cbBet1.isChecked()) betCount++;
        if (cbBet2.isChecked()) betCount++;
        if (cbBet3.isChecked()) betCount++;

        if (betCount == 0) {
            Toast.makeText(this, "⚠️ Bạn phải đặt cược ít nhất 1 con vịt!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (betCount > 3) {
            Toast.makeText(this, "⚠️ Bạn chỉ có thể đặt cược tối đa 3 con vịt!", Toast.LENGTH_SHORT).show();
            return;
        }

        raceRunning = true;
        resetSeekBars();

        new Thread(() -> {
            boolean raceFinished = false;
            while (!raceFinished) {
                int progress1 = seekBar1.getProgress() + random.nextInt(5);
                int progress2 = seekBar2.getProgress() + random.nextInt(5);
                int progress3 = seekBar3.getProgress() + random.nextInt(5);

                handler.post(() -> {
                    seekBar1.setProgress(progress1);
                    seekBar2.setProgress(progress2);
                    seekBar3.setProgress(progress3);
                });

                if (progress1 >= 100 || progress2 >= 100 || progress3 >= 100) {
                    raceFinished = true;
                    handler.post(() -> determineWinner(progress1, progress2, progress3));
                }

                try {
                    Thread.sleep(100); // Điều chỉnh tốc độ di chuyển
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void determineWinner(int p1, int p2, int p3) {
        raceRunning = false;
        int winner;
        if (p1 >= 100) winner = 1;
        else if (p2 >= 100) winner = 2;
        else winner = 3;

        Toast.makeText(this, "🏆 Winner: Duck " + winner + "!", Toast.LENGTH_SHORT).show();

        updateBalance(winner);
    }

    private void updateBalance(int winner) {
        int bet1 = getBetAmount(etBet1);
        int bet2 = getBetAmount(etBet2);
        int bet3 = getBetAmount(etBet3);

        if (cbBet1.isChecked() && winner == 1) balance += bet1;
        else if (cbBet1.isChecked()) balance -= bet1;

        if (cbBet2.isChecked() && winner == 2) balance += bet2;
        else if (cbBet2.isChecked()) balance -= bet2;

        if (cbBet3.isChecked() && winner == 3) balance += bet3;
        else if (cbBet3.isChecked()) balance -= bet3;

        tvBalance.setText("💰 Balance: $" + balance);
    }

    private int getBetAmount(EditText etBet) {
        String betText = etBet.getText().toString();
        return betText.isEmpty() ? 0 : Integer.parseInt(betText);
    }

    private void resetGame() {
        raceRunning = false;
        resetSeekBars();

        cbBet1.setChecked(false);
        cbBet2.setChecked(false);
        cbBet3.setChecked(false);

        etBet1.setText("");
        etBet2.setText("");
        etBet3.setText("");

        tvBalance.setText("💰 Balance: $" + balance);
    }

    private void resetSeekBars() {
        seekBar1.setProgress(0);
        seekBar2.setProgress(0);
        seekBar3.setProgress(0);
    }
}
