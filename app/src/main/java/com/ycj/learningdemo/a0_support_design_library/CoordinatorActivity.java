package com.ycj.learningdemo.a0_support_design_library;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ycj.learningdemo.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CoordinatorActivity extends AppCompatActivity {

    @Bind(R.id.list)
    RecyclerView eventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator);
        ButterKnife.bind(this);

        eventList.setHasFixedSize(true);
        eventList.setAdapter(new EventAdapter(this));
        eventList.setLayoutManager(new LinearLayoutManager(this));


    }

    private static class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder>{

        private Context mContext;

        private ViewGroup.LayoutParams mParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        private EventAdapter(Context context){
            this.mContext = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Button button = new Button(mContext);
            button.setLayoutParams(mParams);
            return new ViewHolder(button);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Button button = (Button)holder.itemView;
            button.setText("Event id = "+ position);
        }



        @Override
        public int getItemCount() {
            return 30;
        }

        static class ViewHolder extends RecyclerView.ViewHolder{

            public ViewHolder(View itemView) {
                super(itemView);
            }
        }
    }
}
