package com.kanboo.www.controller.access;

import com.kanboo.www.dto.project.DemandContentDTO;
import com.kanboo.www.service.inter.project.DemandContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/demand")
public class DemandController {
    private final DemandContentService demandContentService;

    @PostMapping("/postRows")
    public List<DemandContentDTO> updateDemandContent(@RequestBody Map<String, List<DemandContentDTO>> map){
        System.out.println("post들어옴");
        Long idx =  map.get("params").get(0).getDemand().getDemandIdx();
        System.out.println("idx" + idx);
        List<DemandContentDTO> params = map.get("params");
        demandContentService.updateDemandContent(params);
        return demandContentService.loadDemandContent(idx);
    }

    @PostMapping("/load")
    public List<DemandContentDTO> loadDemandContent(@RequestBody Map<String, String> map){
        String mapIdx = (String) map.get("idx");
        Long idx = Long.parseLong(mapIdx);
        List<DemandContentDTO> list = demandContentService.loadDemandContent(idx);
        System.out.println(list.toString());
        return list;
    }
    @PostMapping("/deleteRows")
    public void deleteDemandContent(@RequestBody Map<String, List<DemandContentDTO>> map){
        System.out.println(map.toString());
        List<DemandContentDTO> params = map.get("params");

        for (DemandContentDTO param : params) {
            if (param.getDemand().getDemandIdx() != null &&
                    param.getDemandCnIdx() != null) {
                Long demandIdxItem = Long.parseLong(String.valueOf(param.getDemand().getDemandIdx()));
                Long demandCnIdxItem = Long.parseLong(String.valueOf(param.getDemandCnIdx()));

                System.out.println("de : " + demandIdxItem + " dc :  " + demandCnIdxItem);
                demandContentService.deleteDemandContent(demandIdxItem, demandCnIdxItem);
            } else {
                System.out.println("값없음");
            }
        }
    }

    @PostMapping("/importDocument")
    public ResponseEntity<?> importDocument(@ModelAttribute MultipartFile[] uploadFile, String demandIdx){
        System.out.println("임포트옴");
        System.out.println(demandIdx);
//        String mapIdx = (String) map.get("idx");
//        Long idx = Long.parseLong(mapIdx);
        Long idx = Long.parseLong(demandIdx);
        String uploadFolder = "C:\\Users\\PC\\Desktop\\LCK\\FinalProject\\kanbooFinal\\kanboo_final\\src\\main\\resources\\storage\\demand\\excel\\userInput";
        File uploadPath = new File(uploadFolder);
        for(MultipartFile multipartFile : uploadFile){
            System.out.println("파일명 : " + multipartFile.getOriginalFilename());
            System.out.println("파일크기 : " + multipartFile.getSize());

            String uploadFileName = multipartFile.getOriginalFilename();
            uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1);
            File saveFile = new File(uploadPath, uploadFileName);
            try {
                multipartFile.transferTo(saveFile);
                demandContentService.checkDocument(idx, saveFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return ResponseEntity.ok("??");
    }

    @PostMapping("/downDocument")
    public ResponseEntity<Resource> downloadFile(@RequestBody Map<String, String> map,
                                                 @RequestHeader("User-Agent") String userAgent) {

        File f = new File("");
        String absolutePath = f.getAbsolutePath();
        String localPath = "C:\\Users\\PC\\Desktop\\LCK\\FinalProject\\kanbooFinal\\" +
                "kanboo_final\\src\\main\\resources\\storage\\demand\\excel\\save";
        String mapIdx = (String) map.get("idx");
        String prjctNm = (String) map.get("prjctNm"); // prjctNm vue에서 받아야디ㅗ고 확장자도 받아야한다 균창아
        Long idx = Long.parseLong(mapIdx);
//            demandContentService.downloadExcel(idx);
        String fileName = prjctNm + "-" + idx + ".xlsx";
//        String uploadFolder = "src\\main\\resources\\storage";
//            Resource resource = new FileSystemResource(localPath + "\\" +  fileName);
        Resource resource = demandContentService.downloadExcel(idx);

        System.out.println("뭐노 " + absolutePath);
        System.out.println(resource.toString());
        //해당 파일이 없을 때
        if (!resource.exists()) {
            System.out.println("파일이 없누");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        String resourceName = resource.getFilename();
        HttpHeaders headers = new HttpHeaders();

        try {
            String downloadName = null;
            if(userAgent.contains("Trident")) { //IE 11
                downloadName = URLEncoder.encode(resourceName, "UTF-8").replaceAll("\\+", " ");
            }else if(userAgent.contains("Edge")) {
                downloadName = URLEncoder.encode(resourceName, "UTF-8");
            }else {
                downloadName = new String(resourceName.getBytes("UTF-8"), "ISO-8859-1");
            }
            headers.add("Content-disposition", "attachment;fileName=" +
                    new String(resourceName.getBytes("UTF-8"),"ISO-8859-1"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // resource : 첨부파일 객체
        // headers : 파일명 처리 정보
        // ...OK : 200(성공)
        System.out.println(fileName);
        return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);


    }





}
