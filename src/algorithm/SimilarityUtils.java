package algorithm;

import java.util.ArrayList;

/**
 * @author srx
 *  �Ƚ������ַ������ƶ�
 */

 public class SimilarityUtils {  
     private SimilarityUtils(){}  
       
     public static int ld(String s, String t) {  
    	 //����༭����
         int d[][];  
         int sLen = s.length();  
         int tLen = t.length();  
         int si;   
         int ti;   
         char ch1;  
         char ch2;  
         int cost;  
         if(sLen == 0) {  
             return tLen;  
         }  
         if(tLen == 0) {  
             return sLen;  
         }  
         d = new int[sLen+1][tLen+1];  
         for(si=0; si<=sLen; si++) {  
             d[si][0] = si;  
         }  
         for(ti=0; ti<=tLen; ti++) {  
             d[0][ti] = ti;  
         }  
         for(si=1; si<=sLen; si++) {  
             ch1 = s.charAt(si-1);  
             for(ti=1; ti<=tLen; ti++) {  
                 ch2 = t.charAt(ti-1);  
                 if(ch1 == ch2) {  
                     cost = 0;  
                 } else {  
                     cost = 1;  
                 }  
                 d[si][ti] = Math.min(Math.min(d[si-1][ti]+1, d[si][ti-1]+1),d[si-1][ti-1]+cost);  
             }  
         }  
         return d[sLen][tLen];  
     }  
       
     public static double similarity(String src, String tar) {  
    	 //�������ƶ�
         int ld = ld(src, tar);  
         return 1 - (double) ld / Math.max(src.length(), tar.length());   
     }  
    public static int[][] buildLdMatrix(ArrayList<struct.Sequence> rlxResult){
         //建立编辑距离矩阵，已检验通过
         int[][] matrix = new int[rlxResult.size()][rlxResult.size()];
        for (int i = 0; i<rlxResult.size();i++){
                for(int j = i; j<rlxResult.size();j++){
                if (i == j){
                    matrix[i][j] = 0;
                }
                matrix[i][j] = SimilarityUtils.ld(rlxResult.get(i).getSeqString(),rlxResult.get(j).getSeqString());
            }
        }
        for ( int i = 1; i<rlxResult.size();i++){
            for(int j = 0; j<i;j++){
                matrix[i][j] = matrix [j][i];
            }
        }
        for ( int i = 0; i<rlxResult.size();i++){
            for(int j = 0; j<rlxResult.size();j++){
                System.out.print( matrix[i][j]+ " ");
            }
            System.out.print("\n");
        }
         return matrix;
    }

       
     public static void main(String[] args) {  
         String src = "hello world!";  
         String tar = "hello";  
         System.out.println("dis="+ SimilarityUtils.ld(src, tar));
         System.out.println("sim="+ SimilarityUtils.similarity(src, tar));
     }  
 }  