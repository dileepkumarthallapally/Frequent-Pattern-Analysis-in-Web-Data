import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

class Arule {

	ArrayList<Integer> rulesStart = new ArrayList<Integer>();

	public ArrayList<Integer> getRulesStart() {
		return rulesStart;
	}

	public void setRulesStart(ArrayList<Integer> rulesStart) {
		this.rulesStart = rulesStart;
	}

	public int getNextPage() {
		return nextPage;
	}

	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	public double getSupport() {
		return support;
	}

	public void setSupport(double support) {
		this.support = support;
	}

	public double getConfidence() {
		return confidence;
	}

	public void setConfidence(double confidence) {
		this.confidence = confidence;
	}

	public double getLift() {
		return lift;
	}

	public void setLift(double lift) {
		this.lift = lift;
	}

	int nextPage;
	double support, confidence, lift;

	public boolean match(ArrayList<Integer> inter) {
		// TODO Auto-generated method stub
		ArrayList<Integer> a=this.getRulesStart();
		int index=(a.size())-inter.size();
		boolean bb=false;
		for (Integer ele : inter) {
		if(ele==a.get(index++))
		{
		bb=true;	
		}else
		{
			bb=false;
			break;
		}
		
		}
		if(bb==true)
			return true;
			else
			return false;
	}

}

public class Prediction {
	static String line = null;
	static int[][] T = new int[17][17];
	static double[][] Pr = new double[17][17];
	private static HashMap<Integer, Arule> Rules=new HashMap<Integer, Arule>();;

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String fileName = "sam.seq";

		// This will reference one line at a time
		/*
		 * String line = null; int [][] T = new int[17][17]; double [][] Pr =
		 * new double [17][17];
		 */
		for (int j = 0; j < 17; j++)
			for (int k = 0; k < 17; k++) {
				T[j][k] = 0;
				Pr[j][k] = 0d;
			}
		ArrayList<Integer> Seq = new ArrayList<Integer>();

		try {
			// FileReader reads text files in the default encoding.
			FileReader fileReader = new FileReader(fileName);

			// Always wrap FileReader in BufferedReader.
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while ((line = bufferedReader.readLine()) != null) {

				// System.out.println(line);
				StringTokenizer st = new StringTokenizer(line);
				while (st.hasMoreTokens()) {
					Seq.add(Integer.parseInt(st.nextToken()));

				}

				for (int i = 0; i < Seq.size() - 1; i++) {
					int r = Seq.get(i);
					int c = Seq.get(i + 1);

					T[r - 1][c - 1] = T[r - 1][c - 1] + 1;

				}
				Seq = new ArrayList<Integer>();

			}

			// Always close files.
			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");
			// Or we could just do this:
			// ex.printStackTrace();
		}
		
		  System.out.println("Transaction Matrix is:"); for(int j=0;j<17;j++) { for(int
		  k=0;k<17;k++) { System.out.print(T[j][k] +"     "); }
		  System.out.println(""); }
		 
		//System.out.println("Calculation of Probabilities:");
		double sum = 0d;
		for (int j = 0; j < 17; j++) {
			for (int k = 0; k < 17; k++) {
				sum = sum + T[j][k];
			}
		}
		for (int j = 0; j < 17; j++) {
			for (int k = 0; k < 17; k++) {
				int PofK = T[j][k];
				long total = 0;
				for (int k1 = 0; k1 < 17; k1++)
					total = T[j][k1];
				Pr[j][k] = PofK / sum;
//System.out.println("The Probability of visiting "+(j+1)+" --> "+(k+1) +" is:"+Pr[j][k]);
			}

		}

		
		  double maxP[][] = new double [17][2];
		  double max;
		  double loc; 
		  for(int j=0;j<17;j++)
		  {
			  max = Pr[j][0]; 
			  loc=0;
			 for(int k=1;k<17;k++) {
		  if(max<Pr[j][k]) {
			  max=Pr[j][k];
			  loc=k; 
			  }
		  }
			 maxP[j][0]=max;
		  maxP[j][1]=loc; 
		  //System.out.println("We visit page " +(int)(loc+1) +" after "+(j+1) +" i.e "+(j+1) +" --> "+ (int)(loc+1)+" with P = "+max ); 
		  // System.out.println("Sum = "+sum);
		  }
		/* for(int l=0;l<17;l++)
		 {
			System.out.println((l+1)+"==>"+maxP[l][1]+" P="+maxP[l][0]); 
			 }*/
		 
		System.out.println("Probability Matrix is:");
		for (int j = 0; j < 17; j++) {
			for (int k = 0; k < 17; k++) {
				System.out.print(Pr[j][k] + "     ");
			}
			System.out.println("");
		}

		LoadRules();
		/*Random random = new Random();
		Random random2 = new Random();
		File fout = new File("out.txt");
		if (!fout.exists()) {
			fout.createNewFile();
		}
		FileOutputStream fos = new FileOutputStream(fout);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		File fout1 = new File("out1.txt");
		if (!fout1.exists()) {
			fout1.createNewFile();
		}
		FileOutputStream fos1 = new FileOutputStream(fout1);
		BufferedWriter bw1 = new BufferedWriter(new OutputStreamWriter(fos1));

		for (int i = 1; i <= 500; i++) {
			int length = random2.nextInt(30) + 1;
			String str = "";
			System.out.println("length: " + length);
			for (int j = 0; j < length; j++) {

				int l = random.nextInt(17);
				if (l == 0) {
					l = random2.nextInt(16) + 1;

				}
				str = str + l + " ";
							}
			ArrayList<Integer> a1=new ArrayList<Integer>();
			a1=Rules.get(random2.nextInt(146)+1).getRulesStart();
			for (Integer ii : a1) {
				str=str+ii+" ";
			}
			System.out.println("Input: " + str);
			//Predict(str, bw,bw1);

		}
		// Predict("");
		bw.close();
		bw1.close();	
*/		
		Predict();
	}

	private static void Predict()
			throws IOException {
		// TODO Auto-generated method stub
		ArrayList<Arule> matches = new ArrayList<Arule>();
		System.out.println("Enter Input Sequence: ");
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String ss = in.readLine();
		while (!ss.equals("xx")) {
			boolean b = false, c = false;

			StringTokenizer st = new StringTokenizer(ss);
			ArrayList<Integer> inter = new ArrayList<Integer>();
			while (st.hasMoreTokens()) {
				inter.add(Integer.parseInt(st.nextToken()));
			}
			for (int i = 1; i <= Rules.size(); i++) {
				Arule rule = Rules.get(i);
				if (inter.size() == (rule.getRulesStart().size())) {
					if (inter.equals(rule.getRulesStart())) {
						matches.add(rule);
						//System.out.println("Matched");
						b = false;
						c = true;
					}
				} else {
					b = true;
				}

			}
			if (matches.size() > 0) {
				int loc = 0;
				double max = matches.get(0).getConfidence();
				for (int i = 0; i < matches.size(); i++) {
					if (max > matches.get(i).getConfidence()) {
						max = matches.get(i).getConfidence();
						loc = i;
					}
				}
				System.out.println("Next Page is: "
						+ matches.get(loc).getNextPage());// + " with confidence "
						//+ matches.get(loc).getConfidence());
				//bw.write(str);// + "==>" + matches.get(loc).getNextPage()+ " & confidence: " + matches.get(loc).getConfidence());
				//bw.newLine();
				//bw1.write( matches.get(loc).getNextPage()+"");
				//bw1.newLine();
			}
			else{
				ArrayList<Arule> matches1=new ArrayList<Arule>();
				for (int i = 1; i <= Rules.size(); i++) {
					Arule rule = Rules.get(i);
					if (inter.size()< (rule.getRulesStart().size())) {
						if(rule.match(inter))
						{
							matches1.add(rule);
						}
						
						
					}
				}
				if(matches1.size()>0 && inter.size()>1)
				{
					int loc = 0;
					double max = matches1.get(0).getConfidence();
					for (int i = 0; i < matches1.size(); i++) {
						if (max > matches1.get(i).getConfidence()) {
							max = matches1.get(i).getConfidence();
							loc = i;
						}
					}
					System.out.println("Next Page is: "
							+ matches1.get(loc).getNextPage());/* + " with confidence "
							+ matches1.get(loc).getConfidence()+" From SubRule: "+matches1.get(loc).getRulesStart().toString());
				bw.write(str + "==>" + matches1.get(loc).getNextPage()
							+ " & confidence: " + matches1.get(loc).getConfidence()+" From SubRule:"+matches1.get(loc).getRulesStart().toString());
					bw.newLine();
				*///	bw.write(str);// + "==>" + matches.get(loc).getNextPage()+ " & confidence: " + matches.get(loc).getConfidence());
				//bw.newLine();
				//bw1.write( matches1.get(loc).getNextPage()+"");
				//bw1.newLine();
					c=true;
					matches1.clear();
				}
				
			}
			if (b == true && c == false) {
				int kk=inter.size() - 1;
				if(kk<0)
					kk=inter.size();
				int j = inter.get(kk);
				j = j - 1;
				// System.out.println(j);
				int loc = 0;
				double max = Pr[j][0];

				for (int k = 1; k < 17; k++) {
					if (max < Pr[j][k]) {
						max = Pr[j][k];
						loc = k;

					}
				}
				System.out.println("Next page is " + (loc + 1));
						//+ " with probability " + max);
			/*	bw.write(str);// + "==>" + (loc + 1) + " & probability: " + max);
				bw.newLine();
				bw1.write((loc+1)+"");
				bw1.newLine();
			*/	
				b = false;

			}

			
			matches.clear();
			System.out.println("Enter Next Input Sequence:(xx to quit) ");
			ss = in.readLine();
		}
			//System.out.println("Loop End");
	}

	// }

	private static void LoadRules() {
		// TODO Auto-generated method stub
		String fileName = "RulesDF_NO_TID.txt";

		// This will reference one line at a time
		String line = null;
		FileReader fileReader;
		BufferedReader bufferedReader;
		try {
			fileReader = new FileReader(fileName);

			// Always wrap FileReader in BufferedReader.
			bufferedReader = new BufferedReader(fileReader);
			bufferedReader.readLine();
			//Rules = new HashMap<Integer, Arule>();
			int ruleCount = 0;
			while ((line = bufferedReader.readLine()) != null) {
				ruleCount++;
				StringTokenizer st = new StringTokenizer(line);
				while (st.hasMoreTokens()) {
					// System.out.println("rule number"+);
					st.nextToken();
					Arule rule = new Arule();
					ArrayList<Integer> a = new ArrayList<Integer>();
					char[] s = st.nextToken().toCharArray();
					for (int i = 0; i < s.length; i++) {
						if (Character.isDigit(s[i])) {
							a.add(Character.getNumericValue(s[i]));
						}
					}

					rule.setRulesStart(a);
					st.nextToken();
					s = st.nextToken().toCharArray();
					int nextPage = 00;
					for (int i = 0; i < s.length; i++) {
						if (Character.isDigit(s[i])) {
							nextPage = Character.getNumericValue(s[i]);
						}
					}
					rule.setNextPage(nextPage);
					rule.setSupport(Double.parseDouble(st.nextToken()));
					rule.setConfidence(Double.parseDouble(st.nextToken()));
					rule.setLift(Double.parseDouble(st.nextToken()));
					Rules.put(ruleCount, rule);

				}

			}
			bufferedReader.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Always close files.
		/*
		 * for (int i: Rules.keySet()) { Arule a=Rules.get(i);
		 * System.out.println
		 * ("Rule: "+i+" LH: "+a.getRulesStart()+" -> "+a.nextPage);
		 * System.out.println
		 * ("Support "+a.getSupport()+" Confidence: "+a.getConfidence
		 * ()+" Lift: "+a.getLift()); }
		 */

	}

}
