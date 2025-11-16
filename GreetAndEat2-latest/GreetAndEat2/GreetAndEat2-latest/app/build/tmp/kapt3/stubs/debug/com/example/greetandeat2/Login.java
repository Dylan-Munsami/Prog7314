package com.example.greetandeat2;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\u0018\u0010\u0015\u001a\u00020\u00122\u0006\u0010\u0016\u001a\u00020\u00142\u0006\u0010\u0017\u001a\u00020\u0014H\u0002J\"\u0010\u0018\u001a\u00020\u00122\u0006\u0010\u0019\u001a\u00020\u00042\u0006\u0010\u001a\u001a\u00020\u00042\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0015J\u0012\u0010\u001d\u001a\u00020\u00122\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0014J\b\u0010 \u001a\u00020\u0012H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006!"}, d2 = {"Lcom/example/greetandeat2/Login;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "RC_SIGN_IN", "", "auth", "Lcom/google/firebase/auth/FirebaseAuth;", "biometricBtn", "Landroid/widget/Button;", "emailInput", "Landroid/widget/EditText;", "googleBtn", "googleSignInClient", "Lcom/google/android/gms/auth/api/signin/GoogleSignInClient;", "loginBtn", "passwordInput", "registerBtn", "firebaseAuthWithGoogle", "", "idToken", "", "loginUser", "email", "password", "onActivityResult", "requestCode", "resultCode", "data", "Landroid/content/Intent;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "showBiometricPrompt", "app_debug"})
public final class Login extends androidx.appcompat.app.AppCompatActivity {
    private com.google.firebase.auth.FirebaseAuth auth;
    private com.google.android.gms.auth.api.signin.GoogleSignInClient googleSignInClient;
    private android.widget.EditText emailInput;
    private android.widget.EditText passwordInput;
    private android.widget.Button loginBtn;
    private android.widget.Button googleBtn;
    private android.widget.Button registerBtn;
    private android.widget.Button biometricBtn;
    private final int RC_SIGN_IN = 1001;
    
    public Login() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void loginUser(java.lang.String email, java.lang.String password) {
    }
    
    @java.lang.Override()
    @java.lang.Deprecated()
    protected void onActivityResult(int requestCode, int resultCode, @org.jetbrains.annotations.Nullable()
    android.content.Intent data) {
    }
    
    private final void firebaseAuthWithGoogle(java.lang.String idToken) {
    }
    
    private final void showBiometricPrompt() {
    }
}