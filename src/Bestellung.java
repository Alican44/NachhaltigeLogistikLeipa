import produkt.Easy2Cool;
import produkt.Papair;
import produkt.Produkt;
import produkt.PullPack;
import java.util.*;
import java.util.stream.Collectors;
import java.lang.reflect.Array;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
//import org.json.JSONArray;
//import org.json.JSONObject;



public class Bestellung {
    // Bestellungen enthalten Produkte
    Papair papair;
    Easy2Cool easy2Cool;
    PullPack pullPack;

    int mengePapair;
    int mengeEasy2Cool;
    int mengePullPack;

    // Bestellungen können einem Kunden zugeordnet werden, der einen eindeutigen Standort hat
    Kunde kunde;

    // Kennzahlen und Daten der Bestellung
    double gesamtgewicht;       // (P1*Menge)+(P2*Menge)+(P3*Menge)
    double entfernung;          // Produktionsstätte und zentrallager zum Kunden (Lösung: Algorithmus für Kombination)
    double dieselverbrauch;     // (Entfernung * Energieverbrauch) / 100 >> Dieselverbrauch insg
    double energieverbrauch;    // Verbrauch vom LKW + ( Kapazität - Rohmenge) * MengeProd / gesamtgewicht
    double co2ausstoß;          // gesamter CO2 Ausstoß, wir brauchen diesen Wert für verschiedene Liefervarianten
    // Entfernung * 26,5 * Energieverbrauch


    public Bestellung(Papair papair, Easy2Cool easy2Cool, PullPack pullPack, int mengePapair, int mengeEasy2Cool, int mengePullPack,
                      Kunde kunde, double gesamtgewicht, double entfernung, double dieselverbrauch, double energieverbrauch, double co2ausstoß) {
        this.papair = papair;
        this.easy2Cool = easy2Cool;
        this.pullPack = pullPack;
        this.mengePapair = mengePapair;
        this.mengeEasy2Cool = mengeEasy2Cool;
        this.mengePullPack = mengePullPack;
        this.kunde = kunde;
        this.gesamtgewicht = berechneGesamtGewicht();
        this.entfernung = entfernung;
        this.dieselverbrauch = dieselverbrauch;
        this.energieverbrauch = energieverbrauch;
        this.co2ausstoß = co2ausstoß;
    }

    public Bestellung() {
    }


    public double berechneGesamtGewicht() {
        return (mengePapair * papair.gewicht) + (mengeEasy2Cool * easy2Cool.gewicht) + (mengePullPack * pullPack.gewicht);
    }

    public double berechneDieselverbrauch() {
        return (entfernung * energieverbrauch) / 100;
    }

    public double berechneEnergieverbrauch(double lkwVerbrauch, double rohkapazitaet, double nutzlast) {
        return (lkwVerbrauch + (rohkapazitaet - nutzlast)) * (mengeEasy2Cool + mengePullPack + mengePapair) / gesamtgewicht;
    }

    public double berechneCO2ausstoß(double lkwVerbrauch, double rohkapazitaet, double nutzlast) {
        return entfernung * 26.5 * berechneEnergieverbrauch(lkwVerbrauch, rohkapazitaet, nutzlast);
    }

    public double berechneExtern (double gesamtgewicht){
    double transportkosten = 0;
    int index = -1;
    
    double[] kosten = new double[6]; 
    kosten[0] = 4.50;
    kosten[1] = 5.20;
    kosten[2] = 6.15;
    kosten[3] = 7.75; 
    kosten[4] = 11.95;
    kosten[5] = 13.95;
    
    double[] maxgewicht = new double[6];
    maxgewicht[0] = 1.0;
    maxgewicht[1] =	3.0;
    maxgewicht[2] =	5.0;
    maxgewicht[3] =	10.0;
    maxgewicht[4] =	20.0;
    maxgewicht[5] = 31.5;
    

    for (int i = 0; i < maxgewicht.length; i++) {
        if (this.gesamtgewicht <= maxgewicht[i]) {
            index = i;
            break;
        }
        
    }
    
    if (index == -1) {
        index = maxgewicht.length - 1;
    }
    
    if (this.gesamtgewicht >= maxgewicht[index] && this.gesamtgewicht < maxgewicht[index+1]) {
        // Return the corresponding transport cost for the weight range
        return kosten[index];
    } else {
        // Return an error value indicating that the weight is outside the range of transport costs
        return -1;
    }
    
    
    
//    transportkosten = kosten[index];
    
//    System.out.println("Die Transportkosten betragen: " + transportkosten);
    
//    return transportkosten;
//    
    
    

		}




    public double berechneIntern(double gesamtgewicht, int plz) {  //formel für wirtschaftlichkeit anpassen!
    	
    	Math.ceil(gesamtgewicht);
        double versandkosten = 0;

        if (String.valueOf(plz).charAt(0) == '0') {
            versandkosten = gesamtgewicht/1000 * 53;
        } else if (String.valueOf(plz).charAt(0) == '1') {
            versandkosten = gesamtgewicht/1000 * 68;
        } else if (String.valueOf(plz).charAt(0) == '2') {
            versandkosten = gesamtgewicht/1000 * 80;
        } else if (String.valueOf(plz).charAt(0) == '3') {
            versandkosten = gesamtgewicht/1000 * 60;
        } else if (String.valueOf(plz).charAt(0) == '4') {
            versandkosten = gesamtgewicht/1000 * 54;
        } else if (String.valueOf(plz).charAt(0) == '5') {
            versandkosten = gesamtgewicht/1000 * 50;
        } else if (String.valueOf(plz).charAt(0) == '6') {
            versandkosten = gesamtgewicht/1000 * 48;
        } else if (String.valueOf(plz).charAt(0) == '7') {
            versandkosten = gesamtgewicht/1000 * 25;
        } else if (String.valueOf(plz).charAt(0) == '8') {
            versandkosten = gesamtgewicht/1000 * 24;
        } else if (String.valueOf(plz).charAt(0) == '9') {
            versandkosten = gesamtgewicht/1000 * 24;
        } else {
        	System.out.println("Postleitzahl nicht verfügbar");
        }
        

    return versandkosten;

    }


    public static final double EARTH_RADIUS_KM = 6371.01;

    public static double distanceCalc(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = EARTH_RADIUS_KM * c;

        return distance;
    }




}

