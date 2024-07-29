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
      this.TongTien = 0;
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
