package cosw.eci.edu.androidchat.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cosw.eci.edu.androidchat.R;
import cosw.eci.edu.androidchat.model.Message;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder>{

    private final List<Message> messages;
    private Context context;

    public MessageAdapter(List<Message> response) {
        this.messages = response;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.message_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Message team = messages.get(position);
        holder.name.setText(team.getName());
        holder.message.setText(team.getMessage());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView name, message;

        public ViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            message = (TextView) view.findViewById(R.id.message);
        }
    }
}
