package Sistema.logic;

public class Empleado extends Usuario{
    private String nombre;
    private int numTelefono;

    public Empleado(String id, String clave, String nombre, int telefono) {
        super(id, clave, "empleado");
        this.nombre = nombre;
        this.numTelefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTelefono() {
        return numTelefono;
    }

    public void setTelefono(int telefono) {
        this.numTelefono = telefono;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void agregarReserva(Reserva reserva) {
        reservas.add(reserva);
    }

    @Override
    public String toString() {
        return "Funcionario{id='" + getId() + "', nombre='" + nombre + "'}";
    }
}
