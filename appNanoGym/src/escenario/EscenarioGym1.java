package escenario;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import exceptions.SalaNoCompatibleException;
import nanoGym.NanoGym;
import sala.Sala;
import servicio.actividad.*;
import servicio.actividad.actividadmonitor.*;
import usuario.*;
import usuario.cliente.Cliente;
import utiles.Horario;

public class EscenarioGym1 {
    private NanoGym ng;

    public EscenarioGym1() {
        this.ng = new NanoGym();
    }

    public static void main(String[] args) {
        EscenarioGym1 e1 = new EscenarioGym1();
        e1.crearUsuarios(20);
        System.out.println("Numero de usuarios:" + e1.ng.getUsuarios().size());
        e1.crearSalas();
        System.out.println("Numero de salas:" + e1.ng.getSalas().size());
        //e1.crearActividades();
        System.out.println("Numero de actividades:" + e1.ng.getServicios().size());

        e1.ng.generateBackUp();

        // Leer backup
        NanoGym ng2 = new NanoGym();
        ng2.loadBackUp();
        System.out.println("Numero de usuarios:" + ng2.getUsuarios().size());
    }

    public void crearUsuarios(int num) {
        String usr = "usr";
        String pw = "12345";
        String nombre = "nombre";
        LocalDate fN = LocalDate.of(1990, 12, 12);
        int i;

        Usuario p = new Cliente("pat", "12345", "hola", LocalDate.of(1990, 12, 12));
        this.addNotificaciones(p);
        ng.addUsuario(p);

        p = new Monitor("mon", "12345", "Monitor Prueba", "monitor-prueba@gmail.com", "12345678V");
        this.addNotificaciones(p);
        ng.addUsuario(p);
        for (i = 0; i < (num-1)/2; i++) {
            usr += i;
            nombre += i;

            Usuario u = new Cliente(usr, pw, nombre, fN);
            ng.addUsuario(u);

            this.addNotificaciones(u);

            usr.substring(0, usr.length()-1);
            nombre.substring(0, nombre.length()-1);
        }

        String email = "email";
        String dni = "123456789";
        for (; i < num-1; i++) {
            usr += i;
            nombre += i;

            Usuario u = new Monitor(usr, pw, nombre, email, dni);
            ng.addUsuario(u);
            usr.substring(0, usr.length()-1);
            nombre.substring(0, nombre.length()-1);
        }

        usr = "adm";
        Usuario u = new Administrador(usr, pw);
        ng.addUsuario(u);
    }

    public void addNotificaciones(Usuario u) {
        u.addNotificacion("Yoga empieza en 5 minutos");
        u.addNotificacion("A por la 33");
        u.addNotificacion("Se te ha cancelado la clase de natación");
        u.addNotificacion("Te han cobrado 10€ por la clase de natación");
    }

    public void crearSalas() {
        // Salas creadas: 1 + 5 + 10 + 20 = 36
        int aforo = 100, aforo2, aforo3;
        String nombre = "Sala";
        String descripcion = "Sala de prueba";
        int i;
        Sala sh, sh2, sh3;
        Horario h1 = new Horario(LocalTime.of(8, 0), LocalTime.of(22, 0));

        // Sala Padre
        Sala sp = new Sala(nombre, aforo, descripcion);
        ng.addSala(sp);
        
        aforo = (aforo/5);
        aforo2 = (aforo/2);
        aforo3 = (aforo2/2);
        for (i = 0; i < 5; i++) {
            nombre += i;
            descripcion += i;

            if (i == 4) {
                sh = sp.crearSalaClimatizada(h1, nombre, aforo, descripcion);
            } else {
                sh = sp.crearSalaHija(nombre, aforo, descripcion);
            }
            ng.addSala(sh);

            nombre.substring(0, nombre.length()-1);
            descripcion.substring(0, descripcion.length()-1);
            
            for (int j= 0; j < 2; j++) {
                nombre += i + j;
                descripcion += i + j;

                sh2 = sh.crearSalaHija(nombre, aforo2, descripcion);
                ng.addSala(sh2);

                nombre.substring(0, nombre.length()-3);
                descripcion.substring(0, descripcion.length()-3);

                for (int j2 = 0; j2 < 2; j2++) {
                    nombre += i + j + j2;
                    descripcion += i + j + j2;

                    sh3 = sh2.crearSalaHija(nombre, aforo3, descripcion);
                    ng.addSala(sh3);

                    nombre.substring(0, nombre.length()-3);
                    descripcion.substring(0, descripcion.length()-3);
                }
            }
        }
    }

    public void crearActividades() {
        int i;
        Actividad s;
        Horario[] h = {
            new Horario(LocalTime.of(8, 0), LocalTime.of(10, 0)),
            new Horario(LocalTime.of(10, 0), LocalTime.of(12, 0)),
            new Horario(LocalTime.of(12, 0), LocalTime.of(14, 0)),
            new Horario(LocalTime.of(14, 0), LocalTime.of(16, 0)),
            new Horario(LocalTime.of(16, 0), LocalTime.of(18, 0)),
            new Horario(LocalTime.of(18, 0), LocalTime.of(20, 0)),
            new Horario(LocalTime.of(20, 0), LocalTime.of(22, 0))};
        String nombre = "Servicio";
        String descripcion = "Servicio de prueba";
        Set<TipoActividad> t = ng.getTiposDeActividad();
        List<Monitor> m = ng.getMonitores();
        int k_m = 0;

        List<Sala> salas = ng.getSalasHojas();
        int nSalas = salas.size();
        LocalDate fI = LocalDate.of(2020, 1, 1);

        for (i = 0; i < 70; i++) {
            nombre += i;
            descripcion += i;

            try {
                if (i % 2 == 0) {
                    s = new EntrenamientoLibre(nombre, descripcion, h[i%7], fI, salas.get(i%nSalas));
                }
                else {
                    s = new ActividadGrupal(nombre, descripcion, h[i%7], fI, salas.get(i%nSalas), m.get(k_m), t.iterator().next());
                }
                try {
                    salas.get(i%nSalas).addActividad(s);
                } catch (SalaNoCompatibleException e) {
                    System.out.println(e.getMessage());
                }
            } catch (Exception e) {i--; k_m = (k_m+1)%m.size(); continue;}

            nombre.substring(0, nombre.length()-1);
            descripcion.substring(0, descripcion.length()-1);
        }
    }
}
