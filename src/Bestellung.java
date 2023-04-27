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
import java.text.DecimalFormat;
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
    
    

    public double berechneExtern(double gesamtgewicht) {
    	
        double[] kosten = { 4.50, 5.20, 6.15, 7.75, 11.95, 13.95 };
        double[] gewichte = { 1.0, 3.0, 5.0, 10.0, 20.0, 31.5 };
        
        double summe = 0.0;
        int kostenIndex = -1;
        
        while (gesamtgewicht > 0) {
            for (int i = 0; i < gewichte.length; i++) {
                if (gesamtgewicht <= gewichte[i]) {
                    kostenIndex = i;
                    break;
                }
            }
            
            if (kostenIndex == -1) {
                kostenIndex = gewichte.length - 1;
            }
            
            summe += kosten[kostenIndex];
            gesamtgewicht -= gewichte[kostenIndex];
        }
        
      
        summe = Math.round(summe*100.0)/100.0;
        
        return summe;
    }


    
   

    public double berechneIntern(double gesamtgewicht, int plz) {  //formel für wirtschaftlichkeit anpassen!
    	// 1 Palette = 1 Tonne 
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

