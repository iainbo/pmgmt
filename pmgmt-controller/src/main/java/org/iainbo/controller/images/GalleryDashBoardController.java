package org.iainbo.controller.images;

import org.iainbo.dto.GalleryDTO;
import org.iainbo.dto.ImageDTO;
import org.iainbo.pmgmt.service.images.GalleryService;
import org.iainbo.pmgmt.view.gallery.GalleryDashboardView;
import org.iainbo.pmgmt.view.gallery.GalleryView;
import org.iainbo.pmgmt.view.gallery.ImageView;

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
        createGalleriesView(allAvailableGalleries);
    }

    public void createGalleriesView(List<GalleryDTO> galleries){
        galleryDashboardViews = new ArrayList<>();
        for(GalleryDTO g : galleries){
            GalleryDashboardView galleryDashboardView = new GalleryDashboardView();
            galleryDashboardView.setId(g.getId());
            galleryDashboardView.setGalleryName(g.getGalleryName());
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
        galleryView.setGalleryName(galleryDTO.getGalleryName());
        galleryView.setOwner(galleryOwner);
        galleryView.setContainsImages(galleryDTO.getImageDTOList().isEmpty());
        if(!galleryDTO.getImageDTOList().isEmpty()){
            List<ImageDTO> imageDTOs = galleryDTO.getImageDTOList();
            List<ImageView> imageViews = createImageViewList(imageDTOs, galleryView, galleryOwner);
            galleryView.setImages(imageViews);
            galleryView.setNumberOfImages(Integer.toString(galleryDTO.getImageDTOList().size()));
        }
    }

    public List<ImageView> createImageViewList(List<ImageDTO> imageDTOs, GalleryView galleryView, String galleryOwner){
        List<ImageView> imageViews = new ArrayList<>();
        for(ImageDTO imageDTO : imageDTOs){
            ImageView imageView = new ImageView();
            imageView.setId(imageDTO.getId());
            imageView.setTitle(imageDTO.getTitle());
//            imageView.setFileData(imageDTO.getFileData());
//            imageView.setFilename(imageDTO.getFilename());
//            imageView.setDateUploaded(imageDTO.getDateUploaded());
            imageView.setGalleryView(galleryView);
            imageView.setUploadedBy(galleryOwner);
            imageViews.add(imageView);
        }
        return  imageViews;
    }

}
