package com.example.astrology.Activities;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.astrology.R.menu.search;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.astrology.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button astro = findViewById(R.id.astro);
        astro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,astrologer.class));
            }
        });

        ImageSlider imageSlider = findViewById(R.id.slider);

        List<SlideModel> slideModels = new ArrayList<>();

        slideModels.add(new SlideModel(R.drawable.backgroundd));
        slideModels.add(new SlideModel("https://5.imimg.com/data5/ANDROID/Default/2021/2/CL/AS/MJ/48693369/product-jpeg-500x500.jpg", "1 Image"));
        slideModels.add(new SlideModel("https://www.haribhoomi.com/cms/gall_content/2018/3/zodiac_2018032812431854.jpg", "2 Image"));
        slideModels.add(new SlideModel("https://thehindutimes.in/static/c1e/client/89706/migrated/f13b42b0689041873d096a5b3d447a7d.jpg", "3 Image"));
        slideModels.add(new SlideModel("https://www.pavitrajyotish.com/wp-content/uploads/2015/12/Indian-Vedic-Astrology-Hindi.jpg", "4 Image"));
        slideModels.add(new SlideModel("https://m.facebook.com/619462814750696/photos/a.619469904749987/1264677913562513/?type=3&source=44", "5 Image"));
        imageSlider.setImageList(slideModels,true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(search,menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search Astrology,Numerologist.....");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query){
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {


                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);

    }
}









