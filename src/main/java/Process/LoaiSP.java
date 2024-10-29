package Process;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoaiSP {
    private String maLoai;
    private String tenLoaiSP;

    @Override
    public String toString() {
        return "LoaiSP [maSP=" + maLoai + ", tenSP=" + tenLoaiSP + "]";
    }
}
