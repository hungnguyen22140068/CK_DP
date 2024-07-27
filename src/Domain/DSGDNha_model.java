package Domain;

import java.util.LinkedList;
import java.util.List;

import Domain.Observer.Observer;
import Domain.Observer.Subject;

public class DSGDNha_model implements Subject {
  // filed
  private int MaGiaoDich;
  private String LoaiNha;
  private int DienTich;
  private double DonGia;
  private double TongTien;

  private List<Observer> observers = new LinkedList<>();

  // constructor
  public DSGDNha_model() {
  }

  public DSGDNha_model(int MaGiaoDich, String LoaiNha, int DienTich, double DonGia, double TongTien) {
    this.MaGiaoDich = MaGiaoDich;
    this.LoaiNha = LoaiNha;
    this.DienTich = DienTich;
    this.DonGia = DonGia;
    this.TongTien = TongTien;
  }

  // getter setter
  public int getMaGiaoDich() {
    return this.MaGiaoDich;
  }

  public void setMaGiaoDich(int MaGiaoDich) {
    this.MaGiaoDich = MaGiaoDich;
  }

  public String getLoaiNha() {
    return this.LoaiNha;
  }

  public void setLoaiNha(String LoaiNha) {
    this.LoaiNha = LoaiNha;
  }

  public int getDienTich() {
    return this.DienTich;
  }

  public void setDienTich(int DienTich) {
    this.DienTich = DienTich;
  }

  public double getDonGia() {
    return this.DonGia;
  }

  public void setDonGia(double DonGia) {
    this.DonGia = DonGia;
  }

  public double getTongTien() {
    return this.TongTien;
  }

  public void setTongTien(double TongTien) {
    this.TongTien = TongTien;
  }

  // method
  public void TongTienThanhToan() {
    if ("Cao cấp".equalsIgnoreCase(LoaiNha)) {
      this.TongTien = DonGia * DienTich;
    } else if ("Thường".equalsIgnoreCase(LoaiNha)) {
      this.TongTien = DonGia * DienTich * 0.9;
    } else {
      // Nếu loại nhà không hợp lệ, có thể cần thông báo lỗi hoặc xử lý khác
      this.TongTien = 0; // Hoặc xử lý khác
    }
    notifyObservers();
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
