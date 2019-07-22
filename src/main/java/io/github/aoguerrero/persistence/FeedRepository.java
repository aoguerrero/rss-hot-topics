package io.github.aoguerrero.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.aoguerrero.persistence.entities.Feed;

/**
 * Generic CRUD repository for the FeedItem entity 
 * 
 * @author Andrés Guerrero <aoguerrero@gmail.com>
 *
 */
@Repository
public interface FeedRepository extends JpaRepository<Feed, Long> {
}
