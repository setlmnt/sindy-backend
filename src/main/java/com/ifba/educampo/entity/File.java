package com.ifba.educampo.entity;


import com.ifba.educampo.entity.associate.Associate;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "files")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class File extends BaseEntity<Long> {
    @Column(name = "archive_name", nullable = false)
    private String archiveName;

    @Column(name = "original_name", nullable = false)
    private String originalName;

    @Column(name = "content_type", nullable = false)
    private String contentType;

    @Column(nullable = false)
    private Long size;

    @Column(nullable = false)
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "associate_id")
    private Associate associate;

    @Override
    public String toString() {
        return "File{" +
                ", archiveName='" + archiveName + '\'' +
                ", originalName='" + originalName + '\'' +
                ", contentType='" + contentType + '\'' +
                ", size=" + size +
                ", url='" + url + '\'' +
                '}';
    }
}
