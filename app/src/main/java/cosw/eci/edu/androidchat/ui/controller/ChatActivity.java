package cosw.eci.edu.androidchat.ui.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import cosw.eci.edu.androidchat.R;
import cosw.eci.edu.androidchat.model.Message;
import cosw.eci.edu.androidchat.ui.adapter.MessageAdapter;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private ArrayList<Message> listaMensajes = new ArrayList<>();
    private EditText txt_name, txt_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        recyclerView = (RecyclerView) findViewById( R.id.recyclerView );
        database = FirebaseDatabase.getInstance();
        txt_name = (EditText) findViewById(R.id.txt_name);
        txt_message = (EditText) findViewById(R.id.txt_message);
        myRef = database.getReference("Message");
        myRef.addChildEventListener(messageListener);
        configureRecyclerView();
    }

    private void configureRecyclerView() {
        recyclerView = (RecyclerView) findViewById( R.id.recyclerView );
        recyclerView.setHasFixedSize( true );
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager( this );
        recyclerView.setLayoutManager( layoutManager );

    }

    public void sendMessage(View view){
        Message mensaje = new Message();
        mensaje.setMessage(txt_message.getText().toString());
        mensaje.setName(txt_name.getText().toString());
        myRef.push().setValue(mensaje);

    }

    private final ChildEventListener messageListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            listaMensajes.add(dataSnapshot.getValue(Message.class));
            recyclerView.setAdapter(new MessageAdapter(listaMensajes));
            Log.i("Hijo",dataSnapshot.getValue().toString());
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

}
