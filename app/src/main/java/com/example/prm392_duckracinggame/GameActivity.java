package com.example.prm392_duckracinggame;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
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
    private TextView nameid;

    private int balance = 1000; // Số dư ban đầu
    private boolean raceRunning = false;
    private Handler handler = new Handler();
    private Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gamepage);

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
        nameid = findViewById(R.id.nameid);

        // Cập nhật số dư ban đầu
        tvBalance.setText("💰 Balance: $" + balance);

        Intent intent = getIntent();
        String name = intent.getStringExtra("nameid");
        nameid.setText("🏆 Name: " + name);

        // Vô hiệu hóa ô nhập tiền ban đầu
        etBet1.setEnabled(false);
        etBet2.setEnabled(false);
        etBet3.setEnabled(false);

        btnStart.setOnClickListener(v -> startRace());
        btnReset.setOnClickListener(v -> resetGame());
        btnReset.setEnabled(false); // Vô hiệu hóa nút Reset ban đầu

        // Xử lý khi chọn checkbox để bật/tắt ô nhập cược
        cbBet1.setOnCheckedChangeListener((buttonView, isChecked) -> toggleBetInput(etBet1, isChecked));
        cbBet2.setOnCheckedChangeListener((buttonView, isChecked) -> toggleBetInput(etBet2, isChecked));
        cbBet3.setOnCheckedChangeListener((buttonView, isChecked) -> toggleBetInput(etBet3, isChecked));



    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            balance = data.getIntExtra("balance", balance);
            tvBalance.setText("💰 Balance: $" + balance);
        }
    }
    // Hàm bật/tắt ô nhập cược khi chọn checkbox
    private void toggleBetInput(EditText betInput, boolean isChecked) {
        if (isChecked) {
            betInput.setEnabled(true);
            betInput.requestFocus(); // Tự động mở bàn phím nhập số tiền
        } else {
            betInput.setEnabled(false);
            betInput.setText(""); // Xóa số tiền nếu bỏ chọn checkbox
        }
    }

    private void startRace() {
        if (raceRunning) return; // Nếu đang chạy, không bắt đầu lại

        // Kiểm tra nếu ít nhất 1 vịt được đặt cược
        int betCount = 0;
        if (cbBet1.isChecked()) betCount++;
        if (cbBet2.isChecked()) betCount++;
        if (cbBet3.isChecked()) betCount++;

        if (betCount == 0) {
            Toast.makeText(this, "⚠️ Bạn phải đặt cược ít nhất 1 con vịt!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra nếu tổng cược không vượt quá số dư
        int totalBet = getBetAmount(etBet1) + getBetAmount(etBet2) + getBetAmount(etBet3);
        if (totalBet > balance) {
            Toast.makeText(this, "⚠️ Tổng số tiền cược không thể vượt quá số dư!", Toast.LENGTH_SHORT).show();
            return;
        }



        raceRunning = true;
        btnReset.setEnabled(false); // Chặn nút Reset khi đang đua
        resetSeekBars();

        new Thread(() -> {
            boolean raceOngoing = true;
            while (raceOngoing) {
                int progress1 = seekBar1.getProgress() + random.nextInt(5);
                int progress2 = seekBar2.getProgress() + random.nextInt(5);
                int progress3 = seekBar3.getProgress() + random.nextInt(5);

                handler.post(() -> {
                    seekBar1.setProgress(progress1);
                    seekBar2.setProgress(progress2);
                    seekBar3.setProgress(progress3);
                });

                if (progress1 >= 100 || progress2 >= 100 || progress3 >= 100) {
                    raceOngoing = false;
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
        btnReset.setEnabled(true); // Cho phép bấm Reset

        int winner;
        if (p1 >= 100) winner = 1;
        else if (p2 >= 100) winner = 2;
        else winner = 3;

        Toast.makeText(this, "🏆 Winner: Duck " + winner + "!", Toast.LENGTH_SHORT).show();

        updateBalance(winner);
        int bet1 = getBetAmount(etBet1);
        int bet2 = getBetAmount(etBet2);
        int bet3 = getBetAmount(etBet3);
        int totalBet = (cbBet1.isChecked() ? bet1 : 0) +
                (cbBet2.isChecked() ? bet2 : 0) +
                (cbBet3.isChecked() ? bet3 : 0);
        int winnings = 0;
        if (cbBet1.isChecked() && winner == 1) winnings += bet1 ; // Nhân đôi tiền thắng
        if (cbBet2.isChecked() && winner == 2) winnings += bet2 ;
        if (cbBet3.isChecked() && winner == 3) winnings += bet3 ;

        int lostAmount = totalBet - winnings; // Số tiền thua

        Intent intent = new Intent(GameActivity.this, ResultActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("winner_duck", winner);
        bundle.putInt("bet1", bet1);
        bundle.putInt("bet2", bet2);
        bundle.putInt("bet3", bet3);
        bundle.putInt("winnings", winnings);
        bundle.putInt("lostAmount", lostAmount);
        bundle.putInt("balance", balance);
        intent.putExtras(bundle);
        startActivity(intent);
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
        return TextUtils.isEmpty(betText) ? 0 : Integer.parseInt(betText);
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

        etBet1.setEnabled(false);
        etBet2.setEnabled(false);
        etBet3.setEnabled(false);

        btnReset.setEnabled(false); // Vô hiệu hóa lại nút Reset
    }

    private void resetSeekBars() {
        seekBar1.setProgress(0);
        seekBar2.setProgress(0);
        seekBar3.setProgress(0);
    }
}
