public class Articulo {
    
    private String id;
    private String nombre;
    private String descripcion;
    private double precio;
    private int stock;

    public Articulo(String id, String nombre, String descripcion, double precio, int stock) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
    }

    public String getId() {
        return id;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public double getPrecio() {
        return precio;
    }

    public int getStock() {
        return stock;
    }
}
