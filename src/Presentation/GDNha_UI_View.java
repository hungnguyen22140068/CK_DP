package Presentation;

import Persistance.DSGDNha_DAO;
import Domain.DSGDNha_model;
import Domain.CommandProcessor.Command;
import Domain.CommandProcessor.SuaCommand;
import Domain.CommandProcessor.ThemCommand;
import Domain.CommandProcessor.TimKiemCommand;
import Domain.CommandProcessor.TongTienCommand;
import Domain.CommandProcessor.XoaCommand;
import Domain.Observer.Observer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import java.util.ArrayList;

public class GDNha_UI_View extends JFrame implements Observer {
  private JPanel jPanel;
  private JPanel buttonPanel;
  private JLabel loaiNhaLabel, dienTichLabel, donGiaLabel, tongTienLabel, searchLabel;
  private JTextField loaiNhaTextField, dienTichTextField, donGiaTextField, tongTienTextField, searchTextField;
  private JTable jTable;
  private JScrollPane scrollPane;
  private JButton ThemButton, SuaButton, XoaButton, TimKiemButton, LamMoiButton, TongTienButton;

  // Biến đối tượng
  private DSGDNha_DAO dsgdNha_DAO;
  private DSGDNha_model dsgdNha_model;
  public int selectedRowId;

  public GDNha_UI_View(DSGDNha_DAO dsgdNha_DAO, DSGDNha_model dsgdNha_model) {
    this.dsgdNha_DAO = dsgdNha_DAO;
    this.dsgdNha_model = dsgdNha_model;
    this.dsgdNha_model.registerObserver(this);
    this.dsgdNha_DAO.registerObserver(this);
    init();
  }

  public void init() {
    builPanel();

    add(jPanel, BorderLayout.NORTH);
    add(scrollPane, BorderLayout.CENTER);
    add(buttonPanel, BorderLayout.SOUTH);

    this.setTitle("Quản lý giao dịch nhà");
    this.setSize(600, 500);
    this.setVisible(true);
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  public void builPanel() {
    jPanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5); // padding
    gbc.anchor = GridBagConstraints.WEST;
    gbc.fill = GridBagConstraints.HORIZONTAL;

    loaiNhaLabel = new JLabel("Loại nhà");
    gbc.gridx = 0;
    gbc.gridy = 0;
    jPanel.add(loaiNhaLabel, gbc);
    loaiNhaTextField = new JTextField(15);
    gbc.gridx = 1;
    jPanel.add(loaiNhaTextField, gbc);

    dienTichLabel = new JLabel("Diện tích");
    gbc.gridx = 0;
    gbc.gridy = 1;
    jPanel.add(dienTichLabel, gbc);
    dienTichTextField = new JTextField(15);
    gbc.gridx = 1;
    jPanel.add(dienTichTextField, gbc);

    donGiaLabel = new JLabel("Đơn giá");
    gbc.gridx = 0;
    gbc.gridy = 2;
    jPanel.add(donGiaLabel, gbc);
    donGiaTextField = new JTextField(15);
    gbc.gridx = 1;
    jPanel.add(donGiaTextField, gbc);

    tongTienLabel = new JLabel("Tổng tiền thanh toán");
    gbc.gridx = 0;
    gbc.gridy = 3;
    jPanel.add(tongTienLabel, gbc);
    tongTienTextField = new JTextField(15);
    tongTienTextField.setEditable(false);
    gbc.gridx = 1;
    jPanel.add(tongTienTextField, gbc);

    searchLabel = new JLabel("Tìm kiếm");
    gbc.gridx = 0;
    gbc.gridy = 4;
    jPanel.add(searchLabel, gbc);
    searchTextField = new JTextField(15);
    gbc.gridx = 1;
    jPanel.add(searchTextField, gbc);

    // table
    ArrayList<Object[]> data = dsgdNha_DAO.loadDataFromDatabase();
    jTable = new JTable(dsgdNha_DAO.new CustomTableModel(data));
    scrollPane = new JScrollPane(jTable);
    // Add listener for row selection
    jTable.getSelectionModel().addListSelectionListener(e -> {
      int selectedRow = jTable.getSelectedRow();
      if (selectedRow >= 0) {
        // Update text fields with selected row data
        loaiNhaTextField.setText((String) jTable.getValueAt(selectedRow, 1));
        dienTichTextField.setText(jTable.getValueAt(selectedRow, 2).toString());
        donGiaTextField.setText(jTable.getValueAt(selectedRow, 3).toString());
        tongTienTextField.setText(jTable.getValueAt(selectedRow, 4).toString());
      }
    });

    buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
    ThemButton = new JButton("Thêm");
    buttonPanel.add(ThemButton);
    SuaButton = new JButton("Sửa");
    buttonPanel.add(SuaButton);
    XoaButton = new JButton("Xóa");
    buttonPanel.add(XoaButton);
    TimKiemButton = new JButton("Tìm kiếm");
    buttonPanel.add(TimKiemButton);
    LamMoiButton = new JButton("Làm mới");
    buttonPanel.add(LamMoiButton);
    TongTienButton = new JButton("Tổng tiền");
    buttonPanel.add(TongTienButton);
  }

  // load dữ liệu bảng
  public void refreshTable() {
    ArrayList<Object[]> data = dsgdNha_DAO.loadDataFromDatabase();
    jTable.setModel(dsgdNha_DAO.new CustomTableModel(data));
  }

  // load trường nhập dữ liệu
  public void clearTextFiled() {
    loaiNhaTextField.setText("");
    dienTichTextField.setText("");
    donGiaTextField.setText("");
    tongTienTextField.setText("");
    searchTextField.setText("");
  }

  public class button_Controller implements ActionListener {
    private Command cmd;

    public button_Controller() {
    }

    public void actionListener() {
      ThemButton.addActionListener(this);
      SuaButton.addActionListener(this);
      XoaButton.addActionListener(this);
      TimKiemButton.addActionListener(this);
      LamMoiButton.addActionListener(this);
      TongTienButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

      if (e.getSource() == TongTienButton) {
        try {
          // Đọc giá trị từ các trường văn bản
          double dienTich = Double.parseDouble(dienTichTextField.getText().trim());
          double donGia = Double.parseDouble(donGiaTextField.getText().trim());
          String loaiNha = loaiNhaTextField.getText().trim(); // Đọc loại nhà từ trường văn bản

          // Cập nhật giá trị vào mô hình
          dsgdNha_model.setLoaiNha(loaiNha); // Thiết lập loại nhà
          dsgdNha_model.setDienTich((int) dienTich); // Thiết lập diện tích
          dsgdNha_model.setDonGia(donGia); // Thiết lập đơn giá

          cmd = new TongTienCommand(dsgdNha_DAO, dsgdNha_model);
          cmd.execute();
        } catch (NumberFormatException ex) {
          JOptionPane.showMessageDialog(GDNha_UI_View.this, "Vui lòng nhập số hợp lệ", "Lỗi nhập liệu",
              JOptionPane.ERROR_MESSAGE);
        }
      } else if (e.getSource() == XoaButton) {
        int selectedRow = jTable.getSelectedRow();
        if (selectedRow != -1) {
          int maGiaoDich = (int) jTable.getValueAt(selectedRow, 0);
          int confirm = JOptionPane.showConfirmDialog(GDNha_UI_View.this,
              "Bạn có chắc chắn muốn xóa giao dịch này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
          if (confirm == JOptionPane.YES_OPTION) {
            cmd = new XoaCommand(dsgdNha_DAO, dsgdNha_model, maGiaoDich);
            cmd.execute();
          }
        } else {
          JOptionPane.showMessageDialog(GDNha_UI_View.this, "Vui lòng chọn giao dịch cần xóa", "Thông báo",
              JOptionPane.WARNING_MESSAGE);
        }
      } else if (e.getSource() == ThemButton) {
        try {
          String loaiNha = loaiNhaTextField.getText().trim();
          double dienTich = Double.parseDouble(dienTichTextField.getText().trim());
          double donGia = Double.parseDouble(donGiaTextField.getText().trim());

          cmd = new ThemCommand(dsgdNha_DAO, dsgdNha_model, loaiNha, dienTich, donGia);
          cmd.execute();
        } catch (NumberFormatException ex) {
          JOptionPane.showMessageDialog(GDNha_UI_View.this, "Vui lòng nhập số hợp lệ", "Lỗi nhập liệu",
              JOptionPane.ERROR_MESSAGE);
        }
      } else if (e.getSource() == SuaButton) {
        int selectedRow = jTable.getSelectedRow();
        if (selectedRow >= 0) {
          int maGiaoDich = (int) jTable.getValueAt(selectedRow, 0); // Get the ID from the selected row
          try {
            double dienTich = Double.parseDouble(dienTichTextField.getText().trim());
            double donGia = Double.parseDouble(donGiaTextField.getText().trim());
            String loaiNha = loaiNhaTextField.getText().trim();
            double tongTien = Double.parseDouble(tongTienTextField.getText().trim());

            cmd = new SuaCommand(dsgdNha_DAO, dsgdNha_model, maGiaoDich, loaiNha, dienTich, donGia, tongTien);
            cmd.execute();
          } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(GDNha_UI_View.this, "Vui lòng nhập số hợp lệ", "Lỗi nhập liệu",
                JOptionPane.ERROR_MESSAGE);
          }
        } else {
          JOptionPane.showMessageDialog(GDNha_UI_View.this, "Vui lòng chọn dòng để sửa", "Lỗi",
              JOptionPane.ERROR_MESSAGE);
        }
      } else if (e.getSource() == TimKiemButton) {
        int maGiaoDich = Integer.parseInt(searchTextField.getText().trim());
        cmd = new TimKiemCommand(dsgdNha_DAO, dsgdNha_model, maGiaoDich);
        cmd.execute();
        ArrayList<Object[]> data = dsgdNha_DAO.searchByMaGiaoDich(maGiaoDich);
        jTable.setModel(dsgdNha_DAO.new CustomTableModel(data));
      } else if (e.getSource() == LamMoiButton) {
        refreshTable();
        clearTextFiled();
      }
    }
  }

  @Override
  public void update() {
    tongTienTextField.setText(String.format("%.2f", dsgdNha_model.getTongTien()));
    refreshTable();
  }
}
