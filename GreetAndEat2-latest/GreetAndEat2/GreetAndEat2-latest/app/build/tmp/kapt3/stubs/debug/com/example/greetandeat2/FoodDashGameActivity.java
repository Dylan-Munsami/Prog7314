package com.example.greetandeat2;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rJ\u0012\u0010\u000e\u001a\u00020\u000b2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0015J\b\u0010\u0011\u001a\u00020\u000bH\u0014J\b\u0010\u0012\u001a\u00020\u000bH\u0014J\u000e\u0010\u0013\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0014"}, d2 = {"Lcom/example/greetandeat2/FoodDashGameActivity;", "Lcom/example/greetandeat2/BaseActivity;", "()V", "btnPlayAgain", "Landroid/widget/Button;", "gameView", "Lcom/example/greetandeat2/GameView;", "tvHighScore", "Landroid/widget/TextView;", "tvScore", "gameOver", "", "score", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onPause", "onResume", "updateScore", "app_debug"})
public final class FoodDashGameActivity extends com.example.greetandeat2.BaseActivity {
    private com.example.greetandeat2.GameView gameView;
    private android.widget.TextView tvScore;
    private android.widget.TextView tvHighScore;
    private android.widget.Button btnPlayAgain;
    
    public FoodDashGameActivity() {
        super();
    }
    
    @java.lang.Override()
    @android.annotation.SuppressLint(value = {"MissingInflatedId"})
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override()
    protected void onResume() {
    }
    
    @java.lang.Override()
    protected void onPause() {
    }
    
    public final void updateScore(int score) {
    }
    
    public final void gameOver(int score) {
    }
}