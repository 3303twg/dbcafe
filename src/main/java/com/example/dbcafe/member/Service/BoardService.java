package com.example.dbcafe.member.Service;

import com.example.dbcafe.member.dto.BoardDTO;
import com.example.dbcafe.member.entity.BoardEntitiy;
import com.example.dbcafe.member.repository.BoardRepository;
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
public class BoardService {
    private  final BoardRepository boardRepository;

    //글을 저장하는 서비스
    public void save(BoardDTO boardDTO){
        BoardEntitiy boardEntitiy = BoardEntitiy.toSaveEntitiy(boardDTO);
        //리포지트리의 save함수를 사용함
        boardRepository.save(boardEntitiy);
    }

    //qna dto를 전부 가져오는 메서드
    public List<BoardDTO> findAll(){
        List<BoardEntitiy> boardEntitiyList = boardRepository.findAll();
        List<BoardDTO> boardDTOList= new ArrayList<>();
        for (BoardEntitiy boardEntitiy: boardEntitiyList){
            boardDTOList.add(BoardDTO.toBoardDTO(boardEntitiy));
        }
        return boardDTOList;
    }

    @Transactional //조회수를 올리기위한 메서드
    public void updateHits(Long id){
        boardRepository.updateHits(id);
    }

    //id값을 사용하여 게시글의 엔티티를 가져오는 메서드
    public BoardDTO findById(Long id){
        Optional<BoardEntitiy> optionalBoardEntitiy = boardRepository.findById(id);
        if(optionalBoardEntitiy.isPresent()){
            BoardEntitiy boardEntitiy = optionalBoardEntitiy.get();
            BoardDTO boardDTO = BoardDTO.toBoardDTO((boardEntitiy));
            return boardDTO;
        }
        else {
            return  null;
        }
    }


    //페이지 수정
    public BoardDTO update(BoardDTO boardDTO){
        BoardEntitiy boardEntitiy = BoardEntitiy.toUpdateEntity(boardDTO);
        boardRepository.save(boardEntitiy);
        return findById(boardDTO.getId());
    }

    //페이지삭제
    public void delete(Long id){
        boardRepository.deleteById(id);
    }

    //페이징처리관련
    public Page<BoardDTO> paging(Pageable pageable){
        int page = pageable.getPageNumber() -1;
        int pageLimit = 3; //한페이지에 보여줄 글 갯수
        // 한페이지당 3개씩 글을 보여주고 정렬 기준은 id기준으로 내림차순 정렬
        Page<BoardEntitiy> boardEntitiys = boardRepository.findAll(PageRequest.of(page,pageLimit, Sort.by(Sort.Direction.DESC,"id")));

        //엔티티를 dto객체로 바꿔주는 라인
        Page<BoardDTO> boardDTOS = boardEntitiys.map(board -> new BoardDTO(board.getId(), board.getBoardWriter(), board.getBoardTitle(), board.getBoardHits(), board.getCretedTime()));

        return boardDTOS;
    }


    //검색
    public Page<BoardDTO> searchBoard(String search, String searchCategory, Pageable pageable) {
        Page<BoardEntitiy> searchResult;
        int page = pageable.getPageNumber() -1;
        int pageLimit = 3; //한페이지에 보여줄 글 갯수

        searchResult = boardRepository.findByBoardTitleContaining(search, PageRequest.of(page,pageLimit, Sort.by(Sort.Direction.DESC,"id")));


        if ("title".equals(searchCategory)) {
            searchResult = boardRepository.findByBoardTitleContaining(search, PageRequest.of(page,pageLimit, Sort.by(Sort.Direction.DESC,"id")));
        } else if ("content".equals(searchCategory)) {
            searchResult = boardRepository.findByBoardContentsContaining(search, PageRequest.of(page,pageLimit, Sort.by(Sort.Direction.DESC,"id")));
        } else if ("writer".equals(searchCategory)) {
            searchResult = boardRepository.findByBoardWriter(search, PageRequest.of(page,pageLimit, Sort.by(Sort.Direction.DESC,"id")));
        } else {
            searchResult = boardRepository.findAll(PageRequest.of(page,pageLimit, Sort.by(Sort.Direction.DESC,"id")));

        }

        return searchResult.map(BoardDTO::toBoardDTO);
    }
}
