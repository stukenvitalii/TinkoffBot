package edu.java.model.dto;

import java.io.Serializable;
import java.net.URI;
import java.sql.Timestamp;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.logging.log4j.core.config.plugins.convert.TypeConverters;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Link implements Serializable {
    @Id
    private Long id;

    @Convert(converter = UriConverter.class)
    private URI url;
    private long chatId;
    private Timestamp lastCheckTime;
    private Timestamp createdAt;
}
