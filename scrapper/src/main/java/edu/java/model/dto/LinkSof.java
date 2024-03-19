package edu.java.model.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class LinkSof {
    @Id private long id;
    private long linkId;
    private long countOfAnswer;
    private long countOfComments;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
