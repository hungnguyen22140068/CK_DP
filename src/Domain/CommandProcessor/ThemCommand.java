package Domain.CommandProcessor;

import Domain.DSGDNha_model;
import Persistance.DSGDNha_DAO;

public class ThemCommand extends Command {
    private String loaiNha;
    private double dienTich;
    private double donGia;

    public ThemCommand(DSGDNha_DAO dsgdNha_DAO, DSGDNha_model dsgdNha_model, String loaiNha, double dienTich,
            double donGia) {
        super(dsgdNha_DAO, dsgdNha_model);
        this.loaiNha = loaiNha;
        this.donGia = donGia;
        this.dienTich = dienTich;
    }

    @Override
    public void execute() {
        double tongTien = dienTich * donGia;
        dsgdNha_DAO.insertData(loaiNha, tongTien, tongTien, tongTien);
    }

}
