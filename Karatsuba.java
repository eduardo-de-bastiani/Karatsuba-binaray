import java.util.Scanner;

public class Karatsuba{

    public static String multKaratsuba(String nums){
        String val1, val2, a1, a2, b1, b2;
        int mid1, mid2;

        //separando os dois valores binarios
        String[] splString = nums.split("");
        val1 = splString[0];
        val2 = splString[1];
        
        //pegando o meio da string 1
        mid1 = val1.length() / 2;

        //pegando o meio da string 2
        mid2 = val2.length() /2;

        
        String [] spltval1 = {val1.substring(0, mid1), val1.substring(mid1)};
        String [] spltval2 = {val2.substring(0, mid2), val2.substring(mid2)};

        a1 = spltval1[0];
        a2 = spltval1[1];

        b1 = spltval2[0];
        b2 = spltval2[1];


    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String nums;

        System.out.println("====== KARATSUBA ======");
        System.out.println("Digite os valores binários que serão multiplicados: (lembre-se de separá-los por apenas 1 espaço)");
        nums = in.nextLine();
    }
}