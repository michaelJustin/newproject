package com.habarisoft.firefly;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.CellInfo;
import android.telephony.CellInfoLte;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    TelephonyManager telephonyManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        telephonyManager.listen(new PhoneStateListener() {
            @Override
            public void onSignalStrengthsChanged(SignalStrength signalStrength) {
                printLteSignalStrengths();
            }
        }, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
    }

    private void printLteSignalStrengths() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        List<CellInfo> cellInfoList = telephonyManager.getAllCellInfo();
        for (CellInfo cellInfo : cellInfoList) {
            if (cellInfo instanceof CellInfoLte) {
                // cast to CellInfoLte and call all the CellInfoLte methods you need
                CellInfoLte ci = (CellInfoLte) cellInfo;

                System.out.println("Cell is registered : " + ci.isRegistered());
                System.out.println("LTE signal strength: " + ci.getCellSignalStrength().getDbm());
            }
        }
    }
}
