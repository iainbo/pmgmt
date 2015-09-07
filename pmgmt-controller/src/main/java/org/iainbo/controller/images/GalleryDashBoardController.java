package org.iainbo.controller.images;

import org.iainbo.dto.Gallery.GalleryDTO;
import org.iainbo.dto.Image.ImageDTO;
import org.iainbo.dto.Image.RevisionDTO;
import org.iainbo.pmgmt.service.images.GalleryService;
import org.iainbo.pmgmt.service.images.ImageService;
import org.iainbo.pmgmt.view.Image.ImageView;
import org.iainbo.pmgmt.view.Image.RevisionView;
import org.iainbo.pmgmt.view.gallery.GalleryView;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@ViewScoped
public class GalleryDashBoardController implements Serializable{

    @Inject
    GalleryService galleryService;

    @Inject
    GalleryView galleryView;

    @Inject
    ImageService imageService;

    private List<GalleryView> galleryViews;

    @PostConstruct
    //Method which is used to initialise the class.
    public void init(){
        getGalleries();
    }


    public List<GalleryView> getGalleryViews() {
        return galleryViews;
    }

    public void setGalleryDashboardViews(List<GalleryView> galleryDashboardViews) {
        this.galleryViews = galleryDashboardViews;
    }


    public void getGalleries(){
        List<GalleryDTO> allAvailableGalleries = galleryService.getAllAvailableGalleries();
        createGalleriesDashboardView(allAvailableGalleries);
    }

    public void createGalleriesDashboardView(List<GalleryDTO> galleries){
        galleryViews = new ArrayList<>();
        for(GalleryDTO g : galleries){
            GalleryView galleryView = new GalleryView();
            galleryView.setId(g.getId());
            galleryView.setGalleryName(g.getGalleryName());
            galleryView.setDescription(g.getDescription());
            galleryViews.add(galleryView);
        }
    }

    public String viewGallery(String galleryName){
        GalleryDTO galleryDTO = galleryService.galleryDTOByName(galleryName);
        createGalleryView(galleryDTO);
        return "galleryView";
    }

    public String home(){
        return "adminHome";
    }

    public void createGalleryView(GalleryDTO galleryDTO){
        String galleryOwner = galleryDTO.getOwner().getFirstName()
                + " " + galleryDTO.getOwner().getSurname();
        galleryView.setId(galleryDTO.getId());
        galleryView.setGalleryName(galleryDTO.getGalleryName());
        galleryView.setDescription(galleryDTO.getDescription());
        galleryView.setOwner(galleryOwner);
        galleryView.setGalleryIsEmpty(galleryDTO.getImageDTOList().isEmpty());
        if(!galleryDTO.getImageDTOList().isEmpty()){
            List<ImageDTO> imageDTOs = galleryDTO.getImageDTOList();
            List<ImageView> imageViews = createImageViewList(imageDTOs, galleryView, galleryOwner);
            galleryView.setImages(imageViews);
            galleryView.setNumberOfImages(Integer.toString(galleryDTO.getImageDTOList().size()));
        }else{
            galleryView.setImages(null);
            galleryView.setNumberOfImages(null);
        }
    }

    public List<ImageView> createImageViewList(List<ImageDTO> imageDTOs, GalleryView galleryView, String galleryOwner){
        List<ImageView> imageViews = new ArrayList<>();
        for(ImageDTO imageDTO : imageDTOs){
            ImageView imageView = new ImageView();
            imageView.setId(imageDTO.getId());
            imageView.setRevisionView(createRevisionView(imageDTO));
            String isCheckedOut = imageDTO.getRevisionDTO().getCheckedOut();
            if(isCheckedOut == null || isCheckedOut.equalsIgnoreCase("N")){
                imageView.setImageIsCheckedOut(false);
            }else {
                imageView.setImageIsCheckedOut(true);
            }

            imageView.setTitle(imageDTO.getTitle());
            imageView.setDescription(imageDTO.getDescription());
            imageView.setGalleryView(galleryView);
            imageViews.add(imageView);
        }
        return  imageViews;
    }

    public RevisionView createRevisionView(ImageDTO imageDTO){
        RevisionDTO revisionDTO = imageService.getRevision(imageDTO.getRevisionDTO().getId());
        RevisionView revisionView = new RevisionView();
        revisionView.setId(revisionDTO.getId());
        revisionView.setRevisionNumber(revisionDTO.getRevisionNumber());
        revisionView.setRevisionComment(revisionDTO.getRevisionComment());
        revisionView.setFilename(revisionDTO.getFileDTO().getFilename());
        revisionView.setUploadedBy(revisionDTO.getUploadedBy().getFirstName() + " " + revisionDTO.getUploadedBy().getSurname());
        revisionView.setUploadedDate(revisionDTO.getDateUploaded().toString());
        return revisionView;
    }

}
