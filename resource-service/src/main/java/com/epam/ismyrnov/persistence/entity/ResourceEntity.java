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
@Table(name = "t_resources", schema = "resources")
@SequenceGenerator(
    name = "resources_sequence",
    sequenceName = "q_resources_id",
    allocationSize = 1,
    schema = "resources")
public class ResourceEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "resources_sequence")
  private Long id;

  private byte[] data;
}
