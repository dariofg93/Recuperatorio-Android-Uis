package com.uis.carmensandiego.carmensandiego.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.uis.carmensandiego.carmensandiego.MainActivity;
import com.uis.carmensandiego.carmensandiego.R;
import com.uis.carmensandiego.carmensandiego.adapter.ConexionesAdapter;
import com.uis.carmensandiego.carmensandiego.model.Caso;
import com.uis.carmensandiego.carmensandiego.model.Pais;
import com.uis.carmensandiego.carmensandiego.model.Viajar;
import com.uis.carmensandiego.carmensandiego.service.CarmenSanDiegoService;
import com.uis.carmensandiego.carmensandiego.service.Connection;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ViajarFragment extends Fragment {

    private MainActivity activity;

    public ViajarFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_viajar, container, false);
        activity = ((MainActivity) getActivity());

        ((TextView) view.findViewById(R.id.visitados)).setText(StringUtils.join(activity.getCaso().getPaisesVisitados(), " -> "));
        ((TextView) view.findViewById(R.id.fallidos)).setText(StringUtils.join(activity.getCaso().getPaisesFallidos(), " -> "));

        llenarConexiones(view);

        final ListView lv = (ListView) view.findViewById(R.id.listConexiones);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String conexionSeleccionada = (String) (lv.getItemAtPosition(position));
                viajar(conexionSeleccionada);
            }
        });

        return view;
    }

    public void llenarConexiones(View view){
        Caso caso = activity.getCaso();
        ListView lvConexiones = (ListView) view.findViewById(R.id.listConexiones);
        List<String> conexionesNombre = this.getNombreConexiones(caso.getPais().getConexiones());
        ConexionesAdapter adapter = new ConexionesAdapter(getActivity(),conexionesNombre);
        lvConexiones.setAdapter(adapter);
    }


    public List<String> getNombreConexiones(List<Pais> pais){
        List<String> nombreConexiones = new ArrayList<>();
        for(Pais p : pais){
            nombreConexiones.add(p.getNombre());
        }
        return nombreConexiones;
    }

    private int getIdPais(List<Pais> conexiones, String paisSeleccionado) {
        int id = 0;
        for(Pais pais : conexiones){
            if (pais.getNombre() == paisSeleccionado){
                id = pais.getId();
            }
        }
        return id;
    }

    public void viajar(final String nombrePaisSeleccionado) {
        Caso caso = activity.getCaso();
        int idPaisSeleccionado = getIdPais(caso.getPais().getConexiones(), nombrePaisSeleccionado);
        CarmenSanDiegoService carmenSanDiegoService = Connection.getService();
        Viajar viajarRequest = new Viajar(caso.getId(), idPaisSeleccionado);
        carmenSanDiegoService.viajar(viajarRequest, new Callback<Caso>() {
            @Override
            public void success(Caso caso, Response response) {
                activity.setCaso(caso);
                activity.updateCaso();

                ((TextView) activity.findViewById(R.id.visitados)).setText(StringUtils.join(activity.getCaso().getPaisesVisitados(), " -> "));
                ((TextView) activity.findViewById(R.id.fallidos)).setText(StringUtils.join(activity.getCaso().getPaisesFallidos(), " -> "));

                llenarConexiones(getView());

                Toast toastOrdenEmitida = Toast.makeText(getContext(), "Viajaste a "+ nombrePaisSeleccionado, Toast.LENGTH_SHORT);
                toastOrdenEmitida.setGravity(Gravity.NO_GRAVITY, 0, 0);
                toastOrdenEmitida.show();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("error", error.getMessage());
                error.printStackTrace();
            }
        });
    }
}

