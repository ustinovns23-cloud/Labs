public class While {
    public static void main(String[] args) {
        boolean t = 5>2;
        System.out.println("5>2 - " + t);
        int value = 0;
        boolean b = value > 5;
        System.out.println("0>5 - " + b);
        int value1 = 0;
        while(value1<5){
            System.out.println("hello");
            System.out.println(value1);
            value1++;
        }
    }
}
