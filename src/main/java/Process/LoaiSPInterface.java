package Process;

import java.util.List;

public interface LoaiSPInterface {
    boolean addLoaiSP(String maLoai, String tenLoai);
    List<LoaiSP> getAllLoaiSP();
    LoaiSP getLoaiSP(String maLoai);
    boolean deleteLoaiSP(String maLoai);
    void updateLoaiSP(String maLoai, String tenLoai);
}
