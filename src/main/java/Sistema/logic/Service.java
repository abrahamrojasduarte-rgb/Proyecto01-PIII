package Sistema.logic;

import Sistema.data.data;
import Sistema.data.XmlPersister;

import java.util.List;
import java.util.stream.Collectors;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Service {
    private data d;
    private static Service theInstance;

    private Service() {
        try {
            d = XmlPersister.instance().load();
        } catch (Exception e) {
            d = new data();
        }
        crearAdminPorDefectoSiNoExiste();
    }

    public static Service instance() {
        if (theInstance == null) theInstance = new Service();
        return theInstance;
    }

    public void stop() {
        try {
            XmlPersister.instance().store(d);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void guardar() throws Exception {
        XmlPersister.instance().store(d);
    }

    public void create(Funcionario e) throws Exception {
        Funcionario result = d.getFuncionarios().stream()
                .filter(i -> i.getId().equals(e.getId()))
                .findFirst()
                .orElse(null);
        if (result == null) {
            e.setClave(e.getId()); // Regla de negocio: clave inicial igual al ID
            d.getFuncionarios().add(e);
            guardar();
        } else {
            throw new Exception("Funcionario ya existe");
        }
    }

    public void delete(String id) throws Exception {
        Funcionario result = d.getFuncionarios().stream()
                .filter(i -> i.getId().equals(id))
                .findFirst()
                .orElse(null);
        if (result != null) {
            d.getFuncionarios().remove(result);
            guardar();
        } else {
            throw new Exception("Funcionario no existe");
        }
    }

    public List<Funcionario> search(String id, String nombre) {
        return d.getFuncionarios().stream()
                .filter(i -> (id == null || id.isEmpty() || i.getId().toLowerCase().contains(id.toLowerCase()))
                        && (nombre == null || nombre.isEmpty() || i.getNombre().toLowerCase().contains(nombre.toLowerCase())))
                .collect(Collectors.toList());
    }

    public List<Funcionario> findAll() {
        return d.getFuncionarios();
    }

    public Usuario encontrarUsuario(String id) {
        Usuario result = d.getFuncionarios().stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (result == null) {
            result = d.getAdministradors().stream()
                    .filter(u -> u.getId().equals(id))
                    .findFirst()
                    .orElse(null);
        }
        return result;
    }

    private void crearAdminPorDefectoSiNoExiste() {
        boolean hayAdmin = d.getAdministradors().stream()
                .anyMatch(u -> u.getRol() == Rol.ADMINISTRADOR);

        if (!hayAdmin) {
            Administrador admin = new Administrador("admin", "admin123");
            d.getAdministradors().add(admin);
            try {
                XmlPersister.instance().store(d);
            } catch (Exception e) {
                System.out.println("No se pudo guardar el admin por defecto: " + e);
            }
        }
    }

    public void actualizarClave(String id, String claveNueva) throws Exception {
        Usuario usuario = encontrarUsuario(id);
        if (usuario == null) {
            throw new Exception("Usuario no encontrado");
        }
        usuario.setClave(claveNueva);
        guardar();
    }

    public void createCategoria(CategoriaRecurso c) throws Exception {
        if (c.getID() <= 0) {
            int nextId = d.getCategorias().stream()
                    .mapToInt(CategoriaRecurso::getID)
                    .max()
                    .orElse(0) + 1;
            c.setID(nextId);
        }
        d.getCategorias().add(c);
        guardar();
    }

    public void updateCategoria(CategoriaRecurso c) throws Exception {
        CategoriaRecurso exist = findCategoriaById(c.getID());
        if (exist == null) throw new Exception("Categoría no encontrada");
        exist.setDescripcion(c.getDescripcion());
        guardar();
    }

    public void deleteCategoria(int id) throws Exception {
        CategoriaRecurso c = findCategoriaById(id);
        if (c == null) throw new Exception("Categoría no existe");

        boolean tieneRecursos = d.getRecursos().stream()
                .anyMatch(r -> r.getCategoria() != null && r.getCategoria().getID() == id);
        if (tieneRecursos) throw new Exception("No se puede eliminar: tiene recursos asociados");

        d.getCategorias().remove(c);
        guardar();
    }

    public CategoriaRecurso findCategoriaById(int id) {
        return d.getCategorias().stream()
                .filter(c -> c.getID() == id)
                .findFirst()
                .orElse(null);
    }

    public List<CategoriaRecurso> searchCategorias(String descripcion) {
        return d.getCategorias().stream()
                .filter(c -> descripcion == null || descripcion.trim().isEmpty() ||
                        c.getDescripcion().toLowerCase().contains(descripcion.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<CategoriaRecurso> findAllCategorias() {
        return d.getCategorias();
    }

    public void createRecurso(Recurso r) throws Exception {
        Recurso exist = findRecursoById(r.getId());
        if (exist != null) throw new Exception("El recurso con ID/Activo ya existe");
        d.getRecursos().add(r);
        guardar();
    }

    public void updateRecurso(Recurso r) throws Exception {
        Recurso exist = findRecursoById(r.getId());
        if (exist == null) throw new Exception("Recurso no encontrado");
        exist.setDescripcion(r.getDescripcion());
        exist.setCategoria(r.getCategoria());
        guardar();
    }

    public void deleteRecurso(String id) throws Exception {
        Recurso r = findRecursoById(id);
        if (r == null) throw new Exception("Recurso no existe");
        d.getRecursos().remove(r);
        guardar();
    }

    public Recurso findRecursoById(String id) {
        return d.getRecursos().stream()
                .filter(r -> r.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Recurso> searchRecursosPorCategoria(CategoriaRecurso cat) {
        if (cat == null || cat.getID() == -1) {
            return d.getRecursos();
        }
        return d.getRecursos().stream()
                .filter(r -> r.getCategoria() != null && r.getCategoria().getID() == cat.getID())
                .collect(Collectors.toList());
    }

    public List<Recurso> findAllRecursos() {
        return d.getRecursos();
    }

    public List<Reserva> findReservasByFuncionario(String idFuncionario) {
        return d.getReservas().stream()
                .filter(r -> r.getFuncionario() != null
                        && r.getFuncionario().getId().equals(idFuncionario))
                .collect(Collectors.toList());
    }

    public Reserva findReservaById(int id) {
        return d.getReservas().stream()
                .filter(r -> r.getId() == id)
                .findFirst()
                .orElse(null);
    }

    private int nextReservaId() {
        return d.getReservas().stream()
                .mapToInt(Reserva::getId)
                .max()
                .orElse(0) + 1;
    }

    private boolean estaOcupado(Recurso recurso, LocalDate fecha, LocalTime inicio, LocalTime fin) {
        for (Reserva r : d.getReservas()) {
            if (!r.getFecha().equals(fecha)) continue;

            boolean seCruzan = inicio.isBefore(r.getHoraTermina()) && fin.isAfter(r.getHoraInicia());
            if (!seCruzan) continue;

            for (Recurso usado : r.getRecursos()) {
                if (usado.getId().equals(recurso.getId())) return true;
            }
        }
        return false;
    }

    private Recurso buscarLibre(CategoriaRecurso categoria, LocalDate fecha, LocalTime inicio, LocalTime fin) {
        for (Recurso r : d.getRecursos()) {
            if (r.getCategoria() == null) continue;
            if (r.getCategoria().getID() != categoria.getID()) continue;
            if (!estaOcupado(r, fecha, inicio, fin)) return r;
        }
        return null;
    }

    public void createReserva(String actividad, Funcionario funcionario, LocalDate fecha,
                              LocalTime inicio, LocalTime fin, List<CategoriaRecurso> categorias) throws Exception {

        List<Recurso> asignados = new ArrayList<>();
        List<String> noDisponibles = new ArrayList<>();

        for (CategoriaRecurso c : categorias) {
            Recurso libre = buscarLibre(c, fecha, inicio, fin);
            if (libre == null) {
                noDisponibles.add(c.getDescripcion());
            } else {
                asignados.add(libre);
            }
        }

        if (!noDisponibles.isEmpty()) {
            throw new Exception("No hay disponibilidad en: " + String.join(", ", noDisponibles));
        }

        Reserva reserva = new Reserva(nextReservaId(), actividad, funcionario, fecha, inicio, fin);
        reserva.setRecursos(asignados);
        d.getReservas().add(reserva);
        guardar();
    }

    public void deleteReserva(int id) throws Exception {
        Reserva r = findReservaById(id);
        if (r == null) throw new Exception("La reserva no existe");
        d.getReservas().remove(r);
        guardar();
    }

    public ReservaExtraccion extraerReserva(String frase) {
        OpenAiChatModel aiModel = OpenAiChatModel.builder()
                .baseUrl("http://langchain4j.dev/demo/openai/v1")
                .apiKey("demo")
                .modelName("gpt-4o-mini")
                .build();

        ReservaExtractorService aiService = AiServices.create(ReservaExtractorService.class, aiModel);

        String listaCategorias = d.getCategorias().stream()
                .map(CategoriaRecurso::getDescripcion)
                .collect(Collectors.joining("\n"));

        return aiService.extraer(frase, listaCategorias, LocalDate.now().toString());
    }

}
