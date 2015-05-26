package org.iainbo.controller.gallery;

import org.iainbo.dto.GalleryDTO;
import org.iainbo.pmgmt.service.gallery.GalleryService;
import org.iainbo.pmgmt.view.gallery.GalleryDashboardView;
import org.iainbo.pmgmt.view.gallery.GalleryView;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
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
    public void init(){
        getGalleries();
    }


    public List<GalleryDashboardView> getGalleryDashboardViews() {
        return galleryDashboardViews;
    }

    public void setGalleryDashboardViews(List<GalleryDashboardView> galleryDashboardViews) {
        this.galleryDashboardViews = galleryDashboardViews;
    }


    public List<GalleryDTO> getGalleries(){
        List<GalleryDTO> allAvailableGalleries = galleryService.getAllAvailableGalleries();
        createGalleriesView(allAvailableGalleries);
        return  allAvailableGalleries;
    }

    public void createGalleriesView(List<GalleryDTO> galleries){
        galleryDashboardViews = new ArrayList<>();
        for(GalleryDTO g : galleries){
            GalleryDashboardView galleryDashboardView = new GalleryDashboardView();
            galleryDashboardView.setGalleryName(g.getGalleryName());
            if(g.getThumbnail() != null){
                StreamedContent converted = createImage(g.getThumbnail());
                galleryDashboardView.setThumbnail(converted);
            }
            galleryDashboardViews.add(galleryDashboardView);
        }
    }

    public StreamedContent createImage(byte[] image) {
        InputStream input = new ByteArrayInputStream(image);
        StreamedContent stream = new DefaultStreamedContent(input,
                "image/jpeg");
        return stream;
    }

    public String editGallery(String galleryName){
        GalleryDTO galleryDTO = galleryService.galleryDTOByName(galleryName);
        createGalleryView(galleryDTO);
        return "galleryView";
    }

    public String home(){
        return "adminHome";
    }

    public GalleryView createGalleryView(GalleryDTO galleryDTO){
        String galleryOwner = galleryDTO.getOwner().getFirstName()
                + " " + galleryDTO.getOwner().getSurname();
        galleryView.setGalleryName(galleryDTO.getGalleryName());
        galleryView.setOwner(galleryOwner);
        galleryView.setContainsImages(galleryDTO.getImageDTOList().isEmpty());
        if(!galleryDTO.getImageDTOList().isEmpty()){
            galleryView.setImages(galleryDTO.getImageDTOList());
            galleryView.setNumberOfImages(Integer.toString(galleryDTO.getImageDTOList().size()));
        }
        return galleryView;
    }

}
