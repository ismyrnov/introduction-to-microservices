package com.epam.ismyrnov.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_songs")
@SequenceGenerator(name = "songs_sequence", sequenceName = "q_songs_id", allocationSize = 1)
public class SongEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "songs_sequence")
  private Long id;

  private String name;
  private String artist;
  private String album;
  private String length;
  private String resourceId;
  private String year;
}
