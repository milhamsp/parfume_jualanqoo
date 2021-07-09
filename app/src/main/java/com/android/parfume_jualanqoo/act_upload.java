package com.android.parfume_jualanqoo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firebase.ui.firestore.ObservableSnapshotArray;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import android.os.Bundle;

public class act_upload extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri image_uri;
    private Button upload,pilih;
    private ImageView preview;
    private ProgressBar progress_upload;
    private FirebaseAuth auth;
    private StorageTask uploading;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    StorageReference storage = FirebaseStorage.getInstance().getReference("bukti");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_upload);
        upload = findViewById(R.id.btn_upload);
        pilih = findViewById(R.id.btn_pilih);
        progress_upload = findViewById(R.id.progress_upload);
        preview = findViewById(R.id.preview);
        pilih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(uploading!=null && uploading.isInProgress()){
                    Toast.makeText(act_upload.this,"Upload masih dalam proses!",Toast.LENGTH_SHORT).show();
                }else{
                    uploadFile();
                }
            }
        });
    }

    private void uploadFile() {
        if(image_uri!=null){
            CollectionReference upload = db.collection("upload");
            CollectionReference user = db.collection("user");
            auth = FirebaseAuth.getInstance();
            long millis = System.currentTimeMillis();
            StorageReference upload_file = storage.child(System.currentTimeMillis()+"."+getFileExtension(image_uri));
            String userID = Objects.requireNonNull(auth.getCurrentUser()).getUid();
            String tanggal = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
            user.document(userID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    DocumentSnapshot doc = task.getResult();
                    assert doc != null;
                    String nama_u = doc.getString("nama");
                    uploading = upload_file.putFile(image_uri)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    final Handler handler;
                                    handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            progress_upload.setProgress(0);
                                        }
                                    },1000);
                                    upload_file.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            Uri url_gambar = uri;
                                            Map<String,Object> data=new HashMap<>();
                                            upload.add(data).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                @Override
                                                public void onSuccess(DocumentReference documentReference) {
                                                    String doc_id = documentReference.getId();
                                                    Map<String,Object> isi=new HashMap<>();
                                                    isi.put("nama_gambar",nama_u+" - "+tanggal+" - "+ millis);
                                                    isi.put("url_gambar",url_gambar.toString());
                                                    upload.document(doc_id).set(isi);
                                                    Toast.makeText(act_upload.this,"Upload Berhasil",Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                    });
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(act_upload.this,"Upload Gagal!",Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                                    double progress = (100.0 * snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                                    progress_upload.setProgress((int) progress);
                                }
                            });
                }
            });
        }else{
            Toast.makeText(this,"Tidak ada file gambar yang dipilih!",Toast.LENGTH_SHORT).show();
        }
    }

    private void openFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

    private String getFileExtension(Uri uri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE_REQUEST && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            image_uri = data.getData();
            preview.setBackground(null);
            Picasso.get().load(image_uri).into(preview);
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),act_home.class));
        finish();
    }

    public void btn_tohome(View view) {
        startActivity(new Intent(getApplicationContext(),act_home.class));
        finish();
    }
}