package pack1;

public class Main {
	
	public static class MyDelegateImp01 implements MyDelegate {

		@Override
		public String myFunc(String s) {			
			return s+" and There";
		}		
	}
		
	
	static void MyExecute(MyDelegate aFunction) {
		   System.out.println(aFunction.myFunc("Here"));
		}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		MyExecute (new MyDelegateImp01());
		
		MyExecute(new MyDelegate(){
			public String myFunc (String s){
				return s+" and Now";
				}
			});
		
		MyExecute((s)->s+" and Now");
		MyExecute ((s) -> new StringBuilder(s).reverse().toString());

	}

}
