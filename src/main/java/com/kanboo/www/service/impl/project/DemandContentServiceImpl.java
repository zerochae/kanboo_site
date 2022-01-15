package com.kanboo.www.service.impl.project;

import com.kanboo.www.domain.entity.project.DemandContent;
import com.kanboo.www.domain.repository.project.DemandContentRepository;
import com.kanboo.www.dto.project.DemandContentDTO;
import com.kanboo.www.dto.project.DemandDTO;
import com.kanboo.www.dto.project.ProjectDTO;
import com.kanboo.www.service.inter.project.DemandContentService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DemandContentServiceImpl implements DemandContentService {
    private final DemandContentRepository demandContentRepository;


    @Override
    public DemandContentDTO getDemandContent(Long idx) {
        DemandContent byDemandContentIdx = demandContentRepository.findByDemandContentIdx(idx);
        return byDemandContentIdx.entityToDto();
    }

    @Override
    public List<DemandContentDTO> loadDemandContent(Long idx) {
        List<DemandContentDTO> demandContentDTOList = new ArrayList<>();
        List<DemandContent> demandContentList = demandContentRepository.findByDemandIdx(idx);

        for(DemandContent demandContent : demandContentList){
            demandContentDTOList.add(demandContent.entityToDto());
        }

        return demandContentDTOList;
    }

    @Override
    public void updateDemandContent(List<DemandContentDTO> demandContentDTO) {
        demandContentDTO.forEach(item -> {
            demandContentRepository.save(item.dtoToEntity());
        });
    }

    @Override
    @Transactional
    public void deleteDemandContent(Long demandIdx, Long demandCnIdx) {
        demandContentRepository.deleteByDemandIdx(demandIdx, demandCnIdx);
    }

    @Override
    public Resource downloadExcel(Long idx) {
        XSSFWorkbook workBook = new XSSFWorkbook();
        XSSFSheet sheet = workBook.createSheet("demand");
        List<DemandContentDTO> demandContentDTOList = new ArrayList<>();
        List<DemandContent> demandContentList = demandContentRepository.findByDemandIdx(idx);
        for(DemandContent demandContent : demandContentList){
            demandContentDTOList.add(demandContent.entityToDto());
        }
        XSSFRow row = sheet.createRow(0);
        XSSFCell cell = row.createCell(0);
        CellStyle titleCellStyle = workBook.createCellStyle();
        XSSFFont font = workBook.createFont();
        font.setFontName("gothic");
        font.setBold(true);
        titleCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        titleCellStyle.setFont(font);
        cell.setCellValue(demandContentDTOList.get(0).getDemand().getProject().getPrjctNm() + " - " + "demand");
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,6));
        cell.setCellStyle(titleCellStyle);
        row = sheet.createRow(1);
        cell = row.createCell(0);
        sheet.setColumnWidth(0, 2048);
        cell.setCellValue("No");
        cell = row.createCell(1);
        sheet.setColumnWidth(1, 4096);
        cell.setCellValue("Category");
        cell = row.createCell(2);
        sheet.setColumnWidth(2, 4096);
        cell.setCellValue("Demand ID");
        cell = row.createCell(3);
        sheet.setColumnWidth(3, 6144);
        cell.setCellValue("Demand Name");
        cell = row.createCell(4);
        sheet.setColumnWidth(4, 20480);
        cell.setCellValue("Demand Description");
        cell = row.createCell(5);
        sheet.setColumnWidth(5, 3072);
        cell.setCellValue("Requester");
        cell = row.createCell(6);
        sheet.setColumnWidth(6, 8192);
        cell.setCellValue("Remark");

        for (int i = 0; i < demandContentDTOList.size(); i++) {
            row = sheet.createRow(i+2);
            cell = row.createCell(0);
            cell.setCellValue(demandContentDTOList.get(i).getDemandCnNum());
            cell = row.createCell(1);
            cell.setCellValue(demandContentDTOList.get(i).getDemandCnSe());
            cell = row.createCell(2);
            cell.setCellValue(demandContentDTOList.get(i).getDemandCnId());
            cell = row.createCell(3);
            cell.setCellValue(demandContentDTOList.get(i).getDemandCnNm());
            cell = row.createCell(4);
            cell.setCellValue(demandContentDTOList.get(i).getDemandCnDetail());
            cell = row.createCell(5);
            cell.setCellValue(demandContentDTOList.get(i).getDemandCnRequstNm());
            cell = row.createCell(6);
            cell.setCellValue(demandContentDTOList.get(i).getDemandCnRm());
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("src/main/resources/storage/demand/excel/save/"+
                    demandContentDTOList.get(0).getDemand().getProject().getPrjctNm() + "-" +
                    demandContentDTOList.get(0).getDemand().getProject().getPrjctIdx() +  ".xlsx");
            workBook.write(fileOutputStream);

        } catch (IOException e) {
            System.out.println("에러 ㅜ");
            e.printStackTrace();
        }
        return new FileSystemResource("src/main/resources/storage/demand/excel/save/"+
                demandContentDTOList.get(0).getDemand().getProject().getPrjctNm() + "-" +
                demandContentDTOList.get(0).getDemand().getProject().getPrjctIdx() +  ".xlsx");
    }

    @Transactional
    @Override
    public void checkDocument(Long idx, File file) throws FileNotFoundException {
        FileInputStream fis = new FileInputStream(file);
        try {
            XSSFWorkbook workBook = new XSSFWorkbook(fis);
            List<Map<Integer, String>> list = new ArrayList<Map<Integer, String>>();
            int rowindex = 0;
            int columnindex = 0;
            //시트 수 (첫번째에만 존재하므로 0을 준다)
            //만약 각 시트를 읽기위해서는 FOR문을 한번더 돌려준다
            XSSFSheet sheet = workBook.getSheetAt(0);
            int rows=sheet.getPhysicalNumberOfRows(); //행의 수
            for(rowindex = 0; rowindex < rows; rowindex++){
                Map<Integer, String> map = new HashMap<>();
                XSSFRow row = sheet.getRow(rowindex);//행을읽는다
                if(row !=null){
                    int cells = row.getPhysicalNumberOfCells();//셀의 수
                    for(columnindex = 0; columnindex <= cells; columnindex++){

                        XSSFCell cell = row.getCell(columnindex);//셀값을 읽는다
                        String value = "";
                        if(cell == null){//셀이 빈값일경우를 위한 널체크
                            value = "";
                        }else{
                            switch (cell.getCellType()){
                                case XSSFCell.CELL_TYPE_FORMULA:
                                    value = cell.getCellFormula();
                                    break;
                                case XSSFCell.CELL_TYPE_NUMERIC:
                                    value = cell.getNumericCellValue() + "";
                                    break;
                                case XSSFCell.CELL_TYPE_STRING:
                                    value = cell.getStringCellValue() + "";
                                    break;
                                case XSSFCell.CELL_TYPE_BLANK:
                                case XSSFCell.CELL_TYPE_ERROR:
                                    value = "";
                                    break;
                            }
                        }
                        System.out.println(rowindex + "번 행 : " + columnindex + "번 열 값은: "+ value);
                        map.put(columnindex, value);
                    }
                }
                list.add(map);
            }

            System.out.println("Size : " + list.size());
            demandContentRepository.deleteDemandContentAllByDemandIdx(idx);
            List<DemandContentDTO> demandContentDTOList = new ArrayList<>();
            for (int i = 2; i < list.size(); i++) {
                DemandContentDTO demandContentDTO = new DemandContentDTO();
                demandContentDTO.setDemand(DemandDTO.builder().demandIdx(idx).build());
                demandContentDTO.getDemand().setProject(ProjectDTO.builder().prjctIdx(idx).build());
                demandContentDTO.setDemandCnNum(list.get(i).get(0));
                demandContentDTO.setDemandCnSe(list.get(i).get(1));
                demandContentDTO.setDemandCnId(list.get(i).get(2));
                demandContentDTO.setDemandCnNm(list.get(i).get(3));
                demandContentDTO.setDemandCnDetail(list.get(i).get(4));
                demandContentDTO.setDemandCnRequstNm(list.get(i).get(5));
                demandContentDTO.setDemandCnRm(list.get(i).get(6));
                demandContentDTOList.add(demandContentDTO);
            }


            demandContentDTOList.forEach(item ->{
                demandContentRepository.save(item.dtoToEntity());
            });
        } catch (IOException e) {
            System.out.println("IOException");
        }
    }




}
