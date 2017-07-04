package com.uis.carmensandiego.carmensandiego.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.uis.carmensandiego.carmensandiego.MainActivity;
import com.uis.carmensandiego.carmensandiego.R;
import com.uis.carmensandiego.carmensandiego.adapter.LugaresAdapter;
import com.uis.carmensandiego.carmensandiego.model.Caso;
import com.uis.carmensandiego.carmensandiego.model.Pista;
import com.uis.carmensandiego.carmensandiego.service.CarmenSanDiegoService;
import com.uis.carmensandiego.carmensandiego.service.Connection;

import org.apache.commons.lang3.StringUtils;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class PistasFragment extends Fragment {

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
                String lugarSeleccionado =  lv.getItemAtPosition(position).toString();
                pedirPistas(lugarSeleccionado);
            }
        });

        return view;
    }

    public void llenarLugares(View view) {
        Caso caso = activity.getCaso();

        ListView lvLugares = (ListView) view.findViewById(R.id.listLugares);
        LugaresAdapter adapter = new LugaresAdapter(getActivity(),caso.getPais().getLugares());

        lvLugares.setAdapter(adapter);
    }
    public void pedirPistas(String lugar) {

        CarmenSanDiegoService carmenSanDiegoService = Connection.getService();
        carmenSanDiegoService.getPista(activity.getCaso().getId(),lugar, new Callback<Pista>() {
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
        ((TextView) activity.findViewById(R.id.muestraPista)).setText("Pistas encontradas: \n\t" + StringUtils.join(pista.getPista().split(","),"\n\t"));

        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_Material_Light_Dialog_Alert);

        if(pista.estaElVillano()) {
            if (pista.esGanador())
                showAlert(builder, "ENHORABUENA ", pista.getResultadoOrden(), R.drawable.ganee,R.drawable.victoria);
            else
                showAlert(builder, "FRACASADO ", pista.getResultadoOrden(), R.drawable.perdii,R.drawable.fracaso);
        }
    }

    private void showAlert(AlertDialog.Builder builder, String title, String pistas, int icon, int image) {
        ImageView im = new ImageView(activity);
        im.setImageResource(image);

        builder.setTitle(title)
                .setMessage(pistas)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {}
                })
                .setIcon(icon)
                .setView(im)
                .show();
    }
}