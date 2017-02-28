package com.example.chungonion.restaurantgenerator;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

public class MainActivity extends AppCompatActivity {
    private Button generateButton;
    private Button manageButton;
    private Button clearResultButton;
    private Button webLinkButton;
    private TextView resultText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        generateButton = (Button) findViewById(R.id.generateButton);
        manageButton = (Button) findViewById(R.id.manageButton);
        clearResultButton = (Button) findViewById(R.id.clearResultButton);
        resultText = (TextView) findViewById(R.id.resultText);
        webLinkButton = (Button) findViewById(R.id.webLinkButton);


        generateButton.setOnClickListener(listener);
        manageButton.setOnClickListener(listener);
        clearResultButton.setOnClickListener(listener);
        webLinkButton.setOnClickListener(listener);

    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent;
            switch (v.getId()) {
                case (R.id.generateButton):
                    generateFunction();
                    break;
                case (R.id.manageButton):
                    intent = new Intent();
                    intent.setClass(MainActivity.this, ManageListActivity.class);
                    startActivity(intent);
                    break;
                case (R.id.clearResultButton):
                    resultText.setText("");
                    break;
                case (R.id.webLinkButton):
                    intent = new Intent();
                    intent.setClass(MainActivity.this, WebDemoActivity.class);
                    startActivity(intent);
            }
        }
    };

    private void generateFunction() {
        String json = loadJSON();
        JSONObject obj;
        JSONArray jsonArray = null;
        try {
            try {
                jsonArray = new JSONArray(json);

                int length = jsonArray.length();
                List<String> listContents = new ArrayList<String>(length);
                for (int i = 0; i < length; i++) {
                    listContents.add(jsonArray.getString(i));
                }
                String[] temp = new String[listContents.size()];
                listContents.toArray(temp);
                int randomInt = (int) (Math.random() * temp.length);
                try {
                    String restaurantString = temp[randomInt];
                    resultText.setText(restaurantString);
                } catch (ArrayIndexOutOfBoundsException e) {
                    resultText.setText("No Restaurant :\\");
                }
            }catch (RuntimeException e) {
                resultText.setText("No Restaurant :\\");
            }

        } catch (JSONException e) {
            e.printStackTrace();
            resultText.setText("No Restaurant :\\");
        }

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
}
