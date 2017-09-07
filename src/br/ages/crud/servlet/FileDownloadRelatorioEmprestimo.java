package br.ages.crud.servlet;

import br.ages.crud.dao.EquipamentoAlunoDAO;
import br.ages.crud.model.EquipamentoAluno;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

@WebServlet("/downloadRelatorioEmprestimo")
public class FileDownloadRelatorioEmprestimo extends HttpServlet{

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Empréstimos de Equipamentos");

        CellStyle cellStyle = workbook.createCellStyle();
        CreationHelper creationHelper = workbook.getCreationHelper();
        cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("dd/mm/yyyy hh:mm:ss"));

        try {
            EquipamentoAlunoDAO equipamentoAlunoDAO = new EquipamentoAlunoDAO();
            ArrayList<EquipamentoAluno> equipamentoAlunos = equipamentoAlunoDAO.listarEquipamentosAlunos();

            int i = 1;

            HSSFRow rowZero = sheet.createRow(0);
            rowZero.createCell(0).setCellValue("ID");
            rowZero.createCell(1).setCellValue("Equipamento");
            rowZero.createCell(2).setCellValue("Código");
            rowZero.createCell(3).setCellValue("Nome do Aluno");
            rowZero.createCell(4).setCellValue("Matrícula");
            rowZero.createCell(5).setCellValue("Data Retirada");
            rowZero.createCell(6).setCellValue("Data Entrega");

            for(EquipamentoAluno equipamentoAluno: equipamentoAlunos){
                HSSFRow row = sheet.createRow(i);

                row.createCell(0).setCellValue(equipamentoAluno.getId());
                row.createCell(1).setCellValue(equipamentoAluno.getEquipamento().getNome());
                row.createCell(2).setCellValue(equipamentoAluno.getEquipamento().getCodigo());
                row.createCell(3).setCellValue(equipamentoAluno.getAluno().getNome());
                row.createCell(4).setCellValue(equipamentoAluno.getAluno().getMatricula());

                Cell cellDataRetirada = row.createCell(5);
                cellDataRetirada.setCellValue(equipamentoAluno.getDataRetirada());
                cellDataRetirada.setCellStyle(cellStyle);

                Cell cellDataEntrega = row.createCell(6);
                String dataEntrega = String.valueOf(equipamentoAluno.getDataEntrega());
                if(!dataEntrega.equals("null")){
                    cellDataEntrega.setCellValue(equipamentoAluno.getDataEntrega());
                } else {
                    cellDataEntrega.setCellValue("");
                }
                cellDataEntrega.setCellStyle(cellStyle);

                i++;
            }

            HSSFRow row = workbook.getSheetAt(0).getRow(0);
            for(int colNum = 0; colNum<row.getLastCellNum();colNum++)
                workbook.getSheetAt(0).autoSizeColumn(colNum);

            makeRowBold(workbook, rowZero);

            ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
            workbook.write(outByteStream);
            byte [] outArray = outByteStream.toByteArray();
            response.setContentType("application/ms-excel");
            response.setContentLength(outArray.length);
            response.setHeader("Expires:", "0"); // eliminates browser caching
            response.setHeader("Content-Disposition", "attachment; filename=RelatorioEmprestimoEquipamentos.xlsx");
            OutputStream outStream = response.getOutputStream();
            outStream.write(outArray);
            outStream.flush();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void makeRowBold(Workbook wb, Row row){
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setBold(true);
        style.setFont(font);

        for(int i = 0; i < row.getLastCellNum(); i++){
            row.getCell(i).setCellStyle(style);
        }
    }
}
