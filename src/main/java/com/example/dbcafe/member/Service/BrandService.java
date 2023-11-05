package com.example.dbcafe.member.Service;


import com.example.dbcafe.member.dto.BoardDTO;
import com.example.dbcafe.member.dto.BrandDTO;
import com.example.dbcafe.member.entity.BoardEntitiy;
import com.example.dbcafe.member.entity.BrandEntity;
import com.example.dbcafe.member.repository.BoardRepository;
import com.example.dbcafe.member.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BrandService {
    private  final BrandRepository brandRepository;

    public void save(BrandDTO brandDTO){
        BrandEntity brandEntity = BrandEntity.toSaveEntitiy(brandDTO);
        brandRepository.save(brandEntity);
    }

    public List<BrandDTO> findAll(){
        List<BrandEntity> brandEntityList = brandRepository.findAll();
        List<BrandDTO> brandDTOList= new ArrayList<>();
        for (BrandEntity brandEntity: brandEntityList){
            brandDTOList.add(BrandDTO.toBoardDTO(brandEntity));
        }
        return brandDTOList;
    }

    @Transactional
    public void updateHits(Long id){
        brandRepository.updateHits(id);
    }

    public BrandDTO findById(Long id){
        Optional<BrandEntity> optionalBrandEntity = brandRepository.findById(id);
        if(optionalBrandEntity.isPresent()){
            BrandEntity brandEntity = optionalBrandEntity.get();
            BrandDTO brandDTO = BrandDTO.toBoardDTO((brandEntity));
            return brandDTO;
        }
        else {
            return  null;
        }
    }


    //페이지 수정
    public BrandDTO update(BrandDTO brandDTO){
        BrandEntity brandEntity = BrandEntity.toUpdateEntity(brandDTO);
        brandRepository.save(brandEntity);
        return findById(brandDTO.getId());
    }

    //페이지삭제
    public void delete(Long id){
        brandRepository.deleteById(id);
    }

    //페이징처리관련
//    public Page<BoardDTO> paging(Pageable pageable){
//        int page = pageable.getPageNumber() -1;
//        int pageLimit = 3; //한페이지에 보여줄 글 갯수
//        // 한페이지당 3개씩 글을 보여주고 정렬 기준은 id기준으로 내림차순 정렬
//        Page<BoardEntitiy> boardEntitiys = boardRepository.findAll(PageRequest.of(page,pageLimit, Sort.by(Sort.Direction.DESC,"id")));
//
//        //엔티티를 dto객체로 바꿔주는 라인
//        Page<BoardDTO> boardDTOS = boardEntitiys.map(board -> new BoardDTO(board.getId(), board.getBoardWriter(), board.getBoardTitle(), board.getBoardHits(), board.getCretedTime()));
//
//        return boardDTOS;
//    }
//
//
//    //검색
//
//    public Page<BoardDTO> searchBoard(String search, String searchCategory, Pageable pageable) {
//        Page<BoardEntitiy> searchResult;
//        int page = pageable.getPageNumber() -1;
//        int pageLimit = 3; //한페이지에 보여줄 글 갯수
//
//        searchResult = boardRepository.findByBoardTitleContaining(search, PageRequest.of(page,pageLimit, Sort.by(Sort.Direction.DESC,"id")));
//
//
//        if ("title".equals(searchCategory)) {
//            searchResult = boardRepository.findByBoardTitleContaining(search, PageRequest.of(page,pageLimit, Sort.by(Sort.Direction.DESC,"id")));
//        } else if ("content".equals(searchCategory)) {
//            searchResult = boardRepository.findByBoardContentsContaining(search, PageRequest.of(page,pageLimit, Sort.by(Sort.Direction.DESC,"id")));
//        } else if ("writer".equals(searchCategory)) {
//            searchResult = boardRepository.findByBoardWriter(search, PageRequest.of(page,pageLimit, Sort.by(Sort.Direction.DESC,"id")));
//        } else {
//            searchResult = boardRepository.findAll(PageRequest.of(page,pageLimit, Sort.by(Sort.Direction.DESC,"id")));
//
//        }
//
//        return searchResult.map(BoardDTO::toBoardDTO);
//    }
}