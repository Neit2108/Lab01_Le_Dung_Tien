package Interface;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    private List<LoaiSP> tmpProduct = new ArrayList<LoaiSP>();
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
                tmpProduct.add(new LoaiSP(ma, ten));
                dtm.addRow(new String[]{ma, ten});
                setNull();
            }
        });
        deleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String txt = textField1.getText();
                try {
                    LoaiSPImpl lsp = new LoaiSPImpl();
                    if(JOptionPane.showConfirmDialog(null, "Ban co muon xoa khong") == 0){
                        boolean rowsAffected = lsp.deleteLoaiSP(txt);
                        if(rowsAffected){
                            dtm.setRowCount(0);
                            showData();
                            setNull();
                            JOptionPane.showMessageDialog(null, "xoa thanh cong");
                        }
                        else {
                            JOptionPane.showMessageDialog(null, "xoa khong thanh cong");
                        }
                    }
                }
                catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "xoa khong thanh cong");
                }

            }
        });
        tblSanPham.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try{
                    LoaiSPImpl lsp = new LoaiSPImpl();
                    int row = tblSanPham.getSelectedRow();
                    String txt = tblSanPham.getModel().getValueAt(row, 0).toString();
                    LoaiSP obj = lsp.getLoaiSP(txt);
                    if(obj != null){
                        textField1.setText(obj.getMaLoai());
                        textField2.setText(obj.getTenLoaiSP());
                    }
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    LoaiSPImpl lsp = new LoaiSPImpl();
                    boolean allSave = true;
                    for(LoaiSP x : tmpProduct){
                        boolean rowsAffected = lsp.addLoaiSP(x.getMaLoai(), x.getTenLoaiSP());
                        if(!rowsAffected){
                            allSave = false;
                            break;
                        }
                    }

                    if(allSave){
                        dtm.setRowCount(0);
                        showData();
                        setNull();
                        tmpProduct.clear();
                        JOptionPane.showMessageDialog(null, "luu thanh cong");
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "luu 0 thanh cong");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "them that bai");
                }
            }
        });
    }

    public static void main(String[] args) throws SQLException {
        frmDanhMucLoaiSp frm = new frmDanhMucLoaiSp();
        frm.setVisible(true);
    }
}
