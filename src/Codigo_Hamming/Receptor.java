/*
Wesley Elbert Assis
*/

package Codigo_Hamming;
public class Receptor {
    
    //mensagem recebida pelo transmissor
    private String mensagem;

    public Receptor() {
    //mensagem vazia no inicio da execução
        this.mensagem = "";
    }

    public String getMensagem() {
        return mensagem;
    }

    private void decodificarDado(boolean bits[]) {
        int codigoAscii = 0;
        int expoente = bits.length - 1;

        //convertendo os "bits" para valor inteiro para então encontrar o valor tabela ASCII
        for (int i = 0; i < bits.length; i++) {
            if (bits[i]) {
                codigoAscii += Math.pow(2, expoente);
            }
            expoente--;

        }

        //concatenando cada simbolo na mensagem original
        this.mensagem += (char) codigoAscii;

    }

    private void decodificarDadoHemming(boolean[] bits) {
        //Convertendo codigoAscii em String.
        String codigoAscii = "";
        int contador = 0;
        
        

        //Verificando o Erro pelo Operador XOR
        boolean H1 = bits[0] ^ bits[2] ^ bits[4] ^ bits[6] ^ bits[8] ^ bits[10];
        boolean H2 = bits[1] ^ bits[2] ^ bits[5] ^ bits[6] ^ bits[9] ^ bits[10];
        boolean H3 = bits[3] ^ bits[4] ^ bits[5] ^ bits[6] ^ bits[11];
        boolean H4 = bits[7] ^ bits[8] ^ bits[9] ^ bits[10] ^ bits[11];

        
        //convertendo de boolean para inteiro.
        int h1, h2, h3, h4;
        h1 = H1 == true ? 1 : 0;
        h2 = H2 == true ? 1 : 0;
        h3 = H3 == true ? 1 : 0;
        h4 = H4 == true ? 1 : 0;

        //Depois de verificado, se todos forem 0 está certo, porém se algum der 1,
        // vamos reverter para sabermos em qual posição(bits) ele se encontra.
        String Bits_Inverte = h4 + "" + h3 + "" + h2 + "" + h1;

        //Analisando se algum esta com erro, ou seja se h1 ou h2 ou h3 ou h4 for igual
        // a 1 então a posição está errada.
        if (h1 == 1 || h2 == 1 || h3 == 1 || h4 == 1) {
            int bitPosicao = Integer.parseInt(Bits_Inverte, 2);

            //Posição -1 porque o Array começa de  0 até 11.
            //Onde for encontrado o erro ele inverterá o valor da posição
            if (bits[bitPosicao - 1] == true) {
                bits[bitPosicao - 1] = false;

            } else {
                bits[bitPosicao - 1] = true;
            }
        }       
        //Transformando 12 bits para 8 bits.
        for (int i = 0; i < bits.length; i++) {
            if (!(i == 0 || i == 1 || i == 3 || i == 7)) {
                if (bits[i] == true) {
                    codigoAscii += 1;
                } else {
                    codigoAscii += 0;
                }
            }
        }
        //Mostrando em Bits a informação Corrigida.
        this.mensagem += (char) Integer.parseInt(codigoAscii, 2);
    }
        //recebe os dados do transmissor
        public void receberDadoBits(boolean bits[]) {
            
        decodificarDadoHemming(bits);
    }
}
