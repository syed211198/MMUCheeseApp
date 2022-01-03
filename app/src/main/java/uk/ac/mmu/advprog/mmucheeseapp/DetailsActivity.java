package uk.ac.mmu.advprog.mmucheeseapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;

public class DetailsActivity extends AppCompatActivity {
    TextView cheeseName;
    TextView details;
    ImageView cheeseImage;
    String cheeseImageName;
    String imageURL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        // get the intent
        Bundle extras = getIntent().getExtras();
        // create a cheese object from the cheese object that was passed over from
        // the MainActivity. Notice you use the key ('cheese') to retrieve the value/variable needed.
                Cheese theCheese = (Cheese) extras.get("cheese");
        System.out.println("received from the intent: "+theCheese.getName());

        cheeseName =findViewById(R.id.textView);
        details = findViewById(R.id.textView2);
        cheeseName.setText(theCheese.getName());
        details.setText(theCheese.getDetails());

        ImageView cheeseImage =(ImageView)findViewById(R.id.imageView);
        cheeseImageName = theCheese.getName().toLowerCase();
        cheeseImageName = cheeseImageName.replaceAll(" ","-");



    }

  class  GetCheeseData extends AsyncTask<String,Void,Bitmap>{
        @Override
        protected Bitmap doInBackground(String... String){
            Bitmap bmp = null;
           imageURL = "http://radikaldesign.co.uk/sandbox/cheese_images/"+cheeseImageName+".jpg";
            try{
                URL url = new URL(imageURL);
                System.out.println(url);
                 bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
               // cheeseImage.setImageBitmap(bmp);
            } catch (IOException e){
                e.printStackTrace();
            }

            return bmp;
        }
        @Override
        protected  void onPostExecute(Bitmap bitmap){
            super.onPostExecute(bitmap);
            //cheeseImage.setImageBitmap(bitmap);
        }

    }


}

