package com.ch999.haha;

import com.ch999.haha.admin.document.mongo.MongoTestBO;
import com.ch999.haha.admin.repository.mongo.MongoTestBoRepository;
import com.ch999.haha.admin.service.AnimalService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HaHaApiApplicationTests {


    private final String URL = "https://baike.baidu.com/item/%s";

    private static String[] cat = {"暹罗猫" ,
            "布偶猫" ,
            "苏格兰折耳猫" ,
            "英国短毛猫" ,
            "波斯猫" ,
            "俄罗斯蓝猫" ,
            "美国短毛猫" ,
            "异国短毛猫" ,
            "挪威森林猫" ,
            "孟买猫" ,
            "缅因猫" ,
            "埃及猫" ,
            "伯曼猫" ,
            "斯芬克斯猫" ,
            "缅甸猫" ,
            "阿比西尼亚猫" ,
            "新加坡猫" ,
            "索马里猫" ,
            "土耳其梵猫" ,
            "美国短尾猫" ,
            "中国狸花猫" ,
            "西伯利亚森林猫" ,
            "日本短尾猫" ,
            "巴厘猫" ,
            "土耳其安哥拉猫" ,
            "褴褛猫" ,
            "东奇尼猫" ,
            "柯尼斯卷毛猫" ,
            "马恩岛猫" ,
            "奥西猫" ,
            "沙特尔猫" ,
            "德文卷毛猫" ,
            "呵叻猫" ,
            "美国刚毛猫" ,
            "哈瓦那棕猫" ,
            "重点色短毛猫" ,
            "波米拉猫" ,
            "塞尔凯克卷毛猫" ,
            "拉邦猫" ,
            "美国卷毛猫" ,
            "东方猫" ,
            "欧洲缅甸猫"};

    private static String[] dog = {"哈士奇" ,
            "藏獒" ,
            "贵宾犬" ,
            "松狮" ,
            "边境牧羊犬" ,
            "吉娃娃" ,
            "德国牧羊犬" ,
            "秋田犬" ,
            "蝴蝶犬" ,
            "博美犬" ,
            "杜宾犬" ,
            "柴犬" ,
            "大丹犬" ,
            "卡斯罗" ,
            "法国斗牛犬" ,
            "罗威纳犬" ,
            "英国斗牛犬" ,
            "萨摩耶犬" ,
            "阿富汗猎犬" ,
            "腊肠犬" ,
            "巴哥犬" ,
            "西施犬" ,
            "大白熊犬" ,
            "圣伯纳犬" ,
            "金毛寻回犬" ,
            "法老王猎犬" ,
            "斗牛梗" ,
            "阿拉斯加雪橇犬" ,
            "马尔济斯犬" ,
            "兰波格犬" ,
            "西高地白梗" ,
            "比利时牧羊犬" ,
            "卷毛比雄犬" ,
            "寻血猎犬" ,
            "纽芬兰犬" ,
            "北京犬" ,
            "猎兔犬" ,
            "爱尔兰猎狼犬" ,
            "伯恩山犬" ,
            "喜乐蒂牧羊犬" ,
            "波尔多犬" ,
            "迷你杜宾" ,
            "惠比特犬" ,
            "中国冠毛犬" ,
            "贝灵顿梗" ,
            "柯利犬" ,
            "杰克罗素梗" ,
            "哈瓦那犬" ,
            "苏格兰梗" ,
            "拉布拉多寻回犬" ,
            "大麦町犬" ,
            "美国爱斯基摩犬" ,
            "苏俄猎狼犬" ,
            "万能梗" ,
            "波音达" ,
            "刚毛猎狐梗" ,
            "葡萄牙水犬" ,
            "波利犬" ,
            "约克夏梗" ,
            "拉萨犬" ,
            "中国沙皮犬" ,
            "卡迪根威尔士柯基犬" ,
            "波士顿梗" ,
            "比格猎犬" ,
            "英国可卡犬" ,
            "古代英国牧羊犬" ,
            "雪纳瑞犬" ,
            "巴吉度犬" ,
            "美国可卡犬" ,
            "西藏猎犬" ,
            "马士提夫獒犬" ,
            "凯利蓝梗" ,
            "斗牛獒犬" ,
            "法国狼犬" ,
            "彭布罗克威尔士柯基犬" ,
            "澳大利亚牧羊犬" ,
            "英国猎狐犬" ,
            "丝毛梗" ,
            "匈牙利牧羊犬" ,
            "拳狮犬" ,
            "山地犬" ,
            "罗得西亚脊背犬" ,
            "西藏梗" ,
            "爱尔兰雪达犬" ,
            "湖畔梗" ,
            "瑞典柯基犬" ,
            "芬兰拉普猎犬" ,
            "德国宾莎犬" ,
            "库瓦兹犬" ,
            "奇努克犬" ,
            "巨型雪纳瑞犬" ,
            "维希拉猎犬" ,
            "萨路基猎犬" ,
            "澳大利亚牧牛犬" ,
            "威尔士梗" ,
            "格雷伊猎犬" ,
            "普罗特猎犬" ,
            "墨西哥无毛犬" ,
            "短毛猎狐梗" ,
            "小型斗牛梗" ,
            "斯塔福郡斗牛梗" ,
            "威玛犬" ,
            "意大利灰狗" ,
            "荷兰毛狮犬" ,
            "爱尔兰水猎犬" ,
            "冰岛牧羊犬" ,
            "美国猎狐犬" ,
            "安纳托利亚牧羊犬" ,
            "帕尔森罗塞尔梗" ,
            "短脚长身梗" ,
            "英国跳猎犬" ,
            "爱尔兰梗" ,
            "挪威伦德猎犬" ,
            "挪威猎鹿犬" ,
            "西帕基犬" ,
            "黑俄罗斯梗" ,
            "波兰低地牧羊犬" ,
            "苏格兰猎鹿犬" ,
            "挪威梗" ,
            "爱尔兰红白雪达犬" ,
            "大瑞士山地犬" ,
            "罗秦犬" ,
            "那不勒斯獒" ,
            "捷克梗" ,
            "比利时马林诺斯犬" ,
            "锡利哈姆梗" ,
            "德国短毛波音达" ,
            "巴仙吉犬" ,
            "红骨猎浣熊犬" ,
            "戈登雪达犬" ,
            "诺福克梗" ,
            "小型葡萄牙波登可犬" ,
            "骑士查理王小猎犬" ,
            "美国斯塔福郡梗" ,
            "粗毛柯利犬" ,
            "切萨皮克海湾寻回犬" ,
            "比利时特伏丹犬" ,
            "玩具猎狐梗" ,
            "日本狆" ,
            "玩具曼彻斯特犬" ,
            "爱尔兰峡谷梗" ,
            "澳大利亚梗" ,
            "芬兰波美拉尼亚丝毛狗" ,
            "挪威布哈德犬" ,
            "爱尔兰软毛梗" ,
            "卷毛寻回犬" ,
            "猴头梗" ,
            "英国玩具犬" ,
            "迦南犬" ,
            "弗莱特寻回犬" ,
            "布雷猎犬" ,
            "布鲁塞尔格里芬犬" ,
            "德国硬毛波音达" ,
            "黑褐猎浣熊犬" ,
            "美国水猎犬" ,
            "布列塔尼犬" ,
            "西班牙小猎犬" ,
            "史毕诺犬" ,
            "比利牛斯牧羊犬" ,
            "波兰德斯布比野犬" ,
            "树丛浣熊猎犬" ,
            "布鲁克浣熊猎犬" ,
            "英国猎浣熊犬" ,
            "伊比赞猎犬" ,
            "迷你贝吉格里芬凡丁犬" ,
            "新斯科舍猎鸭寻猎犬" ,
            "捕鼠梗" ,
            "英格兰雪达犬" ,
            "田野小猎犬" ,
            "博得猎狐犬" ,
            "威尔士跳猎犬" ,
            "苏塞克斯猎犬" ,
            "硬毛指示格里芬犬" ,
            "博伊金猎犬" ,
           };

    @Resource
    private MongoTestBoRepository mongoTestBoRepository;

    @Resource
    private AnimalService animalService;

    @Test
    public void contextLoads() {
        MongoTestBO one = mongoTestBoRepository.findOne("eb0fb842-0513-44e7-9968-cf3078833ca0");
        System.out.println(one);
    }


    /**
     * 爬取百度百科猫狗知识
     * @throws Exception
     */
    @Test
    public void httpTest() throws Exception {
        /*for(int i = 0;i<cat.length;i++){
            String format = String.format(URL, cat[i]);
            String s = HttpClientUtils.get(format);
            JXDocument jxDocument = new JXDocument(s);
            List<Object> rs = jxDocument.sel("//div[@class='lemma-summary']//text()");
            List<Object> p = jxDocument.sel("//div[@class='summary-pic']/a/img/@src");
            String name = cat[i];
            String body = null;
            String pic = null;
            for (Object o : rs) {
                if (o instanceof Element) {
                    int index = ((Element) o).siblingIndex();
                    System.out.println(index);
                }
                body = o.toString();
                System.out.println(o.toString());
            }
            for (Object o : p) {
                if (o instanceof Element) {
                    int index = ((Element) o).siblingIndex();
                    System.out.println(index);
                }
                pic = o.toString();
                System.out.println(o.toString());
            }
            Animal animal = new Animal();
            animal.setName(cat[i]);
            animal.setBody(body);
            animal.setPic(pic);
            animal.setType(2);
            boolean insert = animalService.insert(animal);
            System.out.println(insert);
        }*/
    }

    public static void main(String[] args) {
        System.out.println(dog.length);
    }
}
