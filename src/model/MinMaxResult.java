package model;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class MinMaxResult {
	
	private ArrayList<int[]> possibles;
	
	public MinMaxResult(){
		this.possibles = new ArrayList<int[]>();
	}
	
	public void addResult(int x, int y, int minMax){
		int[] details = new int[]{x,y,minMax};
		this.possibles.add(details);
	}
	
	public int getResult(){
		Integer result = null;
		for(int x=0; x < this.possibles.size(); x++){
			int[] details = this.possibles.get(x);
			if(result != null){
			result = result + details[2];
			}else{
				result = details[2];
			}
		}
		return result;
	}
	
	public int[] getMax(){
		int max = -99;
		int maxIndex = 0;
		for(int x=0; x < this.possibles.size(); x++){
			int[] details = this.possibles.get(x);
			if(details[2] > max){
				max = details[2];
				maxIndex = x;
			}else if(details[2] == max){
				if(ThreadLocalRandom.current().nextInt(0, 1 + 1) == 1){
					max = details[2];
					maxIndex = x;
				}
			}
		}
		return this.possibles.get(maxIndex);
	}
	 public int getSize(){
		 return this.possibles.size();
	 }
	
	public int[] getMin(){
		int min = 99;
		int minIndex = 0;
		for(int x=0; x < this.possibles.size(); x++){
			int[] details = this.possibles.get(x);
			if(details[2] < min){
				min = details[2];
				minIndex = x;
			}else if(details[2] == min){
				if(ThreadLocalRandom.current().nextInt(0, 1 + 1) == 1){
					min = details[2];
					minIndex = x;
				}
			}	
		}
		return this.possibles.get(minIndex);
	}
}
