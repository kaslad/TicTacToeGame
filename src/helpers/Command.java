package helpers;

public class Command {
    public static final String INFO = "INFO";
    public static final String START = "START";
    public static final String CORRECT_MOVE = "CORRECT_MOVE";
    public static final String OPPONENT_MOVE = "OPPONENT_MOVE";
    public static final String WIN = "WIN";
    public static final String LOSS = "LOSS";
    public static final String DRAW = "DRAW";
    public static final String FINISH = "END";
    public static final String MOVE = "MOVE";
    public static final String DIVIDER = "#";

    public static String[] getCommandAndVal(String s){
        //System.out.println(s);
        String [] arr = s.split(DIVIDER);
        String [] arr2 = new  String[2];
        if(arr.length == 1){
            arr2[0] = arr[0];
            arr2[1] = "";
            return  arr2;
        }
        return arr;
    }

    public static void main(String[] args) {
        String [] arr = getCommandAndVal(DRAW + DIVIDER);
        System.out.println(arr[0]);
        System.out.println(arr[1]);
    }

}
