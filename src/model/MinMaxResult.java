package model;

import java.util.ArrayList;

public class MinMaxResult {
	
	//{x,y,minMaxResult}
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
	
	public void printResult(){
		
		for(int x=0; x < this.possibles.size(); x++){
			int[] details = this.possibles.get(x);
			System.out.println("Minimax Coords: " + details[1] + ", " + details[0] + ". And score: " + details[2]);
		}
		
	}
	
	public int[] getMax(){
		int max = -99;
		int maxIndex = 0;
		for(int x=0; x < this.possibles.size(); x++){
			int[] details = this.possibles.get(x);
			if(details[2] > max){
				max = details[2];
				maxIndex = x;
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
			}		
		}
		return this.possibles.get(minIndex);
	}
}
