package Sistema.logic;

public class DetalleReserva {

    private Categoria categoriaSolicitada;
    private Recurso recursoAsignado;

    public DetalleReserva(Categoria categoriaSolicitada) {
        this.categoriaSolicitada = categoriaSolicitada;
    }

    public Categoria getCategoriaSolicitada() {
        return categoriaSolicitada;
    }

    public void setCategoriaSolicitada(Categoria categoriaSolicitada) {
        this.categoriaSolicitada = categoriaSolicitada;
    }

    public Recurso getRecursoAsignado() {
        return recursoAsignado;
    }

    public void setRecursoAsignado(Recurso recursoAsignado) {
        this.recursoAsignado = recursoAsignado;
    }

    @Override
    public String toString() {
        return categoriaSolicitada + " -> " + (recursoAsignado != null ? recursoAsignado : "sin asignar");
    }
}