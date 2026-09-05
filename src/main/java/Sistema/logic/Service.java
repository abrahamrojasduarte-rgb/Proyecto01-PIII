package Sistema.logic;

import Sistema.data.data;
import Sistema.data.XmlPersister;

import java.util.Comparator;
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
        } else {
            throw new Exception("Funcionario no existe");
        }
    }

    public List<Funcionario> search(String id, String nombre) {
        return d.getFuncionarios().stream()
                .filter(i -> (id == null || id.isEmpty() || i.getId().contains(id))
                        && (nombre == null || nombre.isEmpty() || i.getNombre().contains(nombre)))
                .collect(Collectors.toList());
    }

    public List<Funcionario> findAll() {
        return d.getFuncionarios();
    }

    public Usuario encontrarUsuario(String id){
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
}
