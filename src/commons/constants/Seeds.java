package commons.constants;

import java.util.ArrayList;
import java.util.Arrays;

public class Seeds {
	
	/*
	 * TODO 
	 *    - fix this, make it work with objects of type "Word"
	 *    - add the list of seed words for each class
	 */
	
	protected static ArrayList<String> love;
	protected static ArrayList<String> hate;
	
	protected static ArrayList<String> happiness;
	protected static ArrayList<String> sadness;
	
	protected static ArrayList<String> fun;
	protected static ArrayList<String> boredom;
	
	protected static ArrayList<String> calm;
	protected static ArrayList<String> anger;
	
	protected static ArrayList<String> enthusiasm;
	protected static ArrayList<String> indifference;
	
	protected static ArrayList<String> relief;
	protected static ArrayList<String> worry;
	
	protected static ArrayList<String> optimisim;
	protected static ArrayList<String> pessimism;
	
	protected static ArrayList<String> respect;
	protected static ArrayList<String> disrespect;
	
	protected static ArrayList<String> neutral;
	protected static ArrayList<String> surprise;

	
	public static ArrayList<String> getLove() {
		if(love==null || love.isEmpty()) {
			love = new ArrayList<>(Arrays.asList("love"));
		}
		return love;
	}
	public static void setLove(ArrayList<String> love) {
		Seeds.love = love;
	}
	
	public static ArrayList<String> getHate() {
		return hate;
	}
	public static void setHate(ArrayList<String> hate) {
		Seeds.hate = hate;
	}
	
	
	public static ArrayList<String> getHappiness() {
		return happiness;
	}
	public static void setHappiness(ArrayList<String> happiness) {
		Seeds.happiness = happiness;
	}

	public static ArrayList<String> getSadness() {
		return sadness;
	}
	public static void setSadness(ArrayList<String> sadness) {
		Seeds.sadness = sadness;
	}


	public static ArrayList<String> getFun() {
		return fun;
	}
	public static void setFun(ArrayList<String> fun) {
		Seeds.fun = fun;
	}
	public static ArrayList<String> getBoredom() {
		return boredom;
	}
	public static void setBoredom(ArrayList<String> boredom) {
		Seeds.boredom = boredom;
	}
	public static ArrayList<String> getCalm() {
		return calm;
	}
	public static void setCalm(ArrayList<String> calm) {
		Seeds.calm = calm;
	}
	public static ArrayList<String> getAnger() {
		return anger;
	}
	public static void setAnger(ArrayList<String> anger) {
		Seeds.anger = anger;
	}
	public static ArrayList<String> getEnthusiasm() {
		return enthusiasm;
	}
	public static void setEnthusiasm(ArrayList<String> enthusiasm) {
		Seeds.enthusiasm = enthusiasm;
	}
	public static ArrayList<String> getIndifference() {
		return indifference;
	}
	public static void setIndifference(ArrayList<String> indifference) {
		Seeds.indifference = indifference;
	}
	public static ArrayList<String> getRelief() {
		return relief;
	}
	public static void setRelief(ArrayList<String> relief) {
		Seeds.relief = relief;
	}
	public static ArrayList<String> getWorry() {
		return worry;
	}
	public static void setWorry(ArrayList<String> worry) {
		Seeds.worry = worry;
	}
	public static ArrayList<String> getOptimisim() {
		return optimisim;
	}
	public static void setOptimisim(ArrayList<String> optimisim) {
		Seeds.optimisim = optimisim;
	}
	public static ArrayList<String> getPessimism() {
		return pessimism;
	}
	public static void setPessimism(ArrayList<String> pessimism) {
		Seeds.pessimism = pessimism;
	}
	public static ArrayList<String> getRespect() {
		return respect;
	}
	public static void setRespect(ArrayList<String> respect) {
		Seeds.respect = respect;
	}
	public static ArrayList<String> getDisrespect() {
		return disrespect;
	}
	public static void setDisrespect(ArrayList<String> disrespect) {
		Seeds.disrespect = disrespect;
	}
	public static ArrayList<String> getNeutral() {
		return neutral;
	}
	public static void setNeutral(ArrayList<String> neutral) {
		Seeds.neutral = neutral;
	}
	public static ArrayList<String> getSurprise() {
		return surprise;
	}
	public static void setSurprise(ArrayList<String> surprise) {
		Seeds.surprise = surprise;
	}
	
	
	
	

}
