package com.infinite.simpleslidingmenu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ListView mMenu,mContent;
    private TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMenu= (ListView) findViewById(R.id.lv_menu);
        mContent= (ListView) findViewById(R.id.lv_content);
        txt= (TextView) findViewById(R.id.txt);
        mMenu.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,new String[]{"111","222","33","444","555","666","777","888","999","100","111","112","113","a","b","C","s"}));
        mContent.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,new String[]{"111","222","33","444","555","666","777","888","999","100","111","112","113","a","b","C","s"}));
        mMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("act",parent.getItemAtPosition(position)+"");
            }
        });
        mContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("act",parent.getItemAtPosition(position)+"");
            }
        });
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("act","txt clicked");
            }
        });
    }
}
