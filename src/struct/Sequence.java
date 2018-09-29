/**
 * 
 */
package struct;

/**
 * @author srx
 *
 */
public class Sequence {
	private int index;  //���
	private String seqString;  //�ַ�����
	public Sequence(int index, String seqString) {
		super();
		this.index = index;
		this.seqString = seqString;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String getSeqString() {
		return seqString;
	}
	public void setSeqString(String seqString) {
		this.seqString = seqString;
	}

}
