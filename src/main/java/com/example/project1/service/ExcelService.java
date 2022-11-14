package com.example.project1.service;

import com.example.project1.dao.LoginDao;
import com.example.project1.dao.TariifDao;
import com.example.project1.entity.LoginEntity;
import com.example.project1.entity.TariffEntity;
import com.example.project1.model.ApiResponse;
import com.example.project1.model.CostsDTO;
import com.example.project1.model.ExcelDTO;
import com.example.project1.model.UserData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

@Service
public class ExcelService {

    @Autowired
    LoginDao loginDoa;

    @Autowired
    TariifDao tariffDao;

    @Autowired
    private ApiResponseService apiService;
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    List<LoginEntity> userdata;

    public void createCell(Row row,int  rowcount,LoginEntity userdata){
        row = sheet.createRow(rowcount);
        Cell cell = row.createCell(0);
        cell.setCellValue(userdata.getId());
        row.createCell(1).setCellValue(userdata.getFirstname());
        row.createCell(2).setCellValue(userdata.getLastname());
        row.createCell(3).setCellValue(userdata.getToken());
    }
    public List<LoginEntity> createExcel(){
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("User Data");
        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue("id");
        row.createCell(1).setCellValue("First Name");
        row.createCell(2).setCellValue("Last Name");
        row.createCell(3).setCellValue("Token");

        userdata = loginDoa.getAllData();

        for(int i=0;i<userdata.size();i++){
            Row rows = sheet.createRow(i+1);
            createCell(rows,i+1,userdata.get(i));
        }
        return userdata;
    }
    public ApiResponse export(HttpServletResponse response) throws Exception {
        try{
            List<LoginEntity> userList= createExcel();
            ServletOutputStream out = response.getOutputStream();
            workbook.write(out);
            out.close();
            return apiService.setResponse(true,"Excel sheet is created",userList);
        }
        catch(IOException e){
            System.out.println(e);
            throw new Exception("Something Went wrong");
        }
    }
//
    public TariffEntity insertExcelObject(List<String> cost_category,List<Integer> cost_value,TariffEntity excelObject,int rowlength){
        List<CostsDTO> costArray = new ArrayList<CostsDTO>();
        for (int j = 0; j < cost_category.size(); j++) {
            CostsDTO costObject = new CostsDTO();
            costObject.setCostcategory(cost_category.get(j));
            costObject.setCostvalue(cost_value.get(j));
            costArray.add(costObject);
        }
        Map<String, List<CostsDTO>> m = new HashMap<>();
        m.put("cost", costArray);
        Gson gsondata = new GsonBuilder().create();
        String jsondata = gsondata.toJson(m);
        excelObject.setCost(jsondata);
        excelObject.setId(rowlength);
        return excelObject;
    }
    public ApiResponse importSheet() throws Exception {
        try
        {
            File file = new File("/home/birinder/Downloads/excel.xlsx");   //creating a new file instance
            FileInputStream fis = new FileInputStream(file);   //obtaining bytes from the file
//creating Workbook instance that refers to .xlsx file
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet sheet = wb.getSheetAt(0);     //creating a Sheet object to retrieve object
            Iterator<Row> itr = sheet.iterator();    //iterating over excel file
            List<String> cost_category = new ArrayList<String>();
            List<Integer> cost_value = new ArrayList<Integer>();
            List<TariffEntity> tariffarray = new ArrayList<>();
            boolean pushedenteries = false;
            TariffEntity excelObject = new TariffEntity();
            int rowlength = 0;
            while (itr.hasNext())
            {

                Row row = itr.next();
                pushedenteries = false;
                Iterator<Cell> cellIterator = row.cellIterator();   //iterating over each column
                while (cellIterator.hasNext())
                {
                    Cell cell = cellIterator.next();
                    if((cell.getColumnIndex() == 0 && cell.getStringCellValue().length() > 1) && !cost_category.isEmpty()){
                        excelObject = insertExcelObject(cost_category,cost_value,excelObject,rowlength);

                        tariffarray.add(excelObject);

                        pushedenteries = true;

                        rowlength++;
                        excelObject = new TariffEntity();
                        cost_category.clear();
                        cost_value.clear();
                    }
                    if((cell.getColumnIndex() == 3 || cell.getColumnIndex() == 4 )&&( cell.getRowIndex() != 0)) {
                        switch (cell.getCellType()) {
                            case Cell.CELL_TYPE_STRING:    //field that represents string cell type

                            cost_category.add(cell.getStringCellValue());
                                break;
                            case Cell.CELL_TYPE_NUMERIC:    //field that represents number cell type

                            cost_value.add((int) cell.getNumericCellValue());
                                break;
                            default:
                                System.out.print("\t\t\t");
                        }
                    }
                    else if(cell.getRowIndex() >= 1){
                        switch(cell.getColumnIndex()){
                            case 0:
                                excelObject.setBillCategory(cell.getStringCellValue());
                                break;
                            case 1:
                                excelObject.setItemName(cell.getStringCellValue());
                                break;
                            case 2:
                                excelObject.setTariffType(cell.getStringCellValue());
                                break;
                            case 4:
                                excelObject.setVitrayaAmount((int)cell.getNumericCellValue());
                                break;
                            case 20:
                                excelObject.setDepartment(cell.getStringCellValue());
                                break;
                            default:
                        }
                    }
                }
            }
            if(!pushedenteries &&  !cost_category.isEmpty()){
                excelObject = insertExcelObject(cost_category,cost_value,excelObject,rowlength);
                tariffarray.add(excelObject);
            }
            for(int i=0;i<tariffarray.size();i++) {
                tariffDao.insertTariff(tariffarray.get(i).getBillCategory(), tariffarray.get(i).getItemName(), tariffarray.get(i).getTariffType(), tariffarray.get(i).getCost(), tariffarray.get(i).getVitrayaAmount(), tariffarray.get(i).getDepartment());

            }
            return apiService.setResponse(true,"Enteries have been pushed!",null);
        }
        catch(Exception e)
        {
            System.out.println(e);
            throw new Exception("Something went wrong");
        }
    }

}
