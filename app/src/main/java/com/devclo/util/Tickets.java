package com.devclo.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Tickets {

    private Map<Float, Integer> cantOri;
    private float cantAConvertir;

    public Tickets () {
        this.cantAConvertir = 0;
        this.cantOri = new HashMap<>();
    }

    public Tickets (Map<Float, Integer> CantOri, float CantAConvertir) {

        this.cantAConvertir = CantAConvertir;
        this.cantOri = new HashMap<>();
        // Cargamos las cantidades
        Iterator it = CantOri.keySet().iterator();
        while(it.hasNext()){
            float key;
            key =  Float.parseFloat(it.next().toString());
            this.cantOri.put(key, CantOri.get(key));
        }
    }

    public float getResto()
    {
        float etCantResEfectivo = cantAConvertir;
        Map<Float, Integer> cantRes = getCantRes();

        Iterator it = cantOri.keySet().iterator();
        while(it.hasNext()){
            float key;
            key =  Float.parseFloat(it.next().toString());
            etCantResEfectivo = etCantResEfectivo - ( key * cantRes.get(key).floatValue()) ;
        }
        return roundFloat(etCantResEfectivo);
    }

    //TODO: Pendiente rellenar
    public Map<Float, Integer> getCantRes() {
        Map<Float, Integer> cantRes = new HashMap<>();
        float valorTicket1 = 0;
        float valorTicket2 = 0;
        int cantTicket1 = 0;
        int cantTicket2 = 0;
        Iterator it = cantOri.keySet().iterator();
        boolean inicializa =true;
        while(it.hasNext()){
            float key;
            key =  Float.parseFloat(it.next().toString());
            cantRes.put(key, 0);

            // Ponemos en valorTicket1 el ticket de mayor valor y en valorTicket2 el de menor
            if(inicializa) {
                valorTicket1 = key;
                valorTicket2 = key;
                cantTicket1 = cantOri.get(key);
                cantTicket2 = cantOri.get(key);
                inicializa = false;
            }
            if (valorTicket1 >= key) {
                valorTicket2 = key;
                cantTicket2 = cantOri.get(key);
            } else {
                valorTicket1 = key;
                cantTicket1 = cantOri.get(key);
            }
        }

        float auxCantAConvertir = cantAConvertir;
        int auxCantTicket1 = 0;
        int auxCantTicket2 = 0;
        // TODO: El algoritmo es muy chapucero, hay que mejorarlo
        while ((auxCantAConvertir >= valorTicket2 && cantTicket2 > 0) ||
               (auxCantAConvertir >= valorTicket1 && cantTicket1 > 0))  {

            if(auxCantAConvertir >= valorTicket1 && cantTicket1 > 0) {
                auxCantAConvertir = auxCantAConvertir - valorTicket1;
                cantTicket1--;
                auxCantTicket1++;
            }  else  if(auxCantAConvertir >= valorTicket2 && cantTicket2 > 0) {
                auxCantAConvertir = auxCantAConvertir - valorTicket2;
                cantTicket2--;
                auxCantTicket2++;
            }
            // Por la perdida de decimales al usar float debemos realizar este redondeo
            auxCantAConvertir = roundFloat(auxCantAConvertir);
        }
        cantRes = new HashMap<>();
        cantRes.put(valorTicket1, auxCantTicket1);
        cantRes.put(valorTicket2, auxCantTicket2);

        return cantRes;
    }

    private float roundFloat(float in) {
        return ((int)((in*100f)+0.5f))/100f;
    }
}