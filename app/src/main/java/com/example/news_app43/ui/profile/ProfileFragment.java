package com.example.news_app43.ui.profile;

import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.FragmentNavigator;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.news_app43.MainActivity;
import com.example.news_app43.prefs.Prefs;
import com.example.news_app43.R;
import com.example.news_app43.databinding.FragmentProfileBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.sql.Struct;
import java.util.HashMap;


public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;
    private Uri uri;
    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        setHasOptionsMenu(true);
        return binding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menuu, menu);
        menu.removeItem(R.id.Cleanone);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.Cleanone) {
            MainActivity.prefs.clearPreferences();
            binding.editTextProfile.setText(MainActivity.prefs.getText());
            if (MainActivity.prefs.getPic() != null) {
                uri = Uri.parse(MainActivity.prefs.getPic());
                Glide.with(binding.profileImage).load(uri).circleCrop().into(binding.profileImage);
            }
            return true;
        }
        return false;
    }

    ActivityResultLauncher<String> resultLauncher = registerForActivityResult(
            new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {
                    binding.profileImage.setImageURI(result);
                    upload(result);
                }
            }
    );

    private void upload(Uri uri) {
        FirebaseStorage.getInstance()
                .getReference()
                .child("avatar.jpg")
                .putFile(uri)
                .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(requireContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show();
                            task.getException().printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletefromFirestore();
            }
        });
        binding.btnProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getName = binding.editTextProfile.getText().toString().trim();
                String getPhoto = binding.profileImage.toString().trim();

                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("name", getName);
                hashMap.put("url", getPhoto);

                FirebaseFirestore.getInstance()
                        .collection("User")
                        .document("UserData")
                        .set(hashMap)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(requireContext(), "Data saved", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(requireContext(), "Data failed" +e.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });

            }
        });
        MainActivity.prefs = new Prefs(requireContext());
        binding.editTextProfile.setText(MainActivity.prefs.getText());
        if (MainActivity.prefs.getPic() != null) {
            Glide.with(binding.profileImage).load(MainActivity.prefs.getPic()).circleCrop().into(binding.profileImage);
        }
        SaveText();
        binding.profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGalary();
//                FragmentNavigator.Extras extras = new FragmentNavigator.Extras.Builder().addSharedElement(binding.profileImage, "example_transition").build();
//                Navigation.findNavController(v).navigate(R.id.action_navigation_profile_to_secondProfileFragment,
//                        null,
//                        null,
//                        extras);
            }
        });

        binding.btnOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
            }
        });
    }

    private void openGalary() {
        resultLauncher.launch("image/*");
    }

    private void deletefromFirestore() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();
        StorageReference desertRef = storageReference.child("avatar.jpg");
        desertRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(requireContext(), "Is success", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(requireContext(), "Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void SaveText() {
        binding.editTextProfile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                MainActivity.prefs.saveEditText(editable.toString());
            }
        });

    }
}