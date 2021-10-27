

public class Single {

    private static Single single;

    private Single() {}

    public static Single getSingle(){
        if(single == null){
            single =  new Single();

            return single;
        }else {
            return single;
        }
    }
}
