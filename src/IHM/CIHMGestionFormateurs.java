package IHM;

import bdd.CTableFormateur;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import objets.CEntreprise;
import objets.CFormateur;

public class CIHMGestionFormateurs {
    
    //accès à la table formateur de la base de donnée
    private final CTableFormateur tableBDD = new CTableFormateur();

    //Collection des formateur
    private final ArrayList<CFormateur> collectionFormateurs = new CTableFormateur().selectFormateur();
    
    //tableau
    private DefaultTableModel model;
    private JTable tableauFormateur;
    private final JPanel panneauTableauFormateur = new JPanel();
    
    //Fenêtres
    private JFrame ihmAjoutFormateur = new JFrame();
    private JFrame ihmAfficherFormateur = new JFrame();
    private JFrame ihmEditerFormateur = new JFrame();
    private JFrame ihmSuppFormateur = new JFrame();
    private JPanel panneauPrincipal = new JPanel();
    
    //Message en haut de page indiquant les différentes actions 
    private final JLabel message = new JLabel("");
    
    //Champs pour formulaires création et modification d'entreprise
    private final JTextField nomR = new JTextField(30);
    private final JTextField prenomR = new JTextField(30);
    private final JTextField mailR = new JTextField(30);
    private final JTextField telR = new JTextField(30);
    private final JTextField loginR = new JTextField(30);
    private final JTextField passwordR = new JTextField(30);
    
    //Désigne le numéro de ligne sélectionnée
    private int selection/*selection*/;
    
    //instance courante pour avoir accès aux getters
    private final CIHMGestionFormateurs instance;


     /****************************************
    *           Constructeur                * 
    ****************************************/
    
    public CIHMGestionFormateurs(){
        instance=this;
        //construction du panneau
        this.pannneauPrincipal();
        //construction du tableau
        this.tableau(); 
    }
    
     /****************************************
    *        Panneau principal              * 
    ****************************************/
    
    private JPanel pannneauPrincipal(){
        panneauPrincipal = new JPanel();
        panneauPrincipal.setBackground(Color.MAGENTA);
        
        
        /**********************************
           *       Titre + message           *
          **********************************/
                JPanel nord = new JPanel();
                nord.setBackground(Color.MAGENTA);
                nord.setLayout(new BorderLayout());
                
                JLabel titre = new JLabel("Gestion des Formateurs");
                Font policeTitre = new Font("Tahoma", Font.BOLD, 16);
                titre.setFont(policeTitre);
                titre.setForeground(Color.MAGENTA);
                titre.setHorizontalAlignment(JLabel.CENTER);
                
                Font policeMessage = new Font("Tahoma", Font.BOLD, 12);
                message.setFont(policeMessage);
                message.setForeground(Color.RED);
                message.setHorizontalAlignment(JLabel.CENTER);

                nord.add(titre, BorderLayout.NORTH);
                nord.add(message, BorderLayout.SOUTH);
    
            /**********************************
           *         Boutons bas de page     *
          **********************************/
                JPanel sud = new JPanel();
                sud.setLayout(new BorderLayout());
                       
                JButton btSelec = new JButton("Afficher le formateur sélectionnée");
                btSelec.addActionListener(new CIHMGestionFormateurs.OuvrirFenetreAfficher());
                
                JButton btAjouter = new JButton("Ajouter un formateur");
                btAjouter.addActionListener(new CIHMGestionFormateurs.OuvrirFenetreAjouter());
                
                sud.add(btSelec,BorderLayout.NORTH);
                sud.add(btAjouter,BorderLayout.SOUTH);
        
            /**********************************
           *        implémentation           *
          **********************************/        
                panneauPrincipal.add(nord,BorderLayout.NORTH);
                panneauPrincipal.add(this.tableau(),BorderLayout.CENTER);
                panneauPrincipal.add(sud,BorderLayout.SOUTH);
        
        return panneauPrincipal;
         
        
    }
     /**************************************************************************
    *     Tableau liste des formateurs impléménté dans Panneau principal     *
   **************************************************************************/
    
    public JPanel tableau(){
        
        //entetes 
        model = new DefaultTableModel();
        model.addColumn("Nom");
        model.addColumn("prenom");
        model.addColumn("mail");
        model.addColumn("telephone");
        model.addColumn("login");
        model.addColumn("mail");
 
        //Importation des données 
        for(int i = 0; i < collectionFormateurs.size(); i++){
        model.addRow(collectionFormateurs.get(i).getDonneesTab());
        }
        
        //Création du tableau
        tableauFormateur = new JTable(model);
  
        //Proporiétés JPanel
        panneauTableauFormateur.setBackground(Color.MAGENTA);
        panneauTableauFormateur.add(new JScrollPane(tableauFormateur));
        
        return panneauTableauFormateur;
    } 
     /**************************************************
    *      Fenêtre pour ajouter un formateur       *
   **************************************************/
    class ihmAjoutFormateur extends JFrame{
        
        public ihmAjoutFormateur(){
        super();
        setTitle("Editer formateur");
        setSize(400, 400);
        setLocationRelativeTo(null);
        
        //On définit le layout à utiliser 10 lignes 2 colonnes 5, 5 d'espacement
        this.setLayout(new GridLayout(10, 2, 5,5));
        
        JLabel nom = new JLabel("nom :");      
        JLabel prenom = new JLabel("prenom :");      
        JLabel mail = new JLabel("mail :");  
        JLabel tel = new JLabel("tel :");
        JLabel login = new JLabel("login :");
        JLabel password = new JLabel("password :");
        JLabel vide = new JLabel("");
        
        JButton btValider = new JButton("Ajouter");
        btValider.addActionListener(new CIHMGestionFormateurs.ValiderAjoutFormateur());
 
        add(nom);
        add(nomR);
        add(prenom);
        add(prenomR);
        add(mail);
        add(mailR);
        add(tel);
        add(telR);
        add(login);
        add(loginR);
        add(password);
        add(passwordR);  
        add(vide);
        add(btValider);
            }
        }

     /**************************************************
    *      Fenêtre afficher une entreprise            *
   **************************************************/
     
    class ihmAfficherFormateur extends JFrame{
        
        private final int i = selection;
        
        public ihmAfficherFormateur(){
            super();
            setTitle("Détail formateur");
            setSize(400, 300);
            setLocationRelativeTo(null);
            this.setLayout(new BorderLayout());
            setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            
            JPanel sud = new JPanel();
            sud.setLayout(new GridLayout());
            
            JButton BtEditer = new JButton("Editer");
            BtEditer.addActionListener(new CIHMGestionFormateurs.EditerFormateur());
            
            JButton BtSupp = new JButton("Supprimer");
            BtSupp.addActionListener(new CIHMGestionFormateurs.SuppFormateur());
            
            JButton BtFermer = new JButton("Fermer");
            BtFermer.addActionListener(new CIHMGestionFormateurs.FermerFormateur());
            
            sud.add(BtEditer);
            sud.add(BtSupp);
            sud.add(BtFermer);
            
            add(this.buildIhmAfficherFormateurs(),BorderLayout.NORTH);
            add(sud,BorderLayout.SOUTH);   
        }
    
        
    public JPanel buildIhmAfficherFormateurs(){
         
        JPanel panel = new JPanel();
        //On définit le layout à utiliser 10 lignes 2 colonnes 5,5 : espacement
        panel.setLayout(new GridLayout(10, 1, 5,5));
    
        JLabel nom = new JLabel("nom :");   
        JLabel prenom = new JLabel("prenom :"); 
        JLabel mail = new JLabel("Courriel :");
        JLabel tel = new JLabel("N° tel :");  
         JLabel login = new JLabel("login :");
        JLabel password = new JLabel("password :"); 
       
        
        JLabel nomA = new JLabel(collectionFormateurs.get(i).getNom());
        JLabel prenomA = new JLabel(collectionFormateurs.get(i).getPrenom());      
        JLabel mailA = new JLabel(collectionFormateurs.get(i).getMail());  
        JLabel telA = new JLabel(collectionFormateurs.get(i).getTel());
        JLabel loginA = new JLabel(collectionFormateurs.get(i).getLogin());
        JLabel passwordA = new JLabel(collectionFormateurs.get(i).getPassword());

        
        panel.add(nom);
        panel.add(nomA);
       panel.add(prenom);
        panel.add(prenomA);
        panel.add(mail);
        panel.add(mailA);
         panel.add(tel);
        panel.add(telA);
         panel.add(login);
        panel.add(loginA);
         panel.add(password);
        panel.add(passwordA);
        
 
        return panel;
     }}

     /**************************************************
    *        Fenêtre éditer une formateur            *
   **************************************************/
     
    class ihmEditerFormateur extends JFrame{  
     
        public ihmEditerFormateur(){
            super();
            setTitle("Editer formateur");
            setSize(400, 400);
            setLocationRelativeTo(null);
            setLayout(new BorderLayout());
            
            JButton BtModifier = new JButton("Modifier");
            BtModifier.addActionListener(new CIHMGestionFormateurs.ModifierFormateur());
 
            add(this.buildIhmEditerFormateur(),BorderLayout.NORTH);
            add(BtModifier,BorderLayout.SOUTH);   
        }
    
        
    public JPanel buildIhmEditerFormateur(){
         
        JPanel panel = new JPanel();
        //On définit le layout à utiliser 10 lignes 2 colonnes 5,5 : espacement
        panel.setLayout(new GridLayout(10, 2, 5,5));
    
        JLabel nom = new JLabel("nom :");      
        JLabel prenom = new JLabel("prenom :");      
        JLabel mail = new JLabel("Courriel :");  
        JLabel tel = new JLabel("tel :"); 
        JLabel login = new JLabel("login :"); 
        JLabel password = new JLabel("password :"); 
 
        panel.add(nom);
        panel.add(nomR);
        panel.add(prenom);
        panel.add(prenomR);
        panel.add(mail);
        panel.add(mailR);
        panel.add(tel);
        panel.add(telR);
        panel.add(login);
        panel.add(loginR);
        panel.add(password);
        panel.add(passwordR);
          

        return panel;
     }}
    
     /**************************************************
    *        Fenêtre supprimer une formateur         *
   **************************************************/
    
    class ihmSuppFormateur extends JFrame{  
    
        public ihmSuppFormateur(){
            super();
            setTitle("Suppression");
            setSize(300, 100);
            setLocationRelativeTo(null);
            setLayout(new BorderLayout());
            
            JLabel texte = new JLabel("Vous allez supprimer le formateur " + collectionFormateurs.get(selection).getNom());
            
            JButton BtConf = new JButton("Confirmer");
            BtConf.addActionListener(new CIHMGestionFormateurs.ConfSuppFormateur());

            this.add(texte,BorderLayout.NORTH);
            this.add(BtConf,BorderLayout.SOUTH);
            
     }}       
     /*******************************************************************************************************************************************
    *                                Traitement des actions des boutons                                                                        *
   *******************************************************************************************************************************************/   
    
            /**************************************************
           *         Bouton ouvrir fenêtre ajouter           *
          **************************************************/ 
           class OuvrirFenetreAjouter implements ActionListener{
               public void actionPerformed(ActionEvent arg0) {
                   
                   //On vide les champs de leurs contenu
                   instance.getNomR().setText("");
                   instance.getPrenomR().setText("");
                   instance.getMailR().setText("");
                   instance.getTelR().setText("");
                   instance.getLoginR().setText("");
                   instance.getPasswordR().setText("");
                         
                   ihmAjoutFormateur = new ihmAjoutFormateur();
                   ihmAjoutFormateur.setVisible(true);
               }
           }
           
   /**************************************************
           *     Action après validation ajout entreprise    *
          **************************************************/

           class ValiderAjoutFormateur implements ActionListener{
               public void actionPerformed(ActionEvent arg0) {

                   //Enregistrement des données récupérées dans un tableau 
                      ArrayList<String> donneeNouvelleFormateur = new ArrayList();
                      donneeNouvelleFormateur.add(instance.getNomR().getText());
                      donneeNouvelleFormateur.add(instance.getPrenomR().getText());
                      donneeNouvelleFormateur.add(instance.getMailR().getText());
                      donneeNouvelleFormateur.add(instance.getTelR().getText());
                      donneeNouvelleFormateur.add(instance.getLoginR().getText());
                      donneeNouvelleFormateur.add(instance.getPasswordR().getText());
                    

                  //Envoie du tableau pour enregistrement dans la BDD  
                     tableBDD.ajoutFormateur(donneeNouvelleFormateur);     
                      
                  //Envoie du tableau pour ajouter à la collection 
                      int lastId = collectionFormateurs.get(collectionFormateurs.size()-1).getId();
                      collectionFormateurs.add(new CFormateur(lastId+1,donneeNouvelleFormateur));

                  //mise a jour du tableau  
                      model.addRow(collectionFormateurs.get(collectionFormateurs.size()-1).getDonneesTab()); 
                        
                  //affiche message pour confirmer l'ajout de l'entreprise   
                     instance.getMessage().setText("le Formateur "+ instance.getNomR().getText() + " à été ajouté");

                  //on referme la fenêtre ajout entreprise   
                     ihmAjoutFormateur.dispose();            
               }
            }
            /**************************************************
           *                Bouton afficher                  *
          **************************************************/ 
           class OuvrirFenetreAfficher implements ActionListener{
               public void actionPerformed(ActionEvent e) {
                   
                   //on indique la ligne sélectionnée
                   selection = tableauFormateur.getSelectedRow(); 
                   
                   //message pour indiquer l'entreprise selectionnée
                   instance.getMessage().setText("le formateur "+collectionFormateurs.get(selection).getNom()+" est sélectionnée" );                   
                   
                   //on ouvre la fenêtre
                   ihmAfficherFormateur= new ihmAfficherFormateur();
                   ihmAfficherFormateur.setVisible(true);
              }
           }
           
            /**************************************************
           *                  Bouton éditer                  *
          **************************************************/ 
           class EditerFormateur implements ActionListener{
               public void actionPerformed(ActionEvent e) { 

                   //Message pour indiquer l'entreprise en cours d'édition
                   instance.getMessage().setText("Edition du formateur "+collectionFormateurs.get(selection).getNom() );                   
                   
                   //On remplie le formulaire avec les données actuelles de l'entreprise 
                   instance.getNomR().setText(collectionFormateurs.get(selection).getNom());
                   instance.getPrenomR().setText(collectionFormateurs.get(selection).getPrenom());
                   instance.getMailR().setText(collectionFormateurs.get(selection).getMail());
                   instance.getTelR().setText(collectionFormateurs.get(selection).getTel());
                   instance.getLoginR().setText(collectionFormateurs.get(selection).getLogin());
                   instance.getPasswordR().setText(collectionFormateurs.get(selection).getPassword());
                   
                   
                   //On affiche la fenêtre 
                   ihmEditerFormateur= new ihmEditerFormateur();
                   ihmEditerFormateur.setVisible(true);
              }
           }
            /********************************************************
          *    Action après validation modification formateur    *
         ********************************************************/

           class ModifierFormateur implements ActionListener{
               public void actionPerformed(ActionEvent arg0) {                 
                    
                   //Enregistrement des données récupérées dans un tableau
                      ArrayList<String> donneeModif = new ArrayList();
                      donneeModif.add(instance.getNomR().getText());
                      donneeModif.add(instance.getPrenomR().getText());
                      donneeModif.add(instance.getMailR().getText());
                      donneeModif.add(instance.getTelR().getText());
                      donneeModif.add(instance.getLoginR().getText());
                      donneeModif.add(instance.getPasswordR().getText());
                    
                    
                    //Modification dans la base de donnée
                    tableBDD.modifFormateur(donneeModif, instance.collectionFormateurs.get(selection).getId());   
                    
                   //Modification dans la collection 
                     int id = collectionFormateurs.get(selection).getId();
                     collectionFormateurs.set(selection, new CFormateur(id,donneeModif));
           
                  //mise a jour du tableau 
                     model.setValueAt(collectionFormateurs.get(selection).getNom(), selection, 0);
                     model.setValueAt(collectionFormateurs.get(selection).getPrenom(), selection, 1);
                     model.setValueAt(collectionFormateurs.get(selection).getMail(), selection, 2);
                     model.setValueAt(collectionFormateurs.get(selection).getTel(), selection, 3);
                     model.setValueAt(collectionFormateurs.get(selection).getLogin(), selection, 4);
                     model.setValueAt(collectionFormateurs.get(selection).getPassword(), selection, 5);
                               
                  //affiche message pour confirmer mofification du formateur   
                     instance.getMessage().setText("Le formateur "+ instance.getCollectionFormateurs().get(selection).getNom() + " à été modifiée");
                     
                  //on referme la fenêtre ajout formateur   
                  ihmEditerFormateur.dispose();
                  ihmAfficherFormateur.dispose();
   
               }
            }      
           
            /**************************************************
           *               Bouton supprimer                  *
          **************************************************/ 
           class SuppFormateur implements ActionListener{
               public void actionPerformed(ActionEvent e) {       
                   
                   ihmSuppFormateur = new ihmSuppFormateur();
                   ihmSuppFormateur.setVisible(true);
              }
           } 
           
             /**************************************************
           *         Bouton valider suppression              *
          **************************************************/ 
           class ConfSuppFormateur implements ActionListener{
               public void actionPerformed(ActionEvent e) { 

                  //Suppression dans la BDD  
                     tableBDD.suppFormateur(collectionFormateurs.get(selection).getId());  
                     
                  //Suppression dans la collection
                     String nomFormateurSupp = collectionFormateurs.get(selection).getNom();
                     collectionFormateurs.remove(selection);
                     
                  //mise a jour du tableau 
                     model.removeRow(selection);   
                        
                  //affiche message pour confirmer suppression de l'entreprise   
                     instance.getMessage().setText("le formateur "+nomFormateurSupp+" à été supprimée");
                     
                  //on referme la fenêtre supp entreprise   
                     ihmAfficherFormateur.dispose();
                     ihmSuppFormateur.dispose();
 
              }
           } 
           
             /**************************************************
           *             Bouton fermer affichage             *
          **************************************************/ 
           class FermerFormateur implements ActionListener{
               public void actionPerformed(ActionEvent e) { 
               
                  //message vide   
                     instance.getMessage().setText("");
                     
                  //on referme la fenêtre affichage   
                     ihmAfficherFormateur.dispose();
              }
           } 
           
  
    
   /*******************************************************************************************************************************************
    *                                Getters                                                                                                   *
   *******************************************************************************************************************************************/     

     public JPanel getPanneauPrincipal() {
        return panneauPrincipal;
    }
    

    public JLabel getMessage() {
        return message;
    }

    public  CIHMGestionFormateurs getInstance() {
        return instance;
    }


    public ArrayList<CFormateur> getCollectionFormateurs() {
        return collectionFormateurs;
    }
    
    
        public JTextField getNomR() {
        return nomR;
    }

    public JTextField getPrenomR() {
        return prenomR;
    }

    public JTextField getMailR() {
        return mailR;
    }

    public JTextField getTelR() {
        return telR;
    }

     public JTextField getLoginR() {
        return loginR;
    }
      public JTextField getPasswordR() {
        return passwordR;
    }
}