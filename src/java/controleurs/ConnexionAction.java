package controleurs;
/* ==== INFO ====

 * @author maxime chausse
 * date : 19 septembre 2018
 * cour : Développement de projet informatique
 * College Rosemont
*/
import classe.Profil;
import dao.ProfilDAO;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpSession;

public class ConnexionAction extends AbstractAction {

    @Override
    public String execute() {
        HttpSession session = request.getSession(true);
        if (session.getAttribute("connected")!=null) {//déjà connecté
            return "recherche";
        }
        if(request.getParameter("emailCon")!=null){
            // *** faire le verification pour un mot de passe null si il ne le verifie pas sur le cote client
            // Initialisation
            String email = request.getParameter("emailCon");
            String pwd = request.getParameter("pwdCon");
            Profil p;
            ProfilDAO pDao = new ProfilDAO();
            
            // Aller chercher le profil avec le email
            p = pDao.findByEmail(email);
            // Verifier si le email a ete trouver
            if(p!=null)
                // Verifier si les deux mots de passes sont les memes
                if(p.getMotDePasse().equals(pwd)){
                        SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        // Retouner setter une variable de connexion pour compensser la session pour le moment
                        p.setDateConnexion(sm.format(new Date()));
                        pDao.update(p);
                        session.setAttribute("connected", p);
                        return "recherche";
                }
            invalide();
        }
        return "accueil";
    }
    
    // Si les mots de passes de correspontent pas on envoie des messages derreurs
    private void invalide(){request.setAttribute("errCon","Champ(s) invalide(s)");}
}
