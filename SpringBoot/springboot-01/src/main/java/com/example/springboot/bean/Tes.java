package com.example.springboot.bean;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @TableName test
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "tes")
public class Tes implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     *
     */
    private String a;

    /**
     *
     */
    private String b;

    /**
     *
     */
    private String c;

    private static final long serialVersionUID = 1L;

    public Tes(String a, String b, String c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }
}
