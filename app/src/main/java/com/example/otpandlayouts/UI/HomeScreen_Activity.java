package com.example.otpandlayouts.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.otpandlayouts.R;
import com.example.otpandlayouts.UI.Fragments.Discover_Fragment;
import com.example.otpandlayouts.UI.Fragments.Matches_Fragment;
import com.example.otpandlayouts.UI.Fragments.NotesScreen_Fragment;
import com.example.otpandlayouts.UI.Fragments.Profile_Fragment;
import com.example.otpandlayouts.ViewModels.ProfileData_VM;
import com.example.otpandlayouts.data_models.ProfileData_Model;
import com.example.otpandlayouts.databinding.ActivityNotesScreenBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

public class HomeScreen_Activity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();
    private ActivityNotesScreenBinding binding;
    private ProfileData_VM profileData_vm;

    private Fragment fragment = null;

    private ProfileData_Model profileData;

    private String Token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotesScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (fragment == null) {
            fragment = new NotesScreen_Fragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment).commit();
            binding.bottomNavBar.setSelectedItemId(R.id.notes);
        }
//        binding.bottomNavBar.setSelectedItemId(R.id.notes);

        binding.bottomNavBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {

                int alreadySelected = binding.bottomNavBar.getSelectedItemId();

                switch (item.getItemId()) {

                    case R.id.discover:
                        if (item.getItemId() == alreadySelected) {
                            Toast.makeText(HomeScreen_Activity.this, "Already here!", Toast.LENGTH_SHORT).show();
                        } else {
                            fragment = new Discover_Fragment();
                        }

                        break;

                    case R.id.notes:
                        if (item.getItemId() == alreadySelected) {
                            Toast.makeText(HomeScreen_Activity.this, "Already here!", Toast.LENGTH_SHORT).show();
                        } else {
                            fragment = new NotesScreen_Fragment();
                        }
                        break;

                    case R.id.matches:
                        if (item.getItemId() == alreadySelected) {
                            Toast.makeText(HomeScreen_Activity.this, "Already here!", Toast.LENGTH_SHORT).show();
                        } else {
                            fragment = new Matches_Fragment();
                        }
                        break;

                    case R.id.profile:
                        if (item.getItemId() == alreadySelected) {
                            Toast.makeText(HomeScreen_Activity.this, "Already here!", Toast.LENGTH_SHORT).show();
                        } else {
                            fragment = new Profile_Fragment();
                        }
                        break;

                }

                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment).commit();
                return true;
            }
        });
    }
}