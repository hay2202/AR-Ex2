/**
 * This class represents the basic flight controller of the Bereshit space craft.
 * @author ben-moshe
 *
 */
public class Bereshit_101 {
	public static final double WEIGHT_EMP = 165; // kg
	public static final double WEIGHT_FULE = 420; // kg
	public static final double WEIGHT_FULL = WEIGHT_EMP + WEIGHT_FULE; // kg
	// https://davidson.weizmann.ac.il/online/askexpert/%D7%90%D7%99%D7%9A-%D7%9E%D7%98%D7%99%D7%A1%D7%99%D7%9D-%D7%97%D7%9C%D7%9C%D7%99%D7%AA-%D7%9C%D7%99%D7%A8%D7%97
	public static final double MAIN_ENG_F = 430; // N - Force of main engine in newton units
	public static final double SECOND_ENG_F = 25; // N - Force of secondary engines in newton units
	public static final double MAIN_BURN = 0.15; //liter per sec, 12 liter per m'
	public static final double SECOND_BURN = 0.009; //liter per sec 0.6 liter per m'
	public static final double ALL_BURN = MAIN_BURN + 8*SECOND_BURN;

	public static double accMax(double weight) {
		return acc(weight, true,8);
	}
	public static double acc(double weight, boolean main, int seconds) {
		double t = 0;
		if(main) {
			t += MAIN_ENG_F;
		}
		t += seconds*SECOND_ENG_F;
		double ans = t/weight;
		return ans;
	}

	public static double cal_force(double hs, double vs, double t, double w, double ang) {
		double a = Math.pow((w * (-hs) / t)*Math.cos(ang), 2);
		double b = Math.pow(((w * (-vs) / t)-Moon.ACC * w) * Math.sin(ang), 2);

		return Math.sqrt(a + b);
	}

	// 14095, 955.5, 24.8, 2.0
	public static void main(String[] args) {
		System.out.println("Simulating Bereshit's Landing:");
		// starting point:
		double vs = 24.8; // Vertical speed
		double hs = 932; // horizontal speed
		double dist = 181*1000; // distance (181 km??)
		double ang = 58.3; // zero is vertical (as in landing) - starting angle
		double alt = 13748; // 2:25:40 (as in the simulation) // https://www.youtube.com/watch?v=JJ0VfRL9AMs // altitude
		double time = 0;
		double dt = 1; // sec
		double acc=0; // Acceleration rate (m/s^2)
		double fuel = 121; //
		double weight = WEIGHT_EMP + fuel; // 165 + 121 = 286 kg
//		System.out.println("time, vs, hs, dist, alt, ang,weight,acc");
		double NN = 0.7; // rate[0,1] // כוח המנועים - 0 = לא עובד, 1 = עובד על מקסימום
		// ***** main simulation loop ******
		while(alt>0) {
			if(time%10==0 || alt<100) {
				System.out.println("************************");
				System.out.println("time: " + time + "," +" VerticalSpeed: " + vs+","+" HorizontalSpeed: "+hs+","+" DistanceFromEstimateLanding: " +dist+
						","+"\nAltitude: "+alt+","+" Angle: "+ang+","+" Fuel: "+(weight - WEIGHT_EMP)+","+" Acceleration rate: "+acc);
			}
			// over 2 km above the ground
			if(alt>2000) {	// maintain a vertical speed of [20-25] m/s
				if(vs >25) {NN+=0.003*dt;} // more power for braking
				if(vs <20) {NN-=0.003*dt;} // less power for braking
			}
			// lower than 2 km - horizontal speed should be close to zero
			else {
				if(ang > 9) {
					ang -= 5.5; // rotate to vertical position.
				}
				else {
					ang = 0;
				}
				if(alt>1000) {
					NN=0.58;
				}
				else if(alt>500) {
					NN=0.6;
				}
				else if(alt>100) {
					NN=0.69;
					if(vs<20)NN=0.65;
				}
				else if(alt>40){
					NN=1;
					if(vs<10 && vs>5) {NN=0.71;}
				}
				else {
					if(vs>5)NN=0.9;
					if(vs<5 && vs>3) {NN=0.75;}
					else if (vs<3) {NN=0.85;}
				}
//				NN = 0.5; // brake slowly, a proper PID controller here is needed!
				if (hs < 2) {
					hs = 0;
				}
//				if (alt < 125) { // very close to the ground!
//					NN = 1; // maximum braking!
//					if (vs < 5) {
//						NN = 0.7; // if it is slow enough - go easy on the brakes
//					}
//				}
			}
			if (alt < 5) { // no need to stop
				NN = 0.4;
			}
//			double f = cal_force(hs, vs, 630-time, weight, ang);
//			NN = f / 630;

			// main computations
			double ang_rad = Math.toRadians(ang);
			double h_acc = Math.sin(ang_rad)*acc;
			double v_acc = Math.cos(ang_rad)*acc;
			double vacc = Moon.getAcc(hs);
			time += dt;
			double as = (hs/dist)/Math.toRadians(Math.PI*2); //angular speed
			ang += as;
			double dw = dt * ALL_BURN * NN;
			if (fuel > 0) {
				fuel -= dw;
				weight = WEIGHT_EMP + fuel;
				acc = NN * accMax(weight);
			}
			else { // ran out of fuel
				acc=0;
			}

			v_acc -= vacc;
			if (hs > 0) {
				hs -= h_acc * dt ;
			}
			dist -= hs * dt;
			vs -= v_acc * dt;
			if(vs < 0) { vs = 1; }
			alt -= dt * vs;
		}
	}
}
