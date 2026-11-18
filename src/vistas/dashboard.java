package vistas;

import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.RoundRectangle2D;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import modelos.DatosDashboard;

/**
 * Dashboard de la agencia: tarjetas de resumen, graficas y tablas.
 * Es un JPanel que se muestra dentro del contenedor de "principal".
 */
public class dashboard extends JPanel {

    // Paleta
    private static final Color FONDO = new Color(0xF4F1F8);
    private static final Color TARJETA = Color.WHITE;
    private static final Color MORADO = new Color(0x660066);
    private static final Color TEXTO = new Color(0x2B2438);
    private static final Color TENUE = new Color(0x8A8498);
    private static final Color LINEA = new Color(0xE8E3EF);
    static final Color ESPERA = new Color(0xF59E0B);
    static final Color PROCESO = new Color(0x3B82F6);
    static final Color FINAL = new Color(0x10B981);

    private final JButton btnActualizar = new JButton("Actualizar");
    private final JLabel lblFecha = new JLabel();

    private final Tarjeta kTotal = new Tarjeta("Servicios totales", MORADO);
    private final Tarjeta kEspera = new Tarjeta("En espera", ESPERA);
    private final Tarjeta kProceso = new Tarjeta("En proceso", PROCESO);
    private final Tarjeta kFinal = new Tarjeta("Finalizados", FINAL);
    private final Tarjeta kClientes = new Tarjeta("Clientes", new Color(0xEC4899));
    private final Tarjeta kAutos = new Tarjeta("Autos", new Color(0x14B8A6));
    private final Tarjeta kEmpleados = new Tarjeta("Empleados", new Color(0x8B5CF6));
    private final Tarjeta kIngresos = new Tarjeta("Refacciones vendidas", new Color(0xEF4444));

    private final GraficaBarras barras = new GraficaBarras();
    private final GraficaDona dona = new GraficaDona();
    private final ListaTop top = new ListaTop();

    private final DefaultTableModel modeloRecientes = modeloTabla("Folio", "Cliente", "Auto", "Estatus", "Ingreso");
    private final DefaultTableModel modeloProximos = modeloTabla("Folio", "Cliente", "Auto", "Fecha");

    public dashboard() {
        setBackground(FONDO);
        setLayout(new BorderLayout(0, 16));
        setBorder(new EmptyBorder(20, 24, 20, 24));
        setPreferredSize(new Dimension(1040, 700));

        add(crearEncabezado(), BorderLayout.NORTH);

        JPanel centro = new JPanel(new GridBagLayout());
        centro.setOpaque(false);
        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.BOTH;
        g.insets = new Insets(0, 0, 16, 16);

        // Fila 1: 4 tarjetas de estado
        Tarjeta[] fila1 = {kTotal, kEspera, kProceso, kFinal};
        for (int i = 0; i < 4; i++) {
            g.gridx = i * 3; g.gridy = 0; g.gridwidth = 3; g.weightx = 1; g.weighty = 0;
            g.insets = new Insets(0, 0, 16, i == 3 ? 0 : 16);
            centro.add(fila1[i], g);
        }
        // Fila 2: 4 tarjetas de catalogos
        Tarjeta[] fila2 = {kClientes, kAutos, kEmpleados, kIngresos};
        for (int i = 0; i < 4; i++) {
            g.gridx = i * 3; g.gridy = 1; g.gridwidth = 3; g.weightx = 1; g.weighty = 0;
            g.insets = new Insets(0, 0, 16, i == 3 ? 0 : 16);
            centro.add(fila2[i], g);
        }
        // Fila 3: barras (6) + dona (3) + top (3)
        g.gridy = 2; g.weighty = 0.5;
        g.gridx = 0; g.gridwidth = 6; g.insets = new Insets(0, 0, 16, 16);
        centro.add(panel("Servicios por mes", barras), g);
        g.gridx = 6; g.gridwidth = 3;
        centro.add(panel("Estado de servicios", dona), g);
        g.gridx = 9; g.gridwidth = 3; g.insets = new Insets(0, 0, 16, 0);
        centro.add(panel("Refacciones más usadas", top), g);
        // Fila 4: tablas
        g.gridy = 3; g.weighty = 0.5;
        g.gridx = 0; g.gridwidth = 8; g.insets = new Insets(0, 0, 0, 16);
        centro.add(panel("Servicios recientes", tabla(modeloRecientes, 3)), g);
        g.gridx = 8; g.gridwidth = 4; g.insets = new Insets(0, 0, 0, 0);
        centro.add(panel("Próximos servicios", tabla(modeloProximos, -1)), g);

        // Fila espaciadora: 12 columnas iguales para que los anchos sean exactos.
        g.gridy = 4; g.gridwidth = 1; g.weighty = 0; g.weightx = 1; g.insets = new Insets(0, 0, 0, 0);
        for (int i = 0; i < 12; i++) {
            g.gridx = i;
            centro.add(Box.createHorizontalStrut(0), g);
        }

        add(centro, BorderLayout.CENTER);
        mostrar(new DatosDashboard());
    }

    // ---------- API para el controlador ----------

    public JButton getBtnActualizar() {
        return btnActualizar;
    }

    public void mostrar(DatosDashboard d) {
        NumberFormat money = NumberFormat.getCurrencyInstance(Locale.US);
        kTotal.set(String.valueOf(d.totalServicios));
        kEspera.set(String.valueOf(d.enEspera));
        kProceso.set(String.valueOf(d.enProceso));
        kFinal.set(String.valueOf(d.finalizados));
        kClientes.set(String.valueOf(d.clientes));
        kAutos.set(String.valueOf(d.autos));
        kEmpleados.set(String.valueOf(d.empleados));
        kIngresos.set(money.format(d.ingresosRefacciones));

        barras.setDatos(d.serviciosPorMes);
        dona.setDatos(d.enEspera, d.enProceso, d.finalizados);
        top.setDatos(d.topRefacciones);

        modeloRecientes.setRowCount(0);
        for (String[] f : d.recientes) modeloRecientes.addRow(f);
        modeloProximos.setRowCount(0);
        for (String[] f : d.proximos) modeloProximos.addRow(f);

        LocalDate hoy = LocalDate.now();
        lblFecha.setText(hoy.getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("es", "MX")) + ", "
                + hoy.getDayOfMonth() + " de " + hoy.getMonth().getDisplayName(TextStyle.FULL, new Locale("es", "MX"))
                + " de " + hoy.getYear());
        repaint();
    }

    // ---------- Construccion de la UI ----------

    private JComponent crearEncabezado() {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);

        JPanel izq = new JPanel();
        izq.setOpaque(false);
        izq.setLayout(new BoxLayout(izq, BoxLayout.Y_AXIS));
        JLabel titulo = new JLabel("Dashboard");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titulo.setForeground(TEXTO);
        lblFecha.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblFecha.setForeground(TENUE);
        izq.add(titulo);
        izq.add(lblFecha);
        p.add(izq, BorderLayout.WEST);

        btnActualizar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnActualizar.setForeground(Color.WHITE);
        btnActualizar.setBackground(MORADO);
        btnActualizar.setFocusPainted(false);
        btnActualizar.setBorder(new EmptyBorder(10, 22, 10, 22));
        btnActualizar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnActualizar.setContentAreaFilled(false);
        btnActualizar.setOpaque(false);
        btnActualizar.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(((AbstractButton) c).getModel().isRollover() ? MORADO.brighter() : MORADO);
                g2.fill(new RoundRectangle2D.Float(0, 0, c.getWidth(), c.getHeight(), 20, 20));
                g2.dispose();
                super.paint(g, c);
            }
        });
        btnActualizar.setRolloverEnabled(true);
        JPanel der = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 6));
        der.setOpaque(false);
        der.add(btnActualizar);
        p.add(der, BorderLayout.EAST);
        return p;
    }

    private JComponent panel(String titulo, JComponent contenido) {
        JPanel p = new Redondeado(18, TARJETA);
        p.setLayout(new BorderLayout(0, 8));
        p.setBorder(new EmptyBorder(14, 18, 14, 18));
        JLabel t = new JLabel(titulo);
        t.setFont(new Font("Segoe UI", Font.BOLD, 15));
        t.setForeground(TEXTO);
        p.add(t, BorderLayout.NORTH);
        p.add(contenido, BorderLayout.CENTER);
        return p;
    }

    private static DefaultTableModel modeloTabla(String... cols) {
        return new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
    }

    private JComponent tabla(DefaultTableModel m, int colEstatus) {
        JTable t = new JTable(m);
        t.setRowHeight(30);
        t.setShowGrid(false);
        t.setIntercellSpacing(new Dimension(0, 0));
        t.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        t.setForeground(TEXTO);
        t.setSelectionBackground(new Color(0xF1E6F4));
        t.setSelectionForeground(TEXTO);
        t.setFillsViewportHeight(true);
        t.setBackground(TARJETA);

        JTableHeader h = t.getTableHeader();
        h.setReorderingAllowed(false);
        h.setPreferredSize(new Dimension(0, 32));
        h.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable tb, Object v, boolean s, boolean f, int r, int c) {
                JLabel l = (JLabel) super.getTableCellRendererComponent(tb, v, false, false, r, c);
                l.setFont(new Font("Segoe UI", Font.BOLD, 12));
                l.setForeground(TENUE);
                l.setBackground(TARJETA);
                l.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(0, 0, 1, 0, LINEA), new EmptyBorder(0, 6, 0, 6)));
                return l;
            }
        });

        DefaultTableCellRenderer celda = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable tb, Object v, boolean s, boolean f, int r, int c) {
                JLabel l = (JLabel) super.getTableCellRendererComponent(tb, v, s, false, r, c);
                l.setBorder(new EmptyBorder(0, 6, 0, 6));
                if (c == colEstatus && v != null) {
                    l.setText("● " + v);
                    l.setForeground(colorEstatus(v.toString()));
                    l.setFont(l.getFont().deriveFont(Font.BOLD));
                } else {
                    l.setForeground(TEXTO);
                }
                return l;
            }
        };
        t.setDefaultRenderer(Object.class, celda);

        t.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        int[] anchos = colEstatus >= 0 ? new int[]{70, 130, 130, 110, 90} : new int[]{70, 110, 110, 90};
        for (int i = 0; i < anchos.length && i < t.getColumnCount(); i++) {
            t.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        JScrollPane sp = new JScrollPane(t);
        sp.setBorder(null);
        sp.getViewport().setBackground(TARJETA);
        return sp;
    }

    static Color colorEstatus(String e) {
        switch (e.toLowerCase()) {
            case "en proceso": return PROCESO;
            case "finalizado": return FINAL;
            default: return ESPERA;
        }
    }

    // ---------- Componentes propios ----------

    /** Panel blanco con esquinas redondeadas y sombra suave. */
    private static class Redondeado extends JPanel {
        private final int r;
        private final Color fondo;

        Redondeado(int r, Color fondo) {
            this.r = r;
            this.fondo = fondo;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(60, 20, 80, 14));
            g2.fill(new RoundRectangle2D.Float(1, 3, getWidth() - 2, getHeight() - 3, r, r));
            g2.setColor(fondo);
            g2.fill(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 3, r, r));
            g2.dispose();
            super.paintComponent(g);
        }
    }

    /** Tarjeta KPI con barra de color lateral. */
    private static class Tarjeta extends Redondeado {
        private final JLabel valor = new JLabel("0");
        private final Color acento;

        Tarjeta(String titulo, Color acento) {
            super(18, TARJETA);
            this.acento = acento;
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBorder(new EmptyBorder(14, 24, 14, 14));
            JLabel t = new JLabel(titulo);
            t.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            t.setForeground(TENUE);
            valor.setFont(new Font("Segoe UI", Font.BOLD, 28));
            valor.setForeground(TEXTO);
            add(t);
            add(Box.createVerticalStrut(2));
            add(valor);
            setPreferredSize(new Dimension(150, 84));
        }

        void set(String v) {
            valor.setText(v);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(acento);
            g2.fill(new RoundRectangle2D.Float(10, 16, 5, getHeight() - 35, 5, 5));
            g2.dispose();
        }
    }

    private static class GraficaBarras extends JComponent {
        private Map<String, Integer> datos = new java.util.LinkedHashMap<>();

        void setDatos(Map<String, Integer> d) {
            datos = d;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int w = getWidth(), h = getHeight();
            if (datos.isEmpty()) {
                vacio(g2, w, h);
                g2.dispose();
                return;
            }
            int max = Math.max(1, datos.values().stream().mapToInt(Integer::intValue).max().orElse(1));
            int izq = 28, abajo = 24, arriba = 16;
            int alto = h - abajo - arriba;
            g2.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            for (int i = 0; i <= 4; i++) {
                int y = arriba + alto - alto * i / 4;
                g2.setColor(LINEA);
                g2.drawLine(izq, y, w, y);
                g2.setColor(TENUE);
                g2.drawString(String.valueOf(Math.round(max * i / 4.0)), 0, y + 4);
            }
            int n = datos.size();
            int slot = (w - izq) / n;
            int bw = Math.min(46, slot - 18);
            int i = 0;
            for (Map.Entry<String, Integer> e : datos.entrySet()) {
                int bh = (int) (alto * (e.getValue() / (double) max));
                int x = izq + i * slot + (slot - bw) / 2;
                int y = arriba + alto - bh;
                g2.setPaint(new GradientPaint(0, y, new Color(0x9B30A8), 0, y + bh, MORADO));
                g2.fill(new RoundRectangle2D.Float(x, y, bw, Math.max(bh, 3), 10, 10));
                g2.setColor(TEXTO);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 12));
                String v = String.valueOf(e.getValue());
                g2.drawString(v, x + (bw - g2.getFontMetrics().stringWidth(v)) / 2, y - 4);
                g2.setColor(TENUE);
                g2.setFont(new Font("Segoe UI", Font.PLAIN, 11));
                String mes = e.getKey();
                try {
                    mes = java.time.YearMonth.parse(e.getKey()).getMonth()
                            .getDisplayName(TextStyle.SHORT, new Locale("es", "MX"));
                } catch (Exception ignorar) {
                }
                g2.drawString(mes, x + (bw - g2.getFontMetrics().stringWidth(mes)) / 2, h - 6);
                i++;
            }
            g2.dispose();
        }
    }

    private static class GraficaDona extends JComponent {
        private int a, b, c;

        void setDatos(int espera, int proceso, int fin) {
            a = espera; b = proceso; c = fin;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int w = getWidth(), h = getHeight();
            int total = a + b + c;
            int legendH = 22;
            int d = Math.min(w, h - legendH) - 10;
            if (total == 0 || d < 20) {
                vacio(g2, w, h);
                g2.dispose();
                return;
            }
            int x = (w - d) / 2, y = 4;
            Color[] cols = {ESPERA, PROCESO, FINAL};
            int[] vals = {a, b, c};
            double ang = 90;
            g2.setStroke(new BasicStroke(d * 0.2f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND));
            int inset = (int) (d * 0.1);
            for (int i = 0; i < 3; i++) {
                if (vals[i] == 0) continue;
                double ext = -360.0 * vals[i] / total;
                g2.setColor(cols[i]);
                g2.draw(new Arc2D.Double(x + inset, y + inset, d - 2 * inset, d - 2 * inset, ang, ext, Arc2D.OPEN));
                ang += ext;
            }
            g2.setColor(TEXTO);
            g2.setFont(new Font("Segoe UI", Font.BOLD, 24));
            String t = String.valueOf(total);
            g2.drawString(t, x + (d - g2.getFontMetrics().stringWidth(t)) / 2, y + d / 2 + 4);
            // leyenda
            String[] nombres = {"Espera", "Proceso", "Final."};
            g2.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            FontMetrics fm = g2.getFontMetrics();
            int anchoTotal = 0;
            for (String nm : nombres) anchoTotal += 14 + fm.stringWidth(nm) + 10;
            int lx = (w - anchoTotal) / 2, ly = h - 6;
            for (int i = 0; i < 3; i++) {
                g2.setColor(cols[i]);
                g2.fillOval(lx, ly - 9, 9, 9);
                g2.setColor(TENUE);
                g2.drawString(nombres[i], lx + 13, ly);
                lx += 14 + fm.stringWidth(nombres[i]) + 10;
            }
            g2.dispose();
        }
    }

    private static class ListaTop extends JComponent {
        private Map<String, Integer> datos = new java.util.LinkedHashMap<>();

        void setDatos(Map<String, Integer> d) {
            datos = d;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int w = getWidth(), h = getHeight();
            if (datos.isEmpty()) {
                vacio(g2, w, h);
                g2.dispose();
                return;
            }
            int max = datos.values().stream().mapToInt(Integer::intValue).max().orElse(1);
            int fila = Math.min(44, h / Math.max(1, datos.size()));
            int y = 0;
            for (Map.Entry<String, Integer> e : datos.entrySet()) {
                g2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                g2.setColor(TEXTO);
                String nombre = e.getKey();
                FontMetrics fm = g2.getFontMetrics();
                while (fm.stringWidth(nombre) > w - 40 && nombre.length() > 3) {
                    nombre = nombre.substring(0, nombre.length() - 2);
                }
                g2.drawString(nombre, 0, y + 14);
                String v = e.getValue() + "x";
                g2.setColor(TENUE);
                g2.drawString(v, w - fm.stringWidth(v), y + 14);
                g2.setColor(LINEA);
                g2.fill(new RoundRectangle2D.Float(0, y + 20, w, 8, 8, 8));
                g2.setColor(MORADO);
                g2.fill(new RoundRectangle2D.Float(0, y + 20, Math.max(8, w * e.getValue() / (float) max), 8, 8, 8));
                y += fila;
            }
            g2.dispose();
        }
    }

    private static void vacio(Graphics2D g2, int w, int h) {
        g2.setColor(TENUE);
        g2.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        String s = "Sin datos todavía";
        g2.drawString(s, (w - g2.getFontMetrics().stringWidth(s)) / 2, h / 2);
    }
}
