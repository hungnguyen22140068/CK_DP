package Persistance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import Domain.Observer.Observer;
import Domain.Observer.Subject;

public class DSGDNha_DAO extends KetNoiCSDL_DAO implements Subject {
    private List<Observer> observers = new LinkedList<>();

    // hiển thị dữ liệu lên bảng
    public ArrayList<Object[]> loadDataFromDatabase() {
        ArrayList<Object[]> dataList = new ArrayList<>();
        try (Connection conn = getConnection()) { // Use inherited method to get the connection
            String query = "SELECT MaGiaoDich, LoaiNha, DienTich, DonGia, TongTien FROM GiaoDichNha";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            // Get metadata
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Create data list
            while (rs.next()) {
                Object[] row = new Object[columnCount];
                for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                    row[columnIndex - 1] = rs.getObject(columnIndex);
                }
                dataList.add(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    // phương thức để chèn dữ liệu vào cơ sở dữ liệu
    public boolean insertData(String loaiNha, double dienTich, double donGia, double tongTien) {
        String query = "INSERT INTO GiaoDichNha (LoaiNha, DienTich, DonGia, TongTien) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, loaiNha);
            pstmt.setDouble(2, dienTich);
            pstmt.setDouble(3, donGia);
            pstmt.setDouble(4, tongTien);

            int affectedRows = pstmt.executeUpdate();
            notifyObservers();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // phương thức để xóa dữ liệu từ cơ sở dữ liệu
    public boolean deleteData(int maGiaoDich) {
        String query = "DELETE FROM GiaoDichNha WHERE MaGiaoDich = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, maGiaoDich);

            int affectedRows = pstmt.executeUpdate();
            notifyObservers();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Phương thức cập nhật dữ liệu
    public boolean updateData(int maGiaoDich, String loaiNha, double dienTich, double donGia, double tongTien) {
        String query = "UPDATE GiaoDichNha SET LoaiNha = ?, DienTich = ?, DonGia = ?, TongTien = ? WHERE MaGiaoDich = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, loaiNha);
            pstmt.setDouble(2, dienTich);
            pstmt.setDouble(3, donGia);
            pstmt.setDouble(4, tongTien);
            pstmt.setInt(5, maGiaoDich);

            int affectedRows = pstmt.executeUpdate();
            notifyObservers();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Tìm kiếm dữ liệu theo mã giao dịch
    public ArrayList<Object[]> searchByMaGiaoDich(String maGiaoDich) {
        ArrayList<Object[]> dataList = new ArrayList<>();
        String query = "SELECT MaGiaoDich, LoaiNha, DienTich, DonGia, TongTien FROM GiaoDichNha WHERE MaGiaoDich = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, maGiaoDich);

            ResultSet rs = pstmt.executeQuery();

            // Get metadata
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Create data list
            while (rs.next()) {
                Object[] row = new Object[columnCount];
                for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                    row[columnIndex - 1] = rs.getObject(columnIndex);
                }
                dataList.add(row);
            }
            notifyObservers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    // giao diện hiển thị
    public class CustomTableModel extends AbstractTableModel {
        private ArrayList<Object[]> data;
        private String[] columnNames = { "Mã giao dịch", "Loại nhà", "Diện tích", "Đơn giá", "Tổng tiền" };

        public CustomTableModel(ArrayList<Object[]> data) {
            this.data = data;
        }

        @Override
        public int getRowCount() {
            return data.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return data.get(rowIndex)[columnIndex];
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }
}
