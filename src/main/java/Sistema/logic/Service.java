package Sistema.logic;

import Sistema.data.data;
import Sistema.data.XmlPersister;
import Sistema.logic.CategoriaRecurso;

import java.util.List;
import java.util.stream.Collectors;

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

    public void createCategoria(CategoriaRecurso categoria) throws Exception {
        categoria.setID(generarSiguienteIdCategoria());
        d.getCategorias().add(categoria);
        guardar();
    }

    private String generarSiguienteIdCategoria() {
        int maxNumero = d.getCategorias().stream()
                .map(CategoriaRecurso::getID)
                .filter(id -> id != null && id.startsWith("CAT-"))
                .mapToInt(id -> Integer.parseInt(id.substring(4)))
                .max()
                .orElse(0);

        return String.format("CAT-%06d", maxNumero + 1);
    }
    public void updateCategoria(CategoriaRecurso c) throws Exception {
        CategoriaRecurso existente = d.getCategorias().stream()
                .filter(cat -> cat.getID().equals(c.getID()))
                .findFirst()
                .orElseThrow(() -> new Exception("Categoría no encontrada"));
        existente.setDescripcion(c.getDescripcion());
        guardar();
    }

    public void deleteCategoria(String id) throws Exception {
        CategoriaRecurso existente = d.getCategorias().stream()
                .filter(cat -> cat.getID().equals(id))
                .findFirst()
                .orElseThrow(() -> new Exception("Categoría no encontrada"));
        d.getCategorias().remove(existente);
        guardar();
    }

    public CategoriaRecurso findCategoriaById(String id) {
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
        if (cat == null || cat.getID() == null) {
            return d.getRecursos();
        }
        return d.getRecursos().stream()
                .filter(r -> r.getCategoria() != null && r.getCategoria().getID() == cat.getID())
                .collect(Collectors.toList());
    }

    public List<Recurso> findAllRecursos() {
        return d.getRecursos();
    }
}
