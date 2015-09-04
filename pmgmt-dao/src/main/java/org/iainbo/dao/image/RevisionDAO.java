package org.iainbo.dao.image;

import org.iainbo.dao.common.BaseDAO;
import org.iainbo.entities.image.Revision;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;


@Stateless
public class RevisionDAO extends BaseDAO {

    private static final String FIND_HEAD_REVISION_BY_IMAGE = "select r from Revision r where r.headRevision = 'Y' and r.image.id =:imageId";
    private static final String FIND_ALL_REVISIONS_FOR_IMAGE = "select r from Revision r where r.image.id =:imageId";
    private static final String FIND_REVISION_BY_ID = "select r from Revision r where r.id =:revisionId";

    public RevisionDAO(){super(Revision.class);}

    public RevisionDAO(EntityManager entityManager){
        super(Revision.class);
        this.entityManager = entityManager;
    }

    public Revision findHeadRevision(Long imageId) throws NoResultException{
        Query query = entityManager.createQuery(FIND_HEAD_REVISION_BY_IMAGE);
        query.setParameter("imageId", imageId);
        Revision result = (Revision) query.getSingleResult();
        return result;
    }

    public List<Revision> allRevisionsForImage(Long imageId){
        Query query = entityManager.createQuery(FIND_ALL_REVISIONS_FOR_IMAGE);
        query.setParameter("imageId", imageId);
        List<Revision> results = query.getResultList();
        return results;
    }

    public Revision findById(Long revisionId){
        Query query = entityManager.createQuery(FIND_REVISION_BY_ID);
        query.setParameter("revisionId", revisionId);
        Revision result = (Revision)query.getSingleResult();
        return result;
    }

    public Revision create(final Revision entity){
        entityManager.persist(entity);
        return entity;
    }

}
