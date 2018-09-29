package algorithm;

import struct.Sequence;

import java.util.*;
import java.util.Map.Entry;

/**
 * Created by srx on 2017/3/23.
 */
public class Greedy {
    public static ArrayList<Sequence> greedy( ArrayList<Sequence> rlxResult, float lamuda){
//        Relaxation rlxObject = new Relaxation();
//        //rlxResult的sequence里含有index和string两个字段
        ArrayList<Sequence> grdResult = new ArrayList<>();
        //贪心选择的结果存在grdResult里
        int[][] ldmatrix = SimilarityUtils.buildLdMatrix(rlxResult);
        int[] sum = new int[rlxResult.size()];
        Map<Sequence, Integer> sumMap= new HashMap<>();
        for(int i = 0; i < sum.length;i++){
            //计算编辑距离和
            sum[i] = 0;
            for(int j = 0; j<sum.length;j++){
                sum[i]= sum[i] + ldmatrix[i][j];
            }
            sumMap.put(rlxResult.get(i), new Integer(sum[i]));

        }
        for (Entry<Sequence, Integer> s : sumMap.entrySet()) {
            System.out.println("String:--- " + s.getKey().getSeqString()+ " --- with sum  "+ s.getValue());
        }
        //将map.entrySet()转换成list
        List<Entry<Sequence,Integer>> list = new ArrayList<Entry<Sequence,Integer>>(sumMap.entrySet());
        Collections.sort(list, new Comparator<Entry<Sequence,Integer>>() {
            //降序排序
            @Override
            public int compare(Entry<Sequence,Integer> o1, Entry<Sequence,Integer> o2) {
                //return o1.getValue().compareTo(o2.getValue());
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        for (int i = 0 ; i< 1/(1+lamuda)*list.size();i++){
            //返回贪心选择的条数
            grdResult.add(list.get(i).getKey());
            System.out.println("String:--- " +list.get(i).getKey().getSeqString()+ " --- with sum  "+ list.get(i).getValue());
        }
        return grdResult;

//        for (Map.Entry<Sequence,Integer> mapping : list) {
//            System.out.println(mapping.getKey() + ":" + mapping.getValue());
//        }



//




    }
}
