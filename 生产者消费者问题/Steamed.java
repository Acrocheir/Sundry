package WaO;

public class Steamed {
	private static Steamed insteamed;
	int id;  //��ͷ���
	/**
	 * ��֤����߳�ֻ��һ��ʵ��������ģʽ��
	 */
	public static Steamed getSteamed(int id){
		if(insteamed == null){
			synchronized (Steamed.class) {
				if(insteamed == null){
					insteamed = new Steamed(id);
				}
			}
		}
		return insteamed;
	}
	
	Steamed(int id){
		this.id = id;
	}
	
	/*public String toString(){
		return "��ͷ������"+id;
	}*/
}
