import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Estacionamento {
    private String[] placas;
    private String livre = "Livre";

    public Estacionamento(int capacidade) throws Exception {
        try{
            if(capacidade > 0)
                this.placas = new String[capacidade + 1]; // Inicializando o array de placas de acordo com as vagas
                validateArraysVagas();
        } catch (Exception ex)
        {
            throw new Exception("Não foi possível definir o número de vagas no estacionamento!");
        }
    }

    public String[] getPlacas() {
        return this.placas;
    }

    public void setPlacas(String[] placa){
        this.placas = placa;
    }

    public void validateArraysVagas() {
        for (int i = 1; i < this.placas.length; i++){
            if(this.placas[i] == null)
                this.placas[i] = this.livre;
        }
    }

    public void entrar(String placa, int vaga) throws Exception {
        FileWriter file = null;
        if(vaga < 1 || vaga > this.placas.length)
            throw new ArrayIndexOutOfBoundsException("Error E1 - Vaga inexistente");

        if(this.placas[vaga] != livre)
            throw new Exception("Error E2 - Vaga ocupada!");

        for(String p : this.placas)
            if(p != null && p.equals(placa))
                throw new Exception("Error E3 - A placa já se encontra no estacionamento!");

        this.placas[vaga] = placa;
        try{
            file = new FileWriter("historico.csv", true);

            LocalDateTime dataAtual = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

            String data = dataAtual.format(formatter);

            String linha = data + "; " + placa + "; " + vaga + "; " + "entrada\n";
            file.write(linha);
            file.close();

        } catch (Exception ex)
        {
            throw new Exception("Error E4 - Não foi possível cadastrar o veículo na vaga!");
        }
    }

    public void sair(int vaga) throws Exception{
        FileWriter file = null;
        if(vaga < 1 || vaga > this.placas.length)
            throw new ArrayIndexOutOfBoundsException("Error S1 - Vaga inexistente");

        if(this.placas[vaga] == livre)
            throw new Exception("Error S2 - Vaga já está desocupada!");
        try {
            file = new FileWriter("historico.csv", true);

            LocalDateTime dataAtual = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

            String data = dataAtual.format(formatter);

            String linha = data + "; " + this.placas[vaga] + "; " + vaga + "; " + "saida\n";
            file.write(linha);
            file.close();

            if(this.placas[vaga] != livre)
                this.placas[vaga] = this.livre;
        } catch (Exception ex)
        {
            throw new Exception("Error S3 - Não foi possível retirar o veículo da vaga!");
        }
    }

    public int consultarPlaca(String placa) {
        int vaga = 1;
        for (int i = 1;  i < this.placas.length; i++) {
            if(this.placas[i].equals(placa))
                return vaga;
            vaga++;
        }
        return -1;
    }

    public void transferir(int vagaAtual, int vagaFutura) throws Exception{
    	if(this.placas[vagaAtual] == this.livre)
            throw new Exception("Error T1 - A vaga atual está livre!");

        if(this.placas[vagaFutura] != this.livre)
            throw new Exception(("Error T2 - A vaga futura está ocupada!"));
    	try{
            this.placas[vagaFutura] = this.placas[vagaAtual];
            this.placas[vagaAtual] = this.livre;

        } catch (Exception ex)
        {
            throw new Exception("Error T4 - Não foi possível transferir o veículo!");
        }
    }

    public String[] listarGeral(){
        String[] result = new String[this.placas.length - 1];

        for(int i = 1; i < this.placas.length; i++){
            result[i-1] = "Carro: " + this.placas[i] +"; Vaga: " + i;
        }

        return result;
    }

    public ArrayList<Integer> listarLivres(){
        ArrayList<Integer> result = new ArrayList<Integer>(this.placas.length);

        for (int i = 1; i < this.placas.length; i++){
            if(this.placas[i] == this.livre)
            {
                result.add(i);
            }
        }

        return result;
    }

    public void gravarDados() throws Exception {
        FileWriter file = null;
        try{
            file = new FileWriter("placas.csv", true);
            file.write("==== Estacionamento ====\n");
            file.write("Placa  ;Vaga\n\n");

            for (int i = 1; i < this.placas.length; i++) {
                String placa = this.placas[i];
                String vaga = String.valueOf(i);
                String linha = String.format("%-7s;%2s\n", placa, vaga);
                file.write(linha);
            }
            System.out.println("Dados salvos com sucesso!");
            file.close();
        } catch (FileNotFoundException ex){
            throw new FileNotFoundException("Error GD1: Arquivo não encontrado!");
        } catch (IOException ex) {
            throw new IOException("Error GD2: Não foi possível gravar este arquivo!");
        } catch (Exception ex) {
            throw new Exception("Error GD3: Ocorreu um erro ao tentar gravar arquivo!");
        }
    }

    public void lerDados() throws Exception{
        try {
        	BufferedReader fileReader = new BufferedReader(new FileReader("placas.csv"));
        	String linha;

            while ((linha = fileReader.readLine()) != null) {
                System.out.println(linha);
            }
            
            fileReader.close();
        } catch (FileNotFoundException ex){
            throw new FileNotFoundException("Error LD1: Arquivo não encontrado!");
        } catch (IOException ex) {
            throw new IOException("Error LD2: Não foi possível ler este arquivo!");
        } catch (Exception ex) {
            throw new Exception("Error LD3: Ocorreu um erro ao tentar ler arquivo!");
        }
    }
}
