import java.util.Scanner;

public class Karatsuba{

    public static String multKaratsuba(String val1, String val2){
        String a1, a2, b1, b2, res;
        int mid, tam;

        if(val1.length() == 1 && val2.length() == 1){
            return multBinary(val1, val2);
        }

        
        tam = Math.max(val1.length(), val2.length());
        val1 = concatZeros(val1, tam);
        val2 = concatZeros(val2, tam);

        //pega o valor do meio
        mid = tam / 2;
        
        //divide as strings em 2
        a1 = val1.substring(0, mid);
        a2 = val1.substring(mid);
        b1 = val2.substring(0, mid);
        b2 = val2.substring(mid);



        String a1b1 = multKaratsuba(a1, b1);
        String a2b2 = multKaratsuba(a2, b2);
        String a1a2b1b2 = multKaratsuba(addBinary(a1, a2), addBinary(b1, b2));


        // (a1 + a2) * (b1 + b2) - a1b1 - a2b2
        String a1b2a2b1 = subBinary(subBinary(a1a2b1b2, a1b1), a2b2);

        // a1b1 << (2 * mid) + a1b2a2b1 << mid + a2b2
        res = addBinary(addBinary(shiftBinary(a1b1, 2 * (tam - mid)), shiftBinary(a1b2a2b1, tam - mid)), a2b2);

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



    public static String addBinary(String a, String b){
        StringBuilder sb = new StringBuilder();
        int carry = 0;
        int maxLength = Math.max(a.length(), b.length());

        // Adiciona zeros à esquerda para igualar o tamanho
        a = concatZeros(a, maxLength);
        b = concatZeros(b, maxLength);

        // Percorre as strings de trás para frente, somando bit a bit
        for (int i = maxLength - 1; i >= 0; i--) {
            int bit1 = a.charAt(i) - '0';
            int bit2 = b.charAt(i) - '0';
            int sum = bit1 + bit2 + carry;
            sb.append(sum % 2);  // Adiciona o bit de soma ao resultado
            carry = sum / 2;         // Calcula o novo carry
        }

        // Se houver carry sobrando, adiciona ao resultado
        if (carry != 0) {
            sb.append(carry);
        }

        return sb.reverse().toString();
        
    }

    public static String subBinary(String a, String b){
        StringBuilder sb = new StringBuilder();
        int borrow = 0;
        int maxLength = Math.max(a.length(), b.length());

        // Adiciona zeros à esquerda para igualar o tamanho
        a = concatZeros(a, maxLength);
        b = concatZeros(b, maxLength);

        // Percorre as strings de trás para frente, subtraindo bit a bit
        for (int i = maxLength - 1; i >= 0; i--) {
            int bit1 = a.charAt(i) - '0';
            int bit2 = b.charAt(i) - '0' + borrow;
            if (bit1 < bit2) {
                bit1 += 2;
                borrow = 1;
            } else {
                borrow = 0;
            }
            sb.append(bit1 - bit2);
        }

         // Remove zeros à esquerda desnecessários do resultado final
         while (sb.length() > 1 && sb.charAt(sb.length() - 1) == '0') {
            sb.deleteCharAt(sb.length() - 1);
        }

        return sb.reverse().toString();
    }

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