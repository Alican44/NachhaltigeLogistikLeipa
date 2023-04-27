import produkt.*;
public class main {
    public static void main(String [] args) {

        Papair papair = new Papair();
        Easy2Cool easy2Cool = new Easy2Cool();
        PullPack pullPack = new PullPack();

       final double umrechnungsfaktor = 26.5;
       final double preisProTonne = 30;
       final double co2Bepreisung = 0.07;

       final double lkwVerbrauch = 40;
       final double rohkapazitaet = 30;
       final double nutzlastKapazitaet = 40;

       int dist = 300;

       Bestellung bestellung = new Bestellung();
       bestellung.papair = papair;
       bestellung.easy2Cool = easy2Cool;
       bestellung.pullPack = pullPack;
       bestellung.mengePapair = 400;
       bestellung.mengeEasy2Cool= 400;
       bestellung.mengePullPack = 70;
       bestellung.kunde= new Kunde(0001,"Bleistift AG","Muenchen");
       bestellung.gesamtgewicht = bestellung.berechneGesamtGewicht();
       bestellung.entfernung = dist;
       bestellung.energieverbrauch = bestellung.berechneEnergieverbrauch(lkwVerbrauch,rohkapazitaet,nutzlastKapazitaet);
       bestellung.dieselverbrauch = bestellung.berechneDieselverbrauch();
       bestellung.co2ausstoß = bestellung.berechneCO2ausstoß(lkwVerbrauch,rohkapazitaet,nutzlastKapazitaet);


       System.out.println("Energieverbrauch: "+ bestellung.energieverbrauch);
       System.out.println("Dieselverbrauch: "+ bestellung.dieselverbrauch);
       System.out.println("CO2 Ausstoß: "+ bestellung.co2ausstoß);
       System.out.println("CO2 Ausstoß: "+ bestellung.co2ausstoß);
       System.out.println("Gesamtgewicht: "+ bestellung.gesamtgewicht);
       
       System.out.println(bestellung.berechneExtern(1502.3));
       


    }
}

