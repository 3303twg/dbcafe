package com.example.dbcafe.member.entity;

import com.example.dbcafe.member.dto.BoardDTO;
import com.example.dbcafe.member.dto.BrandDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "brand_table")
public class BrandEntity extends BassEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //@Column(length = 20, nullable = false)
    //private String boardWriter;

//    @Column
//    private String boardPass;

    @Column
    private String boardTitle;

    @Column
    private String boardWriter;

    @Column(length = 500)
    private String boardContents;

    @Column
    private int boardHits;

    public static BrandEntity toSaveEntitiy(BrandDTO brandDTO){
        BrandEntity brandEntity = new BrandEntity();

        brandEntity.setBoardWriter(brandDTO.getBoardWriter());
        brandEntity.setBoardTitle(brandDTO.getBoardTitle());
        brandEntity.setBoardContents(brandDTO.getBoardContents());
        brandEntity.setBoardHits(0);

        return  brandEntity;

    }


    public static BrandEntity toUpdateEntity(BrandDTO brandDTO) {
        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setId(brandDTO.getId());//위랑 다르게 아이디가있어야 업데이트함
            //boardEntitiy.setBoardWriter(boardDTO.getBoardWriter());
//       boardEntitiy.setBoardPass(boardDTO.getBoardPass());
        brandEntity.setBoardTitle(brandDTO.getBoardTitle());
        brandEntity.setBoardWriter(brandDTO.getBoardWriter());
        brandEntity.setBoardContents(brandDTO.getBoardContents());
        brandEntity.setBoardHits(brandDTO.getBoardHits());
        return  brandEntity;
    }
}

