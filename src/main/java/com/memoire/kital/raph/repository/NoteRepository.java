package com.memoire.kital.raph.repository;

import com.memoire.kital.raph.domain.Note;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Note entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NoteRepository extends JpaRepository<Note, Long>, JpaSpecificationExecutor<Note> {
}
