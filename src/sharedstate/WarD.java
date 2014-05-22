package sharedstate;

import main.War;

public class WarD extends GameObject {
	int team;
	War war;
	public long counter1;
	public long counter2;
	
	public WarD(){
		team = 0;
		//this.war = war;
		counter1 = 0;
		counter2 = 0;
	}

	@Override
	public void update() {
		if(team == 1){
			counter1 ++;
		}
		else if(team == 2){
			counter2 ++;
			
		}
		//war.updateScore(counter1, counter2);
	}

	
	public void setAttackers(int attack){
		team = attack;
	}

}
