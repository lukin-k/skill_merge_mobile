package com.example.bipapp.ui.user;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.graphics.Matrix;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.bipapp.R;
import com.example.bipapp.adapters.AdapterRecyclerSkillsSelected;
import com.example.bipapp.client.ClientMain;
import com.example.bipapp.models.Skill;
import com.example.bipapp.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

//TODO back to UserInfo

public class FragmentUserEdit extends Fragment {
    private ClientMain mClient;
    private AdapterRecyclerSkillsSelected mAdapterRecyclerSkills;

    public final static int PICK_PHOTO_CODE = 1046;

    public final String APP_TAG = "BIPAPP";
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    private File photoFile;
    private Bitmap mTmpUserPhoto;


    public void onPickPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(intent, PICK_PHOTO_CODE);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public Bitmap loadFromUri(Uri photoUri) {
        Bitmap image = null;
        try {
            ImageDecoder.Source source = ImageDecoder.createSource(getActivity().getContentResolver(), photoUri);
            image = ImageDecoder.decodeBitmap(source);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public static Bitmap RotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    public void onLaunchCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        photoFile = getPhotoFileUri("photo.jpg");

        Uri fileProvider = FileProvider.getUriForFile(getContext(), "com.example.bipapp.fileprovider", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }

    public File getPhotoFileUri(String fileName) {
        File mediaStorageDir = new File(getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES), APP_TAG);

        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
            Log.d(APP_TAG, "failed to create directory");
        }

        File file = new File(mediaStorageDir.getPath() + File.separator + fileName);
        return file;
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((data != null) && requestCode == PICK_PHOTO_CODE) {
            Uri photoUri = data.getData();

            Bitmap selectedImage = loadFromUri(photoUri);
            selectedImage = ThumbnailUtils.extractThumbnail(selectedImage, 300, 300, 0);
            mTmpUserPhoto = selectedImage;
        }
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                takenImage = RotateBitmap(takenImage, -90);
                takenImage = ThumbnailUtils.extractThumbnail(takenImage, 300, 300, 0);
                mTmpUserPhoto = takenImage;
            } else {
                mTmpUserPhoto = null;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mClient = ClientMain.getClient();
        View view = inflater.inflate(R.layout.fragment_user_edit, container, false);

        RecyclerView recyclerSkills = view.findViewById(R.id.recycler_skills);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerSkills.setLayoutManager(layoutManager);

        mAdapterRecyclerSkills = new AdapterRecyclerSkillsSelected();
        recyclerSkills.setAdapter(mAdapterRecyclerSkills);

        Button buttonSave = view.findViewById(R.id.button_save_edit_user);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editFullName = view.findViewById(R.id.edit_fullname);
                EditText editAge = view.findViewById(R.id.edit_age);
                EditText editBiography = view.findViewById(R.id.edit_biography);


                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("fullname", editFullName.getText().toString());
                    jsonObject.put("age", Integer.parseInt(editAge.getText().toString()));
                    jsonObject.put("biography", editBiography.getText().toString());
                    jsonObject.put("skills", getSelectedSkills());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //TODO send photo
//                if (tmpUserPhoto != null) {
//                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                    tmpUserPhoto.compress(Bitmap.CompressFormat.JPEG, 100, stream);
//                    byte[] byteArray = stream.toByteArray();
//                    try {
//                        jsonObject.put("photo_bytes", byteArray);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }

                mClient.changeUserInfo(jsonObject);
            }
        });

        ImageView ibCameraImage = view.findViewById(R.id.image_photo);
        ibCameraImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder selectSourceBuilder = new AlertDialog.Builder(getContext(), R.style.AlertDialogStyle);
                selectSourceBuilder.setMessage(getResources().getString(R.string.title_select_source_photo));
                selectSourceBuilder.setCancelable(true);

                selectSourceBuilder.setPositiveButton(getResources().getString(R.string.title_source_gallery),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                onPickPhoto();
                            }
                        });
                selectSourceBuilder.setNegativeButton(getResources().getString(R.string.title_source_camera),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                onLaunchCamera();
                            }
                        });

                selectSourceBuilder.create().show();
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setUserInfo();
    }

    private void setUserInfo() {
        User user = mClient.getUser();
        View view = getView();

        EditText editFullName = view.findViewById(R.id.edit_fullname);
        editFullName.setText(user.getFullName());

        EditText editAge = view.findViewById(R.id.edit_age);
        editAge.setText("" + user.getAge());

        EditText editBiography = view.findViewById(R.id.edit_biography);
        editBiography.setText(user.getBiography());

        ImageView imagePhoto = view.findViewById(R.id.image_photo);
        if (user.getPhoto() == null) {
            imagePhoto.setImageResource(getResources().getIdentifier("test_photo", "drawable", getActivity().getPackageName()));
        } else {
            imagePhoto.setImageBitmap(user.getPhoto());
        }

        mAdapterRecyclerSkills.setSkills(mClient.getAllSkillsList());
        mAdapterRecyclerSkills.setSkillsLevels(mClient.getAllSkillsLevel());
        mAdapterRecyclerSkills.setSelectedSkills(user.getSkills());
        mAdapterRecyclerSkills.notifyDataSetChanged();
    }

    private JSONArray getSelectedSkills() {
        JSONArray jsonArray = new JSONArray();
        ArrayList<Skill> skills = mAdapterRecyclerSkills.getSkills();
        boolean[] selectedSkills = mAdapterRecyclerSkills.getSelectedSkills();

        for (int i = 0; i < selectedSkills.length; i++) {
            if (selectedSkills[i]) {
                jsonArray.put(skills.get(i).getJsonSkill());
            }
        }

        return jsonArray;
    }
}