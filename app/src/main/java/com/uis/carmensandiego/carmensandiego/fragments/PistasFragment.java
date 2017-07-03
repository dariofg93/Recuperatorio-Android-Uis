package com.uis.carmensandiego.carmensandiego.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.uis.carmensandiego.carmensandiego.MainActivity;
import com.uis.carmensandiego.carmensandiego.R;
import com.uis.carmensandiego.carmensandiego.adapter.LugaresAdapter;
import com.uis.carmensandiego.carmensandiego.model.Caso;
import com.uis.carmensandiego.carmensandiego.model.OrdenEmitida;
import com.uis.carmensandiego.carmensandiego.model.Pista;
import com.uis.carmensandiego.carmensandiego.service.CarmenSanDiegoService;
import com.uis.carmensandiego.carmensandiego.service.Connection;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class PistasFragment extends Fragment {

    private ListView lvLugares;
    private MainActivity activity;

    public PistasFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pistas, container, false);
        View viewPista = inflater.inflate(R.layout.row_pista, container, false);
        activity = ((MainActivity) getActivity());

        final Button button = (Button) viewPista.findViewById(R.id.lugar);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String nombreLugar = String.valueOf(button.getText());
                pedirPistas();
            }
        });

        llenarLugares(view);
        return view;
    }

    public void llenarLugares(View view) {
        Caso caso = activity.getCaso();
        lvLugares = (ListView) view.findViewById(R.id.listLugares);
        LugaresAdapter adapter = new LugaresAdapter(getActivity(),caso.getPais().getLugares());
        lvLugares.setAdapter(adapter);
    }
    public void pedirPistas() {

        CarmenSanDiegoService carmenSanDiegoService = Connection.getService();
        carmenSanDiegoService.getPista(activity.getCaso().getId(),"Huadalajaraaa!", new Callback<Pista>() {
            @Override
            public void success(Pista pista, Response response) {
                showPistas(pista);
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
    }

    private void showPistas(Pista pista) {
        Toast toastOrdenEmitida = Toast.makeText(getContext(), pista.getPista(), Toast.LENGTH_SHORT);
        toastOrdenEmitida.setGravity(Gravity.NO_GRAVITY, 0, 0);
        toastOrdenEmitida.show();
    }
}

