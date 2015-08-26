package org.iainbo.controller.images;

import org.iainbo.dto.Gallery.GalleryDTO;
import org.iainbo.dto.Image.ImageDTO;
import org.iainbo.pmgmt.service.images.GalleryService;
import org.iainbo.pmgmt.view.Image.ImageView;
import org.iainbo.pmgmt.view.gallery.GalleryDashboardView;
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

    private List<GalleryDashboardView> galleryDashboardViews;

    @PostConstruct
    //Method which is used to initialise the object after it has been injected.
    public void init(){
        getGalleries();
    }


    public List<GalleryDashboardView> getGalleryDashboardViews() {
        return galleryDashboardViews;
    }

    public void setGalleryDashboardViews(List<GalleryDashboardView> galleryDashboardViews) {
        this.galleryDashboardViews = galleryDashboardViews;
    }


    public void getGalleries(){
        List<GalleryDTO> allAvailableGalleries = galleryService.getAllAvailableGalleries();
        createGalleriesDashboardView(allAvailableGalleries);
    }

    public void createGalleriesDashboardView(List<GalleryDTO> galleries){
        galleryDashboardViews = new ArrayList<>();
        for(GalleryDTO g : galleries){
            GalleryDashboardView galleryDashboardView = new GalleryDashboardView();
            galleryDashboardView.setId(g.getId());
            galleryDashboardView.setGalleryName(g.getGalleryName());
            galleryDashboardView.setDescription(g.getDescription());
            galleryDashboardViews.add(galleryDashboardView);
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
            imageView.setRevisionId(imageDTO.getRevisionDTO().getId());
            String isCheckedOut = imageDTO.getRevisionDTO().getCheckedOut();
            if(isCheckedOut == null || isCheckedOut.equalsIgnoreCase("N")){
                imageView.setImageIsCheckedOut(false);
            }else {
                imageView.setImageIsCheckedOut(true);
            }

            imageView.setTitle(imageDTO.getTitle());
            imageView.setDescription(imageDTO.getDescription());
            imageView.setGalleryView(galleryView);
            imageView.setUploadedBy(galleryOwner);
            imageViews.add(imageView);
        }
        return  imageViews;
    }

}
