package org.iainbo.pmgmt.view.gallery;

import org.iainbo.dto.GalleryDTO;
import org.primefaces.model.StreamedContent;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class GalleryDashboardView implements Serializable{

    private String galleryName;
    private StreamedContent thumbnail;
    private List<GalleryDTO> galleryDTOs;

    public String getGalleryName() {
        return galleryName;
    }

    public void setGalleryName(String galleryName) {
        this.galleryName = galleryName;
    }

    public StreamedContent getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(StreamedContent thumbnail) {
        this.thumbnail = thumbnail;
    }

    public List<GalleryDTO> getGalleryDTOs() {
        return galleryDTOs;
    }

    public void setGalleryDTOs(List<GalleryDTO> galleryDTOs) {
        this.galleryDTOs = galleryDTOs;
    }
}
