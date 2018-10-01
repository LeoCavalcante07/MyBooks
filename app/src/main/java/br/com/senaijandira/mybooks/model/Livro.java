package br.com.senaijandira.mybooks.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity

public class Livro {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] capa; //a capa é uma foto  e para ser guardada no banco, será gravado os bytes da foto, no caso um array de bytes
    private String titulo;
    private String descricao;
    private int statusLivro; //se 0 o livro não é nem lido nem para ler, se 1, o livro é para ler e se 2 o livro é lido



    //Construtor
    public Livro(){

    }

    //Construtor
    public Livro(int id, byte[] capa, String titulo, String descricao, int statusLivro){

        this.id = id;
        this.capa = capa;
        this.titulo = titulo;
        this.descricao = descricao;
        this.statusLivro = statusLivro;

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

    public int getStatusLivro() {
        return statusLivro;
    }

    public void setStatusLivro(int statusLivro) {
        this.statusLivro = statusLivro;
    }
}
