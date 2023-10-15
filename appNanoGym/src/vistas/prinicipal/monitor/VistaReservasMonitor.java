package vistas.prinicipal.monitor;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

import usuario.*;
import controladores.principal.ControladorReservas;
import sala.Sala;
import servicio.actividad.actividadmonitor.ActividadMonitor;
import vistas.prinicipal.VistaReservas;

public class VistaReservasMonitor extends VistaReservas {
    
    private JLabel titulotusClases;
    private JButton crearEntPersClases;

    // Crear ent pers
    private JLabel epNombre, epDescripcion, epMonitor, epObjetivo, epNewObjLabel;
    private JTextField epNombreText, epDescText, epNewObjText;
    private JButton nuevoObj, cepVolver, cepCrear;
    private JComboBox<String> epMonitorCombo, epObjCombo;

    // Seleccionar actividades
    private JLabel seleccionActivLabel, listaActLabel, sinActSelecLabel;
    private JButton addActividad;
    private JComboBox<String> actividadesCombo;
    private HashMap<String, ActividadMonitor> string2actMap;

    // Crear Entrenamiento Monitor
    private JLabel epLabelCrearEntMoni, epNombreEntrenoMoniLabel, epDescEntrenoMoniLabel, epHoraInicialLabel, epHoraFinalLabel, epFechaEntrenoMoniLabel;
    private JButton epCrearEntrenoMoni;
    private JTextField epNombreEntrenoMoni, epDescEntrenoMoni, epHoraInicial, epHoraFinal, epFechaEntrenoMoni;
    private JComboBox<String> salasCombo;
    private HashMap<String, Sala> string2salaMap;

    // Actividades seleccionadas
    private TreeMap<ActividadMonitor, JLabel> epActividadesSeleccionadas;
    private int numActSelec, numActSelecInit;
    private int flag;

    public VistaReservasMonitor() {
        super();
        flag = 0;
        inicializarClases();
        crearObjCrearEntPers();
    }

    @Override
    public void setControlador(ControladorReservas controlador) {
        //System.out.println("ControladorReservasMonitor");
        crearEntPersClases.addActionListener(controlador);
        cepVolver.addActionListener(controlador);
        nuevoObj.addActionListener(controlador);
        cepCrear.addActionListener(controlador);
        addActividad.addActionListener(controlador);
        epCrearEntrenoMoni.addActionListener(controlador);
        super.setControlador(controlador);
        
    }

    // Botones
    public JButton getBotonCrearEntPersClases() {
        return crearEntPersClases;
    }

    public JButton getBotonVolverCrearEntPersClases() {
        return cepVolver;
    }

    public JButton getBotonNuevoObj() {
        return nuevoObj;
    }

    public JButton getBotonCrearEntPers() {
        return cepCrear;
    }

    public JButton getBotonAddActividad() {
        return addActividad;
    }

    // Resto
    public JComboBox<String> getComboMonitor() {
        return epMonitorCombo;
    }

    public JTextField getNombre() {
        return epNombreText;
    }

    public JTextField getDescripcion() {
        return epDescText;
    }

    public JTextField getNewObjText() {
        return epNewObjText;
    }

    public void setMonitores2ComboMonitor(ArrayList<Monitor> monitores) {
        for (Monitor monitor : monitores) {
            epMonitorCombo.addItem(monitor.getName() + " (" + monitor.getNif() + ")");
        }
    }
    public void setMonitores2ComboMonitor(Monitor monitor) {
        String str = monitor.getName() + " (" + monitor.getNif() + ")";
        epMonitorCombo.addItem(str);
        epMonitorCombo.setSelectedIndex(epMonitorCombo.getItemCount()-1);
    }

    public void addObjetivo(String objetivo) {
        epObjCombo.addItem(objetivo);
        epObjCombo.setSelectedItem(objetivo);
    }

    public JComboBox<String> getComboObjetivo() {
        return epObjCombo;
    }

    public void setObjetivos2ComboObjetivo(Collection<String> objetivos) {
        for (String objetivo : objetivos) {
            epObjCombo.addItem(objetivo);
        }
    }

    public JComboBox<String> getComboActividades() {
        return actividadesCombo;
    }

    public void setActividades2ComboActividades(ArrayList<ActividadMonitor> actividades) {
        //System.out.println("Actividades: " + actividades.size());
        string2actMap.clear();
        actividadesCombo.removeAllItems();
        actividadesCombo.addItem("-- Selecciona una actividad --");
        actividadesCombo.setSelectedIndex(0);

        Collections.sort(actividades);
        for (ActividadMonitor actividad : actividades) {
            actividadesCombo.addItem(actM2String(actividad));
            string2actMap.put(actM2String(actividad), actividad);
            System.out.println("Actividadstr: " + actM2String(actividad));
            System.out.println("Actividad: " + actividad);
        }
        System.out.println(string2actMap);
        //Yoga (monitor: Yoga, fecha: 2023-05-10, hora: 20:00 - 21:00, aforo: 0/10)
        //Yoga (monitor: Yoga, fecha: 2023-05-10, hora: 20:00 - 21:00, aforo: 0/10)
    }

    public void procedimientoAddActividad() {
        for (JLabel jl  : epActividadesSeleccionadas.values()) {
            this.remove(jl);
        }
        String actividad = actividadesCombo.getSelectedItem().toString();
        System.out.println("Actividad: " + actividad);
        ActividadMonitor am = string2actMap.get(actividad);
        System.out.println(string2actMap);
        System.out.println("Actividad: " + am);
        if (!actividad.equals("-- Selecciona una actividad --") && (am != null) && !epActividadesSeleccionadas.containsKey(am)) {
            epActividadesSeleccionadas.put(string2actMap.get(actividad), new JLabel(actividad));
            setActividadesSeleccionadas(epActividadesSeleccionadas.keySet());
        }
        this.revalidate();
        this.repaint();

    }

    public String actM2String(ActividadMonitor am) {
        return am.getNombre() + " (monitor: " + am.getNombre() + ", fecha: " + am.getFecha() + ", hora: " + am.getHorario() + ", aforo: " +  am.getReservas().size() + "/" + am.aforoDisponible() + ")";
    }


    public void setActividadesSeleccionadas(Set<ActividadMonitor> actividades) {
        int i = 0;
        numActSelec = numActSelecInit+1;
        //System.out.println("NumActSelec: " + numActSelec + "; size: " + epActividadesSeleccionadas.size());
        for (ActividadMonitor actividad : actividades) {
            i++;
            JLabel label = new JLabel(i + ") " + actM2String(actividad));
            epActividadesSeleccionadas.put(actividad, label);

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = numActSelec; numActSelec++;
            gbc.gridwidth = 3;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.fill = GridBagConstraints.CENTER;
            gbc.insets = new Insets(5, 20, 0, 10);
            this.add(label, gbc);
            //System.out.println("Label añadido: " + label.getText());

            numActSelec++;
        }
    }

    public Set<ActividadMonitor> getActividadesSeleccionadas() {
        return epActividadesSeleccionadas.keySet();
    }

    // Crear entreno moni
    public JButton getBotonCrearEntrenoMoni() {
        return epCrearEntrenoMoni;
    }

    public JLabel getLabelCrearEntrenoMoni() {
        return epLabelCrearEntMoni;
    }

    public JTextField getNombreEntrenoMoni() {
        return epNombreEntrenoMoni;
    }

    public JTextField getDescEntrenoMoni() {
        return epDescEntrenoMoni;
    }

    public JTextField getHoraInicial() {
        return epHoraInicial;
    }

    public JTextField getHoraFinal() {
        return epHoraFinal;
    }

    public JTextField getFechaEntrenoMoni() {
        return epFechaEntrenoMoni;
    }

    public JComboBox<String> getComboSalas() {
        return salasCombo;
    }

    public void setSalas2ComboSalas(Collection<Sala> salas) {
        salasCombo.removeAllItems();
        salasCombo.addItem("-- Selecciona una sala --");
        salasCombo.setSelectedIndex(0);

        for (Sala sala : salas) {
            String str = "Sala " + sala.getId() + " (aforo: " + sala.getAforo() + ")";
            salasCombo.addItem(str);
            string2salaMap.put(str, sala);
        }
    }

    public Sala getSalaSelectedEntrenoMoni() {
        return string2salaMap.get(salasCombo.getSelectedItem().toString());
    }

    // Inicializar y crear ventanas
    public void inicializarClases() {
        titulo.setText("Clases");
        labelSinRes.setText("No tienes clases asignadas en este momento");

        titulotusClases = new JLabel("Tus clases:");
        titulotusClases.setFont(new Font("Times New Roman", Font.BOLD, 14));

        crearEntPersClases = new JButton("Crear entrenamiento personalizado");

        // Colocar elementos
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 20, 10, 10);
        this.add(crearEntPersClases, gbc);

        gbc.gridy = 2;
        this.add(titulotusClases, gbc);
    }

    public void ocultarClases() {
        clearServicios();
        titulotusClases.setVisible(false);
        labelSinRes.setVisible(false);
        crearEntPersClases.setVisible(false);
    }

    public void mostrarClases() {
        titulo.setText("Clases");
        crearEntPersClases.setVisible(true);
        labelSinRes.setVisible(true);
    }

    private void crearObjCrearEntPers() {
        if (flag == 0) {
            cepVolver = new JButton("Volver");
        }
        epNombre = new JLabel("Nombre:");
        epNombreText = new JTextField(20);
        
        epDescripcion = new JLabel("Descripción:");
        epDescText = new JTextField(50);

        epMonitor = new JLabel("Monitor:");
        epMonitorCombo = new JComboBox<String>();
        epMonitorCombo.addItem("-- Selecciona un monitor --");
        epMonitorCombo.setSelectedIndex(0);

        epObjetivo = new JLabel("Objetivo:");
        epObjCombo = new JComboBox<String>();
        epObjCombo.addItem("-- Selecciona un objetivo --");
        epObjCombo.setSelectedIndex(0);

        epNewObjLabel = new JLabel("Si no encuentras tu objetivo, puedes crear uno nuevo:");
        epNewObjLabel.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        
        if (flag == 0) {
            nuevoObj = new JButton("Crear nuevo objetivo");
        }

        epNewObjText = new JTextField(20);
        epNewObjText.setText("Introduzca el objetivo nuevo");
        epNewObjText.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (epNewObjText.getText().equals("Introduzca el objetivo nuevo")) {
                    epNewObjText.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (epNewObjText.getText().equals("")) {
                    epNewObjText.setText("Introduzca el objetivo nuevo");
                }
            }
        });

        if (flag == 0)
            cepCrear = new JButton("Crear Entrenamiento Personalizado");

        // Seleccionar actividades
        seleccionActivLabel = new JLabel("Selecciona las actividades que quieres añadir a tu entrenamiento personalizado:");
        seleccionActivLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));

        if (addActividad == null)
            addActividad = new JButton("Añadir actividad");

        actividadesCombo = new JComboBox<String>();
        actividadesCombo.addItem("-- Selecciona una actividad --");
        actividadesCombo.setSelectedIndex(0);

        string2actMap = new HashMap<String, ActividadMonitor>();


        epLabelCrearEntMoni = new JLabel("Crear sesión de entrenamiento monitor: ");
        epLabelCrearEntMoni.setFont(new Font("Times New Roman", Font.BOLD, 14));

        if (flag == 0) {
            epCrearEntrenoMoni = new JButton("Crear entrenamiento monitor");
        }

        epNombreEntrenoMoniLabel = new JLabel("Nombre:");
        epDescEntrenoMoniLabel = new JLabel("Descripción:");
        epHoraInicialLabel = new JLabel("Hora inicial:");
        epHoraFinalLabel = new JLabel("Hora final:");
        epFechaEntrenoMoniLabel = new JLabel("Fecha:");

        epNombreEntrenoMoni = new JTextField(20);
        epNombreEntrenoMoni.setText("Nombre del entrenamiento");
        epNombreEntrenoMoni.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (epNombreEntrenoMoni.getText().equals("Nombre del entrenamiento")) {
                    epNombreEntrenoMoni.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (epNombreEntrenoMoni.getText().equals("")) {
                    epNombreEntrenoMoni.setText("Nombre del entrenamiento");
                }
            }
        });

        epDescEntrenoMoni = new JTextField(50);
        epDescEntrenoMoni.setText("Descripción del entrenamiento");
        epDescEntrenoMoni.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (epDescEntrenoMoni.getText().equals("Descripción del entrenamiento")) {
                    epDescEntrenoMoni.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (epDescEntrenoMoni.getText().equals("")) {
                    epDescEntrenoMoni.setText("Descripción del entrenamiento");
                }
            }
        });

        epHoraInicial = new JTextField(20);
        epHoraInicial.setText("Hora inicial (hh:mm)");
        epHoraInicial.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (epHoraInicial.getText().equals("Hora inicial (hh:mm)")) {
                    epHoraInicial.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (epHoraInicial.getText().equals("")) {
                    epHoraInicial.setText("Hora inicial (hh:mm)");
                }
            }
        });

        epHoraFinal = new JTextField(20);
        epHoraFinal.setText("Hora final (hh:mm)");
        epHoraFinal.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (epHoraFinal.getText().equals("Hora final (hh:mm)")) {
                    epHoraFinal.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (epHoraFinal.getText().equals("")) {
                    epHoraFinal.setText("Hora final (hh:mm)");
                }
            }
        });

        epFechaEntrenoMoni = new JTextField(20);
        epFechaEntrenoMoni.setText("Fecha (dd/mm/aaaa)");
        epFechaEntrenoMoni.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (epFechaEntrenoMoni.getText().equals("Fecha (dd/mm/aaaa)")) {
                    epFechaEntrenoMoni.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (epFechaEntrenoMoni.getText().equals("")) {
                    epFechaEntrenoMoni.setText("Fecha (dd/mm/aaaa)");
                }
            }
        });

        salasCombo = new JComboBox<String>();
        salasCombo.addItem("-- Selecciona una sala --");
        salasCombo.setSelectedIndex(0);

        string2salaMap = new HashMap<String, Sala>();


        listaActLabel = new JLabel("Actividades seleccionadas:");

        epActividadesSeleccionadas = new TreeMap<ActividadMonitor, JLabel>();
        numActSelec = 0;

        sinActSelecLabel = new JLabel("Todavía no has añadido ninguna actividad al entrenamiento personalizado");
        sinActSelecLabel.setFont(new Font("Times New Roman", Font.PLAIN, 14));
    }

    public void inicializarCrearEntPersClases(Monitor monitores, Collection<String> objetivos, ArrayList<ActividadMonitor> actividades, Collection<Sala> salas) {
        ocultarClases();

        titulo.setText("Crear Entrenamiento Personalizado");

        epMonitorCombo.removeAllItems();
        epMonitorCombo.addItem("-- Selecciona un monitor --");
        epMonitorCombo.setSelectedIndex(0);
        setMonitores2ComboMonitor(monitores);

        epObjCombo.removeAllItems();
        epObjCombo.addItem("-- Selecciona un objetivo --");
        epObjCombo.setSelectedIndex(0);
        setObjetivos2ComboObjetivo(objetivos);

        actividadesCombo.removeAllItems();
        actividadesCombo.addItem("-- Selecciona una actividad --");
        actividadesCombo.setSelectedIndex(0);
        //System.out.println("Actividadesssss: " + actividades.size());
        setActividades2ComboActividades(actividades);

        salasCombo.removeAllItems();
        salasCombo.addItem("-- Selecciona una sala --");
        salasCombo.setSelectedIndex(0);
        setSalas2ComboSalas(salas);


        // Colocar elementos
        int y = 1;
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = y; y++;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 20, 0, 10);
        this.add(epNombre, gbc);

        gbc.insets = new Insets(5, 20, 0, 10);
        gbc.gridy = y; y--;
        this.add(epNombreText, gbc);

        gbc.insets = new Insets(15, 20, 0, 10);
        gbc.gridwidth = 2;
        gbc.gridx = 1;
        gbc.gridy = y; y++;
        this.add(epDescripcion, gbc);

        gbc.insets = new Insets(5, 20, 0, 15);
        gbc.gridy = y; y++;
        this.add(epDescText, gbc);

        gbc.insets = new Insets(15, 20, 0, 10);
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = y; y++;
        this.add(epMonitor, gbc);

        gbc.insets = new Insets(5, 20, 0, 10);
        gbc.gridy = y; y--;
        this.add(epMonitorCombo, gbc);

        gbc.insets = new Insets(15, 20, 0, 10);
        gbc.gridx = 1;
        gbc.gridy = y; y++;
        this.add(epObjetivo, gbc);

        gbc.insets = new Insets(5, 20, 0, 10);
        gbc.gridy = y; y--;
        this.add(epObjCombo, gbc);

        gbc.gridy = y; y++;
        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        this.add(epNewObjLabel, gbc);

        gbc.gridy = y; y++;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        this.add(epNewObjText, gbc);

        gbc.gridy = y; y++;
        gbc.gridwidth = 1;
        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        if (flag == 0)
            this.add(nuevoObj, gbc);
        else
            nuevoObj.setVisible(true);

        // Crear Entreno Monitor
        gbc.insets = gbc.insets = new Insets(15, 20, 0, 10);
        gbc.gridx = 0;
        gbc.gridy = y; y++;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        this.add(epLabelCrearEntMoni, gbc);

        gbc.insets = new Insets(5, 5, 0, 5);
        gbc.gridy = y; y++;
        gbc.gridwidth = 1;
        this.add(epNombreEntrenoMoniLabel, gbc);

        gbc.gridy = y; y--;
        this.add(epNombreEntrenoMoni, gbc);

        gbc.insets = new Insets(5, 5, 0, 15);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        gbc.gridy = y; y++;
        this.add(epDescEntrenoMoniLabel, gbc);

        gbc.gridy = y; y++;
        this.add(epDescEntrenoMoni, gbc);

        gbc.insets = new Insets(5, 5, 0, 5);
        gbc.gridy = y; y++;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        this.add(epFechaEntrenoMoniLabel, gbc);

        gbc.gridy = y; y--;
        this.add(epFechaEntrenoMoni, gbc);

        gbc.gridy = y; y++;
        gbc.gridx = 1;
        gbc.gridwidth = 1;
        this.add(epHoraInicialLabel, gbc);

        gbc.gridy = y; y--;
        this.add(epHoraInicial, gbc);

        gbc.gridy = y; y++;
        gbc.gridx = 2;
        gbc.gridwidth = 1;
        this.add(epHoraFinalLabel, gbc);

        gbc.gridy = y; y++;
        this.add(epHoraFinal, gbc);

        gbc.gridx = 0;
        gbc.gridy = y; y++;
        gbc.gridwidth = 2;
        this.add(salasCombo, gbc);

        gbc.gridx = 2;
        gbc.gridwidth = 1;
        if (flag == 0)
            this.add(epCrearEntrenoMoni, gbc);
        else
            epCrearEntrenoMoni.setVisible(true);

        // Seleccion de actividades
        gbc.insets = new Insets(15, 20, 0, 10);
        gbc.gridx = 0;
        gbc.gridy = y; y++;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        this.add(seleccionActivLabel, gbc);

        gbc.insets = new Insets(5, 20, 0, 10);
        gbc.gridy = y;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridwidth = 2;
        this.add(actividadesCombo, gbc);

        gbc.gridx = 2;
        gbc.gridwidth = 1;
        gbc.gridy = y; y++;
        if (flag == 0)
            this.add(addActividad, gbc);
        else
            addActividad.setVisible(true);

        // Botones crear y volver
        gbc.insets = new Insets(50, 0, 0, 0);
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridwidth = 1;
        if (flag == 0)
            this.add(cepCrear, gbc);
        else
            cepCrear.setVisible(true);

        gbc.gridy = y; y++;
        gbc.gridx = 2;
        if (flag == 0)
            this.add(cepVolver, gbc);
        else
            cepVolver.setVisible(true);

        // Lista de actividades seleccionadas
        gbc.gridx = 0;
        gbc.gridy = y; y++;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(20, 20, 0, 10);
        this.add(listaActLabel, gbc);

        numActSelecInit = y;
        numActSelec = y;
    }

    public void ocultarCrearEntPersClases() {
        flag = 1;
        // Ocultar los botones
        cepCrear.setVisible(false);
        cepVolver.setVisible(false);
        nuevoObj.setVisible(false);
        addActividad.setVisible(false);
        epCrearEntrenoMoni.setVisible(false);

        // Eliminaar todo menos botones
        this.remove(epNombre);
        this.remove(epNombreText);
        this.remove(epDescripcion);
        this.remove(epDescText);
        this.remove(epMonitor);
        this.remove(epMonitorCombo);
        this.remove(epObjetivo);
        this.remove(epObjCombo);
        this.remove(epNewObjLabel);
        this.remove(epNewObjText);
        this.remove(epLabelCrearEntMoni);
        this.remove(epNombreEntrenoMoniLabel);
        this.remove(epDescEntrenoMoniLabel);
        this.remove(epHoraInicialLabel);
        this.remove(epHoraFinalLabel);
        this.remove(epFechaEntrenoMoniLabel);

        this.remove(epNombreEntrenoMoni);
        this.remove(epDescEntrenoMoni);
        this.remove(epHoraInicial);
        this.remove(epHoraFinal);
        this.remove(epFechaEntrenoMoni);
        this.remove(salasCombo);

        this.remove(seleccionActivLabel);
        this.remove(actividadesCombo);
        this.remove(listaActLabel);

        for (JLabel label : epActividadesSeleccionadas.values()) {
            this.remove(label);
        }
        epActividadesSeleccionadas.clear();

        this.remove(sinActSelecLabel);

        this.revalidate();
        this.repaint();
    }

    @Override
    public void clearServicios() {
        super.clearServicios();
        if (flag == 1) {
            ocultarCrearEntPersClases();
        }
        //this.ocultarCrearEntPersClases();
        this.mostrarClases();
        this.revalidate();
        this.repaint();
    }
}
