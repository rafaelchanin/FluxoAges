package br.ages.crud.servlet;

import br.ages.crud.bo.RelatorioBO;
import br.ages.crud.bo.UsuarioBO;
import br.ages.crud.exception.NegocioException;
import br.ages.crud.model.Relatorio;
import br.ages.crud.model.StatusRelatorio;
import br.ages.crud.model.Usuario;
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
import java.util.List;

@WebServlet("/downloadRelatorioSemanal")
public class FileDownloadRelatorioSemanal extends HttpServlet {
    private RelatorioBO relatorioBO;
    private Relatorio relatorio;
    private UsuarioBO usuarioBO;
    private Usuario usuario;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        relatorioBO = new RelatorioBO();
        usuarioBO = new UsuarioBO();
        OutputStream os = response.getOutputStream();
        Document document = new Document();
        document.setPageSize(PageSize.A4);
        String idUsuario = request.getParameter("idUser");
        try {
            usuario = usuarioBO.buscaUsuarioId(Integer.parseInt(idUsuario));
        } catch (NegocioException e) {
            e.printStackTrace();
        }
        String geralValidator = request.getParameter("idGeral");
        String name = "relatorioSemanal";
        if(geralValidator != null && Integer.parseInt(geralValidator) == 1){
            os = getTodosRelatorios(usuario,document,os,relatorioBO);
        }else {
            try {
                String idRelatorio = request.getParameter("idRelatorio");
                relatorio = relatorioBO.buscaRelatorioId(Integer.parseInt(idRelatorio));

                PdfWriter.getInstance(document, os);
                document.open();

                document.addTitle("Relatório Semanal");
                document.addAuthor(usuario.getNome());

                Paragraph title = new Paragraph("Relatório Semanal", FontFactory.getFont(FontFactory.HELVETICA, 22));
                title.setAlignment(Element.ALIGN_CENTER);
                document.add(title);

                title = new Paragraph(relatorio.dataAbertura(), FontFactory.getFont(FontFactory.HELVETICA, 18));
                title.setAlignment(Element.ALIGN_CENTER);
                document.add(title);

                title = new Paragraph(usuario.getNome());
                title.setAlignment(Element.ALIGN_CENTER);
                document.add(title);
                document.add(Chunk.NEWLINE);
                document.add(Chunk.NEWLINE);

                Paragraph p1 = new Paragraph("Atividades Previstas", FontFactory.getFont(FontFactory.HELVETICA, 16));
                p1.setAlignment(Element.ALIGN_CENTER);
                document.add(p1);
                document.add(new Paragraph(relatorio.getAtividadesPrevistas()));

                document.add(Chunk.NEWLINE);

                p1 = new Paragraph("Atividades Concluidas", FontFactory.getFont(FontFactory.HELVETICA, 16));
                p1.setAlignment(Element.ALIGN_CENTER);
                document.add(p1);
                document.add(new Paragraph(relatorio.getAtividadesConcluidas()));

                document.add(Chunk.NEWLINE);

                p1 = new Paragraph("Proximos Passos", FontFactory.getFont(FontFactory.HELVETICA, 16));
                p1.setAlignment(Element.ALIGN_CENTER);
                document.add(p1);
                document.add(new Paragraph(relatorio.getProximo()));

                document.add(Chunk.NEWLINE);

                p1 = new Paragraph("Lições Aprendidas e Problemas Encontrados", FontFactory.getFont(FontFactory.HELVETICA, 16));
                p1.setAlignment(Element.ALIGN_CENTER);
                document.add(p1);
                document.add(new Paragraph(relatorio.getLicoesProblemas()));

                document.close();
                name = relatorio.getInicioSemana() + ".pdf";
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        }
        response.setHeader("Expires", "0");
        response.setHeader("Cache-Control",
                "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Pragma", "public");
        response.addHeader("Content-Disposition", "attachment; filename=" + name);
        response.setContentType("application/pdf");
        os.flush();
        os.close();
    }

    public OutputStream getTodosRelatorios(Usuario user, Document document, OutputStream os, RelatorioBO relatorioBO){
        try {
            PdfWriter.getInstance(document,os);
            document.open();
            List<Relatorio> relatorioList = relatorioBO.listarRelatorios(user.getIdUsuario());
            Relatorio relatorio = null;

            for (Relatorio re: relatorioList) {
                if(re.getStatus() != StatusRelatorio.INVALIDO) {
                    relatorio = relatorioBO.buscaRelatorioId(re.getIdRelatorio());

                    document.addTitle("Relatório Semanal");
                    document.addAuthor(user.getNome());

                    Paragraph title = new Paragraph("Relatório Semanal", FontFactory.getFont(FontFactory.HELVETICA, 22));
                    title.setAlignment(Element.ALIGN_CENTER);
                    document.add(title);

                    title = new Paragraph(relatorio.dataAbertura(), FontFactory.getFont(FontFactory.HELVETICA, 18));
                    title.setAlignment(Element.ALIGN_CENTER);
                    document.add(title);

                    title = new Paragraph(usuario.getNome());
                    title.setAlignment(Element.ALIGN_CENTER);
                    document.add(title);
                    document.add(Chunk.NEWLINE);
                    document.add(Chunk.NEWLINE);

                    Paragraph p1 = new Paragraph("Atividades Previstas", FontFactory.getFont(FontFactory.HELVETICA, 16));
                    p1.setAlignment(Element.ALIGN_CENTER);
                    document.add(p1);
                    document.add(new Paragraph(relatorio.getAtividadesPrevistas()));

                    document.add(Chunk.NEWLINE);

                    p1 = new Paragraph("Atividades Concluidas", FontFactory.getFont(FontFactory.HELVETICA, 16));
                    p1.setAlignment(Element.ALIGN_CENTER);
                    document.add(p1);
                    document.add(new Paragraph(relatorio.getAtividadesConcluidas()));

                    document.add(Chunk.NEWLINE);

                    p1 = new Paragraph("Proximos Passos", FontFactory.getFont(FontFactory.HELVETICA, 16));
                    p1.setAlignment(Element.ALIGN_CENTER);
                    document.add(p1);
                    document.add(new Paragraph(relatorio.getProximo()));

                    document.add(Chunk.NEWLINE);

                    p1 = new Paragraph("Lições Aprendidas e Problemas Encontrados", FontFactory.getFont(FontFactory.HELVETICA, 16));
                    p1.setAlignment(Element.ALIGN_CENTER);
                    document.add(p1);
                    document.add(new Paragraph(relatorio.getLicoesProblemas()));

                    document.add(Chunk.NEXTPAGE);
                }
            }

            document.close();

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (NegocioException e) {
            e.printStackTrace();
        }

        return os;

    }
}
