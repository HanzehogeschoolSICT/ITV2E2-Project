package model;

import java.util.ArrayList;

public class MinMaxResult {
	
	//{x,y,minMaxResult}
	private ArrayList<int[]> possibles;
	
	public MinMaxResult(){
		this.possibles = new ArrayList<int[]>();
	}
	
	public void addResult(int x, int y, int minMax){
		int[] details = {x,y,minMax};
		this.possibles.add(details);
	}
	
	public int[] getMax(){
		int max = -1;
		int maxIndex = 0;
		for(int x=0; x < this.possibles.size(); x++){
			int[] details = this.possibles.get(x);
			if(details[2] >= max){
				max = details[2];
				maxIndex = x;
			}		
		}
		return this.possibles.get(maxIndex);
	}
	
	public int[] getMin(){
		int min = 1;
		int minIndex = 0;
		for(int x=0; x < this.possibles.size(); x++){
			int[] details = this.possibles.get(x);
			if(details[2] <= min){
				min = details[2];
				minIndex = x;
			}		
		}
		return this.possibles.get(minIndex);
	}
}
