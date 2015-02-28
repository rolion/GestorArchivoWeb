/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import Control.CCarpeta;
import Control.CExamen;
import Control.CImagen;
import Entities.ECarpeta;
import Entities.EExamen;
import Entities.EImagen;
import Entities.EUsuario;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.imageio.ImageIO;
import org.bytedeco.javacpp.helper.opencv_core;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_highgui;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.TreeNode;
import org.primefaces.model.UploadedFile;
import util.JavaCV;
import util.MyParam;
import util.Util;

/**
 *
 * @author Rodry
 */
@ManagedBean(name = "nImagenes")
@SessionScoped
public class NCarpetas implements Serializable{
    private CCarpeta carpeta;
    private TreeNode root;
    private List<ECarpeta> listaC;
    private EUsuario user;
    private TreeNode selected;
    private CImagen cImg;
    private List<UploadedFile> file;
    private UploadedFile template;
    private List<EImagen> listaImagenes;
    private StreamedContent Image;
    private CExamen mCExamen;
    private Object id;
    private ECarpeta selectedCarpeta;
    private EExamen selectedExamen;
    private EImagen selectedImage;
    public NCarpetas() {
        carpeta=new CCarpeta();
        cImg= new CImagen();
        mCExamen=new CExamen();
    }

    public ECarpeta getSelectedCarpeta() {
        return selectedCarpeta;
    }

    public void setSelectedCarpeta(ECarpeta selectedCarpeta) {
        this.selectedCarpeta = selectedCarpeta;
    }

    public EExamen getSelectedExamen() {
        return selectedExamen;
    }

    public void setSelectedExamen(EExamen selectedExamen) {
        this.selectedExamen = selectedExamen;
    }

    public EImagen getSelectedImage() {
        return selectedImage;
    }

    public void setSelectedImage(EImagen selectedImage) {
        this.selectedImage = selectedImage;
    }
    
    public List<EImagen> getListaImagenes() {
        return listaImagenes;
    }

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public void setListaImagenes(List<EImagen> listaImagenes) {
        this.listaImagenes = listaImagenes;
    }

    public void setSelected(TreeNode selected) {
        this.selected = selected;
        if(selected!=null){
            if(this.selected.getData() instanceof ECarpeta){
                this.selectedCarpeta=(ECarpeta)this.selected.getData();
                this.selectedExamen=null;
                this.selectedImage=null;
            }
            if(this.selected.getData() instanceof EExamen){
                this.selectedExamen=(EExamen)this.selected.getData();
                this.selectedCarpeta=null;
                this.selectedImage=null;
            }
            if(this.selected.getData() instanceof EImagen){
                this.selectedImage=(EImagen)this.selected.getData();
                
                this.selectedCarpeta=null;
                this.selectedExamen=null;
            }
        }
    }

    public TreeNode getSelected() {
        return selected;
    }
    
    @PostConstruct
    public void init(){
        try {
            FacesContext fc = FacesContext.getCurrentInstance();
            
            Map<String,Object> params = fc.getExternalContext().getSessionMap();
            
            user= new EUsuario();
            user.setNickname((String) params.get(MyParam.Nick_Name));
            root = new DefaultTreeNode("Root", null);
            this.listaC=carpeta.getAllCarpetas(user);
            for (ECarpeta lista : listaC) {
                TreeNode node= new DefaultTreeNode(lista,root);
                List<EExamen> eLista= this.mCExamen.getExamen(lista);
                for (EExamen e : eLista) {
                    TreeNode eNode= new DefaultTreeNode("document_test", e, node);
                    node.getChildren().add(eNode);
                    List<EImagen> lImagen= this.cImg.getImagenesExamen(e);
                    for (EImagen lImagen1 : lImagen) {
                        TreeNode iNode= new DefaultTreeNode(MyParam.TIPO_IMAGEN, lImagen1, eNode);
                        eNode.getChildren().add(iNode);
                    }
                }
                root.getChildren().add(node);
            }
            this.root.setSelected(true);
        } catch (Exception ex) {
            Logger.getLogger(NCarpetas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public TreeNode getRoot() {
        return root;
    }
    public void saveCarpeta( ECarpeta carp){
        if(carp!=null){
            try {
                carp.setNickUser(this.user.getNickname());
                carp=carpeta.insertCarpeta(carp, this.user);

                root.getChildren().add(new DefaultTreeNode(carp, root));
                //this.init();

            } catch (Exception ex) {
                Logger.getLogger(NCarpetas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else
            System.out.println("'saveCarpeta', parameter 'carp' = null");
    }
    
    public void onNodeSelect(NodeSelectEvent event) {
        this.selected= event.getTreeNode();
        if(this.selected.getType()==MyParam.TIPO_IMAGEN){
               this.id= ((EImagen)this.selected.getData()).getId();
        }
    }
    public void deleteSelecteNode(){
       
            try {
                boolean exito=true;
                ECarpeta carp=((ECarpeta)((TreeNode)selected).getData());
                List<TreeNode> l=this.selected.getChildren();
                for (TreeNode l1 : l) {
                    if(l1.getData() instanceof EExamen){
                        List<TreeNode> lImg=l1.getChildren();
                            for (TreeNode lImg1 : lImg) {
                                if(lImg1.getData() instanceof EImagen){
                                    if(!this.cImg.removeImageExamen((EImagen)lImg1.getData())){
                                         showMessage("Error","No se pudo Eliminar la Imagen:"+((EImagen)lImg1.getData()).getNombreArchivo());
                                         exito=false;
                                    }
                                }
                                     
                            }
                        if(exito){
                            if(!this.mCExamen.removeExam(((EExamen)l1.getData())))
                                showMessage("Error","No se pudo Eliminar el Examen:"+((EExamen)l1.getData()).getNombreExamen());
                            else{
                                exito=false;
                            }
                        }  
                    }     
                }
                if(exito){
                    if(!this.carpeta.eliminarCarpeta(carp)){
                        showMessage("Error","No se pudo Eliminar la Carpeta:"+carp.getNombre());
                    }
                    else
                        this.root.getChildren().remove(selected);
                }else
                     showMessage("Error","No se pudo Eliminar la Carpeta:"+carp.getNombre());
                    
            } catch (Exception ex) {
                Logger.getLogger(NCarpetas.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        
    }
    private void showMessage(String title, String message){
         FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage(null, new FacesMessage(title,  
                           message) );
    }
    
    
    public void guardarExamen(EExamen examen){
        if(examen!=null && this.selected!=null && file!=null && file.size()>0){
            try {
                ECarpeta carp=((ECarpeta)((TreeNode)this.selected).getData());
                examen.setM_ECarpeta(carp);
                examen=this.mCExamen.saveExam(examen);
                if(this.selected!=null && this.selected.getData() instanceof ECarpeta ){
                    TreeNode node=this.selected;
                    TreeNode leaf=new DefaultTreeNode("document_test", examen, node);
                    node.getChildren().
                        add(leaf);
                    for (UploadedFile f : file) {
                        EImagen i= new EImagen();
                        i.setImageStream(f.getInputstream());
                        i.setIdDoc(examen.getId());
                        i.setTipoImagen(MyParam.TIPO_IMAGEN_1);
                        i.setNombreArchivo(f.getFileName());
                        i.setContentType(f.getContentType());
                        i=this.cImg.saveImage(i);
                        leaf.getChildren().add(new DefaultTreeNode("picture", i, node));

                    }
                }
                
            } catch (Exception ex) {
                Logger.getLogger(NCarpetas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public void handleFileUpload(FileUploadEvent event) {
        if(file==null)
            
            file= new ArrayList();
        file.add(event.getFile());
    }
    public void handleTemplateUpload(FileUploadEvent event) {
        this.template=event.getFile();
       
       
    }
    public void seekMatch(){
        if(template!=null){
            try {
                File Ftemp= 
                        Util.InputStreamToFile(template.getInputstream());
                
                TreeNode rootMatch=new DefaultTreeNode("Root", null);
                int cont=0;
                List<TreeNode> lFolder =this.root.getChildren();
                for (int i = 0; i < lFolder.size(); i++) {
                    List<TreeNode> lExamen= lFolder.get(i).getChildren();
                    for (int j = 0; j < lExamen.size(); j++) {
                        List<TreeNode> lImg = lExamen.get(j).getChildren();
                        for (int k = 0; k < lImg.size(); k++) {
                            EImagen picture=(EImagen)lImg.get(k).getData();
                            IplImage temp=opencv_highgui.cvLoadImage(Ftemp.getAbsolutePath());
                            IplImage pic= opencv_highgui.cvLoadImage(picture.writetoFile().getAbsolutePath());
                            if (JavaCV.mathTemplate(pic,temp)) {
                                rootMatch.getChildren().add(lImg.get(k));
                                System.out.println("Encontro match nro "+cont
                                +" pos img "+k+" pos Examen "+j+" pos folder "+i);
                                cont++;
                            }
                        }
                    }
                }
                this.root=rootMatch;
                System.out.println("Busqueda Terminada");
            } catch (IOException ex) {
                Logger.getLogger(NCarpetas.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Error al convertir InputStram to BufferedImage");
            }
        }
    }
}
