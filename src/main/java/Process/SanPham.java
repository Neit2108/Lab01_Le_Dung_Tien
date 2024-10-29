package Process;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SanPham {
    private String maSP;
    private String tenSanPham;
    private int donGia;
    private String maLoai;

    @Override
    public String toString() {
        return "SanPham [maSP=" + maSP + ", tenSanPham=" + tenSanPham + ", donGia=" + donGia + ", maLoai=" + maLoai + "]";
    }
}
