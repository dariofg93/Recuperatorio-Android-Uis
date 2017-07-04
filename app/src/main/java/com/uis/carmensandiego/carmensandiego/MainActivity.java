package com.uis.carmensandiego.carmensandiego;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.uis.carmensandiego.carmensandiego.fragments.OrdenArrestoFragment;
import com.uis.carmensandiego.carmensandiego.fragments.PistasFragment;
import com.uis.carmensandiego.carmensandiego.fragments.ViajarFragment;
import com.uis.carmensandiego.carmensandiego.model.Caso;
import com.uis.carmensandiego.carmensandiego.service.CarmenSanDiegoService;
import com.uis.carmensandiego.carmensandiego.service.Connection;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity{

    private Fragment fragment;
    private FragmentManager fragmentManager;
    private Caso caso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        setDefaultView();
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.content, fragment).commit();

        //GET INICIAR JUEGO PARA LLENAR EL CASO MODEL
        iniciarJuego();

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.ordenArresto:
                                fragment = new OrdenArrestoFragment();
                                break;
                            case R.id.viajar:
                                fragment = new ViajarFragment();
                                break;
                            case R.id.pistas:
                                fragment = new PistasFragment();
                                break;
                        }
                        final FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.replace(R.id.content, fragment).commit();
                        return true;
                    }
                }
        );
    }

    private void setDefaultView() {
        fragment = new OrdenArrestoFragment();
    }

    private void iniciarJuego() {
        CarmenSanDiegoService carmenSanDiegoService = Connection.getService();
        carmenSanDiegoService.iniciarJuego(new Callback<Caso>() {
            @Override
            public void success(Caso caso, Response response) {
                llenarMainActivity(caso);
            }
            @Override
            public void failure(RetrofitError error) {
                Log.e("", error.getMessage());
                error.printStackTrace();
            }
        });
    }

    private void llenarMainActivity(Caso caso) {
        this.caso = caso;
        this.updateCaso();
    }

    public Caso getCaso() {
        return this.caso;
    }

    public Caso setCaso(Caso caso) {
        return this.caso = caso;
    }

    public void updateCaso(){
        ((TextView) findViewById(R.id.pais_actual)).setText("Estas en " + getCaso().getPais().getNombre());
        ((TextView) findViewById(R.id.orden_arresto)).setText("Orden de arresto contra: " + getCaso().getOrdenContra());
    }
}
