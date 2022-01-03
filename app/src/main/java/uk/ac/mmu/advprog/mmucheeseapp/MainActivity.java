package uk.ac.mmu.advprog.mmucheeseapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    String cheeseNames[]= null ;
    ArrayList<Cheese> allCheeses = new ArrayList<>();
    ListView cheeseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cheeseList =(ListView)findViewById(R.id.cheeseList);

        GetData getData =new GetData();
        getData.execute();




    }
    private  class GetData extends AsyncTask<Void,Void,ArrayList<Cheese>> {
        @Override
        protected ArrayList<Cheese> doInBackground(Void...v){
            //Making a http call
            HttpURLConnection urlConnection;
            InputStream in = null;
            try {
                // the url we wish to connect to
                URL url = new URL("http://radikaldesign.co.uk/sandbox/cheese.php");
                // open the connection to the specified URL
                urlConnection = (HttpURLConnection) url.openConnection();
                // get the response from the server in an input stream
                in = new BufferedInputStream(urlConnection.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            // covert the input stream to a string
            String response = convertStreamToString(in);
            // print the response to android monitor/log cat
            System.out.println("Server response = " + response);

            try{
                // declare a new json array and pass it the string response from the server
                // this will convert the string into a JSON array which we can then iterate
                // over using a loop
                JSONArray jsonArray = new JSONArray(response);
                // instantiate the cheeseNames array and set the size
                // to the amount of cheese object returned by the server
                cheeseNames = new String[jsonArray.length()];
                // use a for loop to iterate over the JSON array
                for(int i=0;i<jsonArray.length();i++)
                {
                    // the following line of code will get the name of the cheese from the
                    // current JSON object and store it in a string variable called name
                    String name = jsonArray.getJSONObject(i).get("name").toString();
                    String description = jsonArray.getJSONObject(i).get("description").toString();

                    // print the name to log cat
                    System.out.println("name = " + name);

                    // add the name of the current cheese to the cheeseNames array
                    cheeseNames [i] =name;
                    Cheese cheese = new Cheese(name, description);
                    allCheeses.add(cheese);

                }

            } catch (JSONException e){
                e.printStackTrace();
            }
            //Make an Http call
            return allCheeses;
        }
        @Override
        protected  void  onPostExecute(ArrayList<Cheese>cheeseArrayList){
            super.onPostExecute(cheeseArrayList);
            // process the cheese ArrayList and set up List View
            ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,cheeseNames);

            cheeseList.setAdapter(arrayAdapter);

            cheeseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Toast.makeText(MainActivity.this, "you pressed " + allCheeses.get(i).getName(), Toast.LENGTH_SHORT).show();
                    // declare a new intent and give it the context and
                    // specify which activity you want to open/start
                    Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
                    // add/put the selected cheese object in to the intent which will
                    // be passed over to the activity that is started
                    // note we use a KEY:VALUE structure to pass variable/objects
                    // between activities. Here the key is ‘cheese’ and the value is
                    // the cheese object from the cheese array list using the position
                    // which is specified by the ‘i’ variable.
                    intent.putExtra("cheese", allCheeses.get(i));
                    // launch the activity
                    startActivity(intent);
                }
            });
        }
    }



    public String convertStreamToString(InputStream is){
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next():"";
    }

}
