package io.sbed.common.utils.ppt;

import org.apache.poi.POIXMLDocumentPart;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.sl.usermodel.AutoNumberingScheme;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xslf.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.drawingml.x2006.chart.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * @author heguoliang
 * @Description: TODO(PPT公具类)
 * @date 2017-11-30 11:09
 */
public class PPTUtils {

    public static void main(String[] args) throws Exception {
        /*List<SeriesData> dataList = new ArrayList<SeriesData>();
        SeriesData seriesData = new SeriesData("优", 10);
        dataList.add(seriesData);
        seriesData = new SeriesData("中", 150);
        dataList.add(seriesData);
        seriesData = new SeriesData("及格", 80);
        dataList.add(seriesData);
        seriesData = new SeriesData("不及格", 20);
        dataList.add(seriesData);

        GraphData graphData = new GraphData("成绩情况", dataList);

        Map<Integer, GraphData> graphDatas=new HashMap<>();
        graphDatas.put(1, graphData);

        XMLSlideShow pptx = updatePPTGrapth("D:/t.pptx", graphDatas);
        // 保存文件
        OutputStream out = new FileOutputStream("D:/test.pptx");
        pptx.write(out);
        out.close();
        pptx.close();*/

        XMLSlideShow pptx = updatePPTContent("D:/t.pptx");
        // 保存文件
        OutputStream out = new FileOutputStream("D:/test.pptx");
        pptx.write(out);
        out.close();
        pptx.close();

        /*List<SeriesData> dataList = new ArrayList<SeriesData>();
        SeriesData seriesData = new SeriesData("优", 6.92);
        dataList.add(seriesData);
        seriesData = new SeriesData("中", 5.95);
        dataList.add(seriesData);
        seriesData = new SeriesData("及格", 7.0);
        dataList.add(seriesData);
        seriesData = new SeriesData("不及格", 6.1);
        dataList.add(seriesData);

        GraphData graphData = new GraphData("成绩情况", dataList);

        List<SeriesData> dataList1 = new ArrayList<SeriesData>();
        SeriesData seriesData1 = new SeriesData("优", 6.53);
        dataList1.add(seriesData1);
        seriesData1 = new SeriesData("中", 5.72);
        dataList1.add(seriesData1);
        seriesData1 = new SeriesData("及格", 7.56);
        dataList1.add(seriesData1);
        seriesData1 = new SeriesData("不及格", 6.0);
        dataList1.add(seriesData1);

        GraphData graphData1 = new GraphData("级成绩情况", dataList1);

        List<GraphData> graphDataList = new ArrayList<>();
        graphDataList.add(graphData);
        graphDataList.add(graphData1);

        Map<Integer, List<GraphData>> graphDatas=new HashMap<>();
        graphDatas.put(0, graphDataList);

        XMLSlideShow pptx = updatePPTGrapthOfBarAndLine("D:/t.pptx", graphDatas);
        // 保存文件
        OutputStream out = new FileOutputStream("D:/test.pptx");
        pptx.write(out);
        out.close();
        pptx.close();*/

        /*List<SeriesData> dataList = new ArrayList<SeriesData>();
        SeriesData seriesData = new SeriesData("优", 6.92);
        dataList.add(seriesData);
        seriesData = new SeriesData("中", 5.95);
        dataList.add(seriesData);
        seriesData = new SeriesData("及格", 7.0);
        dataList.add(seriesData);
        seriesData = new SeriesData("不及格", 6.1);
        dataList.add(seriesData);

        GraphData graphData = new GraphData("成绩情况", dataList);

        List<SeriesData> dataList1 = new ArrayList<SeriesData>();
        SeriesData seriesData1 = new SeriesData("优", 6.53);
        dataList1.add(seriesData1);
        seriesData1 = new SeriesData("中", 5.72);
        dataList1.add(seriesData1);
        seriesData1 = new SeriesData("及格", 7.56);
        dataList1.add(seriesData1);
        seriesData1 = new SeriesData("不及格", 6.0);
        dataList1.add(seriesData1);

        GraphData graphData1 = new GraphData("级成绩情况", dataList1);

        List<GraphData> graphDataList = new ArrayList<>();
        graphDataList.add(graphData);
        graphDataList.add(graphData1);
        graphDataList.add(graphData);
        graphDataList.add(graphData1);
        graphDataList.add(graphData);

        Map<Integer, List<GraphData>> graphDatas=new HashMap<>();
        graphDatas.put(0, graphDataList);

        XMLSlideShow pptx = updatePPTGrapth("D:/t.pptx", graphDatas);
        // 保存文件
        OutputStream out = new FileOutputStream("D:/test.pptx");
        pptx.write(out);
        out.close();
        pptx.close();*/

        /*List<SeriesData> dataList = new ArrayList<SeriesData>();
        SeriesData seriesData = new SeriesData("优", 6.92);
        dataList.add(seriesData);
        seriesData = new SeriesData("中", 5.95);
        dataList.add(seriesData);
        seriesData = new SeriesData("及格", 7.0);
        dataList.add(seriesData);
        seriesData = new SeriesData("不及格", 6.1);
        dataList.add(seriesData);

        GraphData graphData = new GraphData("成绩情况", dataList);

        List<SeriesData> dataList1 = new ArrayList<SeriesData>();
        SeriesData seriesData1 = new SeriesData("优", 6.53);
        dataList1.add(seriesData1);
        seriesData1 = new SeriesData("中", 5.72);
        dataList1.add(seriesData1);
        seriesData1 = new SeriesData("及格", 7.56);
        dataList1.add(seriesData1);
        seriesData1 = new SeriesData("不及格", 6.0);
        dataList1.add(seriesData1);

        GraphData graphData1 = new GraphData("级成绩情况", dataList1);

        List<GraphData> graphDataList = new ArrayList<>();
        graphDataList.add(graphData);
        graphDataList.add(graphData1);
        graphDataList.add(graphData);

        List<GraphData> graphDataList1 = new ArrayList<>();
        graphDataList1.add(graphData1);
        graphDataList1.add(graphData);

        Map<Integer, List<GraphData>> graphDatas=new HashMap<>();
        graphDatas.put(0, graphDataList);
        graphDatas.put(1, graphDataList1);

        Map<Integer, Map<Integer, List<GraphData>>> gds=new HashMap<>();
        gds.put(0, graphDatas);

        XMLSlideShow pptx = updatePPTGrapthOfBarAndLine("D:/t.pptx", gds);
        // 保存文件
        OutputStream out = new FileOutputStream("D:/test.pptx");
        pptx.write(out);
        out.close();
        pptx.close();*/

    }

    /**
     * 更新ppt的内容
     * @param templateFilePath 模板文件的路径
     * @return
     * @throws Exception
     */
    public static XMLSlideShow updatePPTContent(String templateFilePath) throws Exception {
        // 打开模板ppt
        XMLSlideShow pptx = new XMLSlideShow(new FileInputStream(templateFilePath));
        if (pptx.getSlides() != null && pptx.getSlides().size() > 0) {
            // 遍历每一页
            for (int i = 0;i < pptx.getSlides().size();i++) {
                for (XSLFShape shape : pptx.getSlides().get(i).getShapes()) {
                    //System.out.println(i+": "+shape);
                    /*if(shape instanceof XSLFTable){
                        XSLFTable xslfTable = (XSLFTable) shape;
                        //System.out.println(xslfTable.getShapeName());
                        if("表格 2".equals(xslfTable.getShapeName())){
                            Object[][] datas = {{"陈小姐" , "15817048601",  "女", "23"}, {"陈小姐" , "15817048603",  "女", "24"}, {"陈先生", "15817048602", "男", "28"}};
                            for(int j = 0; j < datas.length; j++){
                                XSLFTableRow tableRow = xslfTable.addRow();
                                for(int k = 0; k < datas[j].length; k++){
                                    XSLFTableCell tableCell = tableRow.addCell();//创建表格单元格
                                    XSLFTextParagraph p = tableCell.addNewTextParagraph();
                                    XSLFTextRun tr = p.addNewTextRun();
                                    tr.setText(String.valueOf(datas[j][k]));
                                    tr.setFontSize(12D);
                                    tr.setFontFamily("宋体");
                                }
                            }
                            // 合并单元格
                            //xslfTable.mergeCells(1, 2, 0, 0);
                        }
                        *//*for(XSLFTableCell cell : xslfTable.getRows().get(1).getCells()){

                        }*//*
                    }*/
                    if (shape instanceof XSLFTextBox) {
                        XSLFTextBox textBox= (XSLFTextBox) shape;
                        //System.out.println(textBox.getText());
                        if (textBox.getText().contains("{title}")) {
                            textBox.clearText();
                            //textBox.setFillColor(Color.LIGHT_GRAY);
                            XSLFTextParagraph paragraph=textBox.addNewTextParagraph();
                            XSLFTextRun run=paragraph.addNewTextRun();
                            run.setText("大街道的名字");
                            run.setBold(true);// 加粗
                            run.setFontSize(14D);
                            run.setFontFamily("宋体");

                        } else if (textBox.getText().contains("{name}")) {
                            textBox.setText(textBox.getText().replace("{name}", "何先生"));
                            for (XSLFTextParagraph paragraph : textBox.getTextParagraphs()) {
                                for (XSLFTextRun run : paragraph.getTextRuns()) {
                                    run.setFontSize(14D);
                                    run.setFontFamily("宋体");
                                }
                            }
                        } else if (textBox.getText().contains("{date}")) {
                            textBox.setText(textBox.getText().replace("{date}", "2017.12.01"));
                            for (XSLFTextParagraph paragraph : textBox.getTextParagraphs()) {
                                for (XSLFTextRun run : paragraph.getTextRuns()) {
                                    run.setFontSize(12D);
                                    run.setFontFamily("宋体");
                                }
                            }
                        } else if (textBox.getText().contains("{test}")) {
                            /*String str="华尔街是纽约市曼哈顿区南部从百老汇路延伸到东河的一条大街道的名字。在岗实践：做好资源配置，协调项目平稳进行；定期与项目涉及人员沟通，了解员工工作进度和障碍，确保项目高效进行；定期回顾项目推动情况，保证项目高效落地，找到未来提升方向。";
                            str+="\r\n“华尔街”一词现已超越这条街道本身，成为附近区域的代称，亦可指对整个美国经济具有影响力的金融市场和金融机构。";
                            str+="\r\n每次啊。";
                            textBox.setText(textBox.getText().replace("{test}", str));*/
                            //System.out.println(textBox.getTextParagraphs().size());
                            /*for (XSLFTextParagraph paragraph : textBox.getTextParagraphs()) {
                                //System.out.println("SpaceBefore:"+paragraph.getSpaceBefore());
                                //System.out.println("SpaceAfter:"+paragraph.getSpaceAfter());
                                //System.out.println("LineSpacing"+paragraph.getLineSpacing());
                                //paragraph.setIndentLevel(1);
                                //paragraph.setBullet(true);
                                //System.out.println(paragraph.getTextRuns().size());
                                System.out.println(paragraph.getIndent());
                                for (XSLFTextRun run : paragraph.getTextRuns()) {
                                    //run.setFontSize(14.0);
                                    //run.setFontFamily("宋体");
                                    //System.out.println("CharacterSpacing:"+run.getCharacterSpacing());
                                }
                            }*/
                            textBox.clearText();
                            XSLFTextParagraph paragraph=textBox.addNewTextParagraph();
                            //paragraph.setBullet(true);
                            paragraph.setBulletAutoNumber(AutoNumberingScheme.arabicPeriod, 1);
                            paragraph.setIndent(-18.0);
                            paragraph.setLeftMargin(18.0);
                            paragraph.setLineSpacing(150.0);//行距
                            //System.out.println(paragraph.getBulletStyle().getAutoNumberingScheme());
                            XSLFTextRun run=paragraph.addNewTextRun();
                            run.setText("华尔街是纽约市曼哈顿区南部从百老汇路延伸到东河的一条大街道的名字。在岗实践：做好资源配置，协调项目平稳进行；定期与项目涉及人员沟通，了解员工工作进度和障碍，确保项目高效进行；定期回顾项目推动情况，保证项目高效落地，找到未来提升方向。");
                            run.setFontSize(12D);
                            run.setFontFamily("宋体");
                            paragraph=textBox.addNewTextParagraph();
                            //paragraph.setBullet(true);
                            paragraph.setBulletAutoNumber(AutoNumberingScheme.arabicPeriod, 1);
                            paragraph.setIndent(-18.0);
                            paragraph.setLeftMargin(18.0);
                            paragraph.setLineSpacing(150.0);//行距
                            run=paragraph.addNewTextRun();
                            run.setText("“华尔街”一词现已超越这条街道本身，成为附近区域的代称，亦可指对整个美国经济具有影响力的金融市场和金融机构。");
                            run.setFontSize(12D);
                            run.setFontFamily("宋体");
                        }
                    }
                    /*if (shape instanceof XSLFTextShape) {
                        XSLFTextShape txShape = (XSLFTextShape) shape;
                        System.out.println("XSLFTextShape: "+txShape.getText());
                        if (txShape.getText().contains("{name}")) {
                            txShape.setText(txShape.getText().replace("{name}", "何先生"));
                        } else if (txShape.getText().contains("{date}")) {
                            //txShape.setText(txShape.getText().replace("{date}", "2017.12.01"));
                            for (XSLFTextParagraph paragraph : txShape.getTextParagraphs()) {
                                for (XSLFTextRun run : paragraph.getTextRuns()) {
                                    System.out.println(run.getRawText());
                                }
                            }
                        } else if (txShape.getText().contains("{test}")) {
                            //String str="华尔街是纽约市曼哈顿区南部从百老汇路延伸到东河的一条大街道的名字。在岗实践：做好资源配置，协调项目平稳进行；定期与项目涉及人员沟通，了解员工工作进 度和障碍，确保项目高效进行；定期回顾项目推动情况，保证项目高效落地，找到未来提升方向。";
                            //String str1="“华尔街”一词现已超越这条街道本身，成为附近区域的代称，亦可指对整个美国经济具有影响力的金融市场和金融机构。";
                            //String str2="每次啊。";
                            //txShape.setText(txShape.getText().replace("{test}", str2));
                            //txShape.appendText(str, false);
                            //txShape.appendText(str1, false);
                            //txShape.setText(txShape.getText().replace("{test}", ""));
                            txShape.clearText();
                            XSLFTextParagraph paragraph=txShape.addNewTextParagraph();
                            XSLFTextRun run=paragraph.addNewTextRun();
                            run.setText("华尔街是纽约市曼哈顿区南部从百老汇路延伸到东河的一条大街道的名字。在岗实践：做好资源配置，协调项目平稳进行；定期与项目涉及人员沟通，了解员工工作进度和障碍，确保项目高效进行；定期回顾项目推动情况，保证项目高效落地，找到未来提升方向。");
                            run.setFontSize(12D);
                            run.setFontFamily("宋体");
                            run.setFontColor(Color.BLUE);
                            paragraph=txShape.addNewTextParagraph();
                            run=paragraph.addNewTextRun();
                            run.setText("“华尔街”一词现已超越这条街道本身，成为附近区域的代称，亦可指对整个美国经济具有影响力的金融市场和金融机构。");
                            run.setFontSize(12D);
                            run.setFontFamily("宋体");
                            run.setFontColor(Color.RED);
                            //txShape.setAnchor(new java.awt.Rectangle(50, 150, 400, 400));
                            txShape.resizeToFitText();
                        } else if (txShape.getText().contains("{test1}")) {
                            txShape.setText(txShape.getText().replace("{test1}", "评价标准"));
                        }
                    }*/
                    /*if (shape instanceof XSLFGroupShape) {
                        XSLFGroupShape grShape = (XSLFGroupShape) shape;
                        System.out.println("XSLFGroupShape: "+grShape.getShapes());
                        for(XSLFShape s : grShape.getShapes()){
                            if (s instanceof XSLFTextShape) {
                                XSLFTextShape txShape = (XSLFTextShape) s;
                                System.out.println("XSLFGroupShape XSLFTextShape: "+txShape.getText());
                            }
                            if (s instanceof XSLFAutoShape) {
                                XSLFAutoShape atShape = (XSLFAutoShape) s;
                                System.out.println("XSLFGroupShape XSLFAutoShape: "+atShape.getText());
                            }
                        }
                    }*/
                    /*if (shape instanceof XSLFAutoShape) {
                        XSLFAutoShape atShape = (XSLFAutoShape) shape;
                        System.out.println("XSLFAutoShape: "+atShape.getText());
                    }*/
                }
            }
        }
        return pptx;
    }

    /**
     * 更新ppt的图表
     * @param templateFilePath 模板文件的路径
     * @param graphDatas 图表数据map，map的key为ppt的页码下标
     * @return
     * @throws Exception
     */
    public static XMLSlideShow updatePPTGrapth(String templateFilePath, Map<Integer, List<GraphData>> graphDatas) throws Exception {
        // 打开模板ppt
        XMLSlideShow pptx = new XMLSlideShow(new FileInputStream(templateFilePath));
        if (pptx.getSlides() != null && pptx.getSlides().size() > 0) {
            // 遍历每一页
            for (int i = 0;i < pptx.getSlides().size();i++) {
                for (POIXMLDocumentPart part : pptx.getSlides().get(i).getRelations()) {
                    if (part instanceof XSLFChart) {
                        if (graphDatas != null && graphDatas.get(i) != null) {
                            updateGraph((XSLFChart) part, graphDatas.get(i));
                        }
                    }
                }
            }
        }
        return pptx;
    }

    /**
     * 更新ppt的柱状图和线性图的组合图表
     * @param templateFilePath
     * @param graphDatas
     * @return
     * @throws Exception
     */
    public static XMLSlideShow updatePPTGrapthOfBarAndLine(String templateFilePath, Map<Integer, Map<Integer, List<GraphData>>> graphDatas) throws Exception {
        // 打开模板ppt
        XMLSlideShow pptx = new XMLSlideShow(new FileInputStream(templateFilePath));
        if (pptx.getSlides() != null && pptx.getSlides().size() > 0) {
            // 遍历每一页
            for (int i = 0;i < pptx.getSlides().size();i++) {
                for (POIXMLDocumentPart part : pptx.getSlides().get(i).getRelations()) {
                    if (part instanceof XSLFChart) {
                        if (graphDatas != null && graphDatas.get(i) != null) {
                            updateBarAndLineGraph((XSLFChart) part, graphDatas.get(i));
                        }
                    }
                }
            }
        }
        return pptx;
    }

    /**
     * 更新柱状图和线性图的组合图表数据
     * @param chart
     * @param graphDatas 柱状图和线性图的graphDataList的map map的下标0为柱状图的graphDataList，1为线性图的
     * @return
     */
    private static boolean updateBarAndLineGraph(XSLFChart chart, Map<Integer, List<GraphData>> graphDatas) {
        boolean result = true;

        if (graphDatas.isEmpty()) {
            return false;
        }

        if (graphDatas.size() > 3) {
            return false;
        }

        // 把图表绑定到Excel workbook中
        try {
            Workbook wb = new XSSFWorkbook();
            Sheet sheet = wb.createSheet();
            CTChart ctChart = chart.getCTChart();
            CTPlotArea plotArea = ctChart.getPlotArea();
            Row row = null;
            for (Integer i : graphDatas.keySet()) {
                if (graphDatas.get(i).isEmpty()) {
                    return false;
                }

                if (0 == i) {
                    // 柱状图
                    CTBarChart tempChart = plotArea.getBarChartArray(0);
                    CTBarSer ser = null;
                    CTSerTx tx = null;
                    for (int j = 0; j < graphDatas.get(i).size(); j++) {
                        if (graphDatas.get(i).get(j) == null) {
                            return false;
                        }

                        // 获取图表的系列
                        if (tempChart.getSerArray().length <= j) {
                            // 新增
                            ser = tempChart.addNewSer();
                            ser.addNewIdx().setVal(j);
                            ser.addNewOrder().setVal(j);
                            ser.addNewTx().addNewStrRef().addNewStrCache().addNewPt().setIdx(0);
                            ser.addNewCat().addNewStrRef().addNewStrCache().addNewPtCount();
                            ser.addNewVal().addNewNumRef().addNewNumCache().addNewPtCount();
                        } else {
                            ser = tempChart.getSerArray(j);
                        }

                        tx = ser.getTx();
                        tx.getStrRef().getStrCache().getPtArray(0).setV(graphDatas.get(i).get(j).getTitle());
                        row = sheet.getRow(0);
                        if (row == null) {
                            row = sheet.createRow(0);
                        }
                        row.createCell(j+1).setCellValue(graphDatas.get(i).get(j).getTitle());
                        String titleRef = new CellReference(sheet.getSheetName(), 0, j+1, true, true).formatAsString();
                        tx.getStrRef().setF(titleRef);

                        // 获取图表系列的名称
                        CTAxDataSource cat = ser.getCat();
                        // 获取图表系列的值
                        CTNumDataSource val = ser.getVal();
                        updateGraphContent(sheet, cat, val, graphDatas.get(i).get(j), j+1, false);
                    }
                } else if (1==i) {
                    // 线性图
                    CTLineChart tempChart = null;
                    if (plotArea.getLineChartArray().length > 0) {
                        tempChart = plotArea.getLineChartArray(0);
                    } else {
                        tempChart = plotArea.addNewLineChart();
                        /*tempChart.addNewGrouping().setVal(STGrouping.Enum.forString("standard"));
                        tempChart.addNewVaryColors().setVal(false);
                        CTDLbls ctdLbls = tempChart.addNewDLbls();
                        ctdLbls.addNewShowLegendKey().setVal(false);
                        ctdLbls.addNewShowVal().setVal(false);
                        ctdLbls.addNewShowCatName().setVal(false);
                        ctdLbls.addNewShowSerName().setVal(false);
                        ctdLbls.addNewShowPercent().setVal(false);
                        ctdLbls.addNewShowBubbleSize().setVal(false);
                        tempChart.addNewMarker().setVal(true);
                        tempChart.addNewSmooth().setVal(false);*/
                        CTUnsignedInt[] ctUnsignedInts = plotArea.getBarChartArray(0).getAxIdArray();
                        if (ctUnsignedInts != null) {
                            for (CTUnsignedInt ctUnsignedInt : ctUnsignedInts) {
                                if (ctUnsignedInt != null) {
                                    tempChart.addNewAxId().setVal(ctUnsignedInt.getVal());
                                }
                            }
                        }
                    }
                    CTLineSer ser = null;
                    CTSerTx tx = null;
                    for (int j = 0; j < graphDatas.get(i).size(); j++) {
                        if (graphDatas.get(i).get(j) == null) {
                            return false;
                        }

                        // 获取图表的系列
                        if (tempChart.getSerArray().length <= j) {
                            // 新增
                            ser = tempChart.addNewSer();
                            ser.addNewIdx().setVal(graphDatas.get(0).size()+j);
                            ser.addNewOrder().setVal(graphDatas.get(0).size()+j);
                            ser.addNewTx().addNewStrRef().addNewStrCache().addNewPt().setIdx(0);
                            ser.addNewCat().addNewStrRef().addNewStrCache().addNewPtCount();
                            ser.addNewVal().addNewNumRef().addNewNumCache().addNewPtCount();
                        } else {
                            ser = tempChart.getSerArray(j);
                        }

                        tx = ser.getTx();
                        tx.getStrRef().getStrCache().getPtArray(0).setV(graphDatas.get(i).get(j).getTitle());
                        row = sheet.getRow(0);
                        if (row == null) {
                            row = sheet.createRow(0);
                        }
                        row.createCell(graphDatas.get(0).size()+j+1).setCellValue(graphDatas.get(i).get(j).getTitle());
                        String titleRef = new CellReference(sheet.getSheetName(), 0, graphDatas.get(0).size()+j+1, true, true).formatAsString();
                        tx.getStrRef().setF(titleRef);

                        // 获取图表系列的名称
                        CTAxDataSource cat = ser.getCat();
                        // 获取图表系列的值
                        CTNumDataSource val = ser.getVal();
                        updateGraphContent(sheet, cat, val, graphDatas.get(i).get(j), graphDatas.get(0).size()+j+1, false);
                    }
                }
            }

            // 更新嵌入的workbook
            POIXMLDocumentPart xlsPart = chart.getRelations().get(0);
            OutputStream xlsOut = xlsPart.getPackagePart().getOutputStream();
            try {
                wb.write(xlsOut);
                xlsOut.close();
            } catch (IOException e) {
                result = false;
            } finally {
                if (wb != null) {
                    try {
                        wb.close();
                    } catch (IOException e) {
                        result = false;
                    }
                }
            }
        } catch (Exception e) {
            result = false;
        }
        return result;
    }

    private static boolean updateGraph(XSLFChart chart, List<GraphData> graphDatas) {
        String type = getGraphType(chart);
        if (GraphData.GRAPH_PIE.equalsIgnoreCase(type)) {
            return updatePieGraph(chart, graphDatas);
        } else if (GraphData.GRAPH_BAR.equalsIgnoreCase(type)) {
            return updateBarGraph(chart, graphDatas);
        } else if (GraphData.GRAPH_LINE.equalsIgnoreCase(type)) {
            return updateLineGraph(chart, graphDatas);
        } else if (GraphData.GRAPH_AREA.equalsIgnoreCase(type)) {
            return updateAreaGraph(chart, graphDatas);
        } else if (GraphData.GRAPH_RADAR.equalsIgnoreCase(type)) {
            return updateRadarGraph(chart, graphDatas);
        } else {
            return false;
        }
    }

    /**
     * 获取图表类型
     *
     * @param chart
     * @return
     */
    private static String getGraphType(XSLFChart chart)
    {
        String graphTye = "noSupport";
        CTPlotArea plot = chart.getCTChart().getPlotArea();
        if (null != plot && plot.getBarChartList().size() > 0) {
            graphTye = GraphData.GRAPH_BAR;
        } else if (null != plot && plot.getPieChartList().size() > 0) {
            graphTye = GraphData.GRAPH_PIE;
        } else if (null != plot && plot.getLineChartList().size() > 0) {
            graphTye = GraphData.GRAPH_LINE;
        } else if (null != plot && plot.getAreaChartList().size() > 0) {
            graphTye = GraphData.GRAPH_AREA;
        } else if (null != plot && plot.getRadarChartList().size() > 0) {
            graphTye = GraphData.GRAPH_RADAR;
        }
        return graphTye;
    }

    /**
     * 更新圆饼图数据（饼图只有一个系列）
     * @param chart
     * @param graphDatas
     * @return
     */
    private static boolean updatePieGraph(XSLFChart chart, List<GraphData> graphDatas) {
        boolean result = true;

        if (graphDatas.isEmpty()) {
            return false;
        }

        // 把图表绑定到Excel workbook中
        try {
            Workbook wb = new XSSFWorkbook();
            Sheet sheet = wb.createSheet();
            CTChart ctChart = chart.getCTChart();
            CTPlotArea plotArea = ctChart.getPlotArea();
            CTPieChart tempChart = plotArea.getPieChartArray(0);
            // 获取图表的系列
            CTPieSer ser = tempChart.getSerArray(0);
            CTSerTx tx = ser.getTx();
            tx.getStrRef().getStrCache().getPtArray(0).setV(graphDatas.get(0).getTitle());
            sheet.createRow(0).createCell(1).setCellValue(graphDatas.get(0).getTitle());
            String titleRef = new CellReference(sheet.getSheetName(), 0, 1, true, true).formatAsString();
            tx.getStrRef().setF(titleRef);

            // 获取图表系列的名称
            CTAxDataSource cat = ser.getCat();
            // 获取图表系列的值
            CTNumDataSource val = ser.getVal();
            updateGraphContent(sheet, cat, val, graphDatas.get(0), 1, false);

            // 更新嵌入的workbook
            POIXMLDocumentPart xlsPart = chart.getRelations().get(0);
            OutputStream xlsOut = xlsPart.getPackagePart().getOutputStream();
            try {
                wb.write(xlsOut);
                xlsOut.close();
            } catch (IOException e) {
                result = false;
            } finally {
                if (wb != null) {
                    try {
                        wb.close();
                    } catch (IOException e) {
                        result = false;
                    }
                }
            }
        } catch (Exception e) {
            result = false;
        }
        return result;
    }

    /**
     * 更新柱状图数据
     * @param chart
     * @param graphDatas
     * @return
     */
    private static boolean updateBarGraph(XSLFChart chart, List<GraphData> graphDatas) {
        boolean result = true;

        if (graphDatas.isEmpty()) {
            return false;
        }

        // 把图表绑定到Excel workbook中
        try {
            Workbook wb = new XSSFWorkbook();
            Sheet sheet = wb.createSheet();
            CTChart ctChart = chart.getCTChart();
            CTPlotArea plotArea = ctChart.getPlotArea();
            // 获取图表的系列
            CTBarChart tempChart = plotArea.getBarChartArray(0);
            CTBarSer ser = null;
            CTSerTx tx = null;
            Row row = null;
            for (int i = 0; i < graphDatas.size(); i++) {
                // 获取图表的系列
                if (tempChart.getSerArray().length <= i) {
                    // 新增
                    ser = tempChart.addNewSer();
                    ser.addNewIdx().setVal(i);
                    ser.addNewOrder().setVal(i);
                    ser.addNewTx().addNewStrRef().addNewStrCache().addNewPt().setIdx(0);
                    ser.addNewCat().addNewStrRef().addNewStrCache().addNewPtCount();
                    ser.addNewVal().addNewNumRef().addNewNumCache().addNewPtCount();
                } else {
                    ser = tempChart.getSerArray(i);
                }

                tx = ser.getTx();
                tx.getStrRef().getStrCache().getPtArray(0).setV(graphDatas.get(i).getTitle());
                row = sheet.getRow(0);
                if (row == null) {
                    row = sheet.createRow(0);
                }
                row.createCell(i+1).setCellValue(graphDatas.get(i).getTitle());
                String titleRef = new CellReference(sheet.getSheetName(), 0, i+1, true, true).formatAsString();
                tx.getStrRef().setF(titleRef);

                // 获取图表系列的名称
                CTAxDataSource cat = ser.getCat();
                // 获取图表系列的值
                CTNumDataSource val = ser.getVal();
                updateGraphContent(sheet, cat, val, graphDatas.get(i), i+1, false);
            }

            // 更新嵌入的workbook
            POIXMLDocumentPart xlsPart = chart.getRelations().get(0);
            OutputStream xlsOut = xlsPart.getPackagePart().getOutputStream();
            try {
                wb.write(xlsOut);
                xlsOut.close();
            } catch (IOException e) {
                result = false;
            } finally {
                if (wb != null) {
                    try {
                        wb.close();
                    } catch (IOException e) {
                        result = false;
                    }
                }
            }
        } catch (Exception e) {
            result = false;
        }
        return result;
    }

    /**
     * 更新线性图数据
     * @param chart
     * @param graphDatas
     * @return
     */
    private static boolean updateLineGraph(XSLFChart chart, List<GraphData> graphDatas) {
        boolean result = true;

        if (graphDatas.isEmpty()) {
            return false;
        }

        // 把图表绑定到Excel workbook中
        try {
            Workbook wb = new XSSFWorkbook();
            Sheet sheet = wb.createSheet();
            CTChart ctChart = chart.getCTChart();
            CTPlotArea plotArea = ctChart.getPlotArea();
            // 获取图表的系列
            CTLineChart tempChart = plotArea.getLineChartArray(0);
            CTLineSer ser = null;
            CTSerTx tx = null;
            Row row = null;
            for (int i = 0; i < graphDatas.size(); i++) {
                // 获取图表的系列
                if (tempChart.getSerArray().length <= i) {
                    // 新增
                    ser = tempChart.addNewSer();
                    ser.addNewIdx().setVal(i);
                    ser.addNewOrder().setVal(i);
                    ser.addNewTx().addNewStrRef().addNewStrCache().addNewPt().setIdx(0);
                    ser.addNewCat().addNewStrRef().addNewStrCache().addNewPtCount();
                    ser.addNewVal().addNewNumRef().addNewNumCache().addNewPtCount();
                } else {
                    ser = tempChart.getSerArray(i);
                }

                tx = ser.getTx();
                tx.getStrRef().getStrCache().getPtArray(0).setV(graphDatas.get(i).getTitle());
                row = sheet.getRow(0);
                if (row == null) {
                    row = sheet.createRow(0);
                }
                row.createCell(i+1).setCellValue(graphDatas.get(i).getTitle());
                String titleRef = new CellReference(sheet.getSheetName(), 0, i+1, true, true).formatAsString();
                tx.getStrRef().setF(titleRef);

                // 获取图表系列的名称
                CTAxDataSource cat = ser.getCat();
                // 获取图表系列的值
                CTNumDataSource val = ser.getVal();
                updateGraphContent(sheet, cat, val, graphDatas.get(i), i+1, false);
            }

            // 更新嵌入的workbook
            POIXMLDocumentPart xlsPart = chart.getRelations().get(0);
            OutputStream xlsOut = xlsPart.getPackagePart().getOutputStream();
            try {
                wb.write(xlsOut);
                xlsOut.close();
            } catch (IOException e) {
                result = false;
            } finally {
                if (wb != null) {
                    try {
                        wb.close();
                    } catch (IOException e) {
                        result = false;
                    }
                }
            }
        } catch (Exception e) {
            result = false;
        }
        return result;
    }

    /**
     * 更新面积图数据
     * @param chart
     * @param graphDatas
     * @return
     */
    private static boolean updateAreaGraph(XSLFChart chart, List<GraphData> graphDatas) {
        boolean result = true;

        if (graphDatas.isEmpty()) {
            return false;
        }

        // 把图表绑定到Excel workbook中
        try {
            Workbook wb = new XSSFWorkbook();
            Sheet sheet = wb.createSheet();
            CTChart ctChart = chart.getCTChart();
            CTPlotArea plotArea = ctChart.getPlotArea();
            // 获取图表的系列
            CTAreaChart tempChart = plotArea.getAreaChartArray(0);
            CTAreaSer ser = null;
            CTSerTx tx = null;
            Row row = null;
            for (int i = 0; i < graphDatas.size(); i++) {
                // 获取图表的系列
                if (tempChart.getSerArray().length <= i) {
                    // 新增
                    ser = tempChart.addNewSer();
                    ser.addNewIdx().setVal(i);
                    ser.addNewOrder().setVal(i);
                    ser.addNewTx().addNewStrRef().addNewStrCache().addNewPt().setIdx(0);
                    ser.addNewCat().addNewNumRef().addNewNumCache().addNewPtCount();
                    ser.addNewVal().addNewNumRef().addNewNumCache().addNewPtCount();
                } else {
                    ser = tempChart.getSerArray(i);
                }

                tx = ser.getTx();
                tx.getStrRef().getStrCache().getPtArray(0).setV(graphDatas.get(i).getTitle());
                row = sheet.getRow(0);
                if (row == null) {
                    row = sheet.createRow(0);
                }
                row.createCell(i+1).setCellValue(graphDatas.get(i).getTitle());
                String titleRef = new CellReference(sheet.getSheetName(), 0, i+1, true, true).formatAsString();
                tx.getStrRef().setF(titleRef);

                // 获取图表系列的名称
                CTAxDataSource cat = ser.getCat();
                // 获取图表系列的值
                CTNumDataSource val = ser.getVal();
                updateGraphContent(sheet, cat, val, graphDatas.get(i), i+1, true);
            }

            // 更新嵌入的workbook
            POIXMLDocumentPart xlsPart = chart.getRelations().get(0);
            OutputStream xlsOut = xlsPart.getPackagePart().getOutputStream();
            try {
                wb.write(xlsOut);
                xlsOut.close();
            } catch (IOException e) {
                result = false;
            } finally {
                if (wb != null) {
                    try
                    {
                        wb.close();
                    } catch (IOException e) {
                        result = false;
                    }
                }
            }
        } catch (Exception e) {
            result = false;
        }
        return result;
    }

    /**
     * 更新雷达图数据
     * @param chart
     * @param graphDatas
     * @return
     */
    private static boolean updateRadarGraph(XSLFChart chart, List<GraphData> graphDatas) {
        boolean result = true;

        if (graphDatas.isEmpty()) {
            return false;
        }

        // 把图表绑定到Excel workbook中
        try {
            Workbook wb = new XSSFWorkbook();
            Sheet sheet = wb.createSheet();
            CTChart ctChart = chart.getCTChart();
            CTPlotArea plotArea = ctChart.getPlotArea();
            // 获取图表的系列
            CTRadarChart tempChart = plotArea.getRadarChartArray(0);
            CTRadarSer ser = null;
            CTSerTx tx = null;
            Row row = null;
            for (int i = 0; i < graphDatas.size(); i++) {
                // 获取图表的系列
                if (tempChart.getSerArray().length <= i) {
                    // 新增
                    ser = tempChart.addNewSer();
                    ser.addNewIdx().setVal(i);
                    ser.addNewOrder().setVal(i);
                    ser.addNewTx().addNewStrRef().addNewStrCache().addNewPt().setIdx(0);
                    ser.addNewCat().addNewNumRef().addNewNumCache().addNewPtCount();
                    ser.addNewVal().addNewNumRef().addNewNumCache().addNewPtCount();
                } else {
                    ser = tempChart.getSerArray(i);
                }

                tx = ser.getTx();
                tx.getStrRef().getStrCache().getPtArray(0).setV(graphDatas.get(i).getTitle());
                row = sheet.getRow(0);
                if (row == null) {
                    row = sheet.createRow(0);
                }
                row.createCell(i+1).setCellValue(graphDatas.get(i).getTitle());
                String titleRef = new CellReference(sheet.getSheetName(), 0, i+1, true, true).formatAsString();
                tx.getStrRef().setF(titleRef);

                // 获取图表系列的名称
                CTAxDataSource cat = ser.getCat();
                // 获取图表系列的值
                CTNumDataSource val = ser.getVal();
                updateGraphContent(sheet, cat, val, graphDatas.get(i), i+1, true);
            }

            // 更新嵌入的workbook
            POIXMLDocumentPart xlsPart = chart.getRelations().get(0);
            OutputStream xlsOut = xlsPart.getPackagePart().getOutputStream();
            try {
                wb.write(xlsOut);
                xlsOut.close();
            } catch (IOException e) {
                result = false;
            } finally {
                if (wb != null) {
                    try
                    {
                        wb.close();
                    } catch (IOException e) {
                        result = false;
                    }
                }
            }
        } catch (Exception e) {
            result = false;
        }
        return result;
    }

    /**
     * 更新图表内容
     * @param sheet
     * @param cat
     * @param val
     * @param graphData
     * @param rowNum sheet的列的下标
     * @param catNumRef cat是否numRef
     */
    private static void updateGraphContent(Sheet sheet, CTAxDataSource cat, CTNumDataSource val, GraphData graphData, int rowNum, boolean catNumRef) {
        int serSize = graphData.getSerList().size();
        CTStrData catStrData = null;
        CTNumData catNumData = null;

        CTNumData valNumData = val.getNumRef().getNumCache();
        valNumData.setPtArray((CTNumVal[]) null);

        if (catNumRef) {
            catNumData = cat.getNumRef().getNumCache();
            catNumData.setPtArray((CTNumVal[]) null);
        } else {
            catStrData = cat.getStrRef().getStrCache();
            catStrData.setPtArray((CTStrVal[]) null);
        }

        Row row = null;
        Cell cell0 = null;
        for (int i = 0; i < serSize; i++) {
            CTNumVal valNumVal = valNumData.addNewPt();
            valNumVal.setIdx(i);
            valNumVal.setV(graphData.getSerList().get(i).getSerVal() + "");

            if (catNumRef) {
                CTNumVal catNumVal = catNumData.addNewPt();
                catNumVal.setIdx(i);
                catNumVal.setV(graphData.getSerList().get(i).getSerVal() + "");
            } else {
                CTStrVal catStrVal = catStrData.addNewPt();
                catStrVal.setIdx(i);
                catStrVal.setV(graphData.getSerList().get(i).getSerName());
            }

            row = sheet.getRow(i+1);
            if(row == null){
                row = sheet.createRow(i+1);
            }
            cell0 = row.getCell(0);
            if(cell0 == null){
                cell0 = row.createCell(0);
            }
            cell0.setCellValue(graphData.getSerList().get(i).getSerName());
            row.createCell(rowNum).setCellValue(graphData.getSerList().get(i).getSerVal());
        }
        valNumData.getPtCount().setVal(serSize);
        if (catNumRef) {
            catNumData.getPtCount().setVal(serSize);
        } else {
            catStrData.getPtCount().setVal(serSize);
        }

        String numDataRange = new CellRangeAddress(1, serSize, rowNum, rowNum).formatAsString(sheet.getSheetName(), true);
        val.getNumRef().setF(numDataRange);
        String axisDataRange = new CellRangeAddress(1, serSize, 0, 0).formatAsString(sheet.getSheetName(), true);
        if (catNumRef) {
            cat.getNumRef().setF(axisDataRange);
        } else {
            cat.getStrRef().setF(axisDataRange);
        }
    }

}
