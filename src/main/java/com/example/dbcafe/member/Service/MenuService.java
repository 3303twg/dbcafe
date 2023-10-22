package com.example.dbcafe.member.Service;

import com.example.dbcafe.member.dto.MenuDTO;
import com.example.dbcafe.member.entity.MenuEntity;
import com.example.dbcafe.member.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MenuService {
    @Autowired
    private final MenuRepository menuRepository;

    public List<MenuDTO> menuFindAll() {
        List<MenuEntity>menuEntityList=menuRepository.findAll();
        List<MenuDTO>menuDTOList=new ArrayList<>();
//        각 리스트에 저장되어있는 entity를 dto로 변환하는 과정
        for(MenuEntity menuEntity:menuEntityList)
            {
                menuDTOList.add(MenuDTO.toDTO(menuEntity));
            }

        return menuDTOList;
    }

    public void register(MenuDTO menuDTO, MultipartFile file, HttpServletRequest request) throws Exception {

        if(file!=null){//      projectpaht==webapp의 절대경로
            String projectPath = request.getSession().getServletContext().getRealPath("/") + "/upload_image/";
//      랜덤한 uid생성하여 file name과 연결
            UUID uuid = UUID.randomUUID();
            String fileName = uuid + "_" + file.getOriginalFilename();


            String filePath = "/upload_image/" + fileName;
//            String filePath = "/upload_image/" + fileName;

            File saveFile = new File(projectPath, fileName);

//        webapp에 file저장
            file.transferTo(saveFile);
//      file path를 저장해줌
            menuDTO.setMenuImagePath(filePath);
        }
        MenuEntity menuEntity = MenuEntity.toMenuEntity(menuDTO);

        menuRepository.save(menuEntity);
    }

    public void delete(Long id ,HttpServletRequest request) {
//        삭제해야하는 객체의 entity저장
        Optional<MenuEntity> menuEntityOptional=menuRepository.findById(id);
//        프로젝트의 절대경로(webapp까지)를 가져옴
        String projectPath = request.getSession().getServletContext().getRealPath("/");

        if(menuEntityOptional.isPresent()){
//            프로젝트 절대경로 + image의 이름을 가져옴
            String filePath = projectPath+menuEntityOptional.get().getMenuImagePath();

            File file=new File(filePath);
//            webapp의 저장되어있는 이미지 삭제
            file.delete();
//            db에서도 삭제시킴
            menuRepository.deleteById(id);
        }
    }

    public MenuDTO menufind(Long id) {
        Optional<MenuEntity> menuEntityOptional = menuRepository.findById(id);
        MenuDTO menuDTO= new MenuDTO();
        if (menuEntityOptional.isPresent()){
            menuDTO=MenuDTO.toDTO(menuEntityOptional.get());
        }
        return menuDTO;
    }

    public void modify(Long id, MenuDTO menuDTO) {
        Optional<MenuEntity> menuEntityOptional = menuRepository.findById(id);
        MenuDTO menuDTOtmp = new MenuDTO();


    }

}
