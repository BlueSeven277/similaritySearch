package struct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Inverted_Table extends HashMap{
	private String gram;
	static String str1 = "seququesgnce";
	static String str2 = "sequem";
	static int a = 0;
	static int b = 1;
	static ArrayList<Sequence> dset = new ArrayList<Sequence>();
	
	public static HashMap<String, Set<Integer>> createQueryTable (HashMap<String, Set<Integer>> oldTable, String query){
		HashMap<String, Set<Integer>> qTable = new HashMap<String, Set<Integer>>();
		int i = 0;
		//System.out.println("bbb");
		while (i != query.length()-1){
			//�����л��������У���Ƭ��gram����Ϊ2
			String sub = query.substring(i, i+2);
			if (oldTable.containsKey(sub)){
				//����ɱ����д�gram����ȡ��gram�����м���
				qTable.put(sub, oldTable.get(sub));
			}
			i++;
		}
	
		return qTable;
		
	}
	
	
	public static HashMap<String, Set<Integer>> createTable (ArrayList<Sequence> dset){
		//�������ű�
//		dset.add(new Sequence(a, str1));
//		dset.add(new Sequence(b, str2));
		HashMap<String, Set<Integer>> idTable = new HashMap<String, Set<Integer>>();
		//idTable�ǵ��ű�
		for (Sequence seq : dset) {
			String string = seq.getSeqString();
			int i = 0;
			//System.out.println("bbb");
			while (i != string.length()-1){
				//�����л��������У���Ƭ��gram����Ϊ2
				String sub = string.substring(i, i+2);
				if (idTable.containsKey(sub)){
					//��������д�gram������
					idTable.get(sub).add(seq.getIndex());
				}
				else {
					//û�еĻ�,�����gram��Ϊ�µ�key
					Set<Integer> newSet = new HashSet<Integer>();
					
					newSet.add(new Integer(seq.getIndex()));
					idTable.put(sub, newSet);
				}
				i++;
			}
		}
		return idTable;
	}
	
//	Map<String gram, arraylistString>;
//	public static void main(String[] args) {
//
//		HashMap<String, Set<Integer>> table = Inverted_Table.createTable();
//		long bs = Calendar.getInstance().getTimeInMillis();
//		java.util.Iterator it = table.entrySet().iterator();
//		//�������ű�
//		while (it.hasNext()) {
//		java.util.Map.Entry entry = (java.util.Map.Entry) it.next();
//		// entry.getKey() ����������Ӧ�ļ�
//		// entry.getValue() ����������Ӧ��ֵ
//		System.out.print(entry.getKey());
//		System.out.println(entry.getValue());
//		
//		}
//		System.out.print("Runtime:");
//		System.out.println(+Calendar.getInstance().getTimeInMillis() - bs);
//
//	}

}
