package com.uis.carmensandiego.carmensandiego.service;

import retrofit.RestAdapter;

public class Connection {

    public static CarmenSanDiegoService getService() {
        String SERVER_IP = "192.168.1.34"; //esta ip se usa para comunicarse con mi localhost en el emulador de Android Studio
        String SERVER_IP_GENY = "192.168.1.40";//esta ip se usa para comunicarse con mi localhost en el emulador de Genymotion
        String API_URL = "http://"+ SERVER_IP +":9000";

        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(API_URL).build();
        return restAdapter.create(CarmenSanDiegoService.class);
    }
}
