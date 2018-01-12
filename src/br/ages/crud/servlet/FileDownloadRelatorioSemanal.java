package br.ages.crud.servlet;

import br.ages.crud.bo.RelatorioBO;
import br.ages.crud.model.Relatorio;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet("/downloadRelatorioSemanal")
public class FileDownloadRelatorioSemanal extends HttpServlet {
    private RelatorioBO relatorioBO;
    private Relatorio relatorio;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        relatorioBO = new RelatorioBO();
        OutputStream os = response.getOutputStream();
        Document document = new Document();
        document.setPageSize(PageSize.A4);
        String idRelatorio = request.getParameter("idRelatorio");
        relatorio = relatorioBO.buscaRelatorioId(Integer.parseInt(idRelatorio));

        try {
            PdfWriter.getInstance(document,os);
            document.open();

            Paragraph p1 = new Paragraph("Atividades Previstas");
            p1.setAlignment(Element.ALIGN_CENTER);
            document.add(p1);
            document.add(new Paragraph(relatorio.getAtividadesPrevistas()));

            document.add(Chunk.NEWLINE);

            p1 = new Paragraph("Atividades Concluidas");
            p1.setAlignment(Element.ALIGN_CENTER);
            document.add(p1);
            document.add(new Paragraph(relatorio.getAtividadesConcluidas()));

            document.add(Chunk.NEWLINE);

            p1 = new Paragraph("Proximos Passos");
            p1.setAlignment(Element.ALIGN_CENTER);
            document.add(p1);
            document.add(new Paragraph(relatorio.getProximo()));

            document.add(Chunk.NEWLINE);

            p1 = new Paragraph("Lições Aprendidas e Problemas Encontrados");
            p1.setAlignment(Element.ALIGN_CENTER);
            document.add(p1);
            document.add(new Paragraph(relatorio.getLicoesProblemas()));

            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        response.setHeader("Expires", "0");
        response.setHeader("Cache-Control",
                "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Pragma", "public");
        String name = relatorio.getInicioSemana() + ".pdf";
        response.addHeader("Content-Disposition", "attachment; filename=" + name);
        response.setContentType("application/pdf");
        os.flush();
        os.close();
    }
}
