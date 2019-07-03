import com.itextpdf.awt.geom.GeneralPath;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


/**
 * Author:阿桑
 * Date:2019/7/4/004
 * Description: 内部使用严禁外泄
 * @author Nvarchar
 */
public class GetPDF {
    private static BaseFont bfChinese;

    private static Font headFont;

    private static Font keyFont;

    private static Font textFont;
    int maxWidth = 520;

    Document document = new Document();

    public GetPDF() {
    }

    public static BaseFont getBfChinese() {
        return bfChinese;
    }

    public static void setBfChinese(BaseFont bfChinese) {
        GetPDF.bfChinese = bfChinese;
    }

    public static Font getHeadFont() {
        return headFont;
    }

    public static void setHeadFont(Font headFont) {
        GetPDF.headFont = headFont;
    }

    public static Font getKeyFont() {
        return keyFont;
    }

    public static void setKeyFont(Font keyFont) {
        GetPDF.keyFont = keyFont;
    }

    public static Font getTextFont() {
        return textFont;
    }

    public static void setTextFont(Font textFont) {
        GetPDF.textFont = textFont;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

     static  {
         try {
             bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
         } catch (Exception e) {
             e.printStackTrace();
         }
         headFont = new Font(bfChinese, 10, Font.BOLD);
         keyFont = new Font(bfChinese, 8, Font.BOLD);
         textFont = new Font(bfChinese, 8, Font.NORMAL);
    }

    public GetPDF(File file) throws Exception {
        document.setPageSize(PageSize.A4);
        PdfWriter.getInstance(document, new FileOutputStream(file));
        document.open();
    }

    public void initPDF(File file) throws Exception {
        document.setPageSize(PageSize.A4);
        PdfWriter.getInstance(document, new FileOutputStream(file));
        document.open();
    }

    /**
     * 为表格添加一个内容
     * @param value           值
     * @param font            字体
     * @param align            对齐方式
     * @return                添加的文本框
     */
    public PdfPCell createCell(String value, Font font, int align) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        cell.setPhrase(new Phrase(value, font));
        return cell;
    }

    /**
     * 为表格添加一个内容
     * @param value           值
     * @param font            字体
     * @return                添加的文本框
     */
    public PdfPCell createCell(String value, Font font) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPhrase(new Phrase(value, font));
        return cell;
    }

    /**
     * 为表格添加一个内容
     * @param value           值
     * @param font            字体
     * @param align            对齐方式
     * @param colspan        占多少列
     * @return                添加的文本框
     */
    public PdfPCell createCell(String value, Font font, int align, int colspan) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        cell.setColspan(colspan);
        cell.setPhrase(new Phrase(value, font));
        return cell;
    }

    /**
     * 为表格添加一个内容
     * @param value           值
     * @param font            字体
     * @param align            对齐方式
     * @param colspan        占多少列
     * @param borderFlag        是否有有边框
     * @return                添加的文本框
     */
    public PdfPCell createCell(String value, Font font, int align, int colspan, boolean borderFlag) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        cell.setColspan(colspan);
        cell.setPhrase(new Phrase(value, font));
        cell.setPadding(3.0f);
        if (!borderFlag) {
            cell.setBorder(0);
            cell.setPaddingTop(15.0f);
            cell.setPaddingBottom(8.0f);
        }
        return cell;
    }

    /**
     * 创建一个表格对象
     * @param colNumber  表格的列数
     * @return              生成的表格对象
     */
    public PdfPTable createTable(int colNumber) {
        PdfPTable table = new PdfPTable(colNumber);
            table.setTotalWidth(maxWidth);
            table.setLockedWidth(true);
            table.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.getDefaultCell().setBorder(1);
        return table;
    }

    public PdfPTable createTable(float[] widths) {
        PdfPTable table = new PdfPTable(widths);
            table.setTotalWidth(maxWidth);
            table.setLockedWidth(true);
            table.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.getDefaultCell().setBorder(1);
        return table;
    }

    public PdfPTable createBlankTable() {
        PdfPTable table = new PdfPTable(1);
        table.getDefaultCell().setBorder(0);
        table.addCell(createCell("", keyFont));
        table.setSpacingAfter(20.0f);
        table.setSpacingBefore(20.0f);
        return table;
    }

    public <T> void generatePDF(String [] head, List<T> list, int colNum) throws Exception {
        Class classType = list.get(0).getClass();
        // 创建一个只有5列的表格
        PdfPTable table = createTable(colNum);
        // 添加备注,靠左，不显示边框
        table.addCell(createCell("APP信息列表：", keyFont, Element.ALIGN_LEFT, colNum,false));
        //设置表头
        for(int i = 0 ; i < colNum ; i++) {
            table.addCell(createCell(head[i], keyFont, Element.ALIGN_CENTER));
        }

        if(null != list && list.size() > 0) {
            int size = list.size();
            for(int i = 0 ; i < size ; i++) {
                T t = list.get(i);
                for(int j = 0 ; j < colNum ; j ++) {
                    //获得首字母
                    String firstLetter = head[j].substring(0,1).toUpperCase();
                    //获得get方法,getName,getAge等
                    String getMethodName = "get" + firstLetter + head[j].substring(1);
                    Method method;
                        //通过反射获得相应的get方法，用于获得相应的属性值
                        method = classType.getMethod(getMethodName, new Class[]{});
                            System.out.print(getMethodName +":" + method.invoke(t, new Class[]{}) +",");
                            //添加数据
                            table.addCell(createCell(method.invoke(t, new Class[]{}).toString(), textFont));
                }
            }
        }
            //将表格添加到文档中
            document.add(table);
        //关闭流
        document.close();
    }

    /**
     * 提供外界调用的接口，生成以head为表头，list为数据的pdf
     * @param head  //数据表头
     * @param list  //数据
     * @return        //excel所在的路径
     */
    public <T> String generatePDFs(String [] head,List<T> list) throws Exception {
        final String FilePath = "pdfPath";
        String saveFilePathAndName = "";
        //获得存储的根目录
        String savePath = "D:\\PDF\\";
        //获得当天存储的路径,不存在则生成当天的文件夹
        File file = new File(savePath);
            file.createNewFile();
        initPDF(file);
            file.createNewFile();  //生成一个pdf文件
        new GetPDF(file).generatePDF(head,list,head.length);

        return saveFilePathAndName;
    }
    public static void main(String[] args) throws Exception {
        String [] head = {"name","sex","adress","height","age","jj"};
        List<ForPDF> list = new ArrayList<ForPDF>();
        ForPDF user1 = new ForPDF("zhangsan",1,1.1f,"北京","男","AA");
        ForPDF user2 = new ForPDF("lisi",22222,3.2f,"上海","女","BB");

        list.add(user1);
        list.add(user2);


        String filePath = new GetPDF().generatePDFs(head,list);
        System.out.println(filePath);
    }
}
