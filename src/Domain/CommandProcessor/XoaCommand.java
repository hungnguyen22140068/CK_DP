package Domain.CommandProcessor;

import Domain.DSGDNha_model;
import Persistance.DSGDNha_DAO;

public class XoaCommand extends Command {
    private int maGiaoDich;

    public XoaCommand(DSGDNha_DAO dsgdNha_DAO, DSGDNha_model dsgdNha_model, int maGiaoDich) {
        super(dsgdNha_DAO, dsgdNha_model);
        this.maGiaoDich = maGiaoDich;
    }

    @Override
    public void execute() {
        dsgdNha_DAO.deleteData(maGiaoDich);
    }

}
