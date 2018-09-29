package exp;

import algorithm.Greedy;
import algorithm.Relaxation;
import algorithm.StarAlgorithm;
import struct.Inverted_Table;
import struct.Sequence;


import java.io.*;
import java.util.*;

public class Search {

	/**
	 * @param
	 */
	private ArrayList<Sequence> fnlResult = new ArrayList<>();

	public void setFnlResult(ArrayList<Sequence> fnlResult) {
		this.fnlResult = fnlResult;
	}

	public ArrayList<Sequence> getFnlResult() {
		return fnlResult;
	}

	public Search() throws IOException  {
		super();
		// TODO Auto-generated constructor stub
	}

	public static ArrayList<Sequence> getDataset(File file) {

		//���ݶ���
		//����һ���������ܵ�
		BufferedReader read = null;
		InputStreamReader reader = null;
		//StringBuffer�� ��һ���ܳ��õ��ķ�������Stringһ����ֻ�� //�������ƴ���ַ����ٶȱȽϿ죬Ч�ʸ�
		StringBuffer strbuf = new StringBuffer("");
		String str = "";
		ArrayList<Sequence> dset = new ArrayList<Sequence>();
		Sequence sequence = new Sequence(0, "aaa");

		try {

			int i = 0;
			read = new BufferedReader( new InputStreamReader( new FileInputStream(file)));
			//reader = new InputStreamReader( new FileInputStream(file));
			while ((str =read.readLine()) != null) {
				Scanner sca=new Scanner(str.trim());
				dset.add(new Sequence(i, str));
				//System.out.println(dset.get(i).getSeqString());
				i++;
			}
			return dset;
		}

		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); }
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	public static ArrayList<Sequence> searchInfo(String dataset, int k1, int k2, int threshold, String qInput, float tradeOff, String algorithm) throws IOException {
		//获取数据集，建立到排表
//		File file = new File("Conference.txt");
//		System.out.print(file);
//		ArrayList<Sequence> datasetArray = Search.getDataset(file);
		File file = new File("/Users/srx/Desktop/srxfiles/HIT/SDUI/"+dataset+".txt");
		System.out.print(file);
		ArrayList<Sequence> datasetArray = Search.getDataset(file);
		HashMap<String, Set<Integer>> table = Inverted_Table.createTable(datasetArray);
		Search searchObj = new Search();
		switch(algorithm){
			default:
				System.out.println("未选择算法");
				break;
			case "genGreedy":
				System.out.println("a");

				ArrayList<Sequence> rlxResult = new ArrayList<>();
				Relaxation rlxObject = new Relaxation();
				rlxResult = rlxObject.setRlxResult(datasetArray, table, qInput, k1, k2, threshold, tradeOff);
				//	SimilarityUtils.buildLdMatrix(rlxResult);
				return Greedy.greedy(rlxResult, tradeOff);

			case "genCluster":
				System.out.println("b");
				ArrayList<Sequence> crlxResult = new ArrayList<>();
				Relaxation crlxObject = new Relaxation();
				rlxResult = crlxObject.setRlxResult(datasetArray, table, qInput, k1, k2, threshold, tradeOff);
				//	SimilarityUtils.buildLdMatrix(rlxResult);
				StarAlgorithm starAlgorithm=new StarAlgorithm(rlxResult, tradeOff);
				return starAlgorithm.getMsaResult();
				//return Greedy.greedy(rlxResult, tradeOff);

			case "CB2S":
				System.out.println("a");
				break;
			case "swap":
				System.out.println("a");
				break;
			case "comGreedy":
				System.out.println("a");
				break;

		}


		return datasetArray;
	}
		
	
	
	public static void main(String[] args) {
		
		//�ļ���ȡ(ok)
		File file = new File("data1.txt"); 
		ArrayList<Sequence> datasetArray = Search.getDataset(file);

		
		
		//����file�ĵ��ű�table
		long bs = Calendar.getInstance().getTimeInMillis();
		HashMap<String, Set<Integer>> table = Inverted_Table.createTable(datasetArray);
		ArrayList<Sequence> rlxResult = new ArrayList<>();
		Relaxation rlxObject = new Relaxation();
//		//�������ű�(ok)
//		java.util.Iterator it = table.entrySet().iterator();
//		while (it.hasNext()) {
//		java.util.Map.Entry entry = (java.util.Map.Entry) it.next();
//		// entry.getKey() ����������Ӧ�ļ�
//		// entry.getValue() ����������Ӧ��ֵ
//		System.out.print(entry.getKey());
//		System.out.println(entry.getValue());	
//		}
//		System.out.print("Runtime:");
//		System.out.println(+Calendar.getInstance().getTimeInMillis() - bs);
		System.out.println("Please input a query string:");
		Scanner sc = new Scanner(System.in);
		String q = sc.nextLine();
		System.out.println("length of input:"+q.length());
		System.out.println("Please input a distance threshold:");
		int threshold = sc.nextInt();
		System.out.println("Input kmin (int):");
		int k1 = sc.nextInt();
		System.out.println("Input kmax (int):");
		int k2 = sc.nextInt();
		System.out.println("Input trade-off parameter of SIMILARITY and DIVERSITY");
		float lamuda = sc.nextFloat();
		rlxResult = rlxObject.setRlxResult(datasetArray, table, q, 10, 15, threshold, lamuda);
	//	SimilarityUtils.buildLdMatrix(rlxResult);
	//	Greedy.greedy(rlxResult, lamuda);
		StarAlgorithm starAlgorithm=new StarAlgorithm(rlxResult, lamuda);

	}


	

}
