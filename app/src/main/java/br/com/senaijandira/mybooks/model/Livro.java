package br.com.senaijandira.mybooks.model;

public class Livro {
    private int id;
    //a capa é uma foto  e para ser guardada no banco, será gravado os bytes da foto, no caso um array de bytes
    private byte[] capa;
    private String titulo;
    private String descricao;



    //Construtor
    public Livro(){

    }

    //Construtor
    public Livro(int id, byte[] capa, String titulo, String descricao){

        this.id = id;
        this.capa = capa;
        this.titulo = titulo;
        this.descricao = descricao;

    }





    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getCapa() {
        return capa;
    }

    public void setCapa(byte[] capa) {
        this.capa = capa;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
