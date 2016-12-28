import java.util.HashSet;
import java.util.Set;
import java.util.Arrays;
import java.lang.Math;

public class CSPZebra extends CSP {
	static String[][] pairs = {{"englishman","red"}, {"spaniard","dog"}, {"coffee","green"}, {"ukrainian","tea"}, 
							{"old-gold", "snails"}, {"kools", "yellow"}, {"orange-juice", "lucky-strike"}, 
							{"japanese", "parliament"}};
	static Set<Object> varCol = new HashSet<Object>( Arrays.asList(new String[] {"blue", "green", "ivory", "red", "yellow"}));
	static Set<Object> varDri = new HashSet<Object>( Arrays.asList(new String[] {"coffee", "milk", "orange-juice", "tea", "water"}));
	static Set<Object> varNat = new HashSet<Object>( Arrays.asList(new String[] {"englishman", "japanese", "norwegian", "spaniard", "ukrainian"}));
	static Set<Object> varPet = new HashSet<Object>( Arrays.asList(new String[] {"dog", "fox", "horse", "snails", "zebra"}));
	static Set<Object> varCig = new HashSet<Object>( Arrays.asList(new String[] {"chesterfield", "kools", "lucky-strike", "old-gold", "parliament"}));
	
	public boolean isGood(Object X, Object Y, Object x, Object y) {		
		
		//if X is not even mentioned in by the constraints, just return true
		//as nothing can be violated
		if(!C.containsKey(X))
			return true;
		
		//check to see if there is an arc between X and Y
		//if there isn't an arc, then no constraint, i.e. it is good
		if(!C.get(X).contains(Y))
			return true;
		
		for(Object[] p : pairs) {
			if(X.equals(p[0]) && Y.equals(p[1]) && !x.equals(y))
				return false;
		}

		//Uniqueness constraints
		if(varCol.contains(X) && varCol.contains(Y) && !X.equals(Y) && x.equals(y))
			return false;
		if(varDri.contains(X) && varDri.contains(Y) && !X.equals(Y) && x.equals(y))
			return false;
		if(varNat.contains(X) && varNat.contains(Y) && !X.equals(Y) && x.equals(y))
			return false;
		if(varPet.contains(X) && varPet.contains(Y) && !X.equals(Y) && x.equals(y))
			return false;
		if(varCig.contains(X) && varCig.contains(Y) && !X.equals(Y) && x.equals(y))
			return false;
		

		//Higher order constraints
		int xDom = (int) x, yDom = (int) y;
		if(X.equals("chesterfield") && Y.equals("fox") && Math.abs(xDom-yDom) != 1)
			return false;		
		if(X.equals("kools") && Y.equals("horse") && Math.abs(xDom-yDom) != 1)
			return false;
		if(X.equals("norwegian") && Y.equals("blue") && Math.abs(xDom-yDom) != 1)
			return false;
		if(X.equals("green") && Y.equals("ivory") && xDom-yDom != 1)
			return false;

		return true;
	}
		
	public static void main(String[] args) throws Exception {
		CSPZebra csp = new CSPZebra();		 
		Integer[] dom = {1,2,3,4,5};
		
		//Initialize domains to variables with unary constraints
		for(Object X : varCol) 
			csp.addDomain(X, dom);
		for(Object X : varPet) 
			csp.addDomain(X, dom);
		for(Object X : varCig)
			csp.addDomain(X, dom);
		for(Object X : varDri){
			if(X.equals("milk")){
				Integer[] milkDom = {3};
				csp.addDomain(X, milkDom);
			}
			else
				csp.addDomain(X, dom);
		}
		for(Object X : varNat){
			if(X.equals("norwegian")){
				Integer[] norDom = {1};
				csp.addDomain(X, norDom);
			}
			else
				csp.addDomain(X, dom);
		}
		
		//initialize binary constraints
		for(Object[] p : pairs)
			csp.addBidirectionalArc(p[0], p[1]);

		//initialize n-ary constraints
		csp.addBidirectionalArc("chesterfield", "fox");
		csp.addBidirectionalArc("kools", "horse");
		csp.addBidirectionalArc("norwegian", "blue");
		csp.addBidirectionalArc("green", "ivory");

		//uniqueness constraints
		for(Object X : varCol)
			for(Object Y : varCol)
				csp.addBidirectionalArc(X,Y);
		for(Object X : varDri)
			for(Object Y : varDri)
				csp.addBidirectionalArc(X,Y);
		for(Object X : varNat)
			for(Object Y : varNat)
				csp.addBidirectionalArc(X,Y);
		for(Object X : varPet)
			for(Object Y : varPet)
				csp.addBidirectionalArc(X,Y);
		for(Object X : varCig)
			for(Object Y : varCig)
				csp.addBidirectionalArc(X,Y);

		Search search = new Search(csp);
		System.out.println(search.BacktrackingSearch());
	}
}