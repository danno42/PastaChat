package com.example.pastachat;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Db {

    public interface DocumentExistsCallback{
        void DocumentExistsCompleted(boolean exists);
    }

    public static void DocumentExists(FirebaseFirestore db, String collection, String field, Object value, final DocumentExistsCallback callback) {
        db.collection(collection).whereEqualTo(field, value).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())  {
                    for(QueryDocumentSnapshot document : task.getResult()) {
                        callback.DocumentExistsCompleted(document.exists());
                        break;
                    }

                } else {
                    Log.e("Db", "Task failed.");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("Db", "Errore in getting document: " + e.getMessage());
            }
        });
    }
}
