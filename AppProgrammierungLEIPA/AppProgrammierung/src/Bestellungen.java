
public class Bestellungen {
	
	 double menge; 
	 double entfernung; 
	 double dieselverbrauch;
	 double energieverbrauch;
	 double co2ausstoß;
	 static double umrechnungsfaktor = 26.5;
	 static double preisprotonne = 30;
	 static double bepreisungco2 = 0.07; 
	 
	 
	 public Bestellungen(double menge, double entfer, double dieselver, double energiever, double co2aus) {
		 this.menge = menge; 
		 this.entfernung = entfer;
		 this.dieselverbrauch = dieselver;
		 this.energieverbrauch = energiever; 
		 this.co2ausstoß = co2aus; 
		 
	 }
	 
	 public double berechnen() {
		 
		 LKW lk1 = new LKW(40, 25, 40, 30);
		 
		 this.energieverbrauch = lk1.verbrauch + (lk1.nutzlastkapazität-lk1.rohmengekapazität)*(this.entfernung/lk1.ladungsgewicht);
		 
		 
		 return co2ausstoß;
	 }
	 
	 
	 
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
