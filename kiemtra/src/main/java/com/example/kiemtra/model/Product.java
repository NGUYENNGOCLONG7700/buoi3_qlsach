package com.example.kiemtra.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "NHANVIEN")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank(message = "Tên nhân viên không được để trống")
    private String name;
    @NotBlank(message = "phái không được để trống")
    private String phai;
    @NotBlank(message = "nơi sinh không được để trống")
    private String noisinh;
    @NotNull(message = "Luong không được để trống")
    @Min(value = 0, message = "Luong phải lớn hơn hoặc bằng 0")
    private Double luong;

    @Length(min = 0, max = 50, message = "Độ dài của URL hình ảnh phải nằm trong khoảng từ 0 đến 50 ký tự")
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
