package org.iainbo.controller;

import org.iainbo.dto.GalleryDTO;
import org.iainbo.pmgmt.service.gallery.GalleryService;
import org.iainbo.pmgmt.view.gallery.GalleryDashboardView;
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
        System.out.println("Attempting to navigate to: " + galleryName);
        galleryService.galleryDTOByName(galleryName);
        return "galleryView";
    }

    public String home(){
        return "adminHome";
    }

}
