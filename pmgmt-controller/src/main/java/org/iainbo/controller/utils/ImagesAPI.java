package org.iainbo.controller.utils;

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

    @GET
    @Path("/view/{id}")
    public Response viewImage(@Context HttpHeaders headers,
                              @PathParam("id") String imageId) throws ServletException, IOException{
        Long image = Long.valueOf(imageId);

        byte[] imageBytes = imageService.getBytesForImage(image);
        if(imageBytes == null || imageBytes.length == 0){
            System.out.println("No Image Available for this ID: " + imageId);
        }
        Response.ResponseBuilder imageResponseBuilder = Response.ok(new ByteArrayInputStream(imageBytes), "image/jpeg");
        imageResponseBuilder.header("Content-Disposition", "attachment;filename=\"image.jpeg\"");
        return imageResponseBuilder.build();
    }
}
