package Domain.CommandProcessor;

import Domain.DSGDNha_model;
import Persistance.DSGDNha_DAO;

public abstract class Command {
    protected DSGDNha_DAO dsgdNha_DAO;
    protected DSGDNha_model dsgdNha_model;

    public Command(DSGDNha_DAO dsgdNha_DAO, DSGDNha_model dsgdNha_model) {
        this.dsgdNha_DAO = dsgdNha_DAO;
        this.dsgdNha_model = dsgdNha_model;
    }

    public abstract void execute();
}
