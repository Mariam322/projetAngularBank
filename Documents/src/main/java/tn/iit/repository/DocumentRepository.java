package tn.iit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.iit.entites.Document;
import tn.iit.entites.TypeDocument;

import java.util.List;
public interface DocumentRepository extends JpaRepository<Document,Long>{
List<Document> findByFactureId(Long factureId);
 List<Document> findByType(TypeDocument type);
}
