package awele.bot.competitor.AB_MattisDroite;

import java.util.Random;

public class Individu implements Comparable<Individu>{
	static double chanceMutation =0.2;
	static int minPoids =-20, maxPoids=20;
	int fitness;
	int[] poids;
	
	Individu(int[] poids){
		this.fitness = 0;
		this.poids = poids;
	}
	
	Individu(Individu papa, Individu maman) {
		poids = new int[6];
		for(int i = 0; i<6; i++) {
			if(new Random().nextInt(2) == 0) {
				poids[i] = papa.poids[i];
			} else {
				poids[i] = maman.poids[i];
			}
		}
		mutation();
	}
	
	public void mutation() {
		if(random(0,100) < chanceMutation *100) {
			poids[random(0,5)] = random(minPoids, maxPoids);
		}
	}
	
    private int random(int min, int max) {
    	return min+new Random().nextInt(max-min)+1;
    }


	@Override
	public int compareTo(Individu o) {
		return o.fitness - this.fitness;
	}
	
	public String toString() {
		return "Individu[fitness:"+fitness+", poids:"+poids[0]+", "+poids[1]+", "+poids[2]+", "+poids[3]+", "+poids[4]+", "+poids[5]+"]";
	}
}