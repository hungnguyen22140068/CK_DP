package Domain.CommandProcessor;

import Domain.DSGDNha_model;
import Persistance.DSGDNha_DAO;

public class SuaCommand extends Command {
    private int maGiaoDich;
    private String loaiNha;
    private double dienTich;
    private double donGia;
    private double tongTien;

    public SuaCommand(DSGDNha_DAO dsgdNha_DAO, DSGDNha_model dsgdNha_model, int maGiaoDich, String loaiNha,
            double dienTich, double donGia, double tongTien) {
        super(dsgdNha_DAO, dsgdNha_model);
        this.maGiaoDich = maGiaoDich;
        this.loaiNha = loaiNha;
        this.dienTich = dienTich;
        this.donGia = donGia;
        this.tongTien = tongTien;

    }

    @Override
    public void execute() {
        dsgdNha_DAO.updateData(maGiaoDich, loaiNha, dienTich, donGia, tongTien);
    }

}
