package Process;

import java.util.List;

public interface SanPhamInterface {
    void addSanPham(String maSP, String tenSP, int donGia, String maLoai);
    List<SanPham> getSanPham();
    SanPham getSanPham(String maSP);
    void deleteSanPham(String maSP, String tenSP, int donGia);
    void updateSanPham(String maSP, String tenSP, int donGia);
}
