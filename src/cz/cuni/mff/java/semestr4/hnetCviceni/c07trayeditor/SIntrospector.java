package cz.cuni.mff.java.semestr4.hnetCviceni.c07trayeditor;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.*;

public class SIntrospector {

    private static void createAndShowGUI() {
        final JFrame frame = new JFrame("Introspector");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container pane = frame.getContentPane();

        final IntroTableModel model = new IntroTableModel();
        JTable table = new JTable(model);
        pane.add(new JScrollPane(table));

        table.getColumnModel().getColumn(1).setCellRenderer(new MyTableCellRenderer());

        JTextField field = new JTextField();
        field.addActionListener(ev -> {
            String className = ev.getActionCommand();
            try {
                Class<?> clazz = Class.forName(className);
                model.setClassToIntrospect(clazz);
            } catch (ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(frame, "Class " + className + " cannot be loaded!", "Exception", JOptionPane.ERROR_MESSAGE);
            }
        });


        field.setToolTipText("Input a class name");
        field.setText("java.lang.Object");

        pane.add(field, BorderLayout.NORTH);

        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SIntrospector::createAndShowGUI);
    }

    private static class IntroTableModel extends AbstractTableModel {
        private Class<?> clazz = Object.class;

        public void setClassToIntrospect(Class<?> clazz) {
            this.clazz = clazz;
            fireTableDataChanged();
        }

        @Override
        public int getRowCount() {
            return 8;
        }

        @Override
        public int getColumnCount() {
            return 2;
        }

        @Override
        public String getColumnName(int column) {
            return column == 0 ? "Element" : "Value";
        }

        @Override
        public Object getValueAt(int row, int column) {
            switch (row) {
                case 0:
                    return column == 0 ? "Name" : clazz.getName();
                case 1:
                    return column == 0 ? "Primitive" : clazz.isPrimitive();
                case 2:
                    return column == 0 ? "Array" : clazz.isArray();
                case 3:
                    return column == 0 ? "Interface" : clazz.isInterface();
                case 4:
                    return column == 0 ? "Package" : clazz.getPackage();
                case 5:
                    return column == 0 ? "Superclass" : clazz.getSuperclass() == null ? "" : clazz.getSuperclass().getName();
                case 6:
                    return column == 0 ? "Enum" : clazz.isEnum();
                case 7:
                    return column == 0 ? "Annotation" : clazz.isAnnotation();
            }
            return null;
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return super.getColumnClass(columnIndex);
        }
    }

    private static class MyTableCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (value instanceof Boolean) {
                JCheckBox box = new JCheckBox();
                box.setEnabled(false);
                box.setSelected((Boolean) value);
                return box;
            } else if (value instanceof String) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (((String) value).isEmpty()) {
                    c.setBackground(Color.RED);
                } else {
                    c.setBackground(Color.WHITE);
                }
            }
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }
}
