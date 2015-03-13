package com.devclo.ticketoptimizer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.devclo.util.Tickets;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class MainActivity extends ActionBarActivity
                          implements View.OnClickListener, View.OnKeyListener {

    private TextView tvCantOri1;
    private TextView tvCantOri2;
    private TextView tvCantRes1;
    private TextView tvCantRes2;

    private EditText etCantOri1;
    private EditText etCantOri2;
    private EditText etCantRes1;
    private EditText etCantRes2;
    private EditText etCantAConvertir;
    private EditText etCantResEfectivo;

    private View btnCalcular;


    private float valorTicket1;
    private float valorTicket2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        try {
            valorTicket1 = Float.valueOf(sharedPref.getString("pref_ticket1", "4.00"));
        }catch (Exception e){
            valorTicket1 = 4.00f;
        }
        try {
            valorTicket2 = Float.valueOf(sharedPref.getString("pref_ticket2", "1.40"));
        }catch (Exception e){
            valorTicket2 = 1.40f;
        }

        tvCantOri1 = (TextView)findViewById(R.id.tvCantOri1);
        tvCantOri1.setText(getString(R.string.CantidadTicket1, String.valueOf(valorTicket1)));
        tvCantOri2 = (TextView)findViewById(R.id.tvCantOri2);
        tvCantOri2.setText(getString(R.string.CantidadTicket2, String.valueOf(valorTicket2)));


        tvCantRes1 = (TextView)findViewById(R.id.tvCantRes1);
        tvCantRes1.setText(getString(R.string.CantidadTicket1, String.valueOf(valorTicket1)));
        tvCantRes2 = (TextView)findViewById(R.id.tvCantRes2);
        tvCantRes2.setText(getString(R.string.CantidadTicket2, String.valueOf(valorTicket2)));

        etCantOri1 = (EditText)findViewById(R.id.etCantOri1);
        etCantOri2 = (EditText)findViewById(R.id.etCantOri2);
        etCantRes1 = (EditText)findViewById(R.id.etCantRes1);
        etCantRes2 = (EditText)findViewById(R.id.etCantRes2);
        etCantAConvertir = (EditText)findViewById(R.id.etCantAConvertir);
        etCantResEfectivo = (EditText)findViewById(R.id.etCantResEfectivo);

        //Valores por defecto en los EditText
        etCantOri1.setText("100");
        etCantOri2.setText("100");
        etCantAConvertir.setText("4.5");

        // Asignar el evento click al boton
        btnCalcular = findViewById(R.id.btnCalcular);
        btnCalcular.setOnClickListener(this);

        //Nos posicionamos a la derecha del EditText
        etCantAConvertir.setSelection(etCantAConvertir.getText().length());
        //Mostramos el teclado
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        //Seleccionamos el texto
        etCantAConvertir.selectAll();

        // Ejecutamos el botón al pulsar siguiente en el teclado
        etCantAConvertir.setOnKeyListener(this);

    }

    @Override
    protected void onStart()
    {
        super.onStart();


        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        try {
            valorTicket1 = Float.valueOf(sharedPref.getString("pref_ticket1", "4.00"));
        }catch (Exception e){
            valorTicket1 = 4.00f;
        }
        try {
            valorTicket2 = Float.valueOf(sharedPref.getString("pref_ticket2", "1.40"));
        }catch (Exception e){
            valorTicket2 = 1.40f;
        }

        //tvCantOri1 = (TextView)findViewById(R.id.tvCantOri1);
        tvCantOri1.setText(getString(R.string.CantidadTicket1, String.valueOf(valorTicket1)));
        //TextView tvCantOri2 = (TextView)findViewById(R.id.tvCantOri2);
        tvCantOri2.setText(getString(R.string.CantidadTicket2, String.valueOf(valorTicket2)));


        //TextView tvCantRes1 = (TextView)findViewById(R.id.tvCantRes1);
        tvCantRes1.setText(getString(R.string.CantidadTicket1, String.valueOf(valorTicket1)));
        //TextView tvCantRes2 = (TextView)findViewById(R.id.tvCantRes2);
        tvCantRes2.setText(getString(R.string.CantidadTicket2, String.valueOf(valorTicket2)));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(MainActivity.this, PrefActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==findViewById(R.id.btnCalcular).getId())
        {
            DecimalFormat df = new DecimalFormat("##0.00");
            Map<Float, Integer> cantOri = new HashMap<>();
            Map<Float, Integer> cantDes;
            try {
                cantOri.put(valorTicket1, Integer.valueOf(etCantOri1.getText().toString()));
            } catch (Exception e){
                cantOri.put(valorTicket1, 0);
            }
            try {
                cantOri.put(valorTicket2, Integer.valueOf(etCantOri2.getText().toString()));
            } catch (Exception e){
                cantOri.put(valorTicket2, 0);
            }
            float cantAConvertir;
            try {
                cantAConvertir = Float.valueOf(etCantAConvertir.getText().toString());
            } catch (Exception e){
                cantAConvertir = 0;
            }
            Tickets ticket = new Tickets(cantOri, cantAConvertir);

            cantDes = ticket.getCantRes();
            Iterator it = cantDes.keySet().iterator();
            while(it.hasNext()){
                float key = Float.parseFloat(it.next().toString());

                if(key == valorTicket1) {
                    etCantRes1.setText(cantDes.get(key).toString());
                } else if (key == valorTicket2) {
                    etCantRes2.setText(cantDes.get(key).toString());
                }
            }
            etCantResEfectivo.setText(df.format(ticket.getResto()));
            //etCantResEfectivo.setText(String.valueOf(ticket.getResto()));
        }



        //Ocultamos el teclado TODO... No funciona así... revisar
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        // Comprobamos que se ha pulsado la tecla enter.
        if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                (keyCode == KeyEvent.KEYCODE_ENTER)) {

            btnCalcular.callOnClick();
            return true;
        }// end if.
        return false;
    }// end onKey.
}

