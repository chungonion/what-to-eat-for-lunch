package com.example.chungonion.restaurantgenerator;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class ManageListActivity extends AppCompatActivity {
    private List<String> list;
    private ListView listview;
    private String[] restaurants = {};
    private EditText newRestaurantText;
    private Button addButton;
    private JSONObject obj;
    private JSONArray jsonArray;
    private CustomList adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_list);

        addButton = (Button) findViewById(R.id.addButton);
        newRestaurantText = (EditText)findViewById(R.id.newRestaurantText);
        addButton.setOnClickListener(listener);
        listview = (ListView) findViewById(R.id.restaurantList);
        restaurants = loadRestaurantList();
        try{
        adapter = new CustomList(ManageListActivity.this, restaurants);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                
            }
        });
        }catch (NullPointerException e){
            e.printStackTrace();
        }


    }

    private String[] loadRestaurantList() {
        String json = loadJSON();

        try {
            jsonArray = new JSONArray(json);

            int length = jsonArray.length();
            List<String> listContents = new ArrayList<String>(length);
            for (int i = 0; i < length; i++) {
                listContents.add(jsonArray.getString(i));
            }
            String[] temp = new String[listContents.size()];
            return listContents.toArray(temp);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;

    }

    public String loadJSON() {
        String json = null;
        try {
            InputStream in = new FileInputStream(Environment.getExternalStorageDirectory().getAbsolutePath()+"/restaurantList.json");
            int size = in.available();
            byte[] buffer = new byte[size];
            in.read(buffer);
            in.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            try {
                InputStream in = getAssets().open("restaurantList.json");
                OutputStream out = new FileOutputStream(Environment.getExternalStorageDirectory().getAbsolutePath()+"/restaurantList.json");
                byte[] buffer = new byte[1024];
                int read;
                while ((read = in.read(buffer)) != -1) {
                    out.write(buffer, 0, read);
                }
                int size = in.available();
                buffer = new byte[size];
                in.close();
                json = new String(buffer, "UTF-8");
                in = null;
                // write the output file (You have now copied the file)
                out.flush();
                out.close();
                out = null;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return json;

    }

    public void saveJSON(){
        try {
            OutputStream out = new FileOutputStream(Environment.getExternalStorageDirectory().getAbsolutePath()+"/restaurantList.json");
            byte[] buffer = new byte [1024];
            String jsonString = jsonArray.toString();
            buffer = jsonString.getBytes();
            out.write(buffer);
            out.flush();
            out.close();
            out = null;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case (R.id.addButton):
//                    System.out.println(newRestaurantText.getText());
                    try {
                        jsonArray.put(newRestaurantText.getText());
                    }catch(NullPointerException e){
                        jsonArray = new JSONArray();
                        jsonArray.put(newRestaurantText.getText());
                    }
                        saveJSON();
                        restaurants = loadRestaurantList();
                        adapter = new CustomList(ManageListActivity.this, restaurants);
                        listview.setAdapter(adapter);
                        newRestaurantText.setText("");

                    break;
            }
        }
    };
}
