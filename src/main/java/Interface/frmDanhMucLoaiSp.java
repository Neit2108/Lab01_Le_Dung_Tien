package Interface;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import Database.DatabaseConnection;
import Process.*;
public class frmDanhMucLoaiSp extends javax.swing.JFrame {
    private JTextField textField1;
    private JTextField textField2;
    private JButton addBtn;
    private JButton cancelBtn;
    private JButton deleteBtn;
    private JButton fixBtn;
    private JButton saveBtn;
    private JButton notsaveBtn;
    private JPanel listBtn;
    private JPanel contentPane;
    private JTable tblSanPham;
    private JScrollPane scrollPane;
    private final DefaultTableModel dtm = (DefaultTableModel) tblSanPham.getModel();
    private List<LoaiSP> addProduct = new ArrayList<LoaiSP>();
    private List<LoaiSP> deleteProduct = new ArrayList<LoaiSP>();

    private void setNull(){
        textField1.setText(null);
        textField2.setText(null);
        textField1.requestFocus();
    }
    public void showData() throws SQLException {
        LoaiSPImpl lsp = new LoaiSPImpl();
        List<LoaiSP> listlsp = lsp.getAllLoaiSP();
        try{
            for (LoaiSP x : listlsp) {
                String[] rows = new String[]{x.getMaLoai(), x.getTenLoaiSP()};
                dtm.addRow(rows);
            }
        }
        catch(Exception e){

        }
    }


    public frmDanhMucLoaiSp() throws SQLException {
        setContentPane(contentPane);
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        dtm.setColumnIdentifiers(new String[]{"Mã sản phẩm", "Tên sản phẩm"});
        showData();
        tblSanPham.setBorder(BorderFactory.createLineBorder(Color.black));

        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ma = textField1.getText();
                String ten = textField2.getText();

                if(ma.isEmpty() || ten.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Vui long nhap day du");
                    return;
                }
                addProduct.add(new LoaiSP(ma, ten));
                dtm.addRow(new String[]{ma, ten});
                setNull();
            }
        });
        deleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ma = textField1.getText();
                boolean found = false;
                for (int i = 0; i < addProduct.size(); i++) {
                    LoaiSP x = addProduct.get(i);
                    if (ma.equals(x.getMaLoai())) {
                        addProduct.remove(i);
                        found = true;
                        break;
                    }
                }
                if (found) {
                    for(int i = 0; i < dtm.getRowCount(); i++){
                        if(ma.equals(dtm.getValueAt(i,0).toString())){
                            dtm.removeRow(i);
                            break;
                        }
                    }
                    deleteProduct.add(new LoaiSP(ma, null));
                    JOptionPane.showMessageDialog(null, "da xoa");
                }
                setNull();
            }
        });
        tblSanPham.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tblSanPham.getSelectedRow();
                if(row != -1){
                    String txt = tblSanPham.getModel().getValueAt(row, 0).toString();
                    LoaiSP obj = null;
                    for(LoaiSP x : addProduct){
                        if(txt.equals(x.getMaLoai())){
                            obj = x;
                            break;
                        }
                    }
                    if(obj == null){
                        try{
                            LoaiSPImpl lsp = new LoaiSPImpl();
                            obj = lsp.getLoaiSP(txt);
                        }
                        catch (SQLException e1){
                            e1.printStackTrace();
                        }
                    }
                    if(obj != null){
                        textField1.setText(obj.getMaLoai());
                        textField2.setText(obj.getTenLoaiSP());
                    }
                }

            }
        });
        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    LoaiSPImpl lsp = new LoaiSPImpl();
                    boolean allSave = true;

                    //Xoa cac ban ghi trong delete product
                    for(LoaiSP x : deleteProduct){
                        boolean rowsAffected = lsp.deleteLoaiSP(x.getMaLoai());
                        if(!rowsAffected){
                            allSave = false;
                            break;
                        }
                    }
                    if(allSave || deleteProduct.isEmpty()){
                        for(LoaiSP x : addProduct){
                            boolean rowsAffected = lsp.addLoaiSP(x.getMaLoai(), x.getTenLoaiSP());
                            if(!rowsAffected){
                                allSave = false;
                            }
                        }
                    }

                    if(allSave){
                        dtm.setRowCount(0);
                        showData();
                        setNull();
                        addProduct.clear();
                        deleteProduct.clear();
                        JOptionPane.showMessageDialog(null, "luu thanh cong");
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "luu 0 thanh cong");
                        
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Luu that bai");
                }
            }
        });
    }

    public static void main(String[] args) throws SQLException {
        frmDanhMucLoaiSp frm = new frmDanhMucLoaiSp();
        frm.setVisible(true);
    }
}

