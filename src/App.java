import Domain.DSGDNha_model;
import Persistance.DSGDNha_DAO;
import Presentation.GDNha_UI_View;

public class App {
    public static void main(String[] args) {
        DSGDNha_DAO dsgdNha_DAO = new DSGDNha_DAO();

        DSGDNha_model dsgdNha_model = new DSGDNha_model();

        GDNha_UI_View gdNha_UI_View = new GDNha_UI_View(dsgdNha_DAO, dsgdNha_model);

        GDNha_UI_View.button_Controller bController = gdNha_UI_View.new button_Controller();
        bController.actionListener();
    }
}
