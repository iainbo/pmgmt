package org.iainbo.pmgmt.view.gallery;

import org.iainbo.dto.GalleryDTO;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class GalleryDashboardView implements Serializable{

    private Long id;
    private String galleryName;
    private String description;
    private List<GalleryDTO> galleryDTOs;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGalleryName() {
        return galleryName;
    }

    public void setGalleryName(String galleryName) {
        this.galleryName = galleryName;
    }

    public List<GalleryDTO> getGalleryDTOs() {
        return galleryDTOs;
    }

    public void setGalleryDTOs(List<GalleryDTO> galleryDTOs) {
        this.galleryDTOs = galleryDTOs;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
