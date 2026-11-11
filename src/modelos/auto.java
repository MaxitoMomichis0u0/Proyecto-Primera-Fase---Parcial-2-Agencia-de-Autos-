package modelos;

public class auto {
    
    private int id;
    private String modelo;
    private String marca;
    private int ano;
    private String caracteristicas;
    
    // Constructor para agregar carros.

    public auto(String modelo, String marca, int ano, String caracteristicas) {
        this.modelo = modelo;
        this.marca = marca;
        this.ano = ano;
        this.caracteristicas = caracteristicas;
    }
    
    // Constructor para leer de la BDD.
    public auto(int id, String modelo, String marca, int ano, String caracteristicas) {
        this.id = id;
        this.modelo = modelo;
        this.marca = marca;
        this.ano = ano;
        this.caracteristicas = caracteristicas;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(String caracteristicas) {
        this.caracteristicas = caracteristicas;
    }
    
    
    
}
