package com.example.dbcafe.member.Service;

import com.example.dbcafe.member.dto.NoticeDTO;
import com.example.dbcafe.member.entity.NoticeEntity;
import com.example.dbcafe.member.repository.NoticeRepository;
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
public class NoticeService {
    private  final NoticeRepository noticeRepository;

    public void save(NoticeDTO noticeDTO){
        NoticeEntity noticeEntity = NoticeEntity.toSaveEntitiy(noticeDTO);
        noticeRepository.save(noticeEntity);
    }

    public List<NoticeDTO> findAll(){
        List<NoticeEntity> noticeEntitiyList = noticeRepository.findAll();
        List<NoticeDTO> noticeDTOList= new ArrayList<>();
        for (NoticeEntity noticeEntity: noticeEntitiyList){
            noticeDTOList.add(NoticeDTO.toNoticeDTO(noticeEntity));
        }
        return noticeDTOList;
    }

    @Transactional
    public void updateHits(Long id){
        noticeRepository.updateHits(id);
    }

    public NoticeDTO findById(Long id){
        Optional<NoticeEntity> optionalNoticeEntity = noticeRepository.findById(id);
        if(optionalNoticeEntity.isPresent()){
            NoticeEntity noticeEntity = optionalNoticeEntity.get();
            NoticeDTO noticeDTO = NoticeDTO.toNoticeDTO((noticeEntity));
            return noticeDTO;
        }
        else {
            return  null;
        }
    }


    //페이지 수정
    public NoticeDTO update(NoticeDTO noticeDTO){
        NoticeEntity noticeEntity = NoticeEntity.toUpdateEntity(noticeDTO);
        noticeRepository.save(noticeEntity);
        return findById(noticeDTO.getId());
    }

    //페이지삭제
    public void delete(Long id){
        noticeRepository.deleteById(id);
    }

    //페이징처리관련
    public Page<NoticeDTO> paging(Pageable pageable){
        int page = pageable.getPageNumber() -1;
        int pageLimit = 3; //한페이지에 보여줄 글 갯수
        // 한페이지당 3개씩 글을 보여주고 정렬 기준은 id기준으로 내림차순 정렬
        Page<NoticeEntity> noticeEntities = noticeRepository.findAll(PageRequest.of(page,pageLimit, Sort.by(Sort.Direction.DESC,"id")));

        //엔티티를 dto객체로 바꿔주는 라인                                                         //notice.getBoardWriter()
        Page<NoticeDTO> noticeDTOS = noticeEntities.map(notice -> new NoticeDTO(notice.getId(),notice.getNoticeWriter(), notice.getNoticeTitle(), notice.getNoticeHits(), notice.getCreatedTime()));

        return noticeDTOS;
    }


    //검색
    public Page<NoticeDTO> searchBoard(String search, String searchCategory, Pageable pageable) {
        Page<NoticeEntity> searchResult;
        int page = pageable.getPageNumber() -1;
        int pageLimit = 3; //한페이지에 보여줄 글 갯수

        searchResult = noticeRepository.findByNoticeTitleContaining(search, PageRequest.of(page,pageLimit, Sort.by(Sort.Direction.DESC,"id")));

        if ("title".equals(searchCategory)) {
            searchResult = noticeRepository.findByNoticeTitleContaining(search, PageRequest.of(page,pageLimit, Sort.by(Sort.Direction.DESC,"id")));
        } else if ("content".equals(searchCategory)) {
            searchResult = noticeRepository.findByNoticeContentsContaining(search, PageRequest.of(page,pageLimit, Sort.by(Sort.Direction.DESC,"id")));
        } else {
            searchResult = noticeRepository.findAll(PageRequest.of(page,pageLimit, Sort.by(Sort.Direction.DESC,"id")));

        }

        return searchResult.map(NoticeDTO::toNoticeDTO);
    }
}
