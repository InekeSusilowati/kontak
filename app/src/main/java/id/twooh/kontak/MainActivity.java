package id.twooh.kontak;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import id.twooh.kontak.Adapter.AdapterKontak;
import id.twooh.kontak.Model.GetKontak;
import id.twooh.kontak.Model.Kontak;
import id.twooh.kontak.Rest.ApiClient;
import id.twooh.kontak.Rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    Button btIns;
    ApiInterface mApiInterface;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public static MainActivity ma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btIns = (Button) findViewById(R.id.btIns);
        btIns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, InsertActivity.class));
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
        ma=this; refresh();
    }

    public void refresh() {
        Call<GetKontak> kontakCall = mApiInterface.getKontak();
        kontakCall.enqueue(new Callback<GetKontak>() {
            @Override
            public void onResponse(Call<GetKontak> call, Response<GetKontak> response) {
                List<Kontak> KontakList = response.body().getListDataKontak();
                Log.d("Retrofit Get", "Jumlah data Kontak: " + String.valueOf(KontakList.size()));
                mAdapter = new AdapterKontak(KontakList);
                mRecyclerView.setAdapter(mAdapter);
            }
            @Override
            public void onFailure(Call<GetKontak> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
            }
        });
    }
}

