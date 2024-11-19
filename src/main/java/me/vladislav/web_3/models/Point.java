package me.vladislav.web_3.models;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode

@Entity
@Table(name = "points")
public class Point {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;

    @Column(name = "x", nullable = false)
    private Double x;

    @Column(name = "y", nullable = false)
    private Double y;

    @Column(name = "r", nullable = false)
    private Double r;

    @Column(name = "result", nullable = false)
    private Boolean result;

    public Point(Double x, Double y, Double r, Boolean result) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.result = result;
    }

}