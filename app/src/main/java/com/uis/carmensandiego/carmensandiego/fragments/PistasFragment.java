package com.uis.carmensandiego.carmensandiego.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.uis.carmensandiego.carmensandiego.MainActivity;
import com.uis.carmensandiego.carmensandiego.R;
import com.uis.carmensandiego.carmensandiego.adapter.LugaresAdapter;
import com.uis.carmensandiego.carmensandiego.model.Caso;

public class PistasFragment extends Fragment {

    private ListView lvLugares;
    private MainActivity activity;

    public PistasFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pistas, container, false);
        activity = ((MainActivity) getActivity());

        llenarLugares(view);

        final ListView lv = (ListView) view.findViewById(R.id.listLugares);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String nombreLugar = (String) (lv.getItemAtPosition(position));
                pedirPistas(nombreLugar);
            }
        });

        return view;
    }

    public void llenarLugares(View view) {
        Caso caso = activity.getCaso();
        lvLugares = (ListView) view.findViewById(R.id.listLugares);
        LugaresAdapter adapter = new LugaresAdapter(getActivity(),caso.getPais().getLugares());
        lvLugares.setAdapter(adapter);
    }
    public void pedirPistas(String nombreLugar) {
/*
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(getContext());
        }
        builder.setTitle("Delete entry")
                .setMessage("Are you sure you want to delete this entry?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }


    Button button = (Button) getView().findViewById(R.id.lugar);
    String nombreLugar = button.getText().toString();

    /*int idVillanoSeleccionado = getIdVillano(villanos, nombreVillanoSeleccionado);

    Toast toastOrdenEmitida = Toast.makeText(getContext(), "Orden emitida exitosamente contra: "+ nombreVillanoSeleccionado, Toast.LENGTH_SHORT);
        toastOrdenEmitida.setGravity(Gravity.CENTER, 0, 0);

        toastOrdenEmitida.show();*/
    }
}

