package cn.tedu.sp25.controller.QuizStatics;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class DoactionDrawNewPaper {

    private Font font = new Font("", Font.PLAIN, 30);// 添加字体的属性设置
    private Graphics2D g = null;
    private int fontsize = 0;



    private  int  getRows(int strWidth,int rowWidth){
        int rows=0;
        if(strWidth%rowWidth>0){
            rows=strWidth/rowWidth+1;
        }else{
            rows=strWidth/rowWidth;
        }
        System.out.println("行数:"+rows);
        return rows;
    }

    private int  getStringLength(Graphics g,String str) {
        char[]  strcha=str.toCharArray();
        int strWidth = g.getFontMetrics().charsWidth(strcha, 0, str.length());
        System.out.println("字符总宽度:"+strWidth);
        return strWidth;
    }

    private int  getStringHeight(Graphics g) {
        int height = g.getFontMetrics().getHeight();
        System.out.println("字符高度:"+height);
        return height;
    }

    private int getRowStrNum(int strnum,int rowWidth,int strWidth){
        int rowstrnum=0;
        rowstrnum=(rowWidth*strnum)/strWidth;
        System.out.println("每行的字符数:"+rowstrnum);
        return rowstrnum;
    }
    /**
     * 修改图片,返回修改后的图片缓冲区
     */
    public BufferedImage modifyImage(BufferedImage img, String strContent, Rectangle rect,Color drawColor) {
        try {
            int loc_X=rect.x;
            int loc_Y=rect.y;

            g = img.createGraphics();
            g.setFont(font);
            g.setBackground(Color.WHITE);
            g.setColor(drawColor);
            //获取字符串 字符的总宽度
            int strWidth =getStringLength(g,strContent);
            //每一行字符串宽度
            int rowWidth=rect.width;
            System.out.println("每行字符宽度:"+rowWidth);
            //获取字符高度
            int strHeight=getStringHeight(g);
            //字符串总个数
            System.out.println("字符串总个数:"+strContent.length());
            FontMetrics metrics = g.getFontMetrics(g.getFont());

            if(strWidth>rowWidth){
                int rowstrnum=getRowStrNum(strContent.length(),rowWidth,strWidth);
                int  rows= getRows(strWidth,rowWidth);
                String temp="";
                for (int i = 0; i < rows; i++) {
                    //获取各行的String
                    if(i==rows-1){
                        //最后一行
                        temp=strContent.substring(i*rowstrnum,strContent.length());
                    }else{
                        temp=strContent.substring(i*rowstrnum,i*rowstrnum+rowstrnum);
                    }
                    if(i>0){
                        //第一行不需要增加字符高度，以后的每一行在换行的时候都需要增加字符高度
                        loc_Y=loc_Y+strHeight;
                    }
                    int x = rect.x + (rect.width - metrics.stringWidth(temp)) / 2;
                    // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
                    int y = loc_Y ;
                    if (y>(rect.height+rect.y))
                    {
                        break;
                    }
                    g.drawString(temp, x, y);
                }
            }else{
                int x = rect.x + (rect.width - metrics.stringWidth(strContent)) / 2;
                // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
                int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();

                //直接绘制
                g.drawString(strContent, x, y);
            }


/*
            int w = img.getWidth();
            int h = img.getHeight();
            g = img.createGraphics();
            g.setBackground(Color.WHITE);
            g.setColor(drawColor);
            if (this.font != null)
                g.setFont(this.font);
            FontMetrics metrics = g.getFontMetrics(g.getFont());
            int x = rect.x + (rect.width - metrics.stringWidth(content)) / 2;
            // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
            int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();


            g.drawString(content, x, y);

            g.dispose();*/
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return img;
    }


    /**
     * 在二维码上画上圆形logo图标
     *
     * @param image
     * @return
     * @throws Exception
     */
    public BufferedImage insertLogoImage(BufferedImage image, BufferedImage HeadImage,Rectangle rectme) {
        //Image src = ImageIO.read(new File(logoPath));
        // 插入LOGO
        Graphics2D graph = image.createGraphics();
        // 图片是一个圆型
        Ellipse2D.Double shape = new Ellipse2D.Double(rectme.x, rectme.y, rectme.width, rectme.height);
        // 需要保留的区域
        graph.setClip(shape);
        graph.drawImage(HeadImage, rectme.x, rectme.y, rectme.width, rectme.height, null);
        return image;
    }


    public BufferedImage modifyImagetogeter(BufferedImage Drawbitmap, BufferedImage d,Rectangle rectme) {
        try {
            double douRate_Detination=rectme.getWidth()*1.0/rectme.getHeight();


            int w = Drawbitmap.getWidth();
            int h = Drawbitmap.getHeight();
            g = d.createGraphics();

            double douRate_Raw=w*1.0/h;

            double UseRateX=0;
            double UseRateY=0;
            double UseRateW=0;
            double UseRateH=0;


            if (douRate_Detination>douRate_Raw){
                UseRateH=rectme.getHeight();
                UseRateW=douRate_Raw*UseRateH;
                UseRateX=(rectme.getWidth()-UseRateW)/2+rectme.x;
                UseRateY=rectme.getMinX();
                UseRateY=rectme.y;

            }
            else{

                UseRateW=rectme.getWidth();
                UseRateH=UseRateW/douRate_Raw;
                UseRateX=rectme.x;
                UseRateY=(rectme.getHeight()-UseRateH)/2+rectme.y;
            }


            g.drawImage(Drawbitmap,(int)UseRateX, (int)UseRateY, (int)UseRateW, (int)UseRateH, null);
            g.dispose();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
        return d;
    }
/*
    public static void main(String[] args) {

        DoactionDrawNewPaper tt = new DoactionDrawNewPaper();
        String str = "一个初创却无可ssd限量的团队：我们有来自于fff五百强企业的技术we宅男，有来自于知名房企的品牌经理，有来自于广告行业的资深销售。我们为同一个目标凝聚，励志做一个互联网时代企业革新的引导者，好伙伴。 ";
        BufferedImage d = tt.loadImageLocal("e:\\IMG_0201.JPG");
        tt.setFont("楷体", 30);
        tt.writeImageLocal("e:\\cc.jpg", tt.modifyImage(d, str, 410, 40));
        System.out.println("success");
    }
*/
    /**
     * 导入本地图片到缓冲区
     */
    public BufferedImage loadImageLocal(String imgName) {
        try {
            return ImageIO.read(new File(imgName));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * 导入网络图片到缓冲区
     */
    public BufferedImage loadImageUrl(String imgName) {
        try {
            URL url = new URL(imgName);
            return ImageIO.read(url);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * 生成新图片到本地
     */
    public void writeImageLocal(String newImage, BufferedImage img) {
        if (newImage != null && img != null) {
            try {
                File outputfile = new File(newImage);
                ImageIO.write(img, "jpg", outputfile);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * 设置文字字体
     */
    public void setFont(String fontStyle, int fontSize) {
        this.fontsize = fontSize;
        this.font = new Font(fontStyle, Font.PLAIN, fontSize);
    }

}


/*
*
*
* */


/*  把头像上传到服务器
            *获取用户头像时调用的函数
            getUserImg: function (e) {
                // 查看是否授权
                wx.getSetting({
                        success: function (res) {
                    if (res.authSetting['scope.userInfo']) {
                        // 已经授权，可以直接调用 getUserInfo 获取头像昵称
                        wx.getUserInfo({
                                success: function (res) {
                            var userInfo = res.userInfo
                            var avatarUrl = userInfo.avatarUrl; //获取微信用户头像存放的Url
                            wx.getImageInfo({
                                    src: avatarUrl,
                                    success: function (sres) {       //访问存放微信用户头像的Url
                                wx.saveImageToPhotosAlbum({   //下载用户头像并保存到相册（默认为手机相册weixin目录下）
                                        filePath: sres.path,
                  })
                            }
              })
                        }
          })
                    }
                }
    })
            }
            * */