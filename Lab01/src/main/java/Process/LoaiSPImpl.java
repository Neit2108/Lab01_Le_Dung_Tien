package Process;

import Database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoaiSPImpl implements LoaiSPInterface{
    private final Connection connection;

    public LoaiSPImpl() throws SQLException {
        this.connection = DatabaseConnection.getConnection();
        this.connection.setAutoCommit(false);
    }

    @Override
    public boolean addLoaiSP(String maLoai, String tenLoai) {
        String query = "insert into LoaiSP(maLoai,tenLoai) values(?,?)";
        int rowsAffected = 0;
        try(PreparedStatement pstm = connection.prepareStatement(query)){
            pstm.setString(1, maLoai);
            pstm.setString(2, tenLoai);
            rowsAffected = pstm.executeUpdate();
            this.connection.commit();

            return rowsAffected > 0;
        }
        catch(SQLException e){
            try{
                this.connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<LoaiSP> getAllLoaiSP() {
        List<LoaiSP> loaisp = new ArrayList<>();
        String query = "SELECT * FROM LoaiSP";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                LoaiSP lsp = new LoaiSP();
                lsp.setMaLoai(resultSet.getString("maLoai"));
                lsp.setTenLoaiSP(resultSet.getString("tenLoai"));
                loaisp.add(lsp);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return loaisp;
    }

    @Override
    public LoaiSP getLoaiSP(String maLoai) {
        String query = "SELECT * FROM LoaiSP WHERE maLoai = ?";
        LoaiSP lsp = new LoaiSP();
        try {
            PreparedStatement psmt = connection.prepareStatement(query);
            psmt.setString(1, maLoai);
            ResultSet rs = psmt.executeQuery();
            if(rs.next()) {
                lsp.setMaLoai(rs.getString("maLoai"));
                lsp.setTenLoaiSP(rs.getString("tenLoai"));
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lsp;
    }

    @Override
    public boolean deleteLoaiSP(String maLoai) {
        int rowsAffected = 0;
        String query = "DELETE FROM LoaiSP WHERE maLoai = ?";
        try(PreparedStatement pstm = connection.prepareStatement(query)){
            pstm.setString(1, maLoai);
            rowsAffected = pstm.executeUpdate();
            this.connection.commit();

            return rowsAffected > 0;
        }
        catch(SQLException e){
            try{
                this.connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public void updateLoaiSP(String maLoai, String tenLoai) {
        String query = "update LoaiSP set tenLoai = ? where maLoai = ?";
        try{
            PreparedStatement pstm = connection.prepareStatement(query);
            pstm.setString(1, tenLoai);
            pstm.setString(2, maLoai);
            pstm.executeUpdate();
            this.connection.commit();
        }
        catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
}
