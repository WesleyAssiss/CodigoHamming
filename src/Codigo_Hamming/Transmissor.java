/*
Wesley Elbert Assis
*/

package Codigo_Hamming;
import java.util.Random;

public class Transmissor {

    private String mensagem;

    public Transmissor(String mensagem) {
        this.mensagem = mensagem;
    }

    //convertendo um símbolo para "vetor" de boolean (bits)
    private boolean[] streamCaracter(char simbolo) {

        //cada símbolo da tabela ASCII é representado com 8 bits
        boolean bits[] = new boolean[8];

        //convertendo um char para int (encontramos o valor do mesmo na tabela ASCII)
        int valorSimbolo = (int) simbolo;
        int indice = 7;

        //convertendo cada "bits" do valor da tabela ASCII
        while (valorSimbolo >= 2) {
            int resto = valorSimbolo % 2;
            valorSimbolo /= 2;
            bits[indice] = (resto == 1);
            indice--;
        }
        bits[indice] = (valorSimbolo == 1);

        return bits;
    }

    //não modifique (seu objetivo é corrigir esse erro gerado no receptor)
    private void geradorRuido(boolean bits[]) {
        Random geradorAleatorio = new Random();

        //pode gerar um erro ou não..
        if (geradorAleatorio.nextInt(5) > 1) {
            int indice = geradorAleatorio.nextInt(8);
            bits[indice] = !bits[indice];
        }

    }

    private boolean[] dadoBitsHemming(boolean bits[]) {     
        int  contador = 0;
        //Inclui números de Hamming e Bits.
        boolean[] novoBits = new boolean[12];

        //Definindo as posições e analisando se é true ou false.
        boolean H1 = bits[0] ^ bits[1] ^ bits[3] ^ bits[4] ^ bits[6];
        boolean H2 = bits[0] ^ bits[2] ^ bits[3] ^ bits[5] ^ bits[6];
        boolean H3 = bits[1] ^ bits[2] ^ bits[3] ^ bits[7];
        boolean H4 = bits[4] ^ bits[5] ^ bits[6] ^ bits[7];

       
        //Percorrendo o array novoBits
        for (int i = 0; i < novoBits.length; i++) {
         
            // i igual a 0, então novoBits na posição 0 recebe H1.
            if (i == 0) {
                novoBits[0] = H1;
            // i igual a 1, então novoBits na posição 1 recebe H2.
            } else if (i == 1) {
                novoBits[1] = H2;
            }
            // i igual a 3, então novoBits na posição 3 recebe H3.
            else if (i == 3) {
                novoBits[3] = H3;
            // i igual a 7, então novoBits na posição 7 recebe H4.
            } else if (i == 7) {
                novoBits[7] = H4;
                  
            }
       
            else {
                novoBits[i] = bits[contador];
                contador++;
            }
        }
       
        return novoBits;
    }

    public void enviaDado(Receptor receptor) {
        for (int i = 0; i < this.mensagem.length(); i++) {
            boolean bits[] = streamCaracter(this.mensagem.charAt(i));

            boolean bitsHemming[] = dadoBitsHemming(bits);

            //add ruidos na mensagem a ser enviada para o receptor
            geradorRuido(bitsHemming);

            //enviando a mensagem "pela rede" para o receptor
            receptor.receberDadoBits(bitsHemming);
        }
    }
}
