package algorithm;

import struct.Inverted_Table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

/**
 * @author srx
 *�ɳ��㷨
 */
public class Relaxation {
	private ArrayList<struct.Sequence> rlxResult;

	public ArrayList<struct.Sequence> setRlxResult(ArrayList<struct.Sequence> dataSet, HashMap<String, Set<Integer>> dsTable, String q,
												   int k1, int k2, int threshold, float lamuda) {
		rlxResult = new ArrayList<>();
		Set<Integer> set = Relax(dataSet, dsTable, q, k1, k2, threshold, lamuda);
		for (Integer integer : set) {
			rlxResult.add(dataSet.get(integer));

		}
		return rlxResult;
	}

	public ArrayList<struct.Sequence> getRlxResult() {
		return rlxResult;
	}

	public static Set<Integer> Relax(ArrayList<struct.Sequence> dataSet, HashMap<String, Set<Integer>> dsTable, String q,
									 int k1, int k2, int threshold, float lamuda) {
	    /*�������룺
	     * dataSet---�ַ������ݼ�
	     * usrQuery---�û���ѯ���ַ���
	     * k1,k2---��������������±߽�
	     * threshold---�༭������ֵ
	     * lamuda---similarity��diversity������ϵ����0,1��
	     * output:
	     * rlxResult �����������Sequence��index
		*/

	    //用ArrayList存的话会有重复元素，所以用set存了
		if(q.length()+2-1-threshold*2 <0){
			//��С��Ƭ��Ϊ����
			System.out.println("min_com <0");
			return null;
		}
//		struct.Sequence usrQ = new struct.Sequence(0, q);
//		ArrayList<struct.Sequence> qList = new ArrayList<struct.Sequence>();
//		qList.add(usrQ);
		HashMap<String, Set<Integer>> qtable = Inverted_Table.createQueryTable(dsTable, q);
		//����Query q�ĵ��ű�
		Set<Integer> rlxResult = new HashSet<Integer>();
		//��ʼ���ɳڽ������rlxResultֻ��Sequence��index
		//�Դ�����ʡʱ��
		
		//ArrayList<struct.Sequence> haveSameCom = new ArrayList<struct.Sequence>();
		HashMap<Integer, Integer> iCount = new HashMap<Integer, Integer>();		
		//���������q������ͬ��ͬgram��sequence�ģ�index��ţ���ͬgram������
	
		java.util.Iterator it = qtable.entrySet().iterator();
		//����q�ĵ��ű��ҵ�������ͬ��Ƭ��sequence����iCount
		while (it.hasNext()) {

			Entry entry = (Entry) it.next();
			for (Integer integer : (Set<Integer>) entry.getValue()) {
				if (iCount.containsKey(integer)) {
					// ���iCount���Ѿ��д����У�����q����ͬ��Ƭ��+1
					Integer i = iCount.get(integer);
					iCount.put(integer, i + 1);
					if (i.intValue()== (int)q.length()+2-1-threshold*2 ) {
						//������д��ڵ���min_com����Ƭ������rlxResult
						rlxResult.add(integer);
						iCount.remove(integer); //�Ѿ�����rlxResult�Ĵ����ɳ�ʱ���ٿ�����
						System.out.println("q���"+integer+"�����еľ���Ϊ��"+SimilarityUtils.ld(q, dataSet.get(integer).getSeqString()));
						if (rlxResult.size() == (int)((lamuda+1) * k2)) {
							System.out.println("rlxResult��������Ϊ��" + rlxResult.size());
							return rlxResult;
						}
					}


				} else {
					// ���û�У���iCount�м�������У���ǰ������ͬ��Ƭ��Ϊ1
					iCount.put(integer, new Integer(1));
				}
			}

		}
		int min_com = 0;
		while (rlxResult.size() < (int) (lamuda + 1) * k1) {
			// �������������㣬���ɳ�
			threshold++;// ����༭����
			min_com = (int) q.length() + 2 - 1 - threshold * 2;
			if(min_com <0){
				//��С��Ƭ��Ϊ����
				System.out.println("min_com <0 when relaxing");
				return null;
			}
			java.util.Iterator it1 = iCount.entrySet().iterator();
			// ��������ͬgram����δ����rlxResult������
			while (it1.hasNext()) {
				Entry entry = (Entry) it1.next();
				
				Integer same_com = (Integer) entry.getValue();// �˴����е���ͬ��Ƭ��
				int same = same_com.intValue();
				if (same >= min_com) {
					rlxResult.add((Integer) entry.getKey());
					iCount.remove((Integer) entry.getKey()); // �Ѿ�����rlxResult�Ĵ����ɳ�ʱ���ٿ�����
					Integer j = (Integer)entry.getKey();
					int m = j.intValue();
					System.out.println("q���"+entry.getKey()+"�����еľ���Ϊ��"+SimilarityUtils.ld(q, dataSet.get(m).getSeqString()));
					if (rlxResult.size() == (int) ((lamuda + 1) * k2)) {
						System.out.println("rlxResult��������Ϊ��" + rlxResult.size());
						return rlxResult;
				}
				
				}
			}
			
		}
		System.out.println("rlxResult��������Ϊ��" + rlxResult.size());
		return rlxResult;
	}


}
