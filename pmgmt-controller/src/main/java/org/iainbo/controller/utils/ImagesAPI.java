package org.iainbo.controller.utils;

import org.iainbo.pmgmt.service.images.GalleryService;
import org.iainbo.pmgmt.service.images.ImageService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.io.ByteArrayInputStream;
import java.io.IOException;

@Named(value = "imagesAPI")
@Stateless
@Path("/image")
public class ImagesAPI{

    @Inject
    ImageService imageService;

    @Inject
    GalleryService galleryService;

    @GET
    @Path("/view/{id}")
    public Response viewImage(@Context HttpHeaders headers,
                              @PathParam("id") String imageId) throws ServletException, IOException{
        Long image = Long.valueOf(imageId);

        byte[] imageBytes = imageService.getBytesForImage(image);
        if(imageBytes == null || imageBytes.length == 0){
            //TODO: create no image available image.
            System.out.println("No Image Available for this ID: " + imageId);
        }
        Response.ResponseBuilder imageResponseBuilder = Response.ok(new ByteArrayInputStream(imageBytes), "image/jpeg");
        imageResponseBuilder.header("Content-Disposition", "attachment;filename=\"image.jpeg\"");
        return imageResponseBuilder.build();
    }

    @GET
    @Path("/thumb/{id}")
    public Response viewThumb(@Context HttpHeaders headers,
                              @PathParam("id") String galleryId) throws ServletException, IOException{
        Long gallery = Long.valueOf(galleryId);
        byte[] thumbBytes = galleryService.getThumbnailForGallery(gallery);
        if(thumbBytes == null || thumbBytes.length == 0){
            //TODO: create no image available image.
            System.out.println("No thumbnail available for: " + galleryId);
        }
        Response.ResponseBuilder imageResponseBuilder = Response.ok(new ByteArrayInputStream(thumbBytes), "image/jpeg");
        imageResponseBuilder.header("Content-Disposition", "attachment;filename=\"image.jpeg\"");
        return imageResponseBuilder.build();
    }
}
