package com.example.mareu.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mareu.R;
import com.example.mareu.events.DeleteMeetingEvent;
import com.example.mareu.model.Meeting;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MeetingListAdapter extends RecyclerView.Adapter<MeetingListAdapter.ViewHolder> {

    private List<Meeting> mMeetings;
    private Activity context;


    public MeetingListAdapter(List<Meeting> mMeetings) {
        this.mMeetings = mMeetings;
        this.context = context;
    }

    @Override
    public MeetingListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_list_meeting, parent, false);
        return new ViewHolder(view);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MeetingListAdapter.ViewHolder holder, int position) {
        Meeting meeting = mMeetings.get(position);
        String meetingColor = meeting.getmRoomName();
        switch (meetingColor) {
            case "Peach":
                holder.mCircle.setImageResource(R.drawable.circle_background_nude);
                break;
            case "Mario":
                holder.mCircle.setImageResource(R.drawable.circle_background_green);
                break;
            case "Luigi":
                holder.mCircle.setImageResource(R.drawable.circle_background_grey);
                break;
            default:
                holder.mCircle.setImageResource(R.drawable.circle_background_red);

        }

        holder.mTopic.setText(meeting.getmTopic() +"\t"+ "-" +"\t"+ meeting.getFormatDate() +"\t"+ "-" + "\t" + meeting.getmRoomName());
        holder.mParticipants.setText(meeting.getmParticipants());


        holder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new DeleteMeetingEvent(meeting));

            }
        });


    }


    @Override
    public int getItemCount() {
        return mMeetings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.circle_item)
        public ImageView mCircle;
        @BindView(R.id.topic)
        public TextView mTopic;
        @BindView(R.id.item_list_meeting_delete)
        public ImageView mDeleteButton;
        @BindView(R.id.participants)
        public TextView mParticipants;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
