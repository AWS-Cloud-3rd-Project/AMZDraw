package com.amzmall.project.puduct.repository;

import com.amzmall.project.puduct.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByParentIsNull();

    @Query("SELECT c FROM Category c WHERE c.parent.id = :parentId")
    List<Category> findByParentIdCustomQuery(Long parentId);

//    List<Category> findByParent(Category parent);


//    List<Category> findAllByDepth(Integer depth);
//    private final EntityManager em;

//    public List<Category> findAll(){
//        return em
//                .createQuery("SELECT c FROM Category c WHERE c.parent IS NULL", Category.class)
//                .getResultList();
//    }

//    public List<Category> findChildrenByDepth(Long parentCategoryId, int targetDepth) {
//        String jpql = "SELECT c FROM Category c WHERE c.parent.id = :parentCategoryId AND c.depth = :targetDepth";
//        return em.createQuery(jpql, Category.class)
//                .setParameter("parentCategoryId", parentCategoryId)
//                .setParameter("targetDepth", targetDepth)
//                .getResultList();
//    }

//gpt 참고자료
//
//    @Repository
//    @RequiredArgsConstructor
//    public class CategoryRepository {
//        private final EntityManager em;
//
//        public List<Category> findAllByDepth(int targetDepth) {
//            if (targetDepth == 1) {
//                return findAll(); // depth가 1인 경우 모든 데이터 반환
//            } else {
//                String jpql = "SELECT c FROM Category c WHERE c.depth = :targetDepth";
//                return em.createQuery(jpql, Category.class)
//                        .setParameter("targetDepth", targetDepth)
//                        .getResultList();
//            }
//        }
//
//        public List<Category> findChildrenByDepth(Long parentCategoryId, int targetDepth) {
//            String jpql = "SELECT c FROM Category c WHERE c.parent.id = :parentCategoryId AND c.depth = :targetDepth";
//            return em.createQuery(jpql, Category.class)
//                    .setParameter("parentCategoryId", parentCategoryId)
//                    .setParameter("targetDepth", targetDepth)
//                    .getResultList();
//        }
//    }
}
