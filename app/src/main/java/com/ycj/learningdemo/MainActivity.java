package com.ycj.learningdemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Bind(R.id.topic_list)
    RecyclerView mTopicList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUIFrame();

        ButterKnife.bind(this);

        mTopicList.setHasFixedSize(true);
        mTopicList.setLayoutManager(new LinearLayoutManager(this));
        mTopicList.setAdapter(new TopicAdapter(this));


    }

    private void initUIFrame(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_camara) {
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private static class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.ViewHolder>{

        private Context mContext;
        private Topics mTopics;

        private View.OnClickListener onClick = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Class aClass = (Class)v.getTag();
                startActivity(aClass);
            }
        };

        private RecyclerView.LayoutParams mParams = new RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT
        );

        private <T extends Activity> void startActivity(Class<T> activityClass){
            Intent intent = new Intent(mContext, activityClass);
            mContext.startActivity(intent);
        }

        public TopicAdapter(Context context){
            mContext = context;
            mTopics = new Topics();
       }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Button button = new Button(mContext);
            button.setOnClickListener(onClick);
            button.setLayoutParams(mParams);
            return new ViewHolder(button);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            Button button = (Button)holder.itemView;
            button.setText(mTopics.getTopic(position).name);
            button.setTag(mTopics.getTopic(position).aClass);
            button.setAllCaps(false);
            button.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
        }

        @Override
        public int getItemCount() {
            return mTopics.getCount();
        }

        final static class ViewHolder extends RecyclerView.ViewHolder{

            public ViewHolder(View itemView) {
                super(itemView);
            }
        }
    }
}
