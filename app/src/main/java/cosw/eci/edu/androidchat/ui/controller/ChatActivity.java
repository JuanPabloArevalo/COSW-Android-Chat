package cosw.eci.edu.androidchat.ui.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
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
    private FirebaseAuth mAuth;

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
        mAuth = FirebaseAuth.getInstance();
    }

    private void configureRecyclerView() {
        recyclerView = (RecyclerView) findViewById( R.id.recyclerView );
        recyclerView.setHasFixedSize( true );
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager( this );
        recyclerView.setLayoutManager( layoutManager );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_favorite) {
            mAuth.signOut();
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void sendMessage(View view){
        if(isValidToSend()) {
            Message mensaje = new Message();
            mensaje.setMessage(txt_message.getText().toString());
            mensaje.setName(txt_name.getText().toString());
            myRef.push().setValue(mensaje);
            txt_message.setText("");
        }
    }

    public boolean isValidToSend(){
        if(txt_name.length()==0){
            txt_name.setError("Please enter your name");
            return false;
        }
        else if(txt_message.length()==0){
            txt_message.setError("Please enter either a message");
            return false;
        }
        else{
            return true;
        }
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
