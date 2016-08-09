package com.infinite.simpleslidingmenu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private ListView mMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMenu= (ListView) findViewById(R.id.lv_menu);
        mMenu.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,new String[]{"111","222","33","444","555","666","777","888","999","100","111","112",}));
    }
}
