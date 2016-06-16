package gustavo.brilhante.tuotorial11aula14;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    TextView temperatureTextView, pressureTextView, lightTextview, humidityTextview;

    Button startSensors, stopSernsor;

    boolean lightAvailable = false, temperatureAvailable = false, pressureAvailable = false, humidityAvailable=false;

    Sensor light, temperature, humidity, pressure;
    SensorManager sMgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        temperatureTextView = (TextView) findViewById(R.id.temperatura_textview);
        lightTextview = (TextView) findViewById(R.id.luminosiade_textview);
        pressureTextView = (TextView) findViewById(R.id.pressao_textview);
        humidityTextview = (TextView) findViewById(R.id.umidade_textview);

        sMgr = (SensorManager)this.getSystemService(SENSOR_SERVICE);

        light = sMgr.getDefaultSensor(Sensor.TYPE_LIGHT);
        temperature = sMgr.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        pressure = sMgr.getDefaultSensor(Sensor.TYPE_PRESSURE);
        humidity = sMgr.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);

        startSensors = (Button) findViewById(R.id.start_sensors_button);
        startSensors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sMgr.registerListener(MainActivity.this, light , SensorManager.SENSOR_DELAY_NORMAL);
                sMgr.registerListener(MainActivity.this, temperature , SensorManager.SENSOR_DELAY_NORMAL);
                sMgr.registerListener(MainActivity.this, pressure , SensorManager.SENSOR_DELAY_NORMAL);
                sMgr.registerListener(MainActivity.this, humidity , SensorManager.SENSOR_DELAY_NORMAL);
            }
        });
        stopSernsor = (Button) findViewById(R.id.stop_sensors_button);
        stopSernsor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sMgr.unregisterListener(MainActivity.this, light);
                sMgr.unregisterListener(MainActivity.this, temperature);
                sMgr.unregisterListener(MainActivity.this, pressure);
                sMgr.unregisterListener(MainActivity.this, humidity);
            }
        });

        List<Sensor> msensorList = sMgr.getSensorList(SensorManager.SENSOR_ALL);
        for(int i =0 ; i< msensorList.size() ; i++){
            if(msensorList.get(i).getType() == Sensor.TYPE_LIGHT)lightAvailable=true;
            else if(msensorList.get(i).getType() == Sensor.TYPE_PRESSURE)pressureAvailable=true;
            else if(msensorList.get(i).getType() == Sensor.TYPE_AMBIENT_TEMPERATURE)temperatureAvailable=true;
            else if(msensorList.get(i).getType() == Sensor.TYPE_RELATIVE_HUMIDITY)humidityAvailable=true;
        }
        if(!lightAvailable)lightTextview.setText("Luminosidade: não disponível" );
        if(!pressureAvailable)pressureTextView.setText("Pressão: não disponível" );
        if(!temperatureAvailable)temperatureTextView.setText("Temperatura: não disponível" );
        if(!humidityAvailable)humidityTextview.setText("Humidade: não disponível" );
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_LIGHT) {
            lightTextview.setText("Luminosidade: "+event.values[0] );

        }else if(event.sensor.getType() == Sensor.TYPE_PRESSURE){
            pressureTextView.setText("Pressão: "+event.values[0] );

        }else if(event.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE){
            temperatureTextView.setText("Temperatura: "+event.values[0] );

        }else if(event.sensor.getType() == Sensor.TYPE_RELATIVE_HUMIDITY){
            humidityTextview.setText("Humidade: "+event.values[0] );

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
