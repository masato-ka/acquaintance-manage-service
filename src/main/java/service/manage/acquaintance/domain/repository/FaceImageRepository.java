package service.manage.acquaintance.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import service.manage.acquaintance.domain.model.FaceImage;

public interface FaceImageRepository extends JpaRepository<FaceImage, Integer> {

}
