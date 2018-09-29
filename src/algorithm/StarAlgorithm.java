package algorithm;

import struct.Node;
import struct.Point;
import struct.Sequence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * @author srx
 *
 */
public class StarAlgorithm implements Combiner{

    ArrayList<String> sequences=new ArrayList<>();
    //打分
    int match = 3;
    int mismatch = -1;
    int gap = 1;
    //两个串对比时的串长
    int inputLength1;
    int inputLength2;

    ArrayList<String> sequencesForStar=new ArrayList<>();
    ArrayList<String> alignedSequencesWithStar=new ArrayList<>();
    int[] distance;
    Map<Integer, Integer> disMap= new HashMap<>();
    ArrayList<Sequence> msaResult = new ArrayList<>();
    int x=0;
    int[] sum; //序列的得分
    String center;//当前的中心
    //扩张后的序列
    String[] AlignedSequences;

    private Node[][] solveMatrix;

    private Node[][] solveMatrixForStar;

    PrintWriter writer = null;
    String LastCenter;//最终的中心motif
    int node;

    public ArrayList<Sequence> getMsaResult() {
        return msaResult;
    }

    public void setMsaResult(ArrayList<Sequence> msaResult) {
        this.msaResult = msaResult;
    }

    public StarAlgorithm() {

        try {
            writer = new PrintWriter("out.txt", "UTF-8");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        readFile("dna.txt");// read input file for sequences

        AlignedSequences=new String[sequences.size()];
        sum=new int[sequences.size()];
        for (int a=0;a<sequences.size();a++)
            sum[a]=0;
        /**
         * for Multiple Processing
         */
        for(int i=0;i<sequences.size();i++){
            for (int j=1;j<sequences.size()-1;j++){
                if(i!=j){

                    inputLength1=sequences.get(i).length()+1;
                    inputLength2=sequences.get(j).length()+1;

                    solveMatrix=new Node[inputLength2][inputLength1];
                    for (int a = 0; a < inputLength2; a++) {
                        for (int b = 0; b < inputLength1; b++) {
                            solveMatrix[a][b] = new Node();
                        }
                    }

                    sum[i]+=needlemanWunschMaxScore(inputLength1, inputLength2,sequences.get(i),sequences.get(j)); // Needleman - Wunsch algorithm
                    traceback(i,j,inputLength2-1,inputLength1-1,sequences.get(i),sequences.get(j));
                }
            }
        }



        int max=sum[0];
        node=0;

  for (int i=0;i<sequences.size()-1;i++){
      if (max<sum[i+1]){
          max=sum[i+1];
          node=i;
      }
  }
        System.out.println("max score & node "+ +max +" "+ node);

         center=AlignedSequences[node];

       for (int i=0;i<sequences.size();i++){

           if(i!=node){
               sequencesForStar.add(AlignedSequences[i]);

           }

       }
        System.out.println("CENTER SEQUENCE:"+center);

        for (int i=0;i<sequencesForStar.size();i++){
            solveMatrixForStar=new Node[sequencesForStar.get(i).length()][center.length()];
            for (int a = 0; a < sequencesForStar.get(i).length(); a++) {
                for (int b = 0; b < center.length(); b++) {
                    solveMatrixForStar[a][b] = new Node();
                }
            }

            mergeMethod(center,sequencesForStar.get(i));
            System.out.println(alignedSequencesWithStar.get(i));

        }
        for (int i = 0; i <sequencesForStar.size();i++){
            System.out.println(sequencesForStar.get(i));


        }


        System.out.println(LastCenter);

    }


    //重构方法，针对rlxResult

    public  StarAlgorithm(ArrayList<Sequence> rlxResult, float lamuda) {

        try {
            writer = new PrintWriter("out.txt", "UTF-8");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
       // readFile("dna.txt");// read input file for sequences
        for (Sequence sq:rlxResult
             ) {
            sequences.add(sq.getSeqString());
        }

        AlignedSequences=new String[sequences.size()];
        sum=new int[sequences.size()];
        for (int a=0;a<sequences.size();a++)
            sum[a]=0;
        distance=new int[sequences.size()];
        for (int a=0;a<sequences.size();a++)
            distance[a]=0;

        /**
         * for Multiple Processing
         */
        for(int i=0;i<sequences.size();i++){
            for (int j=1;j<sequences.size()-1;j++){
                if(i!=j){

                    inputLength1=sequences.get(i).length()+1;
                    inputLength2=sequences.get(j).length()+1;

                    //建立二维矩阵，两两比对
                    solveMatrix=new Node[inputLength2][inputLength1];
                    for (int a = 0; a < inputLength2; a++) {
                        for (int b = 0; b < inputLength1; b++) {
                            solveMatrix[a][b] = new Node();
                        }
                    }

                    sum[i]+=needlemanWunschMaxScore(inputLength1, inputLength2,sequences.get(i),sequences.get(j)); // Needleman - Wunsch algorithm
                    traceback(i,j,inputLength2-1,inputLength1-1,sequences.get(i),sequences.get(j));
                }
            }
        }



        int max=sum[0];
        node=0;//得分最高的序列的序号

        for (int i=0;i<sequences.size()-1;i++){
            if (max<sum[i+1]){
                max=sum[i+1];
                node=i;
            }
        }
        System.out.println("max score & node "+ +max +" "+ node);

        center=AlignedSequences[node];

        for (int i=0;i<sequences.size();i++){

            if(i!=node){
                sequencesForStar.add(AlignedSequences[i]);

            }

        }
        System.out.println("CENTER SEQUENCE:"+center);

        for (int i=0;i<sequencesForStar.size();i++){
            solveMatrixForStar=new Node[sequencesForStar.get(i).length()][center.length()];
            for (int a = 0; a < sequencesForStar.get(i).length(); a++) {
                for (int b = 0; b < center.length(); b++) {
                    solveMatrixForStar[a][b] = new Node();
                }
            }

            mergeMethod(center,sequencesForStar.get(i));
            System.out.println(alignedSequencesWithStar.get(i));

        }
        for (int i = 0; i <sequencesForStar.size();i++){
            System.out.println(sequencesForStar.get(i));
            distance[i] = SimilarityUtils.ld(LastCenter, sequencesForStar.get(i));
            disMap.put(new Integer(i), new Integer(distance[i]));

        }
        //将map.entrySet()转换成list
        List<Map.Entry<Integer,Integer>> list = new ArrayList<Map.Entry<Integer,Integer>>(disMap.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Integer,Integer>>() {
            //降序排序
            @Override
            public int compare(Map.Entry<Integer,Integer> o1, Map.Entry<Integer,Integer> o2) {
                //return o1.getValue().compareTo(o2.getValue());
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        for (int i = 0 ; i< 1/(1+lamuda)*list.size();i++){
            //返回贪心选择的条数
            Sequence s = new Sequence((int)list.get(i).getKey(),sequences.get(list.get(i).getKey()));
            msaResult.add(s);
            System.out.println("String:--- " +s.getSeqString()+ " --- with dis  "+ list.get(i).getValue());
        }
        System.out.println(LastCenter);




    }
    public int needlemanWunschMaxScore(final int sequenceLength1,final int sequenceLength2, String inputString1, String inputString2){ //Neddleman-Wunsch Algorithm


        // initialize matrix
        for (int i = 0; i < sequenceLength2; i++) {
            solveMatrix[i][0].setValue(gap * i);
            solveMatrix[i][0].addDirection('n');
        }
        for (int i = 0; i < sequenceLength1; i++) {
            solveMatrix[0][i].setValue(gap * i);
            solveMatrix[0][i].addDirection('w');
        }

        boolean matchControl = false;

        for (int i = 1; i < sequenceLength2; i++) {
            for (int j = 1; j < sequenceLength1; j++) {


                    int north = solveMatrix[i - 1][j].getValue() + gap;
                    int west = solveMatrix[i][j - 1].getValue() + gap;
                    int northWest = solveMatrix[i - 1][j - 1].getValue();

                    if (inputString1.charAt(j - 1) == inputString2.charAt(i - 1)) {

                        Node controlNode = solveMatrix[i - 1][j - 1];
                        matchControl = true;

                        if (controlNode.isMatchWay()) {
                            northWest += match;
                        } else {
                            northWest += match;
                        }

                    } else {
                        northWest += mismatch;
                        matchControl = false;
                    }

                    final int max = Math.max(Math.max(northWest, west), north);

                    solveMatrix[i][j].setValue(max);

                    if (northWest == max) {
                        solveMatrix[i][j].addDirection('c');
                        if (matchControl)
                            solveMatrix[i][j].setMatchWay(true); // for extension match score
                    }
                    if (west == max) {
                        solveMatrix[i][j].addDirection('w');
                    }
                    if (north == max) {
                        solveMatrix[i][j].addDirection('n');
                    }

            }
        }

        return solveMatrix[sequenceLength2 - 1][sequenceLength1 - 1].getValue();

    }

    private void traceback(int a,int b,int i, int j,String inputString1, String inputString2) {


        final Stack<Point> stack = new Stack<Point>();
        stack.push(new Point(i, j, "", ""));

        do {

            final StringBuilder lastString1 = new StringBuilder(), lastString2 = new StringBuilder();

            final Point p = stack.pop();
            i = p.getX();
            j = p.getY();

            lastString1.append(p.getStoreString1());
            lastString2.append(p.getStoreString2());

            do {

                final Node n = solveMatrix[i][j];
                char forSwitchChar;

                if (n.sizeDirection() > 1) {
                    stack.push(new Point(i, j, lastString1.toString(),
                            lastString2.toString()));
                    forSwitchChar = n.pollDirection();
                } else {
                    forSwitchChar = n.peekDirection();
                }

                switch (forSwitchChar) {

                    case 'c':
                        // go northwest
                        lastString1.append(inputString1.charAt(--j));
                        lastString2.append(inputString2.charAt(--i));
                        break;
                    case 'w':
                        // go west
                        lastString1.append(inputString1.charAt(--j));
                        lastString2.append('-');
                        break;
                    case 'n':
                        // go north
                        lastString1.append('-');
                        lastString2.append(inputString2.charAt(--i));
                        break;
                }

            } while (i > 0 || j > 0);

              AlignedSequences[a]=lastString1.reverse().toString();
              AlignedSequences[b]=lastString2.reverse().toString();
//              System.out.print(a+" "+AlignedSequences[a]+"   ");
//              System.out.println(b+" "+AlignedSequences[b]);
         writeFile(AlignedSequences[a],AlignedSequences[b]);





        } while (stack.size() > 0);

        writer.close();
        writer.close();

    }

    private void writeFile(int point) {

        writer.println(point);
        writer.println("");
    }

    private void writeFile(String str1, String str2) {

        writer.println(str1);
        writer.println(str2);
        writer.println("");
    }


    private void readFile(final String location) {

        Scanner input = null;

        try {
            input = new Scanner(new File(location));

            String[] arrayValues=input.nextLine().split(" ");
            match=Integer.parseInt(arrayValues[0].trim());
            mismatch=Integer.parseInt(arrayValues[1].trim());
            gap=Integer.parseInt(arrayValues[2].trim());

            while (input.hasNext())
            sequences.add(input.nextLine());

            System.out.println(match+" "+mismatch+" "+gap);

            System.out.println(sequences.size());
        } catch (final FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            input.close();
        }

    }


    @Override
    //建立系统树
    public void mergeMethod(String sequenceCenter, String  notAlignedWithCenter) {

        int max=StarMaxScore(center.length(),notAlignedWithCenter.length(), center,notAlignedWithCenter);
        tracebackStar( notAlignedWithCenter.length()-1,center.length()-1,center,notAlignedWithCenter);
    }


    public int StarMaxScore(final int sequenceLength1,final int sequenceLength2, String inputString1, String inputString2){ //Neddleman-Wunsch Algorithm


        // initialize matrix
        for (int i = 0; i < sequenceLength2; i++) {
            solveMatrixForStar[i][0].setValue(gap * i);
            solveMatrixForStar[i][0].addDirection('n');
        }
        for (int i = 0; i < sequenceLength1; i++) {
            solveMatrixForStar[0][i].setValue(gap * i);
            solveMatrixForStar[0][i].addDirection('w');
        }

        boolean matchControl = false;

        for (int i = 1; i < sequenceLength2; i++) {
            for (int j = 1; j < sequenceLength1; j++) {


                int north = solveMatrixForStar[i - 1][j].getValue() + gap;
                int west = solveMatrixForStar[i][j - 1].getValue() + gap;
                int northWest = solveMatrixForStar[i - 1][j - 1].getValue();

                if (inputString1.charAt(j - 1) == inputString2.charAt(i - 1)) {

                    Node controlNode = solveMatrixForStar[i - 1][j - 1];
                    matchControl = true;

                    if (controlNode.isMatchWay()) {
                        northWest += match;
                    } else {
                        northWest += match;
                    }

                } else {
                    northWest += mismatch;
                    matchControl = false;
                }

                final int max = Math.max(Math.max(northWest, west), north);//每个方向的长度
                System.out.print("max: " +max);

                solveMatrixForStar[i][j].setValue(max);

                if (northWest == max) {
                    solveMatrixForStar[i][j].addDirection('c');
                    if (matchControl)
                        solveMatrixForStar[i][j].setMatchWay(true); // for extension match score
                }
                if (west == max) {
                    solveMatrixForStar[i][j].addDirection('w');
                }
                if (north == max) {
                    solveMatrixForStar[i][j].addDirection('n');
                }

            }
        }

        return solveMatrixForStar[sequenceLength2 - 1][sequenceLength1 - 1].getValue();

    }

    private void tracebackStar(int i, int j,String inputString1, String inputString2) {

        //从末端往回生成两个序列的扩张
        final Stack<Point> stack = new Stack<Point>();
        stack.push(new Point(i, j, "", ""));

        do {

            final StringBuilder lastString1 = new StringBuilder(), lastString2 = new StringBuilder();

            final Point p = stack.pop();
            i = p.getX();
            j = p.getY();

            lastString1.append(p.getStoreString1());
            lastString2.append(p.getStoreString2());

            do {

                final Node n = solveMatrixForStar[i][j];
                char forSwitchChar;

                if (n.sizeDirection() > 1) {
                    stack.push(new Point(i, j, lastString1.toString(),
                            lastString2.toString()));
                    forSwitchChar = n.pollDirection();
                } else {
                    forSwitchChar = n.peekDirection();
                }

                switch (forSwitchChar) {

                    case 'c':
                        // go northwest
                        lastString1.append(inputString1.charAt(--j));
                        lastString2.append(inputString2.charAt(--i));
                        break;
                    case 'w':
                        // go west
                        lastString1.append(inputString1.charAt(--j));
                        lastString2.append('-');
                        break;
                    case 'n':
                        // go north
                        lastString1.append('-');
                        lastString2.append(inputString2.charAt(--i));
                        break;
                }

            } while (i > 0 || j > 0);


           LastCenter=lastString1.reverse().toString();
            alignedSequencesWithStar.add(lastString2.reverse().toString());
            System.out.println(LastCenter);



        } while (stack.size() > 0);

        writer.close();

    }

}
