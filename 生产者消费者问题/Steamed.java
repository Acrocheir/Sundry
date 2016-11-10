package WaO;

public class Steamed {
	private static Steamed insteamed;
	int id;  //馒头编号
	/**
	 * 保证多个线程只有一个实例（单例模式）
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
		return "馒头个数："+id;
	}*/
}
