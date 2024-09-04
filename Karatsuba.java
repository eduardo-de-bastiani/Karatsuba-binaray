import java.util.Scanner;

public class Karatsuba{

    public static String multKaratsuba(String val1, String val2){
        String a1, a2, b1, b2, res;
        int mid, tam;

        //verificacao valores 1 digito
        if(val1.length() == 1 && val2.length() == 1){
            return multBinary(val1, val2);
        }

        
        tam = Math.max(val1.length(), val2.length());
        //pega o valor do meio
        mid = tam / 2;
        val1 = concatZeros(val1, tam);
        val2 = concatZeros(val2, tam);

        
        //divide as strings em 2
        a1 = val1.substring(0, mid);
        a2 = val1.substring(mid);
        b1 = val2.substring(0, mid);
        b2 = val2.substring(mid);



        String a1b1 = multKaratsuba(a1, b1);
        String a2b2 = multKaratsuba(a2, b2);
        String a1a2 = sumBinary(a1, a2);
        String b1b2 = sumBinary(b1, b2); 
        String a1a2b1b2 = multKaratsuba(a1a2, b1b2);


        // (a1 + a2) * (b1 + b2) - a1b1 - a2b2
        String a1b2a2b1 = subBinary(subBinary(a1a2b1b2, a1b1), a2b2);

        // a1b1 << (2 * mid) + a1b2a2b1 << mid + a2b2
        res = sumBinary(sumBinary(shiftBinary(a1b1, 2 * (tam - mid)), shiftBinary(a1b2a2b1, tam - mid)), a2b2);

        return res;
    }

    //concatena 0s na esquerda da str
    public static String concatZeros(String str, int tam) {
        int amountZeros = tam - str.length();
        StringBuilder sb = new StringBuilder(tam);
    
        for (int i = 0; i < amountZeros; i++) {
            sb.append('0');
        }
    
        sb.append(str);
        return sb.toString();

        //ou return "0".repeat(tam - str.length()) + str; // menos eficiente
    }

    
    public static String multBinary(String val1, String val2){
        if(val1.equals("1")  && val2.equals("1")){
            return "1";
        }
        return "0";
    }

    public static String shiftBinary(String str, int n){
        return str + "0".repeat(n);
    }



    // public static String sumBinary(String a, String b){
    //     StringBuilder sb = new StringBuilder();
    //     int carry = 0;
    //     int maxLength = Math.max(a.length(), b.length());

    //     a = concatZeros(a, maxLength);
    //     b = concatZeros(b, maxLength);

    //     //percorre as strings de tras pra frente somando bit a bit
    //     for (int i = maxLength - 1; i >= 0; i--) {
    //         int bit1 = a.charAt(i) - '0';
    //         int bit2 = b.charAt(i) - '0';
    //         int sum = bit1 + bit2 + carry;
    //         sb.append(sum % 2);  //adiciona o bit de soma ao resultado
    //         carry = sum / 2;         //calcula o novo carry
    //     }

    //     //se tiver carry sobrando, adiciona ao resultado
    //     if (carry != 0) {
    //         sb.append(carry);
    //     }

    //      //remove zeros à esquerda
    //      while (sb.length() > 1 && sb.charAt(sb.length() - 1) == '0') {
    //         sb.deleteCharAt(sb.length() - 1);
    //     }

    //     return sb.reverse().toString();
        
    // }


    public static String sumBinary(String a, String b) {
        StringBuilder sb = new StringBuilder();

        int maxLength = Math.max(a.length(), b.length());
        a = concatZeros(a, maxLength);
        b = concatZeros(b, maxLength);
        char carry = '0';

        for (int i = maxLength - 1; i >= 0; i--) {
            char bitA = a.charAt(i);
            char bitB = b.charAt(i);

            if(bitA == '0' && bitB == '0') {
                sb.append( (carry == '0') ? '0' : '1' );
                carry = '0';
            } else if (bitA == '1' && bitB == '1') {
                sb.append( (carry == '0') ? '0' : '1' );
                carry = '1';
            } else {
                sb.append( (carry == '0') ? '1' : '0');
                carry = (carry == '0') ? '0' : '1'; 
            }
        }

        if (carry == '1') sb.append(carry);

        while (sb.length() > 1 && sb.charAt(sb.length() - 1) == '0') {
            sb.deleteCharAt(sb.length() - 1);
        }
        
        return sb.reverse().toString();
    }


    public static String subBinary(String a, String b) {
        StringBuilder comp = new StringBuilder();

        int maxLength = Math.max(a.length(), b.length());
        a = concatZeros(a, maxLength);
        b = concatZeros(b, maxLength);

        //calcula complemento de 1
        for(int i = 0; i < b.length(); i++){
            char c = (b.charAt(i) == '0') ? '1' : '0';
            comp.append(c);
        }

        //calcula complemento de 2
        String complement2 = sumBinary(comp.toString(), "1");

        //complemento de 2
        String res = sumBinary(a, complement2);

        //remove bit extra
        if(res.length() > a.length()){
            res = res.substring(1);
        }

        return res;
    }

    // public static String subBinary(String a, String b){
    //     StringBuilder sb = new StringBuilder();
    //     int borrow = 0;
    //     int maxLength = Math.max(a.length(), b.length());

    //     a = concatZeros(a, maxLength);
    //     b = concatZeros(b, maxLength);

    //     //percorre as strings de tras para frente
    //     for (int i = maxLength - 1; i >= 0; i--) {
    //         int bit1 = a.charAt(i) - '0';
    //         int bit2 = b.charAt(i) - '0' + borrow;
    //         if (bit1 < bit2) {
    //             bit1 += 2;
    //             borrow = 1;
    //         } else {
    //             borrow = 0;
    //         }
    //         sb.append(bit1 - bit2);
    //     }

    //      //remove zeros à esquerda
    //      while (sb.length() > 1 && sb.charAt(sb.length() - 1) == '0') {
    //         sb.deleteCharAt(sb.length() - 1);
    //     }

    //     return sb.reverse().toString();
    // }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String nums, val1, val2, res;

        System.out.println("====== KARATSUBA ======");
        System.out.println("Digite os valores binários que serão multiplicados (lembre-se de separá-los por apenas 1 espaço): ");
        nums = in.nextLine();

        //separando os dois valores binarios
        String[] splString = nums.split(" ");
        val1 = splString[0];
        val2 = splString[1];

        res = multKaratsuba(val1, val2);
        System.out.println("====== RESULTADO ======\n" + res);
    }
}