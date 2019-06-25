package testThread;

public class Test02Thread {

	public static void main(String[] args) {
		// Same as previous Test01Thread, but using Runnable Interface
		new Thread(new Runnable(){
			public void run(){
				for(int i=0;i<10000;++i){}
				System.out.println("Hello 1");				
			}
		}).start();;
		System.out.println("Hello 2");
	}
}
