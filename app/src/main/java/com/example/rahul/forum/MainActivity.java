package com.example.rahul.forum;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private EditText editMessage;
    private DatabaseReference mDatabase;
    private RecyclerView mMessageList;
    //private CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editMessage = (EditText) findViewById(R.id.editMessageE);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Forum");
        mMessageList = (RecyclerView) findViewById(R.id.messageRec);
        mMessageList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        mMessageList.setLayoutManager(linearLayoutManager);
        //cardView= (CardView) findViewById(R.id.qcardview);

    }

    public void sendButtonClicked(View view) {
        final String messageValue = editMessage.getText().toString().trim();
        if(!TextUtils.isEmpty(messageValue)){
            final DatabaseReference newPost = mDatabase.push();
            newPost.child("Question").setValue(messageValue);

        }

    }
    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter <Messages, MessageViewHolder> forumadapter = new FirebaseRecyclerAdapter<Messages, MessageViewHolder>(
                Messages.class,
                R.layout.fcmsinglerow,
                MessageViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(MessageViewHolder viewHolder, Messages model, int position) {
              /* cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent i = new Intent(getApplicationContext(), forumcomment.class);
                        startActivity(i);
                    }
                });*/
                viewHolder.setContent(model.getContent());

            }
        };
        mMessageList.setAdapter(forumadapter);
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder{

        View fView;

        public MessageViewHolder(View itemView) {
            super(itemView);
            fView = itemView;
        }


        public void setContent(String content){
            TextView message_content = (TextView) fView.findViewById(R.id.messageText);
            message_content.setText(content);

        }


    }

}
