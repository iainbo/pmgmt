package org.iainbo.dao.image;

import org.iainbo.dao.BaseDAO;
import org.iainbo.entities.image.File;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class FileDAO extends BaseDAO {

    private static final String FIND_FILE_FOR_REVISION = "select f from File f where f.revision.id =:revId";

    public FileDAO(){super(File.class);}

    public FileDAO(EntityManager entityManager){
        super(File.class);
        this.entityManager = entityManager;
    }

    public File findFileForRevision(Long revId){
        Query query = entityManager.createQuery(FIND_FILE_FOR_REVISION);
        query.setParameter("revId", revId);
        File result = (File) query.getSingleResult();
        return result;
    }
}
