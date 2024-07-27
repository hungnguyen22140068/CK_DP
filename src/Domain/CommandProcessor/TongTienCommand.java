package Domain.CommandProcessor;

import Domain.DSGDNha_model;
import Persistance.DSGDNha_DAO;

public class TongTienCommand extends Command {

    public TongTienCommand(DSGDNha_DAO dsgdNha_DAO, DSGDNha_model dsgdNha_model) {
        super(dsgdNha_DAO, dsgdNha_model);
    }

    @Override
    public void execute() {
        dsgdNha_model.TongTienThanhToan();
    }

}
