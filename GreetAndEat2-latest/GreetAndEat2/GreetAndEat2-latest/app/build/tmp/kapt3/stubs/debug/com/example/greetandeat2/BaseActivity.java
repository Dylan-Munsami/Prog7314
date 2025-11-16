package com.example.greetandeat2;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0014J\u0010\u0010\u0015\u001a\u00020\u00122\u0006\u0010\u0016\u001a\u00020\u0017H\u0004R\u001a\u0010\u0003\u001a\u00020\u0004X\u0084.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0084.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u000e\u0010\u000f\u001a\u00020\u0010X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0018"}, d2 = {"Lcom/example/greetandeat2/BaseActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "drawerLayout", "Landroidx/drawerlayout/widget/DrawerLayout;", "getDrawerLayout", "()Landroidx/drawerlayout/widget/DrawerLayout;", "setDrawerLayout", "(Landroidx/drawerlayout/widget/DrawerLayout;)V", "navigationView", "Lcom/google/android/material/navigation/NavigationView;", "getNavigationView", "()Lcom/google/android/material/navigation/NavigationView;", "setNavigationView", "(Lcom/google/android/material/navigation/NavigationView;)V", "toggle", "Landroidx/appcompat/app/ActionBarDrawerToggle;", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "setupDrawer", "toolbar", "Landroidx/appcompat/widget/Toolbar;", "app_debug"})
public class BaseActivity extends androidx.appcompat.app.AppCompatActivity {
    protected androidx.drawerlayout.widget.DrawerLayout drawerLayout;
    protected com.google.android.material.navigation.NavigationView navigationView;
    private androidx.appcompat.app.ActionBarDrawerToggle toggle;
    
    public BaseActivity() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    protected final androidx.drawerlayout.widget.DrawerLayout getDrawerLayout() {
        return null;
    }
    
    protected final void setDrawerLayout(@org.jetbrains.annotations.NotNull()
    androidx.drawerlayout.widget.DrawerLayout p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    protected final com.google.android.material.navigation.NavigationView getNavigationView() {
        return null;
    }
    
    protected final void setNavigationView(@org.jetbrains.annotations.NotNull()
    com.google.android.material.navigation.NavigationView p0) {
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    protected final void setupDrawer(@org.jetbrains.annotations.NotNull()
    androidx.appcompat.widget.Toolbar toolbar) {
    }
}