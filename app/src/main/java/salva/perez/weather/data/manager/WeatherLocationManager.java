package salva.perez.weather.data.manager;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.widget.Toast;

import salva.perez.weather.R;

public class WeatherLocationManager {

    boolean gpsEnabled = false;
    boolean networkEnabled = false;
    private Location networkLocation;
    private Location gpsLocation;
    private Location finalLocation;

    public Location getLocationUpdates(Activity mContext) {
        try {
            LocationManager mLocationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);

            if(mLocationManager != null) {
                gpsEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                networkEnabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

                if(networkEnabled) {
                    networkLocation = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                }else if(gpsEnabled) {
                    gpsLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                }else{
                    Toast.makeText(mContext, mContext.getString(R.string.app_name), Toast.LENGTH_SHORT).show();
                }

                if (gpsLocation != null && networkLocation != null) {
                    if (gpsLocation.getAccuracy() > networkLocation.getAccuracy()) {
                        finalLocation = networkLocation;
                    } else {
                        finalLocation = gpsLocation;
                    }
                } else {
                    if (gpsLocation != null) {
                        finalLocation = gpsLocation;
                    } else if (networkLocation != null) {
                        finalLocation = networkLocation;
                    }
                }
            }else{
                Toast.makeText(mContext, mContext.getString(R.string.app_name), Toast.LENGTH_SHORT).show();
            }
        }catch (SecurityException exception){
            exception.printStackTrace();
        }

        return finalLocation;
    }
}
